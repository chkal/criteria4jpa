package org.criteria4jpa.criterion;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.criteria4jpa.Criteria;

/**
 * 
 * <p>
 * Factory class for {@link Criterion} instances that can be added 
 * to a criteria by calling {@link Criteria#add(Criterion)}.
 * </p>
 * 
 * <p>
 * Note that most <i>path expressions</i> are relative, meaning that they
 * are rooted at the entity of the criteria they are added to. In this
 * cases there is no need to add any kind of alias to the 
 * <i>path expression</i> because this is already done by the framework.
 * </p> 
 * 
 * @author Christian Kaltepoth
 *
 */
public class Restrictions {
  
  /**
   * Private constructor. No instance creation possible.
   */
  private Restrictions() {
  }

  /**
   * Adds an "equal" constraint to a <i>persistent field</i> or a
   * <i>single-valued relationship field</i>.
   * 
   * @param relativePath relative path of the field
   * @param value The value to check equality against.
   * @return {@link Criterion} instance
   */
  public static SimpleExpression eq(String relativePath, Object value) {
    return new SimpleExpression(relativePath, value, "=");
  }
  
  /**
   * Adds a "not equal" constraint to a <i>persistent field</i> or a
   * <i>single-valued relationship field</i>.
   * 
   * @param relativePath relative path of the field
   * @param value The value to check equality against.
   * @return {@link Criterion} instance
   */
  public static SimpleExpression ne(String relativePath, Object value) {
    return new SimpleExpression(relativePath, value, "<>");
  }

  /**
   * Adds a "like" constraint to a <i>persistent field</i>. This method
   * expects the value to already contain the required wildcard characters.
   * Alternatively you can call {@link #like(String, String, MatchMode)}
   * to let the restriction add the wildcards.
   * 
   * @param relativePath relative path of the field
   * @param value the match string
   * @return {@link Criterion} instance
   */
  public static SimpleExpression like(String relativePath, String value) {
    return new SimpleExpression(relativePath, value, "LIKE");
  }
  
  /**
   * Adds a "like" constraint to a <i>persistent field</i>. This method
   * requires a {@link MatchMode} to specify how wildcards are added
   * to the value. You may also use {@link #like(String, String)} if you
   * want to manually specify the wildcards in the match string.
   * 
   * @param relativePath relative path of the field
   * @param value the match string
   * @param matchMode specifies how to add wildcards to the value to create
   *   the LIKE expression
   * @return {@link Criterion} instance
   */
  public static SimpleExpression like(String relativePath, String value, MatchMode matchMode) {
    return new SimpleExpression(relativePath, matchMode.toMatchString(value), "LIKE" );
  }
  
  /**
   * Adds a "greater than" constraint to a <i>persistent field</i>.
   * 
   * @param relativePath relative path of the field
   * @param value The value to check against.
   * @return {@link Criterion} instance
   */
  public static SimpleExpression gt(String relativePath, Object value) {
    return new SimpleExpression(relativePath, value, ">");
  }
  
  /**
   * Adds a "less than" constraint to a <i>persistent field</i>.
   * 
   * @param relativePath relative path of the field
   * @param value The value to check against.
   * @return {@link Criterion} instance
   */
  public static SimpleExpression lt(String relativePath, Object value) {
    return new SimpleExpression(relativePath, value, "<");
  }
  
  /**
   * Adds a "less than or equal" constraint to a <i>persistent field</i>.
   * 
   * @param relativePath relative path of the field
   * @param value The value to check against.
   * @return {@link Criterion} instance
   */
  public static SimpleExpression le(String relativePath, Object value) {
    return new SimpleExpression(relativePath, value, "<=");
  }
  
  /**
   * Adds a "greater than or equal" constraint to a <i>persistent field</i>.
   * 
   * @param relativePath relative path of the field
   * @param value The value to check against.
   * @return {@link Criterion} instance
   */
  public static SimpleExpression ge(String relativePath, Object value) {
    return new SimpleExpression(relativePath, value, ">=");
  }

  /**
   * Adds a "between" constraint to a <i>persistent field</i>.
   * 
   * @param relativePath relative path of the field
   * @param lo the low end of the "between" expression
   * @param hi the high end of the "between" expression
   * @return {@link Criterion} instance
   */
  public static Criterion between(String relativePath, Object lo, Object hi) {
    return new BetweenExpression(relativePath, lo, hi, false);
  }
  
  /**
   * Adds a "not between" constraint to a <i>persistent field</i>.
   * 
   * @param relativePath relative path of the field
   * @param lo the low end of the "not between" expression
   * @param hi the high end of the "not between" expression
   * @return {@link Criterion} instance
   */
  public static Criterion notBetween(String relativePath, Object lo, Object hi) {
    return new BetweenExpression(relativePath, lo, hi, true);
  }
  
