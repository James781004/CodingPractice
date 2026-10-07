package EndlessCheng.GenreMenu.DP.Enhance.Other;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class MinimumCost {

    // https://leetcode.cn/problems/construct-string-with-minimum-cost/solutions/2833949/hou-zhui-shu-zu-by-endlesscheng-32h9/
    public int minimumCost(String target, String[] words, int[] costs) {
        AhoCorasick ac = new AhoCorasick();
        for (int i = 0; i < words.length; i++) {
            ac.put(words[i], costs[i]);
        }
        ac.buildFail();

        char[] t = target.toCharArray();
        int n = t.length;
        int[] f = new int[n + 1];
        Arrays.fill(f, Integer.MAX_VALUE / 2);
        f[0] = 0;
        Node cur = ac.root;
        for (int i = 1; i <= n; i++) {
            cur = cur.son[t[i - 1] - 'a']; // 如果沒有匹配相當於移動到 fail 的 son[t[i-1]-'a']
            if (cur.len > 0) { // 匹配到了一個盡可能長的 words[k]
                f[i] = Math.min(f[i], f[i - cur.len] + cur.cost);
            }
            // 還可能匹配其余更短的 words[k]，要在 last 鏈上找
            for (Node match = cur.last; match != ac.root; match = match.last) {
                f[i] = Math.min(f[i], f[i - match.len] + match.cost);
            }
        }
        return f[n] == Integer.MAX_VALUE / 2 ? -1 : f[n];
    }

    static class Node {
        Node[] son = new Node[26];
        Node fail; // 當 cur.son[i] 不能匹配 target 中的某個字符時，cur.fail.son[i] 即為下一個待匹配節點（等於 root 則表示沒有匹配）
        Node last; // 後綴鏈接（suffix link），用來快速跳到一定是某個 words[k] 的最後一個字母的節點（等於 root 則表示沒有）
        int len;
        int cost = Integer.MAX_VALUE;
    }

    static class AhoCorasick {
        Node root = new Node();

        void put(String s, int cost) {
            Node cur = root;
            for (char b : s.toCharArray()) {
                b -= 'a';
                if (cur.son[b] == null) {
                    cur.son[b] = new Node();
                }
                cur = cur.son[b];
            }
            cur.len = s.length();
            cur.cost = Math.min(cur.cost, cost);
        }

        void buildFail() {
            root.fail = root.last = root;
            Queue<Node> q = new ArrayDeque<>();
            for (int i = 0; i < root.son.length; i++) {
                Node son = root.son[i];
                if (son == null) {
                    root.son[i] = root;
                } else {
                    son.fail = son.last = root; // 第一層的失配指針，都指向根節點 ∅
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
                    // 沿著 last 往上走，可以直接跳到一定是某個 words[k] 的最後一個字母的節點（如果跳到 root 表示沒有匹配）
                    son.last = son.fail.len > 0 ? son.fail : son.fail.last;
                    q.add(son);
                }
            }
        }
    }


}
