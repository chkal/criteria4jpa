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

public class LikeRestrictionTest extends AbstractIntegrationTest {
  
  public LikeRestrictionTest() {
    super("restriction/LikeRestrictionTest.xml");
  }
  
  @Test
  public void testSimpleLikeRestriction() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.like("firstname", "Chr%") );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(1).getFirstname(), "Christoph" );
  }

  @Test
  public void testLikeRestrictionWithExactMatchMode() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.like("firstname", "Christian", MatchMode.EXACT) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 1 );
    assertEquals( result.get(0).getFirstname(), "Christian" );
  }

  @Test
  public void testLikeRestrictionWithStartMatchMode() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.like("firstname", "Ch", MatchMode.START) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(1).getFirstname(), "Christoph" );
  }

  @Test
  public void testLikeRestrictionWithEndMatchMode() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.like("lastname", "er", MatchMode.END) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getLastname(), "Mayer" );
    assertEquals( result.get(1).getLastname(), "Mueller" );
  }

  @Test
  public void testLikeRestrictionWithAnywhereMatchMode() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.like("lastname", "t", MatchMode.ANYWHERE) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getLastname(), "Kaltepoth" );
    assertEquals( result.get(1).getLastname(), "Peterson" );
  }

}
