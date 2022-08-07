package 程序员代码面试指南.ch08;

public class Q14_SortNaturalNumberArray {
//    CD23 自然數數組的排序
//    描述
//    給定一個長度為N的整形數組arr，其中有N個互不相等的自然數1-N。
//    請實現arr的排序，但是不要把下標0∼N−1位置上的數通過直接賦值的方式替換成1∼N
//[要求]
//    時間覆雜度為O(n)，空間覆雜度為O(1)

    public static void sort1(int[] arr) {
        int tmp = 0;
        int next = 0;
        for (int i = 0; i < arr.length; i++) {
            tmp = arr[i];

            // 根據題目定義，arr[i]排序後必須是i+1
            // 所以若目前數的位置不正確，則和正確位置arr[arr[i]-1]進行交換
            while (arr[i] != i + 1) {
                next = arr[tmp - 1];
                arr[tmp - 1] = tmp;
                tmp = next;
            }
        }
    }

    public static void sort2(int[] arr) {
        int tmp = 0;
        for (int i = 0; i < arr.length; i++) {

            // 根據題目定義，arr[i]排序後必須是i+1
            // 所以若目前數的位置不正確，則和正確位置arr[arr[i]-1]進行交換
            while (arr[i] != i + 1) {
                tmp = arr[arr[i] - 1];
                arr[arr[i] - 1] = arr[i];
                arr[i] = tmp;
            }
        }
    }


    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {8, 2, 1, 6, 9, 3, 7, 5, 4};
        sort1(arr);
        printArray(arr);
        arr = new int[]{8, 2, 1, 6, 9, 3, 7, 5, 4};
        sort2(arr);
        printArray(arr);

    }
}
