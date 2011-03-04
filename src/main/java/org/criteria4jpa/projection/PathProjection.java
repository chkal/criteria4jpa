package org.criteria4jpa.projection;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.impl.CriteriaQueryBuilder;

/**
 * 
 * Simple class for all projections that refer to a <i>persistent field</i> 
 * or a <i>single-valued relationship field</i> using a path expression.
 * It is recommended to use the static factory methods
 * of {@link Projections} to created instances of this class.
 * 
 * @see Projections
 * 
 * @author Christian Kaltepoth
 *
 */
public class PathProjection implements Projection {

  private final String path;
  private final boolean absolute;
  private final boolean grouped;

  /**
   * Creates a new instance of a path projection.
   * 
   * @param path relative or absolute path of the field
   * @param absolute <code>true</code> if <i>path</i> is an absolute path,
   *    <code>false</code> if <i>path</i> is relative
   */
  public PathProjection(String path, boolean absolute, boolean grouped) {
    this.path = path;
    this.absolute = absolute;
    this.grouped = grouped;
  }
  
  /*
   * @see org.criteria4jpa.projection.Projection#toQueryString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toQueryString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    if(absolute) {
      return path;
    } else {
      return queryBuilder.getAbsolutePath(criteria, path);
    }
  }

  /*
   * @see org.criteria4jpa.projection.Projection#toGroupByString(org.criteria4jpa.Criteria, org.criteria4jpa.impl.CriteriaQueryBuilder)
   */
  public String toGroupByString(Criteria criteria, CriteriaQueryBuilder queryBuilder) {
    if(grouped) {
      if(absolute) {
        return path;
      } else {
        return queryBuilder.getAbsolutePath(criteria, path);
      }
    }
    return null;
  }

}
