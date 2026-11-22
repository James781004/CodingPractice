package EndlessCheng.GenreMenu.DataStructure.PrefixSum.HashMap;

public class LongestWPI {

    // https://leetcode.cn/problems/longest-well-performing-interval/solutions/2110211/liang-chong-zuo-fa-liang-zhang-tu-miao-d-hysl/
    public int longestWPI(int[] hours) {
        int n = hours.length, ans = 0, s = 0;
        var pos = new int[n + 2]; // 記錄前綴和首次出現的位置
        for (int i = 1; i <= n; ++i) {
            s -= hours[i - 1] > 8 ? 1 : -1; // 取反，改為減法
            if (s < 0) ans = i;
            else {
                if (pos[s + 1] > 0) ans = Math.max(ans, i - pos[s + 1]);
                if (pos[s] == 0) pos[s] = i;
            }
        }
        return ans;
    }

}
