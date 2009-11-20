package org.criteria4jpa.projection;

import java.math.BigDecimal;
import java.math.BigInteger;

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
    return new FunctionProjection("MIN", new PathProjection(relativePath, false));
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
    return new FunctionProjection("MAX", new PathProjection(relativePath, false));
  }
  
  /**
   * A projection that returns the sum of all values.
   * The result of the query will be a {@link Long} (for integral fields), 
   * {@link Double} (for floating point fields), 
   * {@link BigInteger} (for BigInteger fields) or 
   * {@link BigDecimal} (for BigDecimal fields)
   * 
   * @param relativePath relative path of a persistent field
   * @return sum of values
   */
  public static Projection sum(String relativePath) {
    return new FunctionProjection("SUM", new PathProjection(relativePath, false));
  }
  
  /**
   * A projection that returns the average of all values.
   * The result of the query will always be a {@link Double}.
   * 
   * @param relativePath relative path of a persistent field
   * @return average of values
   */
  public static Projection avg(String relativePath) {
    return new FunctionProjection("AVG", new PathProjection(relativePath, false));
  }
  
  /**
   * Creates a count projection for the specified relative path.
   * In most cases you can may use {@link #count(String)}
   * or {@link #countDistinct(String)} instead of this method.
   * The result of the query will always be a {@link Long}.
   * 
   * @param projection nested projection
   * @return new projection instance
   */
  public static CountProjection count(Projection projection) {
    return new CountProjection(projection);
  }

  /**
   * <p>
   * Creates a count projection for the specified relative path.
   * </p>
   * 
   * <p>
   * Calling the method is the same as manually chaining calls to {@link #count(Projection)} 
   * and {@link #relativePath(String)}.
   * </p> 
   * 
   * <p>
   * The result of the query will always be a {@link Long}.
   * </p>
   * 
   * @param relativePath relative path
   * @return new projection instance
   */
  public static CountProjection count(String relativePath) {
    return new CountProjection(
        new PathProjection(relativePath, false)
    );
  }
  
  /**
   * <p>
   * Creates a "count distinct" projection for the specified relative path.
   * </p>
   * 
   * <p>
   * Calling the method is the same as manually chaining calls to {@link #count(Projection)}, 
   * {@link #distinct(Projection)} and {@link #relativePath(String)}.
   * </p> 
   * 
   * <p>
   * The result of the query will always be a {@link Long}.
   * </p>
   * 
   * @param relativePath relative path
   * @return new projection instance
   */
  public static CountProjection countDistinct(String relativePath) {
    return new CountProjection(
        new DistinctProjection(
            new PathProjection(relativePath, false)
        )
    );
  }

  /**
   * <p>
   * Creates a count projection for the root entity of the criteria.
   * </p>
   * 
   * <p>
   * You will get the number of root entities matching the criteria as a result.
   * Please note that you should use {@link #rowCountDistinct()} to get the 
   * expected result the criteria creates joins on collection-valued relationship fields.
   * </p>
   * 
   * <p>
   * Calling the method is the same as manually chaining calls to {@link #count(Projection)} 
   * and {@link #rootEntity()}.
   * </p> 
   * 
   * <p>
   * The result of the query will always be a {@link Long}.
   * </p>
   * 
   * @return new projection instance
   */
  public static Projection rowCount() {
    return new CountProjection(
        new RootEntityProjection()
    );
  }

  /**
   * <p>
   * Creates a "count distinct" projection for the root entity of the criteria.
   * </p>
   * 
   * <p>
   * You will get the number of root entities matching the criteria as a result.
   * It is recommended to use this method instead of {@link #rowCount()}, 
   * when the criteria creates joins on collection-valued relationship fields.
   * </p>
   * 
   * <p>
   * Calling the method is the same as manually chaining calls to {@link #count(Projection)},
   * {@link #distinct(Projection)} and {@link #rootEntity()}.
   * </p> 
   * 
   * <p>
   * The result of the query will always be a {@link Long}.
   * </p>
   * 
   * @return new projection instance
   */
  public static Projection rowCountDistinct() {
    return new CountProjection(
        new DistinctProjection(
            new RootEntityProjection()
        )
    );
  }
  
}
