package Grind.Ch02_String;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q14_PalindromePairs {
    // https://leetcode.cn/problems/palindrome-pairs/solutions/45018/java-trie-yi-yu-li-jie-by-copyreadmachine/
    private Node root;

    public List<List<Integer>> palindromePairs(String[] words) {
        this.root = new Node();
        int n = words.length;

        // 字典樹的插入，注意維護每個節點上的兩個列表
        for (int i = 0; i < n; i++) {
            String rev = new StringBuilder(words[i]).reverse().toString();
            Node cur = root;
            if (isPalindrome(rev.substring(0))) cur.suffixs.add(i); // suffixs 保存所有到當前節點為止，其之後剩余字符可以構成回文對的單詞
            for (int j = 0; j < rev.length(); j++) {
                char ch = rev.charAt(j);
                if (cur.children[ch - 'a'] == null) cur.children[ch - 'a'] = new Node();
                cur = cur.children[ch - 'a'];
                if (isPalindrome(rev.substring(j + 1))) cur.suffixs.add(i);
            }
            cur.words.add(i); //  words 存儲以當前節點為終止節點的所有單詞（逆序之後的)
        }

        // 用以存放答案的列表
        List<List<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String word = words[i];
            Node cur = root;
            int j = 0;
            for (; j < word.length(); j++) {
                // 到j位置，後續字符串若是回文對，則在該節點位置上所有單詞都可以與words[i]構成回文對
                // 因為我們插入的時候是用每個單詞的逆序插入的:)
                if (isPalindrome(word.substring(j)))
                    for (int k : cur.words)
                        if (k != i) ans.add(Arrays.asList(i, k));

                char ch = word.charAt(j);
                if (cur.children[ch - 'a'] == null) break;
                cur = cur.children[ch - 'a'];

            }
            // words[i]遍歷完了，現在找所有大於words[i]長度且符合要求的單詞，suffixs列表就派上用場了:)
            if (j == word.length())
                for (int k : cur.suffixs)
                    if (k != i) ans.add(Arrays.asList(i, k));

        }
        return ans;

    }

    //  判斷一個字符串是否是回文字符串
    private boolean isPalindrome(String w) {
        int i = 0, j = w.length() - 1;
        while (i < j) {
            if (w.charAt(i) != w.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }


    class Node {
        public Node[] children;
        public List<Integer> words;
        public List<Integer> suffixs;

        public Node() {
            this.children = new Node[26];
            this.words = new ArrayList<>();
            this.suffixs = new ArrayList<>();
        }
    }
}
