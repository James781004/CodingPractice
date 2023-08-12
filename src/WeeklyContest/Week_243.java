package WeeklyContest;

import java.util.PriorityQueue;

class Week_243 {
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1880.Check%20if%20Word%20Equals%20Summation%20of%20Two%20Words/README.md
    public boolean isSumEqual(String firstWord, String secondWord, String targetWord) {
        return f(firstWord) + f(secondWord) == f(targetWord);
    }

    private int f(String s) {
        int res = 0;
        for (char c : s.toCharArray()) {
            res = res * 10 + (c - 'a');
        }
        return res;
    }


    // https://leetcode.cn/problems/maximum-value-after-insertion/solutions/1166201/1881-cha-ru-hou-de-zui-da-zhi-java-8457-d2usj/
    public String maxValue(String n, int x) {
        int p = 0;
        StringBuffer ans = new StringBuffer(n);
        int X = x + '0';
        int len = n.length();
        if (n.charAt(0) == '-') {
            p++;
            // 負數插在第一個比x大的數字之前
            while (p < len) {
                if (ans.charAt(p) > X) {
                    break;
                }
                p++;
            }
        } else {
            while (p < len) {
                // 正數插在第一個比x小的數字之前
                if (ans.charAt(p) < X) {
                    break;
                }
                p++;
            }
        }
        return ans.insert(p, x).toString();
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1882.Process%20Tasks%20Using%20Servers/README.md
    public int[] assignTasks(int[] servers, int[] tasks) {
        int m = tasks.length, n = servers.length;

        // 空閒服務器 idle 依據權重、下標排序
        PriorityQueue<int[]> idle
                = new PriorityQueue<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        // 使用中的服務器 busy 依據結束時間、權重、下標排序
        PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> {
            if (a[0] == b[0]) {
                return a[1] == b[1] ? a[2] - b[2] : a[1] - b[1];
            }
            return a[0] - b[0];
        });
        for (int i = 0; i < n; ++i) {
            idle.offer(new int[]{servers[i], i});
        }
        int[] res = new int[m];
        int j = 0;
        for (int start = 0; start < m; ++start) {
            int cost = tasks[start];

            // 若有使用中的服務器小於任務開始時間，將其加入到空閒服務器隊列 idle 中
            while (!busy.isEmpty() && busy.peek()[0] <= start) {
                int[] item = busy.poll();
                idle.offer(new int[]{item[1], item[2]});
            }

            // 若當前有空閒服務器，那麼在空閒隊列 idle 中取出權重最小的服務器，將其加入使用中的隊列 busy 中
            if (!idle.isEmpty()) {
                int[] item = idle.poll();
                res[j++] = item[1];
                busy.offer(new int[]{start + cost, item[0], item[1]});
            } else {
                // 若當前沒有空閒服務器，那麼在使用隊列 busy 中找出最早結束時間且權重最小的服務器，重新加入使用中的隊列 busy 中
                int[] item = busy.poll();
                res[j++] = item[2];
                busy.offer(new int[]{item[0] + cost, item[1], item[2]});
            }
        }
        return res;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1883.Minimum%20Skips%20to%20Arrive%20at%20Meeting%20On%20Time/README.md
    public int minSkips(int[] dist, int speed, int hoursBefore) {
        int n = dist.length;

        // 定義 dp[i][j] 表示前 i 段道路，跳過了 j 次的最短路程（耗時也一樣）
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= n; ++j) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j <= i; ++j) {
                // 考慮最後一段道路 dist[i - 1] 是否跳過
                if (i != j) {
                    // 沒有跳過
                    dp[i][j] = Math.min(
                            dp[i][j], ((dp[i - 1][j] + dist[i - 1] - 1) / speed + 1) * speed);
                }
                if (j > 0) {
                    // 跳過
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + dist[i - 1]);
                }
            }
        }
        for (int i = 0; i <= n; ++i) {
            if (dp[n][i] <= hoursBefore * speed) {
                return i;
            }
        }
        return -1;
    }
}



