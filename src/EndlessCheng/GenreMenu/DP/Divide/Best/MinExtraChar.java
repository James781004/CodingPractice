package EndlessCheng.GenreMenu.DP.Divide.Best;

import java.util.HashSet;

public class MinExtraChar {

    // https://leetcode.cn/problems/extra-characters-in-a-string/solutions/2286613/dong-tai-gui-hua-cong-ji-yi-hua-sou-suo-wtd7a/
    public int minExtraChar(String s, String[] dictionary) {
        var set = new HashSet<String>(dictionary.length);
        for (var str : dictionary) set.add(str);
        int n = s.length();
        var f = new int[n + 1]; // 表示 s 的前 i 個字符的子問題
        for (int i = 0; i < n; i++) {
            f[i + 1] = f[i] + 1; // 不選
            for (int j = 0; j <= i; j++) { // 枚舉選哪個
                if (set.contains(s.substring(j, i + 1))) {
                    f[i + 1] = Math.min(f[i + 1], f[j]);
                }
            }
        }
        return f[n];
    }


}
