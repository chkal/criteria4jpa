package org.criteria4jpa.projection;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Class representing aggregate function projections.
 * It is recommended to use the static factory methods
 * of {@link Projections} to created instances of this class.
 * 
 * @see Projections#min(String)
 * @see Projections#max(String)
 * @see Projections#sum(String)
 * @see Projections#avg(String)
 * 
 * @author Christian Kaltepoth
 *
 */
public class AggregateFunctionProjection implements Projection {

  private final String function;
  private final String relativePath;

  /**
   * creates a new aggregate function projection.
   * 
   * @param function The aggregate function to use
   * @param relativePath relative path of a persistent field
   */
  public AggregateFunctionProjection(String function, String relativePath) {
    this.function = function;
    this.relativePath = relativePath;
  }

  /*
   * @see org.criteria4jpa.projection.Projection#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    return new StringBuilder()
      .append( function )
      .append( '(')
      .append( queryBuilder.getAbsolutePath(criteria, relativePath) )
      .append( ')' )
      .toString();
  }
}
