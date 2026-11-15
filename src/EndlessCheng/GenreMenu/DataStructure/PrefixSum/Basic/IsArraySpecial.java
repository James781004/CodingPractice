package EndlessCheng.GenreMenu.DataStructure.PrefixSum.Basic;

public class IsArraySpecial {

    // https://leetcode.cn/problems/special-array-ii/solutions/2782830/qian-zhui-he-wei-yun-suan-pythonjavacgo-3pvya/
    public boolean[] isArraySpecial(int[] nums, int[][] queries) {
        int[] s = new int[nums.length];
        for (int i = 1; i < nums.length; i++) {
            s[i] = s[i - 1] + (nums[i - 1] % 2 == nums[i] % 2 ? 1 : 0);
        }
        boolean[] ans = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            ans[i] = s[q[0]] == s[q[1]];
        }
        return ans;
    }

}
