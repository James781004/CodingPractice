package FuckingAlgorithm.Extra;

import java.util.*;

public class Q09_PerfectRectangle {
//    https://leetcode.cn/problems/perfect-rectangle/
//    391. 完美矩形
//    給你一個數組 rectangles ，其中 rectangles[i] = [xi, yi, ai, bi] 表示一個坐標軸平行的矩形。
//    這個矩形的左下頂點是 (xi, yi) ，右上頂點是 (ai, bi) 。
//
//    如果所有矩形一起精確覆蓋了某個矩形區域，則返回 true ；否則，返回 false 。

    public boolean isRectangleCover(int[][] rectangles) {
        int X1 = Integer.MAX_VALUE, Y1 = Integer.MAX_VALUE, X2 = Integer.MIN_VALUE, Y2 = Integer.MIN_VALUE;
        Set<String> points = new HashSet<>();
        int actual_area = 0;
        for (int[] item : rectangles) {

            // 計算完美矩形的理論頂點坐標
            int x1 = item[0], y1 = item[1], x2 = item[2], y2 = item[3];
            X1 = Math.min(X1, x1);
            Y1 = Math.min(Y1, y1);
            X2 = Math.max(X2, x2);
            Y2 = Math.max(Y2, y2);

            // 累加小矩形的面積
            actual_area += (x2 - x1) * (y2 - y1);

            // 記錄最終形成的圖形中的頂點
            int[] p1 = new int[]{x1, y1};
            int[] p2 = new int[]{x1, y2};
            int[] p3 = new int[]{x2, y1};
            int[] p4 = new int[]{x2, y2};
            for (int[] p : new int[][]{p1, p2, p3, p4}) {
                String s = p[0] + "," + p[1];
                if (points.contains(s)) {
                    points.remove(s);
                } else {
                    points.add(s);
                }
            }
        }

        // 判斷面積是否相同
        int expected_area = (X2 - X1) * (Y2 - Y1);
        if (actual_area != expected_area) {
            return false;
        }

        // 判斷最終留下的頂點個數是否為 4
        if (points.size() != 4) return false;

        // 判斷留下的 4 個頂點是否是完美矩形的頂點
        String s1 = X1 + "," + Y1;
        String s2 = X1 + "," + Y2;
        String s3 = X2 + "," + Y1;
        String s4 = X2 + "," + Y2;
        if (!points.contains(s1)) return false;
        if (!points.contains(s2)) return false;
        if (!points.contains(s3)) return false;
        if (!points.contains(s4)) return false;

        // 面積和頂點都對應，說明矩形符合題意
        return true;
    }


    // 掃描線
    public boolean isRectangleCover2(int[][] rectangles) {
        int len = rectangles.length * 2, ids = 0;
        int[][] re = new int[len][4];

        // 初始化re數組,組成[橫坐標,縱坐標下頂點,縱坐標上頂點,矩形的左邊or右邊標志]
        for (int[] i : rectangles) {
            re[ids++] = new int[]{i[0], i[1], i[3], 1};
            re[ids++] = new int[]{i[2], i[1], i[3], -1};
        }

        // 排序,按照橫坐標進行排序,橫坐標相等就按縱坐標排序
        Arrays.sort(re, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        // 操作每一個頂點，判斷是否符合要求
        for (int i = 0; i < len; ) {
            // 如果該邊是矩形的左邊界,就加入left
            List<int[]> left = new ArrayList<>();
            // 如果該邊是矩形的左邊界,就加入right
            List<int[]> right = new ArrayList<>();
            // 標志該邊是不是 矩形的左邊
            boolean flag = i == 0;
            // 判斷橫坐標相同情況下的邊
            int x = i;
            while (x < len && re[x][0] == re[i][0]) x++;
            // 判斷該橫坐標的 邊是不是符合要求
            while (i < x) {
                // 因為是引用數據類型,所以可以直接操作list,相當於操作left或者right
                List<int[]> list = re[i][3] == 1 ? left : right;
                if (list.isEmpty()) {
                    list.add(re[i++]);
                } else {
                    int[] pre = list.get(list.size() - 1);
                    int[] cur = re[i++];
                    // 有重疊 直接放回false
                    if (cur[1] < pre[2]) return false;
                    if (cur[1] == pre[2]) pre[2] = cur[2];
                    else list.add(cur);
                }
            }
            
            // 判斷邊是中間邊還是邊界邊
            if (!flag && x < len) {
                // 如果是中間邊 判斷左右是不是相等
                if (left.size() != right.size()) return false;
                for (int j = 0; j < left.size(); ++j) {
                    if (left.get(j)[2] == right.get(j)[2] && left.get(j)[1] == right.get(j)[1]) continue;
                    return false;
                }
            } else {
                // 如果是邊界邊判斷是不是一條
                if (left.size() != 1 && right.size() == 0 || left.size() == 0 && right.size() != 1) return false;
            }
        }
        return true;
    }
}
