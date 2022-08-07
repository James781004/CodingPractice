package 程序员代码面试指南.ch05;

public class Q16_ZeroLeftOneStringNumber {
//    0左边必有1的二进制字符串的数量
//    描述
//    给定一个整数n，求由“0”字符和“1”字符组成的长度为n的所有字符串中，满足“0”字符的左边必有“1”字符的字符串的数量。
//    示例1
//    输入：
//            1
//    输出：
//            1
//    说明：
//    只有“1”满足
//            示例2
//    输入：
//            2
//    输出：
//            2
//    说明：
//    只有“10”和“11”满足
//            示例3
//    输入：
//            3
//    输出：
//            3
//    说明：
//    只有“101”，“110”，“111”满足

    // 實際上就是斐波那契
    public static int getNum1(int n) {
        if (n < 1) {
            return 0;
        }
        return process(1, n);
    }


    // 設f(i)表示長度為i的【0左邊必有1的二進制字元串的數量】：
    // 先確定第一個字元，顯然首個字元必須為1（如果為0則左邊沒有1給它靠了），所以f(1) = 1；
    // 第二個字元既可以為0也可以為1，即10和11兩個，所以f(2) = 2；
    // 如果第二個字元選了0（即前兩位是10），那麼第三個字元只能是1即101（因為0的左邊必須緊挨著一個1，所以沒得選）；
    // 如果第二個字元選了1（即前兩位是11），那麼第三個字元既可以是0也可以是1（110或111），所以f(3) = 1+2 = 3；
    // 遵循這種規則做下去，你會發現f(i)總是等於f(i-1) + f(i-2)，也就是典型的斐波那契數列了，只不過這里f(1) = 1，f(2) = 2。
    // 搞懂了這個邏輯，編碼就是水到渠成了。
    //
    // 我們記函數F(i)可以返回長度為i的二進制字元串中滿足題意的數量，
    // 調用函數F的條件是第一個字元必須為1（滿足題意的二進制字元串的首字元一定是1，因為如果是0，則這個0的左邊就沒有1給它靠著了）。
    // 以i=8為例調用F(8)，如果第二個字元是1，那從第二個字元開始可以調用F(7)，
    // 如果第二個字元是0，那第三個字元肯定是1，否則第三個0左邊就沒有1給它靠著了，
    // 於是可以調用F(6)，得到F(8)=F(7)+F(6)。
    public static int process(int i, int n) {
        if (i == n - 1) {
            return 2;
        }
        if (i == n) {
            return 1;
        }
        return process(i + 1, n) + process(i + 2, n);
    }

    public static int getNum2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int pre = 1;
        int cur = 1;
        int tmp = 0;
        for (int i = 2; i < n + 1; i++) {
            tmp = cur;
            cur += pre;
            pre = tmp;
        }
        return cur;
    }

    public static int getNum3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] tmp = m;
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = muliMatrix(res, tmp);
            }
            tmp = muliMatrix(tmp, tmp);
        }
        return res;
    }

    public static int[][] muliMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        for (int i = 0; i != 20; i++) {
            System.out.println(getNum1(i));
            System.out.println(getNum2(i));
            System.out.println(getNum3(i));
            System.out.println("===================");
        }

    }
}
