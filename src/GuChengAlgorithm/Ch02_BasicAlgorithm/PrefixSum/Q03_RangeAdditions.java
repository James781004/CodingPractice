package GuChengAlgorithm.Ch02_BasicAlgorithm.PrefixSum;

public class Q03_RangeAdditions {
    // https://docs.google.com/presentation/d/14oHMlIK-GXLlADlJIcvPcIx_cHTKWcOv-sOve5L2Aqw/edit#slide=id.ge3eafaac94_1_30
    // lazy propagation
    // 如果有一整個區間要加上同一個數k，就可以在起點加上k，終點後一位減去k，然後滾動更新時進行計算即可
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] res = new int[length];
        for (int[] update : updates) {
            int value = update[2];
            int start = update[0];
            int end = update[1];
            res[start] += value;
            if (end < length - 1) res[end + 1] -= value;
        }
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += res[i];
            res[i] = sum;
        }
        return res;
    }


    // https://docs.google.com/presentation/d/14oHMlIK-GXLlADlJIcvPcIx_cHTKWcOv-sOve5L2Aqw/edit#slide=id.ge3eafaac94_1_42
    class NumMatrix {
        int[][] sums;

        public NumMatrix(int[][] matrix) {
            int row = matrix.length, col = matrix[0].length;
            sums = new int[row + 1][col + 1];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    // 區域和(正方形右下角數字) = 正方形上半部 + 正方形左半部 + matrix[i][j] - 先前算過的正方形左上半部
                    sums[i + 1][j + 1] = sums[i][j + 1] + sums[i + 1][j] + matrix[i][j] - sums[i][j];
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            // 小正方形 = 大正方形 - 大正方形上半部 - 大正方形左半部 + 大正方形左上半部
            return sums[row2 + 1][col2 + 1] - sums[row1][col2 + 1] - sums[row2 + 1][col1] + sums[row1][col1];
        }
    }
}
