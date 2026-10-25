package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

public class CountQuadruplets {

    // https://leetcode.cn/problems/count-special-quadruplets/solutions/1181794/gong-shui-san-xie-yi-ti-si-jie-mei-ju-ha-gmhv/
    public int countQuadruplets(int[] nums) {
        int n = nums.length, ans = 0;
        int[] cnt = new int[10010];
        for (int b = n - 3; b >= 1; b--) {
            for (int d = b + 2; d < n; d++) cnt[nums[d] - nums[b + 1] + 200]++;
            for (int a = 0; a < b; a++) ans += cnt[nums[a] + nums[b] + 200];
        }
        return ans;
    }


}
