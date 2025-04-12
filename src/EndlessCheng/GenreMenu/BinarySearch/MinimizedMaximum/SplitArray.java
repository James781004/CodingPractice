package EndlessCheng.GenreMenu.BinarySearch.MinimizedMaximum;

public class SplitArray {

    // https://leetcode.cn/problems/split-array-largest-sum/solutions/2613046/er-fen-da-an-fu-ti-dan-pythonjavacgojsru-n5la/
    // 由於數組的和是單調的，可以使用二分
    // 所有元素的和一定滿足條件
    public int splitArray(int[] nums, int k) {
        int sum = 0;
        int mx = 0;
        for (int x : nums) {
            sum += x;
            mx = Math.max(mx, x);
        }

        // 各自和的最大值,則一定至少是max(nums) - 1，開區間
        // 更好的寫法是考慮平均值 (sum - 1) / k，如果每一段的元素和都小於平均值，不可能分出 k 段
        int left = Math.max(mx - 1, (sum - 1) / k);
        int right = sum;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (check(nums, k, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    // 判斷能否劃分為k個數組
    private boolean check(int[] nums, int k, int mx) {
        int cnt = 1;
        int s = 0;

        // 因為子數組是連續的，所以只需要依次判斷就可以了
        for (int x : nums) {
            if (s + x <= mx) {
                s += x; // 如果小於等於和
            } else { // 新劃分一段
                if (cnt == k) { // 如果相等直接返回false，因為還需要加1
                    return false;
                }
                cnt += 1;
                s = x;
            }
        }
        return true;
    }


}
