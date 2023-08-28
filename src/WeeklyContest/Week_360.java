package WeeklyContest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Week_360 {
    // https://leetcode.cn/problems/furthest-point-from-origin/solutions/2413317/nao-jin-ji-zhuan-wan-yi-xing-dai-ma-by-e-yfn0/
    public int furthestDistanceFromOrigin(String moves) {
        int left = 0, right = 0, other = 0;
        for (int i = 0; i < moves.length(); i++) {
            if (moves.charAt(i) == 'L') {
                left++;
            } else if (moves.charAt(i) == 'R') {
                right++;
            } else {
                other++;
            }
        }

        return left >= right ? left + other - right : right + other - left;
    }


    // https://leetcode.cn/problems/find-the-minimum-possible-sum-of-a-beautiful-array/solutions/2413304/o1-shu-xue-gong-shi-pythonjavacgo-by-end-xsxg/
    // 貪心 + set
    public long minimumPossibleSum(int n, int target) {
        Set<Integer> set = new HashSet<>();
        int i = 1;
        long sum = 0;
        while (set.size() < n) {
            if (!set.contains(target - i) && set.add(i)) sum += i;
            i++;
        }
        return sum;
    }


    // 數學公式
    // 把 target 記作 k。
    // 對於 [1,k−1] 內的數字：
    // 1 和 k−1 只能選其中一個；
    // 2 和 k−2 只能選其中一個；
    // 3 和 k−3 只能選其中一個；
    //……
    // 一直到 [k/2]，無論 k 是奇數還是偶數，它都可以選。
    // 設 m 為 min(k/2, n) ，答案的第一段是從 1 到 m，元素和為 m(m+1)/2
    // 此時還剩下 n−m 個數，只能從 k 開始往後選，那麼答案的第二段是從 k 到 k+n−m−1
    // 元素和為 (2k+n-m-1)(n-m)/2
    // 所以答案為 (m(m+1) + (2k+n-m-1)(n-m)) / 2
    public long minimumPossibleSum2(int n, int k) {
        long m = Math.min(k / 2, n);
        return (m * (m + 1) + (k * 2 + n - m - 1) * (n - m)) / 2;
    }


    // https://leetcode.cn/problems/minimum-operations-to-form-subsequence-with-target-sum/solutions/2413344/tan-xin-by-endlesscheng-immn/
    public int minOperations(int[] nums, int target) {
        long s = 0;
        long[] cnt = new long[31];
        for (int x : nums) {
            s += x;
            cnt[Integer.numberOfTrailingZeros(x)]++;
        }
        if (s < target) return -1;

        int ans = 0, i = 0;
        s = 0;
        while ((1L << i) <= target) { // 從低位到高位貪心
            s += cnt[i] << i;
            int mask = (int) ((1L << ++i) - 1);
            if (s >= (target & mask)) continue; // 湊到了，繼續找下一位
            ans++; // 一定要找更大的數操作
            for (; cnt[i] == 0; i++) {
                ans++; // 還沒找到，繼續找更大的數
            }
        }
        return ans;
    }

    // https://leetcode.cn/problems/maximize-value-of-function-in-a-ball-passing-game/solutions/2413298/shu-shang-bei-zeng-by-endlesscheng-xvsv/
    public long getMaxFunctionValue(List<Integer> receiver, long K) {
        int n = receiver.size();
        int m = 64 - Long.numberOfLeadingZeros(K); // K 的二進制長度
        int[][] pa = new int[n][m];
        long[][] sum = new long[n][m];
        for (int i = 0; i < n; i++) {
            pa[i][0] = receiver.get(i);
            sum[i][0] = receiver.get(i);
        }
        for (int i = 0; i < m - 1; i++) {
            for (int x = 0; x < n; x++) {
                int p = pa[x][i];
                pa[x][i + 1] = pa[p][i];
                sum[x][i + 1] = sum[x][i] + sum[p][i]; // 合併節點值之和
            }
        }

        long ans = 0;
        for (int i = 0; i < n; i++) {
            long s = i;
            int x = i;
            for (long k = K; k > 0; k &= k - 1) {
                int ctz = Long.numberOfTrailingZeros(k);
                s += sum[x][ctz];
                x = pa[x][ctz];
            }
            ans = Math.max(ans, s);
        }
        return ans;
    }
}
