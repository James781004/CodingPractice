package EndlessCheng.GenreMenu.DP.Enhance.MonoQueue;

public class FindMaximumLength {

    // https://leetcode.cn/problems/find-maximum-non-decreasing-array-length/solutions/2542102/dan-diao-dui-lie-you-hua-dp-by-endlessch-j5qd/
    public int findMaximumLength(int[] nums) {
        int n = nums.length;
        long[] s = new long[n + 1];
        int[] f = new int[n + 1]; // f[i] 表示操作 nums[0] 到 nums[i] 所得到的最長非遞減數組的長度
        long[] last = new long[n + 1]; // last[i] 表示在 f[i] 盡量大的前提下，nums[0] 到 nums[i] 操作後的最後一個數的最小值
        int[] q = new int[n + 1]; // 數組模擬隊列
        int front = 0, rear = 0;
        for (int i = 1; i <= n; i++) {
            s[i] = s[i - 1] + nums[i - 1];

            // 1. 去掉隊首無用數據（計算轉移時，直接取隊首）
            while (front < rear && s[q[front + 1]] + last[q[front + 1]] <= s[i]) {
                front++;
            }

            // 2. 計算轉移
            f[i] = f[q[front]] + 1;
            last[i] = s[i] - s[q[front]];

            // 3. 去掉隊尾無用數據
            while (rear >= front && s[q[rear]] + last[q[rear]] >= s[i] + last[i]) {
                rear--;
            }
            q[++rear] = i;
        }
        return f[n];
    }


}
