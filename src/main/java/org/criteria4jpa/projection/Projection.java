package org.criteria4jpa.projection;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * <p>
 * Interface describing a <i>projection</i> that can be added to
 * a {@link Criteria}.
 * </p>
 * 
 * <p>
 * The {@link Projection} is rendered to the SELECT expression in the generated
 * JPQL query and may be added to a {@link Criteria} by calling
 * {@link Criteria#setProjection(Projection)}.
 * </p>
 * 
 * <p>
 * You should use the static factory methods of {@link Projections} to obtain
 * predefined projections.
 * </p>
 * 
 * @author Christian Kaltepoth
 *
 */
public interface Projection {
 
  /**
   * Renders the {@link Projection} to the SELECT expression of the JPQL query.
   * 
   * @param criteria The criteria this projection was added to
   * @param queryBuilder The query builder that is currently building the query
   * @return The JPQL SELECT expression
   */
 
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder);

}
