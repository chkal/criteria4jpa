package org.criteria4jpa.projection;

/**
 * 
 * Class representing a "count" projection.
 * It is recommended to use the static factory methods
 * of {@link Projections} to created instances of this class.
 * 
 * @see Projections#count(String)
 * @see Projections#countDistinct(String)
 * @see Projections#rowCount()
 * 
 * @author Christian Kaltepoth
 *
 */
public class CountProjection extends FunctionProjection {

  /**
   * Creates a new "count" projection for a wrapped projection.
   * 
   * @param projection wrapped projection
   */
  public CountProjection(Projection projection) {
    super("COUNT", projection);
  }

}
