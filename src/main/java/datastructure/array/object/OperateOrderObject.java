package datastructure.array.object;

//T = K*N/2   无序的 T =K       二分法
public class OperateOrderObject {
	private UserModel[] objects = null;
	private int currentIndex = 0;

	public OperateOrderObject(int length) {
		objects = new UserModel[length];
	}

	public int insert(UserModel data) {
		// 1. find the data index
		int index = 0;
		for (index = 0; index < currentIndex; index++) {
			if (objects[index].getUuid() > data.getUuid()) { // 存放重复值
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
			objects[i] = objects[i - 1];
		}
		// 3. set data to index
		objects[index] = data;
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
		if (index == objects.length) {
			objects[index] = null;
		}
		for (int i = index; i < currentIndex - 1; i++) {
			objects[i] = objects[i + 1];
		}
		currentIndex--;
	}

	public UserModel searchOne(int uuid) {

		return objects[uuid];
	}

	public int binarySearch(int uuid) {

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
			if (objects[index].getUuid() == uuid) {
				return index;
			} else {
				//如果大了
				// |1|2|3|4|5|6|7|8|9|
				//          ^ 中间
				//     比他大               ^ 
				//                 ^   每次都是从结果的中间开始找
			
				if (objects[index].getUuid() < uuid) {
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
			System.out.println(objects[i].toString());
		}
	}

	public static void main(String[] args) {
		//有序查找比较快   插入慢
		//无序插人快 查找慢
		//删除都慢 都是需要将后面的数都移动位置
		OperateOrderObject i = new OperateOrderObject(20);
		i.insert(new UserModel(5));
		i.insert(new UserModel(3));
		i.insert(new UserModel(2));
		i.insert(new UserModel(6));
		i.insert(new UserModel(8));
		i.insert(new UserModel(11));
		i.insert(new UserModel(6));
		i.printdata();
		i.remove(5);
		System.out.println( "----------------");
		i.printdata();
		System.out.println(i.binarySearch(6));

	}
}
