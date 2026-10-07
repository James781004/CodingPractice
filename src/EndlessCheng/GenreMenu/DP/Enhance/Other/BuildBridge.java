package EndlessCheng.GenreMenu.DP.Enhance.Other;

import java.util.Collections;
import java.util.PriorityQueue;

public class BuildBridge {

    // https://leetcode.cn/problems/NfY1m5/solutions/1876087/python-slopetrickkuang-jia-by-981377660l-c70y/
    public long buildBridge(int num, int[][] wood) {
        int n = wood.length;
        long[] length = new long[n];
        for (int i = 0; i < n; i++) {
            length[i] = (long) wood[i][1] - wood[i][0];
        }

        SlopeTrick S = new SlopeTrick();

        for (int i = 0; i < n; i++) {
            long left = wood[i][0];

            // 1. 左拐點向左平移 length[i]
            S.addLeftOffset(-length[i]);

            // 2. 右拐點向右平移 length[i - 1] (從第 2 塊木板開始)
            if (i > 0) {
                S.addRightOffset(length[i - 1]);
            }

            // 3. 加上絕對值代價函數 |x - left|
            S.addAbsXMinusA(left);
        }

        return S.getMinY();
    }

    // ----------------- Slope Trick 數據結構 -----------------
    private static class SlopeTrick {

        private long minY = 0; // 記錄當前 DP 最小值

        // 大頂堆：維護左側斜率變化點（拐點）
        private final PriorityQueue<Long> leftTuring = new PriorityQueue<>(
                Collections.reverseOrder()
        );

        // 小頂堆：維護右側斜率變化點（拐點）
        private final PriorityQueue<Long> rightTuring = new PriorityQueue<>();

        private long leftOffset = 0;  // 左拐點的整體平移量
        private long rightOffset = 0; // 右拐點的整體平移量

        public SlopeTrick() {
            // 初始化哨兵
            leftTuring.add(Long.MIN_VALUE / 2);
            rightTuring.add(Long.MAX_VALUE / 2);
        }

        // 加上 |x - a| 相當於同時加上 (x - a)+ 與 (a - x)+
        public void addAbsXMinusA(long a) {
            addXMinusA(a);
            addAMinusX(a);
        }

        // 加上 (x - a)+
        public void addXMinusA(long a) {
            if (!leftTuring.isEmpty()) {
                minY += Math.max(0L, getLeftTop() - a);
            }
            pushLeft(a);
            pushRight(popLeft());
        }

        // 加上 (a - x)+
        public void addAMinusX(long a) {
            if (!rightTuring.isEmpty()) {
                minY += Math.max(0L, a - getRightTop());
            }
            pushRight(a);
            pushLeft(popRight());
        }

        public void addLeftOffset(long delta) {
            leftOffset += delta;
        }

        public void addRightOffset(long delta) {
            rightOffset += delta;
        }

        public long getMinY() {
            return minY;
        }

        private void pushLeft(long a) {
            leftTuring.add(a - leftOffset);
        }

        private void pushRight(long a) {
            rightTuring.add(a - rightOffset);
        }

        private long popLeft() {
            return leftTuring.poll() + leftOffset;
        }

        private long popRight() {
            return rightTuring.poll() + rightOffset;
        }

        private long getLeftTop() {
            return leftTuring.peek() + leftOffset;
        }

        private long getRightTop() {
            return rightTuring.peek() + rightOffset;
        }
    }

}
