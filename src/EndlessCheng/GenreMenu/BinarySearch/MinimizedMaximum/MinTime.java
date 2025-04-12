package EndlessCheng.GenreMenu.BinarySearch.MinimizedMaximum;

public class MinTime {

    // https://leetcode.cn/problems/xiao-zhang-shua-ti-ji-hua/solutions/1402270/lcp-12-by-lyyprogrammer-wy2f/
    public int minTime(int[] time, int m) {
        int left = 0;
        int right = Integer.MAX_VALUE;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 如果這個時間限制可以在規定天數裡面完成刷題計劃
            if (check(time, mid, m)) {
                // 向左縮小查找范圍
                right = mid - 1;
            } else {
                // 反之 向右縮小查找范圍
                left = mid + 1;
            }
        }
        return left;
    }

    boolean check(int[] time, int target, int m) {
        int maxTime = 0;
        int total = 0;
        // 如果第一天就都做完了
        // 後續代碼不會將days進行遞增 應該初始化為1
        int days = 1;
        // 是否使用過了場外援助
        boolean helper = true;
        for (int i = 0; i < time.length; i++) {
            // 維護花費時間最長的題目
            maxTime = Math.max(maxTime, time[i]);
            // 累加這一天的總做題時間
            total += time[i];
            // 如果超過當天做題時間限制了
            if (total > target) {
                // 如果未使用過場外援助
                if (helper) {
                    // 減去耗時最多的題目
                    total -= maxTime;
                    helper = false;
                } else {
                    // 超時並且使用過場外援助
                    // 得從下一天重新開始了
                    days++;
                    // 刷新場外援助
                    helper = true;
                    // 當天最大值刷新
                    maxTime = 0;
                    // 總計時間刷新
                    total = 0;
                    // 最重要的一點也很容易遺忘
                    // 這道沒有時間做的題目留到下一天重新開始做
                    i--;
                }
            }
        }
        return m >= days;
    }


}
