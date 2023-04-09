package WeeklyContest;

import java.util.*;

public class Week_335 {
    // https://www.youtube.com/watch?v=rtYMAnQdk9E

    // https://leetcode.com/problems/pass-the-pillow/description/
    class passThePillow {
        public int passThePillow(int n, int time) {
            int cur = 1;
            int direction = 1;
            while (time > 0) {
                if (cur == n) {
                    direction = -1;  // 到底部就轉換為-1開始倒退
                }

                if (cur == 1) {
                    direction = 1;  // 到頭部就轉換為+1開始前進
                }

                cur += direction;
                time--;
            }
            return cur;
        }


        // https://leetcode.com/problems/pass-the-pillow/solutions/3258150/2582-pass-the-pillow-java-sol/
        // 概念相同
        public int passThePillow2(int n, int time) {
            //     int count=0;
            //     for(int i=1; i<=time; i++){
            //         if(i < n){
            //             count+=1;
            //         }
            //         if(i>n){
            //             count--;
            //     }
            // }
            //     return count;
            int cur = 1;
            int d = 1;
            int ela = 0;
            while (ela < time) {
                cur += d;
                if (cur == n || cur == 1) {
                    d = -d;  // 到頭部或底部就轉換方向（正負轉換）
                }
                ela++;
            }
            return cur;
        }
    }


    // https://leetcode.com/problems/kth-largest-sum-in-a-binary-tree/description/
    class kthLargestLevelSum {
        public long kthLargestLevelSum(TreeNode root, int k) {
            PriorityQueue<Long> pq = new PriorityQueue<>();  // 小頂堆，pq中剩下k個，堆頂就是答案
            Queue<TreeNode> q = new LinkedList<>();  // BFS
            q.offer(root);
            while (!q.isEmpty()) {
                int size = q.size();
                long sum = 0L;
                for (int i = 0; i < size; i++) {
                    TreeNode cur = q.poll();
                    sum += cur.val; // 計算當前樹層的和
                    if (cur.left != null) q.offer(cur.left);
                    if (cur.right != null) q.offer(cur.right);
                }
                pq.offer(sum);  // 當前樹層的和算完加入pq
            }
            if (pq.size() < k) return -1;  // 總數不到k直接返回-1
            while (pq.size() > k) pq.poll();  // 多的元素排出
            return pq.peek();
        }

        // DFS
        List<Long> list = new ArrayList<>();

        public long kthLargestLevelSumDFS(TreeNode root, int k) {
            collectSum(root, 0);

            // 大頂堆，pq移除k - 1個，堆頂就是答案
            PriorityQueue<Long> pq = new PriorityQueue<>((a, b) -> Long.compare(b, a));
            pq.addAll(list);
            if (pq.size() < k) return -1;
            while (k-- > 1) pq.poll();
            return pq.poll();
        }

        private void collectSum(TreeNode root, int level) {
            if (root == null) return;
            if (level == list.size()) list.add((long) root.val);  // 到了新的level，加入新元素
            else list.set(level, list.get(level) + root.val);  // 到了舊的level，新元素和舊元素值相加
            collectSum(root.left, level + 1);
            collectSum(root.right, level + 1);
        }


        class TreeNode {
            int val;
            TreeNode left, right;

            public TreeNode(int v) {
                val = v;
            }
        }
    }


    // https://leetcode.com/problems/split-the-array-to-make-coprime-products/
    class findValidSplit {
        public int findValidSplit(int[] nums) {
            Map<Integer, Integer> left = new HashMap<>();
            Map<Integer, Integer> right = new HashMap<>();

            // 先從左往右看，把所有質因數放進right
            for (int num : nums) {
                for (int factor : divide(num)) {
                    right.put(factor, right.getOrDefault(factor, 0) + 1);
                }
            }

            // 從右往左看，把所有質因數放進left，right排除重複質因數
            for (int i = 0; i < nums.length - 1; i++) {
                for (int factor : divide(nums[1])) {
                    right.put(factor, right.get(factor) - 1); // right排除重複質因數
                    left.put(factor, left.getOrDefault(factor, 0) + 1); // 所有質因數放進left

                    // 因為右側已經沒有這個因數，左側也不需要考慮，一定是prime
                    if (right.get(factor) <= 0) left.remove(factor);

                    // left為空表示當下就是coprime(互質，兩數最大公因數為1)平衡點
                    if (left.isEmpty()) return i;
                }
            }
            return -1;
        }

        // Count Primes
        private List<Integer> divide(int num) {
            List<Integer> list = new ArrayList<>();
            for (int i = 2; i <= 1000; i++) {
                if (num % i == 0) {
                    list.add(i);
                    while (num % i == 0) num /= i;
                }
            }
            if (num > 1) list.add(num);
            return list;
        }
    }


    // https://leetcode.com/problems/number-of-ways-to-earn-points/
    class WaysToReachTarget {
        int MOD = (int) 1e9 + 7;

        public int waysToReachTarget(int target, int[][] types) {
            int N = types.length;
            int[][] dp = new int[N + 1][target + 1];
            for (int i = 0; i <= N; i++) {
                dp[i][0] = 1;  // 不做當前題目，唯一結果都是0分
            }
            for (int i = 1; i <= N; i++) {
                for (int j = 0; j <= target; j++) {
                    dp[i][j] = dp[i - 1][j];  // 不做當前題目，繼承前i-1累積結果
                    for (int k = 1; k < types[i - 1][0]; k++) {
                        int point = types[i - 1][1] * k;   // 當前題目一共可以選擇做k次
                        if (j - point >= 0) {  // 前i-1累積結果加上(i - 1, j - point)累積結果，計算總結果數
                            dp[i][j] = (dp[i][j] + dp[i - 1][j - point]) % MOD;
                        }
                    }
                }
            }
            return dp[N][target];
        }

        // 1D
        public int waysToReachTarget2(int target, int[][] types) {
            int mod = (int) 1e9 + 7, dp[] = new int[target + 1];
            dp[0] = 1;
            for (int[] t : types)
                for (int i = target; i > 0; --i)
                    for (int k = 1; k <= t[0] && i - t[1] * k >= 0; ++k)
                        dp[i] = (dp[i] + dp[i - t[1] * k]) % mod;
            return dp[target];
        }
    }

}
