package org.criteria4jpa.criterion;

/**
 * 
 * Class representing a conjunction. Subclass of {@link Junction} using
 * <code>AND</code> as the boolean operator.
 * 
 * @see Disjunction
 * @see Restrictions#conjunction()
 * 
 * @author Christian Kaltepoth
 *
 */
public class Conjunction extends Junction {

  /**
   * constructor to create new conjunction
   * 
   * @see Restrictions#conjunction()
   */
  public Conjunction() {
    super("AND");
  }

}
