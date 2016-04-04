package datastructure.array.order;

public class OperateOrderNoIndex {
	private int[] datas = null;
	private int currentIndex = 0;

	public OperateOrderNoIndex(int length) {
		datas = new int[length];
	}

	public int insert(int data) {
		// 1. find the data index
		int index = 0;
		for (index = 0; index < currentIndex; index++) {
			if (datas[index] >= data) { // 存放重复值
				break;
			}
		}
		// 2. move bigger data
		// 从移除的开始 到他当前的索引
		// |------|
		// |------|
		// |------|
		// |------|<---index
		// |------|
		// |------|<---currentindex
		for (int i = currentIndex; i > index; i--) {
			datas[i] = datas[i - 1];
		}
		// 3. set data to index
		datas[index] = data;
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
		if (index == datas.length) {
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

	public int binarySearch(int data) {

		int index = -1;
		//低的
		int lowIndex = 0;  
		//大的索引
		int highIndex = currentIndex - 1;

		while (true) {
			index = (lowIndex + highIndex) / 2;
			if (lowIndex > highIndex) {
				return -1;
			}
			if (datas[index] == data) {
				return index;
			} else {
				//如果大了
				// |1|2|3|4|5|6|7|8|9|
				//          ^ 中间
				//     比他大               ^ 
				//                 ^   每次都是从结果的中间开始找
			
				if (datas[index] < data) {
					lowIndex = index +1;
					
				} else {
					highIndex = index - 1;
				}
			}
		}
	}

	public void printdata() {
		System.out.println("--------------------");
		for (int i = 0; i < currentIndex; i++) {
			System.out.println(datas[i]);
		}
	}

	public static void main(String[] args) {
		//有序查找比较快   插入慢
		//无序插人快 查找慢
		//删除都慢 都是需要将后面的数都移动位置
		OperateOrderNoIndex i = new OperateOrderNoIndex(20);
		i.insert(2);
		i.insert(2);
		i.insert(2);
		i.insert(3);
		i.insert(1);
		i.insert(11);
		i.insert(23);
		i.insert(51);
		i.insert(6);
		i.insert(22);
		i.insert(8);
		i.insert(9);
		i.printdata();
		System.out.println( "----------------");
		System.out.println(i.binarySearch(8));

	}
}
