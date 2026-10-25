package WeeklyContest;

import java.util.*;

public class Week_525 {

    // https://leetcode.cn/problems/binary-digit-score/solutions/4048458/ku-han-shu-o1-shi-jian-pythonjavacgo-by-htiz1/
    public int calculateScore(int a, int b) {
        int s1 = Integer.bitCount(a);
        int s2 = Integer.bitCount(b);
        return s1 >= s2 ? s1 * s2 : s2 / s1;
    }


    // https://leetcode.cn/problems/maximize-sum-after-at-most-k-digit-rearrangements/solutions/4048456/tan-xin-pythonjavacgo-by-endlesscheng-7t8u/
    public long maximizeSum(int[] nums, int k) {
        int n = nums.length;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            ans += x;

            // 把 x 從大到小排列，得到 y
            char[] s = String.valueOf(x).toCharArray();
            Arrays.sort(s);
            int y = 0;
            for (int j = s.length - 1; j >= 0; j--) {
                y = y * 10 + s[j] - '0';
            }

            // 把修改後的增量保存到 nums[i] 中
            nums[i] = y - x;
        }

        Arrays.sort(nums);
        // 選擇增量最大的 k 個數
        for (int i = n - 1; i >= n - k; i--) {
            ans += nums[i];
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-frequency-staircase-subarrays-ii/solutions/4048447/bao-li-mei-ju-pythonjavacgo-by-endlessch-skjn/
    public int countStaircase(int[] nums) {
        int n = nums.length;
        Map<Integer, List<Integer>> pos = new HashMap<>();
        int[] idxInPos = new int[n];
        for (int i = 0; i < n; i++) {
            List<Integer> ps = pos.computeIfAbsent(nums[i], v -> new ArrayList<>());
            idxInPos[i] = ps.size();
            ps.add(i);
        }

        List<Integer> firsts = new ArrayList<>();
        Map<Integer, Integer> firstPos = new HashMap<>();
        int ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            int x = nums[i];
            firstPos.put(x, i);

            // 把 firsts 中的 x 移到最前面
            int j = firsts.indexOf(x);
            if (j != 0) { // 優化：j = 0 時無需移動
                if (j >= 0) {
                    firsts.remove(j);
                }
                firsts.addFirst(x);
            }

            int m = firsts.size();
            if (m * (m + 1) / 2 > n) { // firsts 不需要這麼多元素
                firsts.removeLast();
            }

            int mx = 0;
            int mn = n;
            int r = i; // 子數組右開端點
            for (int cnt = 1; cnt <= firsts.size(); cnt++) {
                x = firsts.get(cnt - 1);
                r += cnt;
                // [i,r) 必須包含恰好 cnt 個 x
                List<Integer> ps = pos.get(x);
                int k = idxInPos[firstPos.get(x)] + cnt - 1;
                if (k >= ps.size()) { // [i,n) 中的 x 的個數小於 cnt
                    break;
                }

                // 更新 mx 和 mn 後，必須滿足 mx < r <= mn
                if (k < ps.size() - 1) {
                    mn = Math.min(mn, ps.get(k + 1));
                }
                if (r > mn) { // 繼續遍歷，r 更大，mn 更小，無法找到合法子數組
                    break;
                }

                mx = Math.max(mx, ps.get(k));
                if (mx < r) {
                    ans++;
                }
            }
        }
        return ans;
    }


}










