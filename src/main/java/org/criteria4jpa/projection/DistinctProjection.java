package org.criteria4jpa.projection;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;


/**
 * 
 * Class representing a "distinct" projection.
 * It is recommended to use the static factory methods
 * of {@link Projections} to created instances of this class.
 * 
 * @see Projections
 * 
 * @author Christian Kaltepoth
 *
 */
public class DistinctProjection implements Projection {

  private final Projection projection;
  
  /**
   * creates a new "distinct" projection for a wrapped projection.
   * 
   * @param projection wrapped projection
   */
  public DistinctProjection(Projection projection) {
    this.projection = projection;
  }

  /*
   * @see org.criteria4jpa.projection.Projection#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    return new StringBuilder()
      .append("DISTINCT ")
      .append( projection.toQueryString(criteria, queryBuilder) )
      .toString();
  }

}
