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

public class JunctionTest extends AbstractIntegrationTest {
  
  public JunctionTest() {
    super("restriction/JunctionTest.xml");
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void testSimpleConjunction() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.conjunction()
        .add(Restrictions.eq("firstname", "Christian"))
        .add(Restrictions.between("age", 20, 40))
        .add(Restrictions.isNotNull("title"))
    );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getId(), 1l );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(0).getLastname(), "Kaltepoth" );

    assertEquals( result.get(1).getId(), 3l );
    assertEquals( result.get(1).getFirstname(), "Christian" );
    assertEquals( result.get(1).getLastname(), "Babel" );

  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSimpleDisjunction() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.disjunction()
        .add(Restrictions.eq("firstname", "Hans"))
        .add(Restrictions.eq("lastname", "Mayer"))
    );
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getId(), 2l );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(0).getLastname(), "Mayer" );

    assertEquals( result.get(1).getId(), 5l );
    assertEquals( result.get(1).getFirstname(), "Hans" );
    assertEquals( result.get(1).getLastname(), "Peterson" );

  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void testNestedJunctions() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.add( Restrictions.conjunction()
        .add( Restrictions.eq("firstname", "Christian") )
        .add( Restrictions.disjunction()
            .add( Restrictions.eq("lastname", "Mayer") )
            .add( Restrictions.eq("lastname", "Babel") )
            )
    );
    
    /*
     * If no brackets are rendered the query would look like this:
     * 
     * WHERE firstname = 'Christian' AND lastname = 'Mayer' OR lastname = 'Babel'
     * 
     * Because AND has higher operator priority, this query would return "Bill Babel".
     * 
     * If the brackets are rendered correctly "Christian Mayer" and
     * "Christian Babel" are returned
     * 
     */
    
    // perform query
    List<Person> result = criteria.getResultList();
    
    // assert result
    assertNotNull( result );
    assertEquals( result.size(), 2 );
    assertEquals( result.get(0).getId(), 2l );
    assertEquals( result.get(0).getFirstname(), "Christian" );
    assertEquals( result.get(0).getLastname(), "Mayer" );

    assertEquals( result.get(1).getId(), 3l );
    assertEquals( result.get(1).getFirstname(), "Christian" );
    assertEquals( result.get(1).getLastname(), "Babel" );

  }
  
}
