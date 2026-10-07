package EndlessCheng.GenreMenu.DP.Probability;

import java.util.Arrays;

public class StatisticsProbability {

    // https://leetcode.cn/problems/nge-tou-zi-de-dian-shu-lcof/solutions/637778/jian-zhi-offer-60-n-ge-tou-zi-de-dian-sh-z36d/
    public double[] statisticsProbability(int n) {
        //因為最後的結果只與前一個動態轉移數組有關，所以這裡只需要設置一個一維的動態轉移數組
        //原本dp[i][j]表示的是前i個骰子的點數之和為j的概率，現在只需要最後的狀態的數組，所以就只用一個一維數組dp[j]表示n個骰子下每個結果的概率。
        //初始是1個骰子情況下的點數之和情況，就只有6個結果，所以用dp的初始化的size是6個
        double[] dp = new double[6];
        //只有一個數組
        Arrays.fill(dp, 1.0 / 6.0);
        //從第2個骰子開始，這裡n表示n個骰子，先從第二個的情況算起，然後再逐步求3個、4個···n個的情況
        //i表示當總共i個骰子時的結果
        for (int i = 2; i <= n; i++) {
            //每次的點數之和范圍會有點變化，點數之和的值最大是i*6，最小是i*1，i之前的結果值是不會出現的；
            //比如i=3個骰子時，最小就是3了，不可能是2和1，所以點數之和的值的個數是6*i-(i-1)，化簡：5*i+1
            //當有i個骰子時的點數之和的值數組先假定是temp
            double[] temp = new double[5 * i + 1];
            //從i-1個骰子的點數之和的值數組入手，計算i個骰子的點數之和數組的值
            //先拿i-1個骰子的點數之和數組的第j個值，它所影響的是i個骰子時的temp[j+k]的值
            for (int j = 0; j < dp.length; j++) {
                //比如只有1個骰子時，dp[1]是代表當骰子點數之和為2時的概率，它會對當有2個骰子時的點數之和為3、4、5、6、7、8產生影響，因為當有一個骰子的值為2時，另一個骰子的值可以為1~6，產生的點數之和相應的就是3~8；比如dp[2]代表點數之和為3，它會對有2個骰子時的點數之和為4、5、6、7、8、9產生影響；所以k在這裡就是對應著第i個骰子出現時可能出現六種情況，這裡可能畫一個K神那樣的動態規劃逆推的圖就好理解很多
                for (int k = 0; k < 6; k++) {
                    //這裡記得是加上dp數組值與1/6的乘積，1/6是第i個骰子投出某個值的概率
                    temp[j + k] += dp[j] * (1.0 / 6.0);
                }
            }
            //i個骰子的點數之和全都算出來後，要將temp數組移交給dp數組，dp數組就會代表i個骰子時的可能出現的點數之和的概率；用於計算i+1個骰子時的點數之和的概率
            dp = temp;
        }
        return dp;
    }

}
