package org.criteria4jpa.criterion;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.model.Address;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class MemberOfRestrictionTest extends AbstractIntegrationTest {
  
  public MemberOfRestrictionTest() {
    super("restriction/MemberOfRestrictionTest.xml");
  }
  
  @Test
  public void testMemberOfRestrictionWithMatch() {
    
    // load a address that we will search for
    Address address = entityManager.find(Address.class, 104l);
    assertNotNull(address);
    assertEquals(address.getCity(), "Washington");
    
    // search for person with this address
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.memberOf("addresses", address));
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 1 );
    assertEquals( result.get(0).getFirstname(), "Bill" );
    assertEquals( result.get(0).getLastname(), "Gates" );
    
  }

  @Test
  public void testMemberOfRestrictionWithoutMatch() {
    
    // load a address that we will search for
    Address address = entityManager.find(Address.class, 106l);
    assertNotNull(address);
    assertEquals(address.getCity(), "Munich");
    
    // search for person with this address
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.memberOf("addresses", address));
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 0 );
    
  }
  
  @Test
  public void testNegatedMemberOfRestriction() {
    
    // load a address that we will search for
    Address address = entityManager.find(Address.class, 104l);
    assertNotNull(address);
    assertEquals(address.getCity(), "Washington");
    
    // search for person with this address
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.notMemberOf("addresses", address));
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 3 );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(1).getFirstname(), "Hans" );
    assertEquals( result.get(2).getFirstname(), "Linus" );
    
  }
  
  @Test
  public void testMemberOfRestrictionNegatedTwice() {
    
    // FIXME: skip this test for OpenJPA because of OpenJPA bug 
    if( entityManager.getDelegate().getClass().getName().contains("openjpa") ) {
      return;
    }
    
    // load a address that we will search for
    Address address = entityManager.find(Address.class, 104l);
    assertNotNull(address);
    assertEquals(address.getCity(), "Washington");
    
    // search for person with this address
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( 
        Restrictions.not(
            Restrictions.notMemberOf("addresses", address)
        )
    );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 1 );
    assertEquals( result.get(0).getFirstname(), "Bill" );
    assertEquals( result.get(0).getLastname(), "Gates" );
    
  }
  
}
