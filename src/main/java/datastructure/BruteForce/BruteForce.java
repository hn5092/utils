package datastructure.BruteForce;

//Brute-Force�㷨
public class BruteForce {
  
	public static int bruteForce(String src,String sub)
	{
		int i=0,j=0,index=-1;
		while(i<src.length()&&j<sub.length())
		{
			if(src.charAt(i)==sub.charAt(j))
			{
				i++;
				j++;
			}
			else
			{
				i=i-j+1;
				j=0;
			}
		}
		
		if(j==sub.length())
		{
			index = i-sub.length();
		}
		
		return index;
	}
}
