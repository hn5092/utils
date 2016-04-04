package datastructure.array.noorder;

import org.junit.Test;

/**
 * 通过索引操作数组
 * @author imad
 *
 */
public class OperateWithIndex {
	private int[] datas = null;
	private int currentIndex = 0;
	public OperateWithIndex(int length) {
		datas = new int[length];
	}

	public int insert(int data) {
		datas[currentIndex] = data;
		currentIndex++;
		return currentIndex;
	}

	public void remove(int index) {
		// 从移除的开始 到他当前的索引
		// |------|
		// |------|
		// |------|
		// |------|<---index
		// |------|
		// |------|<---currentindex
		if (index == datas.length){
			datas[index] = 0;
		}
		for (int i = index; i < currentIndex - 1; i++) {
			datas[i] = datas[i + 1];
		}
		currentIndex--;
	}

	public int searchOne(int index) {
		return datas[index];
	}

	public void printdata() {
		System.out.println("--------------------");
		for (int i = 0; i < currentIndex; i++) {
			System.out.println(datas[i]);
		}
	}
	public static void main(String[] args) {
		OperateWithIndex i = new OperateWithIndex(10);
		i.insert(0);
		i.insert(1);
		i.insert(2);
		i.insert(3);
		i.insert(4);
		i.insert(5);
		i.insert(6);
		i.insert(7);
		i.insert(8);
		i.insert(9);
		i.printdata();
		i.remove(5);
		i.printdata();
		System.out.println(i.searchOne(5));

	}
}
