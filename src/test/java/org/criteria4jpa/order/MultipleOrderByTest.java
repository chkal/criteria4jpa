package org.criteria4jpa.order;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class MultipleOrderByTest extends AbstractIntegrationTest {
  
  public MultipleOrderByTest() {
    super("order/MultipleOrderByTest.xml");
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void testMultiLevelOrderBy() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.addOrder( Order.asc("age") );
    criteria.addOrder( Order.asc("firstname") );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.size(), 4 );
    assertEquals( result.get(0).getAge(),       Integer.valueOf( 21 ) );
    assertEquals( result.get(0).getFirstname(), "Xavian" );
    assertEquals( result.get(1).getAge(),       Integer.valueOf( 28 ) );
    assertEquals( result.get(1).getFirstname(), "Christian" );
    assertEquals( result.get(2).getAge(),       Integer.valueOf( 28 ) );
    assertEquals( result.get(2).getFirstname(), "Hans" );
    assertEquals( result.get(3).getAge(),       Integer.valueOf( 28 ) );
    assertEquals( result.get(3).getFirstname(), "Peter" );
    
  }

}
