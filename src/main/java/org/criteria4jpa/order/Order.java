package org.criteria4jpa.order;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * <p>
 * Class to specify result ordering of {@link Criteria} instances. 
 * </p>
 * 
 * <p>
 * Instances of this class are created by calling the static factory methods
 * {@link #asc(String)} or {@link #desc(String)}. Instances can then be added to a
 * criteria by calling {@link Criteria#addOrder(Order)}.
 * </p>
 * 
 * <p>
 * All <i>path expressions</i> are relative to the entity of the {@link Criteria}
 * the ordering is specified for.
 * </p>
 * 
 * <p><strong>Example:</strong></p>
 * 
 * <pre>
 * Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
 * criteria.addOrder( Order.asc("age") );
 * </pre>
 * 
 * @see Criteria
 * @author Christian Kaltepoth
 *
 */
public class Order {

  private String relativePath;
  private boolean ascending;
  
  /**
   * Instance creation only with {@link #asc(String)} and {@link #desc(String)}
   */
  private Order(String relativePath, boolean ascending) {
    this.relativePath = relativePath;
    this.ascending = ascending;
  }

  /**
   * Renders the order to a ORDER BY substring of JPQL query
   * 
   * @param criteria The {@link Criteria} the order is associated with
   * @param queryBuilder The builder of the JPQL query 
   * @return the generated query string
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    String absolutePath = queryBuilder.getAbsolutePath(criteria, relativePath);
    return ascending ? absolutePath : absolutePath+" DESC";
  }

  /**
   * Creates an {@link Order} instance representing an ascending ordering
   * regarding the specified <i>persistent field</i>. The <i>path expression</i>
   * has to be relative to the entity of the {@link Criteria} this ordering
   * is added to. 
   * 
   * @param relativePath the <i>path expression</i> for a <i>persistent field</i>
   * @return new {@link Order} instance
   */
  public static Order asc(String relativePath) {
    return new Order(relativePath, true);
  }

  /**
   * Creates an {@link Order} instance representing an descending ordering
   * regarding the specified <i>persistent field</i>. The <i>path expression</i>
   * has to be relative to the entity of the {@link Criteria} this ordering
   * is added to. 
   * 
   * @param relativePath the <i>path expression</i> for a <i>persistent field</i>
   * @return new {@link Order} instance
   */
  public static Order desc(String relativePath) {
    return new Order(relativePath, false);
  }
  
}
