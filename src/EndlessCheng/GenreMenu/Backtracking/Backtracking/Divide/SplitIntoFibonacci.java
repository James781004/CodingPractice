package EndlessCheng.GenreMenu.Backtracking.Backtracking.Divide;

import java.util.ArrayList;
import java.util.List;

public class SplitIntoFibonacci {

    // https://leetcode.cn/problems/split-array-into-fibonacci-sequence/solutions/513430/javahui-su-suan-fa-tu-wen-xiang-jie-ji-b-vg5z/
    public List<Integer> splitIntoFibonacci(String S) {
        List<Integer> res = new ArrayList<>();
        backtrack(S.toCharArray(), res, 0);
        return res;
    }

    public boolean backtrack(char[] digit, List<Integer> res, int index) {
        //邊界條件判斷，如果截取完了，並且res長度大於等於3，表示找到了一個組合。
        if (index == digit.length && res.size() >= 3) {
            return true;
        }
        for (int i = index; i < digit.length; i++) {
            //兩位以上的數字不能以0開頭
            if (digit[index] == '0' && i > index) {
                break;
            }
            //截取字符串轉化為數字
            long num = subDigit(digit, index, i + 1);
            //如果截取的數字大於int的最大值，則終止截取
            if (num > Integer.MAX_VALUE) {
                break;
            }
            int size = res.size();
            //如果截取的數字大於res中前兩個數字的和，說明這次截取的太大，直接終止，因為後面越截取越大
            if (size >= 2 && num > res.get(size - 1) + res.get(size - 2)) {
                break;
            }
            if (size <= 1 || num == res.get(size - 1) + res.get(size - 2)) {
                //把數字num添加到集合res中
                res.add((int) num);
                //如果找到了就直接返回
                if (backtrack(digit, res, i + 1))
                    return true;
                //如果沒找到，就會走回溯這一步，然後把上一步添加到集合res中的數字給移除掉
                res.remove(res.size() - 1);
            }
        }
        return false;
    }

    //相當於截取字符串S中的子串然後轉換為十進制數字
    private long subDigit(char[] digit, int start, int end) {
        long res = 0;
        for (int i = start; i < end; i++) {
            res = res * 10 + digit[i] - '0';
        }
        return res;
    }


}
