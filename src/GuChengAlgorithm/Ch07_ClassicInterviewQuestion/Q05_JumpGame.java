package GuChengAlgorithm.Ch07_ClassicInterviewQuestion;

import java.util.*;

public class Q05_JumpGame {
    // https://docs.google.com/presentation/d/1HuFIst_Q09J5jack9kBRMh-sq2059by9mBLWQuXF4ng/edit#slide=id.ge0affcbd54_0_24
    class JumpGame {
        Integer[] memo;

        public boolean canJumpMemo(int[] nums) {
            memo = new Integer[nums.length];
            memo[memo.length - 1] = 1;
            return helper(0, nums) == 1;
        }

        private int helper(int pos, int[] nums) {
            if (memo[pos] != null) return memo[pos];
            int furthest = Math.min(pos + nums[pos], nums.length - 1);
            for (int next = pos + 1; next <= furthest; next++) {
                if (helper(next, nums) == 1) return memo[pos] = 1;
            }
            return memo[pos] = -1;
        }


        public boolean canJump(int[] nums) {
            int max = 0;
            for (int i = 0; i < nums.length - 1; i++) {
                if (i <= max) max = Math.max(max, i + nums[i]);
            }
            return max >= nums.length - 1;
        }

        public boolean canJump2(int[] nums) {
            int lastPos = nums.length - 1;
            for (int i = nums.length - 1; i >= 0; i--) {
                if (i + nums[i] >= lastPos) lastPos = i;
            }
            return lastPos == 0;
        }
    }


    // https://docs.google.com/presentation/d/1HuFIst_Q09J5jack9kBRMh-sq2059by9mBLWQuXF4ng/edit#slide=id.ge0affcbd54_0_71
    class JumpGameII {
        public int jumpDP(int[] nums) {
            int[] dp = new int[nums.length];
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[0] = 0;
            for (int i = 1; i < nums.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (nums[j] + j >= i) dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
            return dp[dp.length - 1];
        }

        public int jump(int[] nums) {
            int jumps = 0, curEnd = 0, curFurthest = 0;
            for (int i = 0; i < nums.length - 1; i++) {
                curFurthest = Math.max(curFurthest, i + nums[1]);
                if (i == curEnd) {
                    jumps++;
                    curEnd = curFurthest;
                }
            }
            return jumps;
        }
    }


    // https://docs.google.com/presentation/d/1HuFIst_Q09J5jack9kBRMh-sq2059by9mBLWQuXF4ng/edit#slide=id.ge0affcbd54_0_95
    class JumpGameIII {
        public boolean canReach(int[] arr, int start) {
            if (start < 0 || start >= arr.length || arr[start] < 0) return false;
            if (arr[start] == 0) return true;
            arr[start] *= -1;  // visit過的點標記為負數
            return canReach(arr, start + arr[start]) || canReach(arr, start - arr[start]);
        }

        public boolean canReach2(int[] arr, int start) {
            int n = arr.length;
            Queue<Integer> q = new LinkedList<>();
            q.add(start);
            while (!q.isEmpty()) {
                int cur = q.poll();
                if (arr[cur] == 0) return true;  // reached 0
                if (arr[cur] < 0) continue;  // visited nodes

                // check available next steps
                if (n + arr[cur] < n) q.offer(cur + arr[cur]);   // go right
                if (n - arr[cur] >= 0) q.offer(cur - arr[cur]);  // go left

                // mark as visited
                arr[cur] = -arr[cur];
            }
            return false;
        }
    }


