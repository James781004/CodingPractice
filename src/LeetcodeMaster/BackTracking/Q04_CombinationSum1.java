package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q04_CombinationSum1 {
    //    39. 組合總和
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0039.%E7%BB%84%E5%90%88%E6%80%BB%E5%92%8C.md
//
//    給定一個無重覆元素的數組 candidates 和一個目標數 target ，找出 candidates 中所有可以使數字和為 target 的組合。
//
//    candidates 中的數字可以無限制重覆被選取。
//
//    說明：
//
//    所有數字（包括 target）都是正整數。
//    解集不能包含重覆的組合。
//    示例 1： 輸入：candidates = [2,3,6,7], target = 7, 所求解集為： [ [7], [2,2,3] ]
//
//    示例 2： 輸入：candidates = [2,3,5], target = 8, 所求解集為： [   [2,2,2,2],   [2,3,3],   [3,5] ]


    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates); // 必須先排序
        process(res, new ArrayList<>(), candidates, target, 0, 0);
        return res;
    }

    public void process(List<List<Integer>> res, List<Integer> path, int[] candidates,
                        int target, int sum, int idx) {
        if (sum == target) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = idx; i < candidates.length; i++) {
            if (sum + candidates[i] > target) break; // 如果 sum + candidates[i] > target 就終止遍歷
            path.add(candidates[i]);
            process(res, path, candidates, target, sum + candidates[i], i); // 可以重覆讀取當前的數，所以index不用變化
            path.remove(path.size() - 1); // 回溯，移除路徑 path 最後一個元素
        }
    }
}
