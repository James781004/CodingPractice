package 程序员代码面试指南.ch09;

public class Q02_GCD {
//    CD55 一行代碼求兩個數的最大公約數
//    描述
//    給定兩個不等於0的整數M和N，求M和N的最大公約數。

    // 輾轉相除法
    public static int gcd(int m, int n) {
        return n == 0 ? m : gcd(n, m % n);
    }

    public static void main(String[] args) {

        System.out.println(gcd(18, 27));

    }
}
