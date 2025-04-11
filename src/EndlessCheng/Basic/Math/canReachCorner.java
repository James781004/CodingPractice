package EndlessCheng.Basic.Math;

public class canReachCorner {

    // https://leetcode.cn/problems/check-if-the-rectangle-corner-is-reachable/solutions/2860214/deng-jie-zhuan-huan-bing-cha-ji-pythonja-yf9y/
    public boolean canReachCorner(int X, int Y, int[][] circles) {
        boolean[] vis = new boolean[circles.length];
        for (int i = 0; i < circles.length; i++) {
            long x = circles[i][0], y = circles[i][1], r = circles[i][2];
            if (inCircle(x, y, r, 0, 0) || // 圓 i 包含矩形左下角
                    inCircle(x, y, r, X, Y) || // 圓 i 包含矩形右上角
                    // 圓 i 是否與矩形上邊界/左邊界相交相切
                    !vis[i] && (x <= X && Math.abs(y - Y) <= r ||
                            y <= Y && x <= r ||
                            y > Y && inCircle(x, y, r, 0, Y)) && dfs(i, X, Y, circles, vis)) {
                return false;
            }
        }
        return true;
    }

    // 判斷點 (x,y) 是否在圓 (ox,oy,r) 內
    private boolean inCircle(long ox, long oy, long r, long x, long y) {
        return (ox - x) * (ox - x) + (oy - y) * (oy - y) <= r * r;
    }

    private boolean dfs(int i, int X, int Y, int[][] circles, boolean[] vis) {
        long x1 = circles[i][0], y1 = circles[i][1], r1 = circles[i][2];
        // 圓 i 是否與矩形右邊界/下邊界相交相切
        if (y1 <= Y && Math.abs(x1 - X) <= r1 ||
                x1 <= X && y1 <= r1 ||
                x1 > X && inCircle(x1, y1, r1, X, 0)) {
            return true;
        }
        vis[i] = true;
        for (int j = 0; j < circles.length; j++) {
            long x2 = circles[j][0], y2 = circles[j][1], r2 = circles[j][2];
            // 在兩圓相交相切的前提下，點 A 是否嚴格在矩形內
            if (!vis[j] &&
                    (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) <= (r1 + r2) * (r1 + r2) &&
                    x1 * r2 + x2 * r1 < (r1 + r2) * X &&
                    y1 * r2 + y2 * r1 < (r1 + r2) * Y &&
                    dfs(j, X, Y, circles, vis)) {
                return true;
            }
        }
        return false;
    }


}
