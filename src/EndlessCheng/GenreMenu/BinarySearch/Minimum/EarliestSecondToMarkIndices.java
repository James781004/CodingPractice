package EndlessCheng.GenreMenu.BinarySearch.Minimum;

import java.util.Arrays;

public class EarliestSecondToMarkIndices {

    // https://leetcode.cn/problems/earliest-second-to-mark-indices-i/solutions/2653101/er-fen-da-an-pythonjavacgo-by-endlessche-or61/
    public int earliestSecondToMarkIndices(int[] nums, int[] changeIndices) {
        int n = nums.length;
        int m = changeIndices.length;
        if (n > m) {
            return -1;
        }

        int[] lastT = new int[n];
        int left = n - 1, right = m + 1; // 最短時間 n, 最長時間 m
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (check(nums, changeIndices, lastT, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right > m ? -1 : right;
    }

    private boolean check(int[] nums, int[] changeIndices, int[] lastT, int mx) {
        Arrays.fill(lastT, -1);
        for (int t = 0; t < mx; t++) {
            lastT[changeIndices[t] - 1] = t; // 必須標記的最後時間, 否則無法完成
        }
        for (int t : lastT) {
            if (t < 0) { // 有課程沒有考試時間
                return false;
            }
        }

        int cnt = 0;
        for (int i = 0; i < mx; i++) {
            int idx = changeIndices[i] - 1;
            if (i == lastT[idx]) { // 考試
                if (nums[idx] > cnt) { // 沒時間復習
                    return false;
                }
                cnt -= nums[idx]; // 復習這門課程
            } else {
                cnt++; // 留著後面用
            }
        }
        return true;
    }


    // 逆向遍歷
    private boolean check2(int[] nums, int[] changeIndices, int[] done, int mx) {
        int exam = nums.length;
        int study = 0;
        for (int i = mx - 1; i >= 0 && study <= i + 1; i--) { // 要復習的天數不能太多
            int idx = changeIndices[i] - 1;
            if (done[idx] != mx) {
                done[idx] = mx;
                exam--; // 考試
                study += nums[idx]; // 需要復習的天數
            } else if (study > 0) {
                study--; // 復習
            }
        }
        return exam == 0 && study == 0; // 考完了並且復習完了
    }


}
