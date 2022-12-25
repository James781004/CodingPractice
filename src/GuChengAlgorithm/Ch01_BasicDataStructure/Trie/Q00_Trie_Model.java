package GuChengAlgorithm.Ch01_BasicDataStructure.Trie;

public class Q00_Trie_Model {
    // https://docs.google.com/presentation/d/1WlREi20X20UEffSFI4F4cngDu2EvEcdmVAcN5_6gRQ4/edit#slide=id.gb792b67c7f_0_11
    // https://leetcode.com/problems/implement-trie-prefix-tree/
    // 208. Implement Trie (Prefix Tree)
    // A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a dataset of strings.
    // There are various applications of this data structure, such as autocomplete and spellchecker.
    //
    // Implement the Trie class:
    //
    // Trie() Initializes the trie object.
    // void insert(String word) Inserts the string word into the trie.
    // boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise.
    // boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix, and false otherwise.

    class Trie {
        TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                // int key = c - 'a';
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
