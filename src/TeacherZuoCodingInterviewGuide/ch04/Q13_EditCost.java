package TeacherZuoCodingInterviewGuide.ch04;

public class Q13_EditCost {
//    CD43 最小编辑代价
//
//    描述
//    给定两个字符串str1和str2，再给定三个整数ic，dc和rc，分别代表插入、删除和替换一个字符的代价，请输出将str1编辑成str2的最小代价。

    public static int minCost1(String str1, String str2, int ic, int dc, int rc) {
        if (str1 == null || str2 == null) return 0;
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int row = chs1.length + 1; // 假設第一個字元為""
        int col = chs2.length + 1; // 假設第一個字元為""
        int[][] dp = new int[row][col];

        // str1 -> "" 的代價為dc * i，即全部移除的代價
        for (int i = 1; i < row; i++) {
            dp[i][0] = dc * i;
        }

        // "" -> str2 的代價為ic * j，即全部新增的代價
        for (int j = 1; j < col; j++) {
            dp[0][j] = ic * j;
        }

        // 普遍位置就要比較當前位置字元是否相等
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                // 注意下標位置，在dp表中第一位已經假設成空字元，其他字元在dp表中已經全部都往後移動了一位
                // 所以dp[i][j]我們實際觀察取值位置為chs1[i - 1], chs2[j - 1]
                if (chs1[i - 1] == chs2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + rc;
                }
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
            }
        }
        return dp[row - 1][col - 1];
    }

    public static int minCost2(String str1, String str2, int ic, int dc, int rc) {
        if (str1 == null || str2 == null) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        char[] longs = chs1.length >= chs2.length ? chs1 : chs2;
        char[] shorts = chs1.length < chs2.length ? chs1 : chs2;

        if (chs1.length < chs2.length) { // str2較長就交換ic和dc的值
            int tmp = ic;
            ic = dc;
            dc = tmp;
        }

        int[] dp = new int[shorts.length + 1];
        for (int i = 1; i <= shorts.length; i++) {
            dp[i] = ic * i;
        }
        for (int i = 1; i <= longs.length; i++) {
            int pre = dp[0]; // pre表示左上角的值
            dp[0] = dc * i; // 進入下一列要先把原值初始化
            for (int j = 1; j <= shorts.length; j++) {
                int tmp = dp[j]; // dp[j]沒更新前先保留下來

                // 一樣要注意下標位置，在dp表中第一位已經假設成空字元，其他字元在dp表中已經全部都往後移動了一位
                // 所以我們實際取值位置為chs1[i - 1], chs2[j - 1]
                // 這邊pre代表的就是原本的dp[i - 1][j - 1]
                if (longs[i - 1] == shorts[j - 1]) {
                    dp[j] = pre;
                } else {
                    dp[j] = pre + rc;
                }

                dp[j] = Math.min(dp[j], dp[j - 1] + ic);
                dp[j] = Math.min(dp[j], tmp + dc);
                pre = tmp; // pre改成dp[j]沒更新前的值
            }
        }
        return dp[shorts.length];
    }

    public static void main(String[] args) {
        String str1 = "ab12cd3";
        String str2 = "abcdf";
        System.out.println(minCost1(str1, str2, 5, 3, 2));
        System.out.println(minCost2(str1, str2, 5, 3, 2));

        str1 = "abcdf";
        str2 = "ab12cd3";
        System.out.println(minCost1(str1, str2, 3, 2, 4));
        System.out.println(minCost2(str1, str2, 3, 2, 4));

        str1 = "";
        str2 = "ab12cd3";
        System.out.println(minCost1(str1, str2, 1, 7, 5));
        System.out.println(minCost2(str1, str2, 1, 7, 5));

        str1 = "abcdf";
        str2 = "";
        System.out.println(minCost1(str1, str2, 2, 9, 8));
        System.out.println(minCost2(str1, str2, 2, 9, 8));

    }
}
