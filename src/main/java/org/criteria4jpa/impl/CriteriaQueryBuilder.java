package org.criteria4jpa.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.criterion.Criterion;
import org.criteria4jpa.order.Order;
import org.criteria4jpa.projection.Projection;
import org.criteria4jpa.util.StringUtils;

public class CriteriaQueryBuilder {
  
  private final Logger log = Logger.getLogger(CriteriaQueryBuilder.class.getName());

  private final EntityManager entityManager;
  
  private final CriteriaImpl rootCriteria;
  
  private final Map<Criteria, String> criterionToAliasMap = 
    new HashMap<Criteria, String>();
  
  public CriteriaQueryBuilder(EntityManager entityManager, CriteriaImpl rootCriteria) {
    this.entityManager = entityManager;
    this.rootCriteria = rootCriteria;
    buildCriterionToAliasMap();
  }
  
  private void buildCriterionToAliasMap() {

    // counter to create unique aliases
    int counter = 1;
    
    // add alias for root criteria
    String rootAlias = rootCriteria.getAlias();
    if(rootAlias == null) {
      rootAlias = StringUtils.generateAlias("root", counter++);
    }
    criterionToAliasMap.put(rootCriteria, rootAlias);
    
    // process subcriteria
    for( SubCriteriaImpl subcriteria : rootCriteria.getSubcriteriaList() ) {
      String alias = subcriteria.getAlias();
      if(alias == null) {
        alias = StringUtils.generateAlias("sub", counter++);
      }
      String old = criterionToAliasMap.put(subcriteria, alias);
      if(old != null) {
        throw new IllegalStateException("Duplicated alias definition for: "+alias);
      }
    }
  }

  public Query createQuery() {
  
    // create for generate query
    Query query = entityManager.createQuery(createQueryString());

    // set max results
    if(rootCriteria.getMaxResults() != null) {
      query.setMaxResults(rootCriteria.getMaxResults());
    }
    
    // set first result
    if(rootCriteria.getFirstResult() != null) {
      query.setFirstResult(rootCriteria.getFirstResult());
    }
    
    // set all parameters on the query
    populateParameters(query);
    
    return query;
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
    builder.append( createOrderByClause() );
    
    log.fine("Query: "+builder.toString());
    return builder.toString();
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
      String joinPath = getAbsolutePath(subcriteria.getParent(), subcriteria.getPath());
      builder.append(" JOIN ");
      builder.append( joinPath );
      builder.append(' ');
      builder.append( getRequiredAlias(subcriteria) );
    }
    
    // return result
    return builder.toString();
  }
  
  private String createWhereClause() {
    
    // iterate over all criterions of this criteria
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
  
  private void populateParameters(Query query) {
    
    // iterate over all restrictions of this criteria
    Iterator<MetaEntry<Criterion>> iter = rootCriteria.getCriterionList().iterator();

    // position 
    int paramPosition = 1;
    
    // loop over all criterion
    while(iter.hasNext()) {
      
      // the current criterion
      Criterion criterion = iter.next().getEntry();
      
      // get the values to set
      Object[] values = criterion.getParameterValues();
      
      // null should be ignores
      if(values == null) {
        continue;
      }
      
      // set all parameters
      for(Object value : values) {
        query.setParameter(paramPosition++, value);
      }
      
    }    
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

}
