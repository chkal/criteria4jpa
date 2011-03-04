package org.criteria4jpa.projection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Simple class for <i>projection lists</i> that wrap other projections.
 * It is recommended to use the static factory methods
 * of {@link Projections} to created instances of this class.
 * 
 * @see Projections
 * 
 * @author Christian Kaltepoth
 *
 */
public class ProjectionList implements Projection { 

  private List<Projection> projectionList = new ArrayList<Projection>();
  
  /**
   * empty default constructor
   */
  public ProjectionList() {
    // empty
  }

  /*
   * @see org.criteria4jpa.projection.Projection#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    
    // check whether list is empty
    if( projectionList.isEmpty() ) {
      throw new IllegalStateException("A projection list must have at least one child.");
    }
    
    // query builder
    StringBuilder builder = new StringBuilder();
    
    // iterate over all projections from the list
    Iterator<Projection> iter = projectionList.iterator();
    while( iter.hasNext() ) {
      Projection projection = iter.next();
      
      // call toQueryString() on child
      builder.append( projection.toQueryString(criteria, queryBuilder) );
      
      // append comma if more projections follow
      if(iter.hasNext()) {
        builder.append(",");
      }
    }
    
    // return result
    return builder.toString();
  }
  
  /*
   * @see org.criteria4jpa.projection.Projection#toGroupByString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toGroupByString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {

    // query builder
    StringBuilder builder = new StringBuilder();
    
    // iterate over all projections from the list
    Iterator<Projection> iter = projectionList.iterator();
    while( iter.hasNext() ) {
      Projection projection = iter.next();
      
      // call toGroupByString() on child
      String groupBy = projection.toGroupByString(criteria, queryBuilder);
      
      // add only if result is not null
      if(groupBy != null && groupBy.trim().length() > 0) {

        // first add a comma if the builder already contains something
        if(builder.length() > 0) {
          builder.append(",");
        }
        
        // add group by expression of child
        builder.append(groupBy);
      
      }

    }

    // return result if something has been written to the builder
    String result = builder.toString().trim();
    if(result.length() > 0) {
      return result;
    }
    
    return null;
  }

  /**
   * Adds another projection to the list of wrapped projections.
   * 
   * @param projection projection to wrap
   * @return this (builder pattern)
   */
  public ProjectionList add(Projection projection) {
    projectionList.add( projection );
    return this;
  }

}
