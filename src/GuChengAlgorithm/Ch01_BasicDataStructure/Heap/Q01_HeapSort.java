package GuChengAlgorithm.Ch01_BasicDataStructure.Heap;

public class Q01_HeapSort {

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
        int smallest = i;
        int left = 2 * i + 1;  // 左樹: 2n + 1
        int right = 2 * i + 2;  // 右樹: 2n + 2

        // max heap這邊要找尋最小值
        if (left < n && a[left] < a[smallest]) smallest = left;
        if (right < n && a[right] < a[smallest]) smallest = right;

        // 如果根節點i已經不是最小值，就更新最小值，然後遞歸操作子樹
        if (smallest != i) {
            int swap = a[i];
            a[i] = a[smallest];
            a[smallest] = swap;

            heapify(a, n, smallest);
        }
    }
}
