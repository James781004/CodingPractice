package Grind.Ch05_Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Q18_WordSearchII {
    // https://leetcode.cn/problems/word-search-ii/solutions/1000331/gong-shui-san-xie-yi-ti-shuang-jie-hui-s-am8f/
    Set<String> set = new HashSet<>();
    char[][] board;
    int n, m;
    TrieNode root = new TrieNode();
    int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    boolean[][] vis = new boolean[15][15];

    public List<String> findWords(char[][] _board, String[] words) {
        board = _board;
        m = board.length;
        n = board[0].length;
        for (String w : words) insert(w); // 將words數組中的所有字符串加入Trie中
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int u = board[i][j] - 'a'; // 遍歷所有二維網格上的字符
                if (root.tns[u] != null) { // 先找 word 的開頭，找到才開始dfs
                    vis[i][j] = true;
                    dfs(i, j, root.tns[u]);
                    vis[i][j] = false;
                }
            }
        }
        List<String> ans = new ArrayList<>();
        for (String s : set) ans.add(s);
        return ans;
    }

    // 目標是返回所有二維網格上的單詞
    void dfs(int i, int j, TrieNode node) {
        if (node.s != null) set.add(node.s); // node 有 s 值表示已經走到某字串的尾部節點
        for (int[] d : dirs) { // 上下左右四個方向不斷回溯嘗試
            int dx = i + d[0], dy = j + d[1];
            if (dx < 0 || dx >= m || dy < 0 || dy >= n) continue;
            if (vis[dx][dy]) continue;
            int u = board[dx][dy] - 'a';
            if (node.tns[u] != null) { // 當前 node 的 children 如果包括 u 才繼續走下去
                vis[dx][dy] = true;
                dfs(dx, dy, node.tns[u]);
                vis[dx][dy] = false;
            }
        }
    }


    class TrieNode {
        String s;
        TrieNode[] tns = new TrieNode[26];
    }

    void insert(String s) {
        TrieNode p = root;
        for (int i = 0; i < s.length(); i++) {
            int u = s.charAt(i) - 'a';
            if (p.tns[u] == null) p.tns[u] = new TrieNode();
            p = p.tns[u];
        }
        p.s = s; // 走到尾部節點才將完整 s 賦值給尾部節點 p
    }
}
