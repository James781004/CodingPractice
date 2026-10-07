package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class PivotIndex {

    // https://leetcode.cn/problems/find-pivot-index/solutions/2834687/jian-ji-xie-fa-o1-e-wai-kong-jian-python-tz0p/
    public int pivotIndex(int[] nums) {
        int s = 0;
        for (int num : nums) {
            s += num;
        }
        int leftS = 0;
        for (int i = 0; i < nums.length; i++) {
            if (leftS * 2 == s - nums[i]) {
                return i;
            }
            leftS += nums[i];
        }
        return -1;
    }

}
