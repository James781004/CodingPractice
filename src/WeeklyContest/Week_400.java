package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Week_400 {
    // https://leetcode.cn/problems/minimum-number-of-chairs-in-a-waiting-room/solutions/2798253/zhuan-huan-cheng-shang-xia-che-geng-hao-rnlj2/
    public int minimumChairs(String s) {
        int ans = 0;
        int cnt = 0;
        for (char c : s.toCharArray()) {
            if (c == 'E') {
                ans = Math.max(ans, ++cnt);
            } else {
                cnt--;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-days-without-meetings/solutions/2798193/zheng-nan-ze-fan-he-bing-qu-jian-pythonj-r0gi/
    public int countDays(int days, int[][] meetings) {
        Arrays.sort(meetings, (p, q) -> p[0] - q[0]); // 按照左端點從小到大排序
        int start = 1, end = 0; // 當前合並區間的左右端點
        for (int[] p : meetings) {
            if (p[0] > end) { // 不相交
                days -= end - start + 1; // 當前合並區間的長度
                start = p[0]; // 下一個合並區間的左端點
            }
            end = Math.max(end, p[1]);
        }
        days -= end - start + 1; // 最後一個合並區間的長度
        return days;
    }


    // https://leetcode.cn/problems/lexicographically-minimum-string-after-removing-stars/solutions/2798240/yong-26-ge-zhan-mo-ni-pythonjavacgo-by-e-mhtn/
    // 核心思路：由於要去掉最小的字母，為了讓字典序盡量小，相比去掉前面的字母，去掉後面的字母更好。
    public String clearStars(String S) {
        char[] s = S.toCharArray();
        List<Integer>[] st = new List[26];  // 從左到右遍歷 s，用 26 個棧模擬
        Arrays.setAll(st, i -> new ArrayList<>());
        for (int i = 0; i < s.length; i++) {
            if (s[i] != '*') { // 第 i 個棧維護第 i 個小寫字母的下標
                st[s[i] - 'a'].add(i);
                continue;
            }
            for (List<Integer> p : st) {
                if (!p.isEmpty()) { // 遇到 * 時，彈出第一個非空棧的棧頂下標
                    p.remove(p.size() - 1);
                    break;
                }
            }
        }

        // 最後把所有棧頂下標對應的字母組合起來，即為答案
        List<Integer> idx = new ArrayList<>();
        for (List<Integer> p : st) {
            idx.addAll(p);
        }
        Collections.sort(idx);

        StringBuilder t = new StringBuilder(idx.size());
        for (int i : idx) {
            t.append(s[i]);
        }
        return t.toString();
    }


    // https://leetcode.cn/problems/find-subarray-with-bitwise-and-closest-to-k/solutions/2798206/li-yong-and-de-xing-zhi-pythonjavacgo-by-gg4d/
    public int minimumDifference(int[] nums, int k) {
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            ans = Math.min(ans, Math.abs(x - k));
            for (int j = i - 1; j >= 0 && (nums[j] & x) != nums[j]; j--) {
                nums[j] &= x;
                ans = Math.min(ans, Math.abs(nums[j] - k));
            }
        }
        return ans;
    }
}


