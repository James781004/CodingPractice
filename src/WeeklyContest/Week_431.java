package WeeklyContest;

import java.util.*;

public class Week_431 {

    // https://leetcode.cn/problems/maximum-subarray-with-equal-products/solutions/3039079/mei-ju-ti-qian-tui-chu-xun-huan-pythonja-a21k/
    public int maxLength(int[] nums) {
        int mx = Arrays.stream(nums).max().getAsInt();
        int allLcm = 1;
        for (int x : nums) {
            allLcm = lcm(allLcm, x);
        }

        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int m = 1;
            int l = 1;
            int g = 0;
            for (int j = i; j < nums.length && m <= allLcm * mx; j++) {
                int x = nums[j];
                m *= x;
                l = lcm(l, x);
                g = gcd(g, x);
                if (m == l * g) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }

    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }

    private int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }


    // https://leetcode.cn/problems/find-mirror-score-of-a-string/solutions/3039028/26-ge-zhan-pythonjavacgo-by-endlesscheng-ysmk/
    public long calculateScore(String s) {
        Deque<Integer>[] stk = new ArrayDeque[26];
        Arrays.setAll(stk, i -> new ArrayDeque<>());
        long ans = 0;
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            if (!stk[25 - c].isEmpty()) {
                ans += i - stk[25 - c].pop();
            } else {
                stk[c].push(i);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-coins-from-k-consecutive-bags/solutions/3039059/hua-dong-chuang-kou-hua-liang-bian-pytho-4u47/
    public long maximumCoins(int[][] coins, int k) {
        Arrays.sort(coins, (a, b) -> a[0] - b[0]);
        long ans = maximumWhiteTiles(coins, k);

        for (int i = 0, j = coins.length - 1; i < j; i++, j--) {
            int[] tmp = coins[i];
            coins[i] = coins[j];
            coins[j] = tmp;
        }
        for (int[] t : coins) {
            int temp = t[0];
            t[0] = -t[1];
            t[1] = -temp;
        }
        return Math.max(ans, maximumWhiteTiles(coins, k));
    }

    // 2271. 毯子覆蓋的最多白色磚塊數
    // https://leetcode.cn/problems/maximum-white-tiles-covered-by-a-carpet/solutions/1496434/by-endlesscheng-kdy9/
    private long maximumWhiteTiles(int[][] tiles, int carpetLen) {
        long ans = 0;
        long cover = 0;
        int left = 0;
        for (int[] tile : tiles) {
            int tl = tile[0], tr = tile[1], c = tile[2];
            cover += (long) (tr - tl + 1) * c; // 先把右邊毯子長度加上
            while (tiles[left][1] + carpetLen - 1 < tr) { // 左邊出
                // 縮短窗口把左邊毯子長度去掉
                cover -= (long) (tiles[left][1] - tiles[left][0] + 1) * tiles[left][2];
                left++;
            }

            // 最大的左端點的位置，之前已經加上的左邊毯子的數量，減去多加的那部分
            long uncover = Math.max((long) (tr - carpetLen + 1 - tiles[left][0]) * tiles[left][2], 0);

            // 更新答案
            ans = Math.max(ans, cover - uncover);
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-score-of-non-overlapping-intervals/solutions/3039058/dong-tai-gui-hua-er-fen-cha-zhao-you-hua-wmuy/
    private record Tuple(int l, int r, int weight, int i) {
    }

    private record Pair(long sum, List<Integer> id) {
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Tuple[] a = new Tuple[n];
        for (int i = 0; i < n; i++) {
            List<Integer> interval = intervals.get(i);
            a[i] = new Tuple(interval.get(0), interval.get(1), interval.get(2), i);
        }
        Arrays.sort(a, (p, q) -> p.r - q.r);

        Pair[][] f = new Pair[n + 1][5];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < 5; j++) {
                f[i][j] = new Pair(0, new ArrayList<>());
            }
        }

        for (int i = 0; i < n; i++) {
            Tuple t = a[i];
            int k = search(a, i, t.l);
            for (int j = 1; j < 5; j++) {
                long s1 = f[i][j].sum;
                long s2 = f[k + 1][j - 1].sum + t.weight;
                if (s1 > s2) {
                    f[i + 1][j] = f[i][j];
                    continue;
                }
                List<Integer> newId = new ArrayList<>(f[k + 1][j - 1].id);
                newId.add(t.i);
                Collections.sort(newId);
                if (s1 == s2 && compareLists(f[i][j].id, newId) < 0) {
                    newId = f[i][j].id;
                }
                f[i + 1][j] = new Pair(s2, newId);
            }
        }
        return f[n][4].id.stream().mapToInt(v -> v).toArray();
    }

    // 返回 r < upper 的最大下標
    private int search(Tuple[] a, int right, int upper) {
        int left = -1;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (a[mid].r < upper) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int compareLists(List<Integer> a, List<Integer> b) {
        for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) - b.get(i);
            }
        }
        return a.size() - b.size();
    }


}









