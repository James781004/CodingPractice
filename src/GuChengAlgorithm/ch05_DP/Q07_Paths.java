package GuChengAlgorithm.ch05_DP;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Q07_Paths {
    // DP常規4步走
    //
    // 1 狀態
    // status指的是dp數組的定義，最大步數，還是最大sum，最大面積等。還要考慮這個狀態是否只有xy來確定，或者雙坐標的話是否需要3，4維來保存當前狀態
    // 2 選擇
    // 也叫transition equation指的是從當前狀態到下一個狀態有哪幾個路可以走，比如常規的之後往右往下，或者對角線的走等等，(i+1, j) (i, j+1)
    // 3 起點
    // 初始化dp array，一般是(0, 0) 點起步，或者第一行第一列
    // 4 終點
    // 到達那里我們就結束開始取max, 一般為(m - 1, n - 1)


    // https://docs.google.com/presentation/d/1U3GXwA-ypxVOpJbSr5G3UrusKwjxl25VkVNcThLHU7E/edit#slide=id.gb2ed7a7e1e_0_27
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        // 初始化
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }

        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }


    public int uniquePathsMemo(int m, int n) {
        int[][] memo = new int[m][n];
        return uniquePathsHelper(m - 1, n - 1, memo);
    }

    private int uniquePathsHelper(int i, int j, int[][] memo) {
        if (i < 0 || j < 0) return 0;
        if (i == 0 && j == 0) return 1;
        if (memo[i][j] > 0) return memo[i][j];
        return memo[i][j] = uniquePathsHelper(i - 1, j, memo) + uniquePathsHelper(i, j - 1, memo);
    }


    // https://docs.google.com/presentation/d/1U3GXwA-ypxVOpJbSr5G3UrusKwjxl25VkVNcThLHU7E/edit#slide=id.gb2ed7a7e1e_0_36
    public int uniquePathsWithObstacleMemo(int[][] obstacleGrid) {
        int M = obstacleGrid.length, N = obstacleGrid[0].length;
        int[][] memo = new int[M][N];
        return obstacleHelper(obstacleGrid, M - 1, N - 1, memo);
    }

    private int obstacleHelper(int[][] grid, int i, int j, int[][] memo) {
        if (i == 0 && j == 0) return grid[i][j] == 1 ? 0 : 1;
        if (i < 0 || j < 0 || grid[i][j] == 1) return 0;
        if (memo[i][j] > 0) return memo[i][j];
        return memo[i][j] = obstacleHelper(grid, i - 1, j, memo) + obstacleHelper(grid, i, j - 1, memo);
    }

    public int uniquePathsWithObstacle(int[][] obstacleGrid) {
        int M = obstacleGrid.length, N = obstacleGrid[0].length;
        int[][] dp = new int[M][N];

        for (int i = 0; i < M; i++) {
            if (obstacleGrid[i][0] == 1) break;
            dp[i][0] = 1;
        }

        for (int j = 0; j < N; j++) {
            if (obstacleGrid[0][j] == 1) break;
            dp[0][j] = 1;
        }


        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                dp[i][j] = obstacleGrid[i][j] == 1 ? 0 : dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[M - 1][N - 1];
    }


    // https://docs.google.com/presentation/d/1U3GXwA-ypxVOpJbSr5G3UrusKwjxl25VkVNcThLHU7E/edit#slide=id.gb2ed7a7e1e_0_46
    public int minimalTotal(List<List<Integer>> triangle) {
        int N = triangle.size();
        int[][] dp = new int[N + 1][N + 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0][0];
    }

    public int minimalTotal1D(List<List<Integer>> triangle) {
        int N = triangle.size();
        int[] dp = new int[N + 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0];
    }

    public int minimalTotalMemo(List<List<Integer>> triangle) {
        int N = triangle.size();
        int[][] memo = new int[N][N];
        return triangleHelper(0, 0, triangle, memo);
    }

    private int triangleHelper(int row, int col, List<List<Integer>> triangle, int[][] memo) {
        if (row >= triangle.size()) return 0;
        if (memo[row][col] != 0) return memo[row][col];
        return memo[row][col] = Math.min(
                triangleHelper(row + 1, col, triangle, memo),
                triangleHelper(row + 1, col + 2, triangle, memo))
                + triangle.get(row).get(col);
    }


    // https://docs.google.com/presentation/d/1U3GXwA-ypxVOpJbSr5G3UrusKwjxl25VkVNcThLHU7E/edit#slide=id.gb2ed7a7e1e_0_55
    public int minPathSumMemo(int[][] grid) {
        int M = grid.length, N = grid[0].length;
        int[][] memo = new int[M][N];
        return minPathSumHelper(grid, M - 1, N - 1, memo);
    }

    private int minPathSumHelper(int[][] grid, int i, int j, int[][] memo) {
        if (i == 0 && j == 0) return grid[0][0];
        if (i < 0 || j < 0) return Integer.MAX_VALUE;
        if (memo[i][j] > 0) return memo[i][j];
        return memo[i][j] = grid[i][j] + Math.min(minPathSumHelper(grid, i - 1, j, memo),
                minPathSumHelper(grid, i, j - 1, memo));
    }

    public int minPathSum(int[][] grid) {
        int M = grid.length, N = grid[0].length;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (i == 0 && j == 0) continue;
                if (i == 0) grid[i][j] += grid[i][j - 1];
                else if (j == 0) grid[i][j] += grid[i - 1][j];
                else grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }
        return grid[M - 1][N - 1];
    }


    // https://docs.google.com/presentation/d/1U3GXwA-ypxVOpJbSr5G3UrusKwjxl25VkVNcThLHU7E/edit#slide=id.gb2ed7a7e1e_0_66
    public int minFallingPathSum(int[][] A) {
        int N = A.length;
        for (int r = N - 1; r >= 0; r--) {
            for (int c = 0; c < N; c++) {
                int minValue = A[r + 1][c];
                if (c > 0) minValue = Math.min(minValue, A[r + 1][c - 1]);
                if (c + 1 < N) minValue = Math.min(minValue, A[r + 1][c + 1]);
                A[r][c] += minValue;
            }
        }
        return Arrays.stream(A[0]).min().getAsInt();
    }


    // https://docs.google.com/presentation/d/1U3GXwA-ypxVOpJbSr5G3UrusKwjxl25VkVNcThLHU7E/edit#slide=id.gb2ed7a7e1e_0_76
    public int minFallingPathSumII(int[][] arr) {
        int m = arr.length, n = arr[0].length;
        int[][] dp = new int[m][n];
        for (int j = 0; j < n; j++) {
            dp[0][j] = arr[0][j];  // 初始化第一行
        }

        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int preRowMin = Integer.MAX_VALUE;
                for (int k = 0; k < n; k++) {  // 找上一行和裡面最小的是誰，然後加上當前位置的數字
                    if (j != k) preRowMin = Math.min(preRowMin, dp[i - 1][k]);
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            res = Math.min(res, dp[m - 1][j]);
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1U3GXwA-ypxVOpJbSr5G3UrusKwjxl25VkVNcThLHU7E/edit#slide=id.gb2ed7a7e1e_0_85
    public int maximalSquare(char[][] matrix) {
        int M = matrix.length, N = matrix[0].length;
        int[][] dp = new int[M][N];
        int max = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (i == 0 || j == 0) {
                    if (matrix[i][j] == '1') dp[i][j] = 1;
                } else {
                    if (matrix[i][j] == '1') {
                        dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
                    }
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        return max * max;
    }


    public int maximalSquare2(char[][] matrix) {
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    if (i > 0 && j > 0 && matrix[i - 1][j] > '0' && matrix[i][j - 1] > '0' &&
                            matrix[i - 1][j - 1] > '0') {
                        matrix[i][j] = (char) (Math.min(matrix[i - 1][j - 1],
                                Math.min(matrix[i - 1][j], matrix[i][j - 1])) + 1);
                    }
                    max = Math.max(max, matrix[i][j]);
                }
            }
        }
        return max == 0 ? 0 : (int) Math.pow(max - '0', 2);
    }


    // https://docs.google.com/presentation/d/1U3GXwA-ypxVOpJbSr5G3UrusKwjxl25VkVNcThLHU7E/edit#slide=id.gaa1e5c2908_1_24
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;
        int[] height = new int[n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0') height[j] = 0;
                else height[j]++;
            }
            res = Math.max(res, largestArea(height));
        }
        return res;
    }

    private int largestArea(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
                int preHeight = height[stack.pop()];
                int width = i - (stack.isEmpty() ? 0 : stack.peek() + 1);
                res = Math.max(res, preHeight * width);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int preHeight = height[stack.pop()];
            int width = height.length - (stack.isEmpty() ? 0 : stack.peek() + 1);
            res = Math.max(res, preHeight * width);
        }
        return res;
    }
}
