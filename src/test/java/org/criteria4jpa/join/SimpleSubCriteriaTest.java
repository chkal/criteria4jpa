package org.criteria4jpa.join;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.criterion.Restrictions;
import org.criteria4jpa.model.Person;
import org.criteria4jpa.projection.Projections;
import org.testng.annotations.Test;

public class SimpleSubCriteriaTest extends AbstractIntegrationTest {
  
  public SimpleSubCriteriaTest() {
    super("join/SimpleSubCriteriaTest.xml");
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void testSimpleJoinOnCollection() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    Criteria subcriteria = criteria.createCriteria("addresses");
    subcriteria.add( Restrictions.eq("city", "Bochum") );
    
    // perform queryn
    List<Person> result = subcriteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 1 );
    assertEquals( result.get(0).getId(), 2l );
    assertEquals( result.get(0).getFirstname(), "Hans" );
    assertEquals( result.get(0).getLastname(), "Peter" );

  }

  
  @Test
  @SuppressWarnings("unchecked")
  public void testSimpleDoubleJoinWithoutDistinct() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    Criteria subcriteria = criteria.createCriteria("addresses").createCriteria("country");
    subcriteria.add( Restrictions.eq("name", "United States") );
    
    // perform queryn
    List<Person> result = subcriteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 3 );
    assertEquals( result.get(0).getId(), 1l );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(0).getLastname(), "Kaltepoth" );
    assertEquals( result.get(1).getId(), 3l );
    assertEquals( result.get(1).getFirstname(), "Bill" );
    assertEquals( result.get(1).getLastname(), "Gates" );
    assertEquals( result.get(2).getId(), 3l );
    assertEquals( result.get(2).getFirstname(), "Bill" );
    assertEquals( result.get(2).getLastname(), "Gates" );

  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSimpleDoubleJoinWithDistinct() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.distinctRootEntity() );
    Criteria subcriteria = criteria.createCriteria("addresses").createCriteria("country");
    subcriteria.add( Restrictions.eq("name", "United States") );
    
    // perform queryn
    List<Person> result = subcriteria.getResultList();
    
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
