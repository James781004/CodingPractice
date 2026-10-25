package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

public class MaximumTripletValue {

    // https://leetcode.cn/problems/maximum-value-of-an-ordered-triplet-ii/solutions/2464857/mei-ju-jzhao-qian-hou-zui-da-zhi-pythonj-um8q/
    public long maximumTripletValue(int[] nums) {
        long ans = 0;
        int maxDiff = 0;
        int preMax = 0;
        for (int x : nums) {
            ans = Math.max(ans, (long) maxDiff * x);
            maxDiff = Math.max(maxDiff, preMax - x);
            preMax = Math.max(preMax, x);
        }
        return ans;
    }


}
