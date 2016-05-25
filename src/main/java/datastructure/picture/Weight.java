package datastructure.picture;

//描述边的权值类
public class Weight {
   
	int row;  //横坐标
	int col;  //纵坐标
	int weight; //权值
	
	Weight(int row,int col,int weight)
	{
		this.row = row;
		this.col = col;
		this.weight = weight;
	}
	
	public static void createAdjGraphic(MyAdjGraphic g, Object[] vertices, int n,Weight[] weight,int e)
	throws Exception
	{
	   //初始化结点
	   for(int i=0;i<n;i++)
	   {
		   g.insertVertice(vertices[i]);
	   }
	   //初始化所有的边
	   for(int i=0;i<e;i++)
	   {
		   g.insertEdges(weight[i].row, weight[i].col, weight[i].weight);
	   }
	}
}
