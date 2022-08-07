package 程序员代码面试指南.ch08;

public class Q12_LongestLessSumSubArrayLength {
//    CD14 未排序數組中累加和小於或等於給定值的最長子數組長度
//    描述
//    給定一個無序數組arr，其中元素可正、可負、可0。給定一個整數k，求arr所有的子數組中累加和小於或等於k的最長子數組長度
//    例如：arr = [3, -2, -4, 0, 6], k = -2. 相加和小於等於-2的最長子數組為{3, -2, -4, 0}，所以結果返回4
//[要求]
//    時間覆雜度為O(n)，空間覆雜度為O(n)

    public static int maxLengthAwesome(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] minSums = new int[arr.length];
        int[] minSumEnds = new int[arr.length];
        minSums[arr.length - 1] = arr[arr.length - 1]; // 記錄以當前位置開頭的最小累加和
        minSumEnds[arr.length - 1] = arr.length - 1; // 記錄當前位置開頭的最小累加和子序列尾部位置

        // 從右往左遍歷，可以把上一次的累計結果重複利用
        for (int i = arr.length - 2; i >= 0; i--) {

            // 以當前位置i開頭的最小累加和選項只有兩種可能
            // 只有(1)arr[i]加上前面的累計結果minSums[i + 1]，或者(2)arr[i]本身
            // 如果minSums[i + 1]小於0，(1)必定是更小的選項
            // 如果選(1)，那i開頭的最小累加和子序列尾部位置就是前面累計結果的位置minSumEnds[i + 1]
            if (minSums[i + 1] < 0) {
                minSums[i] = arr[i] + minSums[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            } else {
                // 如果選(2)，當前位置就是最小累加和子序列尾部位置
                minSums[i] = arr[i];
                minSumEnds[i] = i;
            }
        }
        int end = 0;
        int sum = 0;
        int res = 0;
        // i是窗口的最左的位置，end是窗口最右位置的下一個位置
        for (int i = 0; i < arr.length; i++) {
            // while循環結束之後：
            // 1) 如果以i開頭的情況下，累加和<=k的最長子數組是arr[i..end-1]，看看這個子數組長度能不能更新res；
            // 2) 如果以i開頭的情況下，累加和<=k的最長子數組比arr[i..end-1]短，更新還是不更新res都不會影響最終結果；
            while (end < arr.length && sum + minSums[end] <= k) {
                sum += minSums[end]; // 滿足累加和小於等於k就累加上
                end = minSumEnds[end] + 1; // 因為加上了"子序列結果"，所以移動到累加和序列尾部的下一個位置
            }
            res = Math.max(res, end - i);
            if (end > i) { // [i...end]窗口內還有數，i可以繼續左移進入下一個loop
                sum -= arr[i];
            } else { // [i...end]窗口內已經沒有數了，說明從i開頭的所有子數組累加和都不可能<=k
                end = i + 1;
            }
        }
        return res;
    }

    public static int maxLength(int[] arr, int k) {
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    public static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (maxLengthAwesome(arr, k) != maxLength(arr, k)) {
                System.out.println("oops!");
            }
        }

    }
}