  /**
   * Adds a "is null" constraint to a <i>persistent field</i> or a
   * <i>single-valued relationship field</i>.
   * 
   * @param relativePath relative path of the field
   * @return {@link Criterion} instance
   */
  public static Criterion isNull(String relativePath) {
    return new IsNullExpression(relativePath, false);
  }

  /**
   * Adds a "is not null" constraint to a <i>persistent field</i> or a
   * <i>single-valued relationship field</i>.
   * 
   * @param relativePath relative path of the field
   * @return {@link Criterion} instance
   */
  public static Criterion isNotNull(String relativePath) {
    return new IsNullExpression(relativePath, true);
  }
  
  /**
   * Creates a group for multiple restrictions that are connected by a
   * conjunction. The group generates multiple expressions joined
   * by a logical AND. You can add restrictions to the group by calling
   * {@link Conjunction#add(Criterion)} on the returned object.
   * As the {@link Conjunction} itself is a restriction, it can be added
   * to a criteria instance via {@link Criteria#add(Criterion)}.
   * 
   * @return {@link Conjunction} instance
   */
  public static Conjunction conjunction() {
    return new Conjunction();
  }

  /**
   * Creates a group for multiple restrictions that are connected by a
   * disjunction. The group generates multiple expressions joined
   * by a logical OR. You can add restrictions to the group by calling
   * {@link Disjunction#add(Criterion)} on the returned object.
   * As the {@link Disjunction} itself is a restriction, it can be added
   * to a criteria instance via {@link Criteria#add(Criterion)}.
   * 
   * @return {@link Disjunction} instance
   */
  public static Disjunction disjunction() {
    return new Disjunction();
  }

  /**
   * Adds a "is empty" constraint to a <i>collection-valued relationship field</i>.
   * 
   * @param relativePath relative path of the collection
   * @return {@link Criterion} instance
   */
  public static Criterion isEmpty(String relativePath) {
    return new IsEmptyExpression(relativePath, false);
  }

  /**
   * Adds a "is not empty" constraint to a <i>collection-valued relationship field</i>.
   * 
   * @param relativePath relative path of the collection
   * @return {@link Criterion} instance
   */
  public static Criterion isNotEmpty(String relativePath) {
    return new IsEmptyExpression(relativePath, true);
  }
  
  /**
   * Creates a "not" restriction. Creates a negated version of the
   * supplied restriction.
   * 
   * @param criterion Restriction to negate
   * @return {@link Criterion} instance
   */
  public static Criterion not(Criterion criterion) {
    return new NotExpression(criterion);
  }
  
  /**
   * Connects multiple restrictions with an logical conjunction.
   * Calling this method is a shortcut for creating a {@link Conjunction}
   * by calling {@link #conjunction()} and adding all restrictions
   * to it.
   * 
   * @param criterionList The restrictions to add to the conjunction. 
   * @return {@link Conjunction} instance
   */
  public static Conjunction and(Criterion... criterionList) {
    Conjunction junction = new Conjunction();
    for( Criterion criterion : criterionList ) {
      junction.add( criterion );
    }
    return junction;
  }
  
  /**
   * Connects multiple restrictions with an logical disjunction.
   * Calling this method is a shortcut for creating a {@link Disjunction}
   * by calling {@link #disjunction()} and adding all restrictions
   * to it.
   * 
   * @param criterionList The restrictions to add to the disjunction. 
   * @return {@link Disjunction} instance
   */
  public static Disjunction or(Criterion... criterionList) {
    Disjunction junction = new Disjunction();
    for( Criterion criterion : criterionList ) {
      junction.add( criterion );
    }
    return junction;
  }
  
  /**
   * Adds an "in" restriction to a persistent field.
   * 
   * @param relativePath relative path of the persistent field
   * @param values expected values of the field
   * @return {@link Criterion} instance
   */
  public static Criterion in(String relativePath, Object[] values) {
    return new InExpression(relativePath, values);
  }
  
  /**
   * Adds an "in" restriction to a persistent field.
   * 
   * @param relativePath relative path of the persistent field
   * @param values expected values of the field
   * @return {@link Criterion} instance
   */
  public static Criterion in(String relativePath, Collection<?> values) {
    return new InExpression(relativePath, values.toArray());
  }

  /**
   * <p>
   * Creates a conjunction of "equals" restrictions for each 
   * entry in the map.
   * </p>
   * 
   * <p>
   * Same as manually creating a conjunction via {@link #conjunction()}
   * and adding an equals restriction via {@link #eq(String, Object)} for
   * each map entry.
   * </p>
   * 
   * @param values Map containing property name to value mappings
   * @return {@link Conjunction} of "equal" constraints
   */
  public static Criterion allEq(Map<String,Object> values) {
    Conjunction conjunction = new Conjunction();
    for( Entry<String, Object> entry : values.entrySet() ) {
      conjunction.add( eq(entry.getKey(), entry.getValue()) );
    }
    return conjunction;
  }

}
