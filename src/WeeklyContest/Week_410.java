package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_410 {
    // https://leetcode.cn/problems/snake-in-matrix/solutions/2876200/jian-dan-ti-jian-dan-zuo-pythonjavacgo-b-l929/
    public int finalPositionOfSnake(int n, List<String> commands) {
        int i = 0;
        int j = 0;
        for (String s : commands) {
            switch (s.charAt(0)) {
                case 'U' -> i--;
                case 'D' -> i++;
                case 'L' -> j--;
                default -> j++;
            }
        }
        return i * n + j;
    }


    // https://leetcode.cn/problems/count-the-number-of-good-nodes/solutions/2876198/dfs-ji-suan-zi-shu-da-xiao-pythonjavacgo-9atl/
    public int countGoodNodes(int[][] edges) {
        int n = edges.length + 1;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        dfs(0, -1, g);
        return ans;
    }

    private int ans;

    private int dfs(int x, int fa, List<Integer>[] g) {
        int size = 1;
        int pre = 0;
        boolean ok = true;
        for (int y : g[x]) {
            if (y == fa) { // 不要重複走父節點
                continue;
            }
            int sz = dfs(y, x, g);
            if (pre > 0 && sz != pre) {
                ok = false; // 子樹的大小不同
            }
            pre = sz; // 記錄上一個兒子子樹的大小
            size += sz;
        }
        if (ok) {
            ans++;
        }
        return size;
    }


    // https://leetcode.cn/problems/find-the-count-of-monotonic-pairs-ii/solutions/2876190/qian-zhui-he-you-hua-dppythonjavacgo-by-3biek/
    public int countOfPairs(int[] nums) {
        final int MOD = 1_000_000_007;
        int n = nums.length;
        int m = Arrays.stream(nums).max().getAsInt();
        long[][] f = new long[n][m + 1];
        long[] s = new long[m + 1];

        Arrays.fill(f[0], 0, nums[0] + 1, 1);
        for (int i = 1; i < n; i++) {
            s[0] = f[i - 1][0];
            for (int k = 1; k <= m; k++) {
                s[k] = (s[k - 1] + f[i - 1][k]) % MOD; // f[i-1] 的前綴和
            }
            for (int j = 0; j <= nums[i]; j++) {
                int maxK = j + Math.min(nums[i - 1] - nums[i], 0);
                f[i][j] = maxK >= 0 ? s[maxK] % MOD : 0;
            }
        }

        return (int) (Arrays.stream(f[n - 1], 0, nums[n - 1] + 1).sum() % MOD);
    }


}


