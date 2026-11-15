package EndlessCheng.GenreMenu.DataStructure.PrefixSum.Basic;

public class ShiftDistance {

    // https://leetcode.cn/problems/shift-distance-between-two-strings/solutions/2998657/qian-zhui-he-on-zuo-fa-pythonjavacgo-by-b4hfx/
    public long shiftDistance(String s, String t, int[] nextCost, int[] previousCost) {
        final int SIGMA = 26;
        long[] nxtSum = new long[SIGMA * 2 + 1];
        long[] preSum = new long[SIGMA * 2 + 1];
        for (int i = 0; i < SIGMA * 2; i++) {
            nxtSum[i + 1] = nxtSum[i] + nextCost[i % SIGMA];
            preSum[i + 1] = preSum[i] + previousCost[i % SIGMA];
        }

        long ans = 0;
        for (int i = 0; i < s.length(); i++) {
            int x = s.charAt(i) - 'a';
            int y = t.charAt(i) - 'a';
            ans += Math.min(nxtSum[y < x ? y + SIGMA : y] - nxtSum[x],
                    preSum[(x < y ? x + SIGMA : x) + 1] - preSum[y + 1]);
        }
        return ans;
    }

}
