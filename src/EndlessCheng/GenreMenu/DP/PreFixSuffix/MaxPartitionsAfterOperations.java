package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MaxPartitionsAfterOperations {

    // https://leetcode.cn/problems/maximize-the-number-of-partitions-after-operations/solutions/2595072/ji-yi-hua-sou-suo-jian-ji-xie-fa-pythonj-6g5z/
    private int seg = 1, mask = 0, size = 0;

    public int maxPartitionsAfterOperations(String S, int k) {
        if (k == 26) {
            return 1;
        }

        char[] s = S.toCharArray();
        int n = s.length;
        int[] sufSeg = new int[n + 1];
        int[] sufMask = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            update(s[i], k);
            sufSeg[i] = seg;
            sufMask[i] = mask;
        }

        int ans = seg; // 不修改任何字母
        seg = 1;
        mask = 0;
        size = 0;
        for (int i = 0; i < n; i++) {
            int res = seg + sufSeg[i + 1]; // 情況 3
            int unionSize = Integer.bitCount(mask | sufMask[i + 1]);
            if (unionSize < k) {
                res--; // 情況 1
            } else if (unionSize < 26 && size == k && Integer.bitCount(sufMask[i + 1]) == k) {
                res++; // 情況 2
            }
            ans = Math.max(ans, res);
            update(s[i], k);
        }
        return ans;
    }

    private void update(char c, int k) {
        int bit = 1 << (c - 'a');
        if ((mask & bit) != 0) { // c 已經在當前這一段中，無影響
            return;
        }
        if (++size > k) {
            seg++; // c 在新的一段中
            mask = bit;
            size = 1;
        } else {
            mask |= bit;
        }
    }

}
