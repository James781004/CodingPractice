package EndlessCheng.GenreMenu.Grid.Comprehensive;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class IsEscapePossible {

    // https://leetcode.cn/problems/escape-a-large-maze/solutions/1204417/bian-cheng-xiong-bfs-acmjin-pai-ti-jie-b-dquc/
    int[][] blocked;
    int[] source;
    int[] target;
    int MaxPoint;
    int[][] dir = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    Set<Long> blockSet = new HashSet<>();

    public boolean isEscapePossible(int[][] blocked, int[] source, int[] target) {
        this.blocked = blocked;
        this.source = source;
        this.target = target;
        this.MaxPoint = (blocked.length + 1) * (blocked.length + 2) / 2;
        // 將block塊，放到哈希表，後面遍歷地圖會用到
        for (int i = 0; i < blocked.length; i++)
            blockSet.add(blocked[i][0] * (long) 1e6 + blocked[i][1]);
        // 分別從起點和終點BFS出發遍歷地圖
        return bfs(source[0], source[1], true) && bfs(target[0], target[1], false);
    }

    boolean bfs(int x, int y, boolean is_source) {
        // vis存放已經訪問過的點
        Set<Long> vis = new HashSet<>();
        // 維護BFS過程的隊列
        Deque<Long> d = new ArrayDeque<>();
        // 將起點放入隊列中，並在vis中標記
        d.add(x * (long) 1e6 + y);
        vis.add(x * (long) 1e6 + y);

        while ((!d.isEmpty()) && (vis.size() <= MaxPoint)) {
            long p = d.pollFirst();
            long px = (long) (p / 1e6);
            long py = (long) (p - px * 1e6);

            for (int i = 0; i < 4; i++) {
                // 找到相鄰的點
                long nx = px + dir[i][0];
                long ny = py + dir[i][1];
                // 出邊界 或者 是block塊
                if (nx < 0 || nx >= 1e6 || ny < 0 || ny >= 1e6 || blockSet.contains(nx * (long) 1e6 + ny))
                    continue;
                // 已經訪問過
                if (vis.contains(nx * (long) 1e6 + ny))
                    continue;
                // 已經找到終點
                if (isArrive(is_source, nx, ny))
                    return true;
                // 放入隊列中，並在vis中標記
                d.add(nx * (long) 1e6 + ny);
                vis.add(nx * (long) 1e6 + ny);
            }
        }
        // 判斷訪問過點的數量
        return vis.size() > MaxPoint;
    }

    // 如果從起點出發，那麼中途到達終點；
    // 如果從終點出發，那麼中途到達起點；
    boolean isArrive(boolean is_source, long x, long y) {
        if (is_source && x == target[0] && y == target[1])
            return true;

        if ((!is_source) && x == source[0] && y == source[1])
            return true;

        return false;
    }


}
