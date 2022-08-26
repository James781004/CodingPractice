package LeetcodeMaster.Greedy;

import java.util.Arrays;

public class Q01_Cookies {
//    455.分發餅乾
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0455.%E5%88%86%E5%8F%91%E9%A5%BC%E5%B9%B2.md
//
//    假設你是一位很棒的家長，想要給你的孩子們一些小餅乾。但是，每個孩子最多只能給一塊餅乾。
//
//    對每個孩子 i，都有一個胃口值 g[i]，這是能讓孩子們滿足胃口的餅乾的最小尺寸；並且每塊餅乾 j，都有一個尺寸 s[j]。
//    如果 s[j] >= g[i]，我們可以將這個餅乾 j 分配給孩子 i ，這個孩子會得到滿足。你的目標是盡可能滿足越多數量的孩子，並輸出這個最大數值。
//
//    示例 1:
//    輸入: g = [1,2,3], s = [1,1]
//    輸出: 1 解釋:你有三個孩子和兩塊小餅乾，3個孩子的胃口值分別是：1,2,3。雖然你有兩塊小餅乾，由於他們的尺寸都是1，你只能讓胃口值是1的孩子滿足。所以你應該輸出1。
//
//    示例 2:
//    輸入: g = [1,2], s = [1,2,3]
//    輸出: 2
//    解釋:你有兩個孩子和三塊小餅乾，2個孩子的胃口值分別是1,2。你擁有的餅乾數量和尺寸都足以讓所有孩子滿足。所以你應該輸出2.
//            提示：
//
//            1 <= g.length <= 3 * 10^4
//            0 <= s.length <= 3 * 10^4
//            1 <= g[i], s[j] <= 2^31 - 1


    // 思路1：優先考慮餅乾，小餅乾先喂飽小胃口
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int start = 0;
        int count = 0;

        for (int i = 0; i < s.length && start < g.length; i++) {
            if (s[i] >= g[start]) {
                start++;
                count++;
            }
        }

        return count;
    }


    // 思路2：優先考慮胃口，先喂飽大胃口
    public int findContentChildren2(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int start = s.length - 1;
        int count = 0;

        // 遍歷胃口
        for (int index = g.length - 1; index >= 0; index--) {
            if (start >= 0 && g[index] <= s[start]) {
                start--;
                count++;
            }
        }
        return count;
    }

}
