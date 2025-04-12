package EndlessCheng.GenreMenu.Graph.Other;

import java.util.ArrayList;
import java.util.List;

public class EvenDegrees {

    // https://leetcode.cn/problems/add-edges-to-make-degrees-of-all-nodes-even/solutions/2024528/fen-lei-tao-lun-by-endlesscheng-z71j/
    public boolean isPossible(int n, List<List<Integer>> edges) {
        List<Integer>[] g = new ArrayList[n + 1];
        List<Integer> oddList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            g[i] = new ArrayList<>();
        }
        for (List<Integer> edge : edges) {
            int x = edge.get(0), y = edge.get(1);
            g[x].add(y);
            g[y].add(x);
        }
        // 判斷當前點是否為奇數條邊
        for (int i = 1; i <= n; i++) {
            if (g[i].size() % 2 == 1) {
                oddList.add(i);
            }
        }
        int sz = oddList.size();
        if (sz == 0) return true;

        // 1. 題目規定至多加兩條邊，因此不可能有超過4個奇數點
        // 2. 如果要連邊就必定是偶數個奇數點
        if (sz == 1 || sz == 3 || sz > 4) return false;

        // sz == 2 的情況
        if (sz == 2) {
            int x = oddList.get(0), y = oddList.get(1);

            // 如果 x 和 y 之間沒有邊，那麼連邊之後就符合要求了
            if (!isConnected(x, y, g)) return true;

            // 如果 x 和 y 之間有邊，那麼枚舉 [1,n] 的所有不為 x 和 y 的點 i，
            // 由於 i 的度數一定是偶數，如果 i 和 x 以及 i 和 y 之間沒有邊，那麼連邊之後就符合要求了。
            for (int i = 1; i <= n; i++) {
                if (i == x || i == y) continue;
                if (!isConnected(i, x, g) && !isConnected(i, y, g)) return true;
            }
            return false;
        }

        // sz == 4 的情況
        // 如果 a 和 b 以及 c 和 d 之間沒有邊，那麼連邊之後就符合要求了。
        // 如果 a 和 c 以及 b 和 d 之間沒有邊，那麼連邊之後就符合要求了。
        // 如果 a 和 d 以及 b 和 c 之間沒有邊，那麼連邊之後就符合要求了。
        int a = oddList.get(0), b = oddList.get(1), c = oddList.get(2), d = oddList.get(3);
        if (!isConnected(a, b, g) && !isConnected(c, d, g)) return true;
        if (!isConnected(a, c, g) && !isConnected(b, d, g)) return true;
        if (!isConnected(a, d, g) && !isConnected(b, c, g)) return true;
        return false;
    }

    private boolean isConnected(int x, int y, List<Integer>[] g) {
        return g[x].contains(y);
    }

}
