package org.criteria4jpa.criterion;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Class representing a "in" restriction. 
 * It is recommended to use the static factory methods of 
 * {@link Restrictions} to created instances of this class.
 * 
 * @see Restrictions#in(String, Object[])
 * @see Restrictions#in(String, java.util.Collection)
 * 
 * @author Christian Kaltepoth
 *
 */
public class InExpression implements Criterion {

  private final String relativePath;
  private final Object[] values;
  
  /**
   * Creates a "in" expression
   * 
   * @param relativePath relative path of a field.
   * @param values Array of expected values
   */
  public InExpression(String relativePath, Object[] values) {
    this.relativePath = relativePath;
    this.values = values;
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {

    // query builder
    StringBuilder builder = new StringBuilder();

    // build the basic query
    builder.append( queryBuilder.getAbsolutePath(criteria, relativePath) );
    builder.append( " IN (");
    
    // We must add each value as a parameter, because not all JPA
    // implementations allow lists or arrays as parameters.
    if( values != null ) {
      for( int i = 0; i < values.length; i++) {
        builder.append('?');
        if( i < values.length - 1 ) {
          builder.append(',');
        }
      }
    }
    builder.append(")");
    
    // return result
    return builder.toString();
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#getParameterValues()
   */
  public Object[] getParameterValues() {
    return values;
  }

}
