package TeacherZuoCodingInterviewGuide.ch08;

public class Q13_SmallSum {
//    CD21 計算數組的小和
//    描述
//    數組小和的定義如下：
//
//    例如，數組 s = [1, 3, 5, 2, 4, 6] ，在 s[0] 的左邊小於或等於 s[0] 的數的和為 0 ；
//    在 s[1] 的左邊小於或等於 s[1] 的數的和為 1 ；
//    在 s[2] 的左邊小於或等於 s[2] 的數的和為 1+3=4 ；
//    在 s[3] 的左邊小於或等於 s[3] 的數的和為 1 ；
//    在 s[4] 的左邊小於或等於 s[4] 的數的和為 1+3+2=6 ；
//    在 s[5] 的左邊小於或等於 s[5] 的數的和為 1+3+5+2+4=15 。
//    所以 s 的小和為 0+1+4+1+6+15=27
//    給定一個數組 s ，實現函數返回 s 的小和

    public static int getSmallSum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return func(arr, 0, arr.length - 1);
    }

    public static int func(int[] s, int l, int r) {
        if (l == r) return 0;
        int mid = l + ((r - l) >> 1); // 這樣可以避免溢出，而且使用了位運算，效率更高
        return func(s, l, mid) + func(s, mid + 1, r) + merge(s, l, mid, r);
    }

    // 歸併兩個有序的數組
    public static int merge(int[] s, int left, int mid, int right) {
        int[] h = new int[right - left + 1];
        int hi = 0;
        int i = left;
        int j = mid + 1;
        int smallSum = 0;
        while (i <= mid && j <= right) {
            if (s[i] <= s[j]) { //  根據題目定義，左邊比右邊小，產生小和
                smallSum += s[i] * (right - j + 1);  // 右邊大數有幾個，就加上多少份
                h[hi++] = s[i++]; // 歸併排序結果放入新數組h
            } else {
                h[hi++] = s[j++]; // 歸併排序結果放入新數組h
            }
        }

        // 沒用完的部份放進數組h
        for (; (j < right + 1) || (i < mid + 1); j++, i++) {
            h[hi++] = i > mid ? s[j] : s[i];
        }

        // 把排序好的結果放回原本數組
        for (int k = 0; k != h.length; k++) {
            s[left++] = h[k];
        }
        return smallSum;

    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 2, 4, 6, 2, 7, 8, 1};
        System.out.println(getSmallSum(arr));

    }
}
