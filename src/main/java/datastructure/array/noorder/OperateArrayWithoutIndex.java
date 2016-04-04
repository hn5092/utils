package datastructure.array.noorder;
/**
 * 用来演示操作无序数组时 直接使用数据   可以存放重复值
 * 
 * @author imad
 *
 */
public class OperateArrayWithoutIndex {
	private int[] datas = null;
	private int currentIndex = 0;

	public OperateArrayWithoutIndex(int length) {
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
		int index = getIndex(data);
		if(index != -1){
			if (index == datas.length){
				datas[index] = 0;
			}
			for (int i = index; i < currentIndex - 1; i++) {
				datas[i] = datas[i + 1];
			}
			currentIndex--;
		}
		
	}
	private int getIndex(int data){
		int index = -1;
		for(int i = 0; i < currentIndex; i ++){
			if (datas[i] == data){
				return i;
			}
		}
		return index;
	}

	public int searchOne(int data) {
		int index = getIndex(data);
		if(index != -1){
			
			return index;
		}
		return 0;
	}

	public void printdata() {
		System.out.println("--------------------");
		for (int i = 0; i < currentIndex; i++) {
			System.out.println(datas[i]);
		}
	}
	public static void main(String[] args) {
		OperateArrayWithoutIndex i = new OperateArrayWithoutIndex(10);
		i.insert(1);
		i.insert(2);
		i.insert(2);
		i.insert(3);
		i.insert(4);
		i.insert(5);
		i.printdata();
		i.remove(3);
		i.printdata();
		System.out.println("find datas[5] : "+i.searchOne(3));

	}
}