    // https://docs.google.com/presentation/d/1HuFIst_Q09J5jack9kBRMh-sq2059by9mBLWQuXF4ng/edit#slide=id.ge224544f8a_0_0
    class JumpGameIV {
        // 直接模擬這個過程，把下一個能跳到的地方，同value或者左右鄰居放入queue即可
        public int minJumps(int[] arr) {
            int N = arr.length;
            Map<Integer, List<Integer>> valueToIndex = new HashMap<>();
            for (int i = 0; i < N; i++) {
                valueToIndex.computeIfAbsent(arr[i], v -> new LinkedList<>()).add(i);
            }

            Set<Integer> visited = new HashSet<>();
            visited.add(0);

            Queue<Integer> q = new LinkedList<>();
            q.offer(0);
            int step = 0;

            // BFS
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    int cur = q.poll();
                    if (cur == N - 1) return step;  // reached the last index
                    List<Integer> neighbor = valueToIndex.get(arr[cur]);  // indexes with same value
                    neighbor.add(cur - 1); // left neighbor
                    neighbor.add(cur + 1); // right neighbor
                    for (int next : neighbor) {
                        if (next >= 0 && next < N && visited.add(next)) q.offer(next);
                    }
                    valueToIndex.get(arr[cur]).clear();  // avoid duplicated
                }
                step++;
            }
            return 0;
        }


