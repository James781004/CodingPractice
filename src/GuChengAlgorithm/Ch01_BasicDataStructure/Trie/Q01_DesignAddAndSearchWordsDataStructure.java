package GuChengAlgorithm.Ch01_BasicDataStructure.Trie;

public class Q01_DesignAddAndSearchWordsDataStructure {
//    https://leetcode.com/problems/design-add-and-search-words-data-structure/
//    211. Design Add and Search Words Data Structure
//    Design a data structure that supports adding new words and finding if a string matches any previously added string.
//
//    Implement the WordDictionary class:
//
//    WordDictionary() Initializes the object.
//    void addWord(word) Adds word to the data structure, it can be matched later.
//    bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise.
//    word may contain dots '.' where dots can be matched with any letter.

    class TrieNode {
        boolean isWord;
        TrieNode[] children;

        public TrieNode() {
            children = new TrieNode[26];
        }
    }

    public class wordDictionary {
        TrieNode root = new TrieNode();

        public void addWord(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int key = c - 'a';
                if (node.children[key] == null) node.children[key] = new TrieNode();
                node = node.children[key];
            }
            node.isWord = true;
        }

        public boolean search(String word) {
            return helper(word, 0, root);
        }

        private boolean helper(String word, int pos, TrieNode node) {
            if (pos == word.length()) return node.isWord;
            char c = word.charAt(pos);
            int key = c - 'a';

            // Returns true if there is any string in the data structure that matches word or false otherwise.
            if (c != '.') {
                return node.children[key] != null && helper(word, pos + 1, node.children[key]);
            }

            // dots can be matched with any letter
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null && helper(word, pos + 1, node.children[i])) {
                    return true;
                }
            }

            return false;
        }
    }
}
