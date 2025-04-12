package EndlessCheng.GenreMenu.Graph.Pseudotree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetMaxFunctionValue {

    // https://leetcode.cn/problems/maximize-value-of-function-in-a-ball-passing-game/solutions/2413298/shu-shang-bei-zeng-by-endlesscheng-xvsv/
    public int getMaxFunctionValue(List<Integer> receiver, int k) {
        int n = receiver.size();

        // 構建反圖 rg 和入度 degree
        List<List<Integer>> rg = new ArrayList<>(n);
        int[] degree = new int[n];
        for (int i = 0; i < n; i++) {
            rg.add(new ArrayList<>());
        }
        for (int u = 0; u < n; u++) {
            int v = receiver.get(u);
            rg.get(v).add(u);
            degree[v]++;
        }

        // 剝洋蔥法，剝離環外鏈
        List<Integer> q = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 0) {
                q.add(i);
            }
        }

        while (!q.isEmpty()) {
            List<Integer> tmp = new ArrayList<>(q);
            q.clear();
            for (int u : tmp) {
                int v = receiver.get(u);
                degree[v]--;
                if (degree[v] == 0) {
                    q.add(v);
                }
            }
        }

        // 處理基環
        List<List<Integer>> circles = new ArrayList<>(); // 各基環
        List<Integer> clen = new ArrayList<>();          // 每個環的長度
        int[] cycle = new int[n];                        // 節點 i 屬於哪個環
        int[] pos = new int[n];                          // 節點 i 在環上的位置
        Arrays.fill(cycle, -1);
        Arrays.fill(pos, -1);

        for (int i = 0; i < n; i++) {
            if (degree[i] > 0 && cycle[i] == -1) {
                List<Integer> row = new ArrayList<>();
                row.add(0); // 初始值 0，方便前綴和計算
                int cycleId = circles.size();
                int cur = i;
                while (true) {
                    row.add(cur);
                    cycle[cur] = cycleId;
                    pos[cur] = row.size() - 1;
                    cur = receiver.get(cur);
                    if (cur == i) {
                        break;
                    }
                }
                clen.add(row.size() - 1);

                // 環上前綴和，展開兩倍
                List<Integer> extendedRow = new ArrayList<>(row.subList(1, row.size()));
                row.addAll(extendedRow);
                for (int j = 1; j < row.size(); j++) {
                    row.set(j, row.get(j) + row.get(j - 1));
                }
                circles.add(row);
            }
        }

        // DFS 遍歷每一條路徑
        long[] res = {0}; // 使用陣列模擬 nonlocal
        List<Integer> path = new ArrayList<>();
        path.add(0); // 哨兵點

        // DFS 函數
        class DFSHelper {
            void dfs(int root, int cur) {
                List<Integer> row = circles.get(cycle[root]);
                int cl = clen.get(cycle[root]);
                int outCycle = Math.min(path.size() - 1, k + 1); // 環外部分
                int inCycle = k + 1 - outCycle;

                // 計算當前路徑和
                long ansOut = path.get(path.size() - 1) - path.get(path.size() - 1 - outCycle); // 環外部分
                long ansIn1 = (long) (inCycle / cl) * row.get(cl);                               // 環內完整部分
                long ansIn2 = row.get(pos[root] + inCycle % cl - 1) - row.get(pos[root] - 1);   // 環內餘數部分
                res[0] = Math.max(res[0], ansOut + ansIn1 + ansIn2);

                // 沿反圖 rg 繼續 DFS
                for (int v : rg.get(cur)) {
                    if (degree[v] == 0) {
                        path.add(path.get(path.size() - 1) + v);
                        dfs(root, v);
                        path.remove(path.size() - 1);
                    }
                }
            }
        }

        DFSHelper helper = new DFSHelper();
        for (int i = 0; i < n; i++) {
            if (degree[i] > 0) {
                helper.dfs(i, i);
            }
        }

        return (int) res[0];
    }


    // 樹上倍增模板
//    public long getMaxFunctionValue(List<Integer> receiver, long K) {
//        int n = receiver.size();
//        int m = 64 - Long.numberOfLeadingZeros(K); // K 的二進制長度
//        var pa = new int[m][n];
//        var sum = new long[m][n];
//        for (int i = 0; i < n; i++) {
//            pa[0][i] = receiver.get(i);
//            sum[0][i] = receiver.get(i);
//        }
//        for (int i = 0; i < m - 1; i++) {
//            for (int x = 0; x < n; x++) {
//                int p = pa[i][x];
//                pa[i + 1][x] = pa[i][p];
//                sum[i + 1][x] = sum[i][x] + sum[i][p]; // 合並節點值之和
//            }
//        }
//
//        long ans = 0;
//        for (int i = 0; i < n; i++) {
//            long s = i;
//            int x = i;
//            for (long k = K; k > 0; k &= k - 1) {
//                int ctz = Long.numberOfTrailingZeros(k);
//                s += sum[ctz][x];
//                x = pa[ctz][x];
//            }
//            ans = Math.max(ans, s);
//        }
//        return ans;
//    }


}
