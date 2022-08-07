package 程序员代码面试指南.ch08;

public class Q17_SubMatrixMaxSum {
//    CD27 子矩陣的最大累加和問題
//    描述
//    給定一個矩陣matrix，其中的值有正、有負、有0，返回子矩陣的最大累加和
//    例如，矩陣matrix為：
//            -90 48 78
//            64 -40 64
//            -81 - 7 66
//    其中，最大的累加和的子矩陣為：
//            48 78
//            -40 64
//            -7 66
//    所以返回累加和209。
//    例如，matrix為：
//            -1 -1 -1
//            -1 2 2
//            -1 -1 -1
//    其中，最大累加和的子矩陣為：
//            2 2
//    所以返回4
//[要求]
//    時間覆雜度為O(n^2m)，空間覆雜度為O(nm)


    public static int maxSum(int[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int cur = 0;
        int[] s = null; // 累加數組

        // 根據題意，把矩陣每一列全部加起來，就可以壓縮成一個累加數組
        // 這時候求數組最大累加和即可
        for (int i = 0; i != m.length; i++) {
            s = new int[m[0].length]; // loop完成換列計算時要重置累加數組
            for (int j = i; j != m.length; j++) {
                cur = 0; // 每次換列計算時要重置當前累加和
                for (int k = 0; k != s.length; k++) {
                    s[k] += m[j][k]; // 計算m[i...j][k]累計結果
                    cur += s[k];
                    max = Math.max(max, cur);
                    cur = cur < 0 ? 0 : cur;
                }
            }
        }

        return max;
    }

    public static void main(String[] args) {
        int[][] matrix = {{-90, 48, 78}, {64, -40, 64}, {-81, -7, 66}};
        System.out.println(maxSum(matrix));

    }
}
