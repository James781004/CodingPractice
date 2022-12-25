package GuChengAlgorithm.Ch02_BasicAlgorithm.Recursive;

import java.util.ArrayList;
import java.util.List;

public class Q02_Backtracking {
    // https://docs.google.com/presentation/d/1kEm-0bFbe7PrDXVj3-fAGI7ugROjYvuUPmJUVlCYSEA/edit#slide=id.g1bbba5d6710_0_98
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        backtrack(res, new StringBuilder(), 0, 0, n);
        return res;
    }

    private void backtrack(List<String> res, StringBuilder cur, int open, int close, int max) {
        if (cur.length() == max * 2) {  // 規定長度為max pairs
            res.add(cur.toString());
            return;
        }

        if (open < max) {  // 判斷是否能添加左括號，只要還沒到max就可以
            cur.append("(");
            backtrack(res, cur, open + 1, close, max);
            cur.deleteCharAt(cur.length() - 1);
        }

        if (open > close) {  // 判斷是否能添加右括號，只要open左括號比close右括號還多就行
            cur.append(")");
            backtrack(res, cur, open, close + 1, max);
            cur.deleteCharAt(cur.length() - 1);
        }
    }


    // Avoid StringBuilder
    public List<String> generateParenthesis2(int n) {
        List<String> res = new ArrayList<>();
        dfs(n, n, "", res);
        return res;
    }

    private void dfs(int left, int right, String level, List<String> res) {
        if (left == 0 && right == 0) res.add(level);
        if (left > 0) dfs(left - 1, right, level + "(", res);
        if (right > left) dfs(left, right - 1, level + ")", res);
    }


    // https://docs.google.com/presentation/d/1kEm-0bFbe7PrDXVj3-fAGI7ugROjYvuUPmJUVlCYSEA/edit#slide=id.g1bbba5d6710_0_135
    public double myPow(double x, int n) {
        if (x == 0 || x == 1) return x;
        if (n < 0) return 1 / pow(x, -n);
        return pow(x, n);
    }

    private double pow(double x, int n) {
        if (n == 0) return 1;
        double y = pow(x, n / 2);
        if (n % 2 == 0) return y * y;
        else return y * y * x;
    }


    // https://docs.google.com/presentation/d/1kEm-0bFbe7PrDXVj3-fAGI7ugROjYvuUPmJUVlCYSEA/edit#slide=id.g1bbba5d6710_0_143
    public int mySqrt(int x) {
        int min = 0;
        int max = Integer.MAX_VALUE;
        return mySqrt(x, min, max);
    }

    private int mySqrt(int x, int min, int max) {
        int mid = min + (max - min) / 2;
        long misSquare = (long) mid * mid;
        long misPlusOneSquare = (long) (mid + 1) * (mid + 1);

        if (misSquare <= x && x < misPlusOneSquare) return mid;
        if (x < misSquare) return mySqrt(x, min, mid);
        if (misPlusOneSquare <= x) return mySqrt(x, mid + 1, max);
        return mid;
    }
}
