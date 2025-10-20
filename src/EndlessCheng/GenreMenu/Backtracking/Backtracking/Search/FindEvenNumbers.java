package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.ArrayList;
import java.util.List;

public class FindEvenNumbers {

    // https://leetcode.cn/problems/finding-3-digit-even-numbers/solutions/1139403/mei-ju-suo-you-san-wei-shu-ou-shu-by-end-8n7d/
    public int[] findEvenNumbers(int[] digits) {
        int[] cnt = new int[10];
        for (int d : digits) {
            cnt[d]++;
        }

        List<Integer> ans = new ArrayList<>();
        dfs(0, 0, cnt, ans);
        return ans.stream().mapToInt(i -> i).toArray();
    }

    // i=0 百位，i=1 十位，i=2 個位，x 表示當前正在構造的數字
    private void dfs(int i, int x, int[] cnt, List<Integer> ans) {
        if (i == 3) {
            ans.add(x);
            return;
        }
        for (int d = 0; d < 10; d++) {
            // 百位數不能填 0
            // 十位數隨便填
            // 個位數只能填偶數
            // 填的數字個數必須有剩余
            if (cnt[d] > 0 && (i == 0 && d > 0 || i == 1 || i == 2 && d % 2 == 0)) {
                cnt[d]--; // 消耗一個數字 d
                dfs(i + 1, x * 10 + d, cnt, ans);
                cnt[d]++; // 復原
            }
        }
    }


}
