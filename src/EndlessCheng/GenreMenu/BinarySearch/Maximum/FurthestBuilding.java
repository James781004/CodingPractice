package EndlessCheng.GenreMenu.BinarySearch.Maximum;

import java.util.*;

public class FurthestBuilding {

    // https://leetcode.cn/problems/furthest-building-you-can-reach/solutions/469774/javasan-chong-jie-fa-yu-shi-jian-fu-za-du-fen-xi-b/
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        int n = heights.length, sum = 0;
        Queue<Integer> queue = new PriorityQueue<>();
        for (int i = 1; i < heights.length; i++) {
            int diff = heights[i] - heights[i - 1];

            // 優先使用梯子，梯子不夠時選取差值最小的出堆改用磚頭。(小根堆)
            // 優先使用磚頭，磚頭不夠時選取消耗最大的出堆改用梯子。(大根堆)
            if (diff > 0) {
                queue.offer(diff);
                if (queue.size() > ladders) {
                    sum += queue.poll();
                }
                if (sum > bricks)
                    return i - 1;
            }
        }
        return n - 1;
    }


    public int furthestBuilding2(int[] heights, int bricks, int ladders) {
        int l = ladders, r = heights.length - 1;
        while (l <= r) {
            int mid = l + r >>> 1;
            if (check(heights, bricks, ladders, mid))
                l = mid + 1;
            else
                r = mid - 1;
        }
        return r;
    }

    private boolean check(int[] heights, int bricks, int ladders, int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (heights[i + 1] - heights[i] > 0) {
                list.add(heights[i + 1] - heights[i]);
            }
        }
        Collections.sort(list);
        int sum = 0;

        // 優先使用梯子，梯子不夠時使用磚頭
        for (int i = 0; i < list.size() - ladders; i++) {
            sum += list.get(i);
            if (sum > bricks)
                return false;
        }
        return true;
    }


}
