package org.criteria4jpa;

import static org.testng.Assert.fail;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.testng.annotations.BeforeClass;

public abstract class AbstractIntegrationTest extends AbstractTest {

  private final String datasetName;
  
  protected EntityManager entityManager;

  public AbstractIntegrationTest(String relativeDatasetName) {
    this.datasetName = 
      AbstractIntegrationTest.class.getPackage().getName().replace(".", "/")+
      "/"+relativeDatasetName;
  }
  
  @BeforeClass
  @SuppressWarnings("deprecation")
  public void initDatabase() {

    // build EntityManagerFactory
    EntityManagerFactory entityManagerFactory = 
      Persistence.createEntityManagerFactory("integration-test");
    
    // build EntityManager
    entityManager = entityManagerFactory.createEntityManager();
    
    // very dirty way to get jdbc connection
    Session hibernateSession = (Session) entityManager.getDelegate();
    Connection connection = hibernateSession.connection();
    
    try {
      
      // load dataset
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      URL datasetUrl = classLoader.getResource(datasetName);
      IDataSet dataset = new FlatXmlDataSet(datasetUrl);
    
      // insert data
      IDatabaseConnection databaseConnection = new DatabaseConnection(connection);
      databaseConnection.getConfig().setProperty(
          DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
      DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataset);
      
    } catch (DatabaseUnitException e) {
      fail("Failed", e);
    } catch (SQLException e) {
      fail("Failed", e);
    } catch (IOException e) {
      fail("Failed", e);
    }
  }
  
}
