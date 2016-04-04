package com.xym.hadoop.conf.test;

import java.io.File;

import junit.framework.TestCase;

import org.apache.hadoop.conf.Configuration;
import org.junit.Test;

public class TestConf extends TestCase{
  private Configuration conf;
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    conf = new Configuration();
    System.out.println("开始初始化");
  }
  @Test
  public void testAdd(){
    new File("test-config-TestConfiguration.xml");
  }
}
