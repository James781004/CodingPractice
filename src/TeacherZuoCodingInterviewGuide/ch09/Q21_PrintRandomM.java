package TeacherZuoCodingInterviewGuide.ch09;

public class Q21_PrintRandomM {
//    從N個數中等概率打印M個數
//【題目】
//    給定一個長度為N且沒有重覆元素的數組arr和一個整數n，實現函數等概率隨機打印arr中的M個數。
//
//            【要求】
//
//    相同的數不要重覆打印；
//    時間覆雜度為O(M)，額外空間覆雜度為O(1)；
//    可以改變arr數組。


    // 不考慮額外空間覆雜度的限制，可以創建一個狀態數組或者哈希表記錄已打印的數。
    // 本題的技巧是與最後一個位置的數值交換然後縮減範圍：swap arr[cur], arr[n-1-i]，
    // 很多有關等概率隨機的題目沿用這個思路
    public static void printRandM(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 0) return;
        m = Math.min(arr.length, m);
        int count = 0;
        int i = 0;
        while (count < m) {
            i = (int) (Math.random() * (arr.length) - count);
            System.out.println(arr[i]);

            // 隨機出來的數arr[i]就放到當前最後方，然後縮減範圍
            // count++表示接下來縮減範圍，範圍後方的數是已經出現過的數
            swap(arr, arr.length - count++ - 1, i);
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = {6, 2, 3, 5, 1, 4};
        int m = 1;
        printRandM(arr, m);

    }
}
