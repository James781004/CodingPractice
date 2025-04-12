package EndlessCheng.GenreMenu.Graph.Floyd;

import java.util.BitSet;

public class MaximumDetonation {

    // https://leetcode.cn/problems/detonate-the-maximum-bombs/solutions/1152450/jian-tu-bao-li-mei-ju-suo-you-qi-dian-by-h4mj/
    public int maximumDetonation(int[][] bombs) {
        int n = bombs.length;
        BitSet[] f = new BitSet[n]; // f[i] 表示 i 可以到達的節點集合。注意 i 一定在 f[i] 中
        for (int i = 0; i < n; i++) {
            long x = bombs[i][0];
            long y = bombs[i][1];
            long r = bombs[i][2];
            f[i] = new BitSet(n);
            for (int j = 0; j < n; j++) {
                long dx = x - bombs[j][0];
                long dy = y - bombs[j][1];
                if (dx * dx + dy * dy <= r * r) {
                    f[i].set(j); // i 可以到達 j
                }
            }
        }

        // 如果 i 可以到達 k，那麼 k 能到達的點，i 也可以到達，
        // 於是 i 能到達的點就是 f[i] 和 f[k] 的並集
        // 即 f[i]=f[i]∪f[k]
        for (int k = 0; k < n; k++) {
            for (BitSet fi : f) {
                if (fi.get(k)) { // i 可以到達 k
                    fi.or(f[k]); // i 也可以到 k 可以到達的點
                }
            }
        }

        int ans = 0;
        for (BitSet s : f) {
            ans = Math.max(ans, s.cardinality()); // 集合大小的最大值
        }
        return ans;
    }

}
