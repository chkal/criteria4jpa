package org.criteria4jpa.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.Criteria.FetchMode;
import org.criteria4jpa.Criteria.JoinType;
import org.criteria4jpa.criterion.Criterion;
import org.criteria4jpa.order.Order;
import org.criteria4jpa.projection.Projection;
import org.criteria4jpa.util.QueryLogHelper;
import org.criteria4jpa.util.StringUtils;

public class CriteriaQueryBuilder {
  
  private final Logger queryLogger = Logger.getLogger("org.criteria4jpa.queries");

  private final CriteriaImpl rootCriteria;
  
  private final Map<Criteria, String> criterionToAliasMap = 
    new HashMap<Criteria, String>();
  
  private int positionalParameterCounter = 1;
  
  public CriteriaQueryBuilder(CriteriaImpl rootCriteria) {
    this.rootCriteria = rootCriteria;
    buildCriterionToAliasMap();
  }
  
  private void buildCriterionToAliasMap() {

    // counter to create unique aliases
    int counter = 1;
    
    // add alias for root criteria
    String rootAlias = rootCriteria.getAlias();
    if(rootAlias == null) {
      rootAlias = StringUtils.generateAlias(rootCriteria.getEntityName(), counter++, "root");
    }
    criterionToAliasMap.put(rootCriteria, rootAlias);
    
    // process subcriteria
    for( SubCriteriaImpl subcriteria : rootCriteria.getSubcriteriaList() ) {
      String alias = subcriteria.getAlias();
      if(alias == null) {
        String lastPathComponent = StringUtils.getLastPathComponent(subcriteria.getPath());
        alias = StringUtils.generateAlias(lastPathComponent, counter++, "sub");
      }
      String old = criterionToAliasMap.put(subcriteria, alias);
      if(old != null) {
        throw new IllegalStateException("Duplicated alias definition for: "+alias);
      }
    }
  }

  public Query createQuery(EntityManager entityManager) {
  
    // create for generate query
    String queryString = createQueryString();
    Query query = entityManager.createQuery(queryString);

    // set max results
    if(rootCriteria.getMaxResults() != null) {
      query.setMaxResults(rootCriteria.getMaxResults());
    }
    
    // set first result
    if(rootCriteria.getFirstResult() != null) {
      query.setFirstResult(rootCriteria.getFirstResult());
    }
    
    // get ordered list of query parameters
    List<Object> parameterValues = getQueryParametersAsList();
    
    // position 
    int paramPosition = 1;

    // loop over all values
    for(Object value : parameterValues) {
      query.setParameter(paramPosition++, value);
    }    
    
    // log query
    if( queryLogger.isLoggable(Level.FINE) ) {
      queryLogger.fine( QueryLogHelper.createQueryLogMessage(queryString, parameterValues) );
    }
    
    return query;
  }
  
  /**
   * Creates a human-readable representation of the query that will be created
   * from this builder
   */
  public String createQueryDebugString() {

    // create query
    String queryString = createQueryString();

    // create list of parameters
    List<Object> parameterValues = getQueryParametersAsList();

    // create debug string
    return QueryLogHelper.createQueryLogMessage(queryString, parameterValues);

  }

  public String createQueryString() {
    
    // build query string
    StringBuilder builder = new StringBuilder();
    
    builder.append( createSelectClause() );
    builder.append( ' ' );
    builder.append( createFromClause() );
    builder.append( ' ' );
    builder.append( createWhereClause() );
    builder.append( ' ' );
    builder.append( createGroupByClause() );
    builder.append( ' ' );
    builder.append( createOrderByClause() );

    return builder.toString().trim();
  }

  private String createSelectClause() {

    // begin with SELECT
    StringBuilder builder = new StringBuilder();
    builder.append( "SELECT " );
    
    if(rootCriteria.getProjectionEntry() != null) {
      
      // get required objects to render projection
      Projection projection = rootCriteria.getProjectionEntry().getEntry();
      Criteria projectionCriteria = rootCriteria.getProjectionEntry().getCriteria();
      
      // render projection
      builder.append( projection.toQueryString(projectionCriteria, this) );
      
    }
    else {
      // if no projection exists, we just select the root entity
      builder.append( getRequiredAlias(rootCriteria) );
    }
    
    return builder.toString();
  }

