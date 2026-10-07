package EndlessCheng.GenreMenu.DP.Linear.LIS.Advance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LargestDivisibleSubset {

    // https://leetcode.cn/problems/largest-divisible-subset/solutions/3641565/san-chong-fang-fa-ji-yi-hua-sou-suo-di-t-pift/
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);

        int n = nums.length;
        int[] memo = new int[n];
        int[] from = new int[n];
        Arrays.fill(from, -1);
        int maxF = 0;
        int maxI = 0;

        for (int i = 0; i < n; i++) {
            int f = dfs(i, nums, memo, from);
            if (f > maxF) {
                maxF = f;
                maxI = i; // 最長合法子序列的最後一個數的下標
            }
        }

        List<Integer> path = new ArrayList<>(maxF); // 預分配空間
        for (int i = maxI; i >= 0; i = from[i]) {
            path.add(nums[i]);
        }
        return path; // 不需要 reverse，任意順序返回均可
    }

    private int dfs(int i, int[] nums, int[] memo, int[] from) {
        if (memo[i] > 0) { // 之前計算過
            return memo[i];
        }
        int res = 0;
        for (int j = 0; j < i; j++) {
            if (nums[i] % nums[j] != 0) {
                continue;
            }
            int f = dfs(j, nums, memo, from);
            if (f > res) {
                res = f;
                from[i] = j; // 記錄最佳轉移來源
            }
        }
        return memo[i] = res + 1; // 記憶化
    }


    public List<Integer> largestDivisibleSubsetDP(int[] nums) {
        Arrays.sort(nums);

        int n = nums.length;
        int[] f = new int[n];
        int[] from = new int[n];
        Arrays.fill(from, -1);
        int maxI = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && f[j] > f[i]) {
                    f[i] = f[j];
                    from[i] = j; // 記錄最佳轉移來源
                }
            }
            f[i]++;
            if (f[i] > f[maxI]) {
                maxI = i; // 最長合法子序列的最後一個數的下標
            }
        }

        List<Integer> path = new ArrayList<>(f[maxI]); // 預分配空間
        for (int i = maxI; i >= 0; i = from[i]) {
            path.add(nums[i]);
        }
        return path; // 不需要 reverse，任意順序返回均可
    }


    public List<Integer> largestDivisibleSubsetDP2(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int maxNum = nums[nums.length - 1];

        int[] f = new int[n];
        int[] from = new int[n];
        Arrays.fill(from, -1);
        int maxF = 0;
        int maxI = 0;
        List<Integer> validIdx = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int j : validIdx) { // 只需要遍歷在 validIdx 中的下標
                if (x % nums[j] == 0 && f[j] > f[i]) {
                    f[i] = f[j];
                    from[i] = j;
                }
            }
            f[i]++;
            if (f[i] > maxF) {
                maxF = f[i];
                maxI = i;
                // maxF 變大，去掉 validIdx 中的無用數據（這裡直接生成一個新的）
                List<Integer> newValidIdx = new ArrayList<>();
                for (int j : validIdx) {
                    if (f[j] + 31 - Integer.numberOfLeadingZeros(maxNum / nums[j]) > maxF) {
                        newValidIdx.add(j);
                    }
                }
                validIdx = newValidIdx;
            }
            if (f[i] + 31 - Integer.numberOfLeadingZeros(maxNum / x) > maxF) {
                validIdx.add(i);
            }
        }

        List<Integer> path = new ArrayList<>(maxF);
        for (int i = maxI; i >= 0; i = from[i]) {
            path.add(nums[i]);
        }
        return path;
    }


}
