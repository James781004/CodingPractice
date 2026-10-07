package EndlessCheng.GenreMenu.DP.Divide.Best;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MinimumBeautifulSubstrings {

    // https://leetcode.cn/problems/partition-string-into-minimum-beautiful-substrings/solutions/2336485/on2-ji-yi-hua-sou-suo-dao-di-tui-by-endl-99lb/
    String s;
    Set<String> set;
    int[] cache;

    public int minimumBeautifulSubstrings(String s) {
        this.s = s;
        int n = s.length();
        cache = new int[n + 1];
        Arrays.fill(cache, -1);
        // 預處理所有 5 的冪的 二進制 表示
        set = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            set.add(Integer.toBinaryString((int) Math.pow(5, i)));
        }
        int res = dfs(n);
        return res == Integer.MAX_VALUE / 2 ? -1 : res;
    }

    // 定義 dfs(i) 表示分割 s[:i] 的最少數目
    // 轉移：枚舉 0 < j < i，看 s[j:i] 是否為美麗子字符串
    //   取所有情況的最小值
    // 遞歸邊界： dfs(0) = 0, 表示分割完了s，是一種合法方案
    // 遞歸入口： dfs(len(s)) 若不存在合法情況，返回-1
    public int dfs(int i) {
        if (i == 0) return 0;
        if (cache[i] >= 0) return cache[i];
        int res = Integer.MAX_VALUE / 2;
        for (int j = i - 1; j >= 0; j--) {
            if (s.charAt(j) != '0' && set.contains(s.substring(j, i))) {
                res = Math.min(res, dfs(j) + 1);
            }
        }
        return cache[i] = res;
    }


    public int minimumBeautifulSubstringsDP(String s) {
        // 預處理所有 5 的冪的 二進制 表示
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            set.add(Integer.toBinaryString((int) Math.pow(5, i)));
        }
        int n = s.length();
        int[] f = new int[n + 2]; // 劃分從 s[i] 開始的後綴，最少要劃分成多少段
        Arrays.fill(f, Integer.MAX_VALUE / 2);
        f[0] = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (s.charAt(j) != '0' && set.contains(s.substring(j, i)))
                    f[i] = Math.min(f[i], f[j] + 1);
            }
        }
        return f[n] == Integer.MAX_VALUE / 2 ? -1 : f[n];
    }

}
