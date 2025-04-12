package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

public class JudgeSquareSum {

    // https://leetcode.cn/problems/sum-of-square-numbers/solutions/2973811/liang-chong-fang-fa-mei-ju-shuang-zhi-zh-c26z/
    // 枚舉
    public boolean judgeSquareSum(int c) {
        for (int a = 0; a * a <= c / 2; a++) {
            int b = (int) Math.sqrt(c - a * a);
            if (a * a + b * b == c) {
                return true;
            }
        }
        return false;
    }


    // 相向雙指針
    public boolean judgeSquareSum2(int c) {
        int a = 0;
        int b = (int) Math.sqrt(c);
        while (a <= b) {
            if (a * a == c - b * b) { // 避免溢出
                return true;
            }
            if (a * a < c - b * b) {
                a++;
            } else {
                b--;
            }
        }
        return false;
    }

}
