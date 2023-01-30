package GuChengAlgorithm.Ch07_ClassicInterviewQuestion;

import java.util.HashMap;
import java.util.Map;

public class Q11_PaintHouse {
    // LC 256
    class PaintHouse {
        // 每一行指的是一個house，三個不同的位置指三個不同顏色
        // red 0, blue 1, green 2
        // 四個方向房子顏色不能和當前相同，所以找上方左右房子
        public int minCost(int[][] costs) {
            for (int i = 1; i < costs.length; i++) {
                costs[i][0] += Math.min(costs[i - 1][1], costs[i - 1][2]);
                costs[i][1] += Math.min(costs[i - 1][0], costs[i - 1][2]);
                costs[i][2] += Math.min(costs[i - 1][0], costs[i - 1][1]);
            }
            int n = costs.length - 1;
            return Math.min(Math.min(costs[n][0], costs[n][1]), costs[n][2]);
        }


        int[][] costs;
        Map<String, Integer> memo;

        public int minCost2(int[][] costs) {
            if (costs.length == 0) return 0;
            this.costs = costs;
            this.memo = new HashMap<>();
            return Math.min(paintCost(0, 0),
                    Math.min(paintCost(0, 1), paintCost(0, 2)));
        }

        private int paintCost(int n, int color) {
            String key = String.valueOf(n) + "_" + String.valueOf(color);
            if (memo.containsKey(key)) return memo.get(key);
            int totalCost = costs[n][color];
            if (n == costs.length - 1) {  // end
            } else if (color == 0) { // red
                totalCost += Math.min(paintCost(n + 1, 1), paintCost(n + 1, 2));
            } else if (color == 1) { // green
                totalCost += Math.min(paintCost(n + 1, 0), paintCost(n + 1, 2));
            } else if (color == 2) { // blue
                totalCost += Math.min(paintCost(n + 1, 0), paintCost(n + 1, 1));
            }
            memo.put(key, totalCost);
            return totalCost;
        }
    }


    // LC 265
    class PaintHouseII {
        public int minCost(int[][] costs) {
            if (costs.length == 0) return 0;
            int k = costs[0].length;
            int n = costs.length;

            for (int house = 1; house < n; house++) {
                for (int color = 0; color < k; color++) {
                    int min = Integer.MAX_VALUE;
                    for (int preColor = 0; preColor < k; preColor++) {
                        if (color == preColor) continue;
                        min = Math.min(min, costs[house - 1][preColor]);
                    }
                    costs[house][color] += min;
                }
            }

            // find the minimum in the last row
            int minCost = Integer.MAX_VALUE;
            for (int c : costs[n - 1]) minCost = Math.min(minCost, c);
            return minCost;
        }


        public int minCost2(int[][] costs) {
            if (costs.length == 0) return 0;
            int preMin = 0, preSec = 0, preMinIndex = -1;
            int row = costs.length, col = costs[0].length;
            for (int i = 0; i < row; i++) {
                int curMin = Integer.MAX_VALUE, curSec = Integer.MAX_VALUE, curMinIndex = 0;
                for (int j = 0; j < col; j++) {
                    costs[i][j] += preMinIndex == j ? preSec : preMin;
                    if (costs[i][j] < curMin) {
                        curSec = curMin;
                        curMin = costs[i][j];
                        curMinIndex = j;
                    } else if (costs[i][j] < curSec) curSec = costs[i][j];
                    preMin = curMin;
                    preSec = curSec;
                    preMinIndex = curMinIndex;
                }
            }
            return preMin;
        }


        int n;
        int k;
        int[][] costs;
        Map<String, Integer> memo;

        public int minCostMemo(int[][] costs) {
            if (costs.length == 0) return 0;
            this.k = costs[0].length;
            this.n = costs.length;
            this.costs = costs;
            this.memo = new HashMap<>();
            int minCost = Integer.MAX_VALUE;
            for (int color = 0; color < k; color++) {
                minCost = Math.min(minCost, helper(0, color));
            }
            return minCost;
        }

        private int helper(int houseNumber, int color) {
            if (houseNumber == n - 1) return costs[houseNumber][color];
            String key = String.valueOf(n) + "_" + String.valueOf(color);
            if (memo.containsKey(key)) return memo.get(key);

            int minRemainingCost = Integer.MAX_VALUE;
            for (int nextColor = 0; nextColor < k; nextColor++) {
                if (color == nextColor) continue;
                int cur = helper(houseNumber + 1, nextColor);
                minRemainingCost = Math.min(minRemainingCost, cur);
            }
            int totalCost = costs[houseNumber][color] + minRemainingCost;
            memo.put(key, totalCost);
            return totalCost;
        }
    }


    // LC 1473
    class PaintHouseIII {
        Long[][][] memo;
        int[] houses;
        int[][] cost;
        int m, n;

        public int minCost(int[] house, int[][] cost, int m, int n, int target) {
            this.memo = new Long[m + 1][n + 1][target + 1];
            this.houses = house;
            this.cost = cost;
            this.m = m;
            this.n = n;
            int res = (int) dfs(0, 0, target); // (curIndex, preColor, numberOfBlocksLeft)
            return res == Integer.MAX_VALUE ? -1 : res;
        }

        private long dfs(int cur, int preColor, int target) {
            if (target < 0) return Integer.MAX_VALUE;
            if (cur == m && target == 0) return 0;
            if (cur == m) return Integer.MAX_VALUE;
            if (preColor >= 0 && memo[cur][preColor][target] != null) return memo[cur][preColor][target];

            long minCost = Integer.MAX_VALUE;
            if (houses[cur] == 0) {
                for (int curColor = 1; curColor <= n; curColor++) {
                    if (curColor == preColor) {
                        minCost = Math.min(minCost, cost[cur][curColor - 1] + dfs(cur + 1, curColor, target));
                    } else {
                        minCost = Math.min(minCost, cost[cur][curColor - 1] + dfs(cur + 1, curColor, target - 1));
                    }
                }
            } else {
                if (houses[cur] == preColor) minCost = Math.min(minCost, dfs(cur + 1, houses[cur], target));
                else minCost = Math.min(minCost, dfs(cur + 1, houses[cur], target - 1));
            }
            return memo[cur][preColor][target] = minCost;
        }
    }
}
