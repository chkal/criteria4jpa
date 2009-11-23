package org.criteria4jpa.criterion;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class EqualsRestrictionTest extends AbstractIntegrationTest {
  
  public EqualsRestrictionTest() {
    super("restriction/EqualsRestrictionTest.xml");
  }
  
  @Test
  public void testEqualsRestrictionSingleResult() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.eq("firstname", "Christian") );
    criteria.add( Restrictions.eq("lastname", "Kaltepoth") );
    
    // perform query
    Person result = (Person) criteria.getSingleResult();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.getId(), 1l );
    assertEquals( result.getFirstname(), "Christian" );
    assertEquals( result.getLastname(), "Kaltepoth" );
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testEqualsRestrictionMultipleResults() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.eq("firstname", "Christian") );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getLastname(), "Kaltepoth" );
    assertEquals( result.get(1).getLastname(), "Mayer" );
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testAllEqualsRestriction() {
    
    // build map for allEq() test
    Map<String, Object> values = new HashMap<String, Object>();
    values.put("firstname", "Christian");
    values.put("lastname", "Kaltepoth");
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.allEq(values) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 1 );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(0).getLastname(), "Kaltepoth" );
  }
  
}
