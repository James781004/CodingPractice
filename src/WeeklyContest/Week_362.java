package WeeklyContest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Week_362 {
    // https://leetcode.cn/problems/points-that-intersect-with-cars/solutions/2435384/chai-fen-shu-zu-xian-xing-zuo-fa-by-endl-3xpm/
    public int numberOfPoints(List<List<Integer>> nums) {
        Set<Integer> set = new HashSet<>();
        for (List<Integer> list : nums) {
            int start = list.get(0), end = list.get(1);
            for (int i = start; i <= end; i++) {
                set.add(i);
            }
        }

        return set.size();
    }

    // 差分數組
    public int numberOfPoints2(List<List<Integer>> nums) {
        int[] diff = new int[102];

        // 差分數組中：end+1 會減少 x
        // 讓原數組中每一個數加一個 x (x = 1)
        // 差分數組中的 left 就會加一個 x，right 就會減少一個 x
        for (List<Integer> p : nums) {
            diff[p.get(0)]++;
            diff[p.get(1) + 1]--;
        }

        // 復原原數組，left -> right - 1 就是原數組的區間
        // 在覆蓋范圍以內的區間 sum > 0
        int ans = 0, s = 0;
        for (int d : diff) {
            s += d;
            if (s > 0) {
                ans++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/determine-if-a-cell-is-reachable-at-a-given-time/solutions/2435321/xian-xie-zhao-zou-zai-zhi-zou-ran-hou-za-lkxu/
    public boolean isReachableAtTime(int sx, int sy, int fx, int fy, int t) {
        if (sx == fx && sy == fy) {  // 非常噁心的特判
            return t != 1;
        }

        // 前半段 x, y 同時移動，後半段 x, y 較長的那方自己移動，如果時間沒用完就繞圈耗時間
        return Math.max(Math.abs(sx - fx), Math.abs(sy - fy)) <= t;
    }


    // https://leetcode.cn/problems/minimum-moves-to-spread-stones-over-grid/solutions/2435331/shen-du-sou-suo-java-100030-jiang-shi-to-0seb/
    int res = Integer.MAX_VALUE;

    public int minimumMoves(int[][] grid) {
        backTracking(grid, 0, 1);
        return res;
    }

    // 暴力 DFS
    public void backTracking(int[][] grid, int depth, int flag) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] > 1 || grid[i][j] == 0) {
                    flag = 0; // 如果發現目前尚未分配完成，flag 重設為 0 直接 break，後面繼續分配
                    break;
                }
            }
        }

        if (depth > res) return; // 使用步數已經比之前結果長，直接剪枝 return
        if (flag == 1) { // flag 為 1 表示分配完成，更新使用步數
            res = Math.min(res, depth);
            return;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] > 1) {
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if (grid[k][l] == 0 && grid[i][j] > 1) { // 枚舉找到大於 1 格子以及等於 0 格子組合
                                grid[i][j]--; // 大於 1 格子移除一個
                                grid[k][l]++; // 等於 0 格子增加一個
                                int cnt = Math.abs(k - i) + Math.abs(l - j); // 計算使用步數
                                backTracking(grid, depth + cnt, 1); // flag 設為 1 進入下一層
                                grid[k][l]--; // 回溯還原
                                grid[i][j]++;
                            }
                        }
                    }
                }
            }
        }
    }


    // https://leetcode.cn/problems/string-transformation/solutions/2435435/java-ju-zhen-kuai-su-mi-gun-dong-hash-by-ky0f/
    int mod = 1000000007;

    public int numberOfWays(String s, String t, long k) {
        int[] d = new int[26];
        int n = s.length();
        for (int i = 0; i < s.length(); i++) {
            d[s.charAt(i) - 'a']++;
            d[t.charAt(i) - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (d[i] != 0) return 0;
        }
        long[] cur = new long[]{1L, 0L, 0L, 0L};
        long[] base = new long[]{0, n - 1, 1, n - 2};
        long[] res = pow(cur, base, k);
        long a = res[0], b = res[2];
        long ret = 0;
        String ss = s + s;
        long[] p = new long[ss.length() + 1];

        long pp = 1;
        long ha = 0;
        for (int i = 0; i < ss.length(); i++) {
            p[i + 1] = p[i] * 31 + (ss.charAt(i) - 'a');
            if (i < n) {
                ha = ha * 31 + (t.charAt(i) - 'a');
                pp *= 31;
            }
        }

        long ans = 0;
        for (int i = 0; i < n; i++) {
            if (ha == p[i + n] - p[i] * pp) {
                ans += (i == 0 ? a : b);
                ans %= mod;
            }
        }
        return (int) ans;


    }

    long[] pow(long[] res, long[] base, long k) {
        while (k > 0) {
            if ((k & 1) > 0) {
                res = f(base, res);
            }
            base = f(base, base);
            k >>= 1;
        }
        return res;
    }


    long[] f(long[] t, long[] d) {
        return new long[]{(t[0] * d[0] + t[1] * d[2]) % mod,
                (t[0] * d[1] + t[1] * d[3]) % mod,
                (t[2] * d[0] + t[3] * d[2]) % mod,
                (t[2] * d[1] + t[3] * d[3]) % mod};
    }
}
