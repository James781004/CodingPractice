package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class NumDupDigitsAtMostN {

    // https://leetcode.cn/problems/numbers-with-repeated-digits/solutions/1748539/by-endlesscheng-c5vg/
    char[] s; // 把給定的整數n轉化為字符串數組
    int[][] dp;

    public int numDupDigitsAtMostN(int n) {
        // 把給定的整數n轉化為字符串數組
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        dp = new int[m][1 << 10];
        // 初始化dp
        for (int i = 0; i < m; i++)
            Arrays.fill(dp[i], -1); // -1表示沒有計算過
        // 先算出1-n之間沒有重復數字的個數a 則return n-a;
        return n - f(0, 0, true, false); // 這裡isLimit為什麼要初始化為true:如果是false,那馬上要填的這個填0~9就都可以了,不受[1,n]的約束,顯然不對
    }

    // 模板 有4個參數
    // 從i開始填數字，i前面填過的數字集合是mask
    // isLimit表示前面填的數字是否都是n對應位上的:isLimit==true，那麼當前位至多為s[i](eg:n=123,前兩位填的數字都是n對應位上的填了12,那麼當前位只能填0~s[i]即0~3了);isLimit==false,當前位可以從0~9選填,至多為'9'
    // isNum表示前面是否填了數字(是否跳過,是否是一個合法數字 比如010就不合法):isNum==true,那麼當前位可以從0開始；isNum==false,那麼我們可以跳過這位也不填，或者這位從1~9選填

    // 前置知識:位運算與集合論
    // 集合和二進制一一對應 因此之前填過的數字(集合)可以用一個int mask表示
    // 二進制從低到高第i位為1表示i在集合中，為0表示i不在集合中。例如集合{0,2,3}對應的二進制數為1101

    /**
     * 本題用到的:
     * (1)判斷數字d是否在集合mask中: mask >> d & 1 可以取出 mask 的第 d 個比特位，如果是 1 就說明 d 在集合mask中
     * (2)把數字d添加到集合mask中: mask 更新為 mask | (1 << d)
     */

    public int f(int i, int mask, boolean isLimit, boolean isNum) {
        // 終點
        if (i == s.length)
            return isNum ? 1 : 0; // 之前填過數字,那麼就有一個合法數字了

        // 前面既沒有填n對應位上的數，又填了數，那麼後面填的都不受限
        if (!isLimit && isNum && dp[i][mask] != -1)
            return dp[i][mask];

        int ans = 0;

        if (!isNum) // 可以選擇跳過當前位,也不填數字
            // 為什麼這裡isLimit也是false呢:之前都沒填數字,選擇跳過當前位,下一位i+1位當然也可以從0~9選填
            ans = f(i + 1, mask, false, false);
        // 上界:當前位i能填的數字的上界
        int up = isLimit ? s[i] - '0' : 9; // 也就是上面解釋的isLimit的含義
        // 下面就可以開始枚舉要填入的數字d了
        for (int d = isNum ? 0 : 1; d <= up; d++) {
            if ((mask >> d & 1) == 0) // d不在mask裡
                ans += f(i + 1, mask | (1 << d), isLimit && d == up, true);
        }
        if (!isLimit && isNum)
            dp[i][mask] = ans;
        return ans;
    }


}
