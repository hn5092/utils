package datastructure.stack;

import org.junit.Test;

/**
 * 1.从左到右顺序读取表达式中的字符 2.是操作数,复制到后缀表达式字符串 3.是左括号把字符压入栈中
 * 4.是有括号,从栈中弹出符号到后缀表达式,知道遇到左括号然后把左括号弹出
 * 5.是操作符,如果此时栈顶操作符优先级大于等于此操作符,弹出栈顶操作符到后缀表达式,之大发现优先级更低的元素位置,将操作符压入
 * 6.读到输入的末尾,将栈元素弹出知道栈变成空栈,降幅好写到后缀表达式中
 */
public class PostInfix {
	/**
	 * 把一个中缀表达式转换成为后缀表达式
	 * 
	 * @param str
	 *            需要转换的中缀表达式
	 * @return 转换完成的后缀表达式
	 */
	public String doTransfer(String str) {
		StringBuffer buffer = new StringBuffer();
		MyStack myStack = new MyStack(20);
		char[] cs = str.toCharArray();
		int length = cs.length;
		for (int i = 0; i < length; i++) {
			char c = (char) cs[i];
			// 1. 如果是操作符,就要分级别操作
			if (c == '+' || c == '-') {
				doOperation(myStack, buffer, c, 1);
			} else if (c == '*' || c == '/') {
				doOperation(myStack, buffer, c, 2);
				// 如果是左括号,压榨
			} else if (c == '(') {
				myStack.push(c);
				// 如果是右括号 弹栈道输出中知道遇到(
			} else if (c == ')') {
				doRightBraket(buffer, myStack, c);
			} else {
				buffer.append(c);
			}

		}
		// 把栈中操作符依次弹出
		while (!myStack.isEmpty()) {
			buffer.append(myStack.pop());
		}

		return buffer.toString();
	}

	/**
	 * 处理右括号
	 * 
	 * @param myStack
	 * @param buffer
	 * 
	 * @param c
	 */
	private void doRightBraket(StringBuffer buffer, MyStack myStack, char c) {
		// TODO Auto-generated method stub
		while (!myStack.isEmpty()) {
			char topC = (char) myStack.pop();
			if (topC == '(') {
				break;
			} else {
				buffer.append(topC);
			}

		}

	}

	/**
	 * 按照级别处理操作符
	 * 
	 * @param myStack
	 * @param buffer
	 * @param c
	 * @param i
	 */
	private void doOperation(MyStack myStack, StringBuffer buffer, char c,
			int level) {
		// 依次从栈顶获取一个值
		while (!myStack.isEmpty()) {
			char topC = (char) myStack.pop();
			// 如果是(不懂
			if (topC == '(') {
				myStack.push(topC);
				break;
			} else {
				// 判断获取到栈顶的元素优先级别
				int topLevel = 0;
				if (topC == '+' || topC == '-') {
					topLevel = 1;
				} else {
					topLevel = 2;
				}
				if (topLevel >= level) {
					// 2.2如果栈顶的优先级别大于等于传入的数据级别
					buffer.append(topC);
				} else {
					myStack.push(topC);
					break;
				}

			}

		}
		myStack.push(c);
	}
	@Test
	public void testPost(){
		PostInfix postInfix = new PostInfix();
		String ret = doTransfer("(3+2)/5-((7+8)*4-5)");
		System.out.println(ret);
	}
}

