package WeeklyContest;

import java.util.HashSet;
import java.util.Set;

class Week_245 {
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1897.Redistribute%20Characters%20to%20Make%20All%20Strings%20Equal/README.md
    public boolean makeEqual(String[] words) {
        int[] counter = new int[26];
        for (String word : words) {
            for (char c : word.toCharArray()) {
                ++counter[c - 'a'];
            }
        }
        int n = words.length;
        for (int i = 0; i < 26; ++i) {
            if (counter[i] % n != 0) {
                return false;
            }
        }
        return true;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1898.Maximum%20Number%20of%20Removable%20Characters/README.md
    public int maximumRemovals(String s, String p, int[] removable) {
        int left = 0, right = removable.length;

        // 始終保持答案位於二分區間內，二分結束條件對應的值恰好在答案所處的位置。
        // 對於可能無解的情況，只要判斷二分結束後的 left 或者 right 是否滿足題意即可。
        while (left < right) {
            int mid = (left + right + 1) >> 1;
            if (check(s, p, removable, mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    // 判斷p是否是s的子串,且不能包含被刪除的元素
    private boolean check(String s, String p, int[] removable, int mid) {
        int m = s.length(), n = p.length(), i = 0, j = 0;
        Set<Integer> ids = new HashSet<>();
        for (int k = 0; k < mid; ++k) {
            ids.add(removable[k]);
        }
        while (i < m && j < n) {
            if (!ids.contains(i) && s.charAt(i) == p.charAt(j)) {
                ++j;
            }
            ++i;
        }
        return j == n;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1899.Merge%20Triplets%20to%20Form%20Target%20Triplet/README.md
    // 貪心
    public boolean mergeTriplets(int[][] triplets, int[] target) {
        int x = target[0], y = target[1], z = target[2];
        int d = 0, e = 0, f = 0;
        for (int[] t : triplets) {
            int a = t[0], b = t[1], c = t[2];

            // triplets 如果同時存在三個小於等於 target 的元素，就嘗試更新答案
            if (a <= x && b <= y && c <= z) {
                d = Math.max(d, a);
                e = Math.max(e, b);
                f = Math.max(f, c);
            }
        }
        return d == x && e == y && f == z;
    }


    // https://leetcode.cn/problems/the-earliest-and-latest-rounds-where-players-compete/solutions/825952/li-yong-dui-cheng-xing-deng-xiao-wen-ti-nbdjb/
    public int[] earliestAndLatest(int n, int firstPlayer, int secondPlayer) {
        if (firstPlayer + secondPlayer == n + 1) {
            return new int[]{1, 1};
        }
        if (firstPlayer > n + 1 - secondPlayer) {
            return earliestAndLatest(n, n + 1 - secondPlayer, n + 1 - firstPlayer);
        }
        int m = (n + 1) >> 1;
        int min = n, max = 0;
        if (secondPlayer <= m) {
            for (int f = 1; f <= firstPlayer; f++) {
                for (int s = f + 1; s <= secondPlayer - firstPlayer + f; s++) {
                    int[] el = earliestAndLatest(m, f, s);
                    min = Math.min(el[0], min);
                    max = Math.max(el[1], max);
                }
            }
        } else {
            int _s = n + 1 - secondPlayer;
            for (int f = 1; f <= firstPlayer; f++) {
                for (int s = f + 1; s <= _s - firstPlayer + f; s++) {
                    int[] el = earliestAndLatest(m, f, s + m - _s);
                    min = Math.min(el[0], min);
                    max = Math.max(el[1], max);
                }
            }
        }
        return new int[]{min + 1, max + 1};
    }
}



