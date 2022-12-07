package FuckingAlgorithm.Extra;

import java.util.LinkedList;
import java.util.List;

public class Q04_PancakeSorting {
//    https://leetcode.cn/problems/pancake-sorting/
//    969. 煎餅排序
//    給你一個整數數組 arr ，請使用 煎餅翻轉 完成對數組的排序。
//
//    一次煎餅翻轉的執行過程如下：
//
//    選擇一個整數 k ，1 <= k <= arr.length
//    反轉子數組 arr[0...k-1]（下標從 0 開始）
//    例如，arr = [3,2,1,4] ，選擇 k = 3 進行一次煎餅翻轉，反轉子數組 [3,2,1] ，得到 arr = [1,2,3,4] 。
//
//    以數組形式返回能使 arr 有序的煎餅翻轉操作所對應的 k 值序列。任何將數組排序且翻轉次數在 10 * arr.length 范圍內的有效答案都將被判斷為正確。

    // 記錄反轉操作序列
    LinkedList<Integer> res = new LinkedList<>();

    public List<Integer> pancakeSort(int[] cakes) {
        sort(cakes, cakes.length);
        return res;
    }

    private void sort(int[] cakes, int n) {
        // base case
        if (n == 1) return;

        // 尋找最大餅的索引
        int maxCake = 0;
        int maxCakeIndex = 0;
        for (int i = 0; i < n; i++) {
            if (cakes[i] > maxCake) {
                maxCakeIndex = i;
                maxCake = cakes[i];
            }
        }

        // 第一次翻轉，將最大餅翻到最上面
        reverse(cakes, 0, maxCakeIndex);
        res.add(maxCakeIndex + 1);
        // 第二次翻轉，將最大餅翻到最下面
        reverse(cakes, 0, n - 1);
        res.add(n);

        // 遞歸調用
        sort(cakes, n - 1);
    }

    private void reverse(int[] arr, int i, int j) {
        while (i < j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }
}
