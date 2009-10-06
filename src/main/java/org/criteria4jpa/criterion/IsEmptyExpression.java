package org.criteria4jpa.criterion;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Class representing a "is empty" or "is not empty" restriction. 
 * It is recommended to use the static factory methods of 
 * {@link Restrictions} to created instances of this class.
 * 
 * @see Restrictions#isEmpty(String)
 * @see Restrictions#isNotEmpty(String)
 * 
 * @author Christian Kaltepoth
 *
 */
public class IsEmptyExpression implements Criterion {

  private final String relativePath;
  private final boolean negate;

  /**
   * Creates a "is empty" or "is not empty" expression
   * 
   * @param relativePath relative path of a collection.
   * @param negate <code>true</code> to create a "is not empty" instead
   *   of a "is empty" expression
   */
  public IsEmptyExpression(String relativePath, boolean negate) {
    this.relativePath = relativePath;
    this.negate = negate;
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    String path = queryBuilder.getAbsolutePath(criteria, relativePath);
    if( !negate ) {
      return path+" IS EMPTY";
    } else {
      return path+" IS NOT EMPTY";
    }
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#getParameterValues()
   */
  public Object[] getParameterValues() {
    return new Object[0];
  }

}
