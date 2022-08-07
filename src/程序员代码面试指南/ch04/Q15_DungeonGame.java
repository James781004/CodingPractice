package 程序员代码面试指南.ch04;

public class Q15_DungeonGame {
    //    【題目】
//
//            　　給定一個二維數組map，含義是一張地圖，例如如下，矩陣：
//            　　-2 　-3　 3
//            　　-5　-10　1
//            　　0 　30　-5
//            　　遊戲規則如下：
//
//    騎士從左上角出發，每次只能向右或者向下走，最後到達右下角見到公主。
//    地圖中每個位置的只代表騎士要遭遇的事。如果是負數，表示此處有怪獸，要讓騎士損失血量。如果是非負數，表示此處有血瓶，能讓騎士回血。
//    騎士從左上角到右下角的過程，走到任何一個位置，血量都不能少於１。
//    為了保證騎士能順利見到公主，初始血量至少是多少？根據map，返回初始血量。

    public static int minHP1(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 1;
        }
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col]; // dp[i][j]代表進入m[i][j]所需的最少血量

        // 初始化右下角的格子：
        // 如果在dungeon里右下角的數大於0，就是證明是加血的，那麽進入到這個格子的最少血量只需要1；
        // 如果在dungeon里右下角的數小於0的話就說明這個格子是扣血的，用1減去這個負數，那麽就能保證進入到這個格子至少還剩余1血
        dp[row][col] = m[row][col] > 0 ? 1 : -m[row][col] + 1;

        // 初始化最後一行，最後一行只能往右走
        // 要判斷當前格子是加血的還是扣血的
        for (int j = col - 1; j >= 0; j--) {
            // dp[i][j]代表進入m[i][j]所需的最少血量，所有到達這個格子時最小的生命值可以為1
            // dp[i][j] + m[i][j]表示打怪或回血之後，準備向右或者向下之前的狀況
            // 如果右方dp[i][j+1]或者下方dp[i+1][j]已經有結果，可以反推到達dp[i][j]時必要條件：
            // 1. 如果dp[row][j + 1] - m[row][j]小於0，即說明:
            //    1.1: 當前m[row][j]是加血，且加血量充足，可以直接前往下一格，所有到達dp[row][j]時最小的生命值可以為1
            // 2. 如果dp[row][j + 1] - m[row][j]大於0，即說明:
            //    2.1: m[row][j]是負數要扣血
            //    2.1: m[row][j]是加血，但加血量不足以直接前往下一格
            // 所以要前進dp[row][j + 1]的話，所有到達dp[row][j]時最小的生命值為dp[row][j + 1] - m[row][j]
            dp[row][j] = Math.max(dp[row][j + 1] - m[row][j], 1);
        }

        int right = 0;
        int down = 0;

        // 從後往前處理剩余位置，取最小值
        for (int i = row - 1; i >= 0; i--) {
            dp[i][col] = Math.max(dp[i + 1][col] - m[i][col], 1); // 初始化最後一列在這邊處理
            for (int j = col - 1; j >= 0; j--) {
                right = Math.max(dp[i][j + 1] - m[i][j], 1);
                down = Math.max(dp[i + 1][j] - m[i][j], 1);
                dp[i][j] = Math.min(right, down);
            }
        }
        return dp[0][0];
    }

    public static int minHP2(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 1;
        }
        int more = Math.max(m.length, m[0].length);
        int less = Math.min(m.length, m[0].length);
        boolean rowmore = more == m.length;
        int[] dp = new int[less];
        int tmp = m[m.length - 1][m[0].length - 1];
        dp[less - 1] = tmp > 0 ? 1 : -tmp + 1;
        int row = 0;
        int col = 0;
        for (int j = less - 2; j >= 0; j--) {
            row = rowmore ? more - 1 : j;
            col = rowmore ? j : more - 1;
            dp[j] = Math.max(dp[j + 1] - m[row][col], 1);
        }
        int choosen1 = 0;
        int choosen2 = 0;
        for (int i = more - 2; i >= 0; i--) {
            row = rowmore ? i : less - 1;
            col = rowmore ? less - 1 : i;
            dp[less - 1] = Math.max(dp[less - 1] - m[row][col], 1);
            for (int j = less - 2; j >= 0; j--) {
                row = rowmore ? i : j;
                col = rowmore ? j : i;
                choosen1 = Math.max(dp[j] - m[row][col], 1);
                choosen2 = Math.max(dp[j + 1] - m[row][col], 1);
                dp[j] = Math.min(choosen1, choosen2);
            }
        }
        return dp[0];
    }

    // 網路上找到的
    public int calculateMinimumHP(int[][] dungeon) {
        int rows = dungeon.length;
        int columns = dungeon[0].length;
        int[][] dp = new int[rows][columns]; // dp[i][j]代表進入dungeon[i][j]所需的最少血量

        //初始化右下角的格子：
        //如果在dungeon里右下角的數大於0，就是證明是加血的，那麽進入到這個格子的最少血量只需要1；
        //如果在dungeon里右下角的數小於0的話就說明這個格子是扣血的，用1減去這個負數，那麽就能保證進入到這個格子至少還剩余1血
        dp[rows - 1][columns - 1] = dungeon[rows - 1][columns - 1] > 0 ? 1 : 1 - dungeon[rows - 1][columns - 1];

        //初始化最後一行，最後一行只能往右走
        for (int j = columns - 2; j >= 0; j--) {
            //要判斷當前格子是加血的還是扣血的
            if (dp[rows - 1][j + 1] - dungeon[rows - 1][j] <= 0)//1-30小於0，即說明當前格子是加血的
                dp[rows - 1][j] = 1;//所有到達這個格子時最小的生命值可以為1
            else//如果大於0的話就說明當前格子是負數，進入當前格子至少比這個負數大1
                dp[rows - 1][j] = dp[rows - 1][j + 1] - dungeon[rows - 1][j];
        }

        //初始化最後一列，最後一列只能往下走
        for (int i = rows - 2; i >= 0; i--) {
            if (dp[i + 1][columns - 1] - dungeon[i][columns - 1] <= 0)//說明是加血的
                dp[i][columns - 1] = 1;//進入當前格子生命最少是1就行
            else
                dp[i][columns - 1] = dp[i + 1][columns - 1] - dungeon[i][columns - 1];
        }

        // 從後往前處理剩余位置，取最小值
        for (int i = rows - 2; i >= 0; --i) {
            for (int j = columns - 2; j >= 0; --j) {
                //往右走需要多少血
                int toLeft = dp[i][j + 1] - dungeon[i][j] > 0 ? dp[i][j + 1] - dungeon[i][j] : 1;
                //往上走需要多少血
                int toDown = dp[i + 1][j] - dungeon[i][j] > 0 ? dp[i + 1][j] - dungeon[i][j] : 1;

                dp[i][j] = toLeft > toDown ? toDown : toLeft;
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        int[][] map = {{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5},};
        System.out.println(minHP1(map));
        System.out.println(minHP2(map));

    }

}
