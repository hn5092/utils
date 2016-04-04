package datastructure.stack;

public class MyStack {
	private int[] datas;
	private int topIndex = -1;

	public MyStack(int length) {
		datas = new int[length];

	}
	public void push(int d){
		topIndex++;
		datas[topIndex] = d;
	}
	public int pop (){
		int ret = datas[topIndex];
		topIndex-- ; 
		return ret;
	}
	public int peek(){
		
		return datas[topIndex];
	}
	public boolean isEmpty(){
		return topIndex == -1;
	}
	public boolean isFull(){
		return topIndex == (datas.length -1);
	}
	public void printlnStack() {
		System.out.println("====================");
		for(int i  = 0 ; i <= topIndex ; i++){
			System.out.println(datas[i]);
		}
	}
	public static void main(String[] args) {
		MyStack myStack = new MyStack(10);
		myStack.push(10);
		myStack.push(13);
		myStack.push(15);
		myStack.push(17);
		int ret = myStack.peek();
		System.out.println(ret + " is ret");
		myStack.printlnStack();
		int ret2 = myStack.pop();
		System.out.println(ret2 + " is ret");
		myStack.printlnStack();
	}
}
