package TeacherZuoCodingInterviewGuide.ch09;

import java.util.Arrays;

public class Q29_FindUpMedian {
//    CD81 在兩個長度相等的排序數組中找到上中位數
//    描述
//    給定兩個有序數組arr1和arr2，已知兩個數組的長度都為N，求兩個數組中所有數的上中位數。
//    上中位數：假設遞增序列長度為n，若n為奇數，則上中位數為第n/2+1個數；否則為第n個數
//[要求]
//    時間覆雜度為O(logN)，額外空間覆雜度為O(1)

    public static int getUpMedian(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
            throw new RuntimeException("Your arr is invalid!");
        }
        int start1 = 0;
        int end1 = arr1.length - 1;
        int start2 = 0;
        int end2 = arr2.length - 1;
        int mid1 = 0;
        int mid2 = 0;
        int offset = 0;
        while (start1 < end1) {
            mid1 = start1 + (end1 - start1) / 2;
            mid2 = start2 + (end2 - start2) / 2;

            // 元素個數為奇數，則offset為0，
            // 元素個數為偶數，則offset為1，因為較小那方的mid自身已經不可能成為上中位數，必須在這一輪loop排除
            // 例如[1,2,3,4]以及[1',2',3',4']，兩數組上中位數分別是2和2'，目標尋找第四小的數
            // 假設2>2'，則3,4因為壓過1,2,1',2'必須排除，而2'最多壓過1和1'成為第三個數也不行，所以也排除
            // 偶數offset為1作用就在排除較小那方的mid，於是剩下[1,2]以及[3',4']，進入下一輪
            offset = ((end1 - start1 + 1) & 1) ^ 1;

            // 已知兩有序數組長度相等，上中位數一定在mid1和mid2之間
            // 二分搜索的目的就是把不可能使用到的區間(小mid前方以及大mid後方)排除
            // 如果兩個mid相等，直接返回arr1[mid1]
            if (arr1[mid1] > arr2[mid2]) {
                end1 = mid1;
                start2 = mid2 + offset;
            } else if (arr1[mid1] < arr2[mid2]) {
                start1 = mid1 + offset;
                end2 = mid2;
            } else {
                return arr1[mid1];
            }
        }
        return Math.min(arr1[start1], arr2[start2]);
    }

    // 使用遞歸的作法
    private static int getUpMedian(int[] arr1, int l1, int r1, int[] arr2, int l2, int r2) {
        if (l1 == r1) return Math.min(arr1[l1], arr2[l2]);
        int mid1 = l1 + ((r1 - l1) >> 1);
        int mid2 = l2 + ((r2 - l2) >> 1);
        if (arr1[mid1] == arr2[mid2]) return arr1[mid1];
        if ((r1 - l1 + 1) % 2 == 0) {
            // 元素個數為偶數
            if (arr1[mid1] < arr2[mid2]) {
                return getUpMedian(arr1, mid1 + 1, r1, arr2, l2, mid2);
            } else {
                return getUpMedian(arr1, l1, mid1, arr2, mid2 + 1, r2);
            }
        } else {
            // 元素個數為奇數
            if (arr1[mid1] < arr2[mid2]) {
                return getUpMedian(arr1, mid1, r1, arr2, l2, mid2);
            } else {
                return getUpMedian(arr1, l1, mid1, arr2, mid2, r2);
            }
        }
    }


    // For test, this method is inefficient but absolutely right
    public static int findForTest(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
            throw new RuntimeException("Your arr is invalid!");
        }
        int[] arrAll = new int[arr1.length + arr2.length];
        for (int i = 0; i != arr1.length; i++) {
            arrAll[i * 2] = arr1[i];
            arrAll[i * 2 + 1] = arr2[i];
        }
        Arrays.sort(arrAll);
        return arrAll[(arrAll.length / 2) - 1];
    }

    public static int[] generateSortedArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != len; i++) {
            res[i] = (int) (Math.random() * (maxValue + 1));
        }
        Arrays.sort(res);
        return res;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int maxValue1 = 20;
        int maxValue2 = 50;
        int[] arr1 = generateSortedArray(len, maxValue1);
        int[] arr2 = generateSortedArray(len, maxValue2);
        printArray(arr1);
        printArray(arr2);
        System.out.println(getUpMedian(arr1, arr2));
        System.out.println(findForTest(arr1, arr2));

    }
}
