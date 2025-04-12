package EndlessCheng.GenreMenu.Graph.Pseudotree;

public class LongestCycle {

    // https://leetcode.cn/problems/longest-cycle-in-a-graph/solutions/1710828/nei-xiang-ji-huan-shu-zhao-huan-li-yong-pmqmr/
    public int longestCycle(int[] edges) {
        /*
        基環樹+時間戳
        times[i]記錄首次訪問編號為i的節點時間點，clock維護當前的時間節點
        我們枚舉每個出發點
        1.遇到之前訪問過的節點times[i]>0，跳過
        2.首次訪問節點i，從節點i出發向前走同時標記訪問過的節點的時間戳
        如果前方出現過times[j]>0的情況說明這個時候節點已經被訪問過，說明成環了
        注意:為了避免後面枚舉的節點重復經過之前訪問過的節點但是不成環的情況，要加一個判斷條件times[j]>=start
        其中start為從i點出發的時間戳，若成環必然有times[j]>=start，重復經過舊節點但是不成環的情況是times[j]<start
        因為你這個times[j]之前一輪出發點中已經枚舉過了，時間必定在該輪start之前
        維護這個環的長度最大值就是答案
        時間復雜度:O(N) 空間復雜度:O(N)
         */
        int res = -1;
        int n = edges.length;
        int[] times = new int[n];
        // 枚舉每個出發點
        for (int i = 0, clock = 1; i < n; i++) {
            if (times[i] > 0) continue; // 這個出發點已經訪問過了，跳過
            int start = clock;  // start維護當前出發點的出發時間
            // 枚舉以節點i為出發點的路徑上的點
            for (int x = i; x != -1; x = edges[x]) {
                if (times[x] > 0) { // 已經訪問過該節點(不一定是該輪的)
                    // 若該節點是該輪才訪問的說明成環了
                    if (times[x] >= start) res = Math.max(res, clock - times[x]);  // 維護最大環值
                    break;  // 已經訪問過節點就退出，不論是該輪訪問的還是之前
                }
                times[x] = clock++; // 標記訪問該節點的最早時間戳
            }
        }
        return res;
    }


}
