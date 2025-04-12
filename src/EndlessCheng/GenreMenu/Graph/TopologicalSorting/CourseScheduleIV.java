package EndlessCheng.GenreMenu.Graph.TopologicalSorting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CourseScheduleIV {

    // https://leetcode.cn/problems/course-schedule-iv/solutions/1412769/tuo-bu-pai-xu-by-yy-hh-iecn/
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        // 存入度
        int[] indegree = new int[numCourses];
        // 鄰接矩陣存關系
        int[][] m = new int[numCourses][numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            m[prerequisites[i][0]][prerequisites[i][1]] = 1;
            indegree[prerequisites[i][1]]++;
        }
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }
        while (!q.isEmpty()) {
            int p = q.poll();
            for (int i = 0; i < numCourses; i++) {
                if (m[p][i] == 1) {
                    // 掃描到一個節點同時，關心並處理其前面節點和後面節點的關系， 並更新關系矩陣
                    for (int j = 0; j < numCourses; j++) {
                        if (m[j][p] == 1) {
                            m[j][i] = 1;
                        }
                    }
                    // 如果入度為1，放入隊列
                    indegree[i]--;
                    if (indegree[i] == 0) {
                        q.offer(i);
                    }
                }
            }
        }

        List<Boolean> ans = new ArrayList<>();
        for (int[] a : queries) {
            if (m[a[0]][a[1]] == 1) {
                ans.add(true);
            } else {
                ans.add(false);
            }
        }

        return ans;
    }


}
