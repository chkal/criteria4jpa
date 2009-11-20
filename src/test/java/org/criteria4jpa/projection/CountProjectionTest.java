package org.criteria4jpa.projection;

import static org.testng.Assert.assertEquals;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class CountProjectionTest extends AbstractIntegrationTest {
  
  public CountProjectionTest() {
    super("projection/CountProjectionTest.xml");
  }
  
  @Test
  public void testCountOnRootIdProperty() {
    
    // build criteria with join on collection
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.count("id") );
    criteria.createCriteria("addresses");
    
    // perform query
    Long result = (Long) criteria.getSingleResult();
    
    // assert correct result (4 persons, 7 addresses, no distinct -> 7 results)
    assertEquals( result, Long.valueOf( 7 ) );
    
  }
  
  @Test
  public void testCountDistinctOnRootIdProperty() {
    
    // build criteria with join on collection
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.countDistinct("id") );
    criteria.createCriteria("addresses");
    
    // perform query
    Long result = (Long) criteria.getSingleResult();
    
    // assert correct result (4 persons, 7 addresses, distinct -> 4 results)
    assertEquals( result, Long.valueOf( 4 ) );
    
  }
  
  @Test
  public void testCountOnPropertyPath() {
    
    // build criteria with join on collection
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.countDistinct("firstname") );
    criteria.createCriteria("addresses");
    
    // perform query
    Long result = (Long) criteria.getSingleResult();
    
    // assert correct result (4 persons, 7 addresses, 3x Christian, distinct -> 2 results)
    assertEquals( result, Long.valueOf( 3 ) );
    
  }
  
  @Test
  public void testRowCount() {
    
    // build criteria with join on collection
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.rowCount() );
    criteria.createCriteria("addresses");
    
    // perform query
    Long result = (Long) criteria.getSingleResult();
    
    // assert correct result (4 persons, 7 addresses, no distinct -> 7 results)
    assertEquals( result, Long.valueOf( 7 ) );
  }
  
  @Test
  public void testRowCountDistinct() {
    
    // build criteria with join on collection
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.rowCountDistinct() );
    criteria.createCriteria("addresses");
    
    // perform query
    Long result = (Long) criteria.getSingleResult();
    
    // assert correct result (4 persons, 7 addresses, distinct -> 4 results)
    assertEquals( result, Long.valueOf( 4 ) );
  }
  
}
