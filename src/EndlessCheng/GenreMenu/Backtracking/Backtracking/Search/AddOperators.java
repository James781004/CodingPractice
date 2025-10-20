package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.ArrayList;
import java.util.List;

public class AddOperators {

    // https://leetcode.cn/problems/expression-add-operators/solutions/174926/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-52/
    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<>();
        addOperatorsHelper(num, target, result, new StringBuilder(), 0, 0, 0);
        return result;
    }

    private void addOperatorsHelper(String num, int target, List<String> result, StringBuilder path, int start, long eval, long pre) {
        if (start == num.length()) {
            if (target == eval) {
                result.add(path.toString());
            }
            return;

        }
        for (int i = start; i < num.length(); i++) {
            // 數字不能以 0 開頭
            if (num.charAt(start) == '0' && i > start) {
                break;
            }
            long cur = Long.parseLong(num.substring(start, i + 1));
            int len = path.length();
            if (start == 0) {
                addOperatorsHelper(num, target, result, path.append(cur), i + 1, cur, cur);
                path.setLength(len);
            } else {

                // 加當前值
                addOperatorsHelper(num, target, result, path.append("+").append(cur), i + 1, eval + cur, cur);
                path.setLength(len);
                // 減當前值
                addOperatorsHelper(num, target, result, path.append("-").append(cur), i + 1, eval - cur, -cur);
                path.setLength(len);
                // 乘當前值
                addOperatorsHelper(num, target, result, path.append("*").append(cur), i + 1, eval - pre + pre * cur,
                        pre * cur);
                path.setLength(len);
            }
        }
    }


}
