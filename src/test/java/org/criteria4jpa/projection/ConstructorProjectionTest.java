package org.criteria4jpa.projection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.criteria4jpa.AbstractIntegrationTest;
import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.model.Person;
import org.testng.annotations.Test;

public class ConstructorProjectionTest extends AbstractIntegrationTest {
  
  public ConstructorProjectionTest() {
    super("projection/ConstructorProjectionTest.xml");
  }
  
  @Test
  public void testSimpleConstructorProjection() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.constructor(FirstAndLastName.class)
        .add( Projections.relativePath("firstname") )
        .add( Projections.relativePath("lastname") )
    );
    
    // perform query
    List<FirstAndLastName> result = criteria.getResultList();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.size(), 3);

    // asserts for all 3 results
    assertEquals( result.get(0).getFirstname(), "Christian");
    assertEquals( result.get(0).getLastname(), "Kaltepoth");
    assertEquals( result.get(1).getFirstname(), "Christian");
    assertEquals( result.get(1).getLastname(), "Mayer");
    assertEquals( result.get(2).getFirstname(), "Hans");
    assertEquals( result.get(2).getLastname(), "Peterson");

  }

  @Test
  public void testConstructorProjectionWithGrouping() {
    
    // build criteria
    Criteria criteria = CriteriaUtils.createCriteria(entityManager, Person.class);
    criteria.setProjection( Projections.constructor(FirstNameAndAge.class)
        .add( Projections.relativeGroupPath("firstname") )
        .add( Projections.avg("age") )
    );
    
    // perform query
    List<FirstNameAndAge> result = criteria.getResultList();
    
    // assert correct result
    assertNotNull( result );
    assertEquals( result.size(), 2);

    // asserts for all 2 results
    assertEquals( result.get(0).getFirstname(), "Christian");
    assertEquals( result.get(0).getAge().intValue(), 29); // (28+30)/2 
    assertEquals( result.get(1).getFirstname(), "Hans");
    assertEquals( result.get(1).getAge().intValue(), 50);

  }
  
  public static class FirstAndLastName {
    
    private final String firstname;
    private final String lastname;

    public FirstAndLastName(String firstname, String lastname) {
      this.firstname = firstname;
      this.lastname = lastname;
    }

    public String getFirstname() {
      return firstname;
    }

    public String getLastname() {
      return lastname;
    }
    
  }
  
  public static class FirstNameAndAge {
    
    private final String firstname;
    private final Number age;

    public FirstNameAndAge(String firstname, Integer age) {
      this.firstname = firstname;
      this.age = age;
    }
    
    public FirstNameAndAge(String firstname, Double age) {
      this.firstname = firstname;
      this.age = age;
    }
    
    public String getFirstname() {
      return firstname;
    }

    public Number getAge() {
      return age;
    }
  }
  
}
