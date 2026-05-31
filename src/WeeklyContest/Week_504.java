package WeeklyContest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_504 {

    // https://leetcode.cn/problems/digit-frequency-score/solutions/3976708/shu-wei-he-pythonjavacgo-by-endlesscheng-1spd/
    public int digitFrequencyScore(int n) {
        int ans = 0;
        for (; n > 0; n /= 10) {
            ans += n % 10;
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-number-of-items-from-sale-i/solutions/3976688/0-1-bei-bao-mei-ju-you-hua-pythonjavacgo-6km7/
    public int maximumSaleItems(int[][] items, int budget) {
        int maxFactor = 0;
        int minPrice = Integer.MAX_VALUE;
        for (int[] p : items) {
            maxFactor = Math.max(maxFactor, p[0]);
            minPrice = Math.min(minPrice, p[1]);
        }

        int[] cntFactor = new int[maxFactor + 1];
        for (int[] p : items) {
            cntFactor[p[0]]++;
        }
        int[] cntMulti = new int[maxFactor + 1];
        int[] f = new int[budget + 1];
        int sumPrice = 0;

        for (int[] p : items) {
            int factor = p[0], price = p[1];

            if (cntMulti[factor] == 0) { // 之前沒有計算過
                for (int j = factor; j <= maxFactor; j += factor) {
                    cntMulti[factor] += cntFactor[j];
                }
            }
            int cnt = cntMulti[factor];

            // 視作一個體積為 price，價值為 cnt 的物品
            // 優化：已遍歷的物品的體積和至多為 sumPrice，大於這個值的體積和無法湊出來
            sumPrice = Math.min(sumPrice + price, budget);
            for (int j = sumPrice; j >= price; j--) {
                f[j] = Math.max(f[j], f[j - price] + cnt);
            }
        }

        int ans = 0;
        for (int i = 0; i <= budget; i++) {
            ans = Math.max(ans, f[i] + (budget - i) / minPrice);
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-number-of-items-from-sale-ii/solutions/3976690/tan-xin-pythonjavacgo-by-endlesscheng-a2tp/
    public int maximumSaleItemsII(int[][] items, int budget) {
        int n = items.length;
        int[] cntFactor = new int[n + 1];
        int minPrice = Integer.MAX_VALUE;
        for (int[] p : items) {
            cntFactor[p[0]]++;
            minPrice = Math.min(minPrice, p[1]);
        }

        int[] cntMulti = new int[n + 1];
        List<int[]> a = new ArrayList<>();

        for (int[] p : items) {
            int factor = p[0], price = p[1];
            if (price >= minPrice * 2) {
                continue;
            }

            if (cntMulti[factor] == 0) { // 之前沒有計算過
                for (int j = factor; j <= n; j += factor) {
                    cntMulti[factor] += cntFactor[j];
                }
            }

            if (cntMulti[factor] > 1) {
                a.add(new int[]{price, cntMulti[factor] - 1}); // factor 的倍數不包括該物品
            }
        }

        a.sort((p, q) -> p[0] - q[0]);

        int ans = 0;
        for (int[] p : a) {
            int price = p[0], cnt = p[1];
            if (budget < price) { // 沒錢了
                break;
            }
            int c = Math.min(cnt, budget / price); // 該物品最多買 c 個
            budget -= price * c;
            ans += c * 2;
        }
        return ans + budget / minPrice; // 剩余的錢買最便宜的物品
    }


    // https://leetcode.cn/problems/lexicographically-maximum-mex-array/solutions/3976707/fen-zu-xun-huan-pythonjavacgo-by-endless-x35d/
    public int[] maximumMEX(int[] nums) {
        int n = nums.length;
        // mex 最大是 n，>= n 的數無需考慮
        ArrayDeque<Integer>[] pos = new ArrayDeque[n + 1]; // n 作為哨兵
        Arrays.setAll(pos, v -> new ArrayDeque<>());
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (x < n) {
                pos[x].addLast(i);
            }
        }

        int idx = 0;
        for (int i = 0; i < n; i++) {
            int start = i; // 這一段的起點
            // 枚舉這一段的 mex，越大越好（字典序越大）
            int mex = 0;
            for (; ; mex++) {
                // 清理在 start 之前的下標
                while (!pos[mex].isEmpty() && pos[mex].peekFirst() < start) {
                    pos[mex].pollFirst();
                }
                if (pos[mex].isEmpty()) {
                    break;
                }
                // 這一段包含剩余元素中的最左邊的 mex
                i = Math.max(i, pos[mex].peekFirst());
            }
            nums[idx++] = mex;
        }
        return Arrays.copyOf(nums, idx);
    }


}










