package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class SplitArray {

    // https://leetcode.cn/problems/split-array-with-minimum-difference/solutions/3794077/yi-ci-bian-li-pythonjavacgo-by-endlessch-iysg/
    public long splitArray(int[] nums) {
        int n = nums.length;
        // 最長嚴格遞增前綴
        long pre = nums[0];
        int i = 1;
        while (i < n && nums[i] > nums[i - 1]) {
            pre += nums[i];
            i++;
        }

        // 最長嚴格遞減後綴
        long suf = nums[n - 1];
        int j = n - 2;
        while (j >= 0 && nums[j] > nums[j + 1]) {
            suf += nums[j];
            j--;
        }

        // 情況一
        if (i <= j) {
            return -1;
        }

        long d = pre - suf;
        // 情況二
        if (i - 1 == j) {
            return Math.abs(d);
        }

        // 情況三，suf 多算了一個 nums[i-1]，或者 pre 多算了一個 nums[i-1]
        return Math.min(Math.abs(d + nums[i - 1]), Math.abs(d - nums[i - 1]));
    }

}
