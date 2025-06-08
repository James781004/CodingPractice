package WeeklyContest;

import java.util.ArrayDeque;
import java.util.Deque;

public class Week_453 {

    // https://leetcode.cn/problems/transform-array-to-all-equal-elements/solutions/3695707/liang-chong-qing-kuang-du-bian-cheng-1-h-pcpj/
    public boolean canMakeEqual(int[] nums, int k) {
        return check(nums, k, -1) || check(nums, k, 1);
    }

    private boolean check(int[] nums, int k, int target) {
        int n = nums.length;
        int left = k;
        int mul = 1;
        for (int i = 0; i < n; i++) {
            if (nums[i] * mul == target) {
                mul = 1; // 下一個數不用乘 -1
                continue;
            }
            if (left == 0 || i == n - 1) {
                return false;
            }
            left--;
            mul = -1; // 下一個數要乘 -1
        }
        return true;
    }


    // https://leetcode.cn/problems/count-the-number-of-computer-unlocking-permutations/solutions/3695709/nao-jin-ji-zhuan-wan-pythonjavacgo-by-en-fyrr/
    public int countPermutations(int[] complexity) {
        final int MOD = 1_000_000_007;
        long ans = 1;
        for (int i = 1; i < complexity.length; i++) {
            if (complexity[i] <= complexity[0]) {
                return 0;
            }
            ans = ans * i % MOD;
        }
        return (int) ans;
    }

    // https://leetcode.cn/problems/count-partitions-with-max-min-difference-at-most-k/solutions/3695716/hua-fen-xing-dp-dan-diao-dui-lie-you-hua-9rtj/
    public int countPartitions(int[] nums, int k) {
        final int MOD = 1_000_000_007;
        int n = nums.length;
        Deque<Integer> minQ = new ArrayDeque<>();
        Deque<Integer> maxQ = new ArrayDeque<>();
        int[] f = new int[n + 1]; // f[i+1] 表示前綴 nums[0] 到 nums[i] 在題目約束下，分割出的最少（最多）子數組個數
        f[0] = 1; // 初始值 f[0]=1，空子數組算一個方案
        long sumF = 0; // 窗口中的 f[i] 之和
        int left = 0; // 枚舉最後一個子數組的左端點

        for (int i = 0; i < n; i++) {
            // 1. 入
            sumF += f[i];

            int x = nums[i];

            // 維護最小值隊列（單調遞增）
            while (!minQ.isEmpty() && x <= nums[minQ.peekLast()]) {
                minQ.pollLast();
            }
            minQ.addLast(i);

            // 維護最大值隊列（單調遞減）
            while (!maxQ.isEmpty() && x >= nums[maxQ.peekLast()]) {
                maxQ.pollLast();
            }
            maxQ.addLast(i);

            // 2. 出
            // 調整左邊界，確保max - min <= k
            while (nums[maxQ.peekFirst()] - nums[minQ.peekFirst()] > k) {
                sumF -= f[left]; // 維護窗口
                left++;
                if (minQ.peekFirst() < left) {
                    minQ.pollFirst();
                }
                if (maxQ.peekFirst() < left) {
                    maxQ.pollFirst();
                }
            }

            // 3. 更新答案
            f[i + 1] = (int) (sumF % MOD);
        }

        return f[n]; // 前n個元素的合法方案數
    }

    // https://leetcode.cn/problems/minimum-steps-to-convert-string-with-operations/solutions/3695734/hua-fen-xing-dp-tan-xin-pythonjavacgo-by-17kb/
    public int minOperations(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int n = s.length;
        int[] f = new int[n + 1];

        for (int i = 0; i < n; i++) {
            int res = Integer.MAX_VALUE;
            int[][] cnt = new int[26][26];
            int op = 0;

            for (int j = i; j >= 0; j--) {
                // 不反轉
                int x = s[j] - 'a';
                int y = t[j] - 'a';
                if (x != y) {
                    if (cnt[y][x] > 0) {
                        cnt[y][x]--;
                    } else {
                        cnt[x][y]++;
                        op++;
                    }
                }

                // 反轉
                int[][] revCnt = new int[26][26];
                int revOp = 1;
                for (int p = j; p <= i; p++) {
                    x = s[p] - 'a';
                    y = t[i + j - p] - 'a';
                    if (x == y) {
                        continue;
                    }
                    if (revCnt[y][x] > 0) {
                        revCnt[y][x]--;
                    } else {
                        revCnt[x][y]++;
                        revOp++;
                    }
                }

                res = Math.min(res, f[j] + Math.min(op, revOp));
            }

            f[i + 1] = res;
        }

        return f[n];
    }


}









