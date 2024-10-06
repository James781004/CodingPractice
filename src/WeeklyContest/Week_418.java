package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_418 {

    // https://leetcode.cn/problems/maximum-possible-number-by-binary-concatenation/solutions/2940480/tie-yi-ge-javaban-ben-de-er-jin-zhi-zhua-hykv/
    public int maxGoodNumber(int[] nums) {
        int n = nums.length;
        String[] binary = new String[n];
        StringBuilder bs = new StringBuilder();

        for (int i = 0; i < n; i++) {
            binary[i] = Integer.toBinaryString(nums[i]);
        }

        // 将字符串拼接，并按照字典序排序
        Arrays.sort(binary, (a, b) -> (b + a).compareTo(a + b));

        for (String s : binary) {
            bs.append(s);
        }

        return Integer.parseInt(bs.toString(), 2);
    }


    // https://leetcode.cn/problems/remove-methods-from-project/solutions/2940460/liang-ci-dfspythonjavacgo-by-endlesschen-cjat/
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : invocations) {
            g[e[0]].add(e[1]);
        }

        // 標記所有可疑方法
        boolean[] isSuspicious = new boolean[n];
        dfs(k, g, isSuspicious);

        // 檢查是否有【非可疑方法】->【可疑方法】的邊
        for (int[] e : invocations) {
            if (!isSuspicious[e[0]] && isSuspicious[e[1]]) {
                // 無法移除可疑方法
                List<Integer> ans = new ArrayList<>(n);
                for (int i = 0; i < n; i++) {
                    ans.add(i);
                }
                return ans;
            }
        }

        // 移除所有可疑方法
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!isSuspicious[i]) {
                ans.add(i);
            }
        }
        return ans;
    }

    private void dfs(int x, List<Integer>[] g, boolean[] isSuspicious) {
        isSuspicious[x] = true;
        for (int y : g[x]) {
            if (!isSuspicious[y]) {
                dfs(y, g, isSuspicious);
            }
        }
    }


    // https://leetcode.cn/problems/construct-2d-grid-matching-graph-layout/solutions/2940537/fen-lei-tao-lun-zhu-xing-gou-zao-by-endl-v3x0/
    public int[][] constructGridLayout(int n, int[][] edges) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        // 每種度數選一個點
        int[] degToNode = new int[5];
        Arrays.fill(degToNode, -1);
        for (int x = 0; x < n; x++) {
            degToNode[g[x].size()] = x;
        }

        List<Integer> row = new ArrayList<>();
        if (degToNode[1] != -1) {
            // 答案只有一列
            row.add(degToNode[1]);
        } else if (degToNode[4] == -1) {
            // 答案只有兩列
            int x = degToNode[2];
            for (int y : g[x]) {
                if (g[y].size() == 2) {
                    row.add(x);
                    row.add(y);
                    break;
                }
            }
        } else {
            // 答案至少有三列
            int x = degToNode[2];
            row.add(x);
            int pre = x;
            x = g[x].get(0);
            while (g[x].size() > 2) {
                row.add(x);
                for (int y : g[x]) {
                    if (y != pre && g[y].size() < 4) {
                        pre = x;
                        x = y;
                        break;
                    }
                }
            }
            row.add(x);
        }

        int k = row.size();
        int[][] ans = new int[n / k][k];
        boolean[] vis = new boolean[n];
        for (int j = 0; j < k; j++) {
            int x = row.get(j);
            ans[0][j] = x;
            vis[x] = true;
        }
        for (int i = 1; i < ans.length; i++) {
            for (int j = 0; j < k; j++) {
                for (int y : g[ans[i - 1][j]]) {
                    // 上左右的鄰居都訪問過了，沒訪問過的鄰居只會在下面
                    if (!vis[y]) {
                        vis[y] = true;
                        ans[i][j] = y;
                        break;
                    }
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/sorted-gcd-pair-queries/solutions/2940415/mei-ju-rong-chi-qian-zhui-he-er-fen-pyth-ujis/
    public int[] gcdValues(int[] nums, long[] queries) {
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }
        int[] cntX = new int[mx + 1];
        for (int x : nums) {
            cntX[x]++;
        }

        long[] cntGcd = new long[mx + 1];
        for (int i = mx; i > 0; i--) {
            int c = 0;
            for (int j = i; j <= mx; j += i) {
                c += cntX[j];
                cntGcd[i] -= cntGcd[j]; // gcd 是 2i,3i,4i,... 的數對不能統計進來
            }
            cntGcd[i] += (long) c * (c - 1) / 2; // c 個數選 2 個，組成 c*(c-1)/2 個數對
        }

        for (int i = 2; i <= mx; i++) {
            cntGcd[i] += cntGcd[i - 1]; // 前綴和
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = upperBound(cntGcd, queries[i]);
        }
        return ans;
    }

    private int upperBound(long[] nums, long target) {
        int left = -1, right = nums.length; // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] <= target
            // nums[right] > target
            int mid = (left + right) >>> 1;
            if (nums[mid] > target) {
                right = mid; // 二分范圍縮小到 (left, mid)
            } else {
                left = mid; // 二分范圍縮小到 (mid, right)
            }
        }
        return right;
    }


}






