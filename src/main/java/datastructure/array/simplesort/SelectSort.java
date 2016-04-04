package datastructure.array.simplesort;

public class SelectSort {
	static public void selectSort(int[] as) {
		int count = 0 ;
		int change = 0;
		int min = 0;
		for (int i = 0; i < as.length - 1; i++) {
			//  重置M为当前循环的次数    第一次循环拍第一个个 第二次第二个
			/**
			 *  * X X X X 循环length -1 次
			 *  * * X X X 
			 *  * * * X X
			 *  * * * * X
			 */
			min = i;
			for(int j = i + 1; j < as.length ; j ++){
				change ++;
				if ( as[min] > as [j]){
					min = j;
				}
			}
			BubbleSort.swap(as, min, i);
			count ++;
		}
		System.out.println("交换了n次:" + count + " :" + change);
	}
}
