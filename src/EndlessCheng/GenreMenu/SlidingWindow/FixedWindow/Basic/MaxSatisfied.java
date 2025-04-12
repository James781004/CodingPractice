package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Basic;

public class MaxSatisfied {

    // https://leetcode.cn/problems/grumpy-bookstore-owner/solutions/2751888/ding-chang-hua-dong-chuang-kou-fu-ti-dan-rch7/
    public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
        int[] s = new int[2]; // [不生氣的顧客數量, 生氣的顧客數量]
        int maxS1 = 0;
        for (int i = 0; i < customers.length; i++) {
            s[grumpy[i]] += customers[i];
            if (i < minutes - 1) { // 窗口長度不足 minutes
                continue;
            }
            maxS1 = Math.max(maxS1, s[1]);
            // 窗口最左邊元素離開窗口
            s[1] -= grumpy[i - minutes + 1] > 0 ? customers[i - minutes + 1] : 0;
        }
        return s[0] + maxS1;
    }


}
