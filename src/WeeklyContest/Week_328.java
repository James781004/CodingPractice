package WeeklyContest;

import java.util.*;

public class Week_328 {
    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2535.Difference%20Between%20Element%20Sum%20and%20Digit%20Sum%20of%20an%20Array/README.md
    public int differenceOfSum(int[] nums) {
        int a = 0, b = 0;
        for (int x : nums) {
            a += x;
            for (; x > 0; x /= 10) {
                b += x % 10;
            }
        }
        return Math.abs(a - b);
    }


    // https://leetcode.cn/problems/increment-submatrices-by-one/solution/er-wei-chai-fen-by-endlesscheng-mh0h/
    public int[][] rangeAddQueries(int n, int[][] queries) {
        // 二維前綴和的角度來看，對區域左上角 +1 會對所有右下位置產生影響，
        // 那麼在區域右上角的右邊相鄰處和左下角的下邊相鄰處 −1 可以消除這個影響，
        // 但是兩個 −1 又會對區域右下角的右下所有位置產生影響，
        // 所以要在右下角的右下相鄰處再 +1 還原回來。
        int[][] diff = new int[n + 2][n + 2], ans = new int[n][n];
        for (int[] q : queries) {
            int r1 = q[0], c1 = q[1], r2 = q[2], c2 = q[3];
            ++diff[r1 + 1][c1 + 1];
            --diff[r1 + 1][c2 + 2];
            --diff[r2 + 2][c1 + 1];
            ++diff[r2 + 2][c2 + 2];
        }

        // 用二維前綴和復原
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                ans[i - 1][j - 1] = diff[i][j] += diff[i][j - 1] + diff[i - 1][j] - diff[i - 1][j - 1];
            }
        }

        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2537.Count%20the%20Number%20of%20Good%20Subarrays/README.md
    public long countGood(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<Integer, Integer>();  // 統計窗口內每個元素的出現次數
        long ans = 0;
        int left = 0, pairs = 0;

        // 枚舉子數組右端點 right，那麼答案增加了 cnt[nums[right]]；
        // 然後看左端點 left 最大可以是多少，
        // 如果去掉左端點，答案沒有小於 k，就可以移動左端點。
        // 由於左端點及其左邊的都可以是好子數組的左端點，所以每個右端點對應的答案個數為 left+1
        for (int x : nums) {
            pairs += cnt.getOrDefault(x, 0); // 每次移動右端點，pairs數增加cnt[nums[x]]個，（相同數字可以湊成對）
            cnt.merge(x, 1, Integer::sum); // 移入右端點，注意要先更新pair，以滿足left < x條件
            while (pairs - (cnt.get(nums[left]) - 1) >= k)  // 維護窗口，需要有至少k對組合
                pairs -= cnt.merge(nums[left++], -1, Integer::sum); // 移出左端點，pairs數減少cnt[nums[left]]個
            if (pairs >= k) ans += left + 1;  // 下標位置從0開始，所以+1
        }
        return ans;
    }


    // https://leetcode.cn/problems/difference-between-maximum-and-minimum-price-sum/solution/by-endlesscheng-5l70/
    class MaxOutput {
        private List<Integer>[] g;
        private int[] price;
        private long ans;

        public long maxOutput(int n, int[][] edges, int[] price) {
            this.price = price;
            g = new ArrayList[n];
            Arrays.setAll(g, e -> new ArrayList<>());
            for (int[] e : edges) {
                int x = e[0], y = e[1];
                g[x].add(y);
                g[y].add(x); // 建樹
            }
            dfs(0, -1);
            return ans;
        }

        // 圖解： https://leetcode.cn/problems/difference-between-maximum-and-minimum-price-sum/solution/by-i3old-shockley6h7-4nhw/
        // 返回帶葉子的最大路徑和，不帶葉子的最大路徑和
        // 由於每個節點價值均為正整數，
        // 因此，以節點 x 作為根節點的最小的一條路徑就是 x 節點本身，
        // 那麼價值和最大的一條路徑與最小的一條路徑的差值就等價於去掉路徑的一個端點。
        private long[] dfs(int x, int father) {
            long p = price[x], maxS1 = p, maxS2 = 0;
            for (int y : g[x])
                if (y != father) {  // 父節點不要遍歷
                    long[] res = dfs(y, x);
                    long s1 = res[0], s2 = res[1];
                    // 前面最大帶葉子的路徑和 + 當前不帶葉子的路徑和
                    // 前面最大不帶葉子的路徑和 + 當前帶葉子的路徑和
                    ans = Math.max(ans, Math.max(maxS1 + s2, maxS2 + s1));
                    maxS1 = Math.max(maxS1, s1 + p);
                    maxS2 = Math.max(maxS2, s2 + p); // 這裡加上 p 是因為 x 必然不是葉子
                }
            return new long[]{maxS1, maxS2};
        }
    }
}
