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

public class NullRestrictionTest extends AbstractIntegrationTest {
  
  public NullRestrictionTest() {
    super("restriction/NullRestrictionTest.xml");
  }
  
  @Test
  public void testIsNullRestriction() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.isNull("title") );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 1 );
    assertEquals( result.get(0).getId(), 2l );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(0).getLastname(), "Mayer" );
  }

  @Test
  public void testIsNotNullRestriction() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.isNotNull("title") );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getId(), 1l );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(0).getLastname(), "Kaltepoth" );
    assertEquals( result.get(1).getId(), 3l );
    assertEquals( result.get(1).getFirstname(), "Hans" );
    assertEquals( result.get(1).getLastname(), "Peterson" );

  }

}
