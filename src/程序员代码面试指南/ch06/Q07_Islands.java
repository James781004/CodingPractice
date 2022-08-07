package 程序员代码面试指南.ch06;

public class Q07_Islands {
//    鏈接：https://www.nowcoder.com/questionTerminal/74a8eba825d64dadaf79b9d624fe0a9d?commentTags=Java
//            幾個島
//    給定一個m行n列的二維地圖,
//
//    初始化每個單元都是水.
//            操作addLand 把單元格(row, col)變成陸地.
//    島嶼定義為一系列相連的被水單元包圍的陸地單元,
//
//    橫向或縱向相鄰的陸地稱為相連(斜對角不算).
//    在一系列addLand的操作過程中,給出每次addLand操作後島嶼的個數.
//            二維地圖的每條邊界外側假定都是水 .

    public static int countIslands(int[][] m) {
        if (m == null || m[0] == null) return 0;
        int N = m.length;
        int M = m[0].length;
        int res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (m[i][j] == 1) {
                    res++;
                    infect(m, i, j, N, M);
                }
            }
        }
        return res;
    }


    // 假設m矩陣的大小為N行M列，從i行j列開始感染過程
    public static void infect(int[][] m, int i, int j, int N, int M) {
        // 如果i行j列位置已經越界，或者這個位置上不是1，退出感染過程。
        if (i < 0 || i >= N || j < 0 || j >= M || m[i][j] != 1) {
            return;
        }

        m[i][j] = 2;// 對於訪問過的位置值都變成2，所以每個位置只會感染一次，不可能死循環。
        infect(m, i + 1, j, N, M);// 感染下位置
        infect(m, i - 1, j, N, M);// 感染上位置
        infect(m, i, j - 1, N, M);// 感染左位置
        infect(m, i, j + 1, N, M);// 感染右位置
    }

    public static void main(String[] args) {
        int[][] m1 = {{0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 1, 1, 1, 0}, {0, 1, 1, 1, 0, 0, 0, 1, 0},
                {0, 1, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},};
        System.out.println(countIslands(m1));

        int[][] m2 = {{0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 0}, {0, 1, 1, 1, 0, 0, 0, 1, 0},
                {0, 1, 1, 0, 0, 0, 1, 1, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},};
        System.out.println(countIslands(m2));

    }
}
