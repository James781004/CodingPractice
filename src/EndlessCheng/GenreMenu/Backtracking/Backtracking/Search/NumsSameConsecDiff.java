package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.ArrayList;
import java.util.List;

public class NumsSameConsecDiff {

    // https://leetcode.cn/problems/numbers-with-same-consecutive-differences/solutions/673945/dfs-jian-dan-ming-liao-by-zhangqiang-9-zwn9/
    List<Integer> res = new ArrayList<>();

    public int[] numsSameConsecDiff(int n, int k) {
        // 先初始化開頭第一個字符，進入遞歸
        for (int i = 1; i <= 9; i++) {
            // 如果i+k或者i-k都不是有效的字符，說明當前i不能作為數字的開頭
            if (i + k > 9 && i - k < 0) continue;
            // 進入遞歸
            DFS(new StringBuilder(String.valueOf(i)), n, k);
        }
        int[] nums = new int[res.size()];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = res.get(i);
        }
        return nums;
    }

    public void DFS(StringBuilder str, int n, int k) {
        // 長度到了就進入結果
        if (str.length() == n) {
            res.add(Integer.parseInt(str.toString()));
            return;
        }
        // 獲取上一個數字是什麼
        int num = str.charAt(str.length() - 1) - '0';
        // num+k 可以加入到str中，並且開始遞歸
        if (num + k < 10) {
            str.append(num + k);
            DFS(str, n, k);
            str.deleteCharAt(str.length() - 1);
        }
        // num-k 可以加入到str中並且開始遞歸
        if (num - k >= 0 && k != 0) {
            str.append(num - k);
            DFS(str, n, k);
            str.deleteCharAt(str.length() - 1);
        }
    }


}
