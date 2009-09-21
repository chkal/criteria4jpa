package org.criteria4jpa.projection;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Simple projection that refers project to the entity of the root 
 * criteria of the criteria hierarchy.  It is recommended to use the static 
 * factory methods of {@link Projections} to created instances of this 
 * class.
 * 
 * @see Projections
 * 
 * @author Christian Kaltepoth
 *
 */
public class RootEntityProjection implements Projection {

  /*
   * @see org.criteria4jpa.projection.Projection#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    return queryBuilder.getRequiredAlias( queryBuilder.getRootCriteria() );
  }

}
