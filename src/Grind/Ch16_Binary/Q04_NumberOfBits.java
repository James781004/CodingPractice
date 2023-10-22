package Grind.Ch16_Binary;

public class Q04_NumberOfBits {
    // https://leetcode.cn/problems/number-of-1-bits/solutions/276037/javade-17chong-jie-fa-by-sdwwld/
    // 每次消去最右邊的1，直到消完為止
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= n - 1;
            count++;
        }
        return count;
    }
}
