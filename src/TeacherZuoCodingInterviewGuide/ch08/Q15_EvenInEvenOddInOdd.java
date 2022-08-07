package TeacherZuoCodingInterviewGuide.ch08;

public class Q15_EvenInEvenOddInOdd {
//    CD24 奇數下標都是奇數或者偶數下標都是偶數
//    描述
//    給定一個長度不小於2的數組arr，實現一個函數調整arr，要麽讓所有的偶數下標都是偶數，要麽讓所有的奇數下標都是奇數
//    注意：1、數組下標從0開始！
//            2、本題有special judge，你可以輸出任意一組合法解！同時可以證明解一定存在
//[要求]
//    時間覆雜度為O(n)，額外空間覆雜度為O(1)

    public static void modify(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int even = 0;
        int odd = 1;
        int end = arr.length - 1;

        // 最後位的數是偶數，就跟偶數位置交換，然後下標even+2
        // 最後位的數是奇數，就跟奇數位置交換，然後下標odd+2
        // 交換直到下標無法移動為止
        while (even <= end && odd <= end) {
            printArray(arr);
            if ((arr[end] & 1) == 0) {
                swap(arr, end, even);
                even += 2;
            } else {
                swap(arr, end, odd);
                odd += 2;
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
        int[] arr = {1, 8, 3, 2, 4, 6};
        printArray(arr);
        modify(arr);
        printArray(arr);

    }
}
