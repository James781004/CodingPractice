package GuChengAlgorithm.Ch02_BasicAlgorithm.DFS;

import java.util.Arrays;

public class Q06_Prune {
    // https://docs.google.com/presentation/d/1u12_iFmcm3e1Rn1bB7XYwMP90U_UwdLHQMrTDyK_AFs/edit#slide=id.gee2c1f06ac_0_78
    int res = Integer.MAX_VALUE;

    // prune1: 目前分配給某一個worker的工作時間已經大於global，停止
    // prune2: 一個工作分配給幾個相同cycle的worker效果是一樣的，我們sort後只分配給其中一個，跳過其他
    // prune3: 工作從最時間長的開始分配
    // since N is small as 12, worst case O(kn!), but there could be some closer time estimation.
    // each task can have n * (n - 1) * (n - 2)... 2 * 1
    public int minimumTimeRequired(int[] jobs, int k) {
        Arrays.sort(jobs);  // 排序本身就是一種剪枝策略，先嘗試大的job
        dfs(jobs, jobs.length - 1, new int[k]);
        return res;
    }

    private void dfs(int[] jobs, int pos, int[] sum) {
        if (pos < 0) {
            res = Math.min(res, Arrays.stream(sum).max().getAsInt());
            return;
        }
        if (Arrays.stream(sum).max().getAsInt() >= res) return; // 剪枝
        for (int i = 0; i < sum.length; i++) {
            if (i > 0 && sum[i] == sum[i - 1]) continue;  // 去重剪枝
            sum[i] += jobs[pos];
            dfs(jobs, pos - 1, sum); // pos往後走
            sum[i] -= jobs[pos];
        }
    }


    // https://docs.google.com/presentation/d/1u12_iFmcm3e1Rn1bB7XYwMP90U_UwdLHQMrTDyK_AFs/edit#slide=id.gee2c1f06ac_0_87
    int sessionRes;
    int maxSessionTime;
    int[] tasks;
    int[] sessions;

    public int minSessions(int[] tasks, int sessionTime) {
        Arrays.sort(tasks);   // 排序本身就是一種剪枝策略，從大到小排列，然後先嘗試大的task
        sessionRes = tasks.length;
        maxSessionTime = sessionTime;
        this.tasks = tasks;
        this.sessions = new int[tasks.length];
        helper(tasks.length - 1, 0);
        return sessionRes;
    }

    private void helper(int tasksID, int sessionCount) {
        if (sessionCount > sessionRes) return;  // 剪枝，global min
        if (tasksID < 0) {
            sessionRes = Math.min(sessionRes, sessionCount);
            return;
        }

        // 把當前task放入範圍內(sessionCount)已建立的其中一個session，到達極限後再進入範圍內下一個session
        // task本身只單向，我們有很多的session能放進去就放，不能放進去我們就新開一個session
        // 在sessionCount - 1中止
        for (int i = 0; i < sessionCount; i++) {
            if (sessions[i] + tasks[tasksID] <= maxSessionTime) {
                sessions[i] += tasks[tasksID];
                helper(tasksID - 1, sessionCount);  // 下一個樹層遞歸嘗試下一個task
                sessions[i] -= tasks[tasksID];
            }
        }

        // 把當前task放入新開的session
        // 上面for loop在sessionCount - 1中止，所以sessionCount就是下一個新session
        sessions[sessionCount] += tasks[tasksID];
        helper(tasksID - 1, sessionCount + 1);
        sessions[sessionCount] -= tasks[tasksID];
    }
}
