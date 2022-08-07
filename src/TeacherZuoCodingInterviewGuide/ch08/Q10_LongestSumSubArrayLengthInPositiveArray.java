package TeacherZuoCodingInterviewGuide.ch08;

public class Q10_LongestSumSubArrayLengthInPositiveArray {
    //    CD8 未排序正數數組中累加和為給定值的最長子數組的長度
//    描述
//    給定一個數組arr，該數組無序，但每個值均為正數，再給定一個正數k。求arr的所有子數組中所有元素相加和為k的最長子數組的長度
//    例如，arr = [1, 2, 1, 1, 1], k = 3
//    累加和為3的最長子數組為[1, 1, 1]，所以結果返回3
//[要求]
//    時間覆雜度為O(n)，空間覆雜度為O(1)

    // 雙指針滑動窗口求解，右指針不斷右移擴大窗口
    // 直到子數組sum符合或超過k再進行左指針操作
    public static int getMaxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }
        int left = 0;
        int right = 0;
        int sum = arr[0];
        int len = 0;
        while (right < arr.length) {
            if (sum == k) { // 找到條件相符的子數組，記錄長度並移動左指針
                len = Math.max(len, right - left + 1);
                sum -= arr[left++];
            } else if (sum < k) { // 右指針右移子數組向右擴充，注意右邊界問題
                right++;
                if (right == arr.length) break;
            } else { // 左指針右移，子數組向右縮減，sum減去原本left位置的值
                sum -= arr[left++];
            }
        }
        return len;
    }

    public static int[] generatePositiveArray(int size) {
        int[] result = new int[size];
        for (int i = 0; i != size; i++) {
            result[i] = (int) (Math.random() * 10) + 1;
        }
        return result;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 20;
        int k = 15;
        int[] arr = generatePositiveArray(len);
        printArray(arr);
        System.out.println(getMaxLength(arr, k));

    }
}
