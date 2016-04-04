package test;
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileReader;  
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;  
  

public class ParseLog {
    
     public static Map<String, String> transformlineToMap(String line) {
    	 
    	 Map<String, String> map = new HashMap<String, String>();
    	 String[] line2=line.split(" ");
    	
 		map.put("Time",line2[0]+" "+line2[1]);
		map.put("ApMethod",line2[2]);
		map.put("IP",line2[4]);
		map.put("CookieID",line2[27]);
    	 
    	return  map;
    	 
     }
     
     public static Map<String, String> IpCountSortMap(String line) {
    	 
    	 Map<String, String> map = new HashMap<String, String>();
    	 String[] line2=line.split("\t");//输出是以反斜杠\t

 		map.put("IP",line2[0]);
		map.put("CookieID",line2[1]);
		map.put("Count",line2[2]);
		//map.put("Count","2");

    	return  map;
    	 
     }

     public static List readFileByLines(String fileName) {  
	        File file = new File(fileName);  
	        BufferedReader reader = null;  
	        try {
	            System.out.println("以行为单位读取文件内容，一次读一整行：");  
	            reader = new BufferedReader(new FileReader(file));  
	            String tempString = null;  
	            int line = 1;  
	            // 一次读入一行，直到读入null为文件结束  
	            List content=new ArrayList();
	            while ((tempString = reader.readLine()) != null) {
	            	//应该是新建一个列表存储元素
	            	content.add(tempString);
	                //System.out.println("line " + line + ": " + tempString);  
	                //line++;  
	            } 
	            reader.close(); 
	            return content;
	            
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            if (reader != null) {  
	                try {  
	                    reader.close();  
	                } catch (IOException e1) {  
	                }  
	            }  
	        }
			return null; 
	    } 
	  
}
