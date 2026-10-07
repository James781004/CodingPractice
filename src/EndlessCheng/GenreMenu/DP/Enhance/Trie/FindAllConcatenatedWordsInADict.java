package EndlessCheng.GenreMenu.DP.Enhance.Trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindAllConcatenatedWordsInADict {

    // https://leetcode.cn/problems/concatenated-words/solutions/119178/472-lian-jie-ci-java-trie-dfs-by-mmmmmmosky/

    /**
     * Trie + DFS
     * 首先把所有的字符串添加到 Trie 中
     * 然後依次判斷每個字符串是否屬於答案集合
     * 在前綴樹中沿著當前字符串往下走, 碰到一個 isWord 節點時有兩個選擇: 繼續往下走或者從頭重新走
     * 如果最終能夠恰好走到 isWord 節點, 那麼說明當前字符串屬於答案集合
     * 為了避免當前字符串自身也在 Trie 中導致一定能夠走到 isWord 節點, 可以在判斷之前先將該單詞刪除 (出於效率, 沒有必要刪除邊, 只需要清除標記)
     * 或者加一些額外的判斷
     */
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            if (!word.equals("")) {  // 題目描述有問題, 應該補充上連接詞至少由兩個非空單詞組成, 否則如果 words 包含空串, 那麼 words 中的其它所有字符串都是連接詞. 這裡加上特判就可以了.
                root.add(word);
            }
        }
        List<String> res = new ArrayList<>();
        for (String word : words) {
            if (dfs(word.toCharArray(), 0, root)) {
                res.add(word);
            }
        }
        return res;
    }

    private boolean dfs(char[] str, int start, TrieNode root) {
        int n = str.length;
        TrieNode node = root;
        for (int i = start; i < n; i++) {
            if (!node.next.containsKey(str[i])) {
                return false;
            }
            node = node.next.get(str[i]);
            // 短路運算: 如果找到了一個字符串則嘗試從頭開始走, 找下一個字符串
            if (node.isWord && dfs(str, i + 1, root)) {
                return true;
            }
        }
        return node.isWord && start != 0;  // start != 0 含義即是不能匹配到自己
    }

    static class TrieNode {
        public boolean isWord;
        public Map<Character, TrieNode> next;

        public TrieNode() {
            isWord = false;
            next = new HashMap<>();
        }

        public void add(String str) {
            TrieNode node = this;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (!node.next.containsKey(c)) {
                    node.next.put(c, new TrieNode());
                }
                node = node.next.get(c);
            }
            node.isWord = true;
        }
    }


}
