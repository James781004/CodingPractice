package GuChengAlgorithm.Ch01_BasicDataStructure.Heap;

public class Q00_Heapify {
//    https://www.lintcode.com/problem/130/
//    Given an integer array, heapify it into a min-heap array.
//
//    For a heap array A, A[0] is the root of heap, and for each A[i], A[i * 2 + 1] is the left child of A[i] and A[i * 2 + 2] is the right child of A[i].

    public void heapify(int[] a) {
        int n = a.length;
        for (int i = n / 2; i >= 0; i--) {
            heapify(a, n, i);
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
