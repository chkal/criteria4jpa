package org.criteria4jpa.criterion;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Class representing a custom JPQL restriction.
 * It is recommended to use the static factory methods of 
 * {@link Restrictions} to created instances of this class.
 * 
 * @see Restrictions#jpqlRestriction(String, Object...)
 * 
 * @author Christian Kaltepoth
 *
 */
public class JPQLExpression implements Criterion {

  private final String jpql;
  private final Object[] values;

  /**
   * Creates a custom JPQL restriction.
   * 
   * @param jpql The JPQL fragment
   * @param values The query parameters to add to the query
   */
  public JPQLExpression(String jpql, Object[] values) {
    this.jpql = jpql;
    this.values = values;
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    
    // replace alias variable by real name
    String queryFragment = 
      jpql.replace( "{alias}", queryBuilder.getRequiredAlias(criteria) );

    // process parameters
    for(int i = 1; i <= values.length; i++) {
      
      // create positional parameter for this value
      String paramater = queryBuilder.createPositionalParameter();
      
      /*
       * Replace variable with generated positional parameter.
       * Note: This kind of string processing is not very efficient, 
       * because it generates a new string object in each iteration.
       * But we assume that the number of iterations will be
       * relative small, so it won't be a real problem.
       */
      queryFragment = queryFragment.replace( "{"+i+"}", paramater);
      
    }
    
    return queryFragment;
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#getParameterValues()
   */
  public Object[] getParameterValues() {
    return values;
  }

}
