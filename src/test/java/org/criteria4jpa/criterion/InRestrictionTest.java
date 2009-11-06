package org.criteria4jpa.criterion;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class InRestrictionTest extends AbstractIntegrationTest {
  
  public InRestrictionTest() {
    super("restriction/InRestrictionTest.xml");
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void testSimpleInRestrictionWithArray() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( 
        Restrictions.in("firstname", new String[] { "Christian", "James" })
    );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 3 );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(1).getFirstname(), "Christian" );
    assertEquals( result.get(2).getFirstname(), "James" );
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void testSimpleInRestrictionWithCollection() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( 
        Restrictions.in("firstname", Arrays.asList( "Christian", "James" ))
    );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 3 );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(1).getFirstname(), "Christian" );
    assertEquals( result.get(2).getFirstname(), "James" );
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void testSimpleInRestrictionArrayWithOneValue() {
    
    // create criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( 
        Restrictions.in("firstname", new String[] { "Will" } )
    );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 1 );
    assertEquals( result.get(0).getFirstname(), "Will" );

  }
  
}
