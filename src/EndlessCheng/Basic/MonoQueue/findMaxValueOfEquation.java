package EndlessCheng.Basic.MonoQueue;

import java.util.ArrayDeque;

public class findMaxValueOfEquation {

    // https://leetcode.cn/problems/max-value-of-equation/solutions/2352457/on-dan-diao-dui-lie-fu-ti-dan-pythonjava-hhrr/
    public int findMaxValueOfEquation(int[][] points, int k) {
        int ans = Integer.MIN_VALUE;
        var q = new ArrayDeque<int[]>();
        for (var p : points) {
            int x = p[0], y = p[1];
            while (!q.isEmpty() && q.peekFirst()[0] < x - k) // 隊首超出范圍
                q.pollFirst(); // 彈它！
            if (!q.isEmpty())
                ans = Math.max(ans, x + y + q.peekFirst()[1]); // 加上最大的 yi-xi
            while (!q.isEmpty() && q.peekLast()[1] <= y - x) // 隊尾不如新來的強
                q.pollLast(); // 彈它！
            q.addLast(new int[]{x, y - x});
        }
        return ans;
    }


}
