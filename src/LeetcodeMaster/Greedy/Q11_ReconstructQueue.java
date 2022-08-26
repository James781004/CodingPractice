package LeetcodeMaster.Greedy;

import java.util.Arrays;
import java.util.LinkedList;

public class Q11_ReconstructQueue {
//    406.根據身高重建隊列
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0406.%E6%A0%B9%E6%8D%AE%E8%BA%AB%E9%AB%98%E9%87%8D%E5%BB%BA%E9%98%9F%E5%88%97.md
//
//    假設有打亂順序的一群人站成一個隊列，數組 people 表示隊列中一些人的屬性（不一定按順序）。
//    每個 people[i] = [hi, ki] 表示第 i 個人的身高為 hi ，前面 正好 有 ki 個身高大於或等於 hi 的人。
//
//    請你重新構造並返回輸入數組 people 所表示的隊列。
//    返回的隊列應該格式化為數組 queue ，其中 queue[j] = [hj, kj] 是隊列中第 j 個人的屬性（queue[0] 是排在隊列前面的人）。
//
//    示例 1：
//
//    輸入：people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
//    輸出：[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
//    解釋：
//    編號為 0 的人身高為 5 ，沒有身高更高或者相同的人排在他前面。
//    編號為 1 的人身高為 7 ，沒有身高更高或者相同的人排在他前面。
//    編號為 2 的人身高為 5 ，有 2 個身高更高或者相同的人排在他前面，即編號為 0 和 1 的人。
//    編號為 3 的人身高為 6 ，有 1 個身高更高或者相同的人排在他前面，即編號為 1 的人。
//    編號為 4 的人身高為 4 ，有 4 個身高更高或者相同的人排在他前面，即編號為 0、1、2、3 的人。
//    編號為 5 的人身高為 7 ，有 1 個身高更高或者相同的人排在他前面，即編號為 1 的人。
//    因此 [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]] 是重新構造後的隊列。
//    示例 2：
//
//    輸入：people = [[6,0],[5,0],[4,0],[3,2],[2,2],[1,4]]
//    輸出：[[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]
//    提示：
//
//            1 <= people.length <= 2000
//            0 <= hi <= 10^6
//            0 <= ki < people.length
//            題目數據確保隊列可以被重建


    public int[][] reconstructQueue(int[][] people) {
        // 身高從大到小排（身高相同編號k小的站前面）
        Arrays.sort(people, (a, b) -> {
            if (a[0] == b[0]) return a[1] - b[1];
            return b[0] - a[0];
        });

        LinkedList<int[]> que = new LinkedList<>();
        for (int[] p : people) {
            que.add(p[1], p);  // 插入到編號k的位置
        }

        return que.toArray(new int[people.length][]);
    }
}
