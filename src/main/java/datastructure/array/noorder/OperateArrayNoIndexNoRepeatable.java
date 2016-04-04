package datastructure.array.noorder;

public class OperateArrayNoIndexNoRepeatable {
	private int[] datas = null;
	private int currentIndex = 0;
	
	public OperateArrayNoIndexNoRepeatable(int length) {
		datas = new int[length];
	}

	public int insert(int data) {
			datas[currentIndex] = data;
			currentIndex++;
			return currentIndex;
	}

	public void remove(int data) {
		// 从移除的开始 到他当前的索引
		// |------|
		// |------|<--find index
		// |------|
		// |------|<---index --> remove
		// |------|
		// |------|
		// 查找是否有 
		int index = getIndex(0, data);
		//2.有的话循环查找剩下的
		while (index >= 0) {
			if (index != -1) {
					if (index == currentIndex) {
						datas[index] = 0;
				}
					for (int i = index; i < currentIndex - 1; i++) {
						datas[i] = datas[i + 1];
					}
				currentIndex--;
				index = getIndex(index, data);
			}
		}
	}

	private int getIndex(int index, int data) {

		for (int i = index; i < currentIndex; i++) {
			if (datas[i] == data) {
				return i;
			}
		}
		return -1;
	}

//	public int search(int data) {
//		int index = getIndex(data);
//		if (index != -1) {
//
//			return index;
//		}
//		return 0;
//	}

	public void printdata() {
		System.out.println("--------------------");
		for (int i = 0; i < currentIndex; i++) {
			System.out.println(datas[i]);
		}
	}

	public static void main(String[] args) {
		OperateArrayNoIndexNoRepeatable i = new OperateArrayNoIndexNoRepeatable(10);
		i.insert(1);
		i.insert(2);
		i.insert(2);
		i.insert(2);
		i.insert(4);
		i.insert(5);
		i.printdata();
		i.remove(4);
		i.printdata();
//		System.out.println("find datas[5] : " + i.searchOne(3));

	}
}
