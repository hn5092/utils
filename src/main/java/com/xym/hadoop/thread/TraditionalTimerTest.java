package com.xym.hadoop.thread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.hadoop.fs.shell.Count;

//quartz
public class TraditionalTimerTest {
  static int count = 0;
  public static void main(String[] args) {
    class MyTimerTask extends TimerTask{

      @Override
      public void run() {
        count = (count+1)%2;
        System.out.println("运行一次"+count);
        new Timer().schedule(new MyTimerTask(),count == 0?2000:4000);
      }
      
    }
    new Timer().schedule(new MyTimerTask(),2000);

    new Thread(new Runnable() {
      
      public void run() {
        // TODO Auto-generated method stub
        while(true){
        try {
          Thread.currentThread().sleep(1000);
          System.out.println(new Date().getSeconds());
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        }
        
      }
    }).start();
  }
  
}
