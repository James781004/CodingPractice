package WeeklyContest;

import java.util.*;

public class Week_471 {

    // https://leetcode.cn/problems/sum-of-elements-with-frequency-divisible-by-k/solutions/3803808/tong-ji-mei-ge-yuan-su-de-chu-xian-ci-sh-0rrv/
    public int sumDivisibleByK(int[] nums, int k) {
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        int[] cnt = new int[mx + 1];
        for (int x : nums) {
            cnt[x]++;
        }

        int ans = 0;
        for (int x = 1; x <= mx; x++) {
            if (cnt[x] % k == 0) {
                ans += x * cnt[x];
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/longest-balanced-substring-i/solutions/3803799/bao-li-mei-ju-pythonjavacgo-by-endlessch-wd3t/
    public int longestBalanced(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int[] cnt = new int[26];
            next:
            for (int j = i; j < n; j++) {
                cnt[s[j] - 'a']++;
                int base = 0;
                for (int c : cnt) {
                    if (c == 0) {
                        continue;
                    }
                    if (base == 0) {
                        base = c;
                    } else if (c != base) {
                        continue next;
                    }
                }
                ans = Math.max(ans, j - i + 1);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/longest-balanced-substring-ii/solutions/3803790/shi-zi-bian-xing-mei-ju-you-wei-hu-zuo-p-slnc/
    public int longestBalanced2(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int ans = 0;

        // 一種字母
        for (int i = 0; i < n; ) {
            int start = i;
            for (i++; i < n && s[i] == s[i - 1]; i++) ;
            ans = Math.max(ans, i - start);
        }

        // 兩種字母
        ans = Math.max(ans, f(s, 'a', 'b'));
        ans = Math.max(ans, f(s, 'a', 'c'));
        ans = Math.max(ans, f(s, 'b', 'c'));

        // 三種字母
        // 前綴和數組的首項是 0，位置相當於在 -1
        // 把 (x, y) 壓縮成一個 long，方便保存至哈希表
        // (x, y) 變成 (x + n) << 32 | (y + n)，其中 +n 避免出現負數
        Map<Long, Integer> pos = new HashMap<>();
        pos.put((long) n << 32 | n, -1);
        int[] cnt = new int[3];
        for (int i = 0; i < n; i++) {
            cnt[s[i] - 'a']++;
            long p = (long) (cnt[0] - cnt[1] + n) << 32 | (cnt[1] - cnt[2] + n);
            if (pos.containsKey(p)) {
                ans = Math.max(ans, i - pos.get(p));
            } else {
                pos.put(p, i);
            }
        }
        return ans;
    }

    private int f(char[] s, char x, char y) {
        int n = s.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            Map<Integer, Integer> pos = new HashMap<>();
            pos.put(0, i - 1); // 前綴和數組的首項是 0，位置相當於在 i-1
            int d = 0; // x 的個數減去 y 的個數
            for (; i < n && (s[i] == x || s[i] == y); i++) {
                d += s[i] == x ? 1 : -1;
                if (pos.containsKey(d)) {
                    ans = Math.max(ans, i - pos.get(d));
                } else {
                    pos.put(d, i);
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/sum-of-perfect-square-ancestors/solutions/3803792/ping-fang-sheng-yu-he-mei-ju-you-wei-hu-bfyxy/
    private static final int MX = 100_001;
    private static final int[] core = new int[MX];

    static {
        // 預處理平方剩余核
        for (int i = 1; i < MX; i++) {
            if (core[i] == 0) {
                for (int j = 1; i * j * j < MX; j++) {
                    core[i * j * j] = i;
                }
            }
        }
    }

    public long sumOfAncestors(int n, int[][] edges, int[] nums) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        Map<Integer, Integer> cnt = new HashMap<>();
        return dfs(0, -1, g, nums, cnt);
    }

    private long dfs(int x, int fa, List<Integer>[] g, int[] nums, Map<Integer, Integer> cnt) {
        int cr = core[nums[x]];
        // 本題 x 的祖先不包括 x 自己
        int c = cnt.getOrDefault(cr, 0);
        long res = c;
        cnt.put(cr, c + 1);
        for (int y : g[x]) {
            if (y != fa) {
                res += dfs(y, x, g, nums, cnt);
            }
        }
        cnt.put(cr, c); // 恢復現場
        return res;
    }


}









