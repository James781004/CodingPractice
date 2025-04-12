package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Basic;

import java.util.HashMap;
import java.util.List;

public class MaxSum {

    // https://leetcode.cn/problems/maximum-sum-of-almost-unique-subarray/solutions/2424847/hua-dong-chuang-kou-fu-ti-dan-pythonjava-2vd6/
    public long maxSum(List<Integer> nums, int m, int k) {
        var a = nums.stream().mapToInt(i -> i).toArray();
        long ans = 0, sum = 0;
        var cnt = new HashMap<Integer, Integer>();
        for (int i = 0; i < k - 1; i++) { // 先統計 k-1 個數
            sum += a[i];
            cnt.merge(a[i], 1, Integer::sum); // cnt[a[i]]++
        }
        for (int i = k - 1; i < nums.size(); i++) {
            sum += a[i]; // 再添加一個數就是 k 個數了
            cnt.merge(a[i], 1, Integer::sum); // cnt[a[i]]++
            if (cnt.size() >= m) // 有至少 m 個互不相同的元素，統計答案
                ans = Math.max(ans, sum);

            int out = a[i - k + 1];
            sum -= out; // 下一個子數組不包含 out，移出窗口
            if (cnt.merge(out, -1, Integer::sum) == 0) // --cnt[out] == 0
                cnt.remove(out);
        }
        return ans;
    }


}
