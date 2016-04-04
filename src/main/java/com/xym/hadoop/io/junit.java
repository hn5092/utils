package com.xym.hadoop.io;

import static org.junit.Assert.*;

import org.apache.hadoop.io.Text;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
public class junit {
  
  @Test
  public void tex(){
    Text t = new Text("hadoop");
    int a = 1;
    assertEquals(1,a);
    assertTrue(true);
    assertNull(null);
//    assertThat(a, is(6));
//    assertThat(trueValue, is(true));
//    assertThat(nullObject, nullValue());
//    assertThat(msg, both(startsWith("Hello")).and(endsWith("World")));
    assertThat(t.charAt(2),is((int)'d'));
    //超出下标的就是-1
    assertThat("out of bound",t.charAt(11),is(-1));
    //找到第一个数 相当于indexof
    assertThat("find a substring",t.find("d"),is(2));
    //从第四个开始 寻找
    assertThat("find a substring",t.find("o",4),is(4));
    //找不到返回-1
    assertThat("find a substring",t.find("x",4),is(-1));
    
    
    String s = "\u0041\u00DF\u6771\uD801\uDC00";
    System.out.println(s);
    assertThat("find a substring",s.indexOf("\u0041"),is(0));
    assertThat("find a substring",s.indexOf("\u00DF"),is(1));
    assertThat("find a substring",s.indexOf("\u6771"),is(2));
    assertThat("find a substring",s.indexOf("\uD801\uDC00"),is(3));
    assertThat(s.charAt(3),is('\uD801'));
    assertThat(s.charAt(4),is('\uDC00'));
    
    Text t2 = new Text("\u0041\u00DF\u6771\uD801\uDC00");
    assertThat(t2.getLength(),is(10));
    assertThat(t2.find("\u0041"),is(0));
    assertThat(t2.find("\u00DF"),is(1));
    assertThat(t2.find("\u6771"),is(3));
    assertThat(t2.find("\uD801\uDC00"),is(6));
    
  }
}
