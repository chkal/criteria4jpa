package org.criteria4jpa.criterion;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * <p>
 * Interface describing a restriction that can be added to a {@link Criteria}.
 * </p>
 * 
 * <p>
 * The {@link Criterion} is rendered to a WHERE expression in the generated
 * JPQL query and may be added to a {@link Criteria} by calling
 * {@link Criteria#add(Criterion)}.
 * </p>
 * 
 * <p>
 * You should use the static factory methods of {@link Restrictions} to obtain
 * predefined restrictions.
 * </p>
 * 
 * @author Christian Kaltepoth
 *
 */
public interface Criterion {
  
  /**
   * Renders the {@link Criterion} to a WHERE expression in the JPQL query.
   * 
   * @param criteria The criteria this criterion was added to
   * @param queryBuilder The query builder that is currently building the query
   * @return The JPQL WHERE expression
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder);

  /**
   * Returns the query parameter values of this {@link Criterion}.
   * The order of the values in the array must match the order of the
   * <code>?</code> place holders in the expression created by 
   * {@link #toQueryString(Criteria, CriteriaQueryBuilder)}.
   * 
   * @return Array of query parameters
   */
  public Object[] getParameterValues();
  
}
