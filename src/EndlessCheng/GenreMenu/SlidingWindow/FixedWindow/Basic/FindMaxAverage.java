package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Basic;

public class FindMaxAverage {

    // https://leetcode.cn/problems/maximum-average-subarray-i/solutions/2913238/java-ding-chang-hua-dong-chuang-kou-by-s-j8iv/
    public double findMaxAverage(int[] nums, int k) {
        double sum = 0.0;
        double answer = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (i < k - 1) continue;
            answer = Math.max(answer, sum / k);
            sum -= nums[i - k + 1];
        }

        return answer;
    }


}
