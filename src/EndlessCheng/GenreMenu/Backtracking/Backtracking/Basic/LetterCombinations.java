package EndlessCheng.GenreMenu.Backtracking.Backtracking.Basic;

import java.util.ArrayList;
import java.util.List;

public class LetterCombinations {

    // https://leetcode.cn/problems/letter-combinations-of-a-phone-number/solutions/2059416/hui-su-bu-hui-xie-tao-lu-zai-ci-pythonja-3orv/
    private static final String[] MAPPING = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinations(String digits) {
        int n = digits.length();
        if (n == 0) {
            return List.of();
        }
        List<String> ans = new ArrayList<>();
        char[] path = new char[n]; // 注意 path 長度一開始就是 n，不是空數組
        dfs(0, ans, path, digits.toCharArray());
        return ans;
    }

    private void dfs(int i, List<String> ans, char[] path, char[] digits) {
        if (i == digits.length) {
            ans.add(new String(path));
            return;
        }
        String letters = MAPPING[digits[i] - '0'];
        for (char c : letters.toCharArray()) {
            path[i] = c; // 直接覆蓋
            dfs(i + 1, ans, path, digits);
        }
    }


}
