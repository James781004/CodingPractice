package WeeklyContest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Week_264 {
    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2047.Number%20of%20Valid%20Words%20in%20a%20Sentence/README.md
    public int countValidWords(String sentence) {
        int ans = 0;
        for (String token : sentence.split(" ")) {
            if (check(token)) {
                ans++;
            }
        }
        return ans;
    }

    private boolean check(String token) {
        int n = token.length();
        if (n == 0) {
            return false;
        }
        boolean hyphen = false;
        for (int i = 0; i < n; i++) {
            char c = token.charAt(i);

            // 僅由小寫字母、連字符和/或標點（不含數字）組成。
            // 至多一個 標點符號。如果存在，標點符號應當位於 token 的 末尾
            if (Character.isDigit(c) || (i < n - 1 && (c == '!' || c == '.' || c == ','))) {
                return false;
            }

            // 至多一個 連字符 '-' 。如果存在，連字符兩側應當都存在小寫字母
            if (c == '-') {
                if (hyphen || i == 0 || i == n - 1 || !Character.isLetter(token.charAt(i - 1))
                        || !Character.isLetter(token.charAt(i + 1))) {
                    return false;
                }
                hyphen = true;
            }
        }
        return true;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2048.Next%20Greater%20Numerically%20Balanced%20Number/README.md
    public int nextBeautifulNumber(int n) {
        // 題目規定要找嚴格大於n的數值平衡數，所以從n+1開始
        for (int i = n + 1; i < 10000000; i++) {
            if (check(i)) {
                return i;
            }
        }
        return -1;
    }

    private boolean check(int num) {
        int[] counter = new int[10];
        char[] chars = String.valueOf(num).toCharArray();

        // 統計每個數字的出現次數
        for (char c : chars) {
            counter[c - '0']++;
        }

        // 每個數位 d ，這個數位 恰好 在 x 中出現 d 次。那麼整數 x 就是一個 數值平衡數
        for (char c : chars) {
            if (counter[c - '0'] != c - '0') {
                return false;
            }
        }
        return true;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2049.Count%20Nodes%20With%20the%20Highest%20Score/README.md
    // https://leetcode.cn/problems/count-nodes-with-the-highest-score/solution/-by-wfnuser-g8o0/
    private int n;
    private long maxScore;
    private int ans;
    private List<List<Integer>> graph;

    public int countHighestScoreNodes(int[] parents) {
        n = parents.length;
        maxScore = 0;
        ans = 0;
        graph = new ArrayList<>();

        // 將 parents 數組轉為相對好處理的鄰接矩陣
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++) {
            graph.get(parents[i]).add(i);
        }

        // 利用parents得出第i個節點子節點為children[i]
        // 比如樹：
        //     0
        //    / \
        //   2   4
        //  / \
        // 3   1
        // 注意這個數字代表的就是它是第幾個節點，它的編號
        // 以數組形式表示的話就是
        // {
        //  // 第0個節點的子結點為2和4
        //  {2, 4}.
        //  // 第1個節點沒有子結點
        //  {},
        //  // 第2個節點的子結點為3和1
        //  {3, 1},
        //  // 第3個節點沒有子結點
        //  {},
        //  // 第4個子結點沒有子節點
        //  {}
        // }
        dfs(0);
        return ans;
    }

    private int dfs(int cur) {
        int size = 1; // 記錄子節點總數量 為每個子樹的節點數量之和 加 當前節點
        long score = 1; // 記錄分數
        for (int child : graph.get(cur)) {
            int s = dfs(child); // 計算每個子樹的節點數量
            size += s;  // 累計每個子樹節點數量和
            score *= s; // 每個子樹的節點數量為得分做出了貢獻
        }

        // 判斷是否為最初根節點，否則導致size=0
        // 其貢獻為節點總數減去當前子樹下所有節點數量
        if (cur > 0) {
            score *= n - size; // 乘被刪節點父節點所在樹節點的節點個數
        }

        // 統計最大得分以及出現次數
        if (score > maxScore) {
            maxScore = score;
            ans = 1;
        } else if (score == maxScore) {
            ans++;
        }
        return size;
    }


    // https://leetcode.cn/problems/parallel-courses-iii/solution/tuo-bu-pai-xu-dong-tai-gui-hua-by-endles-dph6/
    public int minimumTime(int n, int[][] relations, int[] time) {
        // 每個節點的入度
        int[] inDegree = new int[n + 1];
        // dp[i] 為第i門課結束的最少月份，dp[i] = max{dp[j]} + time[i-1], j為i的所有前導課程,
        int[] dp = new int[n + 1];
        List<Integer>[] graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        // 初始化
        for (int[] edge : relations) {
            int u = edge[0];
            int v = edge[1];
            // 完成u才能下一門課程
            graph[u].add(v);
            inDegree[v]++;
        }

        // 保存沒有先修課的課程
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                // 入度為0，代表沒有先修課，先作為圖的入口放入queue
                queue.add(i);
                dp[i] = time[i - 1];
            }
        }

        while (!queue.isEmpty()) {
            // 當前沒有先修課的課程
            int index = queue.poll();
            // 對於當前index的所有下一門課程，修改下一門課程最少完成月份
            for (int next : graph[index]) {
                // 題目定義：time[i] 表示完成第 (i+1) 門課程需要花費的 月份 數
                // 所以這邊取dp[index] + time[next - 1] 來做比較，
                // 也可以在上面建圖時先把課程編號-1操作，這邊就不用使用 time[next - 1]
                dp[next] = Math.max(dp[next], dp[index] + time[next - 1]);
                if (--inDegree[next] == 0) {
                    // 下一門課程的入度減一，如果到達零，那麼其他節點全部遍歷完全，將該節點加入，探索是否有next課程。
                    queue.add(next);
                }
            }
        }
        int ans = 0;
        for (int i = 1; i < n + 1; i++) {
            ans = Math.max(dp[i], ans);
        }
        return ans;
    }
}

