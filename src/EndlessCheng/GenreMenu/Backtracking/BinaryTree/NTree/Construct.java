package EndlessCheng.GenreMenu.Backtracking.BinaryTree.NTree;

public class Construct {

    // https://leetcode.cn/problems/construct-quad-tree/solutions/2607372/javapython3cqian-zhui-he-fen-zhi-di-gui-87zts/
    private int[][] preSum;     // 二維數組前綴和

    public Node construct(int[][] grid) {
        int n = grid.length;            // 矩陣尺寸
        preSum = new int[n + 1][n + 1];     // 初始化前綴和數組
        // 計算二維矩陣前綴和
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                preSum[i + 1][j + 1] = preSum[i][j + 1] + preSum[i + 1][j] - preSum[i][j] + grid[i][j];
            }
        }
        // 構造四叉樹
        return constructFromArea(grid, 0, 0, n - 1, n - 1);
    }

    /**
     * 生成給定區域[(ltr, ltc), (rtr, rtc)]的子樹
     *
     * @param grid: 輸入矩陣
     * @param ltr:  范圍矩陣左上角行標
     * @param ltc:  范圍矩陣左上角列標
     * @param rtr:  范圍矩陣右下角行標
     * @param rtc:  范圍矩陣右下角列標
     */
    private Node constructFromArea(int[][] grid, int ltr, int ltc, int rbr, int rbc) {
        // areaSum[(si, sj), (i,j)] = preSum[i+1][j+1] – preSum[si][j+1] – preSum[i+1][sj] + preSum[si][sj]
        int areaSum = preSum[rbr + 1][rbc + 1] - preSum[ltr][rbc + 1] - preSum[rbr + 1][ltc] + preSum[ltr][ltc];    // 計算區域范圍和
        int areaCnts = (rbr - ltr + 1) * (rbc - ltc + 1);       // 計算區域單元格個數
        if (areaSum == 0 || areaSum == areaCnts) {
            // 如果區域和為0，則該區域全為0；如果區域和等於區域格子數，則區域全為1；這兩種情況就是葉子節點
            return new Node(areaSum / areaCnts == 1, true);
        }
        int mr = ltr + (rbr - ltr + 1) / 2;     // 中間行的行號
        int mc = ltc + (rbc - ltc + 1) / 2;     // 中間列的列號
        // 將當前區域劃分成四個子區域，遞歸生成子樹
        return new Node(false, false,
                constructFromArea(grid, ltr, ltc, mr - 1, mc - 1),
                constructFromArea(grid, ltr, mc, mr - 1, rbc),
                constructFromArea(grid, mr, ltc, rbr, mc - 1),
                constructFromArea(grid, mr, mc, rbr, rbc)
        );
    }


    class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;


        public Node() {
            this.val = false;
            this.isLeaf = false;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }
    }

    ;

}
