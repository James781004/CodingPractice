package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.ArrayList;
import java.util.List;

public class JudgePoint24 {

    // https://leetcode.cn/problems/24-game/solutions/820337/java-hui-su-jing-dian-mian-shi-ti-by-air-pj1k/
    private static final double TARGET = 24;
    private static final double EPISLON = 1e-6;

    public boolean judgePoint24(int[] cards) {
        return helper(new double[]{cards[0], cards[1], cards[2], cards[3]});
    }

    private boolean helper(double[] nums) {
        if (nums.length == 1) return Math.abs(nums[0] - TARGET) < EPISLON;
        // 每次選擇兩個不同的數進行回溯
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                // 將選擇出來的兩個數的計算結果和原數組剩下的數加入 next 數組
                double[] next = new double[nums.length - 1];
                for (int k = 0, pos = 0; k < nums.length; k++) if (k != i && k != j) next[pos++] = nums[k];
                for (double num : calculate(nums[i], nums[j])) {
                    next[next.length - 1] = num;
                    if (helper(next)) return true;
                }
            }
        }
        return false;
    }

    private List<Double> calculate(double a, double b) {
        List<Double> list = new ArrayList<>();
        list.add(a + b);
        list.add(a - b);
        list.add(b - a);
        list.add(a * b);
        if (!(Math.abs(b) < EPISLON)) list.add(a / b);
        if (!(Math.abs(a) < EPISLON)) list.add(b / a);
        return list;
    }


}
