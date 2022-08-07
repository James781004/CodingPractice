package TeacherZuoCodingInterviewGuide.ch08;

public class Q05_MinLengthForSort {
//    需要排序的最短子數組長度
//    描述
//    給定一個無序數組arr，求出需要排序的最短子數組的長度，
//    對子數組排序後能使得整個數組有序，即為需要排序的數組。
//    例如：arr=[1,5,3,4,2,6,7]返回4，因為只有[5,3,4,2]需要排序。

    public static int getMinLength(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        // 這邊預設數組是從小到大
        // 從右向左找尋可以需要排序的左邊界
        // 如果左側找到了比右側還大的數，noMinIndex就記錄下位置，不斷更新到最左邊界為止
        int min = arr[arr.length - 1];
        int noMinIndex = -1;
        for (int i = arr.length - 2; i != -1; i--) {
            if (arr[i] > min) {
                noMinIndex = i;
            } else {
                min = Math.min(min, arr[i]);
            }
        }

        if (noMinIndex == -1) {
            return 0;
        }

        // 從左向右找尋可以需要排序的右邊界
        // 如果右側找到了比左側還小的數，noMaxIndex就記錄下位置，不斷更新到最右邊界為止
        int max = arr[0];
        int noMaxIndex = -1;
        for (int i = 1; i != arr.length; i++) {
            if (arr[i] < max) {
                noMaxIndex = i;
            } else {
                max = Math.max(max, arr[i]);
            }
        }

        // 計算長度
        return noMaxIndex - noMinIndex + 1;
    }
}
