package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

public class MinimumOperations {

    // https://leetcode.cn/problems/UlBDOe/solutions/433334/java-quan-zhu-shi-li-jie-dong-tai-gui-hua-by-leetc/
    public int minimumOperations(String leaves) {
        if (leaves == null || leaves == "") {   // 排除 不合法參數情況
            return 0;
        }
        int length = leaves.length();
        char[] chars = leaves.toCharArray();

        /*
            狀態數組，state[i][j]中：
                i表示終止下標
                j表示：0為左半邊，1為中間部分，2為右半邊
            state[i][j] 表示 從0到i需要調整的葉子數
         */
        int[][] state = new int[length][3];

        /*
            記錄 已知狀態數組元素：
                1、第一個葉子，必須是左半部分，所以只需判斷是不是 黃色葉子 即可
                2、第一個葉子，必須是左半部分，所以 state[0][1] 和 state[0][2] 都是無效的
                3、第二個葉子，可以是左半部分，也可以是中間部分，但是不能是右半部分(每個區間必須有葉子)，
                    因此 state[1][2]是無效的
         */
        state[0][0] = chars[0] == 'y' ? 1 : 0;
        state[0][1] = state[0][2] = state[1][2] = Integer.MAX_VALUE;

        int isYellow = 0;   // 判斷 當前遍歷的葉子 是不是 黃色

        /*
            遍歷 原葉集，生成狀態數組
         */
        for (int i = 1; i < length; i++) {
            isYellow = chars[i] == 'y' ? 1 : 0;
            state[i][0] = state[i - 1][0] + isYellow;
            state[i][1] = Math.min(state[i - 1][0], state[i - 1][1]) + (1 - isYellow);
            if (i > 1) {    // 右半部分 的葉子 必須是第2個元素之後的元素
                state[i][2] = Math.min(state[i - 1][1], state[i - 1][2]) + isYellow;
            }
        }

        /*
            最終結果 為 state[length - 1][2]
            因為 state[i][j]最終結果的 i 必須為 length - 1，state[length - 1][j] 中的 j 必須為2
         */
        return state[length - 1][2];
    }

}
