package org.criteria4jpa.order;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class SingleOrderByTest extends AbstractIntegrationTest {
  
  public SingleOrderByTest() {
    super("order/SingleOrderByTest.xml");
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void testSingleLevelOrderByAscending() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.addOrder( Order.asc("age") );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.size(), 3 );
    assertEquals( result.get(0).getAge(), Integer.valueOf( 18 ) );
    assertEquals( result.get(1).getAge(), Integer.valueOf( 25 ) );
    assertEquals( result.get(2).getAge(), Integer.valueOf( 28 ) );
    
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSingleLevelOrderByDescending() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.addOrder( Order.desc("age") );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.size(), 3 );
    assertEquals( result.get(0).getAge(), Integer.valueOf( 28 ) );
    assertEquals( result.get(1).getAge(), Integer.valueOf( 25 ) );
    assertEquals( result.get(2).getAge(), Integer.valueOf( 18 ) );
    
  }
}
