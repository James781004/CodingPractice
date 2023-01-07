package GuChengAlgorithm.Ch06_Advanced.Algorithm;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class Q04_OfflineAlgorithm {
    // https://docs.google.com/presentation/d/155u4hAg5f8j2j6aTaLIBpyS-4RWi250-xhJ6fz16vm4/edit#slide=id.gdf68108926_0_11
    class KthLargest {
        PriorityQueue<Integer> pq;
        int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            pq = new PriorityQueue<>();
            for (int num : nums) add(num);
        }

        public int add(int val) {
            if (pq.size() < k || val > pq.peek()) pq.offer(val);
            if (pq.size() > k) pq.poll();
            return pq.peek();
        }

        public int findKthLargest(int[] nums, int k) {
            divide(nums, 0, nums.length - 1, k);
            return nums[nums.length - k];
        }

        private void divide(int[] nums, int left, int right, int k) {
            if (left >= right) return;
            int position = partition(nums, left, right);
            if (position == nums.length - k) return;
            else if (position < nums.length - k) divide(nums, position + 1, right, k);
            else divide(nums, left, position - 1, k);
        }

        private int partition(int[] nums, int left, int right) {
            int pivot = nums[right], wall = left;
            for (int i = left; i < right; i++) {
                if (nums[i] < pivot) {
                    swap(nums, i, wall);
                    wall++;
                }
            }
            swap(nums, wall, right);
            return wall;
        }

        private void swap(int[] nums, int a, int b) {
            int temp = nums[a];
            nums[a] = nums[b];
            nums[b] = temp;
        }
    }


    // https://docs.google.com/presentation/d/155u4hAg5f8j2j6aTaLIBpyS-4RWi250-xhJ6fz16vm4/edit#slide=id.gdf68108926_0_44
    public int[] minInterval(int[][] intervals, int[] queries) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int[][] q = new int[queries.length][2];  // add[index, query]
        for (int i = 0; i < queries.length; i++) {
            q[i] = new int[]{i, queries[i]};
        }
        Arrays.sort(q, (a, b) -> a[1] - b[1]); // sort queries by val
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> (a[1] - a[0]) - (b[1] - b[0]));
        int[] result = new int[queries.length];
        int j = 0;
        for (int i = 0; i < q.length; i++) {
            int index = q[i][0];
            int val = q[i][1];

            // Window check
            while (j < intervals.length && intervals[j][0] <= val) pq.offer(intervals[j++]);
            while (!pq.isEmpty() && pq.peek()[1] < val) pq.poll();
            result[index] = pq.isEmpty() ? -1 : pq.peek()[1] - pq.peek()[0] + 1;
        }
        return result;
    }


    // https://docs.google.com/presentation/d/155u4hAg5f8j2j6aTaLIBpyS-4RWi250-xhJ6fz16vm4/edit#slide=id.gdf68108926_0_55
    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        int M = edgeList.length, N = queries.length;
        DSU dsu = new DSU(n);
        for (int i = 0; i < queries.length; i++) {
            queries[i] = new int[]{queries[i][0], queries[i][1], queries[i][2], i};
        }
        Arrays.sort(queries, (a, b) -> a[2] - b[2]);
        Arrays.sort(edgeList, (a, b) -> a[2] - b[2]);
        boolean[] res = new boolean[N];

        for (int i = 0, j = 0; i < N; i++) {
            int[] query = queries[i];
            while (j < M && edgeList[j][2] < queries[i][2]) {  // 如果構造edge小於limit就創見
                dsu.union(edgeList[j][0], edgeList[j++][1]);
            }
            res[queries[i][3]] = dsu.find(queries[i][0]) == dsu.find(queries[i][1]);
        }
        return res;
    }

    class DSU {
        int[] parent;

        public DSU(int N) {
            this.parent = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        public void union(int x, int y) {
            parent[find(x)] = parent[find(y)];
        }
    }
}
