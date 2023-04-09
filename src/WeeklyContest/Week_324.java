package WeeklyContest;

import java.util.*;

public class Week_324 {
    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2506.Count%20Pairs%20Of%20Similar%20Strings/README.md
    // 如果兩個字符串包含相同的字母，則它們的二進制數是相同的，
    // 對於每個字符串，用哈希表統計其二進制數出現的次數，每一次累加到答案中，再將其二進制數出現的次數加1。
    public int similarPairs(String[] words) {
        int ans = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (String w : words) {
            int v = 0;
            for (int i = 0; i < w.length(); i++) {
                v |= 1 << (w.charAt(i) - 'a');
            }
            ans += cnt.getOrDefault(v, 0);
            cnt.put(v, cnt.getOrDefault(v, 0) + 1);
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2507.Smallest%20Value%20After%20Replacing%20With%20Sum%20of%20Prime%20Factors/README.md
    public int smallestValue(int n) {
        while (true) {
            int t = n, s = 0;
            for (int i = 2; i <= n / i; i++) {
                while (n % i == 0) {  // 此時i一定是質數
                    s += i;
                    n /= i;
                }
            }
            if (n > 1) s += n;
            if (s == t) return s;
            n = s;
        }
    }


    // https://leetcode.cn/problems/add-edges-to-make-degrees-of-all-nodes-even/solution/fen-lei-tao-lun-by-endlesscheng-z71j/
    public boolean isPossible(int n, List<List<Integer>> edges) {
        Set<Integer>[] g = new Set[n + 1];
        Arrays.setAll(g, k -> new HashSet<>());
        for (List<Integer> e : edges) {
            int x = e.get(0), y = e.get((1));
            g[x].add(y);
            g[y].add(x);
        }

        // 把度數為奇數的節點記到 odd 中，記 m 為 odd 的長度，分類討論
        List<Integer> odd = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (g[i].size() % 2 > 0) odd.add(i);
        }

        int m = odd.size();
        if (m == 0) return true;  // 如果  m=0，那麼已經符合要求

        // 如果 m=2，記 x=odd[0],y=odd[1]：
        // 1. 如果 x 和 y 之間沒有邊，那麼連邊之後就符合要求了。
        // 2. 如果 x 和 y 之間有邊，那麼枚舉 [1,n] 的所有不為 x 和 y 的點 i，
        //    由於 i 的度數一定是偶數，如果 i 和 x 以及 i 和 y 之間沒有邊，那麼連邊之後就符合要求了。
        if (m == 2) {
            int x = odd.get(0), y = odd.get((1));
            if (!g[x].contains(y)) return true;  //  x 和 y 之間連邊
            for (int i = 1; i <= n; i++) {  // 找到一個原本不與xy相連的i點去跟他們相連
                if (i != x && i != y && !g[i].contains(x) && !g[i].contains(y))
                    return true;
            }
            return false;
        }

        // 如果 m=4，記 a=odd[0],b=odd[1],c=odd[2],d=odd[3]：
        // 如果 a 和 b 以及 c 和 d 之間沒有邊，那麼連邊之後就符合要求了。
        // 如果 a 和 c 以及 b 和 d 之間沒有邊，那麼連邊之後就符合要求了。
        // 如果 a 和 d 以及 b 和 c 之間沒有邊，那麼連邊之後就符合要求了。
        if (m == 4) {
            int a = odd.get(0), b = odd.get(1), c = odd.get(2), d = odd.get(3);
            return !g[a].contains(b) && !g[c].contains(d) ||
                    !g[a].contains(c) && !g[b].contains(d) ||
                    !g[a].contains(d) && !g[b].contains(c);
        }
        return false;
    }


    // https://leetcode.cn/problems/cycle-length-queries-in-a-tree/solution/zui-jin-gong-gong-zu-xian-pythonjavacgo-v8ata/
    // 環可以看成是從 a 出發往上走，在某個位置「拐彎」，往下走到 b。
    // 這個拐彎的地方就是 a 和 b 的最近公共祖先。
    public int[] cycleLengthQueries(int n, int[][] queries) {
        int m = queries.length;
        int[] ans = new int[m];

        // 設 LCA 為 a 和 b 的最近公共祖先，那麼環長等於 LCA 到 a 的距離加 LCA 到 b 的距離加一。
        //
        // 如何找 LCA？
        //
        // 注意到在完全二叉樹中，深度越深的點，其編號必定大於上一層的節點編號，
        // 根據這個性質，我們可以不斷循環，每次循環比較 a 和 b 的大小：
        // 如果 a>b，則 a 的深度大於等於 b 的深度，那麼把 a 移動到其父節點，即 a=a/2；
        // 如果 a<b，則 a 的深度小於等於 b 的深度，那麼把 b 移動到其父節點，即 b=b/2；
        // 如果 a=b，則找到了 LCA，退出循環。
        for (int i = 0; i < m; i++) {
            int res = 1, a = queries[i][0], b = queries[i][1];
            while (a != b) {
                if (a > b) a /= 2;
                else b /= 2;
                ++res;
            }
            ans[i] = res;
        }
        return ans;
    }

}
