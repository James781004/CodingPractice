package EndlessCheng.Basic.BinarySearch;

public class hIndex {
    // https://leetcode.cn/problems/h-index-ii/solutions/2504326/tu-jie-yi-tu-zhang-wo-er-fen-da-an-si-ch-d15k/
    public int hIndex(int[] citations) {
        // 在區間 [left, right] 內詢問
        int n = citations.length;
        int left = 1;
        int right = n;
        while (left <= right) { // 區間不為空
            // 循環不變量：
            // left-1 的回答一定為「是」
            // right+1 的回答一定為「否」
            int mid = (left + right) >>> 1;
            // 引用次數最多的 mid 篇論文，引用次數均 >= mid
            if (citations[n - mid] >= mid) {
                left = mid + 1; // 詢問范圍縮小到 [mid+1, right]
            } else {
                right = mid - 1; // 詢問范圍縮小到 [left, mid-1]
            }
        }
        // 循環結束後 right 等於 left-1，回答一定為「是」
        // 根據循環不變量，right 現在是最大的回答為「是」的數
        return right;
    }

}
