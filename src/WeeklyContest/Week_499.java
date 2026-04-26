package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_499 {

    // https://leetcode.cn/problems/valid-elements-in-an-array/solutions/3959262/on-zuo-fa-pythonjavacgo-by-endlesscheng-ddxo/
    public List<Integer> findValidElements(int[] nums) {
        // 標記嚴格大於其右側所有元素的元素
        int n = nums.length;
        boolean[] rightValid = new boolean[n];
        int mx = 0;
        for (int i = n - 1; i >= 0; i--) {
            int x = nums[i];
            rightValid[i] = x > mx;
            mx = Math.max(mx, x);
        }

        List<Integer> ans = new ArrayList<>();
        mx = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (x > mx || rightValid[i]) {
                ans.add(x);
            }
            mx = Math.max(mx, x);
        }
        return ans;
    }


    // https://leetcode.cn/problems/sort-vowels-by-frequency/solutions/3959303/jie-sheng-kong-jian-de-zuo-fa-pythonjava-gmke/
    private static final int[] mp = new int[128];

    static {
        Arrays.fill(mp, -1);
        mp['a'] = 0;
        mp['e'] = 1;
        mp['i'] = 2;
        mp['o'] = 3;
        mp['u'] = 4;
    }

    public String sortVowels(String s) {
        char[] t = s.toCharArray();

        int[] cnt = new int[5];
        List<Character> vowels = new ArrayList<>();
        for (char ch : t) {
            int x = mp[ch];
            if (x < 0) {
                continue;
            }
            if (cnt[x] == 0) {
                vowels.add(ch);
            }
            cnt[x]++;
        }

        // 把 aeiou 按照出現次數從大到小排序
        vowels.sort((a, b) -> cnt[mp[b]] - cnt[mp[a]]);

        int j = 0;
        for (int i = 0; i < t.length; i++) {
            if (mp[t[i]] < 0) {
                continue;
            }
            t[i] = vowels.get(j);
            if (--cnt[mp[t[i]]] == 0) {
                j++;
            }
        }
        return new String(t);
    }


    // https://leetcode.cn/problems/minimum-operations-to-make-array-non-decreasing/solutions/3959260/chai-fen-si-xiang-jian-ji-xie-fa-fu-xian-35hq/
    public long minOperations(int[] nums) {
        long ans = 0;
        for (int i = 1; i < nums.length; i++) {
            ans += Math.max(nums[i - 1] - nums[i], 0);
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-sum-of-alternating-subsequence-with-distance-at-least-k/solutions/3959269/zhi-yu-shu-zhuang-shu-zu-you-hua-zhuang-j1b9q/
    public long maxAlternatingSum(int[] nums, int k) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int n = nums.length;
        long[] fInc = new long[n]; // fInc[i] 表示以 nums[i] 結尾且最後兩項遞增的交替子序列的最大和
        long[] fDec = new long[n]; // fDec[i] 表示以 nums[i] 結尾且最後兩項遞減的交替子序列的最大和

        // 值域樹狀數組
        Fenwick inc = new Fenwick(n + 1); // 維護 fInc[i] 的最大值
        Fenwick dec = new Fenwick(n + 1); // 維護 fDec[i] 的最大值

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int x = nums[i];

            if (i >= k) {
                // 在這個時候才把 fInc[i-k] 和 fDec[i-k] 添加到值域樹狀數組中，從而保證轉移來源的下標 <= i-k
                int j = nums[i - k];
                inc.update(n - j, fInc[i - k]); // len(sorted)-j 可以把後綴變成前綴
                dec.update(j + 1, fDec[i - k]);
            }

            int j = Arrays.binarySearch(sorted, x);
            nums[i] = j; // 注意這裡修改了 nums[i]，這樣上面的 nums[i-k] 無需二分

            fInc[i] = dec.preMax(j) + x;           // 計算滿足 nums[i'] < x 的 fDec[i'] 的最大值
            fDec[i] = inc.preMax(n - 1 - j) + x; // 計算滿足 nums[i'] > x 的 fInc[i'] 的最大值
            ans = Math.max(ans, Math.max(fInc[i], fDec[i])); // 枚舉子序列以 nums[i] 結尾
        }

        return ans;
    }


    static class Fenwick {
        private final long[] f;

        Fenwick(int n) {
            f = new long[n];
        }

        public void update(int i, long val) {
            for (; i < f.length; i += i & -i) {
                f[i] = Math.max(f[i], val);
            }
        }

        public long preMax(int i) {
            long res = 0;
            for (; i > 0; i &= i - 1) {
                res = Math.max(res, f[i]);
            }
            return res;
        }
    }


}










