package EndlessCheng.GenreMenu.DataStructure.EnumerateDiagonal;

public class FindDiagonalOrder {

    // https://leetcode.cn/problems/diagonal-traverse/solutions/3762798/mo-ban-bian-li-dui-jiao-xian-pythonjavac-jnky/
    public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] ans = new int[m * n];
        int idx = 0;
        for (int k = 0; k < m + n - 1; k++) {
            int minJ = Math.max(k - m + 1, 0);
            int maxJ = Math.min(k, n - 1);
            if (k % 2 == 0) { // 偶數從左到右
                for (int j = minJ; j <= maxJ; j++) {
                    ans[idx++] = mat[k - j][j];
                }
            } else { // 奇數從右到左
                for (int j = maxJ; j >= minJ; j--) {
                    ans[idx++] = mat[k - j][j];
                }
            }
        }
        return ans;
    }

}
