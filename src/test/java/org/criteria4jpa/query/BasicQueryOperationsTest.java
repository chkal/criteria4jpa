package org.criteria4jpa.query;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.criterion.Restrictions;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class BasicQueryOperationsTest extends AbstractIntegrationTest {
  
  public BasicQueryOperationsTest() {
    super("query/BasicQueryOperationsTest.xml");
  }
  
  @Test
  public void testGetSingleResultWithValidResult() {
    
    // build and perform criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.eq("firstname", "Hans") );
    Person result = (Person) criteria.getSingleResult();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.getFirstname(), "Hans" );
  }

  @Test(expectedExceptions=NoResultException.class)
  public void testGetSingleResultWithNoResult() {
    
    // build and perform criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.eq("firstname", "DoesNotExist") );
    criteria.getSingleResult();
  }

  @Test(expectedExceptions=NonUniqueResultException.class)
  public void testGetSingleResultWithMultipleResults() {
    
    // build and perform criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.eq("firstname", "Christian") );
    criteria.getSingleResult();
  }
  
  @Test
  public void testGetSingleResultOrNullWithValidResult() {
    
    // build and perform criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.eq("firstname", "Hans") );
    Person result = (Person) criteria.getSingleResultOrNull();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.getFirstname(), "Hans" );
  }

  @Test
  public void testGetSingleResultOrNullWithNoResult() {
    
    // build and perform criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.eq("firstname", "DoesNotExist") );
    Person result = (Person) criteria.getSingleResultOrNull();
    
    // assert correct result
    assertNull(result);
  }

  @Test(expectedExceptions=NonUniqueResultException.class)
  public void testGetSingleResultOrNullWithMultipleResults() {
    
    // build and perform criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.eq("firstname", "Christian") );
    criteria.getSingleResultOrNull();
  }
  
}
