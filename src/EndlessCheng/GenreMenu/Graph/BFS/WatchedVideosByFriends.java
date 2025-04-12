package EndlessCheng.GenreMenu.Graph.BFS;

import java.util.*;

public class WatchedVideosByFriends {

    // https://leetcode.cn/problems/get-watched-videos-by-your-friends/solutions/1869184/by-stormsunshine-cutu/
    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        int n = watchedVideos.size();
        boolean[] visited = new boolean[n];
        visited[id] = true; // 初始時只有編號 id 狀態是已訪問，其余編號的狀態都是未訪問。
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(id); // 初始時，將編號 id 入隊列

        // 得到第 level 層的全部好友的做法是從編號 id 開始廣度優先搜索，
        // 每一輪遍歷同一層的全部好友，經過 level 輪遍歷之後即可得到第 level 層的好友。
        while (!queue.isEmpty() && level > 0) {
            level--;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();
                int[] currFriends = friends[curr];
                for (int friend : currFriends) {
                    if (!visited[friend]) {
                        visited[friend] = true;
                        queue.offer(friend);
                    }
                }
            }
        }

        Map<String, Integer> counts = new HashMap<>();

        // 經過 level 輪遍歷之後，隊列內的元素為第 level 層的全部好友編號。
        // 得到第 level 層的全部好友之後，遍歷每個好友並獲得該好友觀看的視頻，
        // 使用哈希表記錄第 level 層的全部視頻的觀看頻率，
        // 最後將視頻按頻率升序和字典序升序的順序排序並返回。
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            List<String> currVideos = watchedVideos.get(curr);
            for (String video : currVideos) {
                counts.put(video, counts.getOrDefault(video, 0) + 1);
            }
        }
        List<String> videosByFriends = new ArrayList<>(counts.keySet());
        Collections.sort(videosByFriends, (a, b) -> {
            int count1 = counts.get(a), count2 = counts.get(b);
            if (count1 != count2) {
                return count1 - count2;
            } else {
                return a.compareTo(b);
            }
        });
        return videosByFriends;
    }


}
