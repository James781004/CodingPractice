package GuChengAlgorithm.Ch02_BasicAlgorithm.Recursive;

import java.util.ArrayList;
import java.util.List;

public class Q04_DFS {
    // https://docs.google.com/presentation/d/1kEm-0bFbe7PrDXVj3-fAGI7ugROjYvuUPmJUVlCYSEA/edit#slide=id.g1bbba5d6710_0_114
    TrieNode root;

    public List<String> findWords(String[] words) {
        List<String> res = new ArrayList<>();
        root = new TrieNode();
        for (String word : words) addWord(word);
        for (String word : words) if (search(word, 0, 0)) res.add(word);
        return res;
    }

    private void addWord(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.children[c - 'a'] == null) cur.children[c - 'a'] = new TrieNode();
            cur = cur.children[c - 'a'];
        }
        cur.isWord = true;
    }

    private boolean search(String word, int index, int count) {
        if (index == word.length() && count > 1) return true;
        TrieNode cur = root;
        for (int i = index; i < word.length(); i++) {
            if (cur.children[word.charAt(i) - 'a'] == null) return false;
            cur = cur.children[word.charAt(i) - 'a'];
            if (cur.isWord && search(word, i + i, count + 1)) return true; // 找到一個word之後就回到起點重新搜索
        }
        return false;
    }


    class TrieNode {
        TrieNode[] children;
        boolean isWord;

        public TrieNode() {
            children = new TrieNode[26];
        }
    }


    // https://docs.google.com/presentation/d/1kEm-0bFbe7PrDXVj3-fAGI7ugROjYvuUPmJUVlCYSEA/edit#slide=id.g1bbba5d6710_0_123
    public int numDecodings(String s) {
        int[] memo = new int[s.length()];
        return helper(s, 0, memo);
    }

    private int helper(String s, int i, int[] memo) {
        if (i == s.length()) return 1;
        if (memo[i] > 0) return memo[i];
        if (s.charAt(i) == '0') memo[i] = 0; // digit is 0
        else if (validTwoDigits(s, i)) memo[i] = helper(s, i + 2, memo); // 2 digits
        else memo[i] = helper(s, i + 1, memo); // 1 digit
        return memo[i];
    }

    private boolean validTwoDigits(String s, int i) {
        return i + 1 < s.length() && (s.charAt(i) == '1' || (s.charAt(i) == '2' && s.charAt(i + 1) < '7'));  // 10 ~ 26
    }
}
