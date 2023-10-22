package Grind.Ch13_Heap;

import java.util.Arrays;

public class Q04_TaskScheduler {
    // https://leetcode.cn/problems/task-scheduler/solutions/29894/621-ren-wu-diao-du-qi-java-jie-ti-zhu-shi-ying-gai/

    /**
     * 解題思路：
     * 1、將任務按類型分組，正好A-Z用一個int[26]保存任務類型個數
     * 2、對數組進行排序，優先排列個數（count）最大的任務，
     * 如題得到的時間至少為 retCount =（count-1）* (n+1) + 1 ==> A->X->X->A->X->X->A(X為其他任務或者待命)
     * 3、再排序下一個任務，如果下一個任務B個數和最大任務數一致，
     * 則retCount++ ==> A->B->X->A->B->X->A->B
     * 4、如果空位都插滿之後還有任務，那就隨便在這些間隔裡面插入就可以，因為間隔長度肯定會大於n，在這種情況下就是任務的總數是最小所需時間
     */
    public int leastInterval(char[] tasks, int n) {
        if (tasks.length <= 1 || n < 1) return tasks.length;
        int[] counts = new int[26];
        for (int i = 0; i < tasks.length; i++) {
            counts[tasks[i] - 'A']++;
        }
        Arrays.sort(counts);
        int maxCount = counts[25];
        int retCount = (maxCount - 1) * (n + 1) + 1;
        int i = 24;
        while (i >= 0 && counts[i] == maxCount) {
            retCount++;
            i--;
        }
        return Math.max(retCount, tasks.length);
    }
}
