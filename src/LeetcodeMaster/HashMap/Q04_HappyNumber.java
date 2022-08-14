package LeetcodeMaster.HashMap;

import java.util.HashSet;
import java.util.Set;

public class Q04_HappyNumber {
//    第202題. 快樂數
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0202.%E5%BF%AB%E4%B9%90%E6%95%B0.md
//
//    編寫一個算法來判斷一個數 n 是不是快樂數。
//
//   快樂數定義為：對於一個正整數，每一次將該數替換為它每個位置上的數字的平方和，然後重覆這個過程直到這個數變為 1，
//   也可能是 無限循環 但始終變不到 1。如果 可以變為  1，那麽這個數就是快樂數。
//
//    如果 n 是快樂數就返回 True ；不是，則返回 False 。
//    示例：
//
//    輸入：19
//    輸出：true
//    解釋：
//            1^2 + 9^2 = 82
//            8^2 + 2^2 = 68
//            6^2 + 8^2 = 100
//            1^2 + 0^2 + 0^2 = 1

    public boolean isHappy(int n) {
        Set<Integer> record = new HashSet<>();
        while (n != 0 && !record.contains(n)) {
            record.add(n);
            n = getNextNumber(n);
        }
        return n == 1;
    }

    private int getNextNumber(int n) {
        int res = 0;
        int temp = 0;
        while (n > 0) {
            temp = n % 10;
            res += temp * temp;
            n /= 10;
        }
        return res;
    }

}
