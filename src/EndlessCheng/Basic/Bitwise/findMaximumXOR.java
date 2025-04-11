package EndlessCheng.Basic.Bitwise;

import java.util.HashSet;
import java.util.Set;

public class findMaximumXOR {

    // https://leetcode.cn/problems/maximum-xor-of-two-numbers-in-an-array/solutions/2511644/tu-jie-jian-ji-gao-xiao-yi-tu-miao-dong-1427d/
    public int findMaximumXOR(int[] nums) {
        int max = 0;
        for (int x : nums) {
            max = Math.max(max, x);
        }
        int highBit = 31 - Integer.numberOfLeadingZeros(max);

        int ans = 0, mask = 0;
        Set<Integer> seen = new HashSet<>();
        for (int i = highBit; i >= 0; i--) { // 從最高位開始枚舉
            seen.clear();
            mask |= 1 << i; // 按位或，只要碰到1結果就全是1
            int newAns = ans | (1 << i); // 這個比特位可以是 1 嗎？
            for (int x : nums) {
                x &= mask; // 低於 i 的比特位置為 0
                if (seen.contains(newAns ^ x)) {
                    ans = newAns; // 這個比特位可以是 1
                    break;
                }
                seen.add(x);
            }
        }
        return ans;
    }


}
