package EndlessCheng.GenreMenu.DP.Enhance.CHT;

import java.util.ArrayList;
import java.util.List;

public class MinTime {

    // https://leetcode.cn/problems/find-the-minimum-amount-of-time-to-brew-potions/solutions/3624232/zheng-fan-liang-ci-sao-miao-pythonjavacg-5fz9/
    public long minTime(int[] skill, int[] mana) {
        int n = skill.length;
        int[] s = new int[n + 1];
        Vec[] points = new Vec[n];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + skill[i];
            points[i] = new Vec(s[i], skill[i]);
        }
        List<Vec> vs = convexHull(points); // 去掉無用數據

        int m = mana.length;
        long start = 0;
        for (int j = 1; j < m; j++) {
            Vec p = new Vec(mana[j - 1] - mana[j], mana[j - 1]);
            // p.dot(vs[i]) 是個單峰函數，二分找最大值
            int l = -1, r = vs.size() - 1;
            while (l + 1 < r) {
                int mid = (l + r) >>> 1;
                if (p.dot(vs.get(mid)) > p.dot(vs.get(mid + 1))) {
                    r = mid;
                } else {
                    l = mid;
                }
            }
            start += p.dot(vs.get(r));
        }
        return start + (long) mana[m - 1] * s[n];
    }

    private record Vec(int x, int y) {
        Vec sub(Vec b) {
            return new Vec(x - b.x, y - b.y);
        }

        long det(Vec b) {
            return (long) x * b.y - (long) y * b.x;
        }

        long dot(Vec b) {
            return (long) x * b.x + (long) y * b.y;
        }
    }

    // Andrew 算法，計算 points 的上凸包
    // 由於橫坐標（前綴和）是嚴格遞增的，所以無需排序
    private List<Vec> convexHull(Vec[] points) {
        List<Vec> q = new ArrayList<>();
        for (Vec p : points) {
            while (q.size() > 1 && q.getLast().sub(q.get(q.size() - 2)).det(p.sub(q.getLast())) >= 0) {
                q.removeLast();
            }
            q.add(p);
        }
        return q;
    }


}
