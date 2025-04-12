package EndlessCheng.GenreMenu.Graph.MinimumSpanningTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindCriticalAndPseudoCriticalEdges {

    // https://leetcode.cn/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/solutions/569268/java-guan-jian-bian-he-wei-guan-jian-bia-1as1/
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        // 並查集集合初始化
        List<Integer> p = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            p.add(i);
        }
        // 將所有邊添加到列表當中
        List<Edge> nes = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) {
            nes.add(new Edge(i, edges[i][0], edges[i][1], edges[i][2]));
        }

        // 第一步，計算最小生成樹的權重
        int minTreeWeight = minTreeWeightCal(new ArrayList<>(p), nes, 0);

        // 初始化答案列表
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        res.add(new ArrayList<>());
        // 復制一個臨時邊集，用於刪邊操作
        List<Edge> tempEdges = new ArrayList<>(nes);

        // 第二步，先枚舉所有邊，查看這條邊是不是關鍵邊
        for (Edge e : nes) {
            // 如果從邊集中刪除了這條邊，最小生成樹的權重發生了變化，說明這是關鍵邊
            tempEdges.remove(e);
            if (minTreeWeight != minTreeWeightCal(new ArrayList<>(p), tempEdges, 0)) {
                res.get(0).add(e.id);
            }
            // 將刪去邊放回去
            tempEdges.add(e);

        }
        // 第三步，再判斷每條邊是不是偽關鍵邊
        for (Edge e : nes) {
            // 如果當前邊是關鍵邊，那就肯定不是偽關鍵邊
            if (res.get(0).contains(e.id)) continue;

            List<Integer> tp = new ArrayList(p);
            // 提前連上這條邊的兩個端點
            tp.set(e.a, e.b);
            // 判斷權重是否和之前相同
            if (minTreeWeight == minTreeWeightCal(tp, tempEdges, e.weight)) {
                // 連上了這條邊，最小生成樹的權重還是一樣的，說明這是偽關鍵邊
                res.get(1).add(e.id);
            }
        }

        return res;

    }

    // Kruskal算法求最小生成樹，參數分別表示點的並查集，邊列表，初始權重
    private int minTreeWeightCal(List<Integer> p, List<Edge> nes, int w) {
        // 對邊從小到大進行排序
        Collections.sort(nes, (a, b) -> a.weight - b.weight);
        for (Edge e : nes) {
            int a = find(p, e.a), b = find(p, e.b);

            if (a != b) {
                w += e.weight;
                p.set(a, b);
            }
        }

        return w;
    }

    // 並查集模板方法，求當前元素所在集合的根元素
    private int find(List<Integer> p, int x) {
        if (p.get(x) != x) {
            p.set(x, find(p, p.get(x)));
        }

        return p.get(x);
    }

    // 定義邊
    class Edge {
        // 分別表示當前邊的序號、起點、終點、權重
        public int id, a, b, weight;

        public Edge(int i, int _a, int _b, int w) {
            id = i;
            a = _a;
            b = _b;
            weight = w;
        }
    }

}
