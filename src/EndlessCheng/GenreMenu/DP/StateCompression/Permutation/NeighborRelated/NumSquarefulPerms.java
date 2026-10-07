package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborRelated;

import java.util.*;

public class NumSquarefulPerms {

    // https://leetcode.cn/problems/number-of-squareful-arrays/solutions/3614/zheng-fang-xing-shu-zu-de-shu-mu-by-leetcode/
    public int numSquarefulPerms(int[] nums) {
        int n = nums.length;
        // 數據預處理：找到每個數字可以組成平方數的其它數字
        List<List<Integer>> neighbors = getNeighbors(nums, n);
        // dp[s][i]: 未選擇的數字集合為s，當前選擇的下標為i，平方數組的排列數
        int setSize = 1 << n;
        int[][] dp = new int[setSize][n];
        Arrays.fill(dp[0], 1);
        int res = 0;

        for (int s = 1; s < setSize; s++) {
            // 枚舉當前選擇的數字下標
            for (int cur = 0; cur < n; cur++) {
                // 必須包含在未選擇的數字集合s中
                if ((s >> cur & 1) == 0) {
                    continue;
                }
                // 待選集合只有一個數字，直接選擇，方案數為1
                if (Integer.bitCount(s) == 1) {
                    dp[s][cur] = 1;
                    continue;
                }
                // 枚舉下一個選擇的數字
                int cnt = 0;
                for (int next : neighbors.get(cur)) {
                    if ((s >> next & 1) == 1) {
                        cnt += dp[s ^ (1 << cur)][next];
                    }
                }
                dp[s][cur] = cnt;
                if (s == setSize - 1) {
                    res += dp[s][cur];
                }
            }
        }
        // 去重
        res = deduplication(nums, n, res);
        return res;
    }

    private List<List<Integer>> getNeighbors(int[] nums, int n) {
        List<List<Integer>> neighbors = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            neighbors.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (j != i && isPerfectSquare(nums[i] + nums[j])) {
                    neighbors.get(i).add(j);
                    neighbors.get(j).add(i);
                }
            }
        }
        return neighbors;
    }

    private static int deduplication(int[] nums, int n, int res) {
        Map<Integer, Integer> count = new HashMap<>(n);
        int maxCnt = 0;
        for (int num : nums) {
            count.merge(num, 1, Integer::sum);
            maxCnt = Math.max(maxCnt, count.get(num));
        }
        int[] factorials = new int[maxCnt + 1];
        factorials[0] = 1;
        for (int i = 1; i <= maxCnt; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
        for (Integer cnt : count.values()) {
            res /= factorials[cnt];
        }
        return res;
    }

    private boolean isPerfectSquare(int num) {
        int sqrt = (int) Math.sqrt(num);
        return sqrt * sqrt == num;
    }


}
