package GuChengAlgorithm.Ch02_BasicAlgorithm.IntervalSweepLine;

import java.util.*;

public class Q05_Skyline {
    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g8883765d9c_0_68
    // LC 218
    public List<List<Integer>> skyline(int[][] buildings) {  // [xStart, xEnd, height]
        List<List<Integer>> res = new ArrayList<>();
        List<int[]> heights = new ArrayList<>();
        for (int[] b : buildings) {
            heights.add(new int[]{b[0], -b[2]}); // building起點高度為負數，這樣可以sort先訪問
            heights.add(new int[]{b[1], b[2]}); // building終點高度為正數，這樣可以sort後訪問
        }

        Collections.sort(heights, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        pq.offer(0);
        int preMax = 0;

        for (int[] h : heights) {
            if (h[1] < 0) pq.offer(-h[1]); // 遇到新building，加入pq，從大到小排列
            else pq.remove(h[1]); // 遇到舊building結束，從pq移除
            int curMax = pq.peek();
            if (curMax != preMax) { // 高度出現變化，開始紀錄頂點
                res.add(Arrays.asList(h[0], curMax));  // [目前x座標，目前高度y]
                preMax = curMax;  // 紀錄目前高度
            }
        }
        return res;
    }
}
