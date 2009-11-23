package org.criteria4jpa.criterion;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Restriction class for case-insensitive LIKE expressions. 
 * It is recommended to use the static factory methods
 * of {@link Restrictions} to created instances of this class.
 * 
 * @see Restrictions#ilike(String, String)
 * @see Restrictions#ilike(String, String, MatchMode)
 * 
 * @author Christian Kaltepoth
 *
 */
public class ILikeExpression implements Criterion {

  private final String relativePath;
  private final String value;

  /**
   * Creates a case-insensitive LIKE expression 
   * 
   * @param relativePath relative path of a string field.
   * @param value a value to check the field against
   */
  public ILikeExpression(String relativePath, String value) {
    this.relativePath = relativePath;
    this.value = value;
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    StringBuilder builder = new StringBuilder();
    builder.append( "LOWER(" );
    builder.append( queryBuilder.getAbsolutePath(criteria, relativePath) );
    builder.append( ") LIKE " );
    builder.append( queryBuilder.createPositionalParameter() );
    return builder.toString();
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#getParameterValues()
   */
  public Object[] getParameterValues() {
    return new Object[] { 
        value != null ? value.toLowerCase() : null
    };
  }

}
