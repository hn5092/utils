package base.bit;

import org.junit.Test;

/**
 * Created by hadoop on 4/27/2016.
 * & 两个都为1 结果才是1
 * | 两个都为0  结果才是0
 * ^ 两个位相同是0 相异为1
 * ~ 取反 0->1 1->0
 * << 左移 低位补0
 * >> 右移  高位补0
 */
public class TestCount {
//    // 1. 获得int型最大值
//    System.out.println((1 << 31) - 1);// 2147483647， 由于优先级关系，括号不可省略
//    System.out.println(~(1 << 31));// 2147483647
//
//// 2. 获得int型最小值
//    System.out.println(1 << 31);
//    System.out.println(1 << -1);
//
//// 3. 获得long类型的最大值
//    System.out.println(((long)1 << 127) - 1);
//
//// 4. 乘以2运算
//    System.out.println(10<<1);
//
//// 5. 除以2运算(负奇数的运算不可用)
//    System.out.println(10>>1);
//
//// 6. 乘以2的m次方
//    System.out.println(10<<2);
//
//// 7. 除以2的m次方
//    System.out.println(16>>2);
//
//// 8. 判断一个数的奇偶性
//    System.out.println((10 & 1) == 1);
//    System.out.println((9 & 1) == 1);
//
//// 9. 不用临时变量交换两个数（面试常考）
//    a ^= b;
//    b ^= a;
//    a ^= b;
//
//    // 10. 取绝对值（某些机器上，效率比n>0 ? n:-n 高）
//    int n = -1;
//    System.out.println((n ^ (n >> 31)) - (n >> 31));
///* n>>31 取得n的符号，若n为正数，n>>31等于0，若n为负数，n>>31等于-1
//若n为正数 n^0-0数不变，若n为负数n^-1 需要计算n和-1的补码，异或后再取补码，
//结果n变号并且绝对值减1，再减去-1就是绝对值 */
//
//// 11. 取两个数的最大值（某些机器上，效率比a>b ? a:b高）
//    System.out.println(b&((a-b)>>31) | a&(~(a-b)>>31));
//
//// 12. 取两个数的最小值（某些机器上，效率比a>b ? b:a高）
//    System.out.println(a&((a-b)>>31) | b&(~(a-b)>>31));
//
//// 13. 判断符号是否相同(true 表示 x和y有相同的符号， false表示x，y有相反的符号。)
//    System.out.println((a ^ b) > 0);
//
//// 14. 计算2的n次方 n > 0
//    System.out.println(2<<(n-1));
//
//// 15. 判断一个数n是不是2的幂
//    System.out.println((n & (n - 1)) == 0);
///*如果是2的幂，n一定是100... n-1就是1111....
//所以做与运算结果为0*/
//
//// 16. 求两个整数的平均值
//    System.out.println((a+b) >> 1);
//
//    // 17. 从低位到高位,取n的第m位
//    int m = 2;
//    System.out.println((n >> (m-1)) & 1);
//
//// 18. 从低位到高位.将n的第m位置为1
//    System.out.println(n | (1<<(m-1)));
///*将1左移m-1位找到第m位，得到000...1...000
//n在和这个数做或运算*/
//
//// 19. 从低位到高位,将n的第m位置为0
//    System.out.println(n & ~(0<<(m-1)));
//    /* 将1左移m-1位找到第m位，取反后变成111...0...1111
//    n再和这个数做与运算*/
    @Test
    public void testEvenNumber() {
        for (int i = 0; i < 100; i++) {
            //都为1才是1
            if ((i & 1) == 0) { // 偶数
                System.out.println(i);
            }
//            int i1 = i | 2;
//            System.out.println(i1 +"this is i");
//            if (i1 == 0) {
//                System.out.println(i);
//            }
        }
    }

    @Test
    public void swap() {
        int c = 1, d = 2;
        c ^= d;
        d ^= c;
        c ^= d;
        System.out.println("c=" + c);
        System.out.println("d=" + d);
    }

    @Test
    public void transfer() {
        int a = -15, b = 15;
        System.out.println(~a + 1);
        System.out.println(~b + 1);
    }

    @Test
    public void abs() {
        int a = 1;
        System.out.println(a >> 31 == 0 ? a : (~a + 1));
        System.out.println((a ^ (a >> 31)) - (a >> 31));
    }


}
