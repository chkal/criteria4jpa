package org.criteria4jpa;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.criteria4jpa.criterion.Criterion;
import org.criteria4jpa.criterion.Restrictions;
import org.criteria4jpa.order.Order;
import org.criteria4jpa.projection.Projection;
import org.criteria4jpa.projection.Projections;

/**
 * 
 * <p>
 * This class represents a single criteria query.
 * </p>
 * 
 * <p>
 * Use {@link #add(Criterion)} to specify constraints on the query results.
 * The static factory methods of {@link Restrictions} should be used to
 * create new {@link Criterion} instances. 
 * </p>
 * 
 * <p>
 * You can use {@link #createCriteria(String)} or 
 * {@link #createCriteria(String, String)} to build subcriteria for associated
 * entities. These subcriteria are {@link Criteria} instances rooted at a
 * different entity and can be used like the root criteria instance.
 * </p>
 * 
 * <p>
 * If you need to specify a specific ordering of your result, you may define
 * ordering with {@link #addOrder(Order)}. The class {@link Order} contains
 * static factory methods for {@link Order} objects.
 * </p>
 * 
 * <p>
 * After building a {@link Criteria} instance you can use {@link #getResultList()}
 * or {@link #getSingleResult()} to execute the query.
 * </p>
 * 
 * @author Christian Kaltepoth
 *
 */
public interface Criteria {
  
  /**
   * Specifies a JOIN type. 
   */
  public enum JoinType {
    
    /**
     * Performs an INNER JOIN
     */
    INNER_JOIN,
    
    /**
     * Performs a LEFT OUTER JOIN
     */
    LEFT_OUTER_JOIN
    
  }

  /**
   * The fetch mode for use with {@link Criteria#setFetchMode(String, FetchMode)}
   */
  public enum FetchMode {

    /**
     * The default fetch mode.
     */
    DEFAULT,
    
    /**
     * Do an INNER JOIN FETCH
     */
    INNER_FETCH_JOIN,
    
    /**
     * Do a LEFT OUTER FETCH JOIN
     */
    LEFT_OUTER_FETCH_JOIN
    
  }

  /**
   * Returns the alias of the entity referred to by this criteria instance.
   *
   * @return The alias for the entity.
   */
  public String getAlias();

  /**
   * Add a {@link Criterion} to specify constraints on the results
   * that the query should return. Use the static factory methods
   * of the {@link Restrictions} class to create new {@link Criterion}
   * objects.
   *
   * @param criterion The {@link Criterion} object representing the
   * constraints for the criteria query.
   * @return this (builder pattern)
   */
  public Criteria add(Criterion criterion);
  
  /**
   * Add an {@link Order} object to specify the ordering of the
   * results. Use the static factory methods of {@link Order} to
   * create the required instance.
   *
   * @param order The {@link Order} object describing the ordering
   * of the results.
   * @return this (builder pattern)
   */
  public Criteria addOrder(Order order);
  
  /**
   * Set a limit on the maximal number of results to be retrieved.
   *
   * @param maxResults the maximum number of results
   * @return this (builder pattern)
   */
  public Criteria setMaxResults(int maxResults);
  
  /**
   * Set the first result to be retrieved.
   *
   * @param firstResult the first result to retrieve
   * @return this (builder pattern)
   */
  public Criteria setFirstResult(int firstResult);

  
  /**
   * Generates a {@link Query} from the {@link Criteria} instances
   * and calls {@link Query#getResultList()}.
   *
   * @param <E> The expected return type
   * @return The list of matched query results.
   */
  public <E> List<E> getResultList();

  
  /**
   * <p>
   * Generates a {@link Query} from the criteria instances
   * and calls {@link Query#getSingleResult()} on it.
   * </p>
   *
   * @param <E> The expected return type
   * @return result of {@link Query#getSingleResult()}
   */
  public <E> E getSingleResult();

  /**
   * <p>
   * Generates a {@link Query} from the criteria instances
   * and calls {@link Query#getSingleResult()} on it.
   * </p>
   * 
   * <p>
   * If the underlying call to {@link Query#getSingleResult()} results
   * in a {@link NoResultException} it will be automatically catched
   * and <code>null</code> will be returned.
   * </p>
   * 
   * <p>
   * Use {@link #getSingleResult()} if you want standard JPA behavior.
   * </p>
   *
   * @param <E> The expected return type
   * @return result of {@link Query#getSingleResult()} or <code>null</code>
   */
  public <E> E getSingleResultOrNull();
  
  /**
   * Creates a subcriteria on the associated entity named by
   * the supplied <i>path expression</i>. Creates a JPA query
   * containing an equivalent join expression. If you want
   * to assign an alias to the joined entity, use 
   * {@link #createCriteria(String, String)} instead. 
   * This method will perform a {@link JoinType#INNER_JOIN}.
   *
   * @param relativePath The path expression to the associated entity
   * @return the created subcriteria
   */
  public Criteria createCriteria(String relativePath);
  
  /**
   * Creates a subcriteria on the associated entity named by
   * the supplied <i>path expression</i>. Creates a JPA query
   * containing an equivalent join expression. If you want
   * to assign an alias to the joined entity, use 
   * {@link #createCriteria(String, String)} instead.
   *
   * @param relativePath The path expression to the associated entity
   * @param joinType The type of join to perform
   * @return the created subcriteria
   */
  public Criteria createCriteria(String relativePath, JoinType joinType);

  /**
   * Creates a subcriteria on the associated entity named by
   * the supplied <i>path expression</i>. Creates a JPA query
   * containing an equivalent join expression. Assigns the
   * given alias to the joined entity. If you don't need to
   * manually set an alias, use may use {@link #createCriteria(String)}
   * instead. This method will perform a {@link JoinType#INNER_JOIN}.
   *
   * @param relativePath The path expression to the associated entity
   * @param alias The alias assigned to the joined entity
   * @return the created subcriteria
   */
  public Criteria createCriteria(String relativePath, String alias);

  /**
   * Creates a subcriteria on the associated entity named by
   * the supplied <i>path expression</i>. Creates a JPA query
   * containing an equivalent join expression. Assigns the
   * given alias to the joined entity. If you don't need to
   * manually set an alias, use may use {@link #createCriteria(String)}
   * instead.
   *
   * @param relativePath The path expression to the associated entity
   * @param alias The alias assigned to the joined entity
   * @param joinType The type of join to perform
   * @return the created subcriteria
   */
  public Criteria createCriteria(String relativePath, String alias, JoinType joinType);
  
  /**
   * Set a {@link Projection} for the query. Using a {@link Projection}
   * will effectively create a custom <i>SELECT</i> and may be used
   * to retrieve different objects than the root entity. Use
   * the static factory methods of {@link Projections} to create
   * individual {@link Projection} instances.
   *
   * @param projection The {@link Projection} describing the desired
   * result of the query
   * @return this (builder pattern)
   */
  public Criteria setProjection(Projection projection);

  /**
   * Set the fetch mode for the given associated entity named by the supplied
   * <i>path expression</i>. This method is typically used to do fetch joins on
   * associated entities.
   * 
   * @param relativePath
   *          The path expression to the associated entity or collection
   * @param mode
   *          The fetch mode to use
   * @return this (builder pattern)
   */
  public Criteria setFetchMode(String relativePath, FetchMode mode);

}