        public int minJumps2(int[] arr) {
            int N = arr.length;
            if (N <= 1) return 0;
            Map<Integer, List<Integer>> valueToIndex = new HashMap<>();
            for (int i = 0; i < N; i++) {
                valueToIndex.computeIfAbsent(arr[i], v -> new LinkedList<>()).add(i);
            }

            Set<Integer> visited = new HashSet<>();
            visited.add(0);
            visited.add(N - 1);

            Set<Integer> head = new HashSet<>();
            Set<Integer> tail = new HashSet<>();
            head.add(0);
            tail.add(N - 1);
            int step = 0;

            while (!head.isEmpty()) {
                if (head.size() > tail.size()) {
                    Set<Integer> tmp = head;
                    head = tail;
                    tail = tmp;
                }

                Set<Integer> next = new HashSet<>();

                for (int cur : head) {
                    // 跳到同樣的value上
                    for (int z : valueToIndex.get(arr[cur])) {
                        if (tail.contains(z)) return step + 1;
                        if (visited.add(z)) next.add(z);
                    }

                    // avoid duplicated
                    valueToIndex.get(arr[cur]).clear();

                    // 跳到鄰居左右兩個位置
                    if (tail.contains(cur + 1) || tail.contains(cur - 1)) return step + 1;

                    int x = cur + 1, y = cur - 1;
                    if (x < N && visited.add(x)) next.add(x);
                    if (y >= 0 && visited.add(y)) next.add(y);
                }
                step++;
                head = next;
            }

            return -1;
        }
    }


    // https://docs.google.com/presentation/d/1HuFIst_Q09J5jack9kBRMh-sq2059by9mBLWQuXF4ng/edit#slide=id.ge224544f8a_0_30
    class JumpGameV {
        Integer[] memo;
        int[] arr;
        int n, d;

        // Use dynamic programming. dp[i] is max jumps you can do starting from index i. Answer is max(dp[i]).
        // dp[i] = 1 + max (dp[j]) where j is all indices you can reach from i.
        public int maxJumps(int[] arr, int d) {
            this.n = arr.length;
            this.d = d;
            this.memo = new Integer[n];
            this.arr = arr;
            int res = 1;
            for (int i = 0; i < n; i++) {
                res = Math.max(res, 1 + dfs(i));
            }
            return res;
        }

        private int dfs(int cur) {
            if (memo[cur] != n) return memo[cur];
            int res = 1;

            // valid condition: jump to the lower position between arr[0] and arr[n - 1]
            // try right
            for (int i = cur + 1; i <= Math.min(cur + d, n - 1) && arr[i] < arr[cur]; i++) {
                res = Math.max(res, 1 + dfs(i));
            }

            // try left
            for (int i = cur - 1; i >= Math.max(cur - d, 0) && arr[i] < arr[cur]; i--) {
                res = Math.max(res, 1 + dfs(i));
            }
            return memo[cur] = res;
        }
    }


    // https://docs.google.com/presentation/d/1HuFIst_Q09J5jack9kBRMh-sq2059by9mBLWQuXF4ng/edit#slide=id.ge224544fc6_0_2
    class JumpGameVI {
        public int maxResult(int[] nums, int k) {
            int N = nums.length;
            int[] score = new int[N]; // the max score we can get ending at index i
            score[0] = nums[0];
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);  // max heap
            pq.add(new int[]{nums[0], 0}); // [maxSumAtI, index]
            for (int i = 1; i < N; i++) {
                while (i - pq.peek()[i] > k) pq.remove();  // window size
                score[i] = nums[i] + score[pq.peek()[i]];
                pq.add(new int[]{score[i], i});
            }
            return score[N - 1];
        }

        public int maxResult2(int[] nums, int k) {
            int N = nums.length;
            int[] dp = new int[N]; // dp[i] represent the max score we can get ending at index i
            dp[0] = nums[0];
            Deque<Integer> q = new ArrayDeque<>();
            q.offerLast(0);
            for (int i = 0; i < N - 1; i++) {
                while (!q.isEmpty() && i - q.peekFirst() >= k) q.pollFirst();  // 左出：保持窗口大小
                while (!q.isEmpty() && dp[q.peekLast()] <= dp[i]) q.pollLast();  // 右進：保持stack單調性(這邊是大到小)
                q.offerLast(i);
                dp[i + 1] = dp[q.pollFirst()] + nums[i + 1];
            }
            return dp[N - 1];
        }
    }


    // https://docs.google.com/presentation/d/1HuFIst_Q09J5jack9kBRMh-sq2059by9mBLWQuXF4ng/edit#slide=id.ge224544fc6_0_30
    class JumpGameVII {
        public boolean canReach(String s, int minJump, int maxJump) {
            int n = s.length();
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(0);
            for (int i = 0; i < n && i != -1; i = s.indexOf("0", i + 1)) {  // i 每次跳到下一個0的位置
                while (!queue.isEmpty() && queue.peek() < i - maxJump) queue.poll();  // 隊頭元素index與當前位置差距大於maxJump，不符合條件
                if (!queue.isEmpty() && i - queue.peek() >= minJump) {  // 隊頭元素index與當前位置差距小於minJump，不符合條件
                    queue.offer(i);  // 可以抵達的position才放入queue
                    if (i == n - 1) return true;
                }
                if (queue.isEmpty()) return false;
            }
            return false;
        }
    }

    // hhttps://docs.google.com/presentation/d/1HuFIst_Q09J5jack9kBRMh-sq2059by9mBLWQuXF4ng/edit#slide=id.ge224544fc6_0_57
    class FrogJump {
        int[][] memo;

        public boolean canCross(int[] stones) {
            memo = new int[stones.length][stones.length];
            for (int[] row : memo) Arrays.fill(row, -1);
            return helper(stones, 0, 0) == 1;
        }

        private int helper(int[] stones, int index, int jumpSize) {
            if (memo[index][jumpSize] >= 0) return memo[index][jumpSize];
            for (int i = index + 1; i < stones.length; i++) {
                int gap = stones[i] - stones[index];  // 本次跳躍距離，至少會是1以上的距離
                if (gap >= jumpSize - 1 && gap <= jumpSize + 1) {  // 如果在前一次jumpSize-1 ~ jumpSize+1之間，就進入下次遞歸
                    if (helper(stones, i, gap) == 1) return memo[index][jumpSize] = 1;
                }
            }
            return memo[index][jumpSize] = (index == stones.length - 1) ? 1 : 0;
        }


        public boolean canCross2(int[] stones) {
            Map<Integer, Set<Integer>> map = new HashMap<>();
            for (int i = 0; i < stones.length; i++) {
                map.put(stones[i], new HashSet<>());
            }

            map.get(stones[0]).add(1); // <stoneValue, LastJumps>
            int last = stones[stones.length - 1];

            for (int i = 0; i < stones.length; i++) {
                for (int step : map.get(stones[i])) {  // 本次可以跳的距離set(應該會包含之前跳躍距離的step-1, step, step+1)
                    int temp = step + stones[i];    // 可能的下一個位置
                    if (temp == last) return true;  // 抵達終點return true
                    if (map.get(temp) != null) {    // 下一個位置存在，把本次跳躍距離step, step-1, step+1都放進下一個位置的set
                        map.get(temp).add(step);
                        map.get(temp).add(step + 1);
                        if (step - 1 > 0) map.get(temp).add(step - 1);  // step-1是0就不要放，因為無意義又會影響後續計算
                    }
                }
            }
            return false;
        }
    }
}
