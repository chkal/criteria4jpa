package org.criteria4jpa.criterion;

/**
 * 
 * Enum used for to specify a specific <i>matching mode</i> for LIKE expressions 
 * created with {@link Restrictions#like(String, String, MatchMode)}
 * 
 * @see Restrictions#like(String, String, MatchMode)
 * 
 * @author Christian Kaltepoth
 *
 */
public enum MatchMode {

  /**
   * Match the complete value against the match pattern.
   */
  EXACT(true, true), 
  
  /**
   * Match the end of the value against the match pattern. 
   * In this match mode the pattern <code>ian</code> would match 
   * the value <code>Christian</code>.</p>
   */
  END(false, true), 

  /**
   * Match the start of the value against the match pattern. 
   * In this match mode the pattern <code>Chris</code> would match 
   * the value <code>Christian</code>.</p>
   */
  START(true, false), 
  
  /**
   * Match the pattern anywhere in the value. 
   * In this match mode the pattern <code>ist</code> would match 
   * the value <code>Christian</code>.</p>
   */
  ANYWHERE(false, false);
  
  private final boolean mustMatchStart;
  private final boolean mustMatchEnd;

  /**
   * private constructor to setup enum
   */
  private MatchMode(final boolean mustMatchStart, final boolean mustMatchEnd) {
    this.mustMatchStart = mustMatchStart;
    this.mustMatchEnd = mustMatchEnd;
  }

  /**
   * creates a match pattern from the specified string
   * 
   * @param value the string to create the match pattern from
   * @return the match pattern
   */
  public String toMatchString(String value) {
    StringBuilder builder = new StringBuilder();
    if( !mustMatchStart ) {
      builder.append('%');
    }
    builder.append( value );
    if( !mustMatchEnd ) {
      builder.append('%');
    }
    return builder.toString();
  }
}
