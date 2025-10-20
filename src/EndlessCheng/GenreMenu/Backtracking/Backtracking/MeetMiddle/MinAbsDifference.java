package EndlessCheng.GenreMenu.Backtracking.Backtracking.MeetMiddle;

import java.util.Arrays;

public class MinAbsDifference {

    // https://leetcode.cn/problems/closest-subsequence-sum/solutions/1533570/by-shou-hu-zhe-t-pdm6/
    int point = 0; // 數組數據組合的指針（表示數組的下標）

    public int minAbsDifference(int[] nums, int goal) {
        Arrays.sort(nums);
        int mid = nums.length >> 1;
        point = 0;
        
        // 數據{ 5, -7, 3, 5 }==>左側[5,-7] 右側[5,-2]
        // 保存所有左側數據的組合==>[0, -7, 5, -2]   左側數據為(5,-7)
        int[] l = new int[1 << mid];
        // 保存右側所有數據的組合==>[0, 5, 3, 8]   右側數據為(5,-2)
        int[] r = new int[1 << (nums.length - mid)];

        dfs(nums, 0, mid - 1, 0, l); // 枚舉左側所有組合
        point = 0;// 指針歸0
        dfs(nums, mid, nums.length - 1, 0, r);// 枚舉右側所有組合

        int ans = Integer.MAX_VALUE;
        Arrays.sort(l);
        Arrays.sort(r);
        int left = 0, right = r.length - 1;

        //組合左側數據與右側數據
        while (left < l.length && right >= 0) {
            int t = l[left] + r[right]; //臨時保存左側數據與右側數據的組合
            ans = Math.min(ans, Math.abs(t - goal));//更改答案
            if (t > goal) right--;
            else left++;
        }
        return ans;
    }

    /**
     * @param nums  題目給出的數組
     * @param start 數組的開始位置
     * @param end   數組的結束位置
     * @param sum   保存左側組合的和（sum）
     * @param arr   保存數組組合的和
     */
    private void dfs(int[] nums, int start, int end, int sum, int[] arr) {
        arr[point++] = sum;
        for (int i = start; i <= end; i++) {
            if (i != start && nums[i - 1] == nums[i]) {
                continue;
            }
            dfs(nums, i + 1, end, sum + nums[i], arr);
        }
    }

}
