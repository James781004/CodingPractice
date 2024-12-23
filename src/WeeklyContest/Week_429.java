package WeeklyContest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Week_429 {

    // https://leetcode.cn/problems/minimum-number-of-operations-to-make-elements-in-array-distinct/solutions/3027035/on-yi-ci-bian-li-jian-ji-xie-fa-pythonja-jgox/
    public int minimumOperations(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            if (!seen.add(nums[i])) { // nums[i] 在 seen 中
                return i / 3 + 1;
            }
        }
        return 0;
    }


    // https://leetcode.cn/problems/maximum-number-of-distinct-elements-after-operations/solutions/3027034/cong-xiao-dao-da-tan-xin-pythonjavacgo-b-n023/
    public int maxDistinctElements(int[] nums, int k) {
        Arrays.sort(nums);
        int ans = 0;
        int pre = Integer.MIN_VALUE; // 記錄每個人左邊的人的位置
        for (int x : nums) {
            // x盡量往左移，最好是剛好在 pre + 1，
            // 如果 pre + 1 過小，最左到 x - k
            // 但是這個數不能超過 x + k，
            // 所以如果 pre + 1 已經過大，最左就只能到 x + k
            x = Math.min(Math.max(x - k, pre + 1), x + k);

            // 如果發現當前元素修改後的值，比上一個元素修改後的值大，那麼答案加 1
            if (x > pre) {
                ans++;
                pre = x;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/smallest-substring-with-identical-characters-ii/solutions/3027031/er-fen-da-an-tan-xin-gou-zao-pythonjavac-3i4f/
    public int minLength(String S, int numOps) {
        char[] s = S.toCharArray();
        int left = 0;
        int right = s.length;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(mid, s, numOps)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(int m, char[] s, int numOps) {
        int n = s.length;
        int cnt = 0;
        if (m == 1) {
            // 改成 0101...
            for (int i = 0; i < n; i++) {
                // 如果 s[i] 和 i 的奇偶性不同，cnt 加一
                cnt += (s[i] ^ i) & 1;
            }
            // n-cnt 表示改成 1010...
            cnt = Math.min(cnt, n - cnt);
        } else {
            int k = 0;
            for (int i = 0; i < n; i++) {
                k++;
                // 到達連續相同子串的末尾，就要開始操作改子串
                if (i == n - 1 || s[i] != s[i + 1]) {
                    cnt += k / (m + 1); // 相當於每有 m+1 個字符，就要操作一次
                    k = 0;
                }
            }
        }
        return cnt <= numOps;
    }


}









