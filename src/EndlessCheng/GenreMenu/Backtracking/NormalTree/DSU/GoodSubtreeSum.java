package EndlessCheng.GenreMenu.Backtracking.NormalTree.DSU;

import java.util.*;

public class GoodSubtreeSum {

    // https://leetcode.cn/problems/maximum-good-subtree-score/solutions/3695614/shu-xing-dp-mei-ju-zi-ji-by-endlesscheng-e2je/
    private long ans = 0;

    public int goodSubtreeSum(int[] vals, int[] par) {
        int n = par.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 1; i < n; i++) {
            g[par[i]].add(i);
        }

        dfs(0, vals, g);
        return (int) (ans % 1_000_000_007);
    }

    private record Pair(Map<Integer, Integer> f, List<int[]> single) {
    }

    private Pair dfs(int x, int[] vals, List<Integer>[] g) {
        Map<Integer, Integer> f = new HashMap<>();
        List<int[]> single = new ArrayList<>();

        // 計算 vals[x] 的數位集合 mask
        int val = vals[x];
        int mask = 0;
        for (int v = val; v > 0; v /= 10) {
            int d = v % 10;
            if ((mask >> d & 1) > 0) {
                mask = 0;
                break;
            }
            mask |= 1 << d;
        }

        if (mask > 0) {
            f.put(mask, val);
            single.add(new int[]{mask, val});
        }

        for (int y : g[x]) {
            Pair resY = dfs(y, vals, g);
            Map<Integer, Integer> fy = resY.f;
            List<int[]> singleY = resY.single;

            // 啟發式合並
            if (singleY.size() > single.size()) {
                List<int[]> tmpList = single;
                single = singleY;
                singleY = tmpList;

                Map<Integer, Integer> tmpMap = f;
                f = fy;
                fy = tmpMap;
            }

            single.addAll(singleY);

            // 把子樹 y 中的 mask 和 val 一個一個地加到 f 中
            for (int[] p : singleY) {
                int msk = p[0];
                int v = p[1];
                if (v <= f.getOrDefault(msk, 0)) {
                    continue;
                }
                Map<Integer, Integer> nf = new HashMap<>(f);
                nf.put(msk, v);
                for (Map.Entry<Integer, Integer> e2 : f.entrySet()) {
                    int msk2 = e2.getKey();
                    if ((msk & msk2) == 0) {
                        nf.merge(msk | msk2, v + e2.getValue(), Math::max);
                    }
                }
                f = nf;
            }
        }

        if (!f.isEmpty()) {
            ans += Collections.max(f.values());
        }

        return new Pair(f, single);
    }


}
