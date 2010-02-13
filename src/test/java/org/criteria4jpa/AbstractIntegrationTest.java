package org.criteria4jpa;

import static org.testng.Assert.fail;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceProvider;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.testng.annotations.BeforeClass;

public abstract class AbstractIntegrationTest extends AbstractTest {

  private final static String PERSISTENCE_UNIT = "integration-test";
  
  private final static String JPA_PROVIDER_KEY = "jpa.provider";
  
  private final String datasetName;
  
  protected EntityManager entityManager;

  public AbstractIntegrationTest(String relativeDatasetName) {
    this.datasetName = 
      AbstractIntegrationTest.class.getPackage().getName().replace(".", "/")+
      "/"+relativeDatasetName;
  }
  
  @BeforeClass
  public void initDatabase() {

    // check for JPA provider system property
    String selectedProvider = System.getProperty(JPA_PROVIDER_KEY, "default");
    
    // Hibernate (default)
    if( selectedProvider.equals( "hibernate" ) ) {
      entityManager = createEntityManager("org.hibernate.ejb.HibernatePersistence");
    }
    
    // OpenJPA
    else if( selectedProvider.equals( "openjpa" )) {
      entityManager = createEntityManager("org.apache.openjpa.persistence.PersistenceProviderImpl");
    }
    
    // Toplink Essentials
    else if( selectedProvider.equals( "toplink" )) {
      entityManager = createEntityManager("oracle.toplink.essentials.PersistenceProvider");
    }
    
    // Eclipse Link
    else if( selectedProvider.equals("eclipselink") ) {
      entityManager = createEntityManager("org.eclipse.persistence.jpa.PersistenceProvider");
    }
    
    // auto-detect provider
    else {
      entityManager = createEntityManager(null);
    }
    
    // populate database
    try {
      
      // create JDBC connection
      Class.forName("org.hsqldb.jdbcDriver");
      Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "sa", "");

      // load dataset
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      URL datasetUrl = classLoader.getResource(datasetName);
      IDataSet dataset = new FlatXmlDataSet(datasetUrl);

      // insert data
      IDatabaseConnection databaseConnection = new DatabaseConnection(connection);
      databaseConnection.getConfig().setProperty(
          DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
      DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataset);
        
    } catch (SQLException e) {
      fail("Failed", e);
    } catch (ClassNotFoundException e) {
      fail("Cannot load JDBC driver", e);
    } catch (DatabaseUnitException e) {
      fail("Failed", e);
    } catch (IOException e) {
      fail("Failed", e);
    }
    
  }
  
  /**
   * Creates an {@link EntityManager} using the specified JPA SPI provider class.  
   */
  private static EntityManager createEntityManager(String providerClazz) {
    
    try {

      // the EntityManagerFactory
      EntityManagerFactory factory = null;
      
      // Do we want to use a specific provider?
      if( providerClazz != null ) {
        
        // create provider from class name
        PersistenceProvider provider = 
          (PersistenceProvider) Class.forName(providerClazz).newInstance();
        
        // use this provider to create EntityManagerFactory
        factory = provider.createEntityManagerFactory(PERSISTENCE_UNIT, null);
        
      } else {
        
        // create factory by auto-detecting the provider
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, null);
        
      }
      
      // build EntityManager
      return factory.createEntityManager();
      
    } catch (InstantiationException e) {
      throw new IllegalStateException(e);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException(e);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(e);
    }
    
  }
  
}
