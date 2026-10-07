package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MaximumValueSum {

    // https://leetcode.cn/problems/maximum-value-sum-by-placing-three-rooks-ii/solutions/2884186/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-gc48/
    public long maximumValueSum(int[][] board) {
        int n = board.length, m = board[0].length;
        // pre[i][0][0]記錄到第i行為止，第一大的數；pre[i][0][1]記錄第i行為止，第一大的數的列號，後面的後綴依此類推
        int[][][] pre = new int[n + 1][3][2];
        int[][][] suf = new int[n + 1][3][2];
        int[][] max3 = new int[3][2];
        for (int[] t : max3) {
            t[0] = Integer.MIN_VALUE;
        }
        // 計算前綴
        for (int i = 0; i < n - 2; i++) {
            update(board[i], max3);
            for (int j = 0; j < 3; j++) {
                pre[i][j][0] = max3[j][0];
                pre[i][j][1] = max3[j][1];
            }
        }
        // 重置臨時數組
        max3 = new int[3][2];
        for (int[] t : max3) {
            t[0] = Integer.MIN_VALUE;
        }
        // 計算後綴
        for (int i = n - 1; i > 1; i--) {
            update(board[i], max3);
            for (int j = 0; j < 3; j++) {
                suf[i][j][0] = max3[j][0];
                suf[i][j][1] = max3[j][1];
            }
        }

        long res = Long.MIN_VALUE;
        // 枚舉中間的車的行數
        for (int i = 1; i < n - 1; i++) {
            // 枚舉中間的車的列數
            for (int j = 0; j < m; j++) {
                // 枚舉第一個車前i - 1行中能達到的前三大的數
                for (int[] a : pre[i - 1]) {
                    // 枚舉第三個車後i + 1行中能達到的前三大的數
                    for (int[] b : suf[i + 1]) {
                        // 如果三個車不存在相同的列，則可以進行更新
                        if (a[1] != j && b[1] != j && a[1] != b[1]) {
                            res = Math.max(res, (long) a[0] + board[i][j] + b[0]);
                            // 因為都是從最大的開始更新的，如果能更新，就不用看更小的了
                            break;
                        }
                    }
                }
            }
        }
        return res;
    }

    public void update(int[] row, int[][] max3) {
        for (int i = 0; i < row.length; i++) {
            int x = row[i];
            // 如果比最大的大，則更新
            if (x > max3[0][0]) {
                // 如果和最大的列號不同，則可以更新到第二大（把原來最大的更新為第二大）
                if (max3[0][1] != i) {
                    // 如果和第三大的列號也不同，則三個都可以被更新（把原來第二大的更新為第三大）
                    if (max3[1][1] != i) {
                        max3[2] = max3[1];
                    }
                    max3[1] = max3[0];
                }
                max3[0] = new int[]{x, i};
            }
            // 如果無法更新最大，則看能否更新第二大和第三大
            else if (x > max3[1][0] && i != max3[0][1]) {
                if (max3[1][1] != i) {
                    max3[2] = max3[1];
                }
                max3[1] = new int[]{x, i};
            }
            // 如果最大和第二大更新不了，則看能夠更新第三大
            else if (x > max3[2][0] && i != max3[0][1] && i != max3[1][1]) {
                max3[2] = new int[]{x, i};
            }
        }
    }

}
