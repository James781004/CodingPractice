package FuckingAlgorithm.Arrays;

public class Q04_NumArray {
//    https://leetcode.cn/problems/range-sum-query-immutable/
//    303. 區域和檢索 - 數組不可變
//    給定一個整數數組  nums，處理以下類型的多個查詢:
//
//    計算索引 left 和 right （包含 left 和 right）之間的 nums 元素的 和 ，其中 left <= right
//    實現 NumArray 類：
//
//    NumArray(int[] nums) 使用數組 nums 初始化對象
//    int sumRange(int i, int j) 返回數組 nums 中索引 left 和 right 之間的元素的 總和 ，包含 left 和 right 兩點（也就是 nums[left] + nums[left + 1] + ... + nums[right] )

    class NumArray {
//        private int[] nums;
//
//        public NumArray(int[] nums) {
//            this.nums = nums;
//        }
//
//        //  O(N)
//        public int sumRangeTest(int left, int right) {
//            int res = 0;
//            for (int i = left; i < right; i++) {
//                res += nums[i];
//            }
//            return res;
//        }

        // 前綴和數組
        private int[] preSum;

        public NumArray(int[] nums) {
            // preSum[0] = 0，便於計算累加和
            preSum = new int[nums.length + 1];
            // 計算 nums 的累加和
            for (int i = 1; i < preSum.length; i++) {
                preSum[i] = preSum[i - 1] + nums[i - 1];
            }
        }

        public int sumRange(int left, int right) {
            return preSum[right + 1] - preSum[left];
        }
    }


//    https://leetcode.cn/problems/range-sum-query-2d-immutable/
//    304. 二維區域和檢索 - 矩陣不可變
//    給定一個二維矩陣 matrix，以下類型的多個請求：
//
//    計算其子矩形范圍內元素的總和，該子矩陣的 左上角 為 (row1, col1) ，右下角 為 (row2, col2) 。
//    實現 NumMatrix 類：
//
//    NumMatrix(int[][] matrix) 給定整數矩陣 matrix 進行初始化
//    int sumRegion(int row1, int col1, int row2, int col2) 返回 左上角 (row1, col1) 、右下角 (row2, col2) 所描述的子矩陣的元素 總和 。

    class NumMatrix {
        private int[][] preSum;

        public NumMatrix(int[][] matrix) {
            int m = matrix.length, n = matrix[0].length;
            if (m == 0 || n == 0) return;
            // 構造前綴和矩陣
            preSum = new int[m + 1][n + 1];
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    // 計算每個矩陣 [0, 0, i, j] 的元素前綴和
                    // preSum左半部 + preSum上半部 + matrix[i - 1][j - 1]數值 - preSum左上半部
                    preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] + matrix[i - 1][j - 1] - preSum[i - 1][j - 1];
                }
            }
        }

        // 計算子矩陣 [x1, y1, x2, y2] 的元素和
        public int sumRegion(int x1, int y1, int x2, int y2) {
            // 目標矩陣之和由四個相鄰矩陣運算獲得
            return preSum[x2 + 1][y2 + 1] - preSum[x1][y2 + 1] - preSum[x2 + 1][y1] + preSum[x1][y1];
        }
    }

}
