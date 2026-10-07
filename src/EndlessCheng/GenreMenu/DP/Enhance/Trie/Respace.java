package EndlessCheng.GenreMenu.DP.Enhance.Trie;

import java.util.ArrayList;
import java.util.List;

public class Respace {

    // https://leetcode.cn/problems/re-space-lcci/solutions/322866/jian-dan-dp-trieshu-bi-xu-miao-dong-by-sweetiee/
    public int respace(String[] dictionary, String sentence) {
        // 構建字典樹
        Trie trie = new Trie();
        for (String word : dictionary) {
            trie.insert(word);
        }
        // 狀態轉移，dp[i] 表示字符串的前 i 個字符的最少未匹配數
        int n = sentence.length();
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1] + 1;
            for (int idx : trie.search(sentence, i - 1)) {
                dp[i] = Math.min(dp[i], dp[idx]);
            }
        }
        return dp[n];
    }


    static class Trie {
        TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        // 將單詞倒序插入字典樹
        public void insert(String word) {
            TrieNode cur = root;
            for (int i = word.length() - 1; i >= 0; i--) {
                int c = word.charAt(i) - 'a';
                if (cur.children[c] == null) {
                    cur.children[c] = new TrieNode();
                }
                cur = cur.children[c];
            }
            cur.isWord = true;
        }

        // 找到 sentence 中以 endPos 為結尾的單詞，返回這些單詞的開頭下標。
        public List<Integer> search(String sentence, int endPos) {
            List<Integer> indices = new ArrayList<>();
            TrieNode cur = root;
            for (int i = endPos; i >= 0; i--) {
                int c = sentence.charAt(i) - 'a';
                if (cur.children[c] == null) {
                    break;
                }
                cur = cur.children[c];
                if (cur.isWord) {
                    indices.add(i);
                }
            }
            return indices;
        }
    }

    static class TrieNode {
        boolean isWord;
        TrieNode[] children = new TrieNode[26];

        public TrieNode() {
        }
    }


}
