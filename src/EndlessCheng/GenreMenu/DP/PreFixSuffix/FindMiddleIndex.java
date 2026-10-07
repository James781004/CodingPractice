package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class FindMiddleIndex {

    // https://leetcode.cn/problems/find-the-middle-index-in-array/solutions/978660/go-mo-ni-by-endlesscheng-tpc5/
    public int findMiddleIndex(int[] nums) {
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
