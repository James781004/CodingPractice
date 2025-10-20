package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

import java.util.ArrayList;
import java.util.List;

public class MaxLength {

    // https://leetcode.cn/problems/maximum-length-of-a-concatenated-string-with-unique-characters/solutions/3592254/ling-shen-ti-dan-zui-bao-li-hui-su-xuan-ktbks/
    // 回溯三問
    // 策略：選或不選
    // 當前操作：選字符串
    // 子問題：選第 i 個字符串/不選第 i 個字符串
    // 下一個子問題：選第 i+1 個字符串/不選第 i+1 個字符串
    // 遞歸邊界：i == len，表示字符串已經被選完了
    private int ans = Integer.MIN_VALUE;
    private List<String> path = new ArrayList<>();
    private List<String> arr;

    public int maxLength(List<String> arr) {
        int len = arr.size();
        this.arr = arr;
        dfs(0, len);
        return ans;
    }

    private void dfs(int i, int len) {
        if (i == len) {
            String concatenated = String.join("", path);
            if (isUnique(concatenated)) {
                ans = Math.max(ans, concatenated.length());
            }
            return;
        }
        // 不選
        dfs(i + 1, len);
        // 選
        path.add(arr.get(i));
        dfs(i + 1, len);
        path.remove(path.size() - 1);
    }

    // 判斷字母是否有重覆出現
    private boolean isUnique(String s) {
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
            if (cnt[c - 'a'] > 1) return false;
        }
        return true;
    }
}
