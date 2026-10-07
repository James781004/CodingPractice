package EndlessCheng.GenreMenu.DP.JumpGame;

import java.util.*;

public class MinimumJumps {

    // https://leetcode.cn/problems/minimum-jumps-to-reach-home/solutions/2414842/dao-jia-de-zui-shao-tiao-yue-ci-shu-by-l-sza1/
    // 相當於在queue初始化後，取出一次跳躍的結果，
    // 將其下一次跳躍的結果(前進,根據情況判斷此時是否可以後退)加入隊列中
    // 然後循環往復，直到 到達x點 || 隊列為空
    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        Queue<int[]> queue = new ArrayDeque<int[]>();
        // 標記可以到達的點, 正數表示前進到達，負數表示後退到達
        Set<Integer> visited = new HashSet<Integer>();
        // 坐標，方向(1為前進, -1為後退)，步數
        queue.offer(new int[]{0, 1, 0});
        visited.add(0);
        // 定義左右邊界 右邊界為 max(f + a + b, x + b)
        int lower = 0, upper = Math.max(Arrays.stream(forbidden).max().getAsInt() + a, x) + b;
        Set<Integer> forbiddenSet = new HashSet<Integer>();
        for (int position : forbidden) {
            forbiddenSet.add(position);
        }
        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            int position = arr[0], direction = arr[1], step = arr[2];
            // 坐標等於x，成功到達目標點
            if (position == x) {
                return step;
            }
            int nextDirection = 1; // 如果下一次方向為前進
            int nextPosition = position + a; // 下一次前進後的坐標
            // 沒有超出邊界 && 沒有前進到達過這個位置 && 這個坐標不是不可到達點
            if (lower <= nextPosition && nextPosition <= upper && !visited.contains(nextPosition * nextDirection) && !forbiddenSet.contains(nextPosition)) {
                // visited 標記這個點(nextPosition)可以前進到達
                visited.add(nextPosition * nextDirection);
                // 在隊列中加入前進後到達的點
                queue.offer(new int[]{nextPosition, nextDirection, step + 1});
            }
            // 如果本次方向是前進，假設下一次是後退
            if (direction == 1) {
                nextPosition = position - b;
                nextDirection = -1;
                // 沒有超出邊界 && 沒有後退到達過這個點 && 這個點不是不可到達點
                if (lower <= nextPosition && nextPosition <= upper && !visited.contains(nextPosition * nextDirection) && !forbiddenSet.contains(nextPosition)) {
                    // visited標記 且 加入queue中
                    visited.add(nextPosition * nextDirection);
                    queue.offer(new int[]{nextPosition, nextDirection, step + 1});
                }
            }
        }
        return -1;
    }
}
