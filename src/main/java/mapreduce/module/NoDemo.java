package mapreduce.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;


public class NoDemo {
  public static void main(String[] args) throws InterruptedException {
    String no = "E097406001";
    //首先创建一个有效位数的数组
    HashSet<Integer> E = new HashSet<Integer>();
    for(int i =0 ; i <99999999; i++){
      E.add(i);
    }
    HashSet<Integer> F = new HashSet<Integer>();
    for(int i =0 ; i <99999999; i++){
      F.add(i);
    }
    int n1 = Integer.parseInt(no.substring(1, no.length()-3));
    System.out.println(E.contains(n1));
    E.remove(n1);
    System.out.println(E.contains(n1));
    System.out.println("完成啦");
    Thread.currentThread().sleep(10000);
  }
  @Test
  public void map(){
    String Afile = "E097406001";
   
    //虚拟一个if 进入A文件if(filename = A)
    char Amark = Afile.charAt(0); //获取A的标示符
    int A = Integer.parseInt(Afile.substring(1, Afile.length()-3));//获取有效数字
    System.out.println("A:"+A+" Amark:"+Amark);
    //writer(Amark,A) 
    
    //B文件直接writer(999999999,no)  为了保证B最后到reduce
  
  }
  
  @Test
  public void reduce(){
    String Bkey = "E088806008";
    String Bkey2 = "F088806000";
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    map.put("E", 97406);
    map.put("E_B", 88805);
    map.put("E_B2",88806);
    //虚拟一个if if(key ==E)
    List<Integer> listE = new ArrayList<Integer>();
    listE.add(map.get("E"));
    //其他字母的也是一样
    
    
    
    //if(key == E_B2) 当到这个key的时候已经说明 前面已经载入完了
    //虚拟一个if 收到key是B if(key = B)
    //虚拟一个if　判断后三位是000   如果后三位为000  那么有效数字-1
    char Bmark = Bkey.charAt(0); //获取A的标示符
    int B = Integer.parseInt(Bkey.substring(1, Bkey.length()-3))-1;//获取有效数字
    System.out.println("B:"+B+" Bmark:"+Bmark);
    //else  后三位不是000的
    char Bmark2 = Bkey.charAt(0); //获取A的标示符
    int B2 = Integer.parseInt(Bkey2.substring(1, Bkey2.length()-3));
    System.out.println("B:"+B2+" Bmark:"+Bmark2);
    
    //传入有效位数和数组 得到排除了包含的范围
    List<Integer> list = clear(listE, (B2+"").toString().length());
    if(Bmark == 'E'){
      if(list.contains(map.get("E_B"))){
        System.out.println("writer(Bkey,false)");
      }else{
        System.out.println("writer(Bkey,true)");
      }
    }
    
  }
  private List<Integer> clear(List<Integer> orList , int maxLength ) {
    List<Integer> list = new ArrayList<Integer>();
    String maxString = "";
    for(int i = 0;i < maxLength ; i++){
      maxString += "9";
    }
    for(int i = 0; i < Integer.parseInt(maxString); i++){
      list.add(i);
    }
    for(Integer i : orList){
      if(list.contains(i)){
        list.remove(i);
      }
    }
    return list;
  }
  
  
}
