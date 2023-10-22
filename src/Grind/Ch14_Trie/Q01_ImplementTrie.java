package Grind.Ch14_Trie;

public class Q01_ImplementTrie {
    // https://leetcode.cn/problems/implement-trie-prefix-tree/solutions/720989/shu-ju-jie-gou-he-suan-fa-zi-dian-shu-de-6t43/
    public class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        // 插入字符串
        public void insert(String word) {
            TrieNode current = root;
            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';
                // 判斷字符有沒有創建，如果沒有創建就創建
                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                    // 中間的字符不是完整的單詞
                    current.children[index].isWord = false;
                }
                current = current.children[index];
            }
            // 最後一個字符才能構成一個完整的單詞
            current.isWord = true;
        }

        public boolean search(String word) {
            TrieNode cur = find(word);
            return cur != null && cur.isWord;
        }

        public boolean startsWith(String prefix) {
            return find(prefix) != null;
        }

        private TrieNode find(String str) {
            TrieNode cur = root;
            for (int i = 0; i < str.length(); i++) {
                int index = str.charAt(i) - 'a';
                if ((cur = cur.children[index]) == null) {
                    return null;
                }
            }
            return cur;
        }

    }

    class TrieNode {
        boolean isWord;//是否是單詞
        TrieNode[] children;//26個小寫字母

        public TrieNode() {
            isWord = true;
            children = new TrieNode[26];
        }
    }
}
