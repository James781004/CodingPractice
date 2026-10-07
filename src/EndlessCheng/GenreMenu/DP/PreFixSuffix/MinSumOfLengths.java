package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MinSumOfLengths {

    // https://leetcode.cn/problems/find-two-non-overlapping-sub-arrays-each-with-target-sum/solutions/4022830/liang-chong-fang-fa-qian-hou-zhui-fen-ji-pm1t/
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        // sufMin[i] 表示左端點 >= i 的和為 target 的最短子數組長度
        // 不存在子數組時，長度設為 n+1
        int[] sufMin = new int[n];
        int minLen = n + 1;
        int sum = 0;
        int r = n - 1;
        for (int l = n - 1; l > 0; l--) {
            sum += arr[l];
            while (sum > target) {
                sum -= arr[r];
                r--;
            }
            if (sum == target) {
                minLen = Math.min(minLen, r - l + 1); // 維護遍歷過的和為 target 的子數組的最短長度
            }
            sufMin[l] = minLen; // 保存此時此刻的 minLen
        }

        int ans = n + 1;
        sum = 0;
        int l = 0;
        for (r = 0; r < n - 1; r++) {
            sum += arr[r];
            while (sum > target) {
                sum -= arr[l];
                l++;
            }
            if (sum == target) {
                ans = Math.min(ans, r - l + 1 + sufMin[r + 1]);
            }
        }

        return ans > n ? -1 : ans;
    }

}
