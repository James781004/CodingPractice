package 程序员代码面试指南.ch04;

public class Q04_RobotWalk {
//    機器人達到指定位置方法數
//    描述
//    假設有排成一行的N個位置，記為1~N，
//    開始時機器人在M位置，機器人可以往左或者往右走，
//    如果機器人在1位置，那麽下一步機器人只能走到2位置，
//    如果機器人在N位置，那麽下一步機器人只能走到N-1位置。
//    規定機器人只能走k步，最終能來到P位置的方法有多少種。
//    由於方案數可能比較大，所以答案需要對1e9+7取模。


    // 我們先可以分析這個過程，記機器人當前的位置為cur，終點位置為end，還剩下能走的步數為rest。
    // 則針對機器人此時所在的不同位置，有如下的三種情況：
    // 1. 機器人在左邊界1，那它下一步只能往右走；
    // 2. 機器人在右邊界n，那它下一步只能往左走；
    // 3. 機器人在中間位置1<i<n，它下一步可以往左也可以往右。
    // 於是我們先可以寫出一個遞歸的嘗試版本
    public static int ways1(int N, int M, int K, int P) {
        // 參數無效直接返回0
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        // 總共N個位置，從M點出發，還剩K步，返回最終能達到P的方法數
        return walk(N, M, K, P);
    }

    // N : 位置為1 ~ N，固定參數
    // cur : 當前在cur位置，可變參數
    // rest : 還剩res步沒有走，可變參數
    // P : 最終目標位置是P，固定參數
    // 該函數的含義：只能在1~N這些位置上移動，當前在cur位置，走完rest步之後，停在P位置的方法數作為返回值返回
    public static int walk(int N, int cur, int rest, int P) {
        // 如果沒有剩余步數了，當前的cur位置就是最後的位置
        // 如果最後的位置停在P上，那麽之前做的移動是有效的
        // 如果最後的位置沒在P上，那麽之前做的移動是無效的
        if (rest == 0) {
            return cur == P ? 1 : 0;
        }

        // 如果還有rest步要走，而當前的cur位置在1位置上，那麽當前這步只能從1走向2
        // 後續的過程就是，來到2位置上，還剩rest-1步要走
        if (cur == 1) {
            return walk(N, 2, rest - 1, P);
        }

        // 如果還有rest步要走，而當前的cur位置在N位置上，那麽當前這步只能從N走向N-1
        // 後續的過程就是，來到N-1位置上，還剩rest-1步要走
        if (cur == N) {
            return walk(N, N - 1, rest - 1, P);
        }

        // 如果還有rest步要走，而當前的cur位置在中間位置上，那麽當前這步可以走向左，也可以走向右
        // 走向左之後，後續的過程就是，來到cur-1位置上，還剩rest-1步要走
        // 走向右之後，後續的過程就是，來到cur+1位置上，還剩rest-1步要走
        // 走向左、走向右是截然不同的方法，所以總方法數要都算上
        return walk(N, cur + 1, rest - 1, P) + walk(N, cur - 1, rest - 1, P);
    }

    // 記憶化搜索版本：
    public static int ways1_5(int N, int M, int K, int P) {
        // 參數無效直接返回0
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        int[][] memory = new int[K + 1][N + 1];
        for (int i = 0; i <= K; i++) {
            for (int j = 0; j <= N; j++) {
                memory[i][j] = -1;
            }
        }

        // 總共N個位置，從M點出發，還剩K步，返回最終能達到P的方法數
        return walkMemory(N, M, K, P, memory);
    }

    public static int walkMemory(int N, int cur, int rest, int P, int[][] memory) {

        // 有記憶，直接返回
        if (memory[rest][cur] != -1) return memory[rest][cur];


        // 如果沒有剩余步數了，當前的cur位置就是最後的位置
        // 如果最後的位置停在P上，那麽之前做的移動是有效的
        // 如果最後的位置沒在P上，那麽之前做的移動是無效的
        if (rest == 0) {
            memory[rest][cur] = cur == P ? 1 : 0;
        } else if (cur == 1) {
            // 如果還有rest步要走，而當前的cur位置在1位置上，那麽當前這步只能從1走向2
            // 後續的過程就是，來到2位置上，還剩rest-1步要走
            memory[rest][cur] = walkMemory(N, 2, rest - 1, P, memory);
        } else if (cur == N) {
            // 如果還有rest步要走，而當前的cur位置在N位置上，那麽當前這步只能從N走向N-1
            // 後續的過程就是，來到N-1位置上，還剩rest-1步要走
            memory[rest][cur] = walkMemory(N, N - 1, rest - 1, P, memory);
        } else {
            // 如果還有rest步要走，而當前的cur位置在中間位置上，那麽當前這步可以走向左，也可以走向右
            // 走向左之後，後續的過程就是，來到cur-1位置上，還剩rest-1步要走
            // 走向右之後，後續的過程就是，來到cur+1位置上，還剩rest-1步要走
            // 走向左、走向右是截然不同的方法，所以總方法數要都算上
            memory[rest][cur] = walkMemory(N, cur + 1, rest - 1, P, memory) +
                    walkMemory(N, cur - 1, rest - 1, P, memory);
        }


        return memory[rest][cur];
    }

    // 總共N個位置，從M點出發，還剩K步，返回最終能達到P的方法數
    public static int ways2(int N, int M, int K, int P) {
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) return 0;
        int[][] dp = new int[K + 1][N + 1]; // dp[剩餘步數][目前到達位置]
        dp[0][P] = 1; // 遞歸方法邊界條件：if (rest == 0) return cur == P ? 1 : 0;
        for (int i = 1; i <= K; i++) {
            for (int j = 1; j <= N; j++) {
                if (j == 1) {
                    // 如果還有K步要走，而當前的cur位置在1位置上，那麽當前這步只能從1走向2
                    // 後續的過程就是，來到2位置上，還剩K-1步要走
                    dp[i][j] = dp[i - 1][2];
                } else if (j == N) {
                    // 如果還有K步要走，而當前的cur位置在N位置上，那麽當前這步只能從N走向N-1
                    // 後續的過程就是，來到N-1位置上，還剩K-1步要走
                    dp[i][j] = dp[i - 1][N - 1];
                } else {
                    // 如果還有K步要走，而當前的cur位置在中間位置上，那麽當前這步可以走向左，也可以走向右
                    // 走向左之後，後續的過程就是，來到cur-1位置上，還剩rest-1步要走
                    // 走向右之後，後續的過程就是，來到cur+1位置上，還剩rest-1步要走
                    // 走向左、走向右是截然不同的方法，所以總方法數要都算上
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j + 1];
                }
            }
        }

        return dp[K][M];
    }

    public static int ways3(int N, int M, int K, int P) {
        // 參數無效直接返回0
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        int[] dp = new int[N + 1];
        dp[P] = 1;
        for (int i = 1; i <= K; i++) {
            int leftUp = dp[1];// 左上角的值
            for (int j = 1; j <= N; j++) {
                int tmp = dp[j];
                if (j == 1) {
                    dp[j] = dp[j + 1];
                } else if (j == N) {
                    dp[j] = leftUp;
                } else {
                    dp[j] = leftUp + dp[j + 1];
                }
                leftUp = tmp;
            }
        }
        return dp[M];
    }

    public static void main(String[] args) {
        System.out.println(ways1(7, 4, 9, 5));
        System.out.println(ways1_5(7, 4, 9, 5));
        System.out.println(ways2(7, 4, 9, 5));
        System.out.println(ways3(7, 4, 9, 5));
    }
}
