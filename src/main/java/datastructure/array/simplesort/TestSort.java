package datastructure.array.simplesort;

import org.junit.Before;
import org.junit.Test;

public class TestSort {
	public int[] as = { 1, 8, 3, 55, 21, 12124, 12, 123 };
	public int[] as2 = { 1, 8, 3, 55, 21, 12124, 12, 123 };
	@Before
	public void setup(){
	}
	@Test
	public void bubble(){
		//6 次数28
		BubbleSort.bubbleSort(as);
		//6 次数35
		BubbleSort.bubbleSort2(as2);
		BubbleSort.printArray(as);
		BubbleSort.printArray(as2);

	}
	
	
	//交换了n次:7 :28
	@Test
	public void select() {
		SelectSort.selectSort(as);
		BubbleSort.printArray(as);

	}
	//sort count : 6:28
	@Test
	public void co(){
		InsertSort.insertSort(as);
		BubbleSort.printArray(as);
	}
}
