package Grind.Ch11_Stack;

import java.util.Stack;

public class Q13_LongestValidParentheses {
    // https://leetcode.cn/problems/longest-valid-parentheses/solutions/314807/javadai-ma-de-ji-chong-jie-fa-by-sdwwld/
    // // https://leetcode.cn/problems/longest-valid-parentheses/solutions/9110/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-7/
    public int longestValidParentheses(String s) {
        int max = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') { // 遇到左括號就把他的下標壓棧
                stack.push(i);
            } else {
                // 遇到右括號說明和棧頂元素匹配
                // 那麼棧頂元素出棧，用當前元素的下標減去新的棧頂元素的值
                // 為什麼減去新的棧頂元素值，這是因為新的棧頂元素還沒匹配成功，
                // 之前的棧頂元素m到現在元素的下標之間構成了有效的括號
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    max = Math.max(max, i - stack.peek());
                }
            }
        }
        return max;
    }


    public int longestValidParentheses2(String s) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        // avoid result contains 0
        int start = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                // reset start if '(' is not found before
                if (stack.isEmpty()) {
                    start = i;
                } else {
                    // '(' is already found before
                    // pop the existing result
                    // get the length from i to start
                    stack.pop();
                    if (stack.isEmpty()) {
                        res = Math.max(res, i - start);
                    } else {
                        // for cases like "(())"
                        res = Math.max(res, i - stack.peek());
                    }
                }

            }
        }
        return res;
    }


    public int longestValidParenthesesDP(String s) {
        Stack<Integer> stk = new Stack<>();
        // dp[i] 的定義：記錄以 s[i-1] 結尾的最長合法括號子串長度
        int[] dp = new int[s.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                // 遇到左括號，記錄索引
                stk.push(i);
                // 左括號不可能是合法括號子串的結尾
                dp[i + 1] = 0;
            } else {
                // 遇到右括號
                if (!stk.isEmpty()) {
                    // 配對的左括號對應索引
                    int leftIndex = stk.pop();
                    // 以這個右括號結尾的最長子串長度
                    int len = 1 + i - leftIndex + dp[leftIndex];
                    dp[i + 1] = len;
                } else {
                    // 沒有配對的左括號
                    dp[i + 1] = 0;
                }
            }
        }
        // 計算最長子串的長度
        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    // 配合圖解：https://leetcode.cn/problems/longest-valid-parentheses/solutions/9110/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-7/
    public int longestValidParenthesesDP2(String s) {
        int maxans = 0;
        int dp[] = new int[s.length()]; // dp [ i ] 代表以下標 i 結尾的合法序列的最長長度
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                // 右括號前邊是 ( ，類似於 ……（）
                // dp [ i ] = dp [ i - 2] + 2 （前一個合法序列的長度，加上當前新增的長度 2）
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    // 右括號前邊是 )，類似於 ……））
                    // 此時需要判斷 i - dp[i - 1] - 1 （前一個合法序列的前一個位置） 是不是左括號
                    // 如果是，當前位置的前一個合法序列的長度，加上匹配的左括號前邊的合法序列的長度，加上新增的長度 2
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }


    // https://leetcode.cn/problems/longest-valid-parentheses/solutions/9110/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-7/
    // 神奇解法
    public int longestValidParentheses3(String s) {
        // 從左到右掃描，用兩個變量 left 和 right 保存的當前的左括號和右括號的個數，都初始化為 0
        int left = 0, right = 0, maxlength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }

            // 果左括號個數大於右括號個數了，那麼就接著向右邊掃描
            // 如果左括號個數等於右括號個數了，那麼就更新合法序列的最長長度
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * right);
            } else if (right >= left) {
                // 如果左括號數目小於右括號個數了，那麼後邊無論是什麼，此時都不可能是合法序列了，
                // 此時 left 和 right 歸 0，然後接著掃描
                left = right = 0;
            }
        }

        // 從左到右掃描完畢後，同樣的方法從右到左再來一次
        // 因為類似這樣的情況 ( ( ( ) ) ，如果從左到右掃描到最後，
        // left = 3，right = 2，期間不會出現 left == right
        // 但是如果從右向左掃描，掃描到倒數第二個位置的時候，
        // 就會出現 left = 2，right = 2 ，就會得到一種合法序列
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * left);
            } else if (left >= right) {
                left = right = 0;
            }
        }
        return maxlength;
    }
}
