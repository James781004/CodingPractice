package WeeklyContest;

import java.util.*;
import java.util.regex.Pattern;

class Week_355 {
    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2788.Split%20Strings%20by%20Separator/README.md
    public List<String> splitWordsBySeparator(List<String> words, char separator) {
        List<String> ans = new ArrayList<>();
        for (String w : words) {
            for (String s : w.split(Pattern.quote(String.valueOf(separator)))) {
                if (s.length() > 0) {
                    ans.add(s);
                }
            }
        }
        return ans;
    }

    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2789.Largest%20Element%20in%20an%20Array%20after%20Merge%20Operations/README.md
    public long maxArrayValue(int[] nums) {
        int n = nums.length;
        long ans = nums[n - 1], t = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] <= t) {
                t += nums[i];
            } else {
                t = nums[i];
            }
            ans = Math.max(ans, t);
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2790.Maximum%20Number%20of%20Groups%20With%20Increasing%20Length/README.md
    // 思路：https://leetcode.cn/problems/maximum-number-of-groups-with-increasing-length/solution/tan-xin-by-kuang-qie-2-fw0r/

    /**
     * 先對usageLimits排序
     * 貪心，每新增一個group, 最優的策略：group[i + 1].size() = group[i].size() + 1;
     * 從小到大遍歷usageLimits, 每新增一個group, 必須要新增一個元素,
     * 且新增元素值 和 緩存未用完的值 必須要大於當前分組的值。若滿足，說明可以新增一個group，未使用完的值加入緩存。
     * *
     * 舉例說明：[2, 2, 2]
     * group[0]: 0
     * group[1]: 2 1
     * group[2]: 2 1 0
     * *
     * 從i開始遍歷（倒序看上面這個過程，填充"列"），而且排序後遞增順序能保證相同的數不在同一行（下面被新數填充）
     * i == 0時，使用一個0放入group[2][2]即可，剩余一個0可以加入緩存，這個緩存的值在後續的i都是可以使用的。
     * i == 1時，使用兩個1, 分別放在group[1][1], group[2][1].
     * i == 2時，使用兩個2和緩存裡的0, 分別放在group[0][0], grouo[1][0], group[2][0]的位置。
     */
    public int maxIncreasingGroups(List<Integer> usageLimits) {
        Collections.sort(usageLimits);
        int k = 0;
        long s = 0;
        for (int x : usageLimits) {
            s += x;
            if (s > k) {
                ++k;
                s -= k;
            }
        }
        return k;
    }


    // https://www.youtube.com/watch?v=2Wa-HsxzRKk
    // https://leetcode.cn/problems/count-paths-that-can-form-a-palindrome-in-a-tree/solution/yong-wei-yun-suan-chu-li-by-endlesscheng-n9ws/
    private List<int[]>[] g;
    private Map<Integer, Integer> cnt;
    private long res;

    public long countPalindromePaths(List<Integer> parent, String s) {
        int n = parent.size();
        this.g = new ArrayList[n];
        Arrays.setAll(g, k -> new ArrayList<>());
        for (int i = 1; i < n; ++i) {
            g[parent.get(i)].add(new int[]{i, 1 << (s.charAt(i) - 'a')});
        }
        this.cnt = new HashMap<>();
        dfs(0, 0);
        return res;

    }

    private void dfs(int x, int xor) {
        res += cnt.getOrDefault(xor, 0);
        for (int i = 0; i < 26; ++i) {
            res += cnt.getOrDefault(xor ^ (1 << i), 0);
        }
        cnt.merge(xor, 1, Integer::sum);
        for (int[] neighbor : g[x]) {
            int y = neighbor[0];
            int m = neighbor[1];
            dfs(y, xor ^ m);
        }
    }
}


