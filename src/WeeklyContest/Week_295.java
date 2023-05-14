package WeeklyContest;

import java.util.*;

public class Week_295 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2287.Rearrange%20Characters%20to%20Make%20Target%20String/README.md
    // 統計字符串 s 和 target 中每個字符出現的次數，記為 cnt1 和 cnt2。
    // 對於 target 中的每個字符，計算 cnt1 中該字符出現的次數除以 cnt2 中該字符出現的次數，取最小值即可。
    public int rearrangeCharacters(String s, String target) {
        int[] cnt1 = new int[26];
        int[] cnt2 = new int[26];
        for (int i = 0; i < s.length(); i++) {
            ++cnt1[s.charAt(i) - 'a'];
        }
        for (int i = 0; i < target.length(); i++) {
            ++cnt2[target.charAt(i) - 'a'];
        }
        int ans = 100;
        for (int i = 0; i < 26; i++) {
            if (cnt2[i] > 0) {
                ans = Math.min(ans, cnt1[i] / cnt2[i]);
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2288.Apply%20Discount%20to%20Prices/README.md
    // 將句子按空格分割成單詞數組，然後遍歷單詞數組，
    // 對於每個單詞，如果其表示價格，則將其更新為減免折扣後的價格。
    // 最後將更新後的單詞數組拼接成以空格分隔的字符串即可。
    public String discountPrices(String sentence, int discount) {
        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (check(words[i])) {
                double t = Long.parseLong(words[i].substring(1)) * (1 - discount / 100.0);
                words[i] = String.format("$%.2f", t);
            }
        }
        return String.join(" ", words);
    }

    private boolean check(String s) {
        if (s.charAt(0) != '$' || s.length() == 1) {
            return false;
        }
        for (int i = 1; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }


    // https://leetcode.cn/problems/steps-to-make-array-non-decreasing/solution/by-endlesscheng-s2yc/
    // left大刪除right小
    // 5...1 f(5,1)=max(f(...),1)
    // 5 1234 1 --> 1234 -->4
    // 5,1,2,3,4,4,7,3
    // [5,0]--> [5,0][1,1]-->[5,0][2,2]-->...-->[5,0][4,5]-->[7,0][3,1]
    public int totalSteps(int[] nums) {
        int ans = 0;
        Deque<int[]> st = new ArrayDeque<>();  // 用一個單調遞減棧存儲元素及其被刪除的時刻
        for (int num : nums) { // 枚舉每一個元素
            int maxT = 0;
            while (!st.isEmpty() && st.peek()[0] <= num) { // 維持遞減棧，開始排出棧頂
                // 非空且前一個數小於現在的數-->前一個數退出棧-->直到棧空/找到比它大的數
                // 如果棧頂t(st.pop()[1])比當前maxT大，表示現在找到一個遞增中的數列
                maxT = Math.max(maxT, st.pop()[1]);
            }
            maxT = st.isEmpty() ? 0 : maxT + 1;
            // 棧空表示目前是整體遞增數列，不需要移除
            // 非空就表示要繼續進行刪除操作，maxT+1
            ans = Math.max(ans, maxT);
            st.push(new int[]{num, maxT}); // 把新的數放入棧中
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-obstacle-removal-to-reach-corner/solution/0-1-bfs-by-endlesscheng-4pjt/
    public int minimumObstacles(int[][] grid) {
         /*
        01BFS解法：
        本題相當於求從grid[0,0]到grid[m-1][n-1]的最短路徑(最低成本)，其中經過障礙物成本為1，空地成本為0
        用常規的堆優化Dijkstra解法時間復雜度為O(m*n*log(m*n))
        利用01BFS可以將時間復雜度降為O(m*n)，也就是說每個節點只入隊1次，不會重復訪問且可以在O(1)內找到最短距離的節點
        過程為：用一個雙端隊列維護入隊元素，然後如果下一個格子為0可以進入隊頭，否則進入隊尾
            同時利用一個dis二維數組維護起點蔓延到該點的最短距離，最後返回dis[m-1][n-1]就是所求
        時間復雜度:O(m*n) 空間復雜度:O(m*n)
         */
        int m = grid.length, n = grid[0].length;
        int INF = 0x3f3f3f3f;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int[][] dis = new int[m][n];    // dis[i][j]維護起點蔓延到grid[i][j]時的最短距離
        for (int i = 0; i < m; i++) {
            Arrays.fill(dis[i], INF);
        }
        dis[0][0] = 0;  // 起點到起點的距離為0
        Deque<int[]> que = new LinkedList<>();
        que.add(new int[]{0, 0});
        while (!que.isEmpty()) {
            int[] p = que.poll();
            int x = p[0], y = p[1];
            for (int[] d : dirs) {
                int nx = x + d[0], ny = y + d[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    int g = grid[nx][ny];
                    // 沒有被訪問過就可以標記蔓延距離
                    if (dis[nx][ny] == INF) {
                        dis[nx][ny] = dis[x][y] + g;
                        // 看當前格子為0就入隊頭，否則入隊尾
                        if (g == 0) {
                            que.addFirst(new int[]{nx, ny});
                        } else {
                            que.addLast(new int[]{nx, ny});
                        }
                    }
                }
            }
        }
        return dis[m - 1][n - 1];
    }


    // PriorityQueue輔助解題
    // https://leetcode.cn/problems/minimum-obstacle-removal-to-reach-corner/solution/java-by-moonstruck-24zh/
    int[][] grids = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int minimumObstacles2(int[][] grid) {
        int M = grid.length, N = grid[0].length;
        int[][] dp = new int[M][N];
        boolean[][] vis = new boolean[M][N];
        // 按照隊列中的坐標，在dp中的升序排列，這樣計算的一定是最小的值
        Queue<int[]> queue = new PriorityQueue<int[]>((a, b) -> dp[a[0]][a[1]] - dp[b[0]][b[1]]);
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int x = poll[0], y = poll[1];
            for (int[] ints : grids) {
                int nx = x + ints[0], ny = y + ints[1];
                if (nx < 0 || ny < 0 || nx >= M || ny >= N || vis[nx][ny]) continue;
                vis[nx][ny] = true;
                //當前點的值等於上一個過來點的值 + 當前點的值
                dp[nx][ny] = dp[x][y] + grid[nx][ny];
                queue.offer(new int[]{nx, ny});
            }
        }
        return dp[M - 1][N - 1];
    }
}
