package EndlessCheng.GenreMenu.DP.Basic.MaxSubArray;

public class MaximumCostSubstring {

    // https://leetcode.cn/problems/find-the-substring-with-maximum-cost/solutions/2203595/zhuan-huan-zui-da-zi-shu-zu-he-pythonjav-6it2/
    public int maximumCostSubstring(String s, String chars, int[] vals) {
        int[] mapping = new int[26];
        for (int i = 0; i < 26; i++) {
            mapping[i] = i + 1;
        }

        // 把 s 中的字母映射到數字上
        for (int i = 0; i < vals.length; i++) {
            mapping[chars.charAt(i) - 'a'] = vals[i];
        }

        // 最大子數組和（允許子數組為空）
        int ans = 0;
        int f = 0; // 以 a[i] 結尾的最大子數組和，考慮是否接在以 a[i−1] 結尾的最大子數組之後
        for (char c : s.toCharArray()) {
            f = Math.max(f, 0) + mapping[c - 'a'];
            ans = Math.max(ans, f);
        }
        return ans;
    }

}




