package EndlessCheng.GenreMenu.DP.Divide.Valid;

public class ValidPartition {

    // https://leetcode.cn/problems/check-if-there-is-a-valid-partition-for-the-array/solutions/1728735/by-endlesscheng-8y73/
    public boolean validPartition(int[] nums) {
        /*
        DP五部曲:
        1.狀態定義:f[i]代表考慮將[0,i]是否能被有效劃分，有則為true，沒有則為false
        2.狀態轉移:f[i]的轉移有3種可能：
            2.1 由f[i-2]轉移過來，且nums[i-1]==nums[i]
            2.2 由f[i-3]轉移過來，且nums[i-2]==nums[i-1]==nums[i]
            2.3 由f[i-3]轉移過來，且nums[i-1]==nums[i-2]+1;nums[i]==nums[i-1]+1
         其中一種能轉移過來即可
        3.初始化:f[0]=false，f[1]=nums[0]==nums[1]，f[2]=nums[0]==nums[1]==nums[2]||遞增
        4.遍歷順序:正序遍歷[3,n-1]
        5.返回形式:返回f[n-1]
         */
        int n = nums.length;
        boolean[] f = new boolean[n];
        f[0] = false;
        f[1] = nums[0] == nums[1];
        if (n == 2) return f[1];
        f[2] = (nums[0] == nums[1] && nums[1] == nums[2]) || (nums[1] == nums[0] + 1 && nums[2] == nums[1] + 1);
        for (int i = 3; i < n; i++) {
            boolean b1 = f[i - 2] && nums[i - 1] == nums[i];
            boolean b2 = f[i - 3] && nums[i - 2] == nums[i - 1] && nums[i - 1] == nums[i];
            boolean b3 = f[i - 3] && nums[i - 1] == nums[i - 2] + 1 && nums[i] == nums[i - 1] + 1;
            f[i] = b1 || b2 || b3;
        }
        return f[n - 1];
    }


}
