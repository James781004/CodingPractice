package WeeklyContest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Week_415 {
    // https://leetcode.cn/problems/the-two-sneaky-numbers-of-digitville/solutions/2917852/o1-kong-jian-zuo-fa-pythonjavacgo-by-end-4vpn/
    // LC 2965 類似解法
    public int[] getSneakyNumbers(int[] nums) {
        int n = nums.length - 2;
        int xorAll = n ^ (n + 1); // n 和 n+1 多異或了
        for (int i = 0; i < nums.length; i++) {
            xorAll ^= i ^ nums[i];
        }
        int shift = Integer.numberOfTrailingZeros(xorAll);

        int[] ans = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (i < n) {
                ans[i >> shift & 1] ^= i;
            }
            ans[nums[i] >> shift & 1] ^= nums[i];
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-multiplication-score/solutions/2917840/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-9ul8/
    public long maxScore(int[] a, int[] b) {
        int n = b.length;
        long[][] memo = new long[n][4];
        for (long[] row : memo) {
            Arrays.fill(row, Long.MIN_VALUE); // 表示沒有計算過
        }

        // dfs(i,j)，表示從 b[0] 到 b[i] 選 j+1 個數，與 a[0] 到 a[j] 運算，結果的最大值
        return dfs(n - 1, 3, a, b, memo);
    }

    private long dfs(int i, int j, int[] a, int[] b, long[][] memo) {
        if (j < 0) {
            return 0;
        }
        if (i < 0) {
            return Long.MIN_VALUE / 2; // 防止溢出
        }
        if (memo[i][j] == Long.MIN_VALUE) { // 需要計算，並記憶化
            // i：當前要考慮 b[i] 選或不選。
            // j：如果選 b[i]，那麼與 a[j] 相乘。
            // 如果不選 b[i] -> dfs(i−1,j)
            // 如果選 b[i] -> dfs(i−1,j−1)+a[j]⋅b[i]
            memo[i][j] = Math.max(dfs(i - 1, j, a, b, memo), dfs(i - 1, j - 1, a, b, memo) + (long) a[j] * b[i]);
        }
        return memo[i][j];
    }


    public long maxScoreDP(int[] a, int[] b) {
        int n = b.length;
        long[][] f = new long[n + 1][5];
        Arrays.fill(f[0], 1, 5, Long.MIN_VALUE / 2);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 4; j++) {
                f[i + 1][j + 1] = Math.max(f[i][j + 1], f[i][j] + (long) a[j] * b[i]);
            }
        }
        return f[n][4];
    }


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
            if (cur == ac.root) {
                return -1;
            }
            f[i + 1] = f[i + 1 - cur.len] + 1;
        }
        return f[n];
    }


    class Node {
        Node[] son = new Node[26];
        Node fail; // 當 cur.son[i] 不能匹配 target 中的某個字符時，cur.fail.son[i] 即為下一個待匹配節點（等於 root 則表示沒有匹配）
        int len;

        Node(int len) {
            this.len = len;
        }
    }

    class AhoCorasick {
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


