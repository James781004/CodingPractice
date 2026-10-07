package EndlessCheng.GenreMenu.DP.Divide.Fixed;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class MinOperations {

    // https://leetcode.cn/problems/minimum-operations-to-make-elements-within-k-subarrays-equal/solutions/3633641/hua-dong-chuang-kou-zhong-wei-shu-hua-fe-e9cn/
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] dis = medianSlidingWindow(nums, x);
        long[][] f = new long[k + 1][n + 1];
        for (int i = 1; i <= k; i++) {
            f[i][i * x - 1] = Long.MAX_VALUE;
            for (int j = i * x; j <= n - (k - i) * x; j++) { // 左右留出足夠空間給其他子數組
                f[i][j] = Math.min(f[i][j - 1], f[i - 1][j - x] + dis[j - x]); // j-x 為子數組左端點
            }
        }
        return f[k][n];
    }

    // 480. 滑動窗口中位數（有改動）
    // 返回 nums 的所有長為 k 的子數組的（到子數組中位數的）距離和
    private long[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        long[] ans = new long[n - k + 1];
        LazyHeap left = new LazyHeap((a, b) -> Integer.compare(b, a)); // 最大堆
        LazyHeap right = new LazyHeap(Integer::compare); // 最小堆

        for (int i = 0; i < n; i++) {
            // 1. 進入窗口
            int in = nums[i];
            if (left.size() == right.size()) {
                left.push(right.pushPop(in));
            } else {
                right.push(left.pushPop(in));
            }

            int l = i + 1 - k;
            if (l < 0) { // 窗口大小不足 k
                continue;
            }

            // 2. 計算答案
            long v = left.top();
            long s1 = v * left.size() - left.sum();
            long s2 = right.sum() - v * right.size();
            ans[l] = s1 + s2;

            // 3. 離開窗口
            int out = nums[l];
            if (out <= left.top()) {
                left.remove(out);
                if (left.size() < right.size()) {
                    left.push(right.pop()); // 平衡兩個堆的大小
                }
            } else {
                right.remove(out);
                if (left.size() > right.size() + 1) {
                    right.push(left.pop()); // 平衡兩個堆的大小
                }
            }
        }

        return ans;
    }


    static class LazyHeap extends PriorityQueue<Integer> {
        private final Map<Integer, Integer> removeCnt = new HashMap<>(); // 每個元素剩余需要刪除的次數
        private int size = 0; // 實際大小
        private long sum = 0; // 堆中元素總和

        public LazyHeap(Comparator<Integer> comparator) {
            super(comparator);
        }

        public int size() {
            return size;
        }

        public long sum() {
            return sum;
        }

        // 刪除
        public void remove(int x) {
            removeCnt.merge(x, 1, Integer::sum); // 懶刪除
            size--;
            sum -= x;
        }

        // 正式執行刪除操作
        private void applyRemove() {
            while (removeCnt.getOrDefault(peek(), 0) > 0) {
                removeCnt.merge(poll(), -1, Integer::sum);
            }
        }

        // 查看堆頂
        public int top() {
            applyRemove();
            return peek();
        }

        // 出堆
        public int pop() {
            applyRemove();
            size--;
            sum -= peek();
            return poll();
        }

        // 入堆
        public void push(int x) {
            int c = removeCnt.getOrDefault(x, 0);
            if (c > 0) {
                removeCnt.put(x, c - 1); // 抵消之前的刪除
            } else {
                offer(x);
            }
            size++;
            sum += x;
        }

        // push(x) 然後 pop()
        public int pushPop(int x) {
            applyRemove();
            sum += x;
            offer(x);
            sum -= peek();
            return poll();
        }
    }

}
