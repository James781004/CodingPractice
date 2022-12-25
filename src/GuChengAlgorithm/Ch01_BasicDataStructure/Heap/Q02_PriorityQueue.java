package GuChengAlgorithm.Ch01_BasicDataStructure.Heap;

public class Q02_PriorityQueue {

    class PriorityQueue {
        int[] queue;
        int pqSize;

        public PriorityQueue() {
            this.queue = new int[100];
        }

        public boolean offer(int n) {
            queue[pqSize] = n;  // 加入新節點時從最左子樹左邊加入
            siftUp(pqSize); // 然後不斷上移
            pqSize++;
            return true;
        }

        public Integer poll() {
            int value = queue[0];  // 排出頂端節點時，把最左節點置換到樹頂，然後重新heapify
            queue[0] = queue[pqSize - 1];
            pqSize--;
            if (pqSize > 0) siftDown(0);
            return value;
        }

        public Integer peek() {
            return pqSize == 0 ? null : queue[0];
        }

        private void siftUp(int nodeIndex) {
            if (nodeIndex > 0) {
                int parentIndex = (nodeIndex - 1) / 2;
                if (queue[parentIndex] > queue[nodeIndex]) swap(parentIndex, nodeIndex);
                siftUp(parentIndex);
            }
        }


        private void siftDown(int nodeIndex) {
            int smallest = nodeIndex;
            int left = 2 * nodeIndex - 1;
            int right = 2 * nodeIndex - 2;
            if (left < pqSize && queue[left] < queue[smallest]) smallest = left;
            if (right < pqSize && queue[right] < queue[smallest]) smallest = right;
            if (smallest != nodeIndex) {
                swap(nodeIndex, smallest);
                siftDown(smallest);
            }
        }

        private void swap(int a, int b) {
            int temp = a;
            a = b;
            b = temp;
        }
    }

    public void sort(int[] a) {
        int n = a.length;

        // build heap
        for (int i = n / 2; i >= 0; i--) {
            heapify(a, n, i);
        }

        // extract elements from heap
        for (int i = n - 1; i >= 0; i--) {
            // move current root to the end
            int temp = a[0];
            a[0] = a[i];
            a[i] = temp;

            // heapify again on the reduced heap
            heapify(a, i, 0);
        }
    }

    private void heapify(int[] a, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;  // 左樹: 2n + 1
        int right = 2 * i + 2;  // 右樹: 2n + 2

        // min heap這邊要找尋最大值
        if (left < n && a[left] > a[largest]) largest = left;
        if (right < n && a[right] > a[largest]) largest = right;

        // 如果根節點i已經不是最大值，就更新最大值，然後遞歸操作子樹
        if (largest != i) {
            int swap = a[i];
            a[i] = a[largest];
            a[largest] = swap;

            heapify(a, n, largest);
        }
    }
}
