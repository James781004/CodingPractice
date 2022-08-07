package 程序员代码面试指南.ch07;

public class Q01_SwapWithoutTmp {
    //    不用额外变量交换两个整数的值
//    描述
//    不用额外变量交换两个整数的值。

    public static void main(String[] args) {
        int a = 16;
        int b = 111;
        System.out.println(a);
        System.out.println(b);

        // 異或運算交換律和結合律：
        // a ^ b = b ^ a，交換律
        // (a ^ b) ^ c = a ^ (b ^ c)
        //
        // 本題思路
        // 先令a = a ^ b
        // 再執行b = b ^ a，其中a是 最初的a ^ b，則 b = b ^ (a ^ b)，由交換律和結合律，b = 最初的a
        // 再執行a = a ^ b，其中，a是最初的a ^ b，b是最初的a，因此，a  = (a ^ b) ^ a，即，a=最初的b
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println(a);
        System.out.println(b);
    }
}
