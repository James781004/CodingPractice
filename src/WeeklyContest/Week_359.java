package WeeklyContest;

import java.util.*;

public class Week_359 {
    // https://github.com/doocs/leetcode/blob/main/solution/2800-2899/2828.Check%20if%20a%20String%20Is%20an%20Acronym%20of%20Words/README.md
    public boolean isAcronym(List<String> words, String s) {
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            sb.append(w.charAt(0));
        }
        return sb.toString().equals(s);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2800-2899/2829.Determine%20the%20Minimum%20Sum%20of%20a%20k-avoiding%20Array/README.md
    public int minimumSum(int n, int k) {
        int s = 0, i = 1;
        boolean[] vis = new boolean[k + n * n + 1];  // 可以用Set處理
        while (n-- > 0) {
            while (vis[i]) {
                ++i;
            }
            vis[i] = true;
            if (k >= i) {
                vis[k - i] = true;
            }
            s += i;
        }
        return s;
    }


    // https://leetcode.cn/problems/determine-the-minimum-sum-of-a-k-avoiding-array/solutions/2396408/o1-gong-shi-pythonjavacgo-by-endlesschen-cztk/
    // 數學解
    public int minimumSum2(int n, int k) {
        int m = Math.min(k / 2, n); // 前半段組成元素長度

        // 所以前半段就是 [1...m] 等差數列：m * (m + 1) / 2
        // 後半段為 [k...k+n-m-1] 等差數列：(k * 2 + n - m - 1) * (n - m) / 2
        // 注意：因為不可能取 0 所以後半段最小可以從 k 開始
        return (m * (m + 1) + (k * 2 + n - m - 1) * (n - m)) / 2;
    }


    // https://leetcode.cn/problems/maximize-the-profit-as-the-salesman/solutions/2396402/xian-xing-dpfu-xiang-si-ti-mu-pythonjava-wmh7/
    public int maximizeTheProfit(int n, List<List<Integer>> offers) {
        List<int[]>[] groups = new ArrayList[n];
        Arrays.setAll(groups, e -> new ArrayList<>());

        // 把 end 相同的組合放在一起，方便接下來枚舉
        for (List<Integer> offer : offers) // groups[end] = {start, price}
            groups[offer.get(1)].add(new int[]{offer.get(0), offer.get(2)});

        int[] f = new int[n + 1];  // 定義 f[end+1] 表示銷售編號 [0...end] 的房屋的最大盈利 (為避免負數，下標+1方便處理)
        for (int end = 0; end < n; end++) {
            f[end + 1] = f[end]; // 不賣 f[end+1] = f[end]
            for (int[] p : groups[end]) // 賣，遍歷所有 end 的購買請求取最大值。
                // 這邊是枚舉並比較 p_start 之前的區間累積最大值加上 p_price
                f[end + 1] = Math.max(f[end + 1], f[p[0]] + p[1]);
        }
        return f[n];
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2800-2899/2831.Find%20the%20Longest%20Equal%20Subarray/README.md
    // 雙指針維護一個單調變長的窗口，用哈希表維護窗口中每個元素出現的次數
    public int longestEqualSubarray(List<Integer> nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int mx = 0, l = 0;
        for (int r = 0; r < nums.size(); ++r) {
            cnt.merge(nums.get(r), 1, Integer::sum); // 右指針指向的元素加入窗口，更新哈希表
            mx = Math.max(mx, cnt.get(nums.get(r))); // 更新窗口中出現次數最多的元素個數

            // r - l + 1 表示當前窗口總長，mx 表示當前出現次數最多的元素個數
            // 窗口總長減去 mx，剩下就是需要移除的部分，移除後就會形成當前最長等值子數組（題目要求）
            // 需要刪除的元素個數超過了 k 時，就移動一次左指針，然後更新哈希表
            if (r - l + 1 - mx > k) {
                cnt.merge(nums.get(l++), -1, Integer::sum);
            }
        }
        return mx;
    }
}
