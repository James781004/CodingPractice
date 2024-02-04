package WeeklyContest;

public class Week_383 {
    // https://leetcode.cn/problems/ant-on-the-boundary/solutions/2630964/tong-ji-qian-zhui-he-zhong-de-0-de-ge-sh-fh1u/
    // 一邊遍歷數組，一邊累加元素，如果發現累加值等於 0，說明返回到邊界，把答案加一。
    public int returnToBoundaryCount(int[] nums) {
        int ans = 0;
        int sum = 0;
        for (int x : nums) {
            sum += x;
            if (sum == 0) {
                ans++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-time-to-revert-word-to-initial-state-i/solutions/2631174/javayi-ci-bian-li-by-gagaman-706z/
    public int minimumTimeToInitialState(String word, int k) {
        int len = word.length();
        int res = 0;  // 切割次數
        int sum = (int) Math.ceil((double) len / k); // 最大切割次數，等於重新生成一組 word
        for (int i = k; i < len; i += k) {
            res++;
            if (word.indexOf(word.substring(i)) == 0) return res; // 找到後綴表示操作 res 次後符合題目要求
        }
        return sum;
    }


    // https://leetcode.cn/problems/find-the-grid-of-region-average/solutions/2630945/yue-du-li-jie-pythonjavacgo-by-endlessch-pcnd/
    public int[][] resultGrid(int[][] a, int threshold) {
        int m = a.length;
        int n = a[0].length;
        int[][] result = new int[m][n];
        int[][] cnt = new int[m][n];
        for (int i = 2; i < m; i++) {
            next:
            for (int j = 2; j < n; j++) {
                // 檢查左右相鄰格子
                for (int x = i - 2; x <= i; x++) {
                    if (Math.abs(a[x][j - 2] - a[x][j - 1]) > threshold || Math.abs(a[x][j - 1] - a[x][j]) > threshold) {
                        continue next; // 不合法，下一個
                    }
                }

                // 檢查上下相鄰格子
                for (int y = j - 2; y <= j; ++y) {
                    if (Math.abs(a[i - 2][y] - a[i - 1][y]) > threshold || Math.abs(a[i - 1][y] - a[i][y]) > threshold) {
                        continue next; // 不合法，下一個
                    }
                }

                // 合法，計算 3x3 子網格的平均值
                int avg = 0;
                for (int x = i - 2; x <= i; x++) {
                    for (int y = j - 2; y <= j; y++) {
                        avg += a[x][y];
                    }
                }
                avg /= 9;

                // 更新 3x3 子網格內的 result
                for (int x = i - 2; x <= i; x++) {
                    for (int y = j - 2; y <= j; y++) {
                        result[x][y] += avg; // 先累加，最後再求平均值
                        cnt[x][y]++;
                    }
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (cnt[i][j] == 0) { // (i,j) 不屬於任何子網格
                    result[i][j] = a[i][j];
                } else {
                    result[i][j] /= cnt[i][j]; // 求平均值
                }
            }
        }
        return result;
    }


    // https://leetcode.cn/problems/minimum-time-to-revert-word-to-initial-state-ii/solutions/2630932/z-han-shu-kuo-zhan-kmp-by-endlesscheng-w44j/
    public int minimumTimeToInitialState2(String S, int k) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[] z = new int[n]; // z box
        int l = 0, r = 0; // z box 的左右邊界
        for (int i = 1; i < n; i++) {
            if (i <= r) {  // i 在 z box 的邊界內
                z[i] = Math.min(z[i - l], r - i + 1);  // z 函數核心思想
            }

            // 繼續向後暴力匹配
            while (i + z[i] < n && s[z[i]] == s[i + z[i]]) {
                l = i;
                r = i + z[i];
                z[i]++;
            }
            if (i % k == 0 && z[i] >= n - i) {
                return i / k;
            }
        }
        return (n - 1) / k + 1;
    }
}
