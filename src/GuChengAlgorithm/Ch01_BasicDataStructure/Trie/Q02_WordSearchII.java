package GuChengAlgorithm.Ch01_BasicDataStructure.Trie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Q02_WordSearchII {
//    https://leetcode.com/problems/word-search-ii/
//    212. Word Search II
//    Given an m x n board of characters and a list of strings words, return all words on the board.
//
//    Each word must be constructed from letters of sequentially adjacent cells,
//    where adjacent cells are horizontally or vertically neighboring.
//    The same letter cell may not be used more than once in a word.

    Set<String> res = new HashSet<>();  // remove duplicates

    public List<String> findWords(char[][] board, String[] words) {
        int M = board.length, N = board[0].length;
        Trie trie = new Trie();
        for (String s : words) trie.insert(s);
        boolean[][] visited = new boolean[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                dfs(board, visited, "", i, j, trie);
            }
        }
        return new ArrayList<>(res);
    }

    private void dfs(char[][] board, boolean[][] visited, String s, int x, int y, Trie trie) {
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || visited[x][y]) return;
        s += board[x][y];
        if (!trie.startsWith(s)) return; // 剪枝，如果startsWith已經沒有目前的字串，就直接跳過
        if (trie.search(s)) res.add(s); // trie search找到結果就加入結果集

        // 暴力搜尋四個方向
        visited[x][y] = true;
        dfs(board, visited, s, x + 1, y, trie);
        dfs(board, visited, s, x - 1, y, trie);
        dfs(board, visited, s, x, y + 1, trie);
        dfs(board, visited, s, x, y - 1, trie);
        visited[x][y] = false;  // 記得回溯狀態
    }


    class Trie {
        TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) node.children[c - 'a'] = new TrieNode();
                node = node.children[c - 'a'];
            }
            node.isWord = true;
        }

        public boolean search(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) return false;
                node = node.children[c - 'a'];
            }
            return node.isWord;
        }

        public boolean startsWith(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                if (node.children[c - 'a'] == null) return false;
                node = node.children[c - 'a'];
            }
            return true;
        }
    }


    class TrieNode {
        boolean isWord;
        TrieNode[] children;

        public TrieNode() {
            children = new TrieNode[26];
        }
    }
}
