package GuChengAlgorithm.Ch07_ClassicInterviewQuestion;

import java.util.*;

public class Q06_Parentheses {
    // https://docs.google.com/presentation/d/1TEGC2enyTGPUmTgUjtOmWZ9ij18nXrcP5cXjyPFw3-Q/edit#slide=id.gec2f251673_0_17
    // 最基礎的rolling state
    // 忽略數字和符號
    // 只統計開括號，（開括號+1， ）開括號-1
    // 深度就是最大開括號
    class MaxDepthParentheses {
        public int maxDepth(String s) {
            int res = 0, count = 0;
            for (char c : s.toCharArray()) {
                if (c == '(') count++;
                else if (c == ')') count--;
                res = Math.max(res, count);
            }
            return res;
        }
    }


    // https://docs.google.com/presentation/d/1TEGC2enyTGPUmTgUjtOmWZ9ij18nXrcP5cXjyPFw3-Q/edit#slide=id.gec2f251673_0_39
    // 我們還是用平衡的思路，遇到左括號狀態+1，有括號狀態-1，在過程中所有遇到負數的情況我們都需要補，意思是負數代表太多右括號了，左括號需要補了。
    // 最後剩餘state為正則需要補對應的右括號
    class MinAddParentheses {
        public int minAdd(String s) {
            int left = 0, right = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') right++; // 出現左括號，需要右括號填補
                if (s.charAt(i) == ')') {
                    if (right > 0) right--;  // 有欠債已經補了一個，欠債減少
                    else left++; // 沒有欠債直接補left
                }
            }

