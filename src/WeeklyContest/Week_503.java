package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Week_503 {

    // https://leetcode.cn/problems/limit-occurrences-in-sorted-array/solutions/3973361/shuang-zhi-zhen-yuan-di-zuo-fa-pythonjav-oqcw/
    public int[] limitOccurrences(int[] nums, int k) {
        int cnt = 0;
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == 0 || nums[i] == nums[i - 1]) {
                cnt++;
            } else {
                cnt = 1;
            }
            if (cnt <= k) {
                nums[j++] = nums[i];
            }
        }
        return Arrays.copyOf(nums, j);
    }


    // https://leetcode.cn/problems/password-strength/solutions/3973367/ku-han-shu-xie-fa-pythonjavacgo-by-endle-woj1/
    public int passwordStrength(String password) {
        boolean[] vis = new boolean[128];
        int ans = 0;
        for (char ch : password.toCharArray()) {
            if (vis[ch]) {
                continue;
            }
            vis[ch] = true;
            if (Character.isLowerCase(ch)) {
                ans++;
            } else if (Character.isUpperCase(ch)) {
                ans += 2;
            } else if (Character.isDigit(ch)) {
                ans += 3;
            } else {
                ans += 5;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-operations-to-sort-a-permutation/solutions/3973358/fen-lei-tao-lun-pythonjavacgo-by-endless-8jsl/
    public int minOperations(int[] nums) {
        int n = nums.length;
        int cnt = 0;
        int p = 0;
        for (int i = 1; i < n && cnt < 2; i++) {
            if (nums[i - 1] > nums[i]) {
                cnt++;
                p = i;
            }
        }

        if (cnt == 0) { // 已是遞增
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        if (cnt == 1 && nums[0] > nums[n - 1]) { // 兩個遞增段
            ans = Math.min(p, n - p + 2);
        }

        cnt = p = 0;
        for (int i = 1; i < n && cnt < 2; i++) {
            if (nums[i - 1] < nums[i]) {
                cnt++;
                p = i;
            }
        }

        if (cnt == 0) { // 已是遞減
            return 1;
        }
        if (cnt == 1 && nums[0] < nums[n - 1]) { // 兩個遞減段
            ans = Math.min(ans, Math.min(p, n - p) + 1);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }


    // https://leetcode.cn/problems/number-of-pairs-after-increment/solutions/3973347/fen-kuai-pythonjavacgo-by-endlesscheng-2fr9/
    public static final int MX = 1_000_000_001;

    // l, r: 這一段對應 nums2 的子數組 [l, r)，注意是左閉右開區間
    // cnt: 這一段每個元素的出現次數
    // add: 這一段整體要增加 add
    private record Block(int l, int r, Map<Integer, Integer> cnt, int add) {
    }

    public int[] numberOfPairs(int[] nums1, int[] nums2, int[][] queries) {
        int m = nums1.length;
        int n = nums2.length;
        int B = (int) Math.sqrt(m * n);

        Block[] blocks = new Block[(n - 1) / B + 1];
        for (int i = 0; i < n; i += B) {
            int r = Math.min(i + B, n);
            Map<Integer, Integer> cnt = new HashMap<>();
            for (int j = i; j < r; j++) {
                cnt.merge(nums2[j], 1, Integer::sum); // cnt[nums2[j]]++
            }
            blocks[i / B] = new Block(i, r, cnt, 0);
        }

        int cntQ2 = 0;
        for (int[] q : queries) {
            cntQ2 += q[0] - 1;
        }

        int[] ans = new int[cntQ2];
        int idx = 0;
        for (int[] q : queries) {
            if (q[0] == 1) {
                int l = q[1], r = q[2] + 1, val = q[3];
                for (int i = 0; i < blocks.length; i++) {
                    Block b = blocks[i];
                    if (b.l >= r) {
                        break;
                    }
                    if (b.r <= l || b.add >= MX) {
                        continue;
                    }
                    // b 在 [l, r) 中
                    if (l <= b.l && b.r <= r) {
                        blocks[i] = new Block(b.l, b.r, b.cnt, Math.min(b.add + val, MX)); // 避免溢出
                        continue;
                    }
                    // b 的一部分在 [l, r) 中
                    int bl = Math.max(b.l, l);
                    int br = Math.min(b.r, r);
                    // 暴力更新 nums2 的子數組 [bl, br) 的元素值及其出現次數
                    for (int j = bl; j < br; j++) {
                        b.cnt.merge(nums2[j], -1, Integer::sum); // 撤銷舊的
                        nums2[j] = Math.min(nums2[j] + val, MX); // 避免溢出
                        b.cnt.merge(nums2[j], 1, Integer::sum); // 添加新的
                    }
                }
            } else {
                int tot = q[1];
                int res = 0;
                for (Block b : blocks) {
                    int target = tot - b.add;
                    for (int x : nums1) {
                        res += b.cnt.getOrDefault(target - x, 0);
                    }
                }
                ans[idx++] = res;
            }
        }
        return ans;
    }


}










