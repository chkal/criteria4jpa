package org.criteria4jpa.criterion;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class JPQLRestrictionTest extends AbstractIntegrationTest {
  
  public JPQLRestrictionTest() {
    super("restriction/JPQLRestrictionTest.xml");
  }
  
  @Test
  public void testJPQLRestrictionWithoutParameter() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.jpqlRestriction( "{alias}.age > 40" ) );
    
    // perform query
    Person result = (Person) criteria.getSingleResult();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.getId(), 2l );
    assertEquals( result.getFirstname(), "Hans" );
    assertEquals( result.getLastname(), "Peterson" );
  }
  
  @Test
  public void testJPQLRestrictionWithOneParameter() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.jpqlRestriction( "{alias}.age < {1}", 40 ) );
    
    // perform query
    Person result = (Person) criteria.getSingleResult();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.getId(), 1l );
    assertEquals( result.getFirstname(), "Christian" );
    assertEquals( result.getLastname(), "Kaltepoth" );
  }

  @Test
  public void testJPQLRestrictionWithMultipleParameters() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.jpqlRestriction( 
        "{alias}.age BETWEEN {2} AND {1} AND {alias}.firstname LIKE {3}", 
        80, 40, "Ha%") );
    
    // perform query
    Person result = (Person) criteria.getSingleResult();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.getId(), 2l );
    assertEquals( result.getFirstname(), "Hans" );
    assertEquals( result.getLastname(), "Peterson" );
    assertEquals( result.getAge(), Integer.valueOf(54) );
    
  }
  
  @Test
  public void testJPQLRestrictionReuseParameters() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.jpqlRestriction( 
        "{alias}.firstname LIKE {1} OR {alias}.lastname LIKE {1}", "Peter%") );
    
    // perform query
    Person result = (Person) criteria.getSingleResult();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.getId(), 2l );
    assertEquals( result.getFirstname(), "Hans" );
    assertEquals( result.getLastname(), "Peterson" );
    
  }
  
}
