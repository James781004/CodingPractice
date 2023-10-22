package Grind.Ch05_Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Q10_MinimumHeightTrees {
    // https://leetcode.cn/problems/minimum-height-trees/solutions/242910/zui-rong-yi-li-jie-de-bfsfen-xi-jian-dan-zhu-shi-x/
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> res = new ArrayList<>();
        /*如果只有一個節點，那麼他就是最小高度樹*/
        if (n == 1) {
            res.add(0);
            return res;
        }

        /*建立各個節點的出度表*/
        int[] degree = new int[n];

        /*建立圖關系，在每個節點的list中存儲相連節點*/
        List<List<Integer>> map = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            map.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            degree[edge[0]]++;
            degree[edge[1]]++;/*出度++*/
            map.get(edge[0]).add(edge[1]);/*添加相鄰節點*/
            map.get(edge[1]).add(edge[0]);
        }
        /*建立隊列*/
        Queue<Integer> queue = new LinkedList<>();
        /*把所有出度為1的節點，也就是葉子節點入隊*/
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) queue.offer(i);
        }
        /*循環條件當然是經典的不空判斷*/
        while (!queue.isEmpty()) {
            res = new ArrayList<>();/*這個地方注意，我們每層循環都要new一個新的結果集合，
            這樣最後保存的就是最終的最小高度樹了*/
            int size = queue.size();/*這是每一層的節點的數量*/
            for (int i = 0; i < size; i++) {
                int cur = queue.poll();
                res.add(cur);

                /*
                把當前節點加入結果集，不要有疑問，為什麼當前只是葉子節點為什麼要加入結果集呢?
                因為我們每次循環都會新建一個list，所以最後保存的就是最後一個狀態下的葉子節點，
                這也是很多題解裡面所說的剪掉葉子節點的部分，你可以想象一下圖，每層遍歷完，
                都會把該層（也就是葉子節點層）這一層從隊列中移除掉，
                不就相當於把原來的圖給剪掉一圈葉子節點，形成一個縮小的新的圖嗎
                */

                List<Integer> neighbors = map.get(cur);
                /*這裡就是經典的bfs了，把當前節點的相鄰接點都拿出來，
                 * 把它們的出度都減1，因為當前節點已經不存在了，所以，
                 * 它的相鄰節點們就有可能變成葉子節點*/
                for (int neighbor : neighbors) {
                    degree[neighbor]--;
                    if (degree[neighbor] == 1) {
                        /*如果是葉子節點我們就入隊*/
                        queue.offer(neighbor);
                    }
                }
            }
        }
        
        return res;/*返回最後一次保存的list*/
    }
}
