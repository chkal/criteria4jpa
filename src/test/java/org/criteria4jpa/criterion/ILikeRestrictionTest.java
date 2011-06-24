package org.criteria4jpa.criterion;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.criterion.MatchMode;
import org.criteria4jpa.criterion.Restrictions;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class ILikeRestrictionTest extends AbstractIntegrationTest {
  
  public ILikeRestrictionTest() {
    super("restriction/ILikeRestrictionTest.xml");
  }
  
  @Test
  public void testSimpleILikeRestriction() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.ilike("firstname", "CHR%") );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(1).getFirstname(), "Christoph" );
  }

  @Test
  public void testILikeRestrictionWithExactMatchMode() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.ilike("firstname", "christIAN", MatchMode.EXACT) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 1 );
    assertEquals( result.get(0).getFirstname(), "Christian" );
  }

  @Test
  public void testILikeRestrictionWithStartMatchMode() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.ilike("firstname", "ch", MatchMode.START) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(1).getFirstname(), "Christoph" );
  }

  @Test
  public void testILikeRestrictionWithEndMatchMode() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.ilike("lastname", "eR", MatchMode.END) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getLastname(), "Mayer" );
    assertEquals( result.get(1).getLastname(), "Mueller" );
  }

  @Test
  public void testILikeRestrictionWithAnywhereMatchMode() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.ilike("lastname", "T", MatchMode.ANYWHERE) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getLastname(), "Kaltepoth" );
    assertEquals( result.get(1).getLastname(), "Peterson" );
  }

}
