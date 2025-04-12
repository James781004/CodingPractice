package EndlessCheng.GenreMenu.Graph.Other;

public class MaxNumEdgesToRemove {

    // https://leetcode.cn/problems/remove-max-number-of-edges-to-keep-graph-fully-traversable/solutions/577937/javabing-cha-ji-zhong-dian-you-xian-xuan-tibe/
    public int maxNumEdgesToRemove(int n, int[][] edges) {
        // 基本思路：··並查集 + 優先選擇··
        // 即分別對A 和 B進行並查集操作，並且優先選擇··種類3··即··雙向邊··，再考慮單向邊
        int result = 0;
        // a b 分別為Alice和Bob獨享的並查集
        Union a = new Union(n);
        Union b = new Union(n);
        // 優先考慮雙向邊
        for (int[] edge : edges) {
            if (edge[0] == 3) {
                // edge - 1是因為數組中的表示是以節點來表示的，需要減一
                boolean okA = a.union(edge[1] - 1, edge[2] - 1);
                boolean okB = b.union(edge[1] - 1, edge[2] - 1);

                // 如果A B都已經相連過這條邊，則刪除
                if (okA && okB) {
                    result++;
                }
            }
        }
        // 接下來考慮單向邊
        for (int[] edge : edges) {
            if (edge[0] == 1) {
                //如果已經連接，則刪除
                if (a.union(edge[1] - 1, edge[2] - 1)) {
                    result++;
                }
            } else if (edge[0] == 2) {
                if (b.union(edge[1] - 1, edge[2] - 1)) {
                    result++;
                }
            }
        }
        // 如果最終可行，則返回刪除的個數，如果不行則返回-1
        return a.check() && b.check() ? result : -1;
    }

    // 並查集模板
    class Union {
        private int[] parent;
        private int n;

        Union(int n) {
            this.n = n;
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        private int find(int id) {
            return parent[id] == id ? id : (parent[id] = find(parent[id]));
        }

        // 如果已經連接則返回true
        public boolean union(int id1, int id2) {
            int root1 = find(id1);
            int root2 = find(id2);
            if (root1 == root2) {
                return true;
            }
            parent[root1] = root2;
            return false;
        }

        // 如果可連通，那麼只有一個根節點
        public boolean check() {
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (parent[i] == i) {
                    count++;
                }
            }
            return count == 1;
        }
    }


}
