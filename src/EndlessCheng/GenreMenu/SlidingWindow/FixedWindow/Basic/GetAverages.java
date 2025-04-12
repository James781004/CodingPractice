package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Basic;

public class GetAverages {

    // https://leetcode.cn/problems/k-radius-subarray-averages/solutions/1126276/on-hua-dong-chuang-kou-by-endlesscheng-jtr5/
    public int[] getAverages(int[] nums, int k) {
        int[] avgs = new int[nums.length];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i < k || i + k >= nums.length) { // 超過邊界
                avgs[i] = -1;
            }
            sum += nums[i]; // 進入窗口
            if (i >= k * 2) {
                avgs[i - k] = sum / (k * 2 + 1);
                sum -= nums[i - k * 2]; // 窗口最左邊的元素離開窗口
            }
        }
        return avgs;
    }


}
