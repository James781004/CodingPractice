package WeeklyContest;

import java.util.*;

public class Week_419 {

    // https://leetcode.cn/problems/find-x-sum-of-all-k-long-subarrays-i/solutions/2948853/hua-dong-chuang-kou-you-xian-dui-lie-18m-kgyh/
    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        //1,
        HashMap<Integer, Integer> map = new HashMap<>();
        //2,
        PriorityQueue<Integer> queue = new PriorityQueue<>(
                (a, b) -> map.get(a).equals(map.get(b)) ? b - a : -(map.get(a) - map.get(b))//比较器构建小顶堆
        );

        //3,
        for (int j = 0; j < result.length; j++) {
            for (int i = j; i < k + j; i++) {
                map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            }
            for (int i = j; i < k + j; i++) {
                if (queue.contains(nums[i])) {
                    continue;
                }
                queue.add(nums[i]);
            }
            result[j] = sumTopX(map, queue, x);
            map.clear();
            queue.clear();
        }

        return result;
    }

    //计算x-sum
    private int sumTopX(HashMap<Integer, Integer> map, PriorityQueue<Integer> queue, int x) {
        int sum = 0;
        while (!queue.isEmpty() && x > 0) {
            int num = queue.poll();
            sum += map.get(num) * num;
            x--;
        }
        return sum;
    }


    // https://leetcode.cn/problems/k-th-largest-perfect-subtree-size-in-binary-tree/solutions/2948931/you-di-you-gui-de-er-cha-shu-dfspythonja-qy5j/
    public int kthLargestPerfectSubtree(TreeNode root, int k) {
        List<Integer> hs = new ArrayList<>();
        dfs(root, hs);

        if (k > hs.size()) {
            return -1;
        }
        Collections.sort(hs);
        return (1 << hs.get(hs.size() - k)) - 1;
    }

    private int dfs(TreeNode node, List<Integer> hs) {
        if (node == null) {
            return 0;
        }
        int leftH = dfs(node.left, hs);
        int rightH = dfs(node.right, hs);
        if (leftH < 0 || leftH != rightH) {
            return -1;
        }
        hs.add(leftH + 1);
        return leftH + 1;
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


    // https://leetcode.cn/problems/count-the-number-of-winning-sequences/solutions/2948860/dfsmemoshu-zu-ji-yi-hua-jian-dao-shi-tou-dcpf/
    public int countWinningSequences(String s) {
        int res = 0;
        len = s.length();
        // 初始化 記憶化數組memo
        memo = new int[len][len * 2 + 10][3];
        for (int i = 0; i < len; i++)
            for (int j = 0; j < len * 2 + 10; j++)
                for (int k = 0; k < 3; k++)
                    memo[i][j][k] = -1;

        ch = s.toCharArray();
        // F、W、E，3種都跑一下
        res = (res + dfs(len - 2, check('F', ch[len - 1]), 'F')) % mod;
        res = (res + dfs(len - 2, check('W', ch[len - 1]), 'W')) % mod;
        res = (res + dfs(len - 2, check('E', ch[len - 1]), 'E')) % mod;
        return res;
    }

    int mod = 1000000007;
    char[] ch;
    int len;
    int[][][] memo;

    public int dfs(int i, int j, char c) {
        if (i == -1) {
            if (j > 0) return 1;
            return 0;
        }

        if (i + 1 + j <= -1) return 0; //剪枝。

        int k = 0; //F是0，W是1，E是2
        if (c == 'W') k = 1;
        if (c == 'E') k = 2;
        if (memo[i][j + len][k] != -1) return memo[i][j + len][k];

        int res = 0;
        //從後往前dfs記憶化。注意 不能在連續兩個回合中召喚相同的生物
        if (c != 'F') res = (res + dfs(i - 1, j + check('F', ch[i]), 'F')) % mod;
        if (c != 'W') res = (res + dfs(i - 1, j + check('W', ch[i]), 'W')) % mod;
        if (c != 'E') res = (res + dfs(i - 1, j + check('E', ch[i]), 'E')) % mod;

        memo[i][j + len][k] = res;
        return res;
    }

    //兩字符判斷得分
    public int check(char x, char y) {
        if (x == 'F' && y == 'E') return 1;
        if (x == 'E' && y == 'F') return -1;

        if (x == 'W' && y == 'F') return 1;
        if (x == 'F' && y == 'W') return -1;

        if (x == 'E' && y == 'W') return 1;
        if (x == 'W' && y == 'E') return -1;

        return 0; //if (x == y)
    }


    // https://leetcode.cn/problems/find-x-sum-of-all-k-long-subarrays-ii/solutions/2948867/liang-ge-you-xu-ji-he-wei-hu-qian-x-da-p-2rcz/
    private final TreeSet<int[]> L = new TreeSet<>((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
    private final TreeSet<int[]> R = new TreeSet<>(L.comparator());
    private final Map<Integer, Integer> cnt = new HashMap<>();
    private long sumL = 0;

    public long[] findXSumII(int[] nums, int k, int x) {
        long[] ans = new long[nums.length - k + 1];
        for (int r = 0; r < nums.length; r++) {
            // 添加 in
            int in = nums[r];
            del(in);
            cnt.merge(in, 1, Integer::sum); // cnt[in]++
            add(in);

            int l = r + 1 - k;
            if (l < 0) {
                continue;
            }

            // 維護大小
            while (!R.isEmpty() && L.size() < x) {
                r2l();
            }
            while (L.size() > x) {
                l2r();
            }
            ans[l] = sumL;

            // 移除 out
            int out = nums[l];
            del(out);
            cnt.merge(out, -1, Integer::sum); // cnt[out]--
            add(out);
        }
        return ans;
    }

    // 添加元素
    private void add(int val) {
        int c = cnt.get(val);
        if (c == 0) {
            return;
        }
        int[] p = new int[]{c, val};
        if (!L.isEmpty() && L.comparator().compare(p, L.first()) > 0) { // p 比 L 中最小的還大
            sumL += (long) p[0] * p[1];
            L.add(p);
        } else {
            R.add(p);
        }
    }

    // 刪除元素
    private void del(int val) {
        int c = cnt.getOrDefault(val, 0);
        if (c == 0) {
            return;
        }
        int[] p = new int[]{c, val};
        if (L.contains(p)) {
            sumL -= (long) p[0] * p[1];
            L.remove(p);
        } else {
            R.remove(p);
        }
    }

    // 從 L 移動一個元素到 R
    private void l2r() {
        int[] p = L.pollFirst();
        sumL -= (long) p[0] * p[1];
        R.add(p);
    }

    // 從 R 移動一個元素到 L
    private void r2l() {
        int[] p = R.pollLast();
        sumL += (long) p[0] * p[1];
        L.add(p);
    }


}






