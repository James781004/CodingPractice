package EndlessCheng.GenreMenu.BinarySearch.Maximum;

public class HIndex {

    // https://leetcode.cn/problems/h-index-ii/solutions/2504326/tu-jie-yi-tu-zhang-wo-er-fen-da-an-si-ch-d15k/
    public int hIndex(int[] citations) {
        // 在區間 (left, right) 內詢問
        int n = citations.length;
        int left = 0;
        int right = n + 1;
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // left 的回答一定為「是」
            // right 的回答一定為「否」
            int mid = (left + right) >>> 1;
            // 引用次數最多的 mid 篇論文，引用次數均 >= mid
            if (citations[n - mid] >= mid) {
                left = mid; // 詢問范圍縮小到 (mid, right)
            } else {
                right = mid; // 詢問范圍縮小到 (left, mid)
            }
        }
        // 根據循環不變量，left 現在是最大的回答為「是」的數
        return left;
    }


}
