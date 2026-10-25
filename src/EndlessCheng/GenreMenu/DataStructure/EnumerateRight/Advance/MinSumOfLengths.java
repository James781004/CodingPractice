package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

public class MinSumOfLengths {

    // https://leetcode.cn/problems/find-two-non-overlapping-sub-arrays-each-with-target-sum/solutions/4022830/liang-chong-fang-fa-qian-hou-zhui-fen-ji-pm1t/
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int ans = n + 1;
        // preMin[i] 表示右端點 < i 的和為 target 的最短子數組長度
        // 不存在子數組時，長度設為 n+1
        int[] preMin = new int[n + 1];
        preMin[0] = n + 1;
        int minLen = n + 1;
        int sum = 0;
        int l = 0;

        for (int r = 0; r < n; r++) {
            sum += arr[r];
            while (sum > target) {
                sum -= arr[l];
                l++;
            }
            if (sum == target) {
                // 枚舉第二個子數組的右端點為 r，用滑動窗口算出此時第二個子數組的左端點為 l
                // 那麼第一個子數組的右端點必須 < l
                ans = Math.min(ans, preMin[l] + r - l + 1);
                minLen = Math.min(minLen, r - l + 1); // 維護遍歷過的和為 target 的子數組的最短長度
            }
            preMin[r + 1] = minLen; // 保存此時此刻的 minLen
        }

        return ans > n ? -1 : ans;
    }

}
