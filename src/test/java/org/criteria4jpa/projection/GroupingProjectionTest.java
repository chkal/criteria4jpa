package org.criteria4jpa.projection;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class GroupingProjectionTest extends AbstractIntegrationTest {
  
  public GroupingProjectionTest() {
    super("projection/GroupingProjectionTest.xml");
  }
  
  @Test
  public void testSimpleGroupBy() {
    
    // build criteria with join on collection
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.projectionList()
        .add( Projections.count("id") ) 
        .add( Projections.avg("age") ) 
        .add( Projections.groupProperty("firstname") ) 
    );
    
    // perform query
    List<Object[]> result = criteria.getResultList();
    
    // assert correct result
    assertEquals( result.size(), 2 );
    
    // first row
    assertEquals( ((Number)result.get(0)[0]).longValue(), 2l);      // 2 persons
    assertEquals( ((Number)result.get(0)[1]).doubleValue(), 29.0);  // avg age
    assertEquals( result.get(0)[2], "Christian");                   // group property

    // second row
    assertEquals( ((Number)result.get(1)[0]).longValue(), 1l);      // 1 person
    assertEquals( ((Number)result.get(1)[1]).doubleValue(), 50.0);  // his age
    assertEquals( result.get(1)[2], "Hans");                        // group property
    
  }
}
