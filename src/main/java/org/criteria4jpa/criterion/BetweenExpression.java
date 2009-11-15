package org.criteria4jpa.criterion;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Class representing a "between" restriction.
 * It is recommended to use the static factory methods of 
 * {@link Restrictions} to created instances of this class.
 * 
 * @see Restrictions#between(String, Object, Object)
 * 
 * @author Christian Kaltepoth
 *
 */
public class BetweenExpression implements Criterion {

  private final String relativePath;
  private final Object value1;
  private final Object value2;
  private final boolean negate;

  /**
   * creates a "between" restriction.
   * 
   * @param relativePath relative path of a <i>persistent field</i>
   * @param value1 the left value
   * @param value2 the right value
   * @param negate create a "not between" instead of a "between" restriction
   */
  public BetweenExpression(String relativePath, Object value1, Object value2, 
      boolean negate) {
    this.relativePath = relativePath;
    this.value1 = value1;
    this.value2 = value2;
    this.negate = negate;
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    StringBuilder builder = new StringBuilder();
    builder.append( queryBuilder.getAbsolutePath(criteria, relativePath) );
    if(negate) {
      builder.append( " NOT" );
    }
    builder.append( " BETWEEN " );
    builder.append( queryBuilder.createPositionalParameter() );
    builder.append( " AND " );
    builder.append( queryBuilder.createPositionalParameter() );
    return builder.toString();
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#getParameterValues()
   */
  public Object[] getParameterValues() {
    return new Object[] { value1, value2 };
  }

}
