package org.criteria4jpa.projection;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;


/**
 * 
 * <p>
 * Factory class for {@link Projection} instances that can be added 
 * to a criteria by calling {@link Criteria#setProjection(Projection)}.
 * </p>
 * 
 * @author Christian Kaltepoth
 *
 */
public class Projections {

  /**
   * Creates a "distinct" projection wrapping another projection.
   * 
   * @param projection the projection to wrap with a "distinct"
   * @return a new projection instance
   */
  public static Projection distinct(Projection projection) {
    return new DistinctProjection(projection);
  }

  /**
   * Creates a <i>projection list</i> projection that wraps a list
   * if other projections. You may add projections to the list by
   * calling {@link ProjectionList#add(Projection)} on the 
   * returned projection. If you use a projection list for your
   * criteria, that query will return an object array.
   *  
   * @return new instance of {@link ProjectionList}
   */
  public static ProjectionList projectionList() {
    return new ProjectionList();
  }

  /**
   * This method only exists for compatibility with the 
   * <i>Hibernate Criteria API</i> and simply delegates the call
   * to {@link #relativePath(String)}.
   * 
   * @param relativePath relative path expression of a field
   * @return new projection instance
   */
  public static Projection property(String relativePath) {
    return relativePath(relativePath);
  }
  
  /**
   * <p>
   * This projection can be used if you want the criteria to
   * return a certain <i>persistent field</i> or a
   * <i>single-valued relationship field</i>. 
   * </p>
   * 
   * <p>
   * Please note that the path expression must be relative to 
   * the criteria this projection is added to.
   * </p>
   * 
   * @param path relative path expression
   * @return new projection instance
   */
  public static Projection relativePath(String path) {
    return new PathProjection(path, false);
  }

  
  /**
   * <p>
   * This projection can be used if you want the criteria to
   * return a certain <i>persistent field</i> or a
   * <i>single-valued relationship field</i>. 
   * </p>
   * 
   * <p>
   * Please note that the path expression must be absolute 
   * meaning that it has to start with an alias created.
   * </p>
   * 
   * @see CriteriaUtils#createCriteria(javax.persistence.EntityManager, Class, String)
   * @see Criteria#createCriteria(String, String)
   * 
   * @param path absolute path expression
   * @return new projection instance
   */
  public static Projection absolutePath(String path) {
    return new PathProjection(path, false);
  }

  
  /**
   * Creates a projection referring to the root entity of the
   * criteria hierarchy it is added to.
   * 
   * @return new projection instance
   */
  public static Projection rootEntity() {
    return new RootEntityProjection();
  }
  
  /**
   * <p>
   * Returns a projection referring to the distinct root entities of
   * the criteria hierarchy it is added to.
   * </p>
   * 
   * <p>
   * You can get the same result by calling a combination of
   * {@link #distinct(Projection)} and {@link #rootEntity()}.
   * </p>
   * 
   * @return new projection instance
   */
  public static Projection distinctRootEntity() {
    return new DistinctProjection( new RootEntityProjection() );
  }

  /**
   * A projection that returns the smallest value of a
   * persistent field. The result of the query will have the
   * same type as the specified field.
   * 
   * @param relativePath relative path of a persistent field
   * @return smallest value
   */
  public static Projection min(String relativePath) {
    return new AggregateFunctionProjection("MIN", relativePath);
  }
  
  /**
   * A projection that returns the largest value of a
   * persistent field. The result of the query will have the
   * same type as the specified field.
   * 
   * @param relativePath relative path of a persistent field
   * @return largest value
   */
  public static Projection max(String relativePath) {
    return new AggregateFunctionProjection("MAX", relativePath);
  }
  
  /**
   * A projection that returns the sum of all values.
   * The result of the query will always be a {@link Long}.
   * 
   * @param relativePath relative path of a persistent field
   * @return sum of values
   */
  public static Projection sum(String relativePath) {
    return new AggregateFunctionProjection("SUM", relativePath);
  }
  
  /**
   * A projection that returns the average of all values.
   * The result of the query will always be a {@link Double}.
   * 
   * @param relativePath relative path of a persistent field
   * @return average of values
   */
  public static Projection avg(String relativePath) {
    return new AggregateFunctionProjection("AVG", relativePath);
  }


}
