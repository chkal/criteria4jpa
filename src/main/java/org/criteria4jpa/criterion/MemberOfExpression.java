package org.criteria4jpa.criterion;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Class representing a "(not) member of" restriction. 
 * It is recommended to use the static factory methods of 
 * {@link Restrictions} to created instances of this class.
 * 
 * @see Restrictions#memberOf(String, Object)
 * @see Restrictions#notMemberOf(String, Object)
 * 
 * @author Christian Kaltepoth
 *
 */
public class MemberOfExpression implements Criterion {

  private final String relativePath;
  private final Object value;
  private final boolean negate;

  /**
   * Creates a "(not) member of" expression
   * 
   * @param relativePath relative path of a collection.
   * @param value The value to check membership in the collection for
   * @param negate create negated version of restriction
   */
  public MemberOfExpression(String relativePath, Object value, boolean negate) {
    this.relativePath = relativePath;
    this.value = value;
    this.negate = negate;
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    StringBuilder builder = new StringBuilder();
    builder.append( queryBuilder.createPositionalParameter() );
    if( negate ) {
      builder.append(" NOT");
    }
    builder.append( " MEMBER OF " );
    builder.append( queryBuilder.getAbsolutePath(criteria, relativePath) );
    return builder.toString();
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#getParameterValues()
   */
  public Object[] getParameterValues() {
    return new Object[] { value };
  }

}
