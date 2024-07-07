package WeeklyContest;

import java.util.*;

public class Week_405 {
    // https://leetcode.cn/problems/find-the-encrypted-string/solutions/2833810/jian-ji-xie-fa-pythonjavacgo-by-endlessc-eul9/
    public String getEncryptedString(String s, int k) {
        k %= s.length();
        return s.substring(k) + s.substring(0, k);
    }


    // https://leetcode.cn/problems/generate-binary-strings-without-adjacent-zeros/solutions/2833805/wei-yun-suan-zuo-fa-pythonjavacgo-by-end-6lbt/
    public List<String> validStrings(int n) {
        List<String> ans = new ArrayList<>();
        int mask = (1 << n) - 1;  // 長為 n 的全為 1 的二進制數
        for (int i = 0; i < (1 << n); i++) {
            int x = mask ^ i; // i 取反，保留二進制低 n 位
            if (((x >> 1) & x) == 0) { // 取反後的數，沒有相鄰的 1，那麼 i 就沒有相鄰的0
                ans.add(Integer.toBinaryString((1 << n) | i).substring(1));
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-submatrices-with-equal-frequency-of-x-and-y/solutions/2833801/liang-chong-fang-fa-er-wei-qian-zhui-he-a6qqf/
    public int numberOfSubmatrices(char[][] grid) {
        int ans = 0;
        int m = grid.length;
        int n = grid[0].length;
        int[][][] sum = new int[m + 1][n + 1][2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum[i + 1][j + 1][0] = sum[i + 1][j][0] + sum[i][j + 1][0] - sum[i][j][0];
                sum[i + 1][j + 1][1] = sum[i + 1][j][1] + sum[i][j + 1][1] - sum[i][j][1];
                if (grid[i][j] != '.') {
                    sum[i + 1][j + 1][grid[i][j] & 1]++;
                }
                if (sum[i + 1][j + 1][0] > 0 && sum[i + 1][j + 1][0] == sum[i + 1][j + 1][1]) {
                    ans++;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/construct-string-with-minimum-cost/solutions/2833949/hou-zhui-shu-zu-by-endlesscheng-32h9/
    public int minimumCost(String target, String[] words, int[] costs) {
        Map<String, Integer> minCost = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            int c = costs[i];
            minCost.put(w, minCost.getOrDefault(w, Integer.MAX_VALUE) > c ? c : minCost.get(w));
        }

        int n = target.length();
        List<Pair>[] from = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            from[i] = new ArrayList<>();
        }

        for (Map.Entry<String, Integer> entry : minCost.entrySet()) {
            String w = entry.getKey();
            int c = entry.getValue();
            int start = target.indexOf(w);
            while (start != -1) {
                int end = start + w.length();
                from[end].add(new Pair(start, c));
                start = target.indexOf(w, start + 1);
            }
        }

        int[] f = new int[n + 1];
        Arrays.fill(f, Integer.MAX_VALUE / 2);
        f[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (Pair p : from[i]) {
                f[i] = Math.min(f[i], f[p.l] + p.cost);
            }
        }
        return f[n] == Integer.MAX_VALUE / 2 ? -1 : f[n];
    }

    class Pair {
        int l;
        int cost;

        Pair(int l, int cost) {
            this.l = l;
            this.cost = cost;
        }
    }
}


