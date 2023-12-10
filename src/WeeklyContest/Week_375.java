package WeeklyContest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Week_375 {
    // https://leetcode.cn/problems/count-tested-devices-after-test-operations/solutions/2560949/on-zuo-fa-pythonjavacgo-by-endlesscheng-fc5k/
    public int countTestedDevices(int[] batteryPercentages) {
        int dec = 0; // 表示需要減一的次數
        for (int x : batteryPercentages) {
            // 如果 x − dec>  0，即 x > dec，
            // 那麼後面的數都要減一，把 dec 加一即可
            if (x > dec) {
                dec++;
            }
        }
        return dec;
    }


    // https://leetcode.cn/problems/double-modular-exponentiation/solutions/2560946/kuai-su-mi-pythonjavacgo-by-endlesscheng-xeth/
    public List<Integer> getGoodIndices(int[][] variables, int target) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < variables.length; i++) {
            int[] v = variables[i];
            if (pow(pow(v[0], v[1], 10), v[2], v[3]) == target) {
                ans.add(i);
            }
        }
        return ans;
    }

    private long pow(long x, int n, int mod) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) res = res * x % mod;
            x = x * x % mod;
        }
        return res;
    }


    // https://leetcode.cn/problems/count-subarrays-where-max-element-appears-at-least-k-times/solutions/2560940/hua-dong-chuang-kou-fu-ti-dan-pythonjava-xvwg/
    public long countSubarrays(int[] nums, int k) {
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        long ans = 0;
        int cntMx = 0, left = 0;
        for (int x : nums) {
            if (x == mx) {
                cntMx++;
            }
            while (cntMx == k) { // 題目規定 k 必定大於零，所以這邊 left 不怕有越界的狀況(left < n)
                if (nums[left++] == mx) {
                    cntMx--;
                }
            }
            ans += left;
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-the-number-of-good-partitions/solutions/2560938/he-bing-qu-jian-pythonjavacgo-by-endless-yxhw/
    public int numberOfGoodPartitions(int[] nums) {
        Map<Integer, int[]> ps = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (ps.containsKey(x)) {
                ps.get(x)[1] = i; // 更新區間右端點
            } else {
                ps.put(x, new int[]{i, i});
            }
        }

        List<int[]> a = new ArrayList<>(ps.values());
        a.sort((p, q) -> p[0] - q[0]); // 按區間左端點排序

        int ans = 1;
        int maxR = a.get(0)[1];
        for (int i = 1; i < a.size(); i++) {
            int[] interval = a.get(i);
            int left = interval[0];
            int right = interval[1];
            if (left > maxR) { // 無法合並
                ans = ans * 2 % 1_000_000_007;
            }
            maxR = Math.max(maxR, right);
        }
        return ans;
    }
}
