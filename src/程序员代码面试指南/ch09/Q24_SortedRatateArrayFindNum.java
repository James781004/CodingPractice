package 程序员代码面试指南.ch09;

public class Q24_SortedRatateArrayFindNum {
//    CD75 在有序旋轉數組中找到一個數
//    描述
//    有序數組arr可能經過一次旋轉處理，也可能沒有，且arr可能存在重覆的數。
//    例如，有序數組[1, 2, 3, 4, 5, 6, 7]，可以旋轉處理成[4, 5, 6,  7, 1, 2, 3]等。
//    給定一個可能旋轉過的有序數組arr，再給定一個數num，返回arr中是否含有num
//    關於旋轉操作：可以簡單的理解為把序列從某個位置切成兩段然後交換位置
//[要求]
//    期望覆雜度為O(logn)

    public static boolean isContains(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] == num) {
                return true;
            }
            if (arr[low] == arr[mid] && arr[mid] == arr[high]) {
                while (low != mid && arr[low] == arr[mid]) {
                    low++; // low不斷向右移動直到重複情況消失為止
                }
                if (low == mid) { // 直接尋找mid+1...high
                    low = mid + 1;
                    continue;
                }
            }

            // 旋轉遞增數組的"斷點"就是首次出現降序的地方，也會是數組中的最小值
            // 二分搜索目標就是尋找斷點
            if (arr[low] != arr[mid]) {
                if (arr[mid] > arr[low]) { // arr[low...mid]有序的情況，先找左半部
                    if (num >= arr[low] && num < arr[mid]) { // num存在於arr[low...mid]的情況
                        high = mid - 1;
                    } else {
                        low = mid + 1; // num不存在於arr[low...mid]，改為搜尋arr[mid+1...high]
                    }
                } else { // arr[low...mid]有斷點的情況，先找右半部
                    if (num > arr[mid] && num <= arr[high]) { // num存在於arr[mid...high]的情況
                        low = mid + 1;
                    } else {
                        high = mid - 1; // num不存在於arr[mid...high]的情況，改為搜尋arr[low...mid-1]
                    }
                }
            } else {
                if (arr[mid] < arr[high]) { // arr[mid...high]有序的情況，先找右半部
                    if (num > arr[mid] && num <= arr[high]) { // num存在於arr[mid...high]的情況
                        low = mid + 1;
                    } else {
                        high = mid - 1;  // num不存在於arr[mid...high]的情況，改為搜尋arr[low...mid-1]
                    }
                } else {  // arr[mid...high]有斷點的情況，先找左半部
                    if (num >= arr[low] && num < arr[mid]) {  // num存在於arr[low...mid]的情況
                        high = mid - 1;
                    } else {
                        low = mid + 1;  // num不存在於arr[low...mid]，改為搜尋arr[mid+1...high]
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] arr = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 1, 2, 3, 4, 5};
        int num = 10;
        System.out.println(isContains(arr, num));

    }
}

