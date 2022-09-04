package LeetcodeMaster.DisjointSet;

public class Q01_DisjointSet {
//    684.冗余連接
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0684.%E5%86%97%E4%BD%99%E8%BF%9E%E6%8E%A5.md
//
//    樹可以看成是一個連通且 無環 的 無向 圖。
//
//    給定往一棵 n 個節點 (節點值 1～n) 的樹中添加一條邊後的圖。添加的邊的兩個頂點包含在 1 到 n 中間，且這條附加的邊不屬於樹中已存在的邊。圖的信息記錄於長度為 n 的二維數組 edges ，edges[i] = [ai, bi] 表示圖中在 ai 和 bi 之間存在一條邊。
//
//    請找出一條可以刪去的邊，刪除後可使得剩余部分是一個有著 n 個節點的樹。如果有多個答案，則返回數組 edges 中最後出現的邊。
//
//
//
//    提示:
//
//    n == edges.length
//3 <= n <= 1000
//    edges[i].length == 2
//            1 <= ai < bi <= edges.length
//    ai != bi
//    edges 中無重覆元素
//    給定的圖是連通的


    private int n;  // 節點數量3 到 1000
    private int[] father;

    public void init() {
        n = 1005;
        father = new int[n];

        // 並查集初始化
        for (int i = 0; i < n; ++i) {
            father[i] = i;
        }
    }

    // 並查集里尋根的過程
    private int find(int u) {
        if (u == father[u]) {
            return u;
        }
        father[u] = find(father[u]);
        return father[u];
    }

    // 將v->u 這條邊加入並查集
    private void join(int u, int v) {
        u = find(u);
        v = find(v);
        if (u == v) return;
        father[v] = u;
    }

    // 判斷 u 和 v是否找到同一個根，本題用不上
    private Boolean same(int u, int v) {
        u = find(u);
        v = find(v);
        return u == v;
    }

    public int[] findRedundantConnection(int[][] edges) {
        for (int i = 0; i < edges.length; i++) {
            if (same(edges[i][0], edges[i][1])) {
                return edges[i];
            } else {
                join(edges[i][0], edges[i][1]);
            }
        }
        return null;
    }
}
