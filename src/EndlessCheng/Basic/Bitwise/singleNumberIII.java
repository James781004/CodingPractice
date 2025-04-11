package EndlessCheng.Basic.Bitwise;

public class singleNumberIII {

    // https://leetcode.cn/problems/single-number-iii/solutions/2484352/tu-jie-yi-zhang-tu-miao-dong-zhuan-huan-np9d2/
    public int[] singleNumber(int[] nums) {
        int xorAll = 0;
        for (int x : nums) {
            xorAll ^= x;
        }
        int lowbit = xorAll & -xorAll;
        int[] ans = new int[2];
        for (int x : nums) {
            ans[(x & lowbit) == 0 ? 0 : 1] ^= x; // 分組異或
        }
        return ans;
    }


    public int[] singleNumber2(int[] nums) {
        int xorAll = 0;
        for (int x : nums) {
            xorAll ^= x;
        }
        int tz = Integer.numberOfTrailingZeros(xorAll);
        int[] ans = new int[2];
        for (int x : nums) {
            ans[x >>> tz & 1] ^= x;
        }
        return ans;
    }


}
