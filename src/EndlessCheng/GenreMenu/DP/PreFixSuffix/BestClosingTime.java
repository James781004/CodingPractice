package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class BestClosingTime {

    // https://leetcode.cn/problems/minimum-penalty-for-a-shop/solutions/1993077/qian-hou-zhui-fen-jie-o1-kong-jian-by-en-c2m5/
    public int bestClosingTime(String customers) {
        int penalty = 0;
        int minPenalty = 0;
        int ans = 0;
        for (int i = 0; i < customers.length(); i++) {
            penalty += customers.charAt(i) == 'N' ? 1 : -1;
            if (penalty < minPenalty) {
                minPenalty = penalty;
                ans = i + 1;
            }
        }
        return ans;
    }

}
