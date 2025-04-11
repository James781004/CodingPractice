package EndlessCheng.Basic.Greedy;

import java.util.Arrays;

public class earliestFullBloom {

    // https://leetcode.cn/problems/earliest-possible-day-of-full-bloom/solutions/1200254/tan-xin-ji-qi-zheng-ming-by-endlesscheng-hfwe/
    public int earliestFullBloom(int[] plantTime, int[] growTime) {
        int n = plantTime.length;
        var id = new Integer[n];

        // 用id存的是growTime的下標，用來給growTime排序的下標值
        Arrays.setAll(id, i -> i);

        // 將growTime（進程調用i/O設備的時間）按由大到小排序
        Arrays.sort(id, (i, j) -> growTime[j] - growTime[i]);
        int ans = 0, days = 0;

        // 播種時間為進程佔用cpu的時間肯定都要用的，不過生長時間為I/O設備的使用時間
        // 先讓I/O設備使用時間長的進程先使用，這樣便於更好的調度其他進程執行
        for (int i : id) {
            days += plantTime[i]; // 累加播種天數
            ans = Math.max(ans, days + growTime[i]); // 再加上生長天數，就是這個種子的開花時間
        }
        return ans;
    }


}
