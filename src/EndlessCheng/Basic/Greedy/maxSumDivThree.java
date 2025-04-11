package EndlessCheng.Basic.Greedy;

import java.util.ArrayList;
import java.util.Collections;

public class maxSumDivThree {

    // https://leetcode.cn/problems/greatest-sum-divisible-by-three/solutions/2313700/liang-chong-suan-fa-tan-xin-dong-tai-gui-tsll/
    public int maxSumDivThree(int[] nums) {
        int s = 0;
        for (int x : nums)
            s += x;
        if (s % 3 == 0)
            return s;

        var a1 = new ArrayList<Integer>();
        var a2 = new ArrayList<Integer>();
        for (int x : nums) {
            if (x % 3 == 1) a1.add(x);
            else if (x % 3 == 2) a2.add(x);
        }
        Collections.sort(a1);
        Collections.sort(a2);

        if (s % 3 == 2) { // swap(a1,a2)
            var tmp = a1;
            a1 = a2;
            a2 = tmp;
        }

        // 直接扣掉最小差值湊出三的倍數
        int ans = a1.isEmpty() ? 0 : s - a1.get(0);
        if (a2.size() > 1)
            ans = Math.max(ans, s - a2.get(0) - a2.get(1));
        return ans;
    }


}
