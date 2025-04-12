package EndlessCheng.GenreMenu.MonoStack.Lexicographic;

public class MostCompetitive {

    // https://leetcode.cn/problems/find-the-most-competitive-subsequence/solutions/2788312/gen-zhao-wo-guo-yi-bian-shi-li-2ni-jiu-m-36c4/
    public int[] mostCompetitive(int[] nums, int k) {
        int n = nums.length;
        int[] st = new int[k]; // 用數組模擬棧（棧容量為 k）
        int m = 0; // 棧的大小
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            while (m > 0 && x < st[m - 1] && m + n - i > k) {
                m--; // 出棧
            }
            if (m < k) {
                st[m++] = x; // 入棧
            }
        }
        return st;
    }


}
