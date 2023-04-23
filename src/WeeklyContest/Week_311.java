package WeeklyContest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Week_311 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2413.Smallest%20Even%20Multiple/README.md
    public int smallestEvenMultiple(int n) {
        return n % 2 == 0 ? n : n * 2;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2414.Length%20of%20the%20Longest%20Alphabetical%20Continuous%20Substring/README.md
    public int longestContinuousSubstring(String s) {
        int ans = 0;
        int i = 0, j = 1;  // 用雙指針 i 和 j 分別指向當前連續子字符串的起始位置和結束位置
        for (; j < s.length(); j++) {
            ans = Math.max(ans, j - i);
            if (s.charAt(j) - s.charAt(j - 1) != 1) {
                i = j;  // 非連續字符，i位置更新
            }
        }
        ans = Math.max(ans, j - i);
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2415.Reverse%20Odd%20Levels%20of%20Binary%20Tree/README.md
    public TreeNode reverseOddLevels(TreeNode root) {
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        int i = 0;
        while (!q.isEmpty()) {
            List<TreeNode> t = new ArrayList<>();
            for (int n = q.size(); n > 0; n--) {
                TreeNode node = q.pollFirst();
                if (i % 2 == 1) {
                    t.add(node);
                }
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }

            // 遍歷到奇數層時，反轉該層節點的值
            if (!t.isEmpty()) {
                int j = 0, k = t.size() - 1;
                for (; j < k; ++j, --k) {
                    int v = t.get(j).val;
                    t.get(j).val = t.get(k).val;
                    t.get(k).val = v;
                }
            }
            i++;
        }
        return root;
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    // https://leetcode.cn/problems/sum-of-prefix-scores-of-strings/solution/by-endlesscheng-ghfr/
    // https://www.bilibili.com/video/BV1AP411p7pK/
    // 用字典樹存儲所有字符串，由於每個節點都是其子樹節點的前綴，
    // 題干中的分數就是在字符串插入字典樹的過程中，經過該節點的字符串個數，
    // 即以該節點為前綴的字符串的個數
    public int[] sumPrefixScores(String[] words) {
        Node root = new Node();
        for (String word : words) {
            Node cur = root;
            for (char c : word.toCharArray()) {
                c -= 'a';
                if (cur.children[c] == null) cur.children[c] = new Node();
                cur = cur.children[c];
                ++cur.score; // 更新所有前綴的分數
            }
        }

        // 遍歷每個字符串，在字典樹上查找，累加路徑上的分數之和就是答案
        int n = words.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            Node cur = root;
            for (char c : words[i].toCharArray()) {
                cur = cur.children[c - 'a'];
                ans[i] += cur.score; // 累加分數，即可得到答案
            }
        }
        return ans;
    }


    // 另外一種寫法是插入字符串後，DFS 這顆字典樹，累加路徑上的分數，就可以得到每個字符串的所有非空前綴的分數總和。
    // 代碼實現時，由於可能有相同字符串，每個字符串對應的節點需要用一個列表存儲該字符串在 words 中的所有下標。
    int[] ans;

    private void dfs(Node node, int sum) {
        sum += node.score; // 累加分數，即可得到答案
        for (int i : node.ids)
            ans[i] += sum;
        for (Node child : node.children)
            if (child != null)
                dfs(child, sum);
    }

    public int[] sumPrefixScoresDFS(String[] words) {
        int n = words.length;
        Node root = new Node();
        for (int i = 0; i < n; i++) {
            Node cur = root;
            for (char c : words[i].toCharArray()) {
                c -= 'a';
                if (cur.children[c] == null) cur.children[c] = new Node();
                cur = cur.children[c];
                ++cur.score; // 更新所有前綴的分數
            }
            cur.ids.add(i);
        }
        ans = new int[n];
        dfs(root, 0);
        return ans;
    }


    class Node {
        Node[] children = new Node[26];
        int score;
        List<Integer> ids = new ArrayList<>();
    }

}
