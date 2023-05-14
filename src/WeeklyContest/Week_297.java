package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Week_297 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2303.Calculate%20Amount%20Paid%20in%20Taxes/README.md
    public double calculateTax(int[][] brackets, int income) {
        int ans = 0, prev = 0;
        for (int[] e : brackets) {
            int upper = e[0], percent = e[1];
            ans += Math.max(0, Math.min(income, upper) - prev) * percent;
            prev = upper;
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2304.Minimum%20Path%20Cost%20in%20a%20Grid/README.md
    public int minPathCost(int[][] grid, int[][] moveCost) {
        int m = grid.length, n = grid[0].length;
        int[] f = grid[0]; // 定義 f[i][j] 表示從第一行出發，到達第 i 行第 j 列的最小路徑代價 (每次轉移只需要用到上一行的狀態，因此可以將空間復雜度優化)
        final int inf = 1 << 30;
        for (int i = 1; i < m; i++) {
            int[] g = new int[n];
            Arrays.fill(g, inf);
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    // 每次只能從上一行的某一列移動到當前行的某一列
                    // f[i][j] 值可以從 f[i-1][k] 轉移而來
                    g[j] = Math.min(g[j], f[k] + moveCost[grid[i - 1][k]][j] + grid[i][j]);
                }
            }
            f = g;
        }

        // 最終答案即為 f[m-1][j] 的最小值
        // return Arrays.stream(f).min().getAsInt();
        int ans = inf;
        for (int v : f) {
            ans = Math.min(ans, v);
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2305.Fair%20Distribution%20of%20Cookies/README.md
    class DistributeCookies {
        private int[] cookies;
        private int[] cnt; // 存儲每個孩子分到的餅干數量
        private int k;
        private int n;
        private int ans = 1 << 30; // 維護當前的最小不公平程度

        public int distributeCookies(int[] cookies, int k) {
            n = cookies.length;
            cnt = new int[k];
            // 升序排列
            Arrays.sort(cookies);
            this.cookies = cookies;
            this.k = k;
            // 這裡搜索順序是 n-1, n-2,...0
            dfs(n - 1);
            return ans;
        }

        private void dfs(int i) {
            if (i < 0) {
                // ans = Arrays.stream(cnt).max().getAsInt();
                ans = 0;
                for (int v : cnt) {
                    ans = Math.max(ans, v);
                }
                return;
            }

            // 從第一個零食包開始，對於當前零食包 i，枚舉每個孩子 j
            for (int j = 0; j < k; j++) {
                // 如果當前零食包中的餅干 cookies[i] 分給孩子 j 後，使得不公平程度大於等於 ans，
                // 或者當前孩子已有的餅干數量與前一個孩子相同，
                // 那麼我們不需要考慮將當前零食包中的餅干分給孩子 j ，直接跳過（剪枝）
                if (cnt[j] + cookies[i] >= ans || (j > 0 && cnt[j] == cnt[j - 1])) {
                    continue;
                }

                // 將當前零食包中的餅干 cookies[i] 分給孩子 j，然後繼續考慮下一個零食包
                cnt[j] += cookies[i];
                dfs(i - 1);
                cnt[j] -= cookies[i];
            }
        }


        // https://leetcode.cn/problems/fair-distribution-of-cookies/solution/by-endlesscheng-80ao/
        // https://www.bilibili.com/video/BV1aT41157bh/
        public int distributeCookiesDP(int[] cookies, int k) {
            int n = cookies.length;
            int[] sum = new int[1 << n];
            for (int i = 0; i < n; i++)
                for (int j = 0, bit = 1 << i; j < bit; j++)
                    sum[bit | j] = sum[j] + cookies[i];

            int[] f = sum.clone();  // 定義 f[i][j] 表示前 i 個孩子分配的餅干集合為 j 時，前 i 個孩子的不公平程度的最小值
            for (int i = 1; i < k; i++) {
                // 倒序枚舉 j，f 的第一個維度可以省略。
                // sum 也可以通過預處理得到
                for (int j = (1 << n) - 1; j > 0; j--) {
                    for (int s = j; s > 0; s = (s - 1) & j) {
                        // j\s 表示從集合 j 中去掉集合 s 的元素後，剩余元素組成的集合。
                        // 考慮給第 i 個孩子分配的餅干集合為 s，設集合 s 的元素和為 sum[s]，分類討論：
                        // 如果 sum[s]>f[i−1][j∖s]，說明給第 i 個孩子分配的餅干比前面的孩子多，不公平程度變為 sum[s]；
                        // 如果 sum[s]≤f[i−1][j∖s]，說明給第 i 個孩子分配的餅干沒有比前面的孩子多，不公平程度不變，仍為 f[i−1][j∖s]。
                        // 因此，給第 i 個孩子分配餅干集合 s 後，前 i 個孩子的不公平程度為max(f[i−1][j∖s],sum[s])
                        f[j] = Math.min(f[j], Math.max(f[j ^ s], sum[s]));
                    }
                }
            }
            return f[(1 << n) - 1];
        }
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2306.Naming%20a%20Company/README.md
    public long distinctNames(String[] ideas) {
        Set<String> s = new HashSet<>();
        for (String v : ideas) {
            s.add(v);
        }

        // f[i][j] 表示有多少個首字母為 i 的字符串，將首字符換成 j 後，未在 ideas 中出現過的字符串個數
        int[][] f = new int[26][26];
        for (String v : ideas) {
            char[] t = v.toCharArray();
            int i = t[0] - 'a';
            for (int j = 0; j < 26; ++j) {
                t[0] = (char) (j + 'a');
                if (!s.contains(String.valueOf(t))) {
                    ++f[i][j];
                }
            }
        }

        // 針對每個idea，若首字母可以換成i，則累加首字母為i且可以換成idea.charAt(0)的單詞數量
        long ans = 0;
        for (String v : ideas) {
            char[] t = v.toCharArray();
            int i = t[0] - 'a';
            for (int j = 0; j < 26; ++j) {
                t[0] = (char) (j + 'a');
                if (!s.contains(String.valueOf(t))) {
                    ans += f[j][i];
                }
            }
        }
        return ans;
    }

    // https://leetcode.cn/problems/naming-a-company/solution/by-endlesscheng-ruz8/
    public long distinctNames2(String[] ideas) {

        // hash分類。key為後綴，value為首字母
        HashMap<String, Integer> hash = new HashMap<>();

        // 每兩個不同首字母的字符串所出現的相同後綴
        int[][] bad = new int[26][26];

        // 各首字母的出現次數
        long[] number = new long[26];

        // 循環每一個idea
        for (String s : ideas) {
            int i = s.charAt(0) - 'a'; // 當前字符首字母
            s = s.substring(1); // 當前字符去掉首字母的後綴

            // 先取出s前一次的數據mask，然後將最新數據(以mask形式保存)加入到hash分類中
            // (mask | 1 << i): 表示Bitwise OR |，| 符號可以把位元強制標記成 1
            int mask = hash.getOrDefault(s, 0);
            hash.put(s, mask | 1 << i);

            // 首字母出現次數+1
            number[i]++;

            // 求當前後綴的首字母中存在的首字母[j]與最新加入的首字母[i]的位置，並記錄
            // 此證明具有i，j首字母的兩個後綴可組合的個數應當減一
            for (int j = 0; j < 26; ++j)
                // (mask >> j & 1): 把 mask 算術右移 j 位，然後檢測最低位是否為1
                // 這邊是判斷當前字符串是否為 ideas 中出現過的字符串，1 表示出現過
                if ((mask >> j & 1) == 1) {
                    bad[i][j]++; // 統計 i 無法與多少個 j 開頭的字符串交換
                    bad[j][i]++; // 統計 j 無法與多少個 i 開頭的字符串交換
                }
        }

        // 統計兩個不同首字母所存在的後綴個數去除其交集不可組合的後綴個數的相乘結果
        long end = 0L;
        for (int i = 0; i < 26; i++)
            for (int j = i + 1; j < 26; j++)
                end += (number[i] - bad[i][j]) * (number[j] - bad[i][j]);

        // 因為正反變兩倍(a + b 以及 b + a)，所以位運算向左移動一位
        return end << 1;
    }
}
