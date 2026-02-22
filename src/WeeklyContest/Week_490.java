package WeeklyContest;

import java.util.HashMap;
import java.util.Map;

public class Week_490 {

    // https://leetcode.cn/problems/find-the-score-difference-in-a-game/solutions/3906212/mo-ni-jian-ji-xie-fa-pythonjavacgo-by-en-atuo/
    public int scoreDifference(int[] nums) {
        int[] score = new int[2];
        int active = 0; // 主動玩家一開始是第一位玩家
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            active ^= x % 2; // 如果 x 是奇數，主動玩家換人
            if (i % 6 == 5) {
                active ^= 1; // 主動玩家換人
            }
            score[active] += x;
        }
        return score[0] - score[1];
    }


    // https://leetcode.cn/problems/check-digitorial-permutation/solutions/3906208/bu-yong-zi-fu-chuan-de-xie-fa-pythonjava-aot1/
    private static final int[] fac = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};

    public boolean isDigitorialPermutation(int n) {
        int sumFac = 0;
        int[] cnt = new int[10];
        for (; n > 0; n /= 10) {
            int d = n % 10;
            sumFac += fac[d];
            cnt[d]++;
        }

        for (; sumFac > 0; sumFac /= 10) {
            cnt[sumFac % 10]--;
        }

        for (int i = 0; i < 10; i++) {
            if (cnt[i] != 0) {
                return false;
            }
        }
        return true;
    }


    // https://leetcode.cn/problems/maximum-bitwise-xor-after-rearrangement/solutions/3906204/cong-zuo-dao-you-tan-xin-jian-ji-xie-fa-ced9g/
    public String maximumXor(String s, String t) {
        int cnt1 = 0;
        for (char ch : t.toCharArray()) {
            cnt1 += ch - '0';
        }
        int[] left = new int[]{t.length() - cnt1, cnt1}; // t 中剩余的 0 和 1 的個數

        char[] ans = s.toCharArray();
        for (int i = 0; i < ans.length; i++) {
            int x = ans[i] - '0';
            // 如果 x 是 0，那就看還有沒有剩下的 1
            // 如果 x 是 1，那就看還有沒有剩下的 0
            if (left[x ^ 1] > 0) {
                left[x ^ 1]--;
                ans[i] = '1'; // x ^ (x ^ 1) = 1
            } else { // 只能讓兩個相同的數異或
                left[x]--;
                ans[i] = '0'; // x ^ x = 0
            }
        }
        return new String(ans);
    }


    // https://leetcode.cn/problems/count-sequences-to-k/solutions/3906202/zhi-yin-zi-fen-jie-ji-yi-hua-sou-suo-pyt-mb7w/
    private record Exp(int e2, int e3, int e5) {
    }

    // 返回 k 中的質因子 2,3,5 的個數，以及 k 是否只包含 <= 5 的質因子
    private Pair<Exp, Boolean> primeFactorization(long k) {
        int e2 = Long.numberOfTrailingZeros(k);
        k >>= e2;

        int e3 = 0;
        while (k % 3 == 0) {
            e3++;
            k /= 3;
        }

        int e5 = 0;
        while (k % 5 == 0) {
            e5++;
            k /= 5;
        }

        return new Pair<>(new Exp(e2, e3, e5), k == 1);
    }

    public int countSequences(int[] nums, long k) {
        Pair<Exp, Boolean> res = primeFactorization(k);
        Exp e = res.key;
        boolean ok = res.value;
        if (!ok) { // k 有大於 5 的質因子
            return 0;
        }

        int n = nums.length;
        Exp[] es = new Exp[n];
        for (int i = 0; i < n; i++) {
            es[i] = primeFactorization(nums[i]).key;
        }

        Map<Integer, Integer> memo = new HashMap<>();
        return dfs(n - 1, e.e2, e.e3, e.e5, es, memo);
    }

    private int dfs(int i, int e2, int e3, int e5, Exp[] es, Map<Integer, Integer> memo) {
        if (i < 0) {
            return e2 == 0 && e3 == 0 && e5 == 0 ? 1 : 0; // k 變成 1
        }

        // 把 i,e2,e3,e5 拼成一個 int（每個數至多 6 位）
        int n = es.length;
        int key = i << 18 | (e2 + n * 2) << 12 | (e3 + n) << 6 | (e5 + n);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        Exp e = es[i];
        int x = e.e2, y = e.e3, z = e.e5;
        int res1 = dfs(i - 1, e2 - x, e3 - y, e5 - z, es, memo); // k 除以 nums[i]
        int res2 = dfs(i - 1, e2 + x, e3 + y, e5 + z, es, memo); // k 乘以 nums[i]
        int res3 = dfs(i - 1, e2, e3, e5, es, memo); // k 不變
        int res = res1 + res2 + res3;

        memo.put(key, res);
        return res;
    }

    static class Pair<E, B> {
        Exp key;
        Boolean value;

        public Pair(Exp i, Boolean b) {
            key = i;
            value = b;
        }
    }

}









