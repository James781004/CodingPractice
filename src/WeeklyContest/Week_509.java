package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Week_509 {

    // https://leetcode.cn/problems/sum-of-integers-with-maximum-digit-range/solutions/3991838/yi-ci-bian-li-pythonjavacgo-by-endlessch-pbsi/
    public int maxDigitRange(int[] nums) {
        int maxRange = 0;
        int ans = 0;

        for (int x : nums) {
            int mn = 9;
            int mx = 0;
            for (int v = x; v > 0; v /= 10) {
                int d = v % 10;
                mn = Math.min(mn, d);
                mx = Math.max(mx, d);
            }

            int r = mx - mn;
            if (r > maxRange) {
                maxRange = r;
                ans = x; // 重新累加
            } else if (r == maxRange) {
                ans += x;
            }
        }

        return ans;
    }


    // https://leetcode.cn/problems/subsequence-after-one-replacement/solutions/3991828/san-zhi-zhen-pythonjavacgo-by-endlessche-2qdj/
    public boolean canMakeSubsequence(String S, String t) {
        char[] s = S.toCharArray();
        int n = s.length;
        int j0 = 0; // 在不修改的情況下，s 的前綴 [0, j0-1] 是 t 的當前前綴的子序列
        int j1 = 0; // 在改過一次的情況下，s 的前綴 [0, j1-1] 是 t 的當前前綴的子序列
        for (char ch : t.toCharArray()) {
            // j1 普通匹配
            if (s[j1] == ch) {
                j1++;
            }

            // 也可以修改 s[j0] 為 ch，強行匹配
            j1 = Math.max(j1, j0 + 1);

            // j0 普通匹配
            if (s[j0] == ch) {
                j0++;
            }

            if (j0 == n || j1 == n) {
                // s 是 t 的子序列
                return true;
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/divisible-game/solutions/3991837/mei-ju-yin-zi-ji-suan-zui-da-zi-shu-zu-h-ph5x/
    public int divisibleGame(int[] nums) {
        final int MOD = 1_000_000_007;

        // 收集所有質因子
        // 預處理有些慢，改成不預處理的寫法
        List<Integer> allPrimeDivisors = new ArrayList<>();
        for (int x : nums) {
            for (int p = 2; p * p <= x; p++) {
                if (x % p == 0) {
                    allPrimeDivisors.add(p);
                    do {
                        x /= p;
                    } while (x % p == 0);
                }
            }
            if (x > 1) {
                allPrimeDivisors.add(x);
            }
        }

        if (allPrimeDivisors.isEmpty()) {
            // 每個數都是 1
            // 最優是只選一個 1（分數差為 -1），最小 k 為 2
            return MOD - 2;
        }

        Collections.sort(allPrimeDivisors);

        int maxDiff = Integer.MIN_VALUE;
        int bestK = 0;
        int preK = 0;
        // 枚舉質因子作為 k，計算最大子數組和
        for (int k : allPrimeDivisors) {
            if (k == preK) {
                continue;
            }
            int diff = maxSubArray(nums, k);
            if (diff > maxDiff) {
                maxDiff = diff;
                bestK = k;
            }
            preK = k;
        }

        // 保證結果非負
        return (int) (((long) maxDiff * bestK % MOD + MOD) % MOD);
    }

    // 53. 最大子數組和（如果 nums[i] 不是 k 的倍數，則視作 -nums[i]）
    private int maxSubArray(int[] nums, int k) {
        int ans = Integer.MIN_VALUE;
        int f = 0;
        for (int x : nums) {
            f = Math.max(f, 0) + (x % k == 0 ? x : -x);
            ans = Math.max(ans, f);
        }
        return ans;
    }


    // https://leetcode.cn/problems/palindromic-subarray-sum/solutions/3991856/mo-ban-manacher-suan-fa-qian-zhui-he-pyt-d917/
    public long getSum(int[] s) {
        // Manacher 模板
        // 將 s 改造為 t，這樣就不需要討論 s.length 的奇偶性，因為新串 t 的每個回文子串都是奇回文串（都有回文中心）
        // s 和 t 的下標轉換關系：
        // (si+1)*2 = ti
        // ti/2-1 = si
        // ti 為偶數，對應奇回文串（從 2 開始）
        // ti 為奇數，對應偶回文串（從 3 開始）
        int n = s.length;
        int[] t = new int[n * 2 + 3];
        Arrays.fill(t, -1);
        t[0] = -2;
        for (int i = 0; i < n; i++) {
            t[i * 2 + 2] = s[i];
        }
        t[n * 2 + 2] = -3;

        // 定義一個奇回文串的回文半徑=(長度+1)/2，即保留回文中心，去掉一側後的剩余字符串的長度
        // halfLen[i] 表示在 t 上的以 t[i] 為回文中心的最長回文子串的回文半徑
        // 即 [i-halfLen[i]+1,i+halfLen[i]-1] 是 t 上的一個回文子串
        int[] halfLen = new int[t.length - 2];
        halfLen[1] = 1;

        // maxI 記錄最長回文子串在 halfLen 中的下標
        int maxI = 0;
        // boxR 表示當前右邊界下標最大的回文子串的右邊界下標+1
        // boxM 為該回文子串的中心位置，二者的關系為 r=mid+halfLen[mid]
        int boxM = 0;
        int boxR = 0;
        for (int i = 2; i < halfLen.length; i++) {
            int hl = 1;
            if (i < boxR) {
                // 記 i 關於 boxM 的對稱位置 i'=boxM*2-i
                // 若以 i' 為中心的最長回文子串范圍超出了以 boxM 為中心的回文串的范圍（即 i+halfLen[i'] >= boxR）
                // 則 halfLen[i] 應先初始化為已知的回文半徑 boxR-i，然後再繼續暴力匹配
                // 否則 halfLen[i] 與 halfLen[i'] 相等
                hl = Math.min(halfLen[boxM * 2 - i], boxR - i);
            }

            // 暴力擴展
            while (t[i - hl] == t[i + hl]) {
                hl++;
                boxM = i;
                boxR = i + hl;
            }

            halfLen[i] = hl;
            if (hl > halfLen[maxI]) {
                maxI = i;
            }
        }

        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + s[i];
        }

        long ans = 0;
        for (int i = 2; i < halfLen.length; i++) {
            int hl = halfLen[i];
            // 注意 t 上的最長回文子串的最左邊和最右邊都是 -1
            // 所以要對應到 s，最長回文子串的下標是從 i-hl+2 到 i+hl-2
            // 結合上文的下標轉換關系，得到其在 s 上的下標范圍是從 (i-hl)/2 到 (i+hl)/2-2
            ans = Math.max(ans, sum[(i + hl) / 2 - 1] - sum[(i - hl) / 2]);
        }
        return ans;
    }


}