            return left + right;
        }
    }


    // https://docs.google.com/presentation/d/1TEGC2enyTGPUmTgUjtOmWZ9ij18nXrcP5cXjyPFw3-Q/edit#slide=id.gec2f251673_0_5
    // rolling state思路,stack輔助不同類型
    class ValidParentheses {
        public boolean isValid(String s) {
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(' || c == '{' || c == '[') stack.push(c);
                else {
                    if (stack.isEmpty()) return false;
                    char pre = stack.pop();
                    if (pre == '(' && c != ')') return false;
                    if (pre == '{' && c != '}') return false;
                    if (pre == '[' && c != ']') return false;
                }
            }
            return stack.isEmpty();
        }

        public boolean isValid2(String s) {
            Stack<Character> stack = new Stack<>();
            for (char c : s.toCharArray()) {
                if (c == '(') stack.push(')');
                else if (c == '[') stack.push(']');
                else if (c == '{') stack.push('}');
                else if (stack.isEmpty() || stack.pop() != c) return false;
            }
            return stack.isEmpty();
        }
    }


    // https://docs.google.com/presentation/d/1TEGC2enyTGPUmTgUjtOmWZ9ij18nXrcP5cXjyPFw3-Q/edit#slide=id.gec2f251673_0_64
    class MinRemoveParentheses {
        public String minRemoveValid(String s) {
            Set<Integer> indexesToRemove = new HashSet<>();
            Stack<Integer> stack = new Stack<>();

            // 把遇到的括號都儲存，左括號push，右括號pop
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') stack.push(i);
                else if (s.charAt(i) == ')') {
                    if (stack.isEmpty()) indexesToRemove.add(i);  // 遇到無效字元就把index存進set
                    else stack.pop();
                }
            }
            while (!stack.isEmpty()) indexesToRemove.add(stack.pop());  // 把尚未排出的index存進set
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (!indexesToRemove.contains(i)) sb.append(s.charAt(i));
            }
            return sb.toString();
        }

        public String minRemoveValid2(String s) {
            StringBuilder sb = new StringBuilder();
            int balance = 0;

            // remove all invalid ")"
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(') balance++;
                if (c == ')') {
                    if (balance == 0) continue;
                    balance--;
                }
                sb.append(c);
            }

            // remove all invalid "("
            StringBuilder result = new StringBuilder();
            int N = sb.length();
            balance = 0;
            for (int i = N - 1; i >= 0; i--) {
                char c = sb.charAt(i);
                if (c == ')') balance++;
                if (c == '(') {
                    if (balance == 0) continue;
                    balance--;
                }
                result.append(c);
            }

            return result.reverse().toString();
        }
    }


    // https://docs.google.com/presentation/d/1TEGC2enyTGPUmTgUjtOmWZ9ij18nXrcP5cXjyPFw3-Q/edit#slide=id.gec2f251673_0_54
    class ScoreOfParentheses {
        
        // stack裡面存的是開括號前一層的數字，閉括號的時候需要講前一層的數字*2並且和更前面的數字求和，再保存回stack
        public int scoreOfParentheses(String s) {
            Stack<Integer> stack = new Stack<>();
            int cur = 0;
            for (char c : s.toCharArray()) {
                if (c == '(') {
                    stack.push(cur);
                    cur = 0;
                } else {
                    cur = stack.pop() + Math.max(cur * 2, 1);
                }
            }
            return cur;
        }

        public int scoreOfParentheses2(String s) {
            Stack<Integer> stack = new Stack<>();
            stack.push(0);  // the score of current frame
            for (char c : s.toCharArray()) {
                if (c == '(') {
                    stack.push(0);
                } else {
                    int v = stack.pop();  // 括號裡的值
                    int w = stack.pop();  // 前面平行value
                    stack.push(w + Math.max(2 * v, 1));
                }
            }
            return stack.pop();
        }
    }


    // https://docs.google.com/presentation/d/1TEGC2enyTGPUmTgUjtOmWZ9ij18nXrcP5cXjyPFw3-Q/edit#slide=id.gec2f251673_0_92
    class LongestValidParentheses {
        public int LongestValidParentheses(String s) {
            int res = 0;
            Stack<Integer> stack = new Stack<>();
            stack.push(-1);  // 哨兵節點
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') stack.push(i);  // stack儲存的都是valid的起點
                else {  // 出現右括號時先pop出左括號，再判斷長度
                    stack.pop();
                    if (stack.isEmpty()) stack.push(i);
                    else res = Math.max(res, i - stack.peek());
                }
            }
            return res;
        }


        public int LongestValidParentheses2(String s) {
            int cntl = 0, cntr = 0, result = 0;
            for (char c : s.toCharArray()) {
                if (c == '(') cntl++;
                else cntr++;
                if (cntl == cntr) {  // count parentheses
                    result = Math.max(result, 2 * cntl);
                } else if (cntr > cntl) {  // too many right brackets, reset both cnt
                    cntl = cntr = 0;
                }
            }
            cntl = cntr = 0;
            for (int i = s.length() - 1; i >= 0; i--) {
                if (s.charAt(i) == '(') cntl++;
                else cntr++;
                if (cntl == cntr) {
                    result = Math.max(result, 2 * cntl);
                } else if (cntl > cntr) {  // too many left brackets, reset both cnt
                    cntl = cntr = 0;
                }
            }
            return result;
        }
    }


    // https://docs.google.com/presentation/d/1TEGC2enyTGPUmTgUjtOmWZ9ij18nXrcP5cXjyPFw3-Q/edit#slide=id.gec2f251673_1_2
    class GenerateParentheses {
        public List<String> generateParentheses(int n) {
            List<String> res = new ArrayList<>();
            backtrack(res, new StringBuilder(), 0, 0, n);
            return res;
        }

        private void backtrack(List<String> res, StringBuilder cur, int open, int close, int max) {
            if (cur.length() == max * 2) {
                res.add(cur.toString());
                return;
            }

            if (open < max) {
                cur.append("(");
                backtrack(res, cur, open + 1, close, max);
                cur.deleteCharAt(cur.length() - 1);
            }

            if (close < open) {
                cur.append(")");
                backtrack(res, cur, open, close + 1, max);
                cur.deleteCharAt(cur.length() - 1);
            }
        }

        private void backtrack(List<String> res, int left, int right, String level) {
            if (left > right) return;
            if (left == 0 && right == 0) res.add(level);
            if (left > 0) backtrack(res, left - 1, right, level + "(");
            if (right > 0) backtrack(res, left, right - 1, level + ")");
        }
    }


    // https://docs.google.com/presentation/d/1TEGC2enyTGPUmTgUjtOmWZ9ij18nXrcP5cXjyPFw3-Q/edit#slide=id.gec2f251673_1_18
    class DifferentParentheses {
        public List<Integer> diffWays(String input) {
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (ch == '+' || ch == '-' || ch == '*') {  // divide two part
                    List<Integer> leftRes = diffWays(input.substring(0, i));
                    List<Integer> rightRes = diffWays(input.substring(i + 1));

                    for (int left : leftRes) {
                        for (int right : rightRes) {
                            int tmp = 0;
                            if (ch == '+') tmp = left + right;
                            else if (ch == '-') tmp = left - right;
                            else tmp = left * right;
                            res.add(tmp);
                        }
                    }
                }
            }

            // 如果只有數字沒有符號
            if (res.size() == 0) res.add(Integer.parseInt(input));
            return res;
        }
    }


    // https://docs.google.com/presentation/d/1TEGC2enyTGPUmTgUjtOmWZ9ij18nXrcP5cXjyPFw3-Q/edit#slide=id.gec2f251673_1_28
    class RemoveInvalidParentheses {
        // 暴力窮舉仍然使用hashset去重
        public List<String> removeInvalidParentheses(String s) {
            int rmL = 0, rmR = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') rmL++;
                else if (s.charAt(i) == ')') {
                    if (rmL != 0) rmL--;
                    else rmR++;
                }
            }
            Set<String> res = new HashSet<>();
            dfs(s, 0, res, new StringBuilder(), rmL, rmR, 0);
            return new ArrayList<>(res);
        }

        private void dfs(String s, int i, Set<String> res, StringBuilder sb, int rmL, int rmR, int open) {
            if (rmL < 0 || rmR < 0 || open < 0) return;
            if (i == s.length()) {
                if (rmL == 0 && rmR == 0 && open == 0) res.add(sb.toString());
                return;
            }

            char c = s.charAt(i);
            int len = sb.length();

            if (c == '(') {
                dfs(s, i + 1, res, sb, rmL - 1, rmR, open);   // not use (
                dfs(s, i + 1, res, sb.append(c), rmL, rmR, open);  // use (
            } else if (c == ')') {
                dfs(s, i + 1, res, sb, rmL, rmR - 1, open);   // not use )
                dfs(s, i + 1, res, sb.append(c), rmL, rmR, open);  // use )
            } else {
                dfs(s, i + 1, res, sb.append(c), rmL, rmR, open);  // always use letter
            }
            sb.setLength(len);
        }


        private void dfs(String s, int i, List<String> res, String temp, int rmL, int rmR, int open, char lastSkipped) {
            if (rmL < 0 || rmR < 0 || open < 0) return;
            if (i == s.length()) {
                if (rmL == 0 && rmR == 0 && open == 0) res.add(temp);
                return;
            }

            char c = s.charAt(i);
            if (c == '(') {
                if (rmL > 0) dfs(s, i + 1, res, temp, rmL - 1, rmR, open, '(');   // not use (
                if (lastSkipped != '(')
                    dfs(s, i + 1, res, temp + c, rmL, rmR, open + 1, '&');  // use (
            } else if (c == ')') {
                if (rmR > 0) dfs(s, i + 1, res, temp, rmL, rmR - 1, open, ')');   // not use )
                if (open > 0 && lastSkipped != ')')
                    dfs(s, i + 1, res, temp + c, rmL, rmR, open - 1, '&');  // use )
            } else dfs(s, i + 1, res, temp + c, rmL, rmR, open, '&');  // always use letter

        }
    }
}
