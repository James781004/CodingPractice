package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class NumPermsDISequence {

    // https://leetcode.cn/problems/valid-permutations-for-di-sequence/solutions/2044530/-by-wa-pian-d-zt5l/
    char[] di;
    int n;

    int mod = (int) 1e9 + 7;

    public int numPermsDISequence(String s) {
        di = s.toCharArray();
        n = s.length();// [0,n]
        long ans = 0;
        Integer[][] dp = new Integer[n][n + 1];
        for (int i = 0; i <= n; i++) {
            // 第0個位置拿i，返回有多少種拿法滿足序列S的要求。此時剩余比i小的數有i個
            ans += f(0, i, dp);
            ans %= mod;
        }
        return (int) ans;
    }

    // 第index位置做了選擇後，剩下smaller個數比當前選擇的小
    private long f(int index, int smaller, Integer[][] dp) {
        if (index == n) {// 順利選完，這是一種有效的選擇方法
            return 1;
        }
        if (dp[index][smaller] != null) {
            return dp[index][smaller];
        }
        int rest = n + 1 - (index + 1);// 總共n + 1個數，用掉 index + 1 個剩下rest個
        int bigger = rest - smaller;// 比index小的數量是smaller個，比index大的數量是bigger個
        char curid = di[index];// 當前參照di值，由這個di值決定後續從大的中選還是從小的中選。
        long ans = 0;
        if (curid == 'D') {// 當前是D，要比前一個index位置小，從smaller裡面挑一個，挑第i個
            for (int i = 0; i < smaller; i++) {
                ans += f(index + 1, i, dp);
                ans %= mod;
            }
        } else {// 當前是I 從bigger裡面挑一個，挑第i個
            for (int i = 0; i < bigger; i++) {
                ans += f(index + 1, smaller + i, dp);// 剩下smaller + i個小於當前選擇的數
                ans %= mod;
            }
        }
        dp[index][smaller] = (int) ans;
        return ans;
    }


}
