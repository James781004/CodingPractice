package Grind.Ch14_Trie;

public class Q03_DesignAddAndSearchWordsDataStructure {
    // https://leetcode.cn/problems/design-add-and-search-words-data-structure/solutions/72184/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by--43/
    class WordDictionary {
        TrieNode root;

        /**
         * Initialize your data structure here.
         */
        public WordDictionary() {
            root = new TrieNode();
        }


        /**
         * Adds a word into the data structure.
         */
        public void addWord(String word) {
            char[] array = word.toCharArray();
            TrieNode cur = root;
            for (int i = 0; i < array.length; i++) {
                // 當前孩子是否存在
                if (cur.children[array[i] - 'a'] == null) {
                    cur.children[array[i] - 'a'] = new TrieNode();
                }
                cur = cur.children[array[i] - 'a'];
            }
            // 當前節點代表結束
            cur.flag = true;
        }

        /**
         * Returns if the word is in the data structure. A word could contain the
         * dot character '.' to represent any one letter.
         */
        public boolean search(String word) {
            return searchHelp(word, root);
        }


        private boolean searchHelp(String word, TrieNode root) {
            char[] array = word.toCharArray();
            TrieNode cur = root;
            for (int i = 0; i < array.length; i++) {
                // 對於 . , 遞歸的判斷所有不為空的孩子
                if (array[i] == '.') {
                    for (int j = 0; j < 26; j++) {
                        if (cur.children[j] != null) {
                            if (searchHelp(word.substring(i + 1), cur.children[j])) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
                // 不含有當前節點
                if (cur.children[array[i] - 'a'] == null) {
                    return false;
                }
                cur = cur.children[array[i] - 'a'];
            }
            // 當前節點是否為是某個單詞的結束
            return cur.flag;
        }


        class TrieNode {
            TrieNode[] children;
            boolean flag;

            public TrieNode() {
                children = new TrieNode[26];
                flag = false;
                for (int i = 0; i < 26; i++) {
                    children[i] = null;
                }
            }
        }

    }
}
