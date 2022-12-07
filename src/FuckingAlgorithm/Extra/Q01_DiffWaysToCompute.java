package FuckingAlgorithm.Extra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Q01_DiffWaysToCompute {
//    https://leetcode.cn/problems/different-ways-to-add-parentheses/
//    241. 為運算表達式設計優先級
//    給你一個由數字和運算符組成的字符串 expression ，按不同優先級組合數字和運算符，計算並返回所有可能組合的結果。你可以 按任意順序 返回答案。
//
//    生成的測試用例滿足其對應輸出值符合 32 位整數范圍，不同結果的數量不超過 104 。


    HashMap<String, List<Integer>> memo = new HashMap<>();

    public List<Integer> diffWaysToCompute(String input) {
        // 避免重複
        if (memo.containsKey(input)) {
            return memo.get(input);
        }
        List<Integer> res = new LinkedList<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            // 掃描算式 input 中的運算符
            if (c == '-' || c == '*' || c == '+') {
                /******分******/
                // 以運算符為中心，分割成兩個字符串，分別遞歸計算
                List<Integer>
                        left = diffWaysToCompute(input.substring(0, i));
                List<Integer>
                        right = diffWaysToCompute(input.substring(i + 1));
                /******治******/
                // 通過子問題的結果，合成原問題的結果
                for (int a : left)
                    for (int b : right)
                        if (c == '+')
                            res.add(a + b);
                        else if (c == '-')
                            res.add(a - b);
                        else if (c == '*')
                            res.add(a * b);
            }
        }
        // base case
        // 如果 res 為空，說明算式是一個數字，沒有運算符
        if (res.isEmpty()) {
            res.add(Integer.parseInt(input));
        }
        // 將結果添加進備忘錄
        memo.put(input, res);
        return res;
    }

}
