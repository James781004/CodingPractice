package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MinimumTime {

    // https://leetcode.cn/problems/minimum-time-to-remove-all-cars-containing-illegal-goods/solutions/1247626/qian-hou-zhui-fen-jie-dp-by-endlesscheng-6u1b/
    public int minimumTime(String s) {
        var n = s.length();
        var suf = new int[n + 1];
        for (var i = n - 1; i >= 0; --i)
            suf[i] = s.charAt(i) == '0' ? suf[i + 1] : Math.min(suf[i + 1] + 2, n - i);
        var ans = suf[0];
        var pre = 0;
        for (var i = 0; i < n; ++i)
            if (s.charAt(i) == '1') {
                pre = Math.min(pre + 2, i + 1);
                ans = Math.min(ans, pre + suf[i + 1]);
            }
        return ans;
    }

    public int minimumTime2(String s) {
        var n = s.length();
        var ans = n;
        var pre = 0;
        for (var i = 0; i < n; ++i) {
            if (s.charAt(i) == '1') pre = Math.min(pre + 2, i + 1);
            ans = Math.min(ans, pre + n - 1 - i);
        }
        return ans;
    }

}
