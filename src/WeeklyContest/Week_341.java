package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_341 {
    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2643.Row%20With%20Maximum%20Ones/README.md
    public int[] rowAndMaximumOnes(int[][] mat) {
        int[] ans = new int[2];
        for (int i = 0; i < mat.length; i++) {
            int cnt = Arrays.stream(mat[i]).sum();
//            for (int x : mat[i]) {
//                if (x == 1) cnt++;
//            }
            if (ans[i] < cnt) {
                ans[0] = i;
                ans[1] = cnt;
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2644.Find%20the%20Maximum%20Divisibility%20Score/README.md
    public int maxDivScore(int[] nums, int[] divisors) {
        int ans = divisors[0];
        int mx = 0;
        for (int div : divisors) {
            int cnt = 0;
            for (int x : nums) {
                if (x % div == 0) cnt++;
            }
            if (mx < cnt) {
                mx = cnt;
                ans = div;
            } else if (mx == cnt) {
                ans = Math.min(ans, div);
            }
        }

        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2645.Minimum%20Additions%20to%20Make%20Valid%20String/README.md
    public int addMinimum(String word) {
        String s = "abc";
        int ans = 0, n = word.length();
        for (int i = 0, j = 0; j < n; i = (i + 1) % 3) {  // i右移一位，超過3就%3
            if (word.charAt(j) != s.charAt(i)) {
                ans++;  // 需要插入s.charAt(i)，將答案加1
            } else {
                j++;  // 可以匹配，j右移一位
            }
        }

        // 判斷 word 的最後一個字符是否為 'b' 或者 'a'，
        // 如果是，則需要插入 'c' 或者 'bc'，將答案加 1 或者 2 後返回即可。
        if (word.charAt(n - 1) != 'c') {
            ans += word.charAt(n - 1) == 'b' ? 1 : 2;
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimize-the-total-price-of-the-trips/solution/lei-si-da-jia-jie-she-iii-pythonjavacgo-4k3wq/
    // https://www.bilibili.com/video/BV1ng4y1T7QA/
    private List<Integer>[] g;
    private int[] price, cnt;
    private int end;

    public int minimumTotalPrice(int n, int[][] edges, int[] price, int[][] trips) {
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建樹
        }

        this.price = price;

        cnt = new int[n];  // 每個節點在路進上的經過次數
        for (int[] t : trips) {
            end = t[1];
            path(t[0], -1);
        }

        int[] p = dfs(0, -1);
        return Math.min(p[0], p[1]);
    }


    private boolean path(int x, int fa) {
        if (x == end) { // 到達終點（注意樹只有唯一的一條簡單路徑）
            cnt[x]++; // 統計從 start 到 end 的路徑上的點經過了多少次
            return true; // 找到終點
        }
        for (int y : g[x]) {
            if (y != fa && path(y, x)) {
                cnt[x]++; // 統計從 start 到 end 的路徑上的點經過了多少次
                return true; // 找到終點
            }
        }
        return false; // 未找到終點
    }


    // 類似 337. 打家劫舍 III https://leetcode.cn/problems/house-robber-iii/
    private int[] dfs(int x, int fa) {
        int notHalf = price[x] * cnt[x]; // x 不變
        int half = notHalf / 2; // x 減半
        for (int y : g[x])
            if (y != fa) {
                int[] p = dfs(y, x); // 計算 y 不變/減半的最小價值總和
                notHalf += Math.min(p[0], p[1]); // x 不變，那麼 y 可以不變，可以減半，取這兩種情況的最小值
                half += p[0]; // x 減半，那麼 y 只能不變
            }
        return new int[]{notHalf, half};
    }

}
