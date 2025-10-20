package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ComposeCube {

    // https://leetcode.cn/problems/De4qBB/solutions/1445670/hui-su-by-planet87-tsi6/
    int n;

    // 8個旋轉分別坐標偏移情況：
    // [r,c],    [c,n-1-r],[n-1-r,n-1-c],[n-1-c,r]
    // [r,n-1-c],[c,r],    [n-1-r,c],    [n-1-c,n-1-r]

    // a[0]*r+ a[1]*!r+a[2]*c+a3*!c, a[4]*r+ a[5]*!r+a[6]*c+a[7]*!c,
    int[][] r8 = {
            {1, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 0, 0, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 1}, {0, 0, 0, 1, 1, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 1}, {0, 0, 1, 0, 1, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 1, 0, 1, 0, 0}};

    // 預處理除shape0以外，其他5個面的200種情況。
    Map<Integer, Map<Integer, Map<Integer, Set<Integer>>>> csm;

    /**
     * side : 0:bottom 1:left 2:right 3:front 4:back 5:top
     *
     * @param shapes
     * @return
     */
    public boolean composeCube(String[][] shapes) {
        this.n = shapes[0].length;
        int cnt1 = 0;
        for (String[] s : shapes) {
            for (String ss : s) {
                for (char c : ss.toCharArray()) {
                    if (c == '1') {
                        cnt1++;
                    }
                }
            }
        }
        if (cnt1 != (n * n * n - (n - 2) * (n - 2) * (n - 2))) {
            return false;
        }

        // 固定第一個面，不作翻轉。 points表示當前組合在空間中填掉的格
        Set<Integer> points = createPoints(shapes[0], 0, 0);
        csm = new HashMap<>();
        for (int i = 1; i < 6; i++) {
            for (int side = 1; side < 6; side++) {
                for (int ro = 0; ro < 8; ro++) {
                    Set<Integer> pts = createPoints(shapes[i], side, ro);
                    csm.computeIfAbsent(i, z -> new HashMap<>()).computeIfAbsent(side, z -> new HashMap<>())
                            .computeIfAbsent(ro, z -> new HashSet<>()).addAll(pts);
                }
            }
        }
        return dfs(points, 1, new boolean[6]);
    }

    private boolean dfs(Set<Integer> points, int side, boolean[] cs) {
        if (side > 5) {
            // 回溯終止，表示所有面都完成安放
            return true;
        }

        for (int next = 1; next < 6; next++) {
            if (cs[next]) {
                // 該數組記錄那些面被選擇過了
                continue;
            }
            cs[next] = true;
            for (int ro = 0; ro < 8; ro++) {
                // 新增的選擇將在空間中增加的點
                Set<Integer> ns = csm.get(next).get(side).get(ro);
                Set<Integer> nextPoints = new HashSet<>(points);
                int sz1 = points.size();
                int sz2 = ns.size();
                nextPoints.addAll(ns);
                if (nextPoints.size() != sz1 + sz2) {
                    // 表示有點碰撞了
                    continue;
                }
                // next
                if (dfs(nextPoints, side + 1, cs)) {
                    return true;
                }
            }
            cs[next] = false;
        }

        return false;
    }

    private Set<Integer> createPoints(String[] shape, int side, int rotate) {
        Set<Integer> res = new HashSet<>();
        int[] rr = r8[rotate];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int r = rr[0] * i + rr[1] * (n - 1 - i) + rr[2] * j + rr[3] * (n - 1 - j);
                int c = rr[4] * i + rr[5] * (n - 1 - i) + rr[6] * j + rr[7] * (n - 1 - j);
                if (shape[r].charAt(c) == '1') {
                    // 每個面的XYZ。
                    if (side == 0) {
                        res.add(offset(i, j, 0));
                    } else if (side == 1) {
                        res.add(offset(0, i, j));
                    } else if (side == 2) {
                        res.add(offset(n - 1, i, j));
                    } else if (side == 3) {
                        res.add(offset(i, 0, j));
                    } else if (side == 4) {
                        res.add(offset(i, n - 1, j));
                    } else {
                        res.add(offset(i, j, n - 1));
                    }
                }
            }
        }

        return res;
    }

    int offset(int x, int y, int z) {
        return (x * 100) + (y * 10) + z;
    }


}
