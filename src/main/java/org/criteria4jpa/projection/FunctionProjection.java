package org.criteria4jpa.projection;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Class representing projection based on a function.
 * It is recommended to use the static factory methods
 * of {@link Projections} to created instances of this class.
 * 
 * @see Projections
 * 
 * @author Christian Kaltepoth
 *
 */
public class FunctionProjection implements Projection {

  private final String function;
  private final Projection projection;

  /**
   * creates a new projection.
   * 
   * @param function The name of the function to use
   * @param projection the inner projection
   */
  public FunctionProjection(String function, Projection projection) {
    this.function = function;
    this.projection = projection;
  }

  /*
   * @see org.criteria4jpa.projection.Projection#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    return new StringBuilder()
      .append( function )
      .append( '(')
      .append( projection.toQueryString(criteria, queryBuilder) )
      .append( ')' )
      .toString();
  }
}
