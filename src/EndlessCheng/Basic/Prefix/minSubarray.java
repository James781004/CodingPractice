package EndlessCheng.Basic.Prefix;

import java.util.HashMap;

public class minSubarray {

    // https://leetcode.cn/problems/make-sum-divisible-by-p/solutions/2158435/tao-lu-qian-zhui-he-ha-xi-biao-pythonjav-rzl0/
    public int minSubarray(int[] nums, int p) {
        int n = nums.length, ans = n;
        var s = new int[n + 1];
        for (int i = 0; i < n; i++)
            s[i + 1] = (s[i] + nums[i]) % p; // 餘數前綴和
        int x = s[n];
        if (x == 0) return 0; // 移除空子數組（這行可以不要）

        var last = new HashMap<Integer, Integer>();
        for (int i = 0; i <= n; i++) {
            last.put(s[i], i);
            // 前綴和數組上找到兩個數 s[left] 和 s[right]，滿足 right−left 最小且 s[right]−s[left]≡x(mod p)
            // 移項得 s[right]−x≡s[left](mod p)
            // 如果不存在，-n 可以保證 i-j >= n
            int j = last.getOrDefault((s[i] - x + p) % p, -n);
            ans = Math.min(ans, i - j);
        }
        return ans < n ? ans : -1;
    }


}
