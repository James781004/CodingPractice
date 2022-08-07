package TeacherZuoCodingInterviewGuide.ch09;

public class Q23_SortedRatateArrayFindMin {
//    CD74 在有序旋轉數組中找到最小值
//    描述
//    有序數組arr可能經過一次旋轉處理，也可能沒有，且arr可能存在重覆的數。
//    例如，有序數組[1, 2, 3, 4, 5, 6, 7]，可以旋轉處理成[4, 5, 6,  7, 1, 2, 3]等。
//    給定一個可能旋轉過的有序數組arr，返回arr中的最小值。
//            [要求]
//    期望覆雜度為O(logn)

    public static int getMin(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        while (low < high) {
            if (low == high - 1) break;
            if (arr[low] < arr[high]) return arr[low]; // 找到有序遞增數組，直接返回arr[low]

            // 旋轉遞增數組的"斷點"就是首次出現降序的地方，斷點也會是數組中的最小值
            // 二分搜索目標就是尋找斷點
            mid = low + (high - low) / 2;

            // arr[low...mid]沒有遞增，表示斷點在arr[low...mid]區間
            if (arr[low] > arr[mid]) {
                high = mid;
                continue;
            }

            // arr[mid...high]沒有遞增，表示斷點在arr[mid...high]區間
            if (arr[mid] > arr[high]) {
                low = mid;
                continue;
            }

            // arr[low] == arr[mid] == arr[high]的狀況
            while (low < mid) {
                if (arr[low] == arr[mid]) {
                    low++; // arr[low] == arr[mid]持續到mid位置，表示斷點在arr[mid...high]區間
                } else if (arr[low] < arr[mid]) {
                    return arr[low]; // 左方出現降序表示出現斷點(數組中的最小值)，直接返回arr[low]
                } else {
                    high = mid; // 斷點在arr[low...mid]區間，調整high位置繼續二分搜索
                    break;
                }
            }
        }
        return Math.min(arr[low], arr[high]);
    }

    public static void main(String[] args) {
        int[] test = {4, 5, 5, 5, 1, 2, 3};
        System.out.println(getMin(test));

    }
}
