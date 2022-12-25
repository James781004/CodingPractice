package GuChengAlgorithm.Ch02_BasicAlgorithm.TwoPointers;

public class Q05_EggDrop {
    public int eggDrop(int K, int N) {
        int[][] memo = new int[K + 1][N + 1];
        return helper(K, N, memo);
    }

    private int helper(int K, int N, int[][] memo) {
        if (N <= 1) return N;  // 只有一層樓的時候只需嘗試一次
        if (K == 1) return N;  // 只有一個蛋，就只能一步一步嘗試
        if (memo[K][N] > 0) return memo[K][N];
        int low = 1, high = N, res = N;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int left = helper(K - 1, mid - 1, memo);  // 蛋破，少一個蛋，然後往下層找答案
            int right = helper(K, N - mid, memo);   // 蛋沒破，一樣K個蛋，然後排除掉下層往上層找答案
            res = Math.min(res, Math.max(left, right) + 1);  // 先考量最壞情況，加上當前一步操作，然後再跟res比較
            if (left == right) break;
            else if ((left < right)) low = mid + 1;
            else high = mid - 1;
        }
        memo[K][N] = res;
        return res;
    }

}
