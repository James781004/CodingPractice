package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

public class MinIncrements {

    // https://leetcode.cn/problems/make-costs-of-paths-equal-in-a-binary-tree/solutions/2259983/tan-xin-jian-ji-xie-fa-pythonjavacgo-by-5svh1/
    public int minIncrements(int n, int[] cost) {
        int ans = 0;
        for (int i = n / 2; i > 0; i--) { // 從最後一個非葉節點開始算
            ans += Math.abs(cost[i * 2 - 1] - cost[i * 2]); // 兩個子節點變成一樣的
            cost[i - 1] += Math.max(cost[i * 2 - 1], cost[i * 2]); // 累加路徑和
        }
        return ans;
    }


}
