package org.criteria4jpa.criterion;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Class representing a "is null" or "is not null" restriction. 
 * It is recommended to use the static factory methods of 
 * {@link Restrictions} to created instances of this class.
 * 
 * @see Restrictions#isNull(String)
 * @see Restrictions#isNotNull(String)
 * 
 * @author Christian Kaltepoth
 *
 */
public class IsNullExpression implements Criterion {

  private final String propertyName;
  private final boolean negate;

  /**
   * Creates a "is null" or "is not null" expression
   * 
   * @param relativePath relative path of a field.
   * @param negate <code>true</code> to create a "is not null" instead
   *   of a "is null" expression
   */
  public IsNullExpression(String relativePath, boolean negate) {
    this.propertyName = relativePath;
    this.negate = negate;
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    String path = queryBuilder.getAbsolutePath(criteria, propertyName);
    if( !negate ) {
      return path+" IS NULL";
    } else {
      return path+" IS NOT NULL";
    }
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#getParameterValues()
   */
  public Object[] getParameterValues() {
    return new Object[0];
  }

}
