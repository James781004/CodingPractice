package EndlessCheng.GenreMenu.DP.Enhance.Trie;

public class MinExtraChar {

    // https://leetcode.cn/problems/extra-characters-in-a-string/solutions/2596770/python3javacgotypescript-yi-ti-shuang-ji-bidw/
    public int minExtraChar(String s, String[] dictionary) {
        Node root = new Node();
        for (String w : dictionary) {
            Node node = root;

            // 首先將字典中的每個單詞逆序插入到字典樹 root 中
            for (int k = w.length() - 1; k >= 0; --k) {
                int i = w.charAt(k) - 'a';
                if (node.children[i] == null) {
                    node.children[i] = new Node();
                }
                node = node.children[i];
            }
            node.isEnd = true;
        }
        int n = s.length();
        int[] f = new int[n + 1]; // 定義 f[i] 表示字符串 s 的前 i 個字符的最小額外字符數
        for (int i = 1; i <= n; ++i) {
            f[i] = f[i - 1] + 1; // 當 i≥1 時，第 i 個字符 s[i−1] 可以作為一個額外字符，此時 f[i]=f[i−1]+1
            Node node = root;
            for (int j = i - 1; j >= 0; --j) { // 在 [0..i−1] 的范圍內逆序枚舉下標 j，判斷 s[j..i) 是否在字典樹 root 中
                node = node.children[s.charAt(j) - 'a'];
                if (node == null) {
                    break;
                }

                // 如果存在，那麼我們可以將 s[j..i) 作為一個單詞，此時 f[i]=f[j]
                if (node.isEnd && f[j] < f[i]) {
                    f[i] = f[j];
                }
            }
        }
        return f[n];
    }

    static class Node {
        Node[] children = new Node[26];
        boolean isEnd;
    }

}
