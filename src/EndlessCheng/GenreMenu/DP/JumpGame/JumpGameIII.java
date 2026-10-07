package EndlessCheng.GenreMenu.DP.JumpGame;

public class JumpGameIII {

    // https://leetcode.cn/problems/jump-game-iii/solutions/3960993/you-xiang-tu-dfspythonjavaccgojsrust-by-ju1jr/
    public boolean canReach(int[] arr, int start) {
        int n = arr.length;
        boolean[] vis = new boolean[n];
        return dfs(start, arr, vis);
    }

    private boolean dfs(int i, int[] arr, boolean[] vis) {
        if (i < 0 || i >= vis.length || vis[i]) { // 出界，或者之前訪問過（沒找到）
            return false;
        }
        if (arr[i] == 0) { // 找到了
            return true;
        }
        vis[i] = true; // 避免重復訪問
        return dfs(i + arr[i], arr, vis) || dfs(i - arr[i], arr, vis);
    }

}
