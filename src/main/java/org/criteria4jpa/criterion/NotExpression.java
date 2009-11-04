package org.criteria4jpa.criterion;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Class representing a "not" restriction. 
 * It is recommended to use the static factory methods of 
 * {@link Restrictions} to created instances of this class.
 * 
 * @see Restrictions#not(Criterion)
 * 
 * @author Christian Kaltepoth
 *
 */
public class NotExpression implements Criterion {


  private final Criterion criterion;

  /**
   * Creates a "not" expression
   * 
   * @param criterion the restriction to negate
   */
  public NotExpression(Criterion criterion) {
    this.criterion = criterion;
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    return "NOT "+criterion.toQueryString(criteria, queryBuilder);
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#getParameterValues()
   */
  public Object[] getParameterValues() {
    return criterion.getParameterValues();
  }

}
