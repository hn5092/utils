package com.x.hadoop.mr.kpi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class BaseStationInfo implements Writable{
  private String imsi;
  private String timeFlag;
  private String cgi;
  private float stayTime;
  public void write(DataOutput out) throws IOException {
    out.writeUTF(imsi);
    out.writeUTF(timeFlag);
    out.writeUTF(cgi);
    out.writeFloat(stayTime);
  }

  public void readFields(DataInput in) throws IOException {
    this.imsi = in.readUTF();
    this.timeFlag = in.readUTF();
    this.cgi = in.readUTF();
    this.stayTime = in.readFloat();
  }
  public void clear(){
    this.imsi = null;
    this.timeFlag = null;
    this.cgi = null;
    this.stayTime = 0;
  }

  public String getImsi() {
    return imsi;
  }

  public void setImsi(String imsi) {
    this.imsi = imsi;
  }

  public String getTimeFlag() {
    return timeFlag;
  }

  public void setTimeFlag(String timeFlag) {
    this.timeFlag = timeFlag;
  }

  public String getCgi() {
    return cgi;
  }

  public void setCgi(String cgi) {
    this.cgi = cgi;
  }

  public float getStayTime() {
    return stayTime;
  }

  public void setStayTime(float stayTime) {
    this.stayTime = stayTime;
  }
  

}
