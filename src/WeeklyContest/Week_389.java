package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_389 {
    // https://leetcode.cn/problems/existence-of-a-substring-in-a-string-and-its-reverse/solutions/2692152/yi-ci-bian-li-wei-yun-suan-you-hua-pytho-ijj7/
    public boolean isSubstringPresent(String S) {
        char[] s = S.toCharArray();
        boolean[][] vis = new boolean[26][26];
        for (int i = 1; i < s.length; i++) {
            int x = s[i - 1] - 'a';
            int y = s[i] - 'a';
            vis[x][y] = true;
            if (vis[y][x]) {
                return true;
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/count-substrings-starting-and-ending-with-given-character/solutions/2692043/jian-ji-xie-fa-pythonjavacgo-by-endlessc-ecl8/
    public long countSubstrings(String s, char c) {
        long k = s.chars().filter(ch -> ch == c).count();
        return k * (k + 1) / 2;
    }


    // https://leetcode.cn/problems/minimum-deletions-to-make-string-k-special/solutions/2692077/kao-lu-zui-duo-bao-liu-duo-shao-ge-zi-mu-qttz/
    public int minimumDeletions(String word, int k) {
        int[] cnt = new int[26];
        for (char c : word.toCharArray()) {
            cnt[c - 'a']++;
        }
        Arrays.sort(cnt);

        int maxSave = 0;
        for (int i = 0; i < 26; i++) {
            int sum = 0;
            for (int j = i; j < 26; j++) {
                sum += Math.min(cnt[j], cnt[i] + k);
            }
            maxSave = Math.max(maxSave, sum);
        }
        return word.length() - maxSave;
    }


    // https://leetcode.cn/problems/minimum-moves-to-pick-k-ones/solutions/2692009/zhong-wei-shu-tan-xin-by-endlesscheng-h972/
    public long minimumMoves(int[] nums, int k, int maxChanges) {
        List<Integer> pos = new ArrayList<>();
        int c = 0; // nums 中連續的 1 長度
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) continue;
            pos.add(i); // 記錄 1 的位置
            c = Math.max(c, 1);
            if (i > 0 && nums[i - 1] == 1) {
                if (i > 1 && nums[i - 2] == 1) {
                    c = 3; // 有 3 個連續的 1
                } else {
                    c = Math.max(c, 2); // 有 2 個連續的 1
                }
            }
        }

        c = Math.min(c, k);
        if (maxChanges >= k - c) {
            // 其余 k-c 個 1 可以全部用兩次操作得到
            return Math.max(c - 1, 0) + (k - c) * 2;
        }

        int n = pos.size();
        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + pos.get(i);
        }

        long ans = Long.MAX_VALUE;
        // 除了 maxChanges 個數可以用兩次操作得到，其余的 1 只能一步步移動到 pos[i]
        int size = k - maxChanges;
        for (int right = size; right <= n; right++) {
            // s1+s2 是 j 在 [left, right) 中的所有 pos[j] 到 index=pos[(left+right)/2] 的距離之和
            int left = right - size;
            int i = left + size / 2;
            long index = pos.get(i);
            long s1 = index * (i - left) - (sum[i] - sum[left]);
            long s2 = sum[right] - sum[i] - index * (right - i);
            ans = Math.min(ans, s1 + s2);
        }
        return ans + maxChanges * 2;
    }
}


