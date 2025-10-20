package EndlessCheng.GenreMenu.Backtracking.Backtracking.Combination;

import java.util.*;

public class RemoveInvalidParentheses {

    // https://leetcode.cn/problems/remove-invalid-parentheses/solutions/1069124/tong-ge-lai-shua-ti-la-yi-bu-bu-shen-du-jvrgz/
    public List<String> removeInvalidParentheses(String s) {
        // 先統計需要刪除的左括號數和右括號數
        int leftExtra = 0, rightExtra = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                leftExtra++;
            } else if (c == ')') {
                if (leftExtra == 0) {
                    rightExtra++;
                } else {
                    leftExtra--;
                }
            }
        }

        // 如果字符串本身就是合法的，就不用刪除了
        if (leftExtra == 0 && rightExtra == 0) {
            return Arrays.asList(s);
        }

        Set<String> ans = new HashSet<>();

        // 回溯
        dfs(ans, s, 0, 0, new StringBuilder(), leftExtra, rightExtra);

        return new ArrayList<>(ans);
    }

    private void dfs(Set<String> ans, String s, int i, int diff, StringBuilder sb, int leftExtra, int rightExtra) {
        if (i == s.length()) {
            if (diff == 0 && isValid(sb)) {
                ans.add(sb.toString());
            }
            return;
        }

        char c = s.charAt(i);
        if (c == '(') {
            // 要這個左括號
            sb.append(c);
            dfs(ans, s, i + 1, diff + 1, sb, leftExtra, rightExtra);
            sb.deleteCharAt(sb.length() - 1);

            // 不要這個左括號
            if (leftExtra > 0) {
                dfs(ans, s, i + 1, diff, sb, leftExtra - 1, rightExtra);
            }
        } else if (c == ')') {
            // 只有diff大於0的時候才要這個右括號
            if (diff > 0) {
                sb.append(c);
                dfs(ans, s, i + 1, diff - 1, sb, leftExtra, rightExtra);
                sb.deleteCharAt(sb.length() - 1);
            }

            // 不要這個右括號
            if (rightExtra > 0) {
                dfs(ans, s, i + 1, diff, sb, leftExtra, rightExtra - 1);
            }
        } else {
            sb.append(c);
            dfs(ans, s, i + 1, diff, sb, leftExtra, rightExtra);
            sb.deleteCharAt(sb.length() - 1);
        }

    }

    private boolean isValid(StringBuilder sb) {
        // 判斷括號是否合法
        int left = 0;
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c == '(') {
                left++;
            } else if (c == ')') {
                if (left == 0) {
                    return false;
                } else {
                    left--;
                }
            }
        }
        return left == 0;
    }


}
