package WeeklyContest;

public class Week_492 {

    // https://leetcode.cn/problems/minimum-capacity-box/solutions/3917789/bian-li-pythonjavacgo-by-endlesscheng-i70n/
    public int minimumIndex(int[] capacity, int itemSize) {
        int minC = Integer.MAX_VALUE;
        int ans = -1;
        for (int i = 0; i < capacity.length; i++) {
            int c = capacity[i];
            if (c >= itemSize && c < minC) {
                minC = c;
                ans = i;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-the-smallest-balanced-index/solutions/3917772/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-yizq/
    public int smallestBalancedIndex(int[] nums) {
        int n = nums.length;
        long sum = 0;
        for (int i = 0; i < n - 1; i++) {
            sum += nums[i];
        }

        long mul = 1;
        for (int i = n - 1; i > 0; i--) {
            if (sum == mul) {
                return i;
            }
            sum -= nums[i - 1];
            if (mul > sum / nums[i]) {
                break;
            }
            mul *= nums[i];
        }
        return -1;
    }


    // https://leetcode.cn/problems/minimum-operations-to-sort-a-string/solutions/3917770/fen-lei-tao-lun-pythonjavacgo-by-endless-s0wo/
    public int minOperations(String S) {
        char[] s = S.toCharArray();
        int n = s.length;

        boolean isSorted = true;
        for (int i = 1; i < n; i++) {
            if (s[i - 1] > s[i]) {
                isSorted = false;
                break;
            }
        }
        // s 已經是升序
        if (isSorted) {
            return 0;
        }

        // 長為 2，無法排序
        if (n == 2) {
            return -1;
        }

        char mn = s[0];
        char mx = s[0];
        for (char ch : s) {
            mn = (char) Math.min(mn, ch);
            mx = (char) Math.max(mx, ch);
        }

        // 如果 s[0] 是最小值，排序 [1,n-1] 即可
        // 如果 s[n-1] 是最大值，排序 [0,n-2] 即可
        if (s[0] == mn || s[n - 1] == mx) {
            return 1;
        }

        // 如果 [1,n-2] 中有最小值，那麼先排序 [0,n-2]，把最小值排在最前面，然後排序 [1,n-1] 即可
        // 如果 [1,n-2] 中有最大值，那麼先排序 [1,n-1]，把最大值排在最後面，然後排序 [0,n-2] 即可
        for (int i = 1; i < n - 1; i++) {
            if (s[i] == mn || s[i] == mx) {
                return 2;
            }
        }

        // 現在只剩下一種情況：s[0] 是最大值，s[n-1] 是最小值，且 [1,n-2] 不含最小值和最大值
        // 先排序 [0,n-2]，把最大值排到 n-2
        // 然後排序 [1,n-1]，把最大值排在最後面，且最小值排在 1
        // 最後排序 [0,n-2]，把最小值排在最前面
        return 3;
    }


    // https://leetcode.cn/problems/minimum-cost-to-partition-a-binary-string/solutions/3917785/bao-li-fen-zhi-ji-ke-shi-jian-fu-za-du-o-dm36/
    public long minCost(String s, int encCost, int flatCost) {
        int n = s.length();
        int[] sum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + (s.charAt(i) - '0');
        }
        return dfs(0, n, sum, encCost, flatCost);
    }

    // 計算 [l, r) 的最小費用，注意區間是左閉右開，方便計算
    private long dfs(int l, int r, int[] sum, int encCost, int flatCost) {
        // 不拆分
        int x = sum[r] - sum[l];
        long res = x > 0 ? (long) (r - l) * x * encCost : flatCost;

        // 拆分
        if ((r - l) % 2 == 0) {
            int m = (l + r) / 2;
            res = Math.min(res, dfs(l, m, sum, encCost, flatCost) + dfs(m, r, sum, encCost, flatCost));
        }

        return res;
    }


}









