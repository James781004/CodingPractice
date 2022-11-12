package FuckingAlgorithm.Greedy;

import java.util.Arrays;

public class Q03_VideoStitching {
//    https://leetcode.cn/problems/video-stitching/
//    1024. 視頻拼接
//    你將會獲得一系列視頻片段，這些片段來自於一項持續時長為 time 秒的體育賽事。這些片段可能有所重疊，也可能長度不一。
//
//    使用數組 clips 描述所有的視頻片段，其中 clips[i] = [starti, endi] 表示：某個視頻片段開始於 starti 並於 endi 結束。
//
//    甚至可以對這些片段自由地再剪輯：
//
//    例如，片段 [0, 7] 可以剪切成 [0, 1] + [1, 3] + [3, 7] 三部分。
//    我們需要將這些片段進行再剪輯，並將剪輯後的內容拼接成覆蓋整個運動過程的片段（[0, time]）。
//    返回所需片段的最小數目，如果無法完成該任務，則返回 -1 。

    public int videoStitching(int[][] clips, int T) {
        if (T == 0) return 0;

        // 按起點升序排列，起點相同的降序排列
        Arrays.sort(clips, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });

        // 記錄選擇的短視頻個數
        int res = 0;

        int curEnd = 0, nextEnd = 0;
        int i = 0, n = clips.length;

        while (i < n && clips[i][0] <= curEnd) {
            // 在第 res 個視頻的區間內貪心選擇下一個視頻
            while (i < n && clips[i][0] <= curEnd) {
                nextEnd = Math.max(nextEnd, clips[i][1]);
                i++;
            }
            // 找到下一個視頻，更新 curEnd
            res++;
            curEnd = nextEnd;
            if (curEnd >= T) {
                // 已經可以拼出區間 [0, T]
                return res;
            }
        }
        // 無法連續拼出區間 [0, T]
        return -1;
    }


    // DP
    public int videoStitchingDP(int[][] clips, int time) {
        int n = clips.length;
        // 按開始時間排序，如果開始時間相同，按結束時間從大到小排序
        // 排序後，相同開始時間的片段構成從左到右的包含關系，如[0,4],[0,3],[1,7],[1,6]...
        Arrays.sort(clips, (o1, o2) -> (o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0]));
        if (clips[0][0] != 0) return -1;
        // dp[i]表示跳轉到該點的最少次數
        int[] dp = new int[time + 1];
        Arrays.fill(dp, -1);
        // dp數組初始化
        for (int i = 0; i <= Math.min(time, clips[0][1]); i++) dp[i] = 1;
        // 如果左邊界未被賦值，說明無法到達該片段
        for (int i = 1; i < n && clips[i][0] <= time; i++) {
            if (dp[clips[i][0]] == -1) return -1;
            // 如果已經有值，則保持不變，如果從未更新過（-1），則等於該區間起點加1
            // 可以證明dp[i]已更新的部分從左到右非遞減
            for (int j = clips[i][0]; j <= Math.min(time, clips[i][1]); j++) {
                if (dp[j] == -1) dp[j] = dp[clips[i][0]] + 1;
            }
        }
        return dp[time];
    }
}
