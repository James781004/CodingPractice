package Grind.Ch05_Graph;

public class Q15_NumberOfConnectedComponentsInUndirectedGraph {
    // https://github.com/doocs/leetcode/blob/main/solution/0300-0399/0323.Number%20of%20Connected%20Components%20in%20an%20Undirected%20Graph/README.md
    // 並查集以及模板
    private int[] p;

    public int countComponents(int n, int[][] edges) {
        p = new int[n]; // p存儲每個點的父節點，初始化時存自己
        for (int i = 0; i < n; ++i) {
            p[i] = i;
        }
        for (int[] e : edges) {
            int a = e[0], b = e[1];
            p[find(a)] = find(b); // 合並a和b所在的兩個集合
        }
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i == find(i)) {
                ++ans;
            }
        }
        return ans;
    }

    // 返回x的祖宗節點
    private int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}
