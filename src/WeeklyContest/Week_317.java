package WeeklyContest;

import java.util.*;

;

public class Week_317 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2455.Average%20Value%20of%20Even%20Numbers%20That%20Are%20Divisible%20by%20Three/README.md
    public int averageValue(int[] nums) {
        int s = 0, n = 0;
        for (int v : nums) {
            if (v % 6 == 0) {
                s += v;
                n++;
            }
        }
        return n == 0 ? 0 : s / n;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2456.Most%20Popular%20Video%20Creator/README.md
    public List<List<String>> mostPopularCreator(String[] creators, String[] ids, int[] views) {
        Map<String, Integer> cnt = new HashMap<>();  // 統計每個創作者的視頻播放量總和
        Map<String, Integer> d = new HashMap<>();  // 記錄每個創作者的最大播放量
        Map<String, String> x = new HashMap<>();  // 記錄每個創作者的最大播放量和對應的id
        int n = ids.length;
        for (int k = 0; k < n; k++) {
            String c = creators[k];
            String i = ids[k];
            int v = views[k];
            cnt.put(c, cnt.getOrDefault(c, 0) + v);
            if (!d.containsKey(c) || d.get(c) < v || (d.get(c) == v && x.get(c).compareTo(i) > 0)) {
                d.put(c, v);
                x.put(c, i);
            }
        }

        // 遍歷哈希表 cnt，找到最大視頻播放量總和的創作者，將其對應的視頻 id 加入答案數組 ans 中
        List<List<String>> ans = new ArrayList<>();
        int pre = -1;
        for (Map.Entry<String, Integer> e : cnt.entrySet()) {
            String a = e.getKey();
            int b = e.getValue();
            if (b > pre) {
                ans.clear();
                ans.add(Arrays.asList(a, x.get(a)));
                pre = b;
            } else if (b == pre) {
                ans.add(Arrays.asList(a, x.get(a)));
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2457.Minimum%20Addition%20to%20Make%20Integer%20Beautiful/README.md
    public long makeIntegerBeautiful(long n, int target) {
        long x = 0;
        while (f(n + x) > target) {
            long y = n + x;
            long p = 10;
            while (y % 10 == 0) {
                y /= 10;
                p *= 10;
            }
            x = (y / 10 + 1) * p - n;
        }
        return x;
    }

    // 表示一個整數 x 的每一位數字之和
    private int f(long x) {
        int v = 0;
        while (x > 0) {
            v += x % 10;
            x /= 10;
        }
        return v;
    }


    // https://leetcode.cn/problems/height-of-binary-tree-after-subtree-removal-queries/solution/liang-bian-dfspythonjavacgo-by-endlessch-vvs4/
    // https://www.bilibili.com/video/BV1Em4y1c7Hc/
    // 既然是求樹的高度，我們可以先跑一遍 DFS，求出每棵子樹的高度 height（這裡定義成最長路徑的節點數）。
    //
    // 然後再 DFS 一遍這棵樹，同時維護當前節點深度 depth（從 0 開始），
    // 以及刪除當前子樹後剩餘部分的樹的高度 restH（這裡定義成最長路徑的邊數）。
    //
    // 具體做法如下：
    //
    // 往左走，遞歸前算一下從根節點到當前節點右子樹最深節點的長度，
    // 即 depth+height[node.right]，與 restH 取最大值，然後往下遞歸；
    // 往右走，遞歸前算一下從根節點到當前節點左子樹最深節點的長度，
    // 即 depth+height[node.left]，與 restH 取最大值，然後往下遞歸。
    // 每個節點的答案即為遞歸到該節點時的 restH 值。
    // 代碼實現時可以直接把答案覆蓋到 /queries 數組中。

    private Map<TreeNode, Integer> height = new HashMap<>(); // 每棵子樹的高度
    private int[] res; // 每個節點的答案

    public int[] treeQueries(TreeNode root, int[] queries) {
        getHeight(root);
        height.put(null, 0); // 簡化 dfs 的代碼，這樣不用寫 getOrDefault
        res = new int[height.size()];
        dfs(root, -1, 0);
        for (int i = 0; i < queries.length; i++)
            queries[i] = res[queries[i]];
        return queries;
    }

    // 計算並紀錄每棵子樹的高度
    private int getHeight(TreeNode node) {
        if (node == null) return 0;
        int h = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        height.put(node, h);
        return h;
    }

    // 刪除當前子樹後剩餘部分的樹的高度
    private void dfs(TreeNode node, int depth, int restH) {
        if (node == null) return;
        depth++;
        res[node.val] = restH;  // 在遞歸前求出其餘部份高度
        dfs(node.left, depth, Math.max(restH, depth + height.get(node.right)));
        dfs(node.right, depth, Math.max(restH, depth + height.get(node.left)));
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
