package EndlessCheng.GenreMenu.Backtracking.Backtracking.Divide;

import java.util.*;

public class WordBreakII {

    // https://leetcode.cn/problems/word-break-ii/solutions/12406/dong-tai-gui-hua-hui-su-qiu-jie-ju-ti-zhi-python-d/
    public List<String> wordBreak(String s, List<String> wordDict) {
        // 為了快速判斷一個單詞是否在單詞集合中，需要將它們加入哈希表
        Set<String> wordSet = new HashSet<>(wordDict);
        int len = s.length();

        // 第 1 步：動態規劃計算是否有解
        // dp[i] 表示「長度」為 i 的 s 前綴子串可以拆分成 wordDict 中的單詞
        // 長度包括 0 ，因此狀態數組的長度為 len + 1
        boolean[] dp = new boolean[len + 1];
        // 0 這個值需要被後面的狀態值參考，如果一個單詞正好在 wordDict 中，dp[0] 設置成 true 是合理的
        dp[0] = true;

        for (int right = 1; right <= len; right++) {
            // 如果單詞集合中的單詞長度都不長，從後向前遍歷是更快的
            for (int left = right - 1; left >= 0; left--) {
                // substring 不截取 s[right]，dp[left] 的結果不包含 s[left]
                if (wordSet.contains(s.substring(left, right)) && dp[left]) {
                    dp[right] = true;
                    // 這個 break 很重要，一旦得到 dp[right] = True ，不必再計算下去
                    break;
                }
            }
        }

        // 第 2 步：回溯算法搜索所有符合條件的解
        List<String> res = new ArrayList<>();
        if (dp[len]) {
            Deque<String> path = new ArrayDeque<>();
            dfs(s, len, wordSet, dp, path, res);
            return res;
        }
        return res;
    }

    /**
     * s[0:len) 如果可以拆分成 wordSet 中的單詞，把遞歸求解的結果加入 res 中
     *
     * @param s
     * @param len     長度為 len 的 s 的前綴子串
     * @param wordSet 單詞集合，已經加入哈希表
     * @param dp      預處理得到的 dp 數組
     * @param path    從葉子結點到根結點的路徑
     * @param res     保存所有結果的變量
     */
    private void dfs(String s, int len, Set<String> wordSet, boolean[] dp, Deque<String> path, List<String> res) {
        if (len == 0) {
            res.add(String.join(" ", path));
            return;
        }

        // 可以拆分的左邊界從 len - 1 依次枚舉到 0
        for (int i = len - 1; i >= 0; i--) {
            String suffix = s.substring(i, len);
            if (wordSet.contains(suffix) && dp[i]) {
                path.addFirst(suffix);
                dfs(s, i, wordSet, dp, path, res);
                path.removeFirst();
            }
        }
    }


}
