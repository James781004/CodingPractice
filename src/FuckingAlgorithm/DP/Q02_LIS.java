package FuckingAlgorithm.DP;

import java.util.Arrays;
import java.util.Comparator;

public class Q02_LIS {
//    https://leetcode.cn/problems/longest-increasing-subsequence/
//    300. 最長遞增子序列
//    給你一個整數數組 nums ，找到其中最長嚴格遞增子序列的長度。
//
//    子序列 是由數組派生而來的序列，刪除（或不刪除）數組中的元素而不改變其余元素的順序。
//    例如，[3,6,2,7] 是數組 [0,3,1,6,2,2,7] 的子序列。

    public int lengthOfLIS(int[] nums) {
        // helper[i] 表示以 nums[i] 這個數結尾的最長遞增子序列的長度
        int[] dp = new int[nums.length];

        // base case：helper 數組全都初始化為 1
        Arrays.fill(dp, 1);

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }

        return res;
    }

    // 動態規劃 + 二分查找
    // https://leetcode.cn/problems/longest-increasing-subsequence/solution/zui-chang-shang-sheng-zi-xu-lie-dong-tai-gui-hua-2/
    public int lengthOfLIS2(int[] nums) {
        // tails數組是以當前長度連續子序列的最小末尾元素
        // 如tail[0]是求長度為1的連續子序列時的最小末尾元素
        // 例：在 1 6 4中 tail[0]=1 tail[1]=4 沒有tail[2] 因為無法到達長度為3的連續子序列
        int tails[] = new int[nums.length];

        // 注意：tails一定是遞增的 因為看題解那個動畫 我們最開始的那個元素一定找的是該數組裡最小的
        // 不然如果不是最小 由於我們需要連續 後面的數一定會更大（這樣不好的原因是 數越小 我們找到一個比該數大的數的幾率肯定會更大）
        int res = 0;
        for (int num : nums) {
            // 每個元素開始遍歷 看能否插入到之前的tails數組的位置 如果能 是插到哪裡
            int i = 0, j = res;
            while (i < j) {
                int m = (i + j) / 2;
                if (tails[m] < num) i = m + 1;
                else j = m;
            }

            // 如果沒有到達j == res這個條件 就說明tail數組裡只有部分比這個num要小
            // 那麼就把num插入到tail數組合適的位置即可 但是由於這樣的子序列長度肯定是沒有res長的 因此res不需要更新
            tails[i] = num;

            // j == res 說明目前tail數組的元素都比當前的num要小 因此最長子序列的長度可以增加了
            if (j == res) res++;
        }
        return res;
    }


//    https://leetcode.cn/problems/russian-doll-envelopes/
//    354. 俄羅斯套娃信封問題
//    給你一個二維整數數組 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 個信封的寬度和高度。
//
//    當另一個信封的寬度和高度都比這個信封大的時候，這個信封就可以放進另一個信封裡，如同俄羅斯套娃一樣。
//
//    請計算 最多能有多少個 信封能組成一組“俄羅斯套娃”信封（即可以把一個信封放到另一個信封裡面）。
//
//    注意：不允許旋轉信封。

    public int maxEnvelopes(int[][] envelopes) {
        int n = envelopes.length;

        // 按寬度升序排列，如果寬度一樣，則按高度降序排列
        Arrays.sort(envelopes, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[0] == b[0] ?
                        b[1] - a[1] : a[0] - b[0];
            }
        });

        // 對高度數組尋找 LIS
        int[] height = new int[n];
        for (int i = 0; i < n; i++)
            height[i] = envelopes[i][1];

        return lengthOfLIS(height);
    }
}
