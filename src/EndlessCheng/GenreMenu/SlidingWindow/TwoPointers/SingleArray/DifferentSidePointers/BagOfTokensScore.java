package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

import java.util.Arrays;

public class BagOfTokensScore {

    // https://leetcode.cn/problems/bag-of-tokens/solutions/2980263/tan-xin-shuang-zhi-zhen-by-elated-kirchz-t3g8/
    // 貪心思路：每次都用最少的power得分，不能繼續得分時，考慮是否扣1分換取最多的power用來繼續得分
    public int bagOfTokensScore(int[] tokens, int power) {
        Arrays.sort(tokens);
        int i = 0, j = tokens.length - 1, ans = 0;
        while (i <= j && power >= tokens[i]) {
            while (i <= j && power >= tokens[i]) {
                power -= tokens[i++];
                ans++;
            }
            if (i < j) {
                power += tokens[j--];
                ans--;
            }
        }
        return ans;
    }

}
