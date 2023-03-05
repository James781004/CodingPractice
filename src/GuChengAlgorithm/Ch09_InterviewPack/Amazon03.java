package GuChengAlgorithm.Ch09_InterviewPack;

import java.util.*;

public class Amazon03 {
    // https://docs.google.com/presentation/d/1CxRIAPCOOHn3o3tHPgNIuAd_DA2tXBD1-DiOEDwjlkA/edit#slide=id.g14242a9a465_0_52
    class longestConsecutive {
        public int longestConsecutive(int[] nums) {
            Set<Integer> set = new HashSet<>();
            for (int n : nums) set.add(n);
            int res = 0;
            for (int i = 0; i < nums.length; i++) {
                int down = nums[i] - 1, up = nums[i] + 1, count = 1;
                while (set.contains(down)) {  // 一路把相鄰數字取出
                    set.remove(down);
                    down--;
                    count++;
                }
                while (set.contains(up)) {
                    set.remove(up);
                    up++;
                    count++;
                }
                res = Math.max(res, count);
            }
            return res;
        }


        public int longestConsecutive2(int[] nums) {
            DSU dsu = new DSU(nums.length);
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if (map.containsKey(nums[i])) continue;
                map.put(nums[i], i);
                if (map.containsKey(nums[i + 1])) dsu.union(i, map.get(nums[i] + 1));
                if (map.containsKey(nums[i - 1])) dsu.union(i, map.get(nums[i] - 1));
            }
            return dsu.findMax();
        }

        class DSU {
            int[] parent;
            int[] size;

            public DSU(int N) {
                parent = new int[N];
                size = new int[N];
                for (int i = 0; i < N; i++) {
                    parent[i] = i;
                    Arrays.fill(size, 1);
                }
            }

            public int find(int x) {
                if (parent[x] != x) parent[x] = find(parent[x]);
                return parent[x];
            }

            public void union(int x, int y) {
                int rootX = find(x), rootY = find(y);
                if (rootX == rootY) return;
                if (size[rootX] <= size[rootY]) {
                    parent[rootX] = rootY;
                    size[rootY] += size[rootX];
                } else {
                    parent[rootY] = rootX;
                    size[rootX] += size[rootY];
                }
            }

            public int findMax() {
                int max = 0;
                for (int s : size) max = Math.max(max, s);
                return max;
            }
        }
    }


    // https://docs.google.com/presentation/d/1CxRIAPCOOHn3o3tHPgNIuAd_DA2tXBD1-DiOEDwjlkA/edit#slide=id.g14242a9a465_0_69
    class coinChangeII {
        public int change(int amount, int[] coins) {
            int[][] dp = new int[coins.length + 1][amount + 1];
            for (int i = 0; i <= coins.length; i++) {
                dp[i][0] = 1;
            }

            for (int i = 0; i <= coins.length; i++) {
                for (int j = 1; j <= amount; j++) {
                    if (j - coins[i - 1] >= 0) {
                        dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
                    } else {
                        dp[i][j] = dp[i - 1][j];
                    }

                }
            }
            return dp[coins.length][amount];
        }

        Integer[][] memo;
        int[] coins;

        public int change2(int amount, int[] coins) {
            this.coins = coins;
            this.memo = new Integer[coins.length][amount + 1];
            return dfs(amount, 0);
        }

        private int dfs(int amount, int idx) {
            if (amount == 0) return 1;
            if (idx >= coins.length) return 0;
            if (memo[idx][amount] != null) return memo[idx][amount];
            int res = 0;
            if (amount >= coins[idx]) res += dfs(amount - coins[idx], idx);
            res += dfs(amount, idx + 1);
            return memo[idx][amount] = res;
        }
    }
}
