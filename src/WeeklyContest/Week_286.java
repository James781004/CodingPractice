package WeeklyContest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Week_286 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2215.Find%20the%20Difference%20of%20Two%20Arrays/README.md
    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        Set<Integer> s1 = convert(nums1);
        Set<Integer> s2 = convert(nums2);

        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        for (int v : s1) {
            if (!s2.contains(v)) {
                l1.add(v);
            }
        }
        for (int v : s2) {
            if (!s1.contains(v)) {
                l2.add(v);
            }
        }
        ans.add(l1);
        ans.add(l2);
        return ans;
    }

    private Set<Integer> convert(int[] nums) {
        Set<Integer> s = new HashSet<>();
        for (int v : nums) {
            s.add(v);
        }
        return s;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2216.Minimum%20Deletions%20to%20Make%20Array%20Beautiful/README.md
    public int minDeletion(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n - 1; i++) {
            // 不需要修改數組，只統計不符合規則的元素數量即可。
            if (nums[i] == nums[i + 1]) {
                ++ans;
            } else {
                ++i;
            }
        }

        // 完成統計後，計算刪除元素之後的數組長度是否為奇數，若為奇數，還需要進行一次刪除（返回值 + 1）。
        if ((n - ans) % 2 == 1) {
            ++ans;
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2217.Find%20Palindrome%20With%20Fixed%20Length/README.md
    // https://leetcode.cn/problems/find-palindrome-with-fixed-length/solution/by-shou-hu-zhe-t-1f46/
    public long[] kthPalindrome(int[] queries, int intLength) {
        long[] ans = new long[queries.length];
        if (intLength == 1) { // 當長度為1的情況
            for (int i = 0; i < queries.length; i++) {
                if (queries[i] >= 10) ans[i] = -1;
                else ans[i] = queries[i];
            }
        } else {

            // 只看回文數的左半部分，可以發現左半部分是從 1000⋯0 開始，逐漸增加的
            // 由題意可知：回文串的左半部為pow(10,(intLength-1)/2)+q-1;
            int mid = (intLength >> 1) - 1;
            long halfNum = (long) Math.pow(10, mid);  // 10 的 mid 次方
            long halfNumTen = halfNum * 10;

            // 將回文串的左半部反轉得到回文串的右半部，再根據回文串長度判斷是否要刪除右半部分的第一個字符。
            if (intLength % 2 == 0) { // 回文串長度為偶數
                for (int i = 0; i < queries.length; i++) {
                    long tmp = halfNum + queries[i] - 1; // 回文串的左半部
                    if (tmp >= halfNumTen) {  // 長過左半部就是不存在的回文串
                        ans[i] = -1;
                    } else {
                        String str = Long.toString(tmp);
                        str = str + new StringBuffer(str).reverse().toString();
                        ans[i] = Long.valueOf(str);
                    }
                }
            } else { // 回文串長度為奇數
                for (int i = 0; i < queries.length; i++) {
                    long add = queries[i] - 1;
                    long tmp = halfNum + add / 10; // 回文串的左半部，去掉最低位

                    if (tmp >= halfNumTen) { // 長過左半部就是不存在的回文串
                        ans[i] = -1;
                    } else {
                        long Bit = add % 10;
                        String str = Long.toString(tmp);
                        str = str + Long.toString(Bit) + new StringBuffer(str).reverse().toString();
                        ans[i] = Long.valueOf(str);
                    }
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-value-of-k-coins-from-piles/solution/zhuan-hua-cheng-fen-zu-bei-bao-pythongoc-3xnk/
    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        /*
        dp五部曲(轉化為完全背包問題):
        這裡的每一個棧都可以看成是一個分組,例如棧1:pile=[1,2,3],preSum=[1,3,6]
        也就是說組別1內有3件物品,其體積分別為:1,2,3;價值分別為1,3,6
        而每組中的物品只能取出一件,在背包容量有限的情況下最終目標要使得物品價值最大化!
        1.狀態定義:dp[i][j]為考慮[0...i]組,背包容量為j時,能獲取物品價值的最大值
        2.狀態轉移:
            參考分組背包的技巧,每次抉擇范圍為選第i組的哪個物品?其中包含一種特殊情況(m=0代表不選->dp[i-1][j])
            dp[i][j]=max(dp[i][j],dp[i-1][j-v[i][m]]+w[i][m]),m∈[0,pile.size()]
            這裡的v[i][m]指第i組內的第k個物品的體積;w[i][m]指第i組內的第m個物品的價值
            通過3層for循環滾動求出dp[i][j]的正確轉移值,就是第i組要選擇的物品
        3.初始化:
            3.1 dp[i][0]=0->容量為0的情況
            3.2 dp[0][j]=preSum(piles[0][j])->只考慮第0組物品
        4.遍歷順序:顯然是正序遍歷
        5.返回形式:返回dp[pilesNum-1][k]
        時間復雜度:O(N*K*M),空間復雜度:O(N*K)
        */

        // 組數
        int pilesNum = piles.size();
        int[][] dp = new int[pilesNum][k + 1];  // dp[i][j]: 考慮[0...i]組,背包容量為j (第j次取物品) 時, 能獲取物品價值的最大值
        // 初始化，把第0組pile累計結果先求出來
        for (int j = 1; j <= piles.get(0).size() && j <= k; j++) {
            dp[0][j] = dp[0][j - 1] + piles.get(0).get(j - 1);
        }
        // 遍歷每一組物品i∈[1,pilesNum-1]
        for (int i = 1; i <= pilesNum - 1; i++) {
            // 遍歷每種背包容量j∈[1,k]
            for (int j = 1; j <= k; j++) {
                int sum = 0;
                // 遍歷第i組的每件物品m∈[0,piles.get(i).size()]
                // 其中m=0代表不選第i組任何物品,此時sum=0,自動繼承dp[i-1][j]
                // m=piles.get(i).size()表示這個組別的數字全拿了
                for (int m = 0; m <= piles.get(i).size(); m++) {
                    // 計算出第k組物品的第m個物品的價值
                    // 從m=1開始計算,因為m=0相當於不選第i組任何物品,sum=0,dp[i][j]繼承自dp[i-1][j]
                    if (m >= 1) sum += piles.get(i).get(m - 1);
                    // 若當前背包容量還裝得下第k組物品的第m個物品(體積為m)->滾動獲取最大值
                    // 拿之前的or拿現在的->取大的值
                    if (j >= m) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - m] + sum);
                }
            }
        }

        // 最後返回的是:考慮[0,pilesNum-1]中,背包容量為k的價值最大值(和的最大值)
        return dp[pilesNum - 1][k];
    }
}

