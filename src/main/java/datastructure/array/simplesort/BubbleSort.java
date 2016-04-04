package datastructure.array.simplesort;

/**
 * 效率 是 O(N2) 低-
 * 
 * @author imad
 *
 */
public class BubbleSort {
	static public void bubbleSort(int[] as) {
		// 用来控制循环的次数
		int count = 0;
		int change = 0;

		for (int i = as.length - 1; i > 0; i--) {
			// X X X X X X * 1次
			// X X X X X * *
			// X X X X * * *
			// X X X * * * *
			// X X * * * * * 排序
			// X * * * * * * length - 1 次
			for (int j = 0; j < i; j++) {
				change++;
				if (as[j] > as[j + 1]) {
					swap(as, j, j + 1);
					count++;
				}
			}
		}
		System.out.println(count + " 次数"+change);

	}

	static public void bubbleSort2(int[] as) {
		// 外层去一个用来比较的数据 位置
		// 从前往后排 这个是排第一个 按着顺序来拍
		/**
		 *  * X X X X 循环length -1 次
		 *  * * X X X 
		 *  * * * X X
		 *  * * * * X
		 */
		int count = 0;
		int change = 0;

		for (int i = 0; i < as.length - 1; i++) {
			for (int j = i; j < as.length; j++) {
				change ++;
				if (as[i] > as[j]) {
					swap(as, i, j);
					count++;
				}
			}
		}
		System.out.println(count + " 次数" + change);

	}
	static public void printArray(int[] as){
		System.out.println("--------------------------");
		for (int i : as) {
			System.out.println(i);
		}
		System.out.println("--------------------------");
	}

	static public void swap(int[] as, int i, int j) {
		int temp = as[i];
		as[i] = as[j];
		as[j] = temp;
	}

}
