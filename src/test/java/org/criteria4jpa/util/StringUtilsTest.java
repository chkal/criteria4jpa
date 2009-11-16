package org.criteria4jpa.util;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class StringUtilsTest {
  
  @Test
  public void testGetLastPathComponent() {
    
    assertEquals(StringUtils.getLastPathComponent("parent.child"), "child");
    assertEquals(StringUtils.getLastPathComponent("simple"), "simple");
    assertEquals(StringUtils.getLastPathComponent("very.long.path.expression"), "expression");
    
  }

}
