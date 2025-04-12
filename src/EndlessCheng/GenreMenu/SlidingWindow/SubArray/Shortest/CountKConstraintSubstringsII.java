package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Shortest;

public class CountKConstraintSubstringsII {

    // https://leetcode.cn/problems/count-substrings-that-satisfy-k-constraint-ii/solutions/2884463/hua-dong-chuang-kou-qian-zhui-he-er-fen-jzo25/
    public long[] countKConstraintSubstrings(String S, int k, int[][] queries) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[] left = new int[n];
        long[] sum = new long[n + 1];
        int[] cnt = new int[2];
        int l = 0;
        for (int i = 0; i < n; i++) {
            cnt[s[i] & 1]++;
            while (cnt[0] > k && cnt[1] > k) {
                cnt[s[l++] & 1]--;
            }
            left[i] = l;
            // 計算 i-left[i]+1 的前綴和
            sum[i + 1] = sum[i] + i - l + 1;
        }

        long[] ans = new long[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int ql = queries[i][0];
            int qr = queries[i][1];
            int j = lowerBound(left, ql - 1, qr + 1, ql);

            // j 左邊：等差數列之和 (j - ql + 1) * (j - ql) / 2
            // j 右邊：前綴和 sum[qr + 1] - sum[j]
            ans[i] = sum[qr + 1] - sum[j] + (long) (j - ql + 1) * (j - ql) / 2;
        }
        return ans;
    }

    // 返回在開區間 (left, right) 中的最小的 j，滿足 nums[j] >= target
    // 如果沒有這樣的數，返回 right
    // 原理見 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(int[] nums, int left, int right, int target) {
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid; // 范圍縮小到 (mid, right)
            } else {
                right = mid; // 范圍縮小到 (left, mid)
            }
        }
        return right;
    }


}
