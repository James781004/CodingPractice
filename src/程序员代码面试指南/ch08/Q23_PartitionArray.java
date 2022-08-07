package 程序员代码面试指南.ch08;

public class Q23_PartitionArray {
//    CD36 數組的partition調整
//    描述
//    給定一個有序數組arr，調整arr使得這個數組的左半部分[1, \frac{n+1}{2}][1,
//            2
//    n+1
//            ​
//            ]沒有重覆元素且升序，而不用保證右部分是否有序
//    例如，arr = [1, 2, 2, 2, 3, 3, 4, 5, 6, 6, 7, 7, 8, 8, 8, 9]，調整之後arr=[1, 2, 3, 4, 5, 6, 7, 8, 9, .....]。
//            [要求]
//    時間覆雜度為O(n)，空間覆雜度為O(1)
//
//    CD37 數組的partition調整補充問題
//    描述
//    給定一個數組arr，其中只可能含有0、1、2三個值，請實現arr的排序
//[要求]
//    時間覆雜度為O(n)，空間覆雜度為O(1)

    public static void leftUnique(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int u = 0; // 指向"有序區"最後一個元素
        int i = 1; // 指向下一個元素

        // 指針所指的元素跟有序區最後一個元素相等就直接走，不相等就跟有序區的下一個位置(++u)交換
        while (i != arr.length) {
            if (arr[i++] != arr[u]) {
                swap(arr, ++u, i - 1); // 這邊要抓i-1是因為上面已經arr[i++]
            }
        }
    }


    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int left = -1; // 小於區域的尾
        int index = 0;
        int right = arr.length;  // 大於區域的頭
        while (index < right) {
            if (arr[index] == 0) {
                swap(arr, ++left, index++);  // 把當前數追加在小於區域的後面，當前位置處理完成，所以指針向後走
            } else if (arr[index] == 2) {
                swap(arr, index, --right);  // 把當前數插入到大於區域的前面，但當前位置還沒確定是正解，所以指針不動
            } else {
                index++; // 指針向後走
            }
        }
    }


    public static void swap(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 2, 2, 3, 3, 4, 5, 6, 6, 7, 7, 8, 8, 8, 9};
        printArray(arr1);
        leftUnique(arr1);
        printArray(arr1);

        System.out.println();

        int[] arr2 = {2, 1, 2, 0, 1, 1, 2, 2, 0};
        printArray(arr2);
        sort(arr2);
        printArray(arr2);

    }
}
