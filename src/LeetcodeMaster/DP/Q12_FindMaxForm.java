package LeetcodeMaster.DP;

public class Q12_FindMaxForm {
//    474.一和零
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0474.%E4%B8%80%E5%92%8C%E9%9B%B6.md
//
//    給你一個二進制字符串數組 strs 和兩個整數 m 和 n 。
//
//    請你找出並返回 strs 的最大子集的大小，該子集中 最多 有 m 個 0 和 n 個 1 。
//
//    如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集 。
//
//    示例 1：
//
//    輸入：strs = ["10", "0001", "111001", "1", "0"], m = 5, n = 3 輸出：4
//
//    解釋：最多有 5 個 0 和 3 個 1 的最大子集是 {"10","0001","1","0"} ，因此答案是 4 。
//    其他滿足題意但較小的子集包括 {"0001","1"} 和 {"10","1","0"} 。{"111001"} 不滿足題意，因為它含 4 個 1 ，大於 n 的值 3 。
//
//    示例 2： 輸入：strs = ["10", "0", "1"], m = 1, n = 1 輸出：2 解釋：最大的子集是 {"0", "1"} ，所以答案是 2 。
//
//    提示：
//
//            1 <= strs.length <= 600
//            1 <= strs[i].length <= 100
//    strs[i] 僅由 '0' 和 '1' 組成


    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1]; // dp[i][j]表示i個0和j個1時的最大子集
        int oneNum, zeroNum;
        for (String str : strs) {
            oneNum = 0;
            zeroNum = 0;
            for (char ch : str.toCharArray()) {
                if (ch == '0') {
                    zeroNum++;
                } else {
                    oneNum++;
                }
            }

            for (int i = m; i >= zeroNum; i--) { // 倒序遍歷
                for (int j = n; j >= oneNum; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - zeroNum][j - oneNum] + 1);
                }
            }
        }
        return dp[m][n];
    }
}
