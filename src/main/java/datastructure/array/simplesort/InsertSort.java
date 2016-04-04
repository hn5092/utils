package datastructure.array.simplesort;

public class InsertSort {
	public static void insertSort(int[] as){
		int count = 0 ;
		int change = 0;
		//第一次排好2个 第二次排好3个 需要排 length -1 个    
		for( int i = 0 ; i < as.length -1 ; i ++){
			//第一次 默认比较12  二次比较 123  3次比较1234   
			//每次排序都是默认已经排好了
			for(int j = i + 1 ; j > 0 ; j --){
				change ++;
				if(as[j] <  as[j-1]){
					BubbleSort.swap(as, j-1, j);
					count ++;
				}else{
					break;
				}
			}
		}
		System.out.println("sort count : " + count + ":" + change );
	}
}
