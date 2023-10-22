package Grind.Ch16_Binary;

public class Q07_ReverseBits {
    // https://leetcode.cn/problems/reverse-bits/solutions/52589/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-4-9/
    public int reverseBits(int n) {
        int res = 0;
        int count = 0;
        while (count < 32) {
            res <<= 1;  // res 左移一位空出位置
            res |= (n & 1); // 得到的最低位加過來
            n >>= 1; // 原數字右移一位去掉已經處理過的最低位
            count++;
        }
        return res;
    }
}
