package WeeklyContest;

import java.util.*;

public class Week_368 {
    // https://leetcode.cn/problems/minimum-sum-of-mountain-triplets-i/solutions/2493197/shuang-zhi-zhen-by-yechen-yy-16iz/
    // 先for循環取一個中間值 i，再使用雙指針分別遍歷 i 的左右兩邊 l,r，分別找一個最小值 與 i 比較。
    public int minimumSum(int[] nums) {
        int res = -1;
        for (int i = 1; i < nums.length; i++) {
            int l = 0, r = nums.length - 1;
            int minL = nums[l], minR = nums[r];
            while (l < i) {
                if (nums[l] < minL) {
                    minL = nums[l];
                }
                l++;
            }
            while (r > i) {
                if (nums[r] < minR) {
                    minR = nums[r];
                }
                r--;
            }
            if (minL < nums[i] && minR < nums[i]) {
                int sum = minL + nums[i] + minR;
                if (res == -1) {
                    res = sum;
                } else if (sum < res) {
                    res = sum;
                }
            }
        }
        return res;
    }


    // https://leetcode.cn/problems/minimum-sum-of-mountain-triplets-ii/solutions/2493231/java-lei-si-jie-yu-shui-by-chuilu-a45i/
    // 假設每個點都有可能是山頂，只要算出每個山頂的左側最小右側最小就可以了。
    // 首先從左往右遍歷每個節點，記錄當前節點左側的最小值，再從右往左遍歷，
    // 記錄每個節點右側的最小值。最後計算山頂左右兩側的最小值。
    // 類似接雨水
    public int minimumSumII(int[] nums) {
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];

        left[0] = Integer.MAX_VALUE;
        right[nums.length - 1] = Integer.MAX_VALUE;
        //  Left 表示每個節點的左側的最小值
        for (int i = 1; i < nums.length - 1; i++) {
            left[i] = Math.min(left[i - 1], nums[i - 1]);
        }

        //  Right 表示每個節點的右側的最小值
        for (int i = nums.length - 2; i > 0; i--) {
            right[i] = Math.min(right[i + 1], nums[i + 1]);
        }

        int result = Integer.MAX_VALUE;
        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i] > left[i] && nums[i] > right[i]) {
                result = Math.min(result, left[i] + nums[i] + right[i]);
            }
        }

        return result == Integer.MAX_VALUE ? -1 : result;
    }


    // https://leetcode.cn/problems/minimum-number-of-groups-to-create-a-valid-assignment/solutions/2493313/ben-ti-zui-jian-dan-xie-fa-pythonjavacgo-t174/
    // 統計每個數字的出現次數，記在哈希表 cnt 中。
    // 假設可以分成大小為 k 和 k+1 的組，現在需要算出每個 cnt[x] 最少可以分成多少組。
    // 舉例說明，假設 cnt[x]=32，k=10，那麼 32=10+10+10+2，多出的 2 可以分成兩個 1，
    // 加到兩個 10 中，從而得到 11,11,10 這三組。
    // 但如果 cnt[x]=34，那麼 34=10+10+10+4，多出的 4 無法加到另外三個 10 中。
    // 設 q=cnt[x]/k，r = cnt[x] mod k。
    // 如果 q<r 則無法分成 k 和 k+1 組，否則可以。
    // 可以分多少組呢？把 r 加到其它數中後，現在只有 k 和 k+1，可以分出
    // cnt[x] / (k+1) 組。累加組數即為分組個數。
    // 從 min(cnt[x]) 開始倒著枚舉 k，只要可以分，就立刻返回答案。
    public int minGroupsForValidAssignment(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum);
        }
        int k = nums.length;
        for (int c : cnt.values()) {
            k = Math.min(k, c);
        }
        for (; ; k--) {
            int ans = 0;
            for (int c : cnt.values()) {
                if (c / k < c % k) {
                    ans = 0;
                    break;
                }
                ans += (c + k) / (k + 1);
            }
            if (ans > 0) {
                return ans;
            }
        }
    }


    // https://leetcode.cn/problems/minimum-changes-to-make-k-semi-palindromes/solutions/2493147/yu-chu-li-ji-yi-hua-sou-suo-by-endlessch-qp47/
    // 記憶化搜索
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] modify = new int[n - 1][n];
        for (int left = 0; left < n - 1; left++) {
            for (int right = left + 1; right < n; right++) {
                modify[left][right] = getModify(s.substring(left, right + 1));
            }
        }

        int[][] memo = new int[k][n];
        for (int i = 0; i < k; i++) {
            Arrays.fill(memo[i], -1); // -1 表示沒有計算過
        }
        return dfs(k - 1, n - 1, memo, modify);
    }

    private static final int MX = 201;
    private static final List<Integer>[] divisors = new ArrayList[MX];

    static {
        // 預處理每個數的真因子，時間復雜度 O(MX*logMX)
        Arrays.setAll(divisors, k -> new ArrayList<>());
        for (int i = 1; i < MX; i++) {
            for (int j = i * 2; j < MX; j += i) {
                divisors[j].add(i);
            }
        }
    }

    private int getModify(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int res = n;
        for (int d : divisors[n]) {
            int cnt = 0;
            for (int i0 = 0; i0 < d; i0++) {
                for (int i = i0, j = n - d + i0; i < j; i += d, j -= d) {
                    if (s[i] != s[j]) {
                        cnt++;
                    }
                }
            }
            res = Math.min(res, cnt);
        }
        return res;
    }

    private int dfs(int i, int j, int[][] memo, int[][] modify) {
        if (i == 0) { // 遞歸邊界
            return modify[0][j];
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        int res = Integer.MAX_VALUE;
        for (int L = i * 2; L < j; L++) {
            res = Math.min(res, dfs(i - 1, L - 1, memo, modify) + modify[L][j]);
        }
        return memo[i][j] = res; // 記憶化
    }


    // 遞推
    public int minimumChanges2(String s, int k) {
        int n = s.length();
        int[][] modify = new int[n - 1][n];
        for (int left = 0; left < n - 1; left++) {
            for (int right = left + 1; right < n; right++) {
                modify[left][right] = getModify(s.substring(left, right + 1));
            }
        }

        int[] f = modify[0];
        for (int i = 1; i < k; i++) {
            for (int j = n - 1 - (k - 1 - i) * 2; j > i * 2; j--) { // 左右都要預留空間
                f[j] = Integer.MAX_VALUE;
                for (int L = i * 2; L < j; L++) {
                    f[j] = Math.min(f[j], f[L - 1] + modify[L][j]);
                }
            }
        }
        return f[n - 1];
    }

}
