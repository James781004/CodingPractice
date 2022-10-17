package FuckingAlgorithm.Arrays;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q06_Rotations {
//    https://leetcode.cn/problems/rotate-image/
//    48. 旋轉圖像
//    給定一個 n × n 的二維矩陣 matrix 表示一個圖像。請你將圖像順時針旋轉 90 度。
//
//    你必須在 原地 旋轉圖像，這意味著你需要直接修改輸入的二維矩陣。請不要 使用另一個矩陣來旋轉圖像。

    // 將二維矩陣原地順時針旋轉 90 度
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        // 先沿對角線鏡像對稱二維矩陣
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                // swap(matrix[i][j], matrix[j][i]);
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // 然後反轉二維矩陣的每一行
        for (int[] row : matrix) {
            reverse(row);
        }
    }


    // 將二維矩陣原地逆時針旋轉 90 度
    public void rotate2(int[][] matrix) {
        int n = matrix.length, temp = 0;

        // 沿左下到右上的對角線鏡像對稱二維矩陣
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                // swap(matrix[i][j], matrix[n-j-1][n-i-1])
                temp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][n - i - 1];
                matrix[n - j - 1][n - i - 1] = temp;
            }
        }

        // 然後反轉二維矩陣的每一行
        for (int[] row : matrix) {
            reverse(row);
        }
    }

    // 反轉一維數組
    private void reverse(int[] arr) {
        int i = 0, j = arr.length - 1, temp = 0;
        while (j > i) {
            temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }

//    https://leetcode.cn/problems/spiral-matrix/
//    54. 螺旋矩陣
//    給你一個 m 行 n 列的矩陣 matrix ，請按照 順時針螺旋順序 ，返回矩陣中的所有元素。

    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int upper_bound = 0, lower_bound = m - 1;
        int left_bound = 0, right_bound = n - 1;
        List<Integer> res = new LinkedList<>();

        // res.size() == m * n 則遍歷完整個數組
        while (res.size() < m * n) {
            // 按照右、下、左、上的順序遍歷數組，並使用四個變量圈定未遍歷元素的邊界
            // 這邊往右
            if (upper_bound <= lower_bound) {
                // 在頂部從左向右遍歷
                for (int j = left_bound; j <= right_bound; j++) {
                    res.add(matrix[upper_bound][j]);
                }
                // 上邊界下移
                upper_bound++;
            }

            // 這邊往下
            if (left_bound <= right_bound) {
                // 在右側從上向下遍歷
                for (int i = upper_bound; i <= lower_bound; i++) {
                    res.add(matrix[i][right_bound]);
                }
                // 右邊界左移
                right_bound--;
            }

            // 這邊往左
            if (upper_bound <= lower_bound) {
                // 在底部從右向左遍歷
                for (int j = right_bound; j >= left_bound; j--) {
                    res.add(matrix[lower_bound][j]);
                }
                // 下邊界上移
                lower_bound--;
            }

            // 這邊往上
            if (left_bound <= right_bound) {
                // 在左側從下向上遍歷
                for (int i = lower_bound; i >= upper_bound; i--) {
                    res.add(matrix[i][left_bound]);
                }
                // 左邊界右移
                left_bound++;
            }
        }

        return res;
    }

//    https://leetcode.cn/problems/spiral-matrix-ii/
//    59. 螺旋矩陣 II
//    給你一個正整數 n ，生成一個包含 1 到 n2 所有元素，且元素按順時針順序螺旋排列的 n x n 正方形矩陣 matrix 。

    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int upper_bound = 0, lower_bound = n - 1;
        int left_bound = 0, right_bound = n - 1;
        // 需要填入矩陣的數字
        int num = 1;

        while (num <= n * n) {
            if (upper_bound <= lower_bound) {
                // 在頂部從左向右遍歷
                for (int j = left_bound; j <= right_bound; j++) {
                    matrix[upper_bound][j] = num++;
                }
                // 上邊界下移
                upper_bound++;
            }

            if (left_bound <= right_bound) {
                // 在右側從上向下遍歷
                for (int i = upper_bound; i <= lower_bound; i++) {
                    matrix[i][right_bound] = num++;
                }
                // 右邊界左移
                right_bound--;
            }

            if (upper_bound <= lower_bound) {
                // 在底部從右向左遍歷
                for (int j = right_bound; j >= left_bound; j--) {
                    matrix[lower_bound][j] = num++;
                }
                // 下邊界上移
                lower_bound--;
            }

            if (left_bound <= right_bound) {
                // 在左側從下向上遍歷
                for (int i = lower_bound; i >= upper_bound; i--) {
                    matrix[i][left_bound] = num++;
                }
                // 左邊界右移
                left_bound++;
            }
        }

        return matrix;
    }

//    https://leetcode.cn/problems/shift-2d-grid/?show=1
//    1260. 二維網格遷移
//    給你一個 m 行 n 列的二維網格 grid 和一個整數 k。你需要將 grid 遷移 k 次。
//
//    每次「遷移」操作將會引發下述活動：
//
//    位於 grid[i][j] 的元素將會移動到 grid[i][j + 1]。
//    位於 grid[i][n - 1] 的元素將會移動到 grid[i + 1][0]。
//    位於 grid[m - 1][n - 1] 的元素將會移動到 grid[0][0]。
//    請你返回 k 次遷移操作後最終得到的 二維網格。

    // 想象扁平化後的一維數組的平移，再將一維數組映射回二維。
    // 具體來說:
    // 原來的(i, j)對應的一維坐標為i * n + j,
    // 向右平移k後為i * n + j + k,
    // 總共只有m * n個位置所以最終坐標為(i * n + j + k) % (m * n),
    // 轉換回二維坐標即可。
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int[][] ans = new int[m][n];
        for (int i = 0, total = m * n; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int nxt = (i * n + j + k) % total;
                ans[nxt / n][nxt % n] = grid[i][j];
            }
        }
        
        List<List<Integer>> res = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            List<Integer> cur = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                cur.add(ans[i][j]);
            }
            res.add(cur);
        }
        return res;
    }
}
