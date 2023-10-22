package Grind.Ch09_Recursion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Q05_GenerateParentheses {
    // https://leetcode.cn/problems/generate-parentheses/solutions/35947/hui-su-suan-fa-by-liweiwei1419/
    // 當前左右括號都有大於 0 個可以使用的時候，才產生分支；
    // 產生左分支的時候，只看當前是否還有左括號可以使用；
    // 產生右分支的時候，還受到左分支的限制，右邊剩余可以使用的括號數量一定得在嚴格大於左邊剩余的數量的時候，才可以產生分支；
    // 在左邊和右邊剩余的括號數都等於 0 的時候結算。

    // 做減法
    public List<String> generateParenthesisDFS(int n) {
        List<String> res = new ArrayList<>();
        // 特判
        if (n == 0) {
            return res;
        }

        // 執行深度優先遍歷，搜索可能的結果
        dfs("", n, n, res);
        return res;
    }

    /**
     * @param curStr 當前遞歸得到的結果
     * @param left   左括號還有幾個可以使用
     * @param right  右括號還有幾個可以使用
     * @param res    結果集
     */
    private void dfs(String curStr, int left, int right, List<String> res) {
        // 因為每一次嘗試，都使用新的字符串變量，所以無需回溯
        // 在遞歸終止的時候，直接把它添加到結果集即可，注意與「力扣」第 46 題、第 39 題區分
        if (left == 0 && right == 0) {
            res.add(curStr);
            return;
        }

        // 剪枝（如圖，左括號可以使用的個數嚴格大於右括號可以使用的個數，才剪枝，注意這個細節）
        if (left > right) {
            return;
        }

        if (left > 0) {
            dfs(curStr + "(", left - 1, right, res);
        }

        if (right > 0) {
            dfs(curStr + ")", left, right - 1, res);
        }
    }


    // 做加法

    public List<String> generateParenthesisDFS2(int n) {
        List<String> res = new ArrayList<>();
        // 特判
        if (n == 0) {
            return res;
        }

        dfs("", 0, 0, n, res);
        return res;
    }

    /**
     * @param curStr 當前遞歸得到的結果
     * @param left   左括號已經用了幾個
     * @param right  右括號已經用了幾個
     * @param n      左括號、右括號一共得用幾個
     * @param res    結果集
     */
    private void dfs(String curStr, int left, int right, int n, List<String> res) {
        if (left == n && right == n) {
            res.add(curStr);
            return;
        }

        // 剪枝
        if (left < right) {
            return;
        }

        if (left < n) {
            dfs(curStr + "(", left + 1, right, n, res);
        }
        if (right < n) {
            dfs(curStr + ")", left, right + 1, n, res);
        }
    }


    public List<String> generateParenthesisBFS(int n) {
        List<String> res = new ArrayList<>();
        if (n == 0) {
            return res;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node("", n, n));

        while (!queue.isEmpty()) {

            Node curNode = queue.poll();
            if (curNode.left == 0 && curNode.right == 0) {
                res.add(curNode.res);
            }
            if (curNode.left > 0) {
                queue.offer(new Node(curNode.res + "(", curNode.left - 1, curNode.right));
            }
            if (curNode.right > 0 && curNode.left < curNode.right) {
                queue.offer(new Node(curNode.res + ")", curNode.left, curNode.right - 1));
            }
        }
        return res;
    }

    class Node {
        /**
         * 當前得到的字符串
         */
        private String res;
        /**
         * 剩余左括號數量
         */
        private int left;
        /**
         * 剩余右括號數量
         */
        private int right;

        public Node(String str, int left, int right) {
            this.res = str;
            this.left = left;
            this.right = right;
        }
    }
}
