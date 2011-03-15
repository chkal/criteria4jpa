package org.criteria4jpa.projection;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Class representing a constructor projection. It is recommended to use the
 * static factory methods of {@link Projections} to created instances of this
 * class.
 * 
 * @author Christian Kaltepoth
 * 
 */
public class ConstructorProjection implements Projection {

  /**
   * The type to create the constructor projection for
   */
  private final Class<?> clazz;
  
  /**
   * The child projection
   */
  private final ProjectionList projectionList = new ProjectionList();

  /**
   * creates a new constructor projection for a wrapped projection.
   * 
   * @param clazz type of the constructor projection
   */
  public ConstructorProjection(Class<?> clazz) {
    this.clazz = clazz;
  }
  
  /*
   * @see org.criteria4jpa.projection.Projection#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {

    StringBuilder builder = new StringBuilder();
    builder.append( "NEW " );
    builder.append( clazz.getName() );
    builder.append( '(' );
    builder.append( projectionList.toQueryString(criteria, queryBuilder) );
    builder.append( ')' );
    return builder.toString();
  
  }

  /*
   * @see org.criteria4jpa.projection.Projection#toGroupByString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toGroupByString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    return projectionList.toGroupByString(criteria, queryBuilder);
  }
  
  /**
   * Adds another projection to the list of projections for this constructor
   * projection.
   * 
   * @param projection
   *          The projection to add
   * @return this (builder pattern)
   */
  public ConstructorProjection add(Projection projection) {
    projectionList.add( projection );
    return this;
  }

}
