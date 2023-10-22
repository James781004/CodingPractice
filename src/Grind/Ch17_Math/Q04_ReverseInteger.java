package Grind.Ch17_Math;

public class Q04_ReverseInteger {
    // https://leetcode.cn/problems/reverse-integer/solutions/7429/hua-jie-suan-fa-7-zheng-shu-fan-zhuan-by-guanpengc/
    public int reverse(int x) {
        int ans = 0;
        while (x != 0) {
            int pop = x % 10;

            // 當出現 ans > MAX_VALUE / 10 且 還有pop需要添加 時，則一定溢出
            // 當出現 ans == MAX_VALUE / 10 且 pop > 7 時，則一定溢出，7 是 2^31 - 1 的個位數
            if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && pop > 7))
                return 0;

            // 當出現 ans < MIN_VALUE / 10 且 還有pop需要添加 時，則一定溢出
            // 當出現 ans == MIN_VALUE / 10 且 pop < -8 時，則一定溢出，8 是-2^31 的個位數
            if (ans < Integer.MIN_VALUE / 10 || (ans == Integer.MIN_VALUE / 10 && pop < -8))
                return 0;
            
            ans = ans * 10 + pop;
            x /= 10;
        }
        return ans;
    }
}
