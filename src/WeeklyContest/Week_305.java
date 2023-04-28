package WeeklyContest;

import java.util.*;

public class Week_305 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2367.Number%20of%20Arithmetic%20Triplets/README.md
    public int arithmeticTriplets(int[] nums, int diff) {
        boolean[] vis = new boolean[301];
        for (int x : nums) {
            vis[x] = true;
        }
        int ans = 0;
        for (int x : nums) {
            if (vis[x + diff] && vis[x + diff + diff]) {
                ++ans;
            }
        }
        return ans;
    }


    // 三指針
    public int arithmeticTriplets2(int[] nums, int diff) {
        int ans = 0, i = 0, j = 1;
        for (int x : nums) {
            while (nums[j] + diff < x) j++;
            if (nums[j] + diff != x) continue;
            while (nums[i] + diff < nums[j]) i++;
            if (nums[i] + diff == nums[j]) ans++;
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2368.Reachable%20Nodes%20With%20Restrictions/README.md
    class ReachableNodes {
        // dfs搜索
        private List<Integer>[] g;
        private boolean[] vis;
        private int ans;

        public int reachableNodesDFS(int n, int[][] edges, int[] restricted) {
            g = new List[n];
            Arrays.setAll(g, k -> new ArrayList<>());
            vis = new boolean[n];
            for (int v : restricted) {
                vis[v] = true;
            }
            for (int[] e : edges) {
                int a = e[0], b = e[1];
                g[a].add(b);
                g[b].add(a);
            }

            ans = 0;
            dfs(0);
            return ans;
        }

        private void dfs(int u) {
            if (vis[u]) {
                return;
            }
            ++ans;  // 訪問過的節點數目
            vis[u] = true;
            for (int v : g[u]) {
                dfs(v);
            }
        }


        public int reachableNodesBFS(int n, int[][] edges, int[] restricted) {
            List<Integer>[] g = new List[n];
            Arrays.setAll(g, k -> new ArrayList<>());
            for (int[] e : edges) {
                int a = e[0], b = e[1];
                g[a].add(b);
                g[b].add(a);
            }
            boolean[] vis = new boolean[n];
            for (int v : restricted) {
                vis[v] = true;
            }
            Deque<Integer> q = new ArrayDeque<>();
            q.offer(0);
            int ans = 0;
            while (!q.isEmpty()) {
                int i = q.pollFirst();
                ans++;  // 訪問過的節點數目
                vis[i] = true;
                for (int j : g[i]) {
                    if (!vis[j]) q.offer(j);
                }
            }
            return ans;
        }
    }


    // https://leetcode.cn/problems/check-if-there-is-a-valid-partition-for-the-array/solution/by-endlesscheng-8y73/
    // https://www.bilibili.com/video/BV1CN4y1V7uE/
    // 看到劃分，想一想是否有子問題，引出 DP 的思路（回顧一下經典題 70. 爬樓梯）。
    // 定義 f[i+1] 表示從 nums[0] 到 nums[i] 的這些元素能否有效劃分。那麼 f[0]=true，答案為 f[n]。
    public boolean validPartition(int[] nums) {
        int n = nums.length;
        boolean[] f = new boolean[n + 1];
        f[0] = true;  // 空數組也是一種狀態，設為true
        for (int i = 1; i < n; i++) {
            boolean c1 = f[i - 1] && nums[i] == nums[i - 1];
            boolean c2 = i > 1 && f[i - 2] && nums[i] == nums[i - 1] && nums[i] == nums[i - 2];
            boolean c3 = i > 1 && nums[i] == nums[i - 1] + 1 && nums[i] == nums[i - 2] + 2;
            if (c1 || c2 || c3) f[i + 1] = true;
        }
        return f[n];
    }


    // https://leetcode.cn/problems/longest-ideal-subsequence/solution/by-endlesscheng-t7zf/
    // 看到子序列和相鄰就可以往 DP 上想（回顧一下經典題 300. 最長遞增子序列，它也是子序列和相鄰）。
    // 字符串題目套路：枚舉字符。定義 f[i][c] 表示 s 的前 i 個字母中的以 c 結尾的理想字符串的最長長度。
    // 根據題意：
    // 選 s[i] 作為理想字符串中的字符，需要從 f[i−1] 中的 [s[i]−k,s[i]+k] 範圍內的字符轉移過來，
    // 其余情況，f[i][c]=f[i−1][c]。
    // f[i] 只從 f[i-1] 轉移過來，所以可以省略成一維dp
    // 答案為  max(f[n−1])。
    public int longestIdealString(String s, int k) {
        int[] f = new int[26];
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            for (int j = Math.max(c - k, 0); j <= Math.min(c + k, 25); j++) {
                f[c] = Math.max(f[c], f[j]); // 從 s[i]−k 到 s[i]+k 範圍轉移之前累計最長結果
            }
            f[c]++;
        }
        return Arrays.stream(f).max().getAsInt();
    }

}
