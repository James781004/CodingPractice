package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_527 {

    // https://leetcode.cn/problems/find-mismatched-segments-between-two-strings/solutions/4054689/ji-lu-zuo-duan-dian-pan-duan-you-duan-di-631v/
    public List<List<String>> findMismatchedSegments(String s1, String s2) {
        char[] s = s1.toCharArray();
        char[] t = s2.toCharArray();
        int n = s.length;
        int start = 0;
        List<List<String>> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s[i] == t[i]) {
                start = i + 1; // 記錄段的起點
            } else if (i == n - 1 || s[i + 1] == t[i + 1]) { // i+1 相同，所以 i 是這一段的末尾
                ans.add(List.of(s1.substring(start, i + 1), s2.substring(start, i + 1)));
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-valid-suffix-sorts-i/solutions/4054693/fen-cheng-san-ge-xiao-wen-ti-pythonjavac-mpuq/
    public int countSuffix(int[] nums, int[] target) {
        // 大前提：每種元素的出現次數都相等
        int mx = 0;
        for (int x : nums) mx = Math.max(mx, x);
        for (int x : target) mx = Math.max(mx, x);
        int[] diff = new int[mx + 1]; // int[] 比 HashMap 快
        for (int i = 0; i < nums.length; i++) {
            diff[nums[i]]++;
            diff[target[i]]--;
        }
        for (int d : diff) {
            if (d != 0) {
                return 0;
            }
        }

        int n = nums.length;
        int maxI = n - 1;
        for (int i = 0; i < n; i++) {
            if (nums[i] != target[i]) {
                maxI = i;
                break;
            }
        }

        int minI = 0;
        for (int i = n - 1; i > 0; i--) {
            if (target[i - 1] < target[i]) {
                minI = i;
                break;
            }
        }

        return Math.max(maxI - minI + 1, 0);
    }


    // https://leetcode.cn/problems/minimum-cost-to-make-red-nodes-connected/solutions/4054664/shu-xing-dppythonjavacgo-by-endlesscheng-vy5z/
    private long base = 0;
    private long mx = 0;

    public long minCost(int n, String colors, int[][] edges) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        dfs(0, -1, g, colors.toCharArray());
        return base - mx;
    }

    private long[] dfs(int x, int fa, List<Integer>[] g, char[] colors) {
        int size = 1;
        long f = 0;
        // 遍歷 x 的子樹
        for (int y : g[x]) {
            if (y != fa) {
                long[] res = dfs(y, x, g, colors);
                size += res[0];
                // 如果 fy < 0，那麼子樹 y 都變成藍色是最優的，這已經算入 base 中
                f += Math.max(res[1], 0);
            }
        }

        // 考慮節點 x
        if (colors[x] == 'R') {
            base += size; // 把 x 變成藍色的代價
            f += size; // 把 x 恢復成紅色可以讓 base 減少 size
        } else {
            f -= size; // 把 x 變成紅色會讓 base 增大 size
        }
        mx = Math.max(mx, f); // 最多可以讓 base 減少多少

        return new long[]{size, f};
    }


    // https://leetcode.cn/problems/count-valid-suffix-sorts-ii/solutions/4054676/hua-dong-chuang-kou-z-han-shu-shang-yi-g-jsvm/
    public int[] countSuffixII(int[] nums, int[] target) {
        boolean[] validL = findAnagrams(target, nums);

        int n = nums.length;
        int m = target.length;
        int[] s = Arrays.copyOf(nums, n + m);
        System.arraycopy(target, 0, s, n, m);
        int[] z = calcZ(s);

        int[] lastInc = new int[m];
        for (int i = 1; i < m; i++) {
            if (target[i - 1] < target[i]) {
                lastInc[i] = i;
            } else {
                lastInc[i] = lastInc[i - 1];
            }
        }

        int[] ans = new int[validL.length];
        for (int k = 0; k < validL.length; k++) {
            if (validL[k]) {
                ans[k] = Math.max(Math.min(z[n + k], n - 1) - Math.max(lastInc[k + n - 1] - k, 0) + 1, 0);
            }
        }
        return ans;
    }

    private boolean[] findAnagrams(int[] s, int[] p) {
        int mx = 0;
        for (int x : s) mx = Math.max(mx, x);
        for (int x : p) mx = Math.max(mx, x);

        int[] diff = new int[mx + 1]; // 数组比哈希表快
        // 统计 p 的每种整数的出现次数
        for (int x : p) {
            diff[x]++;
        }

        boolean[] validL = new boolean[s.length - p.length + 1];
        int left = 0;
        for (int right = 0; right < s.length; right++) {
            int x = s[right];
            diff[x]--; // x 进入窗口
            while (diff[x] < 0) { // x 太多了
                diff[s[left]]++; // 左端点离开窗口
                left++;
            }
            if (right - left + 1 == p.length) { // 当前窗口与 p 的每种整数的出现次数都相同（证明见 438 题解）
                validL[left] = true;
            }
        }
        return validL;
    }

    // 计算并返回 z 数组，其中 z[i] = |LCP(s[i:], s)|
    private int[] calcZ(int[] s) {
        int n = s.length;
        int[] z = new int[n];
        int boxL = 0;
        int boxR = 0;
        for (int i = 1; i < n; i++) {
            if (i <= boxR) {
                z[i] = Math.min(z[i - boxL], boxR - i + 1);
            }
            while (i + z[i] < n && s[z[i]] == s[i + z[i]]) {
                boxL = i;
                boxR = i + z[i];
                z[i]++;
            }
        }
        return z;
    }


}










