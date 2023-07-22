package WeeklyContest;

class Week_252 {
    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1952.Three%20Divisors/README.md
    public boolean isThree(int n) {
        int ans = 2;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) ans++;
        }

        return ans == 3;
    }


    // https://leetcode.cn/problems/maximum-number-of-weeks-for-which-you-can-work/solution/-by-coco-e1-3q2b/
    // 思考什麼情況下不能完成所有工作？
    // 存在一個項目 i 的任務數量比其他任務加起來都多，使得我們無法通過穿插其他項目的任務來完成項目 i 的所有工作
    // 否則我們一定能通過不同項目任務之間穿插來完成所有的項目
    public long numberOfWeeks2(int[] milestones) {
        long sum = 0;
        int max = 0;
        // 舉個栗子：
        // [1,2,10]，第 2 個項目的任務量是 10 ，即使做 2 的時候穿插 0、1 項目對應的任務，也無法完成項目 2 ，
        // 總共可以完成多少任務？（工作多少周）顯然是 2 * ( 1 + 2 ) + 1 = 7。

        for (int milestone : milestones) {
            sum += milestone;
            max = Math.max(milestone, max);
        }
        return 2L * max > sum + 1 ? 2 * (sum - max) + 1 : sum;
    }


    // https://leetcode.cn/problems/minimum-garden-perimeter-to-collect-enough-apples/solution/c-jie-ti-si-lu-tui-li-guo-cheng-by-lchao-o4qr/
    public long minimumPerimeter(long neededApples) {
        /*
        數學+二分:
        我們將這些蘋果看做一圈一圈的
        假設當前增加的一圈坐標為d，那麼增加的蘋果數目相應是12*d^2
        那麼假設最後的坐標為d，總共的蘋果數目為12*(1^2+2^2+3^2+...+d^2)=2*d*(d+1)*(2*d+1)>=t
        其中t為所需的蘋果數目，這時候求得最小的整數解d滿足上述式子，答案就是8*d
        尋找這個d可以利用二分進行加速，規定d∈[1,target]，或者更精細地有d∈[1,ceil(3√t)]
        時間復雜度:O(0.333*logT) 空間復雜度:O(1)
         */
        long l = 1, r = (long) Math.ceil(Math.cbrt(neededApples));  // 這裡需要開立方根，不然long也會溢出
        while (l < r) {
            long mid = l + (r - l) / 2;
            if (2 * mid * (mid + 1) * (2 * mid + 1) < neededApples) {
                // 蘋果還不夠，向右邊查找(不含)
                l = mid + 1;
            } else {
                // 蘋果夠了，向左邊查找(含)
                r = mid;
            }
        }
        return 8 * l;
    }


    // https://leetcode.cn/problems/count-number-of-special-subsequences/solution/dong-tai-gui-hua-by-endlesscheng-4onu/
    static int mod = 1000000007;

    public int countSpecialSubsequences(int[] nums) {
        // f[i][0] 表示前 i 項得到的全 0 子序列個數
        // f[i][1] 表示前 i 項得到的先 0 後 1 的子序列個數
        // f[i][2] 表示前 i 項得到的特殊子序列個數
        // 這邊使用滾動數組，因為 i 位置可以直接繼承 i-1 的累積狀態
        int f0 = 0, f1 = 0, f2 = 0;

        // 遍歷數組 nums，對於 f[i][j]，若 j != nums[i]，則直接從前一項轉移過來，即 f[i][j]=f[i−1][j]。
        for (int num : nums) {
            // 若 j=nums[i] 則需要分類計算：
            if (num == 0) {
                // 對於 f[i][0]，當遇到 0 時，有選或不選兩種方案，
                // 不選 0 時有 f[i][0]=f[i−1][0]，
                // 選 0 時，可以單獨組成一個子序列，也可以與前面的 0 組合，
                // 因此有 f[i][0]=f[i−1][0]+1，兩者相加得 f[i][0]=2⋅f[i−1][0]+1。
                f0 = (f0 * 2 + 1) % mod;
            } else if (num == 1) {
                // 對於 f[i][1]，當遇到 1 時，有選或不選兩種方案，
                // 不選 1 時有 f[i][1]=f[i−1][1]，
                // 選 1 時，可以單獨與前面的 0 組成一個子序列，也可以與前面的 1 組合，
                // 因此有 f[i][1]=f[i−1][1]+f[i−1][0]，兩者相加得 f[i][1]=2⋅f[i−1][1]+f[i−1][0]。
                f1 = (f1 * 2 % mod + f0) % mod;
            } else {
                // f[i][2] 和 f[i][1] 類似，有 f[i][2]=2⋅f[i−1][2]+f[i−1][1]。
                f2 = (f2 * 2 % mod + f1) % mod;
            }
        }

        // 最後答案為 f[n−1][2]。
        return f2;
    }
}


