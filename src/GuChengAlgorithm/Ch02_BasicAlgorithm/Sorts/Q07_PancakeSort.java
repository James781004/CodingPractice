package GuChengAlgorithm.Ch02_BasicAlgorithm.Sorts;

import java.util.ArrayList;
import java.util.List;

public class Q07_PancakeSort {
    // https://docs.google.com/presentation/d/179ocIZBRl1Tj34UkfEeodsIcfJYIv9-gMxazKYMgDLI/edit#slide=id.gbeca6ee93f_0_32

    // 每次翻轉前k個
    public List<Integer> pancakeSort(int[] A) {
        List<Integer> result = new ArrayList<>();
        int N = A.length, largest = N;
        for (int i = 0; i < N; i++) {
            int index = find(A, largest);
            flip(A, index);  // 目標位置調整到A[0]
            flip(A, largest - 1);  // 最大值位置調整到最後面正確位置
            result.add(index + 1);  // 前k個需要把index = 0 計算進去，所以+1
            result.add(largest--);  // 兩步res.add()只是記錄flip的位置
            // 因為之前是前index位被翻轉，最大值是A[0]，所以還是前index反轉到後面
        }
        return result;
    }

    private void flip(int[] a, int index) {
        int i = 0, j = index;
        while (i < j) {
            int tmp = a[i];
            a[i++] = a[j];
            a[j--] = tmp;
        }
    }

    private int find(int[] a, int largest) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == largest) return i;
        }
        return -1;
    }
}
