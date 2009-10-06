package org.criteria4jpa.criterion;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.criterion.Restrictions;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class EmptyRestrictionTest extends AbstractIntegrationTest {
  
  public EmptyRestrictionTest() {
    super("restriction/EmptyRestrictionTest.xml");
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void testIsEmptyRestriction() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.isEmpty("addresses") );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 1 );
    assertEquals( result.get(0).getId(), 2l );
    assertEquals( result.get(0).getFirstname(), "Hans" );
    assertEquals( result.get(0).getLastname(), "Peter" );
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testIsNotEmptyRestriction() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.isNotEmpty("addresses") );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getId(), 1l );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(0).getLastname(), "Kaltepoth" );
    assertEquals( result.get(1).getId(), 3l );
    assertEquals( result.get(1).getFirstname(), "Bill" );
    assertEquals( result.get(1).getLastname(), "Gates" );

  }

}
