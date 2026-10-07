package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

public class MinimumTime {

    // https://leetcode.cn/problems/minimum-time-to-remove-all-cars-containing-illegal-goods/solutions/1247626/qian-hou-zhui-fen-jie-dp-by-endlesscheng-6u1b/
    public int minimumTime(String s) {
        var n = s.length();
        var suf = new int[n + 1]; // 表示移除從 s[i] 到 s[n−1] 的所有違禁貨物車廂所花費的最少時間
        for (var i = n - 1; i >= 0; i--)
            suf[i] = s.charAt(i) == '0' ? suf[i + 1] : Math.min(suf[i + 1] + 2, n - i);
        var ans = suf[0];
        var pre = 0; // 表示移除從 s[0] 到 s[i] 的所有違禁貨物車廂所花費的最少時間
        for (var i = 0; i < n; i++)
            if (s.charAt(i) == '1') {
                pre = Math.min(pre + 2, i + 1);
                ans = Math.min(ans, pre + suf[i + 1]);
            }
        return ans;
    }


}
