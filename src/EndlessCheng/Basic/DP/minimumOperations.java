package EndlessCheng.Basic.DP;

import java.util.ArrayList;
import java.util.List;

public class minimumOperations {

    // https://leetcode.cn/problems/sorting-three-groups/solutions/2396466/liang-chong-fei-bao-li-zuo-fa-liszhuang-38zac/
    // 最長非遞減子序列
    public int minimumOperations(List<Integer> nums) {
        List<Integer> g = new ArrayList<>();
        for (int x : nums) {
            int j = upperBound(g, x);
            if (j == g.size()) g.add(x);
            else g.set(j, x);
        }
        return nums.size() - g.size();
    }

    // 開區間寫法
    private int upperBound(List<Integer> g, int target) {
        int left = -1, right = g.size(); // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] <= target
            // nums[right] > target
            int mid = (left + right) >>> 1;
            if (g.get(mid) <= target)
                left = mid; // 范圍縮小到 (mid, right)
            else
                right = mid; // 范圍縮小到 (left, mid)
        }
        return right; // 或者 left+1
    }


    // 狀態機 DP
    // 定義 f[i+1][j] 表示考慮 nums[0] 到 nums[i]，且 nums[i] 變成 j 的最小修改次數 (第一個維度 i 可以省略)
    public int minimumOperations2(List<Integer> nums) {
        var f = new int[4];
        for (int x : nums) {
            for (int j = 3; j > 0; j--) { // 為了避免狀態被覆蓋，需要倒序枚舉
                for (int k = 1; k <= j; k++)
                    f[j] = Math.min(f[j], f[k]);
                if (j != x) f[j]++;
            }
        }
        int ans = nums.size();
        for (int j = 1; j < 4; j++)
            ans = Math.min(ans, f[j]);
        return ans;
    }

    // 計算至多保留多少個元素: f[j]=max(f[j],f[j−1])+[j=nums[i]]
    public int minimumOperations3(List<Integer> nums) {
        var f = new int[4];
        for (int x : nums) {
            f[x]++;
            f[2] = Math.max(f[2], f[1]);
            f[3] = Math.max(f[3], f[2]);
        }
        return nums.size() - Math.max(Math.max(f[1], f[2]), f[3]);
    }


}
