package 劍指offer.Search;

public class Q13_movingCount {

    public int movingCount(int threshold, int rows, int cols) {
        //临时变量visited记录格子是否被访问过
        boolean[][] visited = new boolean[rows][cols];
        return dfs(0, 0, rows, cols, threshold, visited);
    }

    public int dfs(int i, int j, int rows, int cols, int threshold, boolean[][] visited) {
        //i >= rows || j >= cols是边界条件的判断，threshold < sum(i, j)判断当前格子坐标是否
        // 满足条件，visited[i][j]判断这个格子是否被访问过
        if (i >= rows || j >= cols || threshold < sum(i, j) || visited[i][j])
            return 0;
        //标注这个格子被访问过
        visited[i][j] = true;
        //沿着当前格子的右边和下边继续访问
        return 1 + dfs(i + 1, j, rows, cols, threshold, visited) +
                dfs(i, j + 1, rows, cols, threshold, visited);
    }

    //计算两个坐标数字的和
    private int sum(int i, int j) {
        int sum = 0;
        //计算坐标i所有数字的和
        while (i != 0) {
            sum += i % 10;
            i /= 10;
        }
        //计算坐标j所有数字的和
        while (j != 0) {
            sum += j % 10;
            j /= 10;
        }
        return sum;
    }
}
