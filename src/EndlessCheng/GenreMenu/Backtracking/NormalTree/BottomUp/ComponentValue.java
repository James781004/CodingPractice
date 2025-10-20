package EndlessCheng.GenreMenu.Backtracking.NormalTree.BottomUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComponentValue {

    // https://leetcode.cn/problems/create-components-with-same-value/solutions/1895302/by-endlesscheng-u03q/
    private List<Integer>[] g;
    private int[] nums;
    private int target;

    public int componentValue(int[] nums, int[][] edges) {
        g = new ArrayList[nums.length];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        this.nums = nums;

        int total = Arrays.stream(nums).sum();
        int max = Arrays.stream(nums).max().getAsInt();
        for (int i = total / max; ; i--) {

            // 由於 total 可以被 target 整除，只需要看一側的點權和是否為 target 的倍數
            if (total % i == 0) {

                // 如果 total 能被 i 整除（i 是 total 的因子），
                // 那麼每個連通塊的點權和都應等於 total / i，記作 target。
                target = total / i;


                // 如果從大到小枚舉連通塊的個數，則此時刪除的邊數是最多的，直接返回 i−1
                if (dfs(0, -1) == 0) {
                    return i - 1;
                }
            }
        }
    }

    private int dfs(int x, int fa) {
        int sum = nums[x];
        for (int y : g[x]) {
            if (y != fa) {
                var res = dfs(y, x);
                if (res < 0) return -1;
                sum += res;
            }
        }

        // 如果點權和超過 target，說明當前刪邊方案不合法，返回 −1
        if (sum > target) {
            return -1;
        }

        // 如果點權和等於 target，這條邊必須刪除，返回 0。
        // 如果點權和小於 target，尚未找到一個完整的連通塊，返回點權和。
        return sum < target ? sum : 0;
    }


}
