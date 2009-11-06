package org.criteria4jpa.projection;


/**
 * 
 * Class representing a "distinct" projection.
 * It is recommended to use the static factory methods
 * of {@link Projections} to created instances of this class.
 * 
 * @see Projections
 * 
 * @author Christian Kaltepoth
 *
 */
public class DistinctProjection extends FunctionProjection {

  /**
   * creates a new "distinct" projection for a wrapped projection.
   * 
   * @param projection wrapped projection
   */
  public DistinctProjection(Projection projection) {
    super("DISTINCT", projection);
  }
  
}