  private String createFromClause() {
    
    // builder
    StringBuilder builder = new StringBuilder();
    
    // root criteria
    builder.append("FROM ");
    builder.append( rootCriteria.getEntityName() );
    builder.append(' ');
    builder.append( getRequiredAlias(rootCriteria) );

    // process all subcriteria
    for(SubCriteriaImpl subcriteria : rootCriteria.getSubcriteriaList()) {
      
      // absolute join path
      String joinPath = getAbsolutePath(subcriteria.getParent(), subcriteria.getPath());
      
      // append correct join expression
      if(subcriteria.getJoinType() == JoinType.INNER_JOIN) {
        builder.append(" JOIN ");
      }
      else if(subcriteria.getJoinType() == JoinType.LEFT_OUTER_JOIN) {
        builder.append(" LEFT JOIN ");
      }
      
      builder.append( joinPath );
      builder.append(' ');
      builder.append( getRequiredAlias(subcriteria) );
    }
    
    for(MetaEntry<PathFetchMode> fetchModeEntry : rootCriteria.getFetchTypeList()) {
      
      PathFetchMode pathFetchMode = fetchModeEntry.getEntry();

      if(pathFetchMode.getFetchMode() == FetchMode.DEFAULT) {
        continue;
      }
      
      // absolute join path
      String joinPath = getAbsolutePath(fetchModeEntry.getCriteria(), pathFetchMode.getRelativePath());

      // append correct join expression
      if(pathFetchMode.getFetchMode() == FetchMode.INNER_FETCH_JOIN) {
        builder.append(" JOIN FETCH ");
      }
      else if(pathFetchMode.getFetchMode() == FetchMode.LEFT_OUTER_FETCH_JOIN) {
        builder.append(" LEFT JOIN FETCH ");
      }
      else {
        throw new IllegalStateException("Unsupported FetchType: "+pathFetchMode.getFetchMode());
      }
    
      builder.append( joinPath );

      // 4.4.5.3: No alias allowed for fetch joins
    }
    
    // return result
    return builder.toString();
  }
  
  private String createWhereClause() {
    
    // iterate over all criterion of this criteria
    Iterator<MetaEntry<Criterion>> iter = rootCriteria.getCriterionList().iterator();

    // abort if not criterion
    if( !iter.hasNext() ) {
      return "";
    }
    
    // String Builder for where clause
    StringBuilder builder = new StringBuilder("WHERE ");
    
    while(iter.hasNext()) {
      
      MetaEntry<Criterion> entry = iter.next();
      Criteria criteria = entry.getCriteria();
      Criterion criterion = entry.getEntry();
      
      // let criterion build its JPQL string
      builder.append( criterion.toQueryString(criteria, this) );
      
      // append AND if more restrictions follow
      if(iter.hasNext()) {
        builder.append( " AND " );  
      }
    }
    
    return builder.toString();
  }

  private String createGroupByClause() {
    
    if(rootCriteria.getProjectionEntry() != null) {
      
      // get required objects to render projection
      Projection projection = rootCriteria.getProjectionEntry().getEntry();
      Criteria projectionCriteria = rootCriteria.getProjectionEntry().getCriteria();
      
      // render group by properties
      String groupByString = projection.toGroupByString(projectionCriteria, this);
      if( groupByString != null && groupByString.trim().length() > 0) {
        return "GROUP BY "+groupByString.trim();
      }
      
    }
    
    // no group by
    return "";
    
  }
  
  private String createOrderByClause() {

    // get iterator for order objects
    Iterator<MetaEntry<Order>> iter = rootCriteria.getOrderList().iterator();
    
    // no orders added -> exit here
    if( !iter.hasNext() ) {
      return "";
    }
    
    // add ORDER BY keywords
    StringBuilder builder = new StringBuilder("ORDER BY ");
    
    // loop over all order objects
    while( iter.hasNext() ) {

      // current order object
      MetaEntry<Order> orderEntry = iter.next();
      Order order = orderEntry.getEntry();
      Criteria criteria = orderEntry.getCriteria();
      
      // create JPQL string
      String orderByString = order.toQueryString(criteria, this);
      builder.append( orderByString );
      
      // append comma if more to write
      if( iter.hasNext() ) {
        builder.append(", ");
      }
      
    }
    
    return builder.toString();
    
  }
  
  /**
   * Creates an ordered list of all parameter values registered at
   * the root criteria.
   * 
   * @return list of parameter values
   */
  private List<Object> getQueryParametersAsList() {
    
    // iterate over all restrictions of this criteria
    Iterator<MetaEntry<Criterion>> iter = rootCriteria.getCriterionList().iterator();

    // result list
    List<Object> result = new ArrayList<Object>();
    
    // loop over all criterion
    while(iter.hasNext()) {
      
      // the current criterion
      Criterion criterion = iter.next().getEntry();
      
      // get the values to set
      Object[] parameterValues = criterion.getParameterValues();
      
      // null should be ignores
      if(parameterValues == null) {
        continue;
      }
      
      // set all parameters
      for(Object value : parameterValues) {
        result.add( value );
      }
      
    }
    
    return result;
  }

  public String getAbsolutePath(Criteria c, String relativePath) {
    return getRequiredAlias(c)+"."+relativePath;
  }
  
  public String getRequiredAlias(Criteria criteria) {
    String alias = criterionToAliasMap.get(criteria);
    if(alias == null) {
      throw new IllegalStateException("Cannot find alias for criteria: "+criteria);
    }
    return alias;
  }

  public CriteriaImpl getRootCriteria() {
    return rootCriteria;
  }
  
  /**
   * Creates a positional parameter for a JPQL query.
   */
  public String createPositionalParameter() {
    return "?" + (positionalParameterCounter++);
  }

}
