package EndlessCheng.Basic.SlidingWindow;

import java.util.Arrays;
import java.util.HashMap;

public class countPairs {

    // https://leetcode.cn/problems/count-pairs-of-nodes/solutions/2400682/ji-bai-100cong-shuang-zhi-zhen-dao-zhong-yhze/
    public int[] countPairs(int n, int[][] edges, int[] queries) {
        // deg[i] 表示與點 i 相連的邊的數目
        var deg = new int[n + 1]; // 節點編號從 1 到 n
        var cntE = new HashMap<Integer, Integer>();
        for (var e : edges) {
            int x = e[0], y = e[1];
            if (x > y) {
                // 交換 x 和 y，因為 1-2 和 2-1 算同一條邊
                int tmp = x;
                x = y;
                y = tmp;
            }
            deg[x]++;
            deg[y]++;
            // 統計每條邊的出現次數
            // 用一個 int 存儲兩個不超過 65535 的數
            cntE.merge(x << 16 | y, 1, Integer::sum); // cntE[x<<16|y]++
        }

        var ans = new int[queries.length];
        var sortedDeg = deg.clone();
        Arrays.sort(sortedDeg); // 排序，為了雙指針
        for (int j = 0; j < queries.length; j++) {
            int q = queries[j];
            int left = 1, right = n; // 相向雙指針
            while (left < right) {
                if (sortedDeg[left] + sortedDeg[right] <= q) {
                    left++;
                } else {
                    ans[j] += right - left;
                    right--;
                }
            }
            for (var e : cntE.entrySet()) {
                int k = e.getKey(), c = e.getValue();
                int s = deg[k >> 16] + deg[k & 0xffff]; // 取出 k 的高 16 位和低 16 位
                if (s > q && s - c <= q) {
                    ans[j]--;
                }
            }
        }
        return ans;
    }


}
