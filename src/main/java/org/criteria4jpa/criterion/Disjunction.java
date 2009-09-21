package org.criteria4jpa.criterion;

/**
 * 
 * Class representing a disjunction. Subclass of {@link Junction} using
 * <code>OR</code> as the boolean operator.
 * 
 * @see Conjunction
 * @see Restrictions#disjunction()
 * 
 * @author Christian Kaltepoth
 *
 */
public class Disjunction extends Junction {

  /**
   * constructor to create new conjunction
   * 
   * @see Restrictions#disjunction()
   */
  public Disjunction() {
    super("OR");
  }

}
