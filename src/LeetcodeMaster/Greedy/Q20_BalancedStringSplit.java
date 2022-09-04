package LeetcodeMaster.Greedy;

public class Q20_BalancedStringSplit {
//    1221. 分割平衡字符串
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/1221.%E5%88%86%E5%89%B2%E5%B9%B3%E8%A1%A1%E5%AD%97%E7%AC%A6%E4%B8%B2.md
//
//    在一個 平衡字符串 中，'L' 和 'R' 字符的數量是相同的。
//
//    給你一個平衡字符串 s，請你將它分割成盡可能多的平衡字符串。
//
//    注意：分割得到的每個字符串都必須是平衡字符串。
//
//    返回可以通過分割得到的平衡字符串的 最大數量 。
//
//    示例 1：
//
//    輸入：s = "RLRRLLRLRL"
//    輸出：4
//    解釋：s 可以分割為 "RL"、"RRLL"、"RL"、"RL" ，每個子字符串中都包含相同數量的 'L' 和 'R' 。
//    示例 2：
//
//    輸入：s = "RLLLLRRRLR"
//    輸出：3
//    解釋：s 可以分割為 "RL"、"LLLRRR"、"LR" ，每個子字符串中都包含相同數量的 'L' 和 'R' 。
//    示例 3：
//
//    輸入：s = "LLLLRRRR"
//    輸出：1
//    解釋：s 只能保持原樣 "LLLLRRRR".
//    示例 4：
//
//    輸入：s = "RLRRRLLRLL"
//    輸出：2
//    解釋：s 可以分割為 "RL"、"RRRLLRLL" ，每個子字符串中都包含相同數量的 'L' 和 'R' 。


    // 從前向後遍歷，只要遇到平衡子串，計數就+1，遍歷一遍即可。
    // 局部最優：從前向後遍歷，只要遇到平衡子串 就統計
    // 全局最優：統計了最多的平衡子串。
    // 局部最優可以推出全局最優，舉不出反例，那麽就試試貪心。
    public int balancedStringSplit(String s) {
        int result = 0;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'R') count++;
            else count--;
            if (count == 0) result++;
        }
        return result;
    }
}
