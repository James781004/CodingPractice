package WeeklyContest;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class Week_363 {
    // https://leetcode.cn/problems/sum-of-values-at-indices-with-k-set-bits/solutions/2446045/ku-han-shu-xie-fa-pythonjavacgo-by-endle-hull/
    // 把所有滿足下標的二進制中的 1 的個數等於 k 的 nums[i] 加起來，就是答案。
    public int sumIndicesWithKSetBits(List<Integer> nums, int k) {
        int ans = 0, n = nums.size();
        for (int i = 0; i < n; i++) {
            if (Integer.bitCount(i) == k) {
                ans += nums.get(i);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/happy-students/solutions/2446022/pai-xu-pythonjavacgojs-by-endlesscheng-ptzl/
    public int countWays(List<Integer> nums) {
        int[] a = nums.stream().mapToInt(i -> i).toArray();
        Arrays.sort(a); // 從小到大排序
        int n = a.length;
        int ans = a[0] > 0 ? 1 : 0; // 如果 nums[0]>0，那麼所有 nums[i] 都是大於 0 的，可以一個學生都不選
        for (int i = 0; i < n - 1; i++) {
            // 如果 nums[i] < i+1 < nums[i+1]，
            // 這意味著選擇 nums[0] 到 nums[i] 這一共 i+1 個學生，是滿足要求的：
            // 由於數組已經排好序，nums[0] 到 nums[i] 都是小於 i+1 的，
            // 而 nums[i+1] 到 nums[n−1] 都是大於 i+1 的。
            if (a[i] < i + 1 && i + 1 < a[i + 1]) {
                ans++;
            }
        }

        // 如果 i=n−1，我們只需要判斷是否滿足 nums[i] < n。
        return ans + (a[n - 1] < n ? 1 : 0);
    }

    // https://leetcode.cn/problems/happy-students/solutions/2446018/treemapji-shu-qian-mian-lei-jia-by-chen-w23gw/
    // 要讓所有學生保持開心。
    // 條件1是 組員數 大於 nums[i]。
    // TreeMap計數 + 前面累加。累加用sum記錄。
    // 條件2是 sum 小於 TreeMap的當前key的nextKey。nextKey用treeMap.higherKey()來求。
    public int countWays2(List<Integer> nums) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int i : nums) treeMap.put(i, treeMap.getOrDefault(i, 0) + 1);

        int res = 0;
        if (!treeMap.containsKey(0)) res++;
        int sum = 0;
        for (int i : treeMap.keySet()) {
            sum = sum + treeMap.get(i);
            Integer higher = treeMap.higherKey(i);
            if (sum > i) {
                if (higher == null) res++;
                if (higher != null && sum < higher) res++;
            }
        }
        return res;
    }


    // https://leetcode.cn/problems/maximum-number-of-alloys/solutions/2446024/er-fen-da-an-fu-ti-dan-by-endlesscheng-3jdr/
    public int maxNumberOfAlloys(int n, int k, int budget, List<List<Integer>> composition, List<Integer> stock, List<Integer> cost) {
        int res = 0;
        int mx = stock.get(0) + budget;
        for (int st : stock)
            mx = Math.min(st + budget, mx);
        for (List<Integer> com : composition) {
            int left = 0, right = mx + 1;   // 二分查找能生產的數量
            while (left + 1 < right) {
                int mid = (left + right) >> 1;
                if (check(n, mid, budget, stock, com, cost))
                    // 能夠生產mid件則往大的尋找
                    left = mid;
                else
                    right = mid;
            }
            res = Math.max(res, left);
        }
        return res;
    }

    private boolean check(int n, int num, int budget, List<Integer> stock, List<Integer> com, List<Integer> cost) {
        // 能否生產num個產品
        long money = 0;
        for (int i = 0; i < n; i++) {
            long need = com.get(i) * num;
            if (stock.get(i) < need) {
                // 第i個零件的庫存不夠
                money += (need - stock.get(i)) * cost.get(i);   // 需要花錢
                if (money > budget)
                    return false;
            }
        }
        return true;
    }


    // https://leetcode.cn/problems/maximum-element-sum-of-a-complete-subset-of-indices/solutions/2446037/an-zhao-corei-fen-zu-pythonjavacgo-by-en-i6nu/
    // 按照下標的 core 值分組，累加同一組的元素和，最大元素和即為答案
    public long maximumSum(List<Integer> nums) {
        long ans = 0;
        int n = nums.size();
        long[] sum = new long[n + 1];
        for (int i = 0; i < nums.size(); i++) {
            int c = core(i + 1);
            sum[c] += nums.get(i);
            ans = Math.max(ans, sum[c]);
        }
        return ans;
    }

    // n 除去完全平方因子後的剩餘結果
    private int core(int n) {
        int res = 1;
        for (int i = 2; i * i <= n; i++) { // i 最多枚舉到 min(x, n/x)
            int e = 0;
            while (n % i == 0) {
                e ^= 1;
                n /= i;
            }
            if (e == 1) { // e 是奇數，要保留 i
                res *= i;
            }
        }
        if (n > 1) { // n 除去完全平方因子後還大於 1，表示可能有大質數因子存在，保留 n
            res *= n;
        }
        return res;
    }


    public long maximumSum2(List<Integer> nums) {
        int[] a = nums.stream().mapToInt(i -> i).toArray();
        long ans = 0;
        int n = a.length;
        for (int i = 1; i <= n; i++) {
            long sum = 0;
            for (int j = 1; i * j * j <= n; j++) {
                sum += a[i * j * j - 1]; // -1 是因為數組下標從 0 開始
            }
            ans = Math.max(ans, sum);
        }
        return ans;
    }
}
