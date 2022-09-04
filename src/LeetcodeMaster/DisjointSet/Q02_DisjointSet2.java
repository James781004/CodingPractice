package LeetcodeMaster.DisjointSet;

import java.util.ArrayList;

public class Q02_DisjointSet2 {
//    685.冗余連接II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0685.%E5%86%97%E4%BD%99%E8%BF%9E%E6%8E%A5II.md
//
//    在本問題中，有根樹指滿足以下條件的 有向 圖。該樹只有一個根節點，所有其他節點都是該根節點的後繼。該樹除了根節點之外的每一個節點都有且只有一個父節點，而根節點沒有父節點。
//
//    輸入一個有向圖，該圖由一個有著 n 個節點（節點值不重覆，從 1 到 n）的樹及一條附加的有向邊構成。附加的邊包含在 1 到 n 中的兩個不同頂點間，這條附加的邊不屬於樹中已存在的邊。
//
//    結果圖是一個以邊組成的二維數組 edges 。 每個元素是一對 [ui, vi]，用以表示 有向 圖中連接頂點 ui 和頂點 vi 的邊，其中 ui 是 vi 的一個父節點。
//
//    返回一條能刪除的邊，使得剩下的圖是有 n 個節點的有根樹。若有多個答案，返回最後出現在給定二維數組的答案。
//
//
//
//
//
//    提示：
//
//    n == edges.length
//3 <= n <= 1000
//    edges[i].length == 2
//            1 <= ui, vi <= n


    private int n;  // 節點數量3 到 1000
    private int[] father;

    public void init() {
        n = 1005;
        father = new int[n];

        // 並查集初始化
        initFather();
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

    // 判斷 u 和 v是否找到同一個根
    private Boolean same(int u, int v) {
        u = find(u);
        v = find(v);
        return u == v;
    }

    /**
     * 初始化並查集
     */
    private void initFather() {
        // 並查集初始化
        for (int i = 0; i < n; ++i) {
            father[i] = i;
        }
    }

    /**
     * 在有向圖里找到刪除的那條邊，使其變成樹
     *
     * @param edges
     * @return 要刪除的邊
     */
    private int[] getRemoveEdge(int[][] edges) {
        initFather();
        for (int i = 0; i < edges.length; i++) {
            if (same(edges[i][0], edges[i][1])) { // 構成有向環了，就是要刪除的邊
                return edges[i];
            }
            join(edges[i][0], edges[i][1]);
        }
        return null;
    }

    /**
     * 刪一條邊之後判斷是不是樹
     *
     * @param edges
     * @param deleteEdge 要刪除的邊
     * @return true: 是樹， false： 不是樹
     */
    private Boolean isTreeAfterRemoveEdge(int[][] edges, int deleteEdge) {
        initFather();
        for (int i = 0; i < edges.length; i++) {
            if (i == deleteEdge) continue;
            if (same(edges[i][0], edges[i][1])) { // 構成有向環了，一定不是樹
                return false;
            }
            join(edges[i][0], edges[i][1]);
        }
        return true;
    }


    public int[] findRedundantDirectedConnection(int[][] edges) {
        int[] inDegree = new int[n];
        for (int i = 0; i < edges.length; i++) {
            // 入度
            inDegree[edges[i][1]] += 1;
        }

        // 找入度為2的節點所對應的邊，注意要倒序，因為優先返回最後出現在二維數組中的答案
        ArrayList<Integer> twoDegree = new ArrayList<Integer>();

        for (int i = edges.length - 1; i >= 0; i--) {
            if (inDegree[edges[i][1]] == 2) {
                twoDegree.add(i);
            }
        }

        // 處理圖中情況1 和 情況2
        // 如果有入度為2的節點，那麽一定是兩條邊里刪一個，看刪哪個可以構成樹
        if (!twoDegree.isEmpty()) {
            if (isTreeAfterRemoveEdge(edges, twoDegree.get(0))) {
                return edges[twoDegree.get(0)];
            }
            return edges[twoDegree.get(1)];
        }

        // 明確沒有入度為2的情況，那麽一定有有向環，找到構成環的邊返回就可以了
        return getRemoveEdge(edges);
    }
}
