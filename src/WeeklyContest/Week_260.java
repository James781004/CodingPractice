package WeeklyContest;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

class Week_260 {
    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2016.Maximum%20Difference%20Between%20Increasing%20Elements/README.md
    public int maximumDifference(int[] nums) {
        int mi = 1 << 30;
        int ans = -1;
        for (int x : nums) {
            if (x > mi) {
                ans = Math.max(ans, x - mi);
            } else {
                mi = x; // 維護當前最小值
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/grid-game/solution/javaqian-zhui-he-by-merickbao-2-nq78/
    public long gridGame(int[][] grid) {
        int n = grid[0].length;
        long[][] p = new long[2][n + 1];
        for (int i = 1; i <= n; i++) {
            p[0][i] = p[0][i - 1] + grid[0][i - 1]; // p[0][i]表示在下標 i 向下拐右邊剩余留給第二個機器人的點數總和 （前綴和）
            p[1][i] = p[1][i - 1] + grid[1][i - 1]; // p[1][i]表示在下標 i 向下拐左邊剩余留給第二個機器人的點數總和
        }
        long ans = Long.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            // 第二個機器人選擇兩行中的最大值
            // 不必考慮第二個機器人路徑和，因為第一個機器人已經把最佳路徑歸零，把剩餘沒歸零的格子加總即可
            // p[0][n] - p[0][i] 表示第一行從第一個機器人拐彎點後一格走到底
            // p[1][i - 1] 表示第二行從頭走到第一個機器人拐彎點前一格
            ans = Math.min(ans, Math.max(p[0][n] - p[0][i], p[1][i - 1]));
        }
        return ans;
    }


    // https://leetcode.cn/problems/check-if-word-can-be-placed-in-crossword/solution/mei-ju-liang-ge-zhi-jian-de-zi-fu-by-end-pjq1/
    public boolean placeWordInCrossword(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        int k = word.length();
        // 從上到下遍歷每一行 從左到右放
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == '#') { // 遇到障礙直接跳過
                    continue;
                }

                // 遍歷並匹配兩個 # 之間的字符
                int j0 = j;
                // 正序放
                for (j = j0; j < n; j++) {
                    if (board[i][j] != ' ' && j - j0 < k && board[i][j] != word.charAt(j - j0) || j - j0 >= k) {
                        break; // 終止條件：當前格子不是空格，並且無法匹配word相對位置的字符，或者長度已經到達 k
                    }
                }

                // 正序放下去了
                // 正序檢驗：[j0...j]長度已經到達 k，並且兩端為 board 頭尾或者是障礙(#)
                // 注意正序的情況 j0 為開頭，j 為結尾
                if (j - j0 == k && (j < n && board[i][j] == '#' || j == n) && (j0 == 0 || board[i][j0 - 1] == '#')) {
                    return true;
                }

                // 倒序放
                for (j = j0; j < n; j++) {
                    if (board[i][j] != ' ' && j - j0 < k && board[i][j] != word.charAt(k - j + j0 - 1) || j - j0 >= k) {
                        break; // 終止條件：當前格子不是空格，並且無法匹配word相對位置的字符，或者長度已經到達 k
                    }
                }

                // 倒序放下去了
                // 倒序檢驗：[j...j0]長度已經到達 k，並且兩端為 board 頭尾或者是障礙(#)
                // 注意倒序的情況 j 為開頭，j0 為結尾
                if (j - j0 == k && (j < n && board[i][j] == '#' || j == n) && (j0 == 0 || board[i][j0 - 1] == '#')) {
                    return true;
                }
            }
        }

        // 遍歷列（同上）
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                if (board[i][j] == '#') {
                    continue;
                }
                int i0 = i;
                // 正序放
                for (i = i0; i < m; i++) {
                    if (board[i][j] != ' ' && i - i0 < k && board[i][j] != word.charAt(i - i0) || i - i0 >= k) {
                        break;
                    }
                }
                // 正序放下去了
                if (i - i0 == k && (i < m && board[i][j] == '#' || i == m) && (i0 == 0 || board[i0 - 1][j] == '#')) {
                    return true;
                }

                // 倒序放
                for (i = i0; i < m; i++) {
                    if (board[i][j] != ' ' && i - i0 < k && board[i][j] != word.charAt(k - i + i0 - 1) || i - i0 >= k) {
                        break;
                    }
                }

                // 倒序放下去了
                if (i - i0 == k && (i < m && board[i][j] == '#' || i == m) && (i0 == 0 || board[i0 - 1][j] == '#')) {
                    return true;
                }
            }
        }

        return false;
    }


    // https://leetcode.cn/problems/the-score-of-students-solving-math-expression/solution/qu-jian-dpzong-zuo-you-by-zaozhe-gion/
    public int scoreOfStudents(String s, int[] a) {
        // stack 模擬計算機處理正確答案
        int correct = 0;
        int n = s.length();
        Deque<String> stack = new LinkedList();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '*') { // 遇到乘號，先把棧頂數字和乘號後面數字相乘後入棧
                int t = Integer.parseInt(stack.pollLast());
                stack.offerLast(String.valueOf(t * (s.charAt(++i) - '0')));
            } else if (s.charAt(i) != '+') { // 先跳過所有加號，把數字都直接入棧
                stack.offerLast(String.valueOf(s.charAt(i)));
            }
        }
        while (!stack.isEmpty()) { // 最後把棧裡所有數字加起來
            correct += Integer.parseInt(stack.pollLast());
        }

        // 區間DP
        int[] numbers = new int[(n + 1) / 2]; // 數字實際只佔 s 全長一半，但是一定會比運算符多一個
        boolean[] operations = new boolean[n / 2];
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '+') {
                operations[i / 2] = true;
            } else if (c != '*') {
                numbers[i / 2] = c - '0';
            }
        }

        int len = (n + 1) / 2;

        // dp[i][j] 表示字符串 s[i..j] 總共可以通過調換計算順序，得到哪些計算結果，用set排除重複結果。
        Set<Integer>[][] dp = new HashSet[len][len];
        for (int i = 0; i < len; i++) {
            dp[i][i] = new HashSet();
            dp[i][i].add(numbers[i]);
        }
        for (int j = 1; j < len; j++) {
            for (int i = j - 1; i >= 0; i--) {
                dp[i][j] = new HashSet();
                for (int k = i; k < j; k++) {
                    if (operations[k]) {
                        for (int x : dp[i][k]) { // 枚舉加法部份
                            for (int y : dp[k + 1][j]) { // 因為學生猜測結果均在 [0,1000]，因此超限的值可以直接忽略
                                if (x + y <= 1000) dp[i][j].add(x + y);
                            }
                        }
                    } else {
                        for (int x : dp[i][k]) {  // 枚舉乘法部份
                            for (int y : dp[k + 1][j]) { // 因為學生猜測結果均在 [0,1000]，因此超限的值可以直接忽略
                                if (x * y <= 1000) dp[i][j].add(x * y);
                            }
                        }
                    }
                }
            }
        }

        // 統計學生
        int res = 0;
        for (int x : a) {
            if (x == correct) res += 5; // 正確算五分
            else if (dp[0][len - 1].contains(x)) res += 2; // 如果錯誤出現在可能結果裡，算兩分
        }
        return res;
    }
}

