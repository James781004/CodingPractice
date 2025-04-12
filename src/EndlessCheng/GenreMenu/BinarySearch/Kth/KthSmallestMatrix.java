package EndlessCheng.GenreMenu.BinarySearch.Kth;

public class KthSmallestMatrix {

    // https://leetcode.cn/problems/find-the-kth-smallest-sum-of-a-matrix-with-sorted-rows/solutions/2286593/san-chong-suan-fa-bao-li-er-fen-da-an-du-k1vd/
    private int leftK;

    public int kthSmallest(int[][] mat, int k) {
        int sl = 0, sr = 0;
        for (var row : mat) {
            sl += row[0];
            sr += row[row.length - 1];
        }

        int left = sl - 1, right = sr; // 開區間 (sl-1,sr)
        while (left + 1 < right) { // 開區間不為空
            // 循環不變量：
            // f(left) < k
            // f(right) >= k
            int mid = (left + right) >>> 1;
            leftK = k;
            if (dfs(mat, mat.length - 1, mid - sl)) // 先把第一列的所有數都選上
                right = mid; // 二分范圍縮小至開區間 (left, mid)
            else // f(mid) < k
                left = mid; // 二分范圍縮小至開區間 (mid, right)
        }
        return right;
    }

    // 返回是否找到 k 個子數組和
    private boolean dfs(int[][] mat, int i, int s) {
        if (i < 0) // 能遞歸到這裡，說明數組和不超過二分的 mid
            return --leftK == 0; // 是否找到 k 個
        for (int x : mat[i]) { // 「枚舉選哪個」，注意 mat[i] 是有序的
            if (x - mat[i][0] > s) // 選 x 不選 mat[i][0]
                break; // 剪枝：後面的元素更大，無需枚舉
            if (dfs(mat, i - 1, s - (x - mat[i][0]))) // 選 x 不選 mat[i][0]
                return true; // 找到 k 個就一直返回 true，不再遞歸
        }
        return false;
    }


}
