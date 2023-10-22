package Grind.Ch05_Graph;

public class Q01_FloodFill {
    // https://leetcode.cn/problems/flood-fill/solutions/376297/ti-mu-han-yi-tu-jie-by-charming-ishi2aka/
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int originColor = image[sr][sc];
        // 如果顏色相同則不處理
        if (originColor != color) {
            dfs(image, sr, sc, color, originColor);
        }
        return image;
    }

    public void dfs(int[][] image, int x, int y, int newColor, int originColor) {
        // 判斷是否超出邊界
        if (x < 0 || x >= image.length || y < 0 || y >= image[0].length) {
            return;
        }
        if (image[x][y] == originColor) {
            // 將顏色替換
            image[x][y] = newColor;
            // 深度優先搜索四周的像素點
            dfs(image, x - 1, y, newColor, originColor);
            dfs(image, x + 1, y, newColor, originColor);
            dfs(image, x, y - 1, newColor, originColor);
            dfs(image, x, y + 1, newColor, originColor);
        }
        return;
    }
}
