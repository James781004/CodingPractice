package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Week_486 {

    // https://leetcode.cn/problems/minimum-prefix-removal-to-make-array-strictly-increasing/solutions/3888460/dao-xu-bian-li-pythonjavacgo-by-endlessc-vhug/
    public int minimumPrefixLength(int[] nums) {
        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i - 1] >= nums[i]) {
                return i; // 移除前綴 [0, i-1]，長度為 i
            }
        }
        return 0;
    }


    // https://leetcode.cn/problems/rotate-non-negative-elements/solutions/3888457/mo-ni-pythonjavacgo-by-endlesscheng-hpbw/
    public int[] rotateElements(int[] nums, int k) {
        // 取出非負數
        List<Integer> a = new ArrayList<>();
        for (int x : nums) {
            if (x >= 0) {
                a.add(x);
            }
        }

        // 向左輪替 k 個位置（原地操作）
        Collections.rotate(a, -k);

        // 雙指針，把 a 填入 nums，跳過負數
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                nums[i] = a.get(j++);
            }
        }
        return nums;
    }


    // https://leetcode.cn/problems/pythagorean-distance-nodes-in-a-tree/solutions/3888454/ji-suan-x-y-z-dao-mei-ge-dian-de-ju-chi-xhrb2/
    public int specialNodes(int n, int[][] edges, int x, int y, int z) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            int v = e[0];
            int w = e[1];
            g[v].add(w);
            g[w].add(v);
        }

        int[] dx = calcDis(x, g);
        int[] dy = calcDis(y, g);
        int[] dz = calcDis(z, g);

        int ans = 0;
        for (int i = 0; i < n; i++) {
            int[] a = new int[]{dx[i], dy[i], dz[i]};
            Arrays.sort(a);
            if ((long) a[0] * a[0] + (long) a[1] * a[1] == (long) a[2] * a[2]) {
                ans++;
            }
        }
        return ans;
    }

    private int[] calcDis(int start, List<Integer>[] g) {
        int[] dis = new int[g.length];
        dfs(start, -1, g, dis);
        return dis;
    }

    private void dfs(int v, int fa, List<Integer>[] g, int[] dis) {
        for (int w : g[v]) {
            if (w != fa) {
                dis[w] = dis[v] + 1;
                dfs(w, v, g, dis);
            }
        }
    }


    // https://leetcode.cn/problems/find-nth-smallest-integer-with-k-one-bits/solutions/3888450/shi-tian-fa-zu-he-shu-xue-pythonjavacgo-d099n/
    class Solution {
        private static final int MX = 51;
        private static final long[][] comb = new long[MX][MX];
        private static boolean initialized = false;

        // 這樣寫比 static block 快
        public Solution() {
            if (initialized) {
                return;
            }
            initialized = true;

            // 預處理組合數
            for (int i = 0; i < MX; i++) {
                comb[i][0] = 1;
                for (int j = 1; j <= i; j++) {
                    comb[i][j] = comb[i - 1][j - 1] + comb[i - 1][j];
                }
            }
        }

        public long nthSmallest(long n, int k) {
            long ans = 0;
            for (int i = 49; k > 0; i--) {
                long c = comb[i][k]; // 第 i 位填 0 的方案數
                if (n > c) { // n 比較大，第 i 位必須填 1
                    n -= c;
                    ans |= 1L << i;
                    k--; // 維護剩余的 1 的個數
                }
            }
            return ans;
        }
    }


}









