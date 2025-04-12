package EndlessCheng.GenreMenu.Graph.BFS;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class MaxCandies {

    // https://leetcode.cn/problems/maximum-candies-you-can-get-from-boxes/solutions/2803028/duan-xiao-jing-han-de-bfsfang-fa-by-wryy-qe3x/
    public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        // 要拿到盒子i中的糖果，需要有：1.盒子i 2.鑰匙 or status[i] == 1
        int res = 0, n = status.length;
        // 隊列中存儲盒子的id
        Queue<Integer> queue = new LinkedList<>();
        // 記錄有沒有拿走盒子中的糖果
        boolean[] visited = new boolean[n];
        // 記錄已擁有的盒子
        Set<Integer> set = new HashSet<>();
        for (int x : initialBoxes) {
            queue.offer(x);
            set.add(x);
        }
        while (!queue.isEmpty()) {
            int box = queue.poll();
            // 如果沒有拿走box中的物品，並且box是可以打開的
            if (!visited[box] && status[box] == 1) {
                // 拿走糖果
                res += candies[box];
                visited[box] = true;
                // 將box中的盒子加入隊列
                for (int x : containedBoxes[box]) queue.offer(x);
                for (int x : keys[box]) {
                    // 拿到盒子x的鑰匙，那麽將狀態位置1，表示盒子x是可以打開的
                    status[x] = 1;
                    // 如果沒有拿走盒子x中的物品並且我們擁有這個盒子，
                    // 說明之前拿到了這個盒子，但是沒有鑰匙，不能打開這個盒子，
                    // 那麽我們需要將這個盒子加入隊列
                    if (!visited[x] && set.contains(x))
                        queue.offer(x);
                }
            } else {
                // 即使box不能打開，但是後面的盒子里可能有box的鑰匙，所以先加入到“已擁有的盒子”集合中
                set.add(box);
            }
        }
        return res;
    }


}
