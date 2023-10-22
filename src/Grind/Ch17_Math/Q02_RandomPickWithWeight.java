package Grind.Ch17_Math;

import java.util.Random;

public class Q02_RandomPickWithWeight {
    class Solution {
        private Random random = new Random();
        private int[] preSum;

        public Solution(int[] w) {
            this.preSum = new int[w.length];
            this.preSum[0] = w[0];
            for (int i = 1; i < w.length; i++) { // 計算前綴和
                this.preSum[i] = this.preSum[i - 1] + w[i];
            }
        }

        // 二分優化
        public int pickIndex() {
            int max = this.preSum[this.preSum.length - 1];
            int rand = random.nextInt(max) + 1; // 注意，生成的隨機數不能包含0，否則部分用例過不了
            return binarySearch(preSum, rand);
        }

//        public int pickIndex() {
//            int max = this.preSum[this.preSum.length - 1];
//            // 注意，生成的隨機數不能包含0，否則部分用例過不了
//            int rand = random.nextInt(max) + 1;
//
//            for (int i = 0; i < preSum.length; i++) {
//                // 判斷rand的范圍落在哪個區間
//                if (rand <= preSum[i]) {
//                    return i;
//                }
//            }
//
//            return 0;
//        }

        private int binarySearch(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (target <= nums[mid]) { // 考慮等於的情況，去掉等號過不了所有用例
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return right;
        }
    }
}
