package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Basic;

public class NumOfSubarrays {

    // https://leetcode.cn/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/solutions/2913366/java-ding-chang-hua-dong-chuang-kou-by-s-pqur/
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        if (arr.length == 0) return 0;
        int subArraySum = 0;
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            subArraySum += arr[i];
            if (i < k - 1) continue;
            if ((subArraySum / k) >= threshold)
                res++;
            subArraySum -= arr[i - k + 1];
        }
        return res;
    }


}
