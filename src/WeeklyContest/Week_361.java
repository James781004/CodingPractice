package WeeklyContest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Week_361 {
    // https://leetcode.cn/problems/count-symmetric-integers/solutions/2424072/mo-ni-jie-jue-by-_ak-wgpg/
    public int countSymmetricIntegers(int low, int high) {
        int cnt = 0;
        while (low <= high) {
            if (check(low)) cnt++;
            low++;
        }
        return cnt;
    }

    boolean check(int num) {
        String s = "" + num;
        int len = s.length();
        if (len % 2 != 0) return false;
        int l = 0, r = len - 1;
        int lc = 0, rc = 0;
        while (l < r) {
            lc += s.charAt(l++) - '0';
            rc += s.charAt(r--) - '0';

        }
        return lc == rc;
    }


    // https://leetcode.cn/problems/minimum-operations-to-make-a-special-number/solutions/2424069/bian-cheng-0-huo-zui-hou-liang-wei-bian-ry8ht/
    public int minimumOperations(String num) {
        int len = num.length();
        char[] ch = num.toCharArray();
        int zeroCount = 0;
        for (int i : ch) {
            if (i == '0') zeroCount++;
        }
        int toZero = len - zeroCount; // 變成0 需要的操作次數,是把裡面的非0都刪除了。

        int doubleZero = minCheck('0', '0', len, ch); // 最後兩位變成是 00 需要的次數
        int twentyFiveCount = minCheck('2', '5', len, ch); // 最後兩位變成是 25 需要的次數
        int fiftyCount = minCheck('5', '0', len, ch); // 最後兩位變成是 50 需要的次數
        int seventyFiveCount = minCheck('7', '5', len, ch); // 最後兩位變成是 75 需要的次數

        // 返回5種情況中的最小值。
        return Math.min(Math.min(Math.min(Math.min(toZero, doubleZero), twentyFiveCount), fiftyCount), seventyFiveCount);
    }

    // 為了美觀 第一個參數是 倒數第二個字符，第二個參數是 倒數第一個字符。
    public int minCheck(char secondChar, char firstChar, int len, char[] ch) {
        int res = len; // 結果初始為字符串長度。
        boolean first = false;
        boolean second = false;
        int thisCount = 0;
        for (int i = len - 1; i >= 0; i--) {
            if (first == false && ch[i] == firstChar) first = true; // 找到尾數 (2'5', 5'0', 7'5')
            else if (first && ch[i] == secondChar) second = true; // 找到尾數的前提下去找頭數 ('2'5, '5'0, '7'5)
            else thisCount++; // 需要刪掉當前下標數字的操作數

            if (first && second) { // 頭尾都找到的狀況下，才統計長度 (把頭尾接上，中間全部刪掉的操作數)
                res = thisCount;
                break;
            }
        }
        return res;
    }


    // https://leetcode.cn/problems/count-of-interesting-subarrays/solutions/2424063/qian-zhui-he-ha-xi-biao-fu-ti-dan-by-end-74bb/
    public long countInterestingSubarrays(List<Integer> nums, int modulo, int k) {
        // 判斷每個數字是否 % module == k
        // 前綴和 記錄 一共有多少個符合的數字
        // 如果當前前綴和是 10
        // 判斷前綴和個數 是否符合 % module == k
        // cache 緩存 所有的前綴和 % module 的結果的個數，後續cache的時候 ，如果該值已存在，代表符合條件
        Map<Integer, Integer> map = new HashMap<>();
        int len = nums.size();
        int[] preSum = new int[len + 1];
        long ans = 0;
        map.put(0, 1);
        for (int i = 0; i < len; i++) {
            int num = nums.get(i);
            preSum[i + 1] = preSum[i] + (num % modulo == k ? 1 : 0);
            int ys = preSum[i + 1] % modulo;
            int ysk = (modulo + ys - k) % modulo;
            // 余數 ys
            // 尋找前面存儲的前綴和 余數 為  ys - k 的 所有可能
            // 則有 (i - j) % module == k
            int yskCount = map.getOrDefault(ysk, 0);
            ans += yskCount;
            map.put(ys, map.getOrDefault(ys, 0) + 1);
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-edge-weight-equilibrium-queries-in-a-tree/solutions/2424398/java-bei-zeng-er-fen-qiu-lca-by-xie-bin-9fgl0/
    int[][] d;
    Map<Integer, List<int[]>> g = new HashMap<>();

    public int[] minOperationsQueries(int n, int[][] edges, int[][] queries) {
        int root = 0;
        d = new int[n][27];
        for (int[] e : edges) {
            g.putIfAbsent(e[0], new ArrayList<>());
            g.putIfAbsent(e[1], new ArrayList<>());
            g.get(e[0]).add(new int[]{e[1], e[2]});
            g.get(e[1]).add(new int[]{e[0], e[2]});
        }
        dfs(root, -1);
        LCA lca = new LCA(n, edges, root);
        int len = queries.length;
        int[] ret = new int[len];
        for (int i = 0; i < len; i++) {
            int x = queries[i][0], y = queries[i][1];
            int ca = lca.getAncestors(x, y);
            // System.out.println(x+" " + y+" ...: "+ca);
            int[] t = new int[27];
            for (int j = 0; j < 27; j++) {
                t[j] += d[x][j] + d[y][j];
                t[j] -= 2 * d[ca][j];
            }
            // System.out.println(Arrays.toString(d[x]));
            // System.out.println(Arrays.toString(d[y]));
            int total = 0, max = 0;
            for (int xx : t) {
                total += xx;
                max = Math.max(max, xx);
            }
            ret[i] = total - max;
        }
        return ret;
    }

    void dfs(int c, int f) {
        for (int[] cur : g.getOrDefault(c, new ArrayList<>())) {
            if (cur[0] == f) continue;
            d[cur[0]][cur[1]]++;
            for (int i = 0; i < 27; i++) {
                d[cur[0]][i] += d[c][i];
            }
            dfs(cur[0], c);
        }
    }

    class LCA {
        Map<Integer, List<Integer>> g;
        int depth;
        int[] d;
        int[] fa;
        int[][] f;

        public LCA(int n, int[][] edges, int root) {
            g = new HashMap<>();
            fa = new int[n];
            fa[root] = -1;
            d = new int[n];
            for (int[] e : edges) {
                g.putIfAbsent(e[0], new ArrayList<>());
                g.putIfAbsent(e[1], new ArrayList<>());
                g.get(e[0]).add(e[1]);
                g.get(e[1]).add(e[0]);
            }
            dfs(root, -1, 0);

            f = new int[n][33];
            for (int i = 0; i < n; i++) {
                f[i][0] = i;
            }
            for (int i = 1; i <= 32; i++) {
                for (int j = 0; j < n; j++) {
                    if (j == root) {
                        f[j][i] = root;
                    } else {
                        f[j][i] = (i == 1 ? fa[j] : f[f[j][i - 1]][i - 1]);
                    }
                }
            }

        }

        public int[] getAncestors(int n, int[][] edges, int[][] queries) {
            LCA lca = new LCA(n, edges, 0);
            int[] ans = new int[queries.length];
            for (int i = 0; i < queries.length; i++) {
                ans[i] = getAncestors(queries[i][0], queries[i][1]);
            }
            return ans;
        }

        public int getAncestors(int x, int y) {
            int l = 0, r = depth;
            int xd = d[x], yd = d[y];
            if (xd > yd) {
                x = getAncestorsForOne(x, xd - yd);
            } else if (xd < yd) {
                y = getAncestorsForOne(y, yd - xd);
            }
            if (x == y) {
                return x;
            }
            while (l < r) {
                int mid = (l + r) >> 1;
                int fx = getAncestorsForOne(x, mid);
                int fy = getAncestorsForOne(y, mid);
                if (fx == fy) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            return getAncestorsForOne(x, r);
        }

        public int getAncestorsForOne(int node, int num) {
            int cur = node;
            for (int i = 31; i >= 0; i--) {
                if ((num & (1 << i)) > 0) {
                    cur = f[cur][i + 1];
                }
            }
            return cur;
        }


        void dfs(int c, int f, int curDepth) {
            depth = Math.max(depth, curDepth);
            d[c] = curDepth;
            for (int x : g.getOrDefault(c, new ArrayList<>())) {
                if (x == f) continue;
                fa[x] = c;
                dfs(x, c, curDepth + 1);
            }
        }
    }


}
