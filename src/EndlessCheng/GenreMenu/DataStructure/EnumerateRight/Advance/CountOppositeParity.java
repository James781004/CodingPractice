package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

public class CountOppositeParity {

    // https://leetcode.cn/problems/count-indices-with-opposite-parity/solutions/3962727/on-zuo-fa-mei-ju-zuo-wei-hu-you-pythonja-oeia/
    public int[] countOppositeParity(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        int[] cnt = new int[2];
        for (int i = n - 1; i >= 0; i--) {
            int x = nums[i] & 1; // x 的奇偶性
            ans[i] = cnt[x ^ 1]; // 查詢右側奇偶性不等於 x（即 x^1）的元素個數
            cnt[x]++;
        }
        return ans;
    }

}
