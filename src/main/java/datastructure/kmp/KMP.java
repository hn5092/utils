package datastructure.kmp;

//KMP算法
public class KMP {

	//根据给定的模式串，求next[j]的算法
	public static int[] getNext(String sub)
	{
		int j=1,k=0;
		int[] next = new int[sub.length()];
		next[0]=-1;
		next[1]=0;
		//不包含
		while(j<sub.length()-1)
		{
			char c1 = sub.charAt(k);
			char c = sub.charAt(j);
			if(c == c1)
			{
				next[j+1]=k+1;
				j++;
				k++;
			}
			else if(k==0)
			{
				next[j+1]=0;
				j++;
			}
			else
			{
				k=next[k];
			}
		}
		return next;
	}
	
	//根据给定主串和子串，采用KMP算法
	public static int kmp(String src,String sub)
	{
		//先生成模式串sub的next[j]
		int[] next = getNext(sub);
		
		//i:主串的游标
		//j:子串的游标
		int i=0,j=0,index=-1;
		while(i<src.length()&&j<sub.length())
		{
			if(src.charAt(i)==sub.charAt(j))
			{
				i++;
				j++;
			}
			else if(j==0)
			{
				i++;
			}
			else
			{
				j=next[j]; //向右滑动
			}
				
		}
		if(j==sub.length())
		{
			index = i-sub.length();
		}
		return index;
	}
}
