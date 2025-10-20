package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.Arrays;

public class Makesquare {

    // https://leetcode.cn/problems/matchsticks-to-square/solutions/3735023/huo-chai-pin-zheng-fang-xing-hui-su-you-ytg3d/
    public boolean makesquare(int[] matchsticks) {
        if (matchsticks.length < 4) return false;

        int sum = Arrays.stream(matchsticks).sum();
        if (sum % 4 != 0) return false;

        int sideLength = sum / 4;

        // 檢查最大火柴棒
        int maxStick = Arrays.stream(matchsticks).max().orElse(0);
        if (maxStick > sideLength) return false;

        // 降序排序
        Arrays.sort(matchsticks);
        reverse(matchsticks);

        return backtrack(matchsticks, new int[4], 0, sideLength);
    }

    private boolean backtrack(int[] matchsticks, int[] sides, int index, int target) {
        if (index == matchsticks.length) {
            return true; // 所有火柴棒都已成功放置
        }

        for (int i = 0; i < 4; i++) {
            // 超過邊長
            if (sides[i] + matchsticks[index] > target) continue;

            // 剪枝：避免重復（相同長度的邊）
            if (i > 0 && sides[i] == sides[i - 1]) continue;

            // 選擇
            sides[i] += matchsticks[index];

            // 遞歸
            if (backtrack(matchsticks, sides, index + 1, target)) {
                return true;
            }

            // 回溯
            sides[i] -= matchsticks[index];
        }

        return false;
    }

    private void reverse(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
    }


}
