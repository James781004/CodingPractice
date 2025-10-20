package EndlessCheng.GenreMenu.Backtracking.Backtracking.Duplication;

public class LongestSubsequenceRepeatedK {

    // https://leetcode.cn/problems/longest-subsequence-repeated-k-times/solutions/1006067/mei-ju-pai-lie-zi-xu-lie-pi-pei-by-endle-oi2h/
    private char[] ans;
    private int ansLen = 0;

    public String longestSubsequenceRepeatedK(String S, int k) {
        char[] s = S.toCharArray();

        int[] cnt = new int[26];
        for (char c : s) {
            cnt[c - 'a']++;
        }

        StringBuilder tmp = new StringBuilder();
        // 倒序，這樣我們可以優先枚舉字典序大的排列
        for (int i = 25; i >= 0; i--) {
            String c = String.valueOf((char) ('a' + i));
            tmp.append(c.repeat(cnt[i] / k));
        }
        char[] a = tmp.toString().toCharArray();

        ans = new char[a.length];
        permute(a, k, s);

        return new String(ans, 0, ansLen);
    }

    // 47. 全排列 II
    // 枚舉從 nums 中選任意個數的所有排列，處理枚舉的排列
    private void permute(char[] nums, int k, char[] s) {
        int n = nums.length;
        char[] path = new char[n];
        boolean[] onPath = new boolean[n]; // onPath[j] 表示 nums[j] 是否已經填入排列
        dfs(0, nums, path, onPath, k, s);
    }

    private void dfs(int i, char[] nums, char[] path, boolean[] onPath, int k, char[] s) {
        // 處理當前排列 path
        // 如果 path*k 不是 s 的子序列，那麼繼續往 path 後面添加元素，更不可能是 s 的子序列
        if (i > 0 && !process(path, i, k, s)) {
            return; // 剪枝
        }

        if (i == nums.length) {
            return;
        }

        // 枚舉 nums[j] 填入 path[pathLen]
        for (int j = 0; j < nums.length; j++) {
            // 如果 nums[j] 已填入排列，continue
            // 如果 nums[j] 和前一個數 nums[j-1] 相等，且 nums[j-1] 沒填入排列，continue
            if (onPath[j] || j > 0 && nums[j] == nums[j - 1] && !onPath[j - 1]) {
                continue;
            }
            path[i] = nums[j]; // 填入排列
            onPath[j] = true; // nums[j] 已填入排列（注意標記的是下標，不是值）
            dfs(i + 1, nums, path, onPath, k, s); // 填排列的下一個數
            onPath[j] = false; // 恢復現場
            // 注意 path 無需恢復現場，直接覆蓋 path[i] 就行
        }
    }

    private boolean process(char[] seq, int seqLen, int k, char[] s) {
        if (!isSubsequence(seq, seqLen, k, s)) {
            return false;
        }
        if (seqLen > ansLen || seqLen == ansLen && compare(seq, ans, ansLen) > 0) {
            System.arraycopy(seq, 0, ans, 0, seqLen);
            ansLen = seqLen;
        }
        return true;
    }

    // 比較 a 和 b 的字典序大小
    private int compare(char[] a, char[] b, int n) {
        for (int i = 0; i < n; i++) {
            if (a[i] != b[i]) {
                return a[i] - b[i];
            }
        }
        return 0;
    }

    // 392. 判斷子序列
    // 返回 seq*k 是否為 s 的子序列
    private boolean isSubsequence(char[] seq, int n, int k, char[] s) {
        int i = 0;
        for (char c : s) {
            if (seq[i % n] == c) {
                i++;
                if (i == n * k) { // seq*k 的所有字符匹配完畢
                    return true; // seq*k 是 s 的子序列
                }
            }
        }
        return false;
    }


}
