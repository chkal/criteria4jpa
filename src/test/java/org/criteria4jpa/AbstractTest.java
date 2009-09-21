package org.criteria4jpa;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.testng.annotations.BeforeSuite;

public abstract class AbstractTest {

  @BeforeSuite
  public void initLogger() {
    
    // get log manager
    LogManager manager = LogManager.getLogManager();
    
    // configure root logger
    Logger rootLogger = manager.getLogger("");
    rootLogger.getHandlers()[0].setLevel(Level.FINEST);
    rootLogger.setLevel(Level.WARNING);
    
    // configure criteria4jpa logger
    Logger logger = Logger.getLogger("org.criteria4jpa");
    logger.setLevel(Level.FINEST);
    
  }
}
