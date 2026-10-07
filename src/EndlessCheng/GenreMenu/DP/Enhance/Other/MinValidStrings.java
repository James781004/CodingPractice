package EndlessCheng.GenreMenu.DP.Enhance.Other;

import java.util.ArrayDeque;
import java.util.Queue;

public class MinValidStrings {

    // https://leetcode.cn/problems/minimum-number-of-valid-strings-to-form-target-ii/solutions/2917929/ac-zi-dong-ji-pythonjavacgo-by-endlessch-hcqk/
    public int minValidStrings(String[] words, String target) {
        AhoCorasick ac = new AhoCorasick();
        for (String w : words) {
            ac.put(w);
        }
        ac.buildFail();

        char[] t = target.toCharArray();
        int n = t.length;
        int[] f = new int[n + 1];
        Node cur = ac.root;
        for (int i = 0; i < n; i++) {
            // 如果沒有匹配相當於移動到 fail 的 son[t[i]-'a']
            cur = cur.son[t[i] - 'a'];
            // 沒有任何字符串的前綴與 target[..i] 的後綴匹配
            if (cur == ac.root) {
                return -1;
            }
            f[i + 1] = f[i + 1 - cur.len] + 1;
        }
        return f[n];
    }

    // 從根到 node 的字符串是某個（某些）words[i] 的前綴
    static class Node {
        Node[] son = new Node[26];
        Node fail; // 當 cur.son[i] 不能匹配 target 中的某個字符時，cur.fail.son[i] 即為下一個待匹配節點（等於 root 則表示沒有匹配）
        int len;

        Node(int len) {
            this.len = len;
        }
    }

    static class AhoCorasick {
        Node root = new Node(0);

        void put(String s) {
            Node cur = root;
            for (char b : s.toCharArray()) {
                b -= 'a';
                if (cur.son[b] == null) {
                    cur.son[b] = new Node(cur.len + 1);
                }
                cur = cur.son[b];
            }
        }

        void buildFail() {
            root.fail = root;
            Queue<Node> q = new ArrayDeque<>();
            for (int i = 0; i < root.son.length; i++) {
                Node son = root.son[i];
                if (son == null) {
                    root.son[i] = root;
                } else {
                    son.fail = root; // 第一層的失配指針，都指向根節點 ∅
                    q.add(son);
                }
            }
            // BFS
            while (!q.isEmpty()) {
                Node cur = q.poll();
                for (int i = 0; i < 26; i++) {
                    Node son = cur.son[i];
                    if (son == null) {
                        // 虛擬子節點 cur.son[i]，和 cur.fail.son[i] 是同一個
                        // 方便失配時直接跳到下一個可能匹配的位置（但不一定是某個 words[k] 的最後一個字母）
                        cur.son[i] = cur.fail.son[i];
                        continue;
                    }
                    son.fail = cur.fail.son[i]; // 計算失配位置
                    q.add(son);
                }
            }
        }
    }


}
