package EndlessCheng.GenreMenu.Graph.Tarjan;

import java.util.*;

public class MinimumCost {

    // https://leetcode.cn/problems/s5kipK/solution/by-tsreaper-z8by/
    private static final int S = 0;
    private Map<Integer, List<Integer>> adj;
    private boolean[] isCut;
    private int[] dfn;
    private int[] low;
    private int clk;
    private Deque<Integer> stk;
    private LinkedList<List<Integer>> dcc;


    public long minimumCost(int[] cost, int[][] roads) {
        int n = cost.length;
        if (n == 1) {
            return cost[0];
        }

        adj = new HashMap<>();
        for (int[] road : roads) {
            adj.computeIfAbsent(road[0], key -> new ArrayList<>()).add(road[1]);
            adj.computeIfAbsent(road[1], key -> new ArrayList<>()).add(road[0]);
        }
        isCut = new boolean[n];
        dfn = new int[n];
        low = new int[n];
        clk = 0;
        stk = new ArrayDeque<>();
        dcc = new LinkedList<>();
        tarjan(S);

        // 整張圖是一個雙連通分量，選擇整張圖權值最小的點即可
        if (dcc.size() == 1) {
            return Arrays.stream(cost).min().orElseThrow();
        }

        List<Integer> vec = new ArrayList<>();
        for (List<Integer> c : dcc) {
            int cnt = 0;
            int min = Integer.MAX_VALUE;
            for (int x : c) {
                if (isCut[x]) {
                    cnt++;
                } else {
                    min = Math.min(min, cost[x]);
                }
            }
            // 該雙連通分量只和一個割點相連，是縮點形成的樹的葉子節點
            if (cnt == 1) {
                vec.add(min);
            }
        }

        Collections.sort(vec);
        long res = 0;
        for (int i = 0; i < vec.size() - 1; i++) {
            res += vec.get(i);
        }
        return res;
    }

    private void tarjan(int sn) {
        dfn[sn] = low[sn] = ++clk;
        stk.push(sn);
        int flag = 0;
        for (int fn : adj.getOrDefault(sn, new ArrayList<>())) {
            if (dfn[fn] == 0) {
                tarjan(fn);
                low[sn] = Math.min(low[sn], low[fn]);
                if (low[fn] >= dfn[sn]) {
                    flag++;
                    if (sn != S || flag > 1) {
                        isCut[sn] = true;
                    }

                    int t;
                    dcc.add(new ArrayList<>());
                    do {
                        t = stk.pop();
                        dcc.getLast().add(t);
                    } while (t != fn);
                    dcc.getLast().add(sn);
                }
            } else {
                low[sn] = Math.min(low[sn], dfn[fn]);
            }
        }
    }


}
