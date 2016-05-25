package datastructure.picture;

import java.util.ArrayList;

//邻接矩阵类
public class MyAdjGraphic {

	static final int maxWeight=-1; //如果两个结点之间没有边，权值为-1；
	ArrayList vertices = new ArrayList();//存放结点的集合
	int[][] edges; //邻接矩阵的二维数组
	int numOfEdges; //边的数量
	
	public MyAdjGraphic(int n)
	{
	    edges = new int[n][n];
		for(int i=0;i<n;i++)
	    {
	    	for(int j=0;j<n;j++)
	    	{
	    		if(i==j) //对角线上的元素
	    		{
	    		   edges[i][j]=0;	
	    		}
	    		else
	    		{
	    		   edges[i][j]=maxWeight;
	    		}
	    	}
	    }
	    numOfEdges = 0;
	}
	
	//返回边的数量
	public int getNumOfEdges()
	{
		return this.numOfEdges;
	}
	
	//返回结点的数量
	public int getNumOfVertice()
	{
		return this.vertices.size();
	}
	
	//返回结点的值
	public Object getValueOfVertice(int index)
	{
	    return this.vertices.get(index);	
	}
	
	//获得某条边的权值
	public int getWeightOfEdges(int v1,int v2) throws Exception
	{
	   if((v1 < 0 || v1 >= vertices.size())||(v2 < 0||v2 >= vertices.size()))
	   {
		   throw new Exception("v1或者v2参数越界错误！");
	   }
	   return this.edges[v1][v2];
	   
	}
	
	//插入结点
	public void insertVertice(Object obj)
	{
		this.vertices.add(obj);
	}
	
	//插入带权值的边
	public void insertEdges(int v1,int v2,int weight) throws Exception
	{
		if((v1 < 0 || v1 >= vertices.size())||(v2 < 0||v2 >= vertices.size()))
		{
		  throw new Exception("v1或者v2参数越界错误！");
		}
		
		this.edges[v1][v2]=weight;
		this.numOfEdges++;
	}
	
	//删除某条边
	public void deleteEdges(int v1,int v2) throws Exception
	{
	    if((v1 < 0 || v1 >= vertices.size())||(v2 < 0||v2 >= vertices.size()))
		{
		  throw new Exception("v1或者v2参数越界错误！");
		}
	    if( v1==v2 || this.edges[v1][v2]==maxWeight)	
	    {
	    	throw new Exception("边不存在！");
	    }
	    
	    this.edges[v1][v2]=maxWeight;
	    this.numOfEdges--;   
	}
	
	//打印邻接矩阵
	public void print()
	{
		for(int i=0;i<this.edges.length;i++ )
		{
			for(int j=0;j<this.edges[i].length;j++)
			{
			    System.out.print(edges[i][j]+" ");	
			}
			System.out.println();
		}
	}
}
