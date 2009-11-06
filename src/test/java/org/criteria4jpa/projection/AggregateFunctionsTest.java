package org.criteria4jpa.projection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class AggregateFunctionsTest extends AbstractIntegrationTest {
  
  public AggregateFunctionsTest() {
    super("projection/AggregateFunctionsTest.xml");
  }
  
  @Test
  public void testMinFunction() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.min("age") );
    
    // perform query
    Integer result = (Integer) criteria.getSingleResult();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result, Integer.valueOf( 28 ) );
  }

  @Test
  public void testMaxFunction() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.max("age") );
    
    // perform query
    Integer result = (Integer) criteria.getSingleResult();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result, Integer.valueOf( 50 ) );
  }

  @Test
  public void testSumFunction() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.sum("age") );
    
    // perform query
    // Result will be of type Long (see specification)
    Long result = (Long) criteria.getSingleResult();
    
    // assert correct result
    assertNotNull( result );
    // 50 + 28 + 30 = 108
    assertEquals( result, Long.valueOf( 108 ) );
  }

  @Test
  public void testAvgFunction() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.avg("age") );
    
    // perform query
    // Result will be of type Double (see specification)
    Double result = (Double) criteria.getSingleResult();
    
    // assert correct result
    assertNotNull( result );
    // (50 + 28 + 30)/3 = 36
    assertEquals( result, Double.valueOf( 36 ) );
  }
  
}
