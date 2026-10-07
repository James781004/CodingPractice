package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.ArrayList;
import java.util.List;

public class GoodDaysToRobBank {

    // https://leetcode.cn/problems/find-good-days-to-rob-the-bank/solutions/1173638/qian-zhui-he-hou-zhui-shu-zu-ji-lu-by-sl-ek7l/
    public List<Integer> goodDaysToRobBank(int[] security, int time) {
        int n = security.length;
        List<Integer> ls = new ArrayList<>();
        if (n < 2 * time + 1) return ls; //若不滿足條件直接判斷沒有滿足的結果
        int[] left = new int[n];
        int[] right = new int[n];
        left[0] = 0;
        right[n - 1] = 0;
        int cnt = 0;
        for (int i = 1; i < n; ++i) {
            if (security[i - 1] >= security[i]) {
                cnt++;
                left[i] = cnt;
            } else {
                cnt = 0;
                left[i] = cnt;
            }
        }
        cnt = 0;
        for (int i = n - 2; i >= 0; --i) {
            if (security[i + 1] >= security[i]) {
                cnt++;
                right[i] = cnt;
            } else {
                cnt = 0;
                right[i] = cnt;
            }
        }
        for (int i = 0; i < n; ++i) {
            if (left[i] >= time && right[i] >= time) ls.add(i);
        }
        return ls;
    }

}
