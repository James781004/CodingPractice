package EndlessCheng.GenreMenu.MonoStack.Lexicographic;

public class MaxNumber {

    // https://leetcode.cn/problems/create-maximum-number/solutions/438337/java-dan-diao-zhan-si-xiang-by-lil-q/
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] res = new int[0];
        // 從 nums1 中選出長 i 的子序列
        for (int i = 0; i <= k && i <= nums1.length; i++) {
            // 從 nums2 中選出長 k - i 的子序列
            if (k - i >= 0 && k - i <= nums2.length) {
                // 合並
                int[] tmp = merge(subMaxNumber(nums1, i), subMaxNumber(nums2, k - i));
                // 取最大值
                if (compare(tmp, 0, res, 0)) {
                    res = tmp;
                }
            }
        }
        return res;
    }

    // 類似於單調遞減棧
    public int[] subMaxNumber(int[] nums, int len) {
        int[] subNums = new int[len];
        int cur = 0, rem = nums.length - len; // rem 表示還可以刪去多少字符
        for (int i = 0; i < nums.length; i++) {
            while (cur > 0 && subNums[cur - 1] < nums[i] && rem > 0) {
                cur--;
                rem--;
            }
            if (cur < len) {
                subNums[cur++] = nums[i];
            } else {
                rem--; // 避免超過邊界而少刪字符
            }
        }
        return subNums;
    }

    public int[] merge(int[] nums1, int[] nums2) {
        int[] res = new int[nums1.length + nums2.length];
        int cur = 0, p1 = 0, p2 = 0;
        while (cur < nums1.length + nums2.length) {
            if (compare(nums1, p1, nums2, p2)) { // 不能只比較當前值，如果當前值相等還需要比較後續哪個大
                res[cur++] = nums1[p1++];
            } else {
                res[cur++] = nums2[p2++];
            }
        }
        return res;
    }

    public boolean compare(int[] nums1, int p1, int[] nums2, int p2) {
        if (p2 >= nums2.length) return true;
        if (p1 >= nums1.length) return false;
        if (nums1[p1] > nums2[p2]) return true;
        if (nums1[p1] < nums2[p2]) return false;
        return compare(nums1, p1 + 1, nums2, p2 + 1);
    }


}
