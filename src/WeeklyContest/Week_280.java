package WeeklyContest;

import java.util.Arrays;

public class Week_280 {
    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2169.Count%20Operations%20to%20Obtain%20Zero/README.md
    public int countOperations(int num1, int num2) {
        int ans = 0;
        while (num1 != 0 && num2 != 0) {
            if (num1 >= num2) {
                num1 -= num2;
            } else {
                num2 -= num1;
            }
            ++ans;
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-operations-to-make-the-array-alternating/solution/tan-xin-fen-lei-tao-lun-by-endlesscheng-qj15/
    public int minimumOperations(int[] nums) {
        /*
        考慮兩種間隔的前兩個最大值+分類討論
        要想最少操作次數，就要充分利用原理有出現的數字
        記偶數索引的最多出現的數字為a1，次多出現的數字為a2
        記奇數索引的最多出現的數字為b1，次多出現的數字為b2
        1.當a1!=b1 這是最理想的結果，直接將其他數字改成a1與b1，總的操作次數為n-(m1[a1]+m2[b1])
        2.當a1==b1 此時就有點麻煩了，不能同時將其他數字變為a1與b1因為會產生沖突
            此時我們可以有兩種選擇:選a1與b2 或 a2與b1 (a2與b2數目不多於前兩者可以忽略)
            取數目總和大的，最後結果為:n-max(m1[a1]+m2[b2],m1[a2]+m2[b1])
        重點就是如何維護兩個最大值了
         */

        int n = nums.length;
        int[] m1 = new int[100010], m2 = new int[100010];
        int a1 = 0, a2 = 0, b1 = 0, b2 = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                // 偶數索引
                m1[nums[i]]++;
                int cur = m1[nums[i]];  // cur為當前數字nums[i]出現的次數
                if (a1 == 0 || cur > m1[a1]) {  // 最大出現次數的數字還沒出現||當前數字出現次數已經大於a1出現次數
                    // nums[i]取代a1位置，之前的a1退居a2位置
                    a2 = a1;
                    a1 = nums[i];
                } else if (nums[i] != a1 && (a2 == 0 || cur > m1[a2])) {
                    // nums[i]!=a1說明這個nums[i]不是最大值的，此時若次大值處還沒賦值或者nums[i]出現次數比原先的a2多
                    // 那麼nums[i]可以取代原來a2的位置
                    a2 = nums[i];
                }
            } else {
                // 奇數索引
                m2[nums[i]]++;
                int cur = m2[nums[i]];
                if (b1 == 0 || cur > m2[b1]) {
                    b2 = b1;
                    b1 = nums[i];
                } else if (nums[i] != b1 && (b2 == 0 || cur > m2[b2])) {
                    b2 = nums[i];
                }
            }
        }
        if (a1 != b1) return n - (m1[a1] + m2[b1]);
        return n - Math.max(m1[a1] + m2[b2], m1[a2] + m2[b1]);
    }


    // https://leetcode.cn/problems/removing-minimum-number-of-magic-beans/solution/pai-xu-hou-yi-ci-bian-li-by-endlesscheng-36g8/
    // 將 beans 從小到大排序後，枚舉最終非空袋子中魔法豆的數目 v，
    // 將小於 v 的魔法豆全部清空，大於 v 的魔法豆減少至 v，
    // 這樣所有非空袋子中的魔法豆就均相等了。
    // 由於拿出魔法豆 + 剩餘魔法豆 = 初始魔法豆之和，
    // 可以考慮最多能剩下多少個魔法豆，從而計算出最少能拿出多少個魔法豆。
    public long minimumRemoval(int[] beans) {
        Arrays.sort(beans);
        long s = 0;
        for (int v : beans) s += v;
        long ans = s;
        int n = beans.length;
        for (int i = 0; i < n; i++) {
            // 拿出魔法豆 + 剩餘魔法豆 = 初始魔法豆之和
            // 拿出魔法豆 = 初始魔法豆之和 - 剩餘魔法豆
            // 假設剩餘 beans[i]，剩餘魔法豆總數就是 beans[i] * (n - i)
            ans = Math.min(ans, s - (long) beans[i] * (n - i));
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-and-sum-of-array/solution/zhuang-tai-ya-suo-dp-by-endlesscheng-5eqn/
    public int maximumANDSum(int[] nums, int numSlots) {
        int ans = 0;
        int[] f = new int[1 << (numSlots * 2)];
        for (int i = 0; i < f.length; i++) {
            int c = Integer.bitCount(i);
            if (c >= nums.length) continue;
            for (int j = 0; j < numSlots * 2; j++) {
                if ((i & (1 << j)) == 0) { // 枚舉空籃子 j
                    int s = i | (1 << j);
                    f[s] = Math.max(f[s], f[i] + ((j / 2 + 1) & nums[c]));
                    ans = Math.max(ans, f[s]);
                }
            }
        }
        return ans;
    }
}
