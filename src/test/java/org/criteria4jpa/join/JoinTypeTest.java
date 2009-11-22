package org.criteria4jpa.join;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.Criteria.JoinType;
import org.criteria4jpa.model.Person;
import org.criteria4jpa.projection.Projections;
import org.testng.annotations.Test;

public class JoinTypeTest extends AbstractIntegrationTest {
  
  public JoinTypeTest() {
    super("join/JoinTypeTest.xml");
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void testDefaultJoinType() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.distinctRootEntity() );
    criteria.createCriteria("addresses");
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // We did an inner join. One person has no address, so we get 3 results!
    assertEquals( result.size(), 3 );

  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void testInnerJoin() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.distinctRootEntity() );
    criteria.createCriteria("addresses", JoinType.INNER_JOIN);
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // We did an default join (=INNER). One person has no address, so we get 3 results!
    assertEquals( result.size(), 3 );
    
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void testLeftOuterJoin() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.distinctRootEntity() );
    criteria.createCriteria("addresses", JoinType.LEFT_OUTER_JOIN);
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // We did an left outer join. So we will get all 4 persons, even if they have no address
    assertEquals( result.size(), 4 );
    
  }
  
}
