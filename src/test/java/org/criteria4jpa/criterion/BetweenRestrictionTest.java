package org.criteria4jpa.criterion;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class BetweenRestrictionTest extends AbstractIntegrationTest {
  
  public BetweenRestrictionTest() {
    super("restriction/BetweenRestrictionTest.xml");
  }
  
  @Test
  public void testBetweenRestrictionSingleResult() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.between("age", 40, 60) );
    
    // perform query
    Person result = (Person) criteria.getSingleResult();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.getId(), 3l );
    assertEquals( result.getFirstname(), "Hans" );
    assertEquals( result.getLastname(), "Peterson" );
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testBetweenRestrictionMultipleResults() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.between("age", 20, 40) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getLastname(), "Kaltepoth" );
    assertEquals( result.get(0).getAge(), Integer.valueOf(28) );
    assertEquals( result.get(1).getLastname(), "Mayer" );
    assertEquals( result.get(1).getAge(), Integer.valueOf(30) );
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testBetweenNegatedRestriction() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.notBetween("age", 20, 40) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 1 );
    assertEquals( result.get(0).getId(), 3l );
    assertEquals( result.get(0).getLastname(), "Peterson" );
    assertEquals( result.get(0).getAge(), Integer.valueOf(50) );
  }

  
}
