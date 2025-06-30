package WeeklyContest;

import java.util.*;

public class Week_456 {

    // https://leetcode.cn/problems/partition-string/solutions/3710991/an-ti-yi-mo-ni-ha-xi-ji-he-pythonjavacgo-p761/
    public List<String> partitionString(String s) {
        List<String> ans = new ArrayList<>();
        Set<String> vis = new HashSet<>();
        String t = "";
        for (char c : s.toCharArray()) {
            t += c;
            if (vis.add(t)) { // t 不在 vis 中
                ans.add(t);
                t = "";
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/longest-common-prefix-between-adjacent-strings-after-removals/solutions/3710963/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-8sn2/
    public int[] longestCommonPrefix(String[] words) {
        int n = words.length;
        int[] ans = new int[n];
        if (n == 1) { // 不存在相鄰對
            return ans;
        }

        // 後綴 [i,n-1] 中的相鄰 LCP 長度的最大值
        int[] sufMax = new int[n];
        for (int i = n - 2; i > 0; i--) {
            sufMax[i] = Math.max(sufMax[i + 1], lcp(words[i], words[i + 1]));
        }

        ans[0] = sufMax[1];
        int preMax = 0; // 前綴 [0,i-1] 中的相鄰 LCP 長度的最大值
        for (int i = 1; i < n - 1; i++) {
            ans[i] = Math.max(Math.max(preMax, lcp(words[i - 1], words[i + 1])), sufMax[i + 1]);
            preMax = Math.max(preMax, lcp(words[i - 1], words[i])); // 為下一輪循環做准備
        }
        ans[n - 1] = preMax;
        return ans;
    }

    private int lcp(String s, String t) {
        int n = Math.min(s.length(), t.length());
        int cnt = 0;
        for (int i = 0; i < n && s.charAt(i) == t.charAt(i); i++) {
            cnt++;
        }
        return cnt;
    }


    // https://leetcode.cn/problems/partition-array-to-minimize-xor/solutions/3710966/hua-fen-xing-dp-de-tong-yong-tao-lu-pyth-lmcm/
    public int minXor(int[] nums, int k) {
        int n = nums.length;
        int[][] f = new int[k + 1][n + 1];
        Arrays.fill(f[0], Integer.MAX_VALUE);
        f[0][0] = 0;
        for (int i = 1; i <= k; i++) {
            // 前後每個子數組長度至少是 1，預留空間給這些子數組
            for (int j = i; j <= n - (k - i); j++) {
                int res = Integer.MAX_VALUE;
                int s = 0;
                for (int l = j - 1; l >= i - 1; l--) {
                    s ^= nums[l];
                    res = Math.min(res, Math.max(f[i - 1][l], s));
                }
                f[i][j] = res;
            }
        }
        return f[k][n];
    }


    // https://leetcode.cn/problems/maximize-spanning-tree-stability-with-upgrades/solutions/3711009/liang-chong-fang-fa-er-fen-da-an-kruskal-6p7a/
    public int maxStability(int n, int[][] edges, int k) {
        UnionFind mustUf = new UnionFind(n); // 必選邊並查集
        UnionFind allUf = new UnionFind(n); // 全圖並查集
        int minS = Integer.MAX_VALUE;
        int maxS = 0;
        for (int[] e : edges) {
            int x = e[0], y = e[1], s = e[2], must = e[3];
            if (must > 0 && !mustUf.merge(x, y)) { // 必選邊成環
                return -1;
            }
            allUf.merge(x, y);
            minS = Math.min(minS, s);
            maxS = Math.max(maxS, s);
        }

        if (allUf.cc > 1) { // 圖不連通
            return -1;
        }

        int left = minS;
        int right = maxS * 2 + 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (check(mid, n, edges, k)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private boolean check(int low, int n, int[][] edges, int k) {
        UnionFind u = new UnionFind(n);
        for (int[] e : edges) {
            int x = e[0], y = e[1], s = e[2], must = e[3];
            if (must > 0 && s < low) { // 必選邊強度太小
                return false;
            }
            if (must > 0 || s >= low) {
                u.merge(x, y);
            }
        }

        for (int[] e : edges) {
            if (k == 0 || u.cc == 1) {
                break;
            }
            int x = e[0], y = e[1], s = e[2], must = e[3];
            if (must == 0 && s < low && s * 2 >= low && u.merge(x, y)) {
                k--;
            }
        }
        return u.cc == 1;
    }


    class UnionFind {
        private final int[] fa; // 代表元
        public int cc; // 連通塊個數

        UnionFind(int n) {
            // 一開始有 n 個集合 {0}, {1}, ..., {n-1}
            // 集合 i 的代表元是自己
            fa = new int[n];
            for (int i = 0; i < n; i++) {
                fa[i] = i;
            }
            cc = n;
        }

        // 返回 x 所在集合的代表元
        // 同時做路徑壓縮，也就是把 x 所在集合中的所有元素的 fa 都改成代表元
        public int find(int x) {
            // 如果 fa[x] == x，則表示 x 是代表元
            if (fa[x] != x) {
                fa[x] = find(fa[x]); // fa 改成代表元
            }
            return fa[x];
        }

        // 把 from 所在集合合並到 to 所在集合中
        // 返回是否合並成功
        public boolean merge(int from, int to) {
            int x = find(from);
            int y = find(to);
            if (x == y) { // from 和 to 在同一個集合，不做合並
                return false;
            }
            fa[x] = y; // 合並集合。修改後就可以認為 from 和 to 在同一個集合了
            cc--; // 成功合並，連通塊個數減一
            return true;
        }
    }

}









