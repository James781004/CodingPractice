package WeeklyContest;

import java.util.Arrays;

public class Week_381 {
    // https://leetcode.cn/problems/minimum-number-of-pushes-to-type-word-i/solutions/2613419/o1-shu-xue-gong-shi-pythonjavacgo-by-end-v2bt/
    public int minimumPushes(String word) {
        int n = word.length();
        int k = n / 8;
        return k * (k + 1) * 8 / 2 + n % 8 * (k + 1);
    }


    // https://leetcode.cn/problems/count-the-number-of-houses-at-a-certain-distance-i/solutions/2613421/fei-bao-li-zuo-fa-chai-fen-shu-zu-fen-le-g6wo/
    public int[] countOfPairs(int n, int x, int y) {
        if (x > y) {
            int temp = x;
            x = y;
            y = temp;
        }

        diff = new int[n + 2];

        for (int i = 1; i <= n; i++) {
            add(1, i - 1, 1);
            add(1, n - i, 1);
            if (x + 1 >= y) {
                continue;
            }
            if (i <= x) {
                update(i, x, y, n);
            } else if (i >= y) {
                update(n + 1 - i, n + 1 - y, n + 1 - x, n);
            } else if (i < (x + y) / 2) {
                update2(i, x, y, n);
            } else if (i > (x + y + 1) / 2) {
                update2(n + 1 - i, n + 1 - y, n + 1 - x, n);
            }
        }

        int[] ans = new int[n];
        int sumD = 0;
        for (int i = 0; i < n; i++) {
            sumD += diff[i + 1];
            ans[i] = sumD;
        }
        return ans;
    }

    private int[] diff;

    private void add(int l, int r, int v) {
        if (l > r) return;
        diff[l] += v;
        diff[r + 1] -= v;
    }

    private void update(int i, int x, int y, int n) {
        add(y - i, n - i, -1); // 撤銷 [y,n]
        int dec = y - x - 1; // 縮短的距離
        add(y - i - dec, n - i - dec, 1);

        int j = (x + y + 1) / 2 + 1;
        add(j - i, y - 1 - i, -1); // 撤銷 [j, y-1]
        add(x - i + 2, x - i + y - j + 1, 1);
    }

    private void update2(int i, int x, int y, int n) {
        add(y - i, n - i, -1); // 撤銷 [y,n]
        int dec = (y - i) - (i - x + 1); // 縮短的距離
        add(y - i - dec, n - i - dec, 1);

        int j = i + (y - x + 1) / 2 + 1;
        add(j - i, y - 1 - i, -1); // 撤銷 [j, y-1]
        add(i - x + 2, i - x + y - j + 1, 1);
    }


    // https://leetcode.cn/problems/minimum-number-of-pushes-to-type-word-ii/solutions/2613399/tan-xin-jian-ji-xie-fa-pythonjavacgo-by-5l4je/
    public int minimumPushesII(String word) {
        // 統計每個字母的出現次數，按照出現次數從大到小排序。
        int[] cnt = new int[26];
        for (char b : word.toCharArray()) {
            cnt[b - 'a']++;
        }
        Arrays.sort(cnt);

        int ans = 0;
        for (int i = 0; i < 26; i++) {
            // 出現次數前 8 大的字母，只需要按一次；出現次數前 9 到 16 大的字母，需要按兩次；依此類推。
            // 把出現次數和對應的按鍵次數相乘再相加，即為答案。
            ans += cnt[25 - i] * ((i / 8) + 1);
        }
        return ans;
    }

}
