package WeeklyContest;

import java.util.*;

public class Week_307 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2383.Minimum%20Hours%20of%20Training%20to%20Win%20a%20Competition/README.md
    // 貪心 + 模擬
    public int minNumberOfHours(int initialEnergy, int initialExperience, int[] energy, int[] experience) {
        int ans = 0;
        for (int i = 0; i < energy.length; i++) {
            int a = energy[i], b = experience[i];

            // 若經驗或者精力不足以超過對手，則補到剛好能超過
            if (initialEnergy <= a) {
                ans += a - initialEnergy + 1;
                initialEnergy = a + 1;
            }
            if (initialExperience <= b) {
                ans += b - initialExperience + 1;
                initialExperience = b + 1;
            }

            // 增加對應的經驗值，減少對應的精力值
            initialEnergy -= a;
            initialExperience += b;
        }
        return ans;
    }


    // 貪心
    public int minNumberOfHours2(int initialEnergy, int initialExperience, int[] energy, int[] experience) {
        // 先在初始時，把精力直接補充到足夠擊敗這 n 個對手
        int s = 0;
        for (int x : energy) {
            s += x;
        }

        // 接下來只需考慮經驗值的問題。
        // 遍歷 n 個對手，若當前經驗不足以超過對手，則將經驗補到剛好能超過該對手，
        // 擊敗對手後，把對手的經驗值加到自己身上。
        int ans = Math.max(0, s - initialEnergy + 1);
        for (int x : experience) {
            if (initialExperience <= x) {
                ans += x - initialExperience + 1;
                initialExperience = x + 1;
            }
            initialExperience += x;
        }
        return ans;
    }


    // https://leetcode.cn/problems/largest-palindromic-number/solution/tan-xin-by-endlesscheng-epjv/
    // 1.先統計每個數出現的個數
    // 2.貪心，【9-1】從數值大的往小開始遍歷，每次個數減2，直到個數為0或者1
    // 3.計算中間位 ：【9-0】從大到小，個數第一個位1的
    public String largestPalindromic(String num) {
        int[] count = new int[10];
        /*統計每個數字出現的次數*/
        for (char c : num.toCharArray()) {
            count[c - '0']++;
        }
        StringBuilder s = new StringBuilder();

        /*2.貪心，【9-1】從數值大的往小開始遍歷，每次個數減2，直到個數為0或者1*/
        for (int i = 9; i > 0; i--) {
            while (count[i] > 1) {
                s.append(String.valueOf(i));
                count[i] -= 2;
            }
        }
        /*若填了數字，則可以追加0*/
        if (s.length() > 0) {
            while (count[0] > 1) {
                s.append("0");
                count[0] -= 2;
            }
        }


        /*逆序，找出對稱的另一半*/
        StringBuilder t = new StringBuilder(s).reverse();

        /*3.計算中間位 ：【9-0】從大到小，個數第一個位1的*/
        for (int i = 9; i >= 0; i--) {
            if (count[i] == 1) {
                s.append(i);
                break;
            }
        }

        String result = s.append(t).toString();
        return result.length() == 0 ? "0" : result;
    }


    // https://www.bilibili.com/video/BV1md4y1P75q/
    // https://leetcode.cn/problems/amount-of-time-for-binary-tree-to-be-infected/solution/dfs-bfs-by-endlesscheng-48cz/
    HashMap<TreeNode, TreeNode> map;
    int start;
    TreeNode s;
    int res = -1;

    public int amountOfTime(TreeNode root, int start) {
        map = new HashMap<>();
        this.start = start;
        // 找到 傳染節點 並記錄每個節點的父節點
        dfs(null, root);

        // bfs 記錄 傳染時間
        Set<TreeNode> vis = new HashSet<>();
        vis.add(s);
        vis.add(null);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(s);

        while (!q.isEmpty()) {
            res++;
            int len = q.size();
            while (len-- > 0) {
                TreeNode node = q.poll();
                if (!vis.contains(node.left)) {
                    vis.add(node.left);
                    q.add(node.left);
                }
                if (!vis.contains(node.right)) {
                    vis.add(node.right);
                    q.add(node.right);
                }
                if (!vis.contains(map.get(node))) {
                    vis.add(map.get(node));
                    q.add(map.get(node));
                }
            }
        }
        return res;
    }

    public void dfs(TreeNode p, TreeNode node) {
        if (node == null) return;
        if (node.val == start) s = node;
        map.put(node, p);
        dfs(node, node.left);
        dfs(node, node.right);
    }


    class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    // https://leetcode.cn/problems/find-the-k-sum-of-an-array/solution/zhuan-huan-dui-by-endlesscheng-8yiq/
    public long kSum(int[] nums, int k) {
        long sum = 0L;

        // 將 nums 所有數取絕對值，這樣可以統一成從 sum 中減去某些數
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) sum += nums[i];
            else nums[i] = -nums[i];
        }

        // 將 nums 所有數取絕對值後排序，然後用最大堆來實現。
        // 最大堆維護子序列的和，以及（後續需要減去的）數字的下標 i
        // 初始時，將 sum 和下標 0 入堆。
        Arrays.sort(nums);
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Long.compare(b.key, a.key));
        pq.offer(new Pair(sum, 0));


        // 每次彈出堆頂時，將子序列的和減去 nums[i]，
        // 並考慮是否保留 nums[i−1]，從而滿足子序列每個元素「選或不選」的要求。
        while (--k > 0) {
            Pair p = pq.poll();
            Long s = p.key;
            int i = p.value;
            if (i < nums.length) {
                pq.offer(new Pair(s - nums[i], i + 1)); // 保留 nums[i-1]
                if (i > 0) pq.offer(new Pair(s - nums[i] + nums[i - 1], i + 1)); // 不保留 nums[i-1]，把之前減去的加回來
            }
        }

        // 循環 k−1 次後，堆頂的和就是答案
        return pq.peek().key;
    }

    class Pair {
        Long key;
        int value;

        Pair(Long k, int v) {
            key = k;
            value = v;
        }
    }
}
