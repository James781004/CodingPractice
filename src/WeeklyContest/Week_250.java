package WeeklyContest;

import java.util.*;

class Week_250 {
    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1935.Maximum%20Number%20of%20Words%20You%20Can%20Type/README.md
    public int canBeTypedWords(String text, String brokenLetters) {
        Set<Character> letters = new HashSet<>();
        for (char c : brokenLetters.toCharArray()) {
            letters.add(c);
        }
        int res = 0;
        for (String word : text.split(" ")) {
            boolean find = false;
            for (char c : letters) {
                if (word.indexOf(c) > -1) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                ++res;
            }
        }
        return res;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1936.Add%20Minimum%20Number%20of%20Rungs/README.md
    // 向上取整即可
    // 只需要考慮下一個梯子，若下一個梯子的高度大於當前高度+dist，需要通過疊加多少次dist實現
    public int addRungs(int[] rungs, int dist) {
        int res = 0;
        for (int i = 0, prev = 0; i < rungs.length; i++) {
            res += (rungs[i] - prev - 1) / dist;
            prev = rungs[i];
        }
        return res;
    }


    // https://leetcode.cn/problems/maximum-number-of-points-with-cost/solutions/882598/dong-tai-gui-hua-jian-zhi-by-shauna-vayn-t9o7/
    public long maxPoints(int[][] points) {
        int n = points.length;
        int m = points[0].length;
        long[][] dp = new long[n][m];  // 定義 dp[i][j] 表示前 i 行中，第 i 行選擇 points[i][j] 時的最大得分

        // 1. 先遍歷出points數組的第一行，放入dp數組，因為第一行不需要通過上面的計算結果來求最大值
        for (int i = 0; i < m; i++) {
            dp[0][i] = points[0][i];
        }

        // 2. 如果dp數組挨個去加當前行的每一列，再求最大值，則肯定超時
        // 3. 所以對於當前行的當前列points[i][j], 我們可以求出Math.max(max - 1,dp[i - 1][j]),
        //    其中max為 i - 1 行中，j 這一列前面的列的最大值，從 j = 0 開始，總是能求出max的值，max - 1 即為所求的前綴最大值，
        //    然後通過前綴最大值和dp[i - 1][j]比較
        // 4. 考慮到i - 1這一行，有可能最大值出現在後綴列，所以通過同樣的方法，
        //    求出後綴列與dp[i - 1][j] 作比較，同時與前綴列的dp[i][j]作比較
        for (int i = 1; i < n; i++) {
            long max = 0;
            for (int j = 0; j < m; j++) {
                // 從左邊遍歷
                // 注意max其實是不斷滾動更新尋找 i-1 行相對於當前位置 (i, j) 的最佳選項
                // 根據題意，座標距離導致減分， j 要是不同，每走一列都要減 1，
                // 所以先前算出的max，進入當前loop之後都會先減 1
                max = Math.max(max - 1, dp[i - 1][j]);
                dp[i][j] = max + points[i][j];
            }
            max = 0;
            for (int j = m - 1; j >= 0; j--) {
                // 從右邊遍歷，可以類推
                max = Math.max(max - 1, dp[i - 1][j]);
                dp[i][j] = Math.max(dp[i][j], max + points[i][j]);
            }
        }
        long max = 0;
        for (int i = 0; i < m; i++) {
            max = Math.max(max, dp[n - 1][i]);
        }
        return max;
    }


    // 思路：https://leetcode.cn/problems/maximum-genetic-difference-query/solutions/882220/chi-xian-zi-dian-shu-by-endlesscheng-ojnw/
    public int[] maxGeneticDifference(int[] parents, int[][] queries) {
        // 構造一棵樹，維護 父->一堆孩子的關系
        Map<Integer, List<Integer>> tree = new HashMap<>();
        for (int i = 0; i < parents.length; ++i) {
            if (!tree.containsKey(parents[i])) {
                tree.put(parents[i], new ArrayList<>());
            }
            tree.get(parents[i]).add(i);
        }

        // 構造查詢map    node->queryVal -> res   res先寫0
        Map<Integer, Map<Integer, Integer>> queryMap = new HashMap<>();
        for (int i = 0; i < queries.length; ++i) {
            if (!queryMap.containsKey(queries[i][0])) {
                queryMap.put(queries[i][0], new HashMap<>());
            }
            queryMap.get(queries[i][0]).put(queries[i][1], 0);
        }


        // 從root開始dfs
        dfs(new ZeroOneTrie(), tree, queryMap, tree.get(-1).get(0));

        // 把結果拼出來
        int[] r = new int[queries.length];
        for (int i = 0; i < r.length; ++i) {
            r[i] = queryMap.get(queries[i][0]).get(queries[i][1]);
        }
        return r;
    }


    private void dfs(ZeroOneTrie zeroOneTrie, Map<Integer, List<Integer>> tree,
                     Map<Integer, Map<Integer, Integer>> queryMap, int root) {
        // 把這個節點加入0-1字典樹
        zeroOneTrie.insert(root);

        // 做完root對應的查詢
        if (queryMap.containsKey(root)) {
            Map<Integer, Integer> resMap = queryMap.get(root);
            for (Integer query : resMap.keySet()) {
                resMap.put(query, query ^ zeroOneTrie.queryMax(query));
            }
        }

        // 下一層
        if (tree.containsKey(root)) {
            List<Integer> next = tree.get(root);
            for (int r : next) {
                dfs(zeroOneTrie, tree, queryMap, r);
            }
        }
        // 回溯
        zeroOneTrie.delete(root);
    }


    class ZeroOneTrie {

        Node root = new Node();

        /**
         * 按二進制從高到低插入
         *
         * @param num
         */
        public void insert(int num) {
            Node cur = root;
            for (int i = 31; i >= 0; --i) {
                int op = ((num >> i) & 1);
                if (cur.next[op] == null) {
                    cur.next[op] = new Node();
                }
                cur = cur.next[op];
                cur.count++;
            }
            cur.val = num;
        }

        public void delete(int num) {
            Node cur = root;
            for (int i = 31; i >= 0; --i) {
                int op = ((num >> i) & 1);
                if (cur.next[op].count == 1) {
                    cur.next[op] = null;
                    return;
                } else {
                    cur = cur.next[op];
                    cur.count--;
                }
            }
        }

        /**
         * 查詢所有數中和num異或結果最大的數
         *
         * @return
         */
        public int queryMax(int num) {
            Node cur = root;
            for (int i = 31; i >= 0; --i) {
                int op = ((num >> i) & 1) == 0 ? 1 : 0;
                if (cur.next[op] != null) {
                    cur = cur.next[op];
                } else {
                    op = op ^ 1;
                    cur = cur.next[op];
                }
            }
            return cur.val;
        }

        public int queryMin(int num) {
            Node cur = root;
            for (int i = 31; i >= 0; --i) {
                int op = ((num >> i) & 1);
                if (cur.next[op] != null) {
                    cur = cur.next[op];
                } else {
                    cur = cur.next[op ^ 1];
                }
            }
            return cur.val;
        }

        private class Node {
            public int count = 0;
            public int val = 0;
            public Node[] next = new Node[2];
        }
    }
}


