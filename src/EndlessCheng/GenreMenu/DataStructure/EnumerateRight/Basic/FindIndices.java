package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

public class FindIndices {

    // https://leetcode.cn/problems/find-indices-with-index-and-value-difference-ii/solutions/2483143/shuang-zhi-zhen-wei-hu-zui-da-zui-xiao-p-h4bx/
    public int[] findIndices(int[] nums, int indexDifference, int valueDifference) {
        int maxIdx = 0;
        int minIdx = 0;
        for (int j = indexDifference; j < nums.length; j++) {
            int i = j - indexDifference;
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            } else if (nums[i] < nums[minIdx]) {
                minIdx = i;
            }
            if (nums[maxIdx] - nums[j] >= valueDifference) {
                return new int[]{maxIdx, j};
            }
            if (nums[j] - nums[minIdx] >= valueDifference) {
                return new int[]{minIdx, j};
            }
        }
        return new int[]{-1, -1};
    }

}
