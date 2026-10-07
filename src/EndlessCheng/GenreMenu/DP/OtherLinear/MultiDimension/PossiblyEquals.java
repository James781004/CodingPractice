package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class PossiblyEquals {

    // https://leetcode.cn/problems/check-if-an-original-string-exists-given-two-encoded-strings/solutions/1075925/ji-yi-hua-sou-suo-by-endlesscheng-ll3r/
    public boolean possiblyEquals(String s1, String s2) {
        boolean[][][] visited = new boolean[s1.length() + 1][s2.length() + 1][2000];
        return dfs(s1, s2, visited, 0, 0, 0);
    }

    // dfs(i,j,d) 表示 s1[:i] 的原始字符串的長度減去 s2[:j] 的原始字符串的長度等於 d 時，
    // 能否匹配（即可以由同一個原始字符串轉換得到）。
    boolean dfs(String s1, String s2, boolean[][][] visited, int a, int b, int d) {
        if (a == s1.length() && b == s2.length() && d == 0) {
            return true;
        }

        if (visited[a][b][d + 1000]) {
            return false;
        }
        visited[a][b][d + 1000] = true;
        if (d == 0 && a < s1.length() && b < s2.length() && s1.charAt(a) == s2.charAt(b) && dfs(s1, s2, visited, a + 1, b + 1, 0)) {
            return true;
        }
        if (d >= 0) {
            //字母
            if (d > 0 && b < s2.length() && Character.isAlphabetic(s2.charAt(b))) {
                return dfs(s1, s2, visited, a, b + 1, d - 1);
            } else {
                int i = b;
                int num = 0;
                while (i < s2.length() && Character.isDigit(s2.charAt(i))) {
                    num = num * 10 + (s2.charAt(i) - '0');
                    if (dfs(s1, s2, visited, a, i + 1, d - num)) {
                        return true;
                    }
                    i++;
                }
            }
        }
        if (d <= 0) {
            if (d < 0 && a < s1.length() && Character.isAlphabetic(s1.charAt(a))) {
                return dfs(s1, s2, visited, a + 1, b, d + 1);
            } else {
                int i = a;
                int num = 0;
                while (i < s1.length() && Character.isDigit(s1.charAt(i))) {
                    num = num * 10 + (s1.charAt(i) - '0');
                    if (dfs(s1, s2, visited, i + 1, b, d + num)) {
                        return true;
                    }
                    i++;
                }
            }
        }
        return false;
    }

}
