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

public class NotRestrictionTest extends AbstractIntegrationTest {
  
  public NotRestrictionTest() {
    super("restriction/NotRestrictionTest.xml");
  }
  
  @Test
  public void testNotRestrictionOnEquals() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.not( Restrictions.eq( "firstname", "Christian" )) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 1 );
    assertEquals( result.get(0).getId(), 3l );
    assertEquals( result.get(0).getFirstname(), "Hans" );
    
  }

  @Test
  public void testNotRestrictionOnBetween() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.not( Restrictions.between("age", 20, 30)) );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 1 );
    assertEquals( result.get(0).getId(), 2l );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(0).getLastname(), "Mayer" );
    assertEquals( result.get(0).getAge(), Integer.valueOf(70) );

  }

}
