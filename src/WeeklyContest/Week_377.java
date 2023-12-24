package WeeklyContest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Week_377 {
    // https://leetcode.cn/problems/minimum-number-game/solutions/2577915/pai-xu-hou-liang-liang-jiao-huan-pythonj-udxc/
    // 把 nums 排序後，兩個兩個一對交換，就得到了答案。
    public int[] numberGame(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i += 2) {
            int tmp = nums[i - 1];
            nums[i - 1] = nums[i];
            nums[i] = tmp;
        }
        return nums;
    }


    // https://leetcode.cn/problems/maximum-square-area-by-removing-fences-from-a-field/solutions/2577867/fen-bie-ji-suan-bao-li-mei-ju-by-endless-zxhr/
    // 水平柵欄和垂直柵欄分開計算。
    //
    // 對於水平柵欄，計算出任意兩個柵欄之間的距離，存到一個哈希表 h 中。
    // 對於垂直柵欄，計算出任意兩個柵欄之間的距離，存到一個哈希表 v 中。
    // 答案就是 hhh 和 vvv 交集中的最大值的平方。如果不存在最大值，返回 −1。
    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        Set<Integer> h = f(hFences, m);
        Set<Integer> v = f(vFences, n);
        int ans = 0;
        for (int x : h) {
            if (v.contains(x)) {
                ans = Math.max(ans, x);
            }
        }
        return ans > 0 ? (int) ((long) ans * ans % 1_000_000_007) : -1;
    }

    private Set<Integer> f(int[] a, int mx) {
        int len = a.length;
        a = Arrays.copyOf(a, len + 2);
        a[len] = 1;
        a[len + 1] = mx;
        Arrays.sort(a);

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                set.add(a[j] - a[i]);
            }
        }
        return set;
    }

    // https://leetcode.cn/problems/minimum-cost-to-convert-string-i/solutions/2577903/floyd-suan-fa-by-endlesscheng-ug8s/
    // 建圖，從 original[i] 向 changed[i] 連邊，邊權為 cost[i]。
    // 然後用 Floyd 算法求圖中任意兩點最短路，得到 dis 矩陣。
    // 這裡得到的 dis[i][j] 表示字母 i 通過若干次替換操作變成字母 j 的最小成本。
    // 最後累加所有 dis[original[i]][changed[i]]，即為答案。如果答案為無窮大，返回 −1。
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        int[][] dis = new int[26][26];
        for (int i = 0; i < 26; i++) {
            Arrays.fill(dis[i], Integer.MAX_VALUE / 2);
            dis[i][i] = 0;
        }
        for (int i = 0; i < cost.length; i++) {
            int x = original[i] - 'a';
            int y = changed[i] - 'a';
            dis[x][y] = Math.min(dis[x][y], cost[i]);
        }
        for (int k = 0; k < 26; k++) {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
                }
            }
        }

        long ans = 0;
        for (int i = 0; i < source.length(); i++) {
            int d = dis[source.charAt(i) - 'a'][target.charAt(i) - 'a'];
            if (d == Integer.MAX_VALUE / 2) {
                return -1;
            }
            ans += d;
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-cost-to-convert-string-ii/solutions/2577877/zi-dian-shu-floyddp-by-endlesscheng-oi2r/
    private Node root = new Node();
    private int sid = 0;
    private char[] s, t;
    private int[][] dis;
    private long[] memo;

    public long minimumCost(String source, String target, String[] original, String[] changed, int[] cost) {
        // 初始化距離矩陣
        int m = cost.length;
        dis = new int[m * 2][m * 2];
        for (int i = 0; i < dis.length; i++) {
            Arrays.fill(dis[i], Integer.MAX_VALUE / 2);
            dis[i][i] = 0;
        }
        for (int i = 0; i < cost.length; i++) {
            int x = put(original[i]);
            int y = put(changed[i]);
            dis[x][y] = Math.min(dis[x][y], cost[i]);
        }

        // Floyd 求任意兩點最短路
        for (int k = 0; k < sid; k++) {
            for (int i = 0; i < sid; i++) {
                if (dis[i][k] == Integer.MAX_VALUE / 2) {
                    continue;
                }
                for (int j = 0; j < sid; j++) {
                    dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
                }
            }
        }

        s = source.toCharArray();
        t = target.toCharArray();
        memo = new long[s.length];
        Arrays.fill(memo, -1);
        long ans = dfs(0);
        return ans < Long.MAX_VALUE / 2 ? ans : -1;
    }

    private int put(String s) {
        Node o = root;
        for (char b : s.toCharArray()) {
            int i = b - 'a';
            if (o.son[i] == null) {
                o.son[i] = new Node();
            }
            o = o.son[i];
        }
        if (o.sid < 0) {
            o.sid = sid++;
        }
        return o.sid;
    }

    private long dfs(int i) {
        if (i >= s.length) {
            return 0;
        }
        if (memo[i] != -1) { // 之前算過
            return memo[i];
        }
        long res = Long.MAX_VALUE / 2;
        if (s[i] == t[i]) {
            res = dfs(i + 1); // 不修改 source[i]
        }
        Node p = root, q = root;
        for (int j = i; j < s.length; j++) {
            p = p.son[s[j] - 'a'];
            q = q.son[t[j] - 'a'];
            if (p == null || q == null) {
                break;
            }
            if (p.sid < 0 || q.sid < 0) {
                continue;
            }
            // 修改從 i 到 j 的這一段
            int d = dis[p.sid][q.sid];
            if (d < Integer.MAX_VALUE / 2) {
                res = Math.min(res, d + dfs(j + 1));
            }
        }
        return memo[i] = res; // 記憶化
    }

    class Node {
        Node[] son = new Node[26];
        int sid = -1; // 字符串的編號
    }
}
