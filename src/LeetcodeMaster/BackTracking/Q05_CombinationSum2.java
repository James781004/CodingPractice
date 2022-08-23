package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Q05_CombinationSum2 {
//    40.組合總和II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0040.%E7%BB%84%E5%90%88%E6%80%BB%E5%92%8CII.md
//
//    給定一個數組 candidates 和一個目標數 target ，找出 candidates 中所有可以使數字和為 target 的組合。
//
//    candidates 中的每個數字在每個組合中只能使用一次。
//
//    說明： 所有數字（包括目標數）都是正整數。 解集不能包含重覆的組合。
//
//    示例 1: 輸入: candidates = [10,1,2,7,6,1,5], target = 8, 所求解集為: [ [1, 7], [1, 2, 5], [2, 6], [1, 1, 6] ]
//
//    示例 2: 輸入: candidates = [2,5,2,1,2], target = 5, 所求解集為: [   [1,2,2],   [5] ]


    List<List<Integer>> res = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();
    int sum = 0;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // 為了將重複的數字都放到一起，所以先進行排序
        Arrays.sort(candidates);
        process(candidates, target, 0);
        return res;
    }

    private void process(int[] candidates, int target, int index) {
        if (sum == target) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = index; i < candidates.length && sum + candidates[i] <= target; i++) {
            // 正確剔除重複解的辦法
            // 跳過同一樹層使用過的元素
            if (i > index && candidates[i] == candidates[i - 1]) {
                continue;
            }

            sum += candidates[i];
            path.add(candidates[i]);
            // i+1 代表當前組內元素只選取一次
            process(candidates, target, i + 1);

            int temp = path.getLast();
            sum -= temp;
            path.removeLast();
        }
    }
}
