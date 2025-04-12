package EndlessCheng.GenreMenu.Graph.TopologicalSorting;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class CourseScheduleII {

    // https://leetcode.cn/problems/course-schedule-ii/solutions/8431/tuo-bu-pai-xu-shen-du-you-xian-bian-li-python-dai-/
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if (numCourses <= 0) {
            return new int[0];
        }

        HashSet<Integer>[] adj = new HashSet[numCourses];
        for (int i = 0; i < numCourses; i++) {
            adj[i] = new HashSet<>();
        }

        // [1,0] 0 -> 1
        int[] inDegree = new int[numCourses];
        for (int[] p : prerequisites) {
            adj[p[1]].add(p[0]);
            inDegree[p[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int[] res = new int[numCourses];
        // 當前結果集列表裡的元素個數，正好可以作為下標
        int count = 0;

        while (!queue.isEmpty()) {
            // 當前入度為 0 的結點
            Integer head = queue.poll();
            res[count] = head;
            count++;

            Set<Integer> successors = adj[head];
            for (Integer nextCourse : successors) {
                inDegree[nextCourse]--;
                // 馬上檢測該結點的入度是否為 0，如果為 0，馬上加入隊列
                if (inDegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }

        // 如果結果集中的數量不等於結點的數量，就不能完成課程任務，這一點是拓撲排序的結論
        if (count == numCourses) {
            return res;
        }
        return new int[0];
    }


}
