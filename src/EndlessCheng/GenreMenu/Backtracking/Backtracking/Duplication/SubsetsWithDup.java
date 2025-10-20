package EndlessCheng.GenreMenu.Backtracking.Backtracking.Duplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubsetsWithDup {

    // https://leetcode.cn/problems/subsets-ii/solutions/3036436/liang-chong-fang-fa-xuan-huo-bu-xuan-mei-v0js/
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums); // 方便跳過相同元素，在遞歸前，把 nums 排序
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(0, nums, ans, path);
        return ans;
    }

    private void dfs(int i, int[] nums, List<List<Integer>> ans, List<Integer> path) {
        int n = nums.length;
        if (i == n) {
            ans.add(new ArrayList<>(path));
            return;
        }

        // 選 x
        int x = nums[i];
        path.add(x);
        dfs(i + 1, nums, ans, path);
        path.remove(path.size() - 1); // 恢復現場

        // 不選 x，那麼後面所有等於 x 的數都不選
        // 如果不跳過這些數，會導致「選 x 不選 x'」和「不選 x 選 x'」這兩種情況都會加到 ans 中，這就重復了
        i++;
        while (i < n && nums[i] == x) {
            i++;
        }
        dfs(i, nums, ans, path);
    }


}
