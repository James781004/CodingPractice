package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborNonRelated;

public class MaxCompatibilitySum {

    // https://leetcode.cn/problems/maximum-compatibility-score-sum/solutions/895170/om3-er-fen-tu-zui-da-dai-quan-pi-pei-by-a0wqb/
    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        int personSize = students.length;
        int problemSize = students[0].length;
        // 由於題目數量<=8，可以將每個人的調查問卷結果進行二進制壓縮
        int[] compressedS = new int[personSize];
        int[] compressedM = new int[personSize];
        for (int i = 0; i < personSize; i++) {
            int studentRes = 0;
            int mentorRes = 0;
            for (int j = 0; j < problemSize; j++) {
                studentRes += students[i][j] == 1 ? (1 << j) : 0;
                mentorRes += mentors[i][j] == 1 ? (1 << j) : 0;
            }
            compressedS[i] = studentRes;
            compressedM[i] = mentorRes;
        }

        // 由於學生人數數量<=8，對學生的全排列集合進行二進制壓縮
        int setSize = 1 << personSize;
        // dp[s]: 需要選擇的學生集合為s時，最大兼容性評分和
        int[] dp = new int[setSize];

        for (int s = 1; s < setSize; s++) {
            // 已選擇的學生個數=待選擇的導師下標
            int i = personSize - Integer.bitCount(s);
            int maxScore = 0;
            for (int j = 0; j < personSize; j++) {
                // 枚舉待選擇的學生
                if ((s >> j & 1) == 1) {
                    // 答案相同的題目個數 = 題目總數 - 答案不同的題目個數
                    int curScore = problemSize - Integer.bitCount(compressedM[i] ^ compressedS[j]);
                    maxScore = Math.max(maxScore, curScore + dp[s ^ (1 << j)]);
                }
                dp[s] = maxScore;
            }
        }
        return dp[setSize - 1];
    }

}
