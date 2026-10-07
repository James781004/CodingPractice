package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class SmallestBalancedIndex {

    // https://leetcode.cn/problems/find-the-smallest-balanced-index/solutions/3917772/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-yizq/
    public int smallestBalancedIndex(int[] nums) {
        int n = nums.length;
        long sum = 0; // sum 表示 [0,i-1] 之和
        for (int i = 0; i < n - 1; i++) {
            sum += nums[i];
        }

        long mul = 1; // [i+1,n-1] 之積
        for (int i = n - 1; i > 0; i--) {
            if (sum == mul) { // [0,i-1] 之和等於 [i+1,n-1] 之積
                return i;
            }
            sum -= nums[i - 1]; // 倒序遍歷的過程中減去遍歷的數

            // 如果 mul * nums[i] > sum[i-1]，那麼繼續向左遍歷，
            // mul 越來越大（或者不變），sum 越來越小，不可能找到答案
            // 為避免乘法溢出，改成等價的除法
            if (mul > sum / nums[i]) {
                break;
            }
            mul *= nums[i];
        }
        return -1;
    }

}
