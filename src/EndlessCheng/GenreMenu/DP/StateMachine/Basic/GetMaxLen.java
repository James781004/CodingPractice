package EndlessCheng.GenreMenu.DP.StateMachine.Basic;

public class GetMaxLen {

    // https://leetcode.cn/problems/maximum-length-of-subarray-with-positive-product/description/
    public int getMaxLen(int[] nums) {
        int n = nums.length;
        // f[i][0]代表0-i位置的能獲得到負數的子元素長度
        // f[i][1]代表0-i位置的能獲得到正數的子元素長度
        var f = new int[n][2];
        if (nums[0] < 0) {
            f[0][0] = 1;
        }
        if (nums[0] > 0) {
            f[0][1] = 1;
        }
        int ans = f[0][1];
        for (int i = 1; i < n; i++) {
            // 如果當前位i是正數，那麼能得到正數的數量就是上一位(i-1)能獲得正數的數量+1，
            // 能得到負數的數量就是上一位（i-1）能獲得負數的數量+1。
            if (nums[i] > 0) {
                // 這邊要做一個判斷，因為如果當前位之前的全是正數，那麼將無法得到負數，
                // 只有有負數的時候，當前位是正數才能獲得負數的結果
                if (f[i - 1][0] > 0) f[i][0] = f[i - 1][0] + 1;
                f[i][1] = f[i - 1][1] + 1;
            }
            // 同上
            if (nums[i] < 0) {
                f[i][0] = f[i - 1][1] + 1;
                if (f[i - 1][0] > 0) f[i][1] = f[i - 1][0] + 1;
            }
            ans = Math.max(f[i][1], ans);
        }
        return ans;
    }

    public int getMaxLen2(int[] nums) {
        int n = nums.length;
        var f = new int[2];
        int ans = f[1];
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                if (f[0] > 0)
                    f[0] += 1;
                f[1] = f[1] + 1;
            }
            if (nums[i] < 0) {
                // 因為後續f[1]有可能變更但是我們計算f[0]還需要原先值，所以先儲存起來
                int tmp = f[1];
                if (f[0] > 0)
                    f[1] = f[0] + 1;
                    // 二維數組中，如果沒有滿足上面的if條件，f[i][1]會被自動重制為0，但是一維數組中需要手動處理
                    // nums[i]>0的情況不需要處理是因為如果不把f[1]變為0，在後續遍歷到正數的時候會累積f[1]的結果
                    // 例子：
                    // [1，2，3，-1，4]
                    // 這個的正確答案應該是3，但是如果不把f[1]重置為0，f[1]的結果在遍歷4的時候依舊會是3 導致最後ans更新為4。
                else {
                    f[1] = 0;
                }
                f[0] = tmp + 1;
            }
            //二維數組中，如果遇到0直接跳過了 f[i][0]和f[i][1]都會自動重置為0，但是在一維數組中，需要我們手動處理
            if (nums[i] == 0) {
                f[1] = 0;
                f[0] = 0;
            }
            ans = Math.max(f[1], ans);
        }
        return ans;
    }


    public int getMaxLen3(int[] nums) {
        int n = nums.length, f0 = 0, f1 = 0;
        int ans = f1;
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                if (f0 > 0)
                    f0 += 1;
                f1 = f1 + 1;
            }
            if (nums[i] < 0) {
                int tmp = f1;
                f1 = f0 > 0 ? f0 + 1 : 0;
                f0 = tmp + 1;
            }
            if (nums[i] == 0) {
                f1 = 0;
                f0 = 0;
            }
            ans = Math.max(f1, ans);
        }
        return ans;
    }
}
