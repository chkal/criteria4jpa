package org.criteria4jpa.join;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria.FetchMode;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.model.Person;
import org.criteria4jpa.projection.Projections;
import org.hibernate.LazyInitializationException;
import org.testng.annotations.Test;

@SuppressWarnings("unchecked")
public class FetchJoinTypeTest extends AbstractIntegrationTest {
  
  public FetchJoinTypeTest() {
    super("join/FetchJoinTypeTest.xml");
  }
  
  @Test(expectedExceptions=LazyInitializationException.class)
  public void testNoFetchJoinWillThrowLazyInitializationException() {
    
    // list all persons
    List<Person> persons = CriteriaUtils.createCriteria(entityManager, Person.class)
        .getResultList();

    // person will get detached
    entityManager.clear();
    
    // we also got the person with no addresses
    assertEquals(2, persons.size());

    // get an address -> will throw LazyInitializationException
    persons.get(0).getAddresses().size();
    
  }

  @Test
  public void testFetchJoinWithĹeftOuterJoin() {
    
    // list all persons
    List<Person> persons = CriteriaUtils.createCriteria(entityManager, Person.class)
        .setProjection( Projections.distinctRootEntity() )
        .setFetchMode("addresses", FetchMode.LEFT_OUTER_FETCH_JOIN)
        .getResultList();

    // person will get detached
    entityManager.clear();
    
    // we also got the person with no addresses
    assertEquals(2, persons.size());

    // fetch join got all related entities
    assertEquals(3, persons.get(0).getAddresses().size());
    assertEquals("London", persons.get(0).getAddresses().get(0).getCity());
    assertEquals("New York", persons.get(0).getAddresses().get(1).getCity());
    assertEquals("Berlin", persons.get(0).getAddresses().get(2).getCity());
    assertEquals(0, persons.get(1).getAddresses().size());
    
  }

  @Test
  public void testFetchJoinWithInnerJoin() {
    
    // list all persons
    List<Person> persons = CriteriaUtils.createCriteria(entityManager, Person.class)
        .setProjection( Projections.distinctRootEntity() )
        .setFetchMode("addresses", FetchMode.INNER_FETCH_JOIN)
        .getResultList();

    // person will get detached
    entityManager.clear();
    
    // we got only the person with addresses
    assertEquals(1, persons.size());

    // fetch join got all related entities
    assertEquals(3, persons.get(0).getAddresses().size());
    assertEquals("London", persons.get(0).getAddresses().get(0).getCity());
    assertEquals("New York", persons.get(0).getAddresses().get(1).getCity());
    assertEquals("Berlin", persons.get(0).getAddresses().get(2).getCity());
    
  }
  
}
