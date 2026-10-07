package EndlessCheng.GenreMenu.DP.OtherLinear.Intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxTwoEvents {

    // https://leetcode.cn/problems/two-best-non-overlapping-events/solutions/1075386/yong-you-xian-dui-lie-wei-hu-ling-yi-ge-8ld3x/
    public int maxTwoEvents(int[][] events) {
        // 按照結束時間排序
        Arrays.sort(events, (a, b) -> a[1] - b[1]);

        // 從棧底到棧頂，結束時間遞增，價值遞增
        ArrayList<int[]> st = new ArrayList<>(); // (結束時間, 價值)
        st.add(new int[]{0, 0}); // 棧底哨兵

        int ans = 0;
        for (int[] e : events) {
            int i = search(st, e[0]);
            int value = e[2];
            ans = Math.max(ans, st.get(i)[1] + value);
            // 遇到比棧頂更大的價值，入棧
            if (value > st.getLast()[1]) {
                st.add(new int[]{e[1], value});
            }
        }
        return ans;
    }

    // 返回最後一個滿足 st[i][0] < target 的 i
    private int search(List<int[]> st, int target) {
        int left = -1, right = st.size();
        while (left + 1 < right) { // 開區間二分
            int mid = left + (right - left) / 2;
            if (st.get(mid)[0] < target) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }


}
