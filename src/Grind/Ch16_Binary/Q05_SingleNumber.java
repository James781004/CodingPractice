package Grind.Ch16_Binary;

public class Q05_SingleNumber {
    // https://leetcode.cn/problems/single-number/solutions/5118/xue-suan-fa-jie-guo-xiang-dui-yu-guo-cheng-bu-na-y/
    // https://labuladong.github.io/algo/di-san-zha-24031/shu-xue-yu-659f1/chang-yong-13a76/
    // 運用異或運算的性質：
    // 一個數和它本身做異或運算結果為 0，即 a ^ a = 0；一個數和 0 做異或運算的結果為它本身，即 a ^ 0 = a
    public int singleNumber(int[] nums) {
        int n = 0;
        for (int num : nums) {
            n = n ^ num;
        }
        return n;
    }
}
