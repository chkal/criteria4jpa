package org.criteria4jpa.criterion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Base class for {@link Conjunction} and {@link Disjunction}
 * 
 * @author Christian Kaltepoth
 *
 */
public class Junction implements Criterion {

  private final String op;
  private List<Criterion> criterionList = new ArrayList<Criterion>();
  
  /**
   * Creates the {@link Junction} using the specified operator
   * @param op <code>AND</code> or <code>OR</code> operator
   */
  public Junction(String op) {
    this.op = op;
  }
  
  /**
   * Add a new child to this junction. All children will be joined
   * using the operator if this junction instance.
   * 
   * @param criterion the criterion to add as a child to the junction
   * @return this (builder pattern)
   */
  public Junction add(Criterion criterion) {
    this.criterionList.add( criterion );
    return this;
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    
    // fallback for empty criteria list
    if( criterionList.isEmpty() ) {
      return "1=1";
    }
    
    // build junction
    StringBuilder builder = new StringBuilder();
    builder.append("(");
    
    Iterator<Criterion> critIter = criterionList.iterator();
    while( critIter.hasNext() ) {
      
      // add JPQL string
      builder.append( critIter.next().toQueryString(criteria, queryBuilder) );
      
      // add op if more entries follow
      if( critIter.hasNext() ) {
        builder.append(' ').append(op).append(' ');
      }
      
    }
    
    builder.append(")");
    return builder.toString();
  
  }

  /*
   * @see org.criteria4jpa.criterion.Criterion#getParameterValues()
   */
  public Object[] getParameterValues() {
    ArrayList<Object> values = new ArrayList<Object>();
    
    // process all criteria from criterionList
    for(Criterion criterion : criterionList) {
      
      // add all values from this criterion to result list
      for( Object value : criterion.getParameterValues()) {
        values.add(value);
      }
      
    }
    return values.toArray();
  }
  
}
