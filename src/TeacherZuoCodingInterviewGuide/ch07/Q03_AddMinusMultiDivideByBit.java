package TeacherZuoCodingInterviewGuide.ch07;

public class Q03_AddMinusMultiDivideByBit {
//    不用算术运算符实现整数的加减乘除运算
//    描述
//    给定两个32位整数a和b。要求不使用算术运算符，分别实现a和b的加减乘除运算。
//    如果给定的a和b执行加减乘除的某些结果本来就会导致数据的溢出，
//    那么你实现的函数不需要对那些结果负责（你的输出和加减乘除溢出的结果保持一致就行）。

    // 加法：兩個數的“異或”為無進位和，
    // 兩個數的“與”再左移一位為進位數，
    // 當進位不為0時反復調用無進位和加上進位就能夠通過位運算算出加法結果；
    public static int add(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
        return sum;
    }

    public static int negNum(int n) {
        return add(~n, 1);
    }

    // 減法：直接用第一個數加上第二個數的相反數就行，一個數的相反數為其取反再加1。
    public static int minus(int a, int b) {
        return add(a, negNum(b));
    }

    // 乘法：用豎式模擬，假設有x和y兩個數，從右往左逐位檢查y的各位是否為1（通過y的右移來完成這個檢查），
    // 為1時就將x累加到結果上，由於在豎式乘法中橫線等號下方的數會向左不斷偏移一位來排列，然後通過豎式加法計算得到乘法結果。
    // 因此x需要隨著y的右移而左移。
    public static int multi(int a, int b) {
        int res = 0;
        while (b != 0) {
            if ((b & 1) == 0) {
                res = add(res, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return res;
    }

    public static boolean isNeg(int n) {
        return n < 0;
    }

    public static int div(int a, int b) {
        // 先把兩個數轉成正數
        int x = isNeg(a) ? negNum(a) : a;
        int y = isNeg(b) ? negNum(b) : b;
        int res = 0;
        for (int i = 31; i > -1; i = minus(i, 1)) {
            if ((x >> i) >= y) {
                res |= (1 << i);   // 將res從右往左的第i位標記為1
                x = minus(x, y << i); // x減去y向左移i位的結果
            }
        }

        // 同號直接返回結果，異號返回相反數
        return isNeg(a) ^ isNeg(b) ? negNum(res) : res;
    }

    // 除法：為乘法的逆向操作，用x自底向上減去乘法豎式橫線等號下方的數，但需要考慮符號的問題。
    public static int divide(int a, int b) {
        if (b == 0) {
            throw new RuntimeException("divisor is 0");
        }
        if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
            return 1;
        } else if (b == Integer.MIN_VALUE) {
            return 0;
        } else if (a == Integer.MIN_VALUE) {
            int res = div(add(a, 1), b);
            return add(res, div(minus(a, multi(res, b)), b));
        } else {
            return div(a, b);
        }
    }

    public static void main(String[] args) {
        int a = (int) (Math.random() * 100000) - 50000;
        int b = (int) (Math.random() * 100000) - 50000;
        System.out.println("a = " + a + ", b = " + b);
        System.out.println(add(a, b));
        System.out.println(a + b);
        System.out.println("=========");
        System.out.println(minus(a, b));
        System.out.println(a - b);
        System.out.println("=========");
        System.out.println(multi(a, b));
        System.out.println(a * b);
        System.out.println("=========");
        System.out.println(divide(a, b));
        System.out.println(a / b);
        System.out.println("=========");

        a = Integer.MIN_VALUE;
        b = 32;
        System.out.println(divide(a, b));
        System.out.println(a / b);

    }
}
