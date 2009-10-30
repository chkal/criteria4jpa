package org.criteria4jpa.util;

import java.util.Iterator;
import java.util.List;

/**
 * 
 * Helper class to generate logging information
 * 
 * @author Christian Kaltepoth
 *
 */
public class QueryLogHelper {

  /**
   * Method to create a human readable log message regarding a generated query.
   * 
   * @param query the JPQL query string
   * @param parameterValues the parameter values of the query
   * @return log string
   */
  public static String createQueryLogMessage(String query, List<Object> parameterValues) {
    
    // builder for log message
    StringBuilder builder = new StringBuilder();
    
    // log the JPQL query string
    builder.append("Query: \"");
    builder.append(query);
    builder.append("\"");

    // append parameter value list
    if(parameterValues != null && parameterValues.size() > 0) {
      
      builder.append(", parameters: { ");

      // parameter value iterator
      Iterator<Object> parameterIterator = parameterValues.iterator();
      
      // iterate over all parameter values
      while (parameterIterator.hasNext()) {

        // use private helper to create string representation
        builder.append( objectToString( parameterIterator.next() ) );
        
        // append comma if more values follow
        if( parameterIterator.hasNext() ) {
          builder.append(", ");
        }
        
      }
      
      // closing bracket
      builder.append(" }");
      
    }
    
    // return result
    return builder.toString();
  }

  /**
   * Helper method to create a string representation of an object.
   * 
   * @param obj the object to print
   * @return a string representation of the object
   */
  private static String objectToString(Object obj) {

    // handle 'null'
    if(obj == null) {
      return "null";
    }
    
    // enclose Strings in quotes
    if(obj instanceof CharSequence) {
      return "\""+obj.toString()+"\"";
    }

    // use toString() for all other objects
    return obj.toString();
  }
  
}
