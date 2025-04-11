package EndlessCheng.Basic.Greedy;

public class minOperations {

    // https://leetcode.cn/problems/equal-sum-arrays-with-minimum-number-of-operations/solutions/2009786/mei-xiang-ming-bai-yi-ge-dong-hua-miao-d-ocuu/
    public int minOperations(int[] nums1, int[] nums2) {
        if (6 * nums1.length < nums2.length || 6 * nums2.length < nums1.length)
            return -1; // 優化
        // int d = Arrays.stream(nums2).sum() - Arrays.stream(nums1).sum();
        int d = 0; // 數組元素和的差，我們要讓這個差變為 0
        for (int x : nums2) d += x;
        for (int x : nums1) d -= x;
        if (d < 0) {
            d = -d;
            int[] tmp = nums1;
            nums1 = nums2;
            nums2 = tmp; // 交換，統一讓 nums1 的數變大，nums2 的數變小
        }
        int[] cnt = new int[6]; // 統計每個數的最大變化量
        for (int x : nums1) ++cnt[6 - x]; // nums1 的變成 6
        for (int x : nums2) ++cnt[x - 1]; // nums2 的變成 1
        for (int i = 5, ans = 0; ; --i) { // 從大到小枚舉最大變化量 5 4 3 2 1
            if (i * cnt[i] >= d) // 可以讓 d 變為 0
                return ans + (d + i - 1) / i;
            ans += cnt[i]; // 需要所有最大變化量為 i 的數
            d -= i * cnt[i];
        }
    }


}
