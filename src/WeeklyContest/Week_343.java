package WeeklyContest;

import java.util.*;

public class Week_343 {
    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2660.Determine%20the%20Winner%20of%20a%20Bowling%20Game/README.md
    public int isWinner(int[] player1, int[] player2) {
        int a = f(player1), b = f(player2);
        return a > b ? 1 : b > a ? 2 : 0;
    }

    private int f(int[] arr) {
        int s = 0;
        for (int i = 0; i < arr.length; i++) {
            int k = (i > 0 && arr[i - 1] == 10) || (i > 1 && arr[i - 2] == 10) ? 2 : 1;
            s += k * arr[i];
        }
        return s;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2661.First%20Completely%20Painted%20Row%20or%20Column/README.md
    public int firstCompleteIndex(int[] arr, int[][] mat) {
        int m = mat.length, n = mat[0].length;
        Map<Integer, int[]> idx = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                idx.put(mat[i][j], new int[]{i, j});  // 記錄每個元素在矩陣 mat 中的位置
            }
        }

        // 記錄每行和每列已經涂色的元素個數
        int[] row = new int[m];
        int[] col = new int[n];

        // 1. 遍歷數組 arr ，找到其在矩陣 mat 中的位置 (i, j)
        for (int k = 0; ; k++) {
            int[] x = idx.get(arr[k]);
            int i = x[0], j = x[1];

            // 2. 然後將 row[i] 和 col[j] 分別加一，
            row[i]++;
            col[j]++;

            // 3. 如果 row[i] == n 或 col[j] == m，
            // 說明第 i 行或第 j 列已經被涂色，
            // 那麼 arr[k] 就是我們要找的元素，返回 k 即可
            if (row[i] == n || col[j] == m) {
                return k;
            }
        }
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2662.Minimum%20Cost%20of%20a%20Path%20With%20Special%20Roads/README.md
    // 對於訪問到的每個坐標點(x, y)，假設從起點到(x, y)的最小代價為 d
    // 如果選擇直接移動到(targetX, targetY) 總代價就是 d + |x - targetX| + |y - targetY|
    // 如果選擇經過某條特殊路徑(x1, y1) -> (x2, y2)，需要可以花費 |x - x1| + |y - y1| + cost 代價
    // 使用 Dijkstra 算法求出從起點到所有點的最小代價，然後從中選擇最小的那個
    public int minimumCost(int[] start, int[] target, int[][] specialRoads) {
        int ans = 1 << 30;
        int n = 1000000;  // 離散化

        // 定義一個優先隊列 q ，隊列中的每一個元素是一個三元組 (d,x,y)
        // 表示從起點到 (x,y) 的最小代價為 d
        // 初始時，將 (0, startX, startY) 加入隊列
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        Set<Long> vis = new HashSet<>();
        q.offer(new int[]{0, start[0], start[1]});
        while (!q.isEmpty()) {
            int[] p = q.poll();
            int x = p[1], y = p[2];
            long k = 1L * x * n + y;
            if (vis.contains(k)) {
                continue;
            }
            vis.add(k);
            int d = p[0];
            ans = Math.min(ans, d + dist(x, y, target[0], target[1]));
            for (int[] r : specialRoads) {
                int x1 = r[0], y1 = r[1], x2 = r[2], y2 = r[3], cost = r[4];
                q.offer(new int[]{d + dist(x, y, x1, y1) + cost, x2, y2});
            }
        }
        return ans;
    }

    private int dist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }


    // https://leetcode.cn/problems/lexicographically-smallest-beautiful-string/solution/tan-xin-pythonjavacgo-by-endlesscheng-yix5/
    public String smallestBeautifulString(String S, int k) {
        k += 'a';
        char[] s = S.toCharArray();
        int n = S.length(), i = n - 1;
        s[i]++; // 從最後一個字母開始，先加上1
        while (i < n) {
            if (s[i] == k) { // 超過范圍
                if (i == 0) return ""; // 無法進位
                // 進位: 當前位置重設為a，然後把前一位+1
                s[i] = 'a';
                ++s[--i];
            } else if (i > 0 && s[i] == s[i - 1] || i > 1 && s[i] == s[i - 2]) {
                ++s[i]; // 如果 s[i] 和前面的字符形成回文串(只需要看長為2或3的回文串)，就繼續增加 s[i]
            } else {
                ++i; // 檢查 s[i] 是否和後面的字符形成回文串
            }
        }
        return new String(s);
    }
}
