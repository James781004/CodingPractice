package EndlessCheng.GenreMenu.Backtracking.Backtracking.MeetMiddle;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class CountPairs {

    // https://leetcode.cn/problems/count-almost-equal-pairs-ii/solutions/2892072/pai-xu-mei-ju-you-wei-hu-zuo-pythonjavac-vbg0/
    private static final int[] pow10 = {1, 10, 100, 1000, 10000, 100000, 1000000};

    public int countPairs(int[] nums) {
        int n = nums.length;
        int m = String.valueOf(Arrays.stream(nums).max().orElseThrow()).length();
        int ans = 0;
        Map<Integer, BitSet> mp = new HashMap<>();
        int[] digits = new int[m];
        for (int pos = 0; pos < n; pos++) {
            int x = nums[pos];
            BitSet s = new BitSet();
            if (mp.containsKey(x)) s.or(mp.get(x));
            mp.computeIfAbsent(x, e -> new BitSet()).set(pos);
            int zz = 0;
            for (int i = 0, v = x; i < m; i++, v /= 10) {
                digits[zz++] = v % 10;
            }
            for (int i = 0; i < m; i++) {
                for (int j = i + 1; j < m; j++) {
                    if (digits[i] == digits[j]) continue;
                    int y = x + (digits[j] - digits[i]) * (pow10[i] - pow10[j]);
                    if (mp.containsKey(y)) s.or(mp.get(y));
                    mp.computeIfAbsent(y, e -> new BitSet()).set(pos);
                }
            }
            ans += s.cardinality();
        }
        return ans;
    }

}
