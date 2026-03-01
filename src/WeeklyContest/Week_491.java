package WeeklyContest;

import java.util.HashMap;
import java.util.Map;

public class Week_491 {

    // https://leetcode.cn/problems/trim-trailing-vowels/solutions/3910789/xun-huan-ku-han-shu-pythonjavacgo-by-end-wczb/
    public String trimTrailingVowels(String s) {
        int i = s.length() - 1;
        while (i >= 0 && "aeiou".indexOf(s.charAt(i)) != -1) {
            i--;
        }
        return s.substring(0, i + 1);
    }


    // https://leetcode.cn/problems/minimum-cost-to-split-into-ones/solutions/3910783/deng-jie-zhuan-hua-o1-gong-shi-pythonjav-9flx/
    public int minCost(int n) {
        return n * (n - 1) / 2;
    }


    // https://leetcode.cn/problems/minimum-bitwise-or-from-grid/solutions/3910784/shi-tian-fa-pythonjavacgo-by-endlesschen-b3zh/
    public int minimumOR(int[][] grid) {
        int mx = 0;
        for (int[] row : grid) {
            for (int x : row) {
                mx = Math.max(mx, x);
            }
        }
        int bitLength = 32 - Integer.numberOfLeadingZeros(mx);

        int ans = 0;
        // 試填法：ans 的第 i 位能不能是 0？
        // 如果在每一行的能選的數字中，都存在第 i 位是 0 的數，那麼 ans 的第 i 位可以是 0，否則必須是 1
        for (int i = bitLength - 1; i >= 0; i--) {
            int mask = ans | ((1 << i) - 1); // mask 低於 i 的比特位全是 1
            for (int[] row : grid) {
                boolean ok = false;
                for (int x : row) {
                    // x 的高於 i 的比特位，如果 ans 是 0，那麼 x 的這一位必須也是 0
                    // x 的低於 i 的比特位，隨意
                    // x 的第 i 個比特位，我們期望它是 0
                    if ((x | mask) == mask) { // x 可以選，且第 i 位是 0
                        ok = true;
                        break;
                    }
                }
                if (!ok) { // 這一行的可選數字中，第 i 位全是 1
                    ans |= 1 << i; // ans 第 i 位必須是 1
                    break;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-subarrays-with-k-distinct-integers/solutions/3910806/qia-hao-xing-hua-dong-chuang-kou-pythonj-5mll/
    public long countSubarrays(int[] nums, int k, int m) {
        return calc(nums, k, k, m) - calc(nums, k + 1, k, m);
    }

    private long calc(int[] nums, int distinctLimit, int k, int m) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int geM = 0;  // 窗口中的出現次數 >= m 的元素個數
        int left = 0;
        long ans = 0;
        for (int x : nums) {
            // 1. 入
            int c = cnt.merge(x, 1, Integer::sum);
            if (c == m) {
                geM++;
            }

            // 2. 出
            while (cnt.size() >= distinctLimit && geM >= k) {
                int out = nums[left];
                c = cnt.get(out);
                if (c == m) {
                    geM--;
                }
                if (c == 1) {
                    cnt.remove(out);
                } else {
                    cnt.put(out, c - 1);
                }
                left++;
            }

            // 3. 更新答案
            ans += left;
        }
        return ans;
    }


}









