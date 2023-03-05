package GuChengAlgorithm.Ch09_InterviewPack;

import java.util.*;

public class Amazon01 {
    // https://docs.google.com/presentation/d/13WtL1-P4dZaS6ci36nZsErg1bQGe5J1lydeJqdR6Zmg/edit#slide=id.g13a63fca717_0_15
    class SnakeGame {
        LinkedList<Position> snake;
        int[][] food;
        int foodCount;
        int width;
        int height;

        public SnakeGame(int width, int height, int[][] food) {
            this.width = width;
            this.height = height;
            this.food = food;
            this.foodCount = 0;
            this.snake = new LinkedList<>();
            snake.add(new Position(0, 0));
        }

        public int move(String direction) {
            Position head = snake.getFirst();
            Position newHead = new Position(head.x, head.y);  // 貪食蛇遊戲，前進時顯示上都是長出一個新頭，然後尾巴縮短
            Position tail = snake.removeLast();  // 先斷尾後長頭，新頭可以和原本斷尾尾巴位置重合，不算違規
            if (direction.equals("U")) newHead.x--;
            else if (direction.equals("L")) newHead.y--;
            else if (direction.equals("R")) newHead.y++;
            else newHead.x++;

            // 如果提前出界或者咬到自己就結束，注意提前斷尾
            if (newHead.x < 0 || newHead.x == height || newHead.y < 0 || newHead.y == width || snake.contains(newHead)) {
                return -1;
            }
            snake.addFirst(newHead);  // 長出一個新頭

            // food還沒吃完，如果新頭咬到food(新頭x, y座標跟food重合)，尾部就加回來
            if (foodCount < food.length && food[foodCount][0] == newHead.x && food[foodCount][1] == newHead.y) {
                snake.addLast(tail);
                foodCount++;
            }
            return foodCount;
        }
    }


    class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y;
        }
    }


    // https://docs.google.com/presentation/d/13WtL1-P4dZaS6ci36nZsErg1bQGe5J1lydeJqdR6Zmg/edit#slide=id.g13a63fca717_0_34
    class CourseScheduleII {
        // BFS
        public int[] findOrderBFS(int numCourses, int[][] prerequisites) {
            int[] res = new int[numCourses];
            int[] indegree = new int[numCourses];
            List<Integer>[] graph = new ArrayList[numCourses];

            // 初始化graph
            for (int i = 0; i < numCourses; i++) {
                graph[i] = new ArrayList<>();
            }

            // 建立單向圖，計算入度
            for (int i = 0; i < prerequisites.length; i++) {
                graph[prerequisites[i][1]].add(prerequisites[i][0]);
                indegree[prerequisites[i][0]]++;
            }

            // 找尋單向圖入口(入度0)
            Queue<Integer> q = new LinkedList<>();
            for (int i = 0; i < indegree.length; i++) {
                if (indegree[i] == 0) q.add(i);
            }

            // 拓撲排序
            int count = 0;
            while (!q.isEmpty()) {
                int cur = q.poll();
                res[count++] = cur;
                for (int next : graph[cur]) {
                    if (--indegree[next] == 0) q.offer(next);
                }
            }
            return count == numCourses ? res : new int[0];
        }


        // DFS
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] visited;
        boolean isValid = true;
        int[] res;
        int index;

        public int[] findOrderDFS(int numCourses, int[][] prerequisites) {
            int N = numCourses;
            res = new int[N];
            visited = new int[N];

            // 建立單向圖
            for (int[] edge : prerequisites) {
                graph.computeIfAbsent(edge[0], x -> new ArrayList<>()).add(edge[1]);
            }

            // 找尋沒有訪問過的節點開始DFS(0: 未訪問, 1: 訪問中, 2: 已訪問)
            for (int i = 0; i < N; i++) {
                if (visited[i] == 0) dfs(i);
            }
            return isValid ? res : new int[0];
        }

        private void dfs(int node) {
            visited[node] = 1;  // 三色法
            for (int next : graph.getOrDefault(node, new ArrayList<>())) {
                if (visited[next] == 0) dfs(next);
                else if (visited[next] == 1) isValid = false;
            }
            res[index++] = node;
            visited[node] = 2;
        }
    }


    // https://docs.google.com/presentation/d/13WtL1-P4dZaS6ci36nZsErg1bQGe5J1lydeJqdR6Zmg/edit#slide=id.g13a63fca717_0_45
    public int countPrime(int n) {
        if (n == 2) return 0;

        boolean[] numbers = new boolean[n];
        for (int p = 2; p <= (int) Math.sqrt(n); p++) {
            if (numbers[p] == false) {
                for (int j = p * p; j < n; j += p) {
                    numbers[j] = true;
                }
            }
        }

        int res = 0;
        for (int i = 2; i < n; i++) {
            if (numbers[1] == false) res++;
        }
        return res;
    }
}
