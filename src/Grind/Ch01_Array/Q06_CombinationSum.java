package Grind.Ch01_Array;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Q06_CombinationSum {
    // https://leetcode.com/problems/combination-sum/
    // https://leetcode.cn/problems/combination-sum/solutions/14697/hui-su-suan-fa-jian-zhi-python-dai-ma-java-dai-m-2/
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        int len = candidates.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }

        Deque<Integer> path = new ArrayDeque<>();
        dfs(candidates, 0, len, target, path, res);
        return res;
    }

    /**
     * @param candidates 候選數組
     * @param begin      搜索起點
     * @param len        冗余變量，是 candidates 裡的屬性，可以不傳
     * @param target     每減去一個元素，目標值變小
     * @param path       從根結點到葉子結點的路徑，是一個棧
     * @param res        結果集列表
     */
    private void dfs(int[] candidates, int begin, int len, int target, Deque<Integer> path, List<List<Integer>> res) {
        // target 為負數和 0 的時候不再產生新的孩子結點
        if (target < 0) {
            return;
        }

        // target 為 0 的時候記錄走過的路徑
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        // 重點理解這裡從 begin 開始搜索的語意
        for (int i = begin; i < len; i++) {
            path.addLast(candidates[i]);

            // 注意：由於每一個元素可以重復使用，下一輪搜索的起點依然是 i，這裡非常容易弄錯
            dfs(candidates, i, len, target - candidates[i], path, res);

            // 狀態重置
            path.removeLast();
        }
    }
}
