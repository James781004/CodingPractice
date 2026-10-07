package EndlessCheng.GenreMenu.DP.Tree.Other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaysToBuildRooms {

    // https://leetcode.cn/problems/count-ways-to-build-rooms-in-an-ant-colony/solutions/846997/python-shu-zhuang-shu-zu-qiu-tuo-bu-fang-5e8f/
    static class Solution {
        private static final int mod = (int) 1e9 + 7;
        private static final int[] fac = new int[100005];
        private static final int[] inv = new int[100005];

        static {
            long f = 1;
            for (int i = 1; i <= 100000; i++) {
                f = f * i % mod;
                fac[i] = (int) f;
                inv[i] = quickPower(fac[i], mod - 2);
            }
        }

        private Map<Integer, List<Integer>> graph;

        public int waysToBuildRooms(int[] prevRoom) {
            graph = new HashMap<>();
            for (int i = 0; i < prevRoom.length; i++) {
                List<Integer> list = graph.getOrDefault(prevRoom[i], new ArrayList<>());
                list.add(i);
                graph.put(prevRoom[i], list);
            }
            return dfs(0)[1];
        }

        // 返回:元素個數,拓撲排序方案數
        private int[] dfs(int i) {
            int size = 0;
            long res = 1;
            if (graph.containsKey(i)) {
                for (Integer child : graph.get(i)) {
                    int[] cur = dfs(child);
                    size += cur[0];

                    // 因為當前的拓撲排序方案數不影響加入該子樹後的拓撲排序方案數，所以是乘法疊加
                    // 新的拓撲排序方案數為: 當前的拓撲排序方案數 * 從nodes個位置裡選nodes_個位置分配給該子樹 * 子樹的拓撲排序方案數
                    // ans = (ans * comb(nodes, nodes_) * ans_) % (10 ** 9 + 7)
                    if (size > cur[0]) {
                        res = res * fac[size] % mod;
                        res = res * inv[size - cur[0]] % mod;
                        res = res * inv[cur[0]] % mod;
                    }
                    res = res * cur[1] % mod;
                }
            }

            // 加上根節點
            return new int[]{++size, (int) res};
        }

        private static int quickPower(int x, int n) {
            long ans = 1, lx = (long) x;
            while (n > 0) {
                if ((n & 1) == 1)
                    ans = ans * lx % mod;
                lx = lx * lx % mod;
                n >>= 1;
            }
            return (int) ans;
        }
    }

}
