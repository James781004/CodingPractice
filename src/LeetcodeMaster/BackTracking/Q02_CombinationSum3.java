package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q02_CombinationSum3 {
//    216.組合總和III
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0216.%E7%BB%84%E5%90%88%E6%80%BB%E5%92%8CIII.md
//
//    找出所有相加之和為 n 的 k 個數的組合。組合中只允許含有 1 - 9 的正整數，並且每種組合中不存在重覆的數字。
//
//    說明：
//
//    所有數字都是正整數。
//    解集不能包含重覆的組合。
//    示例 1: 輸入: k = 3, n = 7 輸出: [[1,2,4]]
//
//    示例 2: 輸入: k = 3, n = 9 輸出: [[1,2,6], [1,3,5], [2,3,4]]

    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();

    public List<List<Integer>> combinationSum(int k, int n) {
        process(n, k, 1, 0);
        return result;
    }

    private void process(int targetSum, int k, int startIndex, int sum) {
        // 剪枝
        if (sum > targetSum) {
            return;
        }

        // 達到目標
        if (path.size() == k) {
            if (sum == targetSum) result.add(new ArrayList<>(path));
            return;
        }

        // 剪枝: 9(集合總數) - (k - path.size()) + 1
        for (int i = startIndex; i <= 9 - (k - path.size()) + 1; i++) {
            path.add(i);
            sum += i;
            process(targetSum, k, i + 1, sum); // 遞歸，startIndex作為起始位置記得+1

            // 回溯
            path.removeLast();
            sum -= i;
        }
    }
}
