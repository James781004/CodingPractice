package WeeklyContest;

import java.util.*;

public class Week_274 {
    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2124.Check%20if%20All%20A%27s%20Appears%20Before%20All%20B%27s/README.md
    public boolean checkString(String s) {
        return !s.contains("ba");
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2125.Number%20of%20Laser%20Beams%20in%20a%20Bank/README.md
    public int numberOfBeams(String[] bank) {
        int last = 0; // 上一個不全為 0 的行的安全設備個數
        int ans = 0;
        for (String b : bank) {
            int t = 0;
            for (char c : b.toCharArray()) {
                if (c == '1') {
                    ++t; // 統計每一行的安全設備個數
                }
            }
            if (t > 0) {
                ans += last * t; // 累加激光束的個數
                last = t; // 滾動換行
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2126.Destroying%20Asteroids/README.md
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        Arrays.sort(asteroids);
        long m = mass;
        for (int v : asteroids) {
            if (m < v) {
                return false;
            }
            m += v;
        }
        return true;
    }


    // https://leetcode.cn/problems/maximum-employees-to-be-invited-to-a-meeting/solution/nei-xiang-ji-huan-shu-tuo-bu-pai-xu-fen-c1i1b/
    //5970. 參加會議的最多員工數
    //內向基環樹
    public int maximumInvitations(int[] favorite) {
        // 構建拓撲圖的入度表 和 反向圖，用於找基環大小為2的鏈
        int n = favorite.length;
        int[] inDeg = new int[n];
        List<Integer>[] reverseEdge = new List[n];
        for (int i = 0; i < n; i++) {
            reverseEdge[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            int to = favorite[i];
            inDeg[to]++;
            reverseEdge[to].add(i);
        }

        // 拓撲排序：基環和樹枝分離
        boolean[] vis = new boolean[n];
        Deque<Integer> que = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (inDeg[i] == 0) {
                vis[i] = true;
                que.add(i);
            }
        }
        while (!que.isEmpty()) {
            int now = que.poll();
            int to = favorite[now];
            if (--inDeg[to] == 0) {
                que.add(to);
                vis[to] = true;
            }
        }

        // 內向基環森林裡面找環
        int maxRingSize = 0, listSize = 0;
        for (int i = 0; i < n; i++) {
            if (inDeg[i] != 0 && !vis[i]) {
                vis[i] = true;
                int start = i;
                int size = 1;
                //找環
                while (!vis[favorite[start]]) {
                    start = favorite[start];
                    vis[start] = true;
                    size++;
                }
                // 大小為2的基環，左右兩邊沿著鏈條延伸
                if (size == 2) {
                    int u = i;
                    int v = favorite[i];
                    int uList = dfsGetMaxListNum(reverseEdge, u, v);
                    int vList = dfsGetMaxListNum(reverseEdge, v, u);
                    // 基環為2的一條長鏈可以相互連接，也能達到喜歡的人挨著坐的效果
                    listSize = listSize + uList + vList;
                } else {
                    maxRingSize = Math.max(maxRingSize, size);
                }
            }
        }
        return Math.max(maxRingSize, listSize);
    }

    // 獲取基環大小為2的左右兩側最長鏈路，假設1 <-> 2，還有3->4->1 以及 5->1，需要找到最長的一條
    public int dfsGetMaxListNum(List<Integer>[] reverseEdge, int parent, int now) {
        int size = 1;
        int maxDeep = 0;
        for (int i = 0; i < reverseEdge[now].size(); i++) {
            if (reverseEdge[now].get(i) != parent) {
                maxDeep = Math.max(maxDeep, dfsGetMaxListNum(reverseEdge, now, reverseEdge[now].get(i)));
            }
        }
        return size + maxDeep;
    }


    // 拓撲排序的同時計算出最長鏈的長度，
    // 這樣就不需要建反圖和在反圖上找最長鏈
    public int maximumInvitations2(int[] g) { // favorite 就是內向基環森林 g
        int n = g.length;
        int[] deg = new int[n]; // g 上每個節點的入度
        for (int w : g) ++deg[w];

        int[] maxDepth = new int[n];
        Deque<Integer> q = new ArrayDeque<Integer>();
        for (int i = 0; i < n; ++i)
            if (deg[i] == 0) q.push(i);
        while (!q.isEmpty()) {  // 拓撲排序，剪掉 g 上的所有樹枝
            int v = q.pop();
            ++maxDepth[v];
            int w = g[v]; // v 只有一條出邊
            maxDepth[w] = Math.max(maxDepth[w], maxDepth[v]);
            if (--deg[w] == 0) q.push(w);
        }

        int maxRingSize = 0, sumChainSize = 0;
        for (int i = 0; i < n; ++i) {
            if (deg[i] <= 0) continue;
            // 遍歷基環上的點（拓撲排序後入度大於 0）
            deg[i] = -1;
            int ringSize = 1;
            for (int v = g[i]; v != i; v = g[v]) {
                deg[v] = -1; // 將基環上的點的入度標記為 -1，避免重復訪問
                ++ringSize;
            }
            if (ringSize == 2) sumChainSize += maxDepth[i] + maxDepth[g[i]] + 2; // 基環大小為 2，累加兩條最長鏈的長度
            else maxRingSize = Math.max(maxRingSize, ringSize); // 取所有基環的最大值
        }
        return Math.max(maxRingSize, sumChainSize);
    }
}
