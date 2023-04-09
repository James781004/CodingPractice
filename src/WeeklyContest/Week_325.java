package WeeklyContest;

import java.util.Arrays;

public class Week_325 {
    // https://www.bilibili.com/video/BV1FV4y1F7v7/?vd_source=3ffe88901c9b0b355ae9becd01f3e4bf
    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2515.Shortest%20Distance%20to%20Target%20String%20in%20a%20Circular%20Array/README.md
    public int closetTarget(String[] words, String target, int startIndex) {
        int n = words.length;
        int ans = n;
        for (int i = 0; i < n; i++) {
            if (words[i].equals(target)) {
                int t = Math.abs(i - startIndex);  // 正走距離
                ans = Math.min(ans, Math.min(t, n - t));  // 正走反走比較距離
            }
        }
        return ans == n ? -1 : ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2516.Take%20K%20of%20Each%20Character%20From%20Left%20and%20Right/README.md
    // 題目要我們在字符串左側以及右側取走字符，最終取到的每種字符的個數都不少於 k 個。
    // 反著考慮問題，取走中間某個窗口大小的字符串，使得剩下的兩側字符串中，每種字符的個數都不少於 k 個。
    public int takeCharacters(String s, int k) {
        // 統計字符串中每個字符的個數，如果有字符的個數小於 k 個，則無法取到，提前返回 -1。
        int[] cnt = new int[3];
        int n = s.length();
        for (int i = 0; i < n; i++) {
            cnt[s.charAt(i) - 'a']++;
        }

        if (cnt[0] < k || cnt[1] < k || cnt[2] < k) {
            return -1;
        }


        int ans = 0, j = 0;
        for (int i = 0; i < n; i++) {  // 右邊界移動，消耗cnt裡面存量
            int c = s.charAt(i) - 'a';
            cnt[c]--;
            while (cnt[c] < k) {
                cnt[s.charAt(j++) - 'a']++;  // 左邊界移動，返還cnt裡面存量
            }
            ans = Math.max(ans, i - j + 1);  // 更新最大窗口
        }
        return n - ans;  // s長度減去最大窗口
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2517.Maximum%20Tastiness%20of%20Candy%20Basket/README.md
    private int[] price;
    private int k;

    public int maximumTastiness(int[] price, int k) {
        // 先對數組 price 進行排序
        Arrays.sort(price);
        this.price = price;
        this.k = k;

        // 然後二分枚舉甜蜜度，找到最大的且滿足至少有 k 類糖果的甜蜜度
        int left = 0, right = 1000000000;
        while (left < right) {
            int mid = (left + right + 1) >> 1;
            if (check(mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    private boolean check(int x) {
        int s = price[0];
        int cnt = 1;
        for (int i = 1; i < price.length; i++) {
            if (price[i] - s >= x) {
                s = price[i];
                cnt++;
            }
        }
        return cnt >= k;
    }


    // https://leetcode.cn/problems/number-of-great-partitions/solution/ni-xiang-si-wei-01-bei-bao-fang-an-shu-p-v47x/
    // 如果直接計算好分區的數目，我們可以用 01 背包來做，但是背包容量太大，會超時。
    // 正難則反，可以反過來計算壞分區的數目，即第一個組或第二個組的元素和小於 k 的方案數。
    // 根據對稱性，我們只需要計算第一個組的元素和小於 k 的方案數，然後乘 2 即可。
    // 那麼原問題就轉換為「從 nums 中選擇若干元素，使得元素和小於 k 的方案數」，這樣用 01 背包就不會超時了。

    private static final int MOD = (int) 1e9 + 7;

    public int countPartitions(int[] nums, int k) {
        long sum = 0L;
        for (int x : nums) sum += x;
        if (sum < k * 2) return 0;  // 不可能分區
        int ans = 1;
        int[] f = new int[k];  // 空間壓縮01背包
        f[0] = 1;
        for (int x : nums) {
            ans = ans * 2 % MOD;
            for (int j = k - 1; j >= x; j--) {
                f[j] = (f[j] + f[j - x]) % MOD;
            }
        }

        // 答案為所有分區的數目減去壞分區的數目
        // 所有分區的數目減去壞分區的數目，即 2^n − bad，這裡 n 為 nums 的長度。
        for (int x : f)
            ans = (ans - x * 2 % MOD + MOD) % MOD; // 保證答案非負
        return ans;
    }
}
