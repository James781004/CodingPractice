package TeacherZuoCodingInterviewGuide.ch09;

public class Q35_ThrowChessPiecesProblem {
//    CD88 丟棋子問題
//    描述
//    一座大樓有0∼N層，地面算作第0層，最高的一層為第N層。
//    已知棋子從第0層掉落肯定不會摔碎，從第i層掉落可能會摔碎，也可能不會摔碎(1⩽i⩽N)。
//    給定整數N作為樓層數，再給定整數K作為棋子數，
//    返回如果想找到棋子不會摔碎的最高層數，即使在最差的情況下扔的最小次數。
//    一次只能扔一個棋子。
//            [要求]
//    時間覆雜度在最壞情況下為O(n)

    public static int solution1(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        return Process1(nLevel, kChess);
    }

    public static int Process1(int nLevel, int kChess) {
        if (nLevel == 0) return 0; // 樓層為0就不用試
        if (kChess == 1) return nLevel; // 只有一顆棋，就只能從第一層試到第N層，所以直接返回nLevel
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < nLevel + 1; i++) { // 從第一層開始往上
            if (i == nLevel) {

            }
            min = Math.min(
                    min,
                    Math.max(Process1(i - 1, kChess - 1), // 棋碎了剩下kChess - 1顆，i以上的樓層不用繼續嘗試了
                            Process1(nLevel - i, kChess))); // 棋沒碎，i以下的樓層不用繼續嘗試了，樓層剩下nLevel - i
        }
        return min + 1; // 加上最初的一步
    }

    public static int solution2(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) return nLevel;

        int[][] dp = new int[nLevel + 1][kChess + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[i][1] = i;  // if (kChess == 1) return nLevel;
        }

        for (int i = 1; i < dp.length; i++) { // nLevel < 1必定是0，所以從1開始處理
            for (int j = 2; j < dp[0].length; j++) { // dp[i][1]已經初始化，所以從2開始處理
                int min = Integer.MAX_VALUE;
                for (int k = 1; k < i + 1; k++) { // 從Process1 for loop導出的(i < nLevel + 1;)

                    // 與Process1相同有兩種可能:
                    // 1. 棋碎了剩下j - 1顆，k以上的樓層不用繼續嘗試了，從k - 1開始嘗試
                    // 2. 棋沒碎，k以下的樓層不用繼續嘗試了，樓層剩下i - k
                    min = Math.min(min,
                            Math.max(dp[k - 1][j - 1], dp[i - k][j]));
                }
                dp[i][j] = min + 1;
            }
        }
        return dp[nLevel][kChess];
    }

    public static int solution3(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) return nLevel;
        int[] preArr = new int[nLevel + 1];
        int[] curArr = new int[nLevel + 1];
        for (int i = 1; i != curArr.length; i++) {
            curArr[i] = i;
        }
        for (int i = 1; i != kChess; i++) {
            int[] tmp = preArr;
            preArr = curArr;
            curArr = tmp;
            for (int j = 1; j != curArr.length; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 1; k != j + 1; k++) {
                    min = Math.min(min, Math.max(preArr[k - 1], curArr[j - k]));
                }
                curArr[j] = min + 1;
            }
        }
        return curArr[curArr.length - 1];
    }

    // dp問題如果需要枚舉，可以考慮四邊形不等式
    public static int solution4(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kChess + 1];
        for (int i = 1; i != dp.length; i++) {
            dp[i][1] = i;
        }
        int[] cands = new int[kChess + 1];
        for (int i = 1; i != dp[0].length; i++) {
            dp[1][i] = 1;
            cands[i] = 1;
        }
        for (int i = 2; i < nLevel + 1; i++) {
            for (int j = kChess; j > 1; j--) {
                int min = Integer.MAX_VALUE;
                int minEnum = cands[j];
                int maxEnum = j == kChess ? i / 2 + 1 : cands[j + 1];
                for (int k = minEnum; k < maxEnum + 1; k++) {
                    int cur = Math.max(dp[k - 1][j - 1], dp[i - k][j]);
                    if (cur <= min) {
                        min = cur;
                        cands[j] = k;
                    }
                }
                dp[i][j] = min + 1;
            }
        }
        return dp[nLevel][kChess];
    }

    // 貪心算法
    public static int solution5(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        int bsTimes = log2N(nLevel) + 1; // 理論上二分法可以求得最少次數
        if (kChess >= bsTimes) {
            return bsTimes;
        }

        // kChess數量不足，二分法沒有找到答案，從零開始嘗試
        // map[i][j] = map[i][j-1] + map[i-1][j-1] + 1，map[i][j]意義是i顆棋子扔j次可以搞定最大樓層數
        // map[i][j-1]表示第i棋子沒碎，繼續看i顆棋子扔j-1次可以搞定最大樓層數
        // map[i-1][j-1]表示第i棋子碎了，看剩下i-1顆棋子扔j-1次可以搞定最大樓層數
        // 別忘了加上目前所在的1層樓
        // 此處已經降維優化，用一行dp處理map
        int[] dp = new int[kChess];
        int res = 0;
        while (true) {
            res++;
            int previous = 0;
            for (int i = 0; i < dp.length; i++) {
                int tmp = dp[i];
                dp[i] = dp[i] + previous + 1;
                previous = tmp;
                if (dp[i] >= nLevel) {
                    return res;
                }
            }
        }
    }

    public static int log2N(int n) {
        int res = -1;
        while (n != 0) {
            res++;
            n >>>= 1;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(solution1(21, 2));
        System.out.println(solution2(21, 2));
        System.out.println(solution3(21, 2));
        System.out.println(solution4(21, 2));
        System.out.println(solution5(21, 2));

        System.out.println("==============");

        System.out.println(solution2(105, 2));
        System.out.println(solution3(105, 2));
        System.out.println(solution4(105, 2));
        System.out.println(solution5(105, 2));

        System.out.println("==============");

        System.out.println(solution2(3000, 10));
        System.out.println(solution3(3000, 10));
        System.out.println(solution4(3000, 10));
        System.out.println(solution5(3000, 10));

        System.out.println("==============");

        System.out.println(solution2(6884, 5));
        System.out.println(solution3(6884, 5));
        System.out.println(solution4(6884, 5));
        System.out.println(solution5(6884, 5));

        System.out.println("==============");

        System.out.println(solution2(6885, 5));
        System.out.println(solution3(6885, 5));
        System.out.println(solution4(6885, 5));
        System.out.println(solution5(6885, 5));

        System.out.println("==============");

        int nLevel = 100000000;
        int kChess = 10;
        long start = System.currentTimeMillis();
        System.out.println(solution5(nLevel, kChess));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");

    }
}
