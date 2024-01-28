package WeeklyContest;

import java.util.HashMap;

public class Week_382 {
    // https://leetcode.cn/problems/number-of-changing-keys/solutions/2622667/wei-yun-suan-jian-ji-xie-fa-pythonjavacg-h7rz/
    public int countKeyChanges(String s) {
        int ans = 0;
        for (int i = 1; i < s.length(); i++) {
            if ((s.charAt(i - 1) & 31) != (s.charAt(i) & 31)) {
                ans++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-the-maximum-number-of-elements-in-subset/solutions/2622718/bao-li-mei-ju-xpythonjavacgo-by-endlessc-hdmc/
    public int maximumLength(int[] nums) {
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum);
        }
        Integer c1 = cnt.remove(1);
        int ans = c1 != null ? c1 - (c1 % 2 ^ 1) : 0;
        for (int num : cnt.keySet()) {
            int res = 0;
            long x = num;
            while (true) {
                if (x > Integer.MAX_VALUE || !cnt.containsKey((int) x)) {
                    res--; // 上一個 x 我們選了兩個，去掉其中一個
                    break;
                }
                if (cnt.get((int) x) == 1) {
                    res++;
                    break;
                }
                res += 2;
                x *= x;
            }
            ans = Math.max(ans, res);
        }
        return ans;
    }


    // https://leetcode.cn/problems/alice-and-bob-playing-flower-game/solutions/2622659/o1-gong-shi-yi-xing-dai-ma-pythonjavacgo-f7ch/
    public long flowerGame(int n, int m) {
        return (long) n * m / 2;
    }

    // https://leetcode.cn/problems/minimize-or-of-remaining-elements-using-operations/solutions/2622658/shi-tian-fa-pythonjavacgo-by-endlesschen-ysom/
    public int minOrAfterOperations(int[] nums, int k) {
        int ans = 0;
        int mask = 0;
        for (int b = 29; b >= 0; b--) {
            mask |= 1 << b;
            int cnt = 0;
            int and = -1;
            for (int x : nums) {
                and &= x & mask;
                if (and != 0) {
                    cnt++; // 合並 x
                } else {
                    and = -1; // 准備合並下一段
                }
            }
            if (cnt > k) {
                ans |= 1 << b; // 答案的這個比特位必須是 1
                mask ^= 1 << b; // 後面不考慮這個比特位
            }
        }
        return ans;
    }
}
