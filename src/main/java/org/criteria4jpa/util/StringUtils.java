package org.criteria4jpa.util;

import java.util.Locale;

/**
 * 
 * Small helper class
 * 
 * @author Christian Kaltepoth
 *
 */
public class StringUtils {

  /**
   * Method to create unique aliases for JPA identifiers. Each alias
   * consists of two parts. The <i>basename</i> should be a human
   * readable string that helps to identify which entity is referred. 
   * The <i>counter</i> is unique number that is added to the
   * alias to make the alias unique. It is recommended to use each
   * counter value one once independently from the basename used.
   * 
   * @param basename a base name that will be used for alias creation
   * @param counter A unique number
   * @param fallback An optional fallback name
   * @return the generated JPQL alias
   */
  public static String generateAlias(String basename, int counter, String fallback) {
    
    // build trimmed and lower case version of the base name
    String lowerCaseBaseName = basename.trim().toLowerCase(Locale.ENGLISH);

    // use only simple characters for the alias
    StringBuilder builder = new StringBuilder();
    for( char c : lowerCaseBaseName.toCharArray() ) {
      if( c >= 'a' && c <= 'z' ) {
        builder.append( c ); 
      }
    }
    
    // add some default keyword, if builder is still empty
    if( builder.length() == 0 ) {
      if(fallback != null && fallback.length() > 0) {
        builder.append( fallback );
      } else {
        builder.append( "default" );
      }
    }
    
    // finally append the counter to get uniqueness
    builder.append( counter );
    
    // return the result
    return builder.toString();
  }

}
