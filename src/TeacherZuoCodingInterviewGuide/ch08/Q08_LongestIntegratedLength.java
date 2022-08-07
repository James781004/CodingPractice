package TeacherZuoCodingInterviewGuide.ch08;

import java.util.Arrays;
import java.util.HashSet;

public class Q08_LongestIntegratedLength {
//    CD2 最長的可整合子數組的長度
//    描述
//    先給出可整合數組的定義：如果一個數組在排序之後，每相鄰兩個數的差的絕對值都為1，或者該數組長度為1，則該數組為可整合數組。
//    例如，[5, 3, 4, 6, 2]排序後為[2, 3, 4, 5, 6]，符合每相鄰兩個數差的絕對值都為1，所以這個數組為可整合數組
//    給定一個數組arr, 請返回其中最大可整合子數組的長度。
//    例如，[5, 5, 3, 2, 6, 4, 3]的最大可整合子數組為[5, 3, 2, 6, 4]，所以請返回5

    public static int getLIL1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int len = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (isIntegrated(arr, i, j)) { // 範圍內排列後直接比較差值的過程
                    len = Math.max(len, i - j + 1);
                }
            }
        }
        return len;
    }

    public static boolean isIntegrated(int[] arr, int left, int right) {
        int[] newArr = Arrays.copyOfRange(arr, left, right + 1); // O(N)
        Arrays.sort(newArr); // O(N*logN)
        for (int i = 1; i < newArr.length; i++) {
            if (newArr[i - 1] != newArr[i] - 1) { // 驗證鄰居差值是不是1
                return false;
            }
        }
        return true;
    }

    public static int getLIL2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int len = 0;
        int max = 0;
        int min = 0;
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < arr.length; i++) {
            max = Integer.MIN_VALUE;
            min = Integer.MAX_VALUE;

            // 省略掉範圍內排列後比較差值的過程
            // 使用set把遇見的數放進去，遇見重複的就直接break，因為不符合題義條件
            // 另外記下最大最小數，可以照題義排序的子序列[i...j]數字含量必定是j - i + 1
            // 所以如果max-min+1和j-i+1相等，就可以記下長度
            for (int j = i; j < arr.length; j++) {
                if (set.contains(arr[j])) break;
                set.add(arr[j]);
                max = Math.max(max, arr[j]);
                min = Math.min(min, arr[j]);
                if (max - min == j - i) {
                    len = Math.max(len, j - i + 1);
                }
            }
            set.clear();
        }
        return len;
    }

    public static void main(String[] args) {
        int[] arr = {5, 5, 3, 2, 6, 4, 3};
        System.out.println(getLIL1(arr));
        System.out.println(getLIL2(arr));

    }
}
