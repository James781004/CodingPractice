package TeacherZuoCodingInterviewGuide.ch08;

public class Q16_SubArrayMaxSum {
//    子數組的最大累加和問題
//    描述
//    給定一個數組arr，返回子數組的最大累加和
//    例如，arr = [1, -2, 3, 5, -2, 6, -1]，所有子數組中，[3, 5, -2, 6]可以累加出最大的和12，所以返回12.
//[要求]
//    時間覆雜度為O(n)，空間覆雜度為O(1)

    public static int maxSum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int cur = 0;
        for (int i = 0; i < arr.length; i++) {
            cur += arr[i];  // cur加上當前位置的數
            max = Math.max(max, cur); // 更新累計總和
            cur = cur < 0 ? 0 : cur; // 當前cur如果已經小於0，不如捨棄先前結果，直接從0開始
        }
        return max;
    }


    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr1 = {-2, -3, -5, 40, -10, -10, 100, 1};
        System.out.println(maxSum(arr1));

        int[] arr2 = {-2, -3, -5, 0, 1, 2, -1};
        System.out.println(maxSum(arr2));

        int[] arr3 = {-2, -3, -5, -1};
        System.out.println(maxSum(arr3));

    }
}
