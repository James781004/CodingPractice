package EndlessCheng.GenreMenu.DP.Knapsack.Multiple;

import java.util.HashMap;
import java.util.List;

public class CountSubMultisets {

    // https://leetcode.cn/problems/count-of-sub-multisets-with-bounded-sum/solutions/2482876/duo-zhong-bei-bao-fang-an-shu-cong-po-su-f5ay/
    public int countSubMultisets(List<Integer> nums, int l, int r) {
        final int MOD = 1_000_000_007;
        int total = 0;
        var cnt = new HashMap<Integer, Integer>();
        for (int x : nums) {
            total += x;
            cnt.merge(x, 1, Integer::sum);
        }
        if (l > total) {
            return 0;
        }

        r = Math.min(r, total);
        int[] f = new int[r + 1];
        f[0] = cnt.getOrDefault(0, 0) + 1;
        cnt.remove(0);

        int sum = 0;
        for (var e : cnt.entrySet()) {
            int x = e.getKey(), c = e.getValue();
            int[] newF = f.clone();
            sum = Math.min(sum + x * c, r); // 到目前為止，能選的元素和至多為 sum
            for (int j = x; j <= sum; j++) { // 把循環上界從 r 改成 sum 可以快不少
                newF[j] = (newF[j] + newF[j - x]) % MOD;
                if (j >= (c + 1) * x) {
                    newF[j] = (newF[j] - f[j - (c + 1) * x] + MOD) % MOD; // 避免減法產生負數
                }
            }
            f = newF;
        }

        int ans = 0;
        for (int i = l; i <= r; ++i) {
            ans = (ans + f[i]) % MOD;
        }
        return ans;
    }


    public int countSubMultisets2(List<Integer> nums, int l, int r) {
        final int MOD = 1_000_000_007;
        int total = 0;
        var cnt = new HashMap<Integer, Integer>();
        for (int x : nums) {
            total += x;
            cnt.merge(x, 1, Integer::sum);
        }
        if (l > total) {
            return 0;
        }

        r = Math.min(r, total);
        int[] f = new int[r + 1];
        f[0] = cnt.getOrDefault(0, 0) + 1;
        cnt.remove(0);

        int sum = 0;
        for (var e : cnt.entrySet()) {
            int x = e.getKey(), c = e.getValue();
            sum = Math.min(sum + x * c, r);
            for (int j = x; j <= sum; j++) {
                f[j] = (f[j] + f[j - x]) % MOD; // 原地計算同余前綴和
            }
            for (int j = sum; j >= x * (c + 1); j--) {
                f[j] = (f[j] - f[j - x * (c + 1)] + MOD) % MOD; // 兩個同余前綴和的差
            }
        }

        int ans = 0;
        for (int i = l; i <= r; ++i) {
            ans = (ans + f[i]) % MOD;
        }
        return ans;
    }


}
