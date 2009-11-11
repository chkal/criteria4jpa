package org.criteria4jpa;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.criteria4jpa.criterion.Criterion;
import org.criteria4jpa.impl.CriteriaImpl;
import org.criteria4jpa.order.Order;
import org.criteria4jpa.projection.Projection;

/**
 * 
 * Utility class to create new {@link Criteria} instances.
 * 
 * @author Christian Kaltepoth
 *
 */
public class CriteriaUtils {

  /**
   * <p>
   * Creates a new {@link Criteria} query for a entity class.
   * </p>
   * 
   * <p>
   * Please note that the entity name is deduced from the unqualified class name.
   * You should use {@link #createCriteria(EntityManager, String)}
   * if you have changed the entity name by using the <code>name</code> attribute 
   * of the {@link Entity} annotation.
   * </p>
   * 
   * @param entityManager {@link EntityManager} for query creation
   * @param persistentClass persistent class
   * @return new {@link Criteria} query
   */
  public static Criteria createCriteria(EntityManager entityManager, 
      Class<?> persistentClass) {
    errorIfClosed(entityManager);
    return new CriteriaImpl( entityManager, persistentClass.getSimpleName() );
  }

  /**
   * <p>
   * Creates a new {@link Criteria} query for a named entity.
   * </p>
   * 
   * @param entityManager {@link EntityManager} for query creation
   * @param entityName name of the entity
   * @return new {@link Criteria} query
   */
  public static Criteria createCriteria(EntityManager entityManager, 
      String entityName) {
    errorIfClosed(entityManager);
    return new CriteriaImpl( entityManager, entityName );
  }

  /**
   * <p>
   * Creates a new {@link Criteria} query for a entity class. Sets the
   * specified alias for the root entity. You may later use this alias to
   * refer to the  entity in {@link Criterion}, {@link Projection} and
   * {@link Order} instances.
   * </p>
   * 
   * <p>
   * Please note that the entity name is deduced from the unqualified class name.
   * You should use {@link #createCriteria(EntityManager, Class, String)}
   * if you have changed the entity name by using the <code>name</code> attribute 
   * of the {@link Entity} annotation.
   * </p>
   * 
   * @param entityManager {@link EntityManager} for query creation
   * @param persistentClass persistent class
   * @param alias alias to set for the criteria
   * @return new {@link Criteria} query
   */
  public static Criteria createCriteria(EntityManager entityManager, 
      Class<?> persistentClass, String alias) {
    return new CriteriaImpl( entityManager, persistentClass.getSimpleName(), alias);
  }
  
  /**
   * <p>
   * Creates a new {@link Criteria} query for a named entity. Sets the
   * specified alias for the root entity. You may later use this alias to
   * refer to the  entity in {@link Criterion}, {@link Projection} and
   * {@link Order} instances.
   * </p>
   * 
   * @param entityManager {@link EntityManager} for query creation
   * @param entityName name of the entity
   * @return new {@link Criteria} query
   */
  public static Criteria createCriteria(EntityManager entityManager, 
      String entityName, String alias) {
    errorIfClosed(entityManager);
    return new CriteriaImpl( entityManager, entityName, alias);
  }
  
  /**
   * <p>
   * Check if the specified {@link EntityManager} is still open.
   * </p>
   */
  private static void errorIfClosed(EntityManager entityManager) {
    if(!entityManager.isOpen()) {
      throw new IllegalArgumentException("EntityManager not open!");
    }
  }
}
