package FuckingAlgorithm.Math;

public class Q05_SuperPow {
//    https://leetcode.cn/problems/super-pow/
//    372. 超級次方
//    你的任務是計算 ab 對 1337 取模，a 是一個正整數，b 是一個非常大的正整數且會以數組形式給出。

    class Solution {
        int base = 1337;

        public int superPow(int a, int[] b) {
            return superP(a, b, b.length);
        }

        private int superP(int a, int[] b, int len) {
            if (len == 0) return 1;
            int last = b[len - 1];
            len--;
            //拆分 成兩個部分 一個部分是將數組末尾的數字計算取模
            //另一個是將剩余的 還有個10次方取模
            int part1 = myPow(a, last);
            int part2 = myPow(superP(a, b, len), 10);
            return (part2 * part1) % base;
        }

        // 求 a^b 對 base取模的結果
        private int myPow(int a, int b) {
            if (b == 0) return 1;
            a = a % base;

            if (b % 2 == 1) {
                return (a * myPow(a, b - 1)) % base;
            } else {
                int sub = myPow(a, b / 2);
                return (sub * sub) % base;
            }
        }
    }

}
