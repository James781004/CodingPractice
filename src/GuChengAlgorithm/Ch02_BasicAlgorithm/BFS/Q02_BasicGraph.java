package GuChengAlgorithm.Ch02_BasicAlgorithm.BFS;

import java.util.*;

public class Q02_BasicGraph {
    // https://docs.google.com/presentation/d/1R8rHF7l3C5DEOI0GTwSwSzkmyRmscu1KwjVIVpL4tgQ/edit#slide=id.g99a2da5d10_4_29
    // LC 127
    public int wordLadderLength(String begin, String end, List<String> wordList) {
        Set<String> visited = new HashSet<>(wordList);
        Queue<String> q = new LinkedList<>();
        q.offer(begin);
        int step = 1, N = begin.length();
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                String cur = q.poll();
                if (cur.equals(end)) return step;
                for (int j = 0; j < N; j++) {
                    for (char letter = 'a'; letter <= 'z'; letter++) { // 每個位置字母替換成其他25個字母
                        StringBuilder next = new StringBuilder(cur);
                        next.setCharAt(j, letter);
                        String nextWord = next.toString();
                        if (visited.contains(nextWord)) { // wordList裡面有，nextWord才可以作為選項繼續往下走
                            if (nextWord.equals(end)) return step + 1;
                            visited.remove(nextWord); // 使用完畢後必須移除，否則就循環了
                            q.offer(nextWord);
                        }
                    }
                }
            }
            step++;
        }
        return 0;
    }


    // 雙向BFS
    public int wordLadderLength2(String begin, String end, List<String> wordList) {
        Set<String> beginSet = new HashSet<>(), endSet = new HashSet<>();
        Set<String> wordSet = new HashSet<>(wordList);
        Set<String> visited = new HashSet<>();
        if (wordSet.contains(end)) return 0;
        int step = 1, N = begin.length();
        beginSet.add(begin);
        endSet.add(end);
        while (!begin.isEmpty() && !endSet.isEmpty()) {
            Set<String> nextSet = new HashSet<>();
            for (String word : beginSet) {
                char[] chs = word.toCharArray();
                for (int i = 0; i < N; i++) {
                    for (char c = 'a'; c <= 'z'; c++) {  // 暴力嘗試每個位置的字母
                        char pre = chs[i];
                        chs[i] = c;
                        String nextWord = new String(chs);
                        if (endSet.contains(nextWord)) return step + 1;

                        // wordSet裡面有，nextWord才可以作為選項繼續往下走
                        // 已經visited的nextWord也不能繼續往下走
                        if (visited.add(nextWord) && wordSet.contains(nextWord)) {
                            nextSet.add(nextWord); // 注意這邊nextWord是加入nextSet，而不是beginSet，因為下面會有重新指向操作
                            chs[i] = pre; // 進入下一個loop之前要還原字符串
                        }
                    }
                }
            }

            // 選擇較小的set進行展開，並且把beginSet重新指向較小的set
            if (endSet.size() < nextSet.size()) {
                beginSet = endSet;
                endSet = nextSet;
            } else {
                beginSet = nextSet;
            }
            step++;
        }
        return 0;
    }


    // https://docs.google.com/presentation/d/1R8rHF7l3C5DEOI0GTwSwSzkmyRmscu1KwjVIVpL4tgQ/edit#slide=id.gcabbc9c948_0_0
    // 雙向BFS
    public int openLock(String target, String[] deadEnds) {
        Set<String> beginSet = new HashSet<>(), endSet = new HashSet<>();
        Set<String> deads = new HashSet<>(Arrays.asList(deadEnds));
        beginSet.add("0000");
        endSet.add(target);
        int level = 0;
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            Set<String> temp = new HashSet<>();
            for (String s : beginSet) {
                if (endSet.contains(s)) return level;
                if (deads.contains(s)) continue;
                deads.add(s);
                StringBuilder sb = new StringBuilder(s);
                for (int i = 0; i < 4; i++) {
                    char c = sb.charAt(i);
                    String headPart = sb.substring(0, i), backPart = sb.substring(i + 1);
                    String s1 = headPart + (c == '9' ? 0 : c - '0' + 1) + backPart;
                    String s2 = headPart + (c == '0' ? 9 : c - '0' - 1) + backPart;
                    if (!deads.contains(s1)) temp.add(s1);  // 注意這邊是加入temp，下面會有重新指向操作
                    if (!deads.contains(s2)) temp.add(s2);
                }
            }
            level++;

            // 把beginSet重新指向endSet，endSet重新指向temp
            beginSet = endSet;
            endSet = temp;
        }
        return -1;
    }


    // https://docs.google.com/presentation/d/1R8rHF7l3C5DEOI0GTwSwSzkmyRmscu1KwjVIVpL4tgQ/edit#slide=id.g99a2da5d10_4_36
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        Queue<int[]> q = new LinkedList<>();
        q.add(start);
        visited[start[0]][start[1]] = true;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            if (cur[0] == destination[0] && cur[1] == destination[1]) return true;
            for (int[] dir : dirs) {
                int x = cur[0] + dir[0], y = cur[1] + dir[1];
                // 如果目前方位可以繼續走，就直走到越界為止
                while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                    x += dir[0];
                    y += dir[1];
                }

                // 上面while是到了越界才會中止，所以要把前一步多加的扣回來
                x -= dir[0];
                y -= dir[1];

                // 沒走過的點繼續加入queue
                if (!visited[x][y]) {
                    q.add(new int[]{x, y});
                    visited[x][y] = true;
                }
            }
        }
        return false;
    }


    // https://docs.google.com/presentation/d/1R8rHF7l3C5DEOI0GTwSwSzkmyRmscu1KwjVIVpL4tgQ/edit#slide=id.g99a2da5d10_4_75
    // 典型的SSSP單源最短路徑演算法，用於計算一個節點到其他所有節點的最短路徑。主要特點是以起始點為中心向外層層擴展，直到擴展到終點為止。
    int[][] dirs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        int[][] distance = new int[maze.length][maze[0].length]; // 存放起點到當前[x座標, y座標] 的距離
        for (int[] row : distance) Arrays.fill(row, Integer.MAX_VALUE);
        distance[start[0]][start[1]] = 0; // 起點距離0
        dijkstra(maze, start, distance);
        return distance[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : distance[destination[0]][destination[1]];
    }

    private void dijkstra(int[][] maze, int[] start, int[][] distance) {
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> (a[2] - b[2]));  // [x座標, y座標, 起點到當前xy的距離d]
        q.offer(new int[]{start[0], start[1], 0});
        while (!q.isEmpty()) {
            int[] cur = q.poll();

            // 暴力嘗試上下左右方位
            for (int[] dir : dirs) {
                int x = cur[0] + dir[0], y = cur[1] + dir[1], count = 0;

                // 如果目前方位可以繼續走，就直走到越界為止
                while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                    x += dir[0];
                    y += dir[1];
                    count++;
                }

                // 上面while是到了越界才會中止，所以要把前一步多加的扣回來
                x -= dir[0];
                y -= dir[1];

                // 如果找到了較短的距離，就更新distance距離紀錄並且加入pq
                if (distance[cur[0]][cur[1]] + count < distance[x][y]) {
                    distance[x][y] = distance[cur[0]][cur[1]] + count;
                    q.add(new int[]{x, y, distance[x][y]});
                }
            }
        }
    }

}
