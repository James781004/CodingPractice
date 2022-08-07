package TeacherZuoCodingInterviewGuide.ch05;

import java.util.Arrays;

public class Q09_ShuffleProblem {
//    https://www.nowcoder.com/practice/90e03089da164172bf193786d242184b?tpId=101&rp=1&ru=%2Fexam%2Foj%2Fta&qru=%2Fexam%2Foj%2Fta&sourceUrl=%2Fexam%2Foj%2Fta%3FtpId%3D101&difficulty=&judgeStatus=&tags=&title=%E5%AE%8C%E7%BE%8E%E6%B4%97%E7%89%8C%E9%97%AE%E9%A2%98&gioEnter=menu
//    https://www.nowcoder.com/practice/cec73361214940c0930eefdbbbef14fe?tpId=101&rp=1&ru=%2Fexam%2Foj%2Fta&qru=%2Fexam%2Foj%2Fta&sourceUrl=%2Fexam%2Foj%2Fta%3FtpId%3D101&difficulty=&judgeStatus=&tags=&title=%E5%AE%8C%E7%BE%8E%E6%B4%97%E7%89%8C%E9%97%AE%E9%A2%98&gioEnter=menu


    // 數組的長度為len，調整前的位置是i，返回調整之後的位置
    // 下標不從0開始，從1開始
    public static int modifyIndex1(int i, int len) {
        if (i <= len / 2) {
            return 2 * i;
        } else {
            return 2 * (i - (len / 2)) - 1;
        }
    }

    // 數組的長度為len，調整前的位置是i，返回調整之後的位置
    // 下標不從0開始，從1開始
    public static int modifyIndex2(int i, int len) {
        return (2 * i) % (len + 1);
    }

    // 主函數
    // 數組必須不為空，且長度為偶數
    public static void shuffle(int[] arr) {
        if (arr != null && arr.length != 0 && (arr.length & 1) == 0) {
            shuffle(arr, 0, arr.length - 1);
        }
    }

    // 在arr[L..R]上做完美洗牌的調整
    public static void shuffle(int[] arr, int L, int R) {
        while (R - L + 1 > 0) { // 切成一塊一塊的解決，每一塊的長度滿足(3^k)-1
            int len = R - L + 1;
            int base = 3;
            int k = 1;
            // 計算小於等於len並且是離len最近的，滿足(3^k)-1的數
            // 也就是找到最大的k，滿足3^k <= len+1
            while (base <= (len + 1) / 3) {
                base *= 3;
                k++;
            }
            // 當前要解決長度為base-1的塊，一半就是再除2
            int half = (base - 1) / 2;
            // [L..R]的中點位置
            int mid = (L + R) / 2;
            // 要旋轉的左部分為[L+half...mid], 右部分為arr[mid+1..mid+half]
            // 注意在這里，arr下標是從0開始的
            rotate(arr, L + half, mid, mid + half);
            // 旋轉完成後，從L開始算起，長度為base-1的部分進行下標連續推
            cycles(arr, L, base - 1, k);
            // 解決了前base-1的部分，剩下的部分繼續處理
            L = L + base - 1;
        }
    }

    // 從start位置開始，往右len的長度這一段，做下標連續推
    // 出發位置依次為1,3,9...
    public static void cycles(int[] arr, int start, int len, int k) {
        // 找到每一個出發位置trigger，一共k個
        // 每一個trigger都進行下標連續推
        // 出發位置是從1開始算的，而數組下標是從0開始算的
        for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
            int preValue = arr[trigger + start - 1];
            int cur = modifyIndex2(trigger, len);
            while (cur != trigger) {
                int tmp = arr[cur + start - 1];
                arr[cur + start - 1] = preValue;
                preValue = tmp;
                cur = modifyIndex2(cur, len);
            }
            arr[cur + start - 1] = preValue;
        }
    }

    // [L..M]為左部分，[M+1..R]為右部分，左右兩部分互換
    public static void rotate(int[] arr, int L, int M, int R) {
        reverse(arr, L, M);
        reverse(arr, M + 1, R);
        reverse(arr, L, R);
    }

    // [L..R]做逆序調整
    public static void reverse(int[] arr, int L, int R) {
        while (L < R) {
            int tmp = arr[L];
            arr[L++] = arr[R];
            arr[R--] = tmp;
        }
    }

    public static void wiggleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        // 假設這個排序是額外空間覆雜度O(1)的，當然系統提供的排序並不是，你可以自己實現一個堆排序
        Arrays.sort(arr);
        if ((arr.length & 1) == 1) {
            shuffle(arr, 1, arr.length - 1);
        } else {
            shuffle(arr, 0, arr.length - 1);
            for (int i = 0; i < arr.length; i += 2) {
                int tmp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = tmp;
            }
        }
    }

    // for test
    public static boolean isValidWiggle(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if ((i & 1) == 1 && arr[i] < arr[i - 1]) {
                return false;
            }
            if ((i & 1) == 0 && arr[i] > arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] generateArray() {
        int len = (int) (Math.random() * 10) * 2;
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        return arr;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5000000; i++) {
            int[] arr = generateArray();
            wiggleSort(arr);
            if (!isValidWiggle(arr)) {
                System.out.println("ooops!");
                printArray(arr);
                break;
            }
        }
    }
}
