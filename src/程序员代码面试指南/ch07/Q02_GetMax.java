package 程序员代码面试指南.ch07;

public class Q02_GetMax {
//    不用做任何比较判断运算符找出两个整数中的较大的值
//    描述
//    给定两个32位整数a和b，返回a和b中较大的，要求不能用任何比较判断运算符。

    public static int flip(int n) {
        return n ^ 1; // 如果n為0，返回1。如果n為1，返回0
    }

    public static int sign(int n) {
        return flip((n >> 31) & 1); // 負數返回0，正數返回1
    }

    public static int getMax1(int a, int b) {
        int c = a - b;
        int scA = sign(c);
        int scB = flip(scA);

        // scA和scB必是一個1一個0，看最後剩下誰即可
        return a * scA + b * scB;
    }

    public static int getMax2(int a, int b) {
        int c = a - b;
        int sa = sign(a);     // a的符號
        int sb = sign(b);     // b的符號
        int sc = sign(c);     // c的符號
        int difSab = sa ^ sb;  // a與b符號不同為1，反之為0
        int sameSab = flip(difSab); // a與b符號相同為1，反之為0
        int returnA = difSab * sa + sameSab * sc; // 符號相同時a大，sc=1，b大sc=0，符號不同時a為正就返回a
        int returnB = flip(returnA); // 與returnA互斥，返回a就不返回b，返回b就不返回a
        return a * returnA + b * returnB;
    }

    public static void main(String[] args) {
        int a = -16;
        int b = 1;
        System.out.println(getMax1(a, b));
        System.out.println(getMax2(a, b));
        a = 2147483647;
        b = -2147480000;
        System.out.println(getMax1(a, b)); // wrong answer because of overflow
        System.out.println(getMax2(a, b));

    }

}
