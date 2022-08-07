package TeacherZuoCodingInterviewGuide.ch08;

public class Q09_PrintUniquePairAndTriad {
//    CD3 不重覆打印排序數組中相加和為給定值的所有二元組
//    描述
//    給定排序數組arr和整數k，不重覆打印arr中所有相加和為k的不降序二元組
//    例如,arr =[-8,-4,-3,0,1,2,4,5,8,9],k =10，打印結果為：
//            1,9
//            2,8
//    CD4 不重覆打印排序數組中相加和為給定值的所有三元組
//    描述
//    給定排序數組arr和整數k，不重覆打印arr中所有相加和為k的嚴格升序的三元組
//    例如, arr = [-8, -4, -3, 0, 1, 1, 2, 4, 5, 8, 9], k = 10，打印結果為：
//            -4 5 9
//            -3 4 9
//            -3 5 8
//            0 1 9
//            0 2 8
//            1 4 5
//    其中三元組1 1 8不滿足嚴格升序所以不打印

    public static void printUniquePair(int[] arr, int k) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            if (arr[left] + arr[right] < k) {
                left++;
            } else if (arr[left] + arr[right] > k) {
                right--;
            } else {
                // 去重
                if (left == 0 || arr[left - 1] != arr[left]) {
                    System.out.println(arr[left] + "," + arr[right]);
                }
                left++;
                right--;
            }
        }
    }

    public static void printUniqueTriad(int[] arr, int k) {
        if (arr == null || arr.length < 3) {
            return;
        }

        // 先鎖定左1(arr[i])，找尋左2...右1之中k - arr[i]的結果
        for (int i = 0; i < arr.length - 2; i++) {
            if (i == 0 || arr[i] != arr[i - 1]) {
                printRest(arr, i, i + 1, arr.length - 1, k - arr[i]);
            }
        }
    }

    public static void printRest(int[] arr, int f, int l, int r, int k) {
        while (l < r) {
            if (arr[l] + arr[r] < k) {
                l++;
            } else if (arr[l] + arr[r] > k) {
                r--;
            } else {
                if (l == f + 1 || arr[l - 1] != arr[l]) {
                    System.out.println(arr[f] + "," + arr[l] + "," + arr[r]);
                }
                l++;
                r--;
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
        int sum = 10;
        int[] arr1 = {-8, -4, -3, 0, 1, 2, 4, 5, 8, 9};
        printArray(arr1);
        System.out.println("====");
        printUniquePair(arr1, sum);
        System.out.println("====");
        printUniqueTriad(arr1, sum);

    }
}
