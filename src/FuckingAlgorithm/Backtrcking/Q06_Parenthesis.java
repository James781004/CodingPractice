package FuckingAlgorithm.Backtrcking;

import java.util.ArrayList;
import java.util.List;

public class Q06_Parenthesis {
//    https://leetcode.cn/problems/generate-parentheses/
//    22. 括號生成
//    數字 n 代表生成括號的對數，請你設計一個函數，用於能夠生成所有可能的並且 有效的 括號組合。


    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();

        // 可用的左括號和右括號數量初始化為 n
        // StringBuilder代表回溯過程中的路徑
        backtrack(res, new StringBuilder(), n, n);
        return res;
    }

    // 可用的左括號數量為 left 個，可用的右括號數量為 rgiht 個
    private void backtrack(List<String> res, StringBuilder sb, int left, int right) {
        if (right < left) return;  // 若左括號剩下的多，說明不合法
        if (left < 0 || right < 0) return;  // 數量小於 0 肯定是不合法的

        // 當所有括號都恰好用完時，得到一個合法的括號組合
        if (left == 0 && right == 0) {
            res.add(sb.toString());
            return;
        }

        // 嘗試放一個左括號
        sb.append('('); // 選擇
        backtrack(res, sb, left - 1, right);
        sb.deleteCharAt(sb.length() - 1); // 撤消選擇

        // 嘗試放一個右括號
        sb.append(')'); // 選擇
        backtrack(res, sb, left, right - 1);
        sb.deleteCharAt(sb.length() - 1); // 撤消選擇
    }

}
