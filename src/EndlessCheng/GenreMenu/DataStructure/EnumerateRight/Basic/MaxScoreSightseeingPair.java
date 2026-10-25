package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

public class MaxScoreSightseeingPair {

    // https://leetcode.cn/problems/best-sightseeing-pair/solutions/2836414/mei-ju-you-wei-hu-zuo-jian-ji-xie-fa-pyt-x385/
    public int maxScoreSightseeingPair(int[] values) {
        int ans = 0;
        int mx = values[0]; // j 左邊的 values[i] + i 的最大值
        for (int j = 1; j < values.length; j++) {
            ans = Math.max(ans, mx + values[j] - j);
            mx = Math.max(mx, values[j] + j);
        }
        return ans;
    }

}
