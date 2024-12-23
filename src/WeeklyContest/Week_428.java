package WeeklyContest;

import java.util.*;

public class Week_428 {

    // https://leetcode.cn/problems/button-with-longest-push-time/solutions/3020689/3386-an-xia-shi-jian-zui-chang-de-an-niu-0wm2/
    public int buttonWithLongestTime(int[][] events) {
        int longestIndex = -1, longestTime = 0;
        int prev = 0;
        for (int[] event : events) {
            int index = event[0], time = event[1];
            int pressTime = time - prev;
            if (pressTime > longestTime || (pressTime == longestTime && index < longestIndex)) {
                longestIndex = index;
                longestTime = pressTime;
            }
            prev = time;
        }
        return longestIndex;
    }


    // https://leetcode.cn/problems/maximize-amount-after-two-days-of-conversions/solutions/3020665/jian-tu-zi-ding-xiang-xia-dfspythonjavac-um05/
    public double maxAmount(String initialCurrency, List<List<String>> pairs1, double[] rates1, List<List<String>> pairs2, double[] rates2) {
        Map<String, List<Pair>> g = buildGraph(pairs1, rates1);
        Map<String, Double> day1Amount = new HashMap<>();
        init(initialCurrency, 1.0, g, day1Amount);

        g = buildGraph(pairs2, rates2);
        Set<String> vis = new HashSet<>();
        for (Map.Entry<String, Double> e : day1Amount.entrySet()) {
            vis.clear();
            dfs(e.getKey(), e.getValue(), initialCurrency, g, vis);
        }
        return ans;
    }

    private double ans;

    private record Pair(String to, double rate) {
    }

    private Map<String, List<Pair>> buildGraph(List<List<String>> pairs, double[] rates) {
        Map<String, List<Pair>> g = new HashMap<>();
        for (int i = 0; i < pairs.size(); i++) {
            String x = pairs.get(i).get(0);
            String y = pairs.get(i).get(1);
            double r = rates[i];
            g.computeIfAbsent(x, k -> new ArrayList<>()).add(new Pair(y, r));
            g.computeIfAbsent(y, k -> new ArrayList<>()).add(new Pair(x, 1.0 / r));
        }
        return g;
    }

    private void init(String x, double curAmount, Map<String, List<Pair>> g, Map<String, Double> day1Amount) {
        day1Amount.put(x, curAmount);
        for (Pair p : g.get(x)) {
            // 每個節點只需遞歸一次（重復遞歸算出來的結果是一樣的，因為題目保證匯率沒有矛盾）
            if (!day1Amount.containsKey(p.to)) {
                init(p.to, curAmount * p.rate, g, day1Amount);
            }
        }
    }

    private boolean dfs(String x, double curAmount, String initialCurrency, Map<String, List<Pair>> g, Set<String> vis) {
        if (x.equals(initialCurrency)) {
            ans = Math.max(ans, curAmount);
            return true;
        }
        vis.add(x);
        if (!g.containsKey(x)) {
            return false;
        }
        for (Pair p : g.get(x)) {
            // 每個節點只需遞歸一次（重復遞歸算出來的結果是一樣的，因為題目保證匯率沒有矛盾）
            if (!vis.contains(p.to) && dfs(p.to, curAmount * p.rate, initialCurrency, g, vis)) {
                return true;
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/count-beautiful-splits-in-an-array/solutions/3020939/liang-chong-fang-fa-lcp-shu-zu-z-shu-zu-dwbrd/
    public int beautifulSplits(int[] nums) {
        int n = nums.length;
        // lcp[i][j] 表示 s[i:] 和 s[j:] 的最長公共前綴
        // 分類討論：
        // 如果 nums[i] != nums[j]，那麼 lcp[i][j]=0。
        // 如果 nums[i] = nums[j]，那麼問題變成計算後綴 nums[i+1:] 和後綴 nums[j+1:] 的最長公共前綴的長度，
        // 那麼 lcp[i][j]=lcp[i+1][j+1]+1。
        int[][] lcp = new int[n + 1][n + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= i; j--) {
                if (nums[i] == nums[j]) {
                    lcp[i][j] = lcp[i + 1][j + 1] + 1;
                }
            }
        }

        int ans = 0;

        // 如果第一段是第二段的前綴，那麼必須滿足：
        // 第一段的長度 i 不超過第二段的長度 j−i，即 i≤j−i。
        // nums 和後綴 nums[i:] 的最長公共前綴的長度至少是第一段的長度，即 lcp[0][i]≥i。
        // 如果第二段是第三段的前綴，那麼必須滿足：
        // 第二段的長度 j−i 不超過第三段的長度 n−j，即 j−i≤n−j。
        // 後綴 nums[i:] 和後綴 nums[j:] 的最長公共前綴的長度至少是第二段的長度，即 lcp[i][j]≥j−i。
        for (int i = 1; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (i <= j - i && lcp[0][i] >= i ||
                        j - i <= n - j && lcp[i][j] >= j - i) {
                    ans++;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-operations-to-make-character-frequencies-equal/solutions/3020630/mei-ju-dpfen-lei-tao-lun-pythonjavacgo-b-ahfn/
    public int makeStringGood(String s) {
        int[] cnt = new int[26];
        for (char b : s.toCharArray()) {
            cnt[b - 'a']++;
        }
        int m = Arrays.stream(cnt).max().getAsInt();

        int ans = s.length();
        int[] f = new int[27];
        for (int target = 0; target <= m; target++) {
            f[25] = Math.min(cnt[25], Math.abs(cnt[25] - target));
            for (int i = 24; i >= 0; i--) {
                int x = cnt[i];
                int y = cnt[i + 1];
                f[i] = f[i + 1] + Math.min(x, Math.abs(x - target));
                // 只有當 y 需要變大時，才去執行第三種操作
                if (y < target) { // y 變成 target（y 變成 0 的情況上面 calcOp(y) 計算了）
                    if (x > target) { // x 夾帶著第三種操作，變成 target
                        f[i] = Math.min(f[i], f[i + 2] + Math.max(x - target, target - y));
                    } else { // x 夾帶著第三種操作，變成 0
                        f[i] = Math.min(f[i], f[i + 2] + Math.max(x, target - y));
                    }
                }
            }
            ans = Math.min(ans, f[0]);
        }
        return ans;
    }

}









