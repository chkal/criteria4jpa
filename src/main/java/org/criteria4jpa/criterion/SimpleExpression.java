package org.criteria4jpa.criterion;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Basic restriction class for simple expressions "equals", "greater than",
 * "like" and others. It is recommended to use the static factory methods
 * of {@link Restrictions} to created instances of this class.
 * 
 * @see Restrictions
 * 
 * @author Christian Kaltepoth
 *
 */
public class SimpleExpression implements Criterion {

  private final String relativePath;
  private final Object value;
  private final String op;

  /**
   * Creates a simple expression of the form:
   * <pre> 
   * &lt;path expression&gt; &lt;operator&gt; &lt;value&gt;
   * </pre> 
   * 
   * @param relativePath relative path of a <i>persistent field</i> or
   *   <i>single-valued relationship field</i>.
   * @param value a value to check the field against
   * @param op the operator
   */
  public SimpleExpression(String relativePath, Object value, String op) {
    this.relativePath = relativePath;
    this.value = value;
    this.op = op;
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    StringBuilder builder = new StringBuilder();
    builder.append( queryBuilder.getAbsolutePath(criteria, relativePath) );
    builder.append( ' ' );
    builder.append( op );
    builder.append( ' ' );
    builder.append( queryBuilder.createPositionalParameter() );
    return builder.toString();
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#getParameterValues()
   */
  public Object[] getParameterValues() {
    return new Object[] { value };
  }

}
