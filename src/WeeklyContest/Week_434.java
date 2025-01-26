package WeeklyContest;

import java.util.*;

public class Week_434 {

    // https://leetcode.cn/problems/count-partitions-with-even-sum-difference/solutions/3057701/nao-jin-ji-zhuan-wan-pythonjavacgo-by-en-sgu3/
    // 設 nums 的所有元素之和為 s，左子數組元素和為 sl，那麼右子數組的元素和為 s−sl
    // 題目要求 sl−(s−sl)=2⋅sl−s 是偶數。由於 2⋅sl 一定是偶數
    // 所以問題變成判斷 s 是否為偶數。
    public int countPartitions(int[] nums) {
        int s = Arrays.stream(nums).sum();
        return s % 2 > 0 ? 0 : nums.length - 1;
    }


    // https://leetcode.cn/problems/count-mentions-per-user/solutions/3057699/an-zhao-shi-jian-chuo-fen-zu-mo-ni-by-en-w77b/
    public int[] countMentions(int numberOfUsers, List<List<String>> events) {
        // 按照時間戳排序，時間戳相同的，離線事件排在前面
        events.sort((a, b) -> {
            int ta = Integer.parseInt(a.get(1));
            int tb = Integer.parseInt(b.get(1));
            return ta != tb ? ta - tb : b.get(0).charAt(0) - a.get(0).charAt(0);
        });

        int[] ans = new int[numberOfUsers];
        int[] onlineT = new int[numberOfUsers];
        for (List<String> e : events) {
            int curT = Integer.parseInt(e.get(1));
            String mention = e.get(2);
            if (e.get(0).charAt(0) == 'O') {
                onlineT[Integer.parseInt(mention)] = curT + 60;
            } else if (mention.charAt(0) == 'A') {
                for (int i = 0; i < numberOfUsers; i++) {
                    ans[i]++;
                }
            } else if (mention.charAt(0) == 'H') {
                for (int i = 0; i < numberOfUsers; i++) {
                    if (onlineT[i] <= curT) {
                        ans[i]++;
                    }
                }
            } else {
                for (String s : mention.split(" ")) {
                    int i = Integer.parseInt(s.substring(2));
                    ans[i]++;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-frequency-after-subarray-operation/solutions/3057702/mei-ju-zhuang-tai-ji-dp-by-endlesscheng-qpt0/
    public int maxFrequency(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums) {
            set.add(x);
        }

        int ans = 0;
        for (int target : set) {
            int f0 = 0;
            int f1 = Integer.MIN_VALUE;
            int f2 = Integer.MIN_VALUE;
            for (int x : nums) {
                f2 = Math.max(f2, f1) + (x == k ? 1 : 0);
                f1 = Math.max(f1, f0) + (x == target ? 1 : 0);
                f0 += (x == k ? 1 : 0);
            }
            ans = Math.max(ans, Math.max(f1, f2));
        }
        return ans;
    }


    // https://leetcode.cn/problems/frequencies-of-shortest-supersequences/solutions/3057743/mei-ju-zi-ji-jian-tu-pan-duan-shi-fou-yo-n43u/
    public List<List<Integer>> supersequences(String[] words) {
        // 收集有哪些字母，同時建圖
        int all = 0;
        List<Integer>[] g = new ArrayList[26];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (String s : words) {
            int x = s.charAt(0) - 'a';
            int y = s.charAt(1) - 'a';
            all |= (1 << x) | (1 << y);
            g[x].add(y);
        }

        Set<Integer> set = new HashSet<>();
        int minSize = Integer.MAX_VALUE;
        // 枚舉 all 的所有子集 sub
        int sub = all;
        do {
            int size = Integer.bitCount(sub);
            // 剪枝：如果 size > minSize 就不需要判斷了
            if (size <= minSize && !hasCycle(sub, g)) {
                if (size < minSize) {
                    minSize = size;
                    set.clear();
                }
                set.add(sub);
            }
            sub = (sub - 1) & all;
        } while (sub != all);

        List<List<Integer>> ans = new ArrayList<>();
        for (int s : set) {
            List<Integer> cnt = new ArrayList<>(26);
            for (int i = 0; i < 26; i++) {
                cnt.add((all >> i & 1) + (s >> i & 1));
            }
            ans.add(cnt);
        }
        return ans;
    }

    private boolean hasCycle(int sub, List<Integer>[] g) {
        int[] color = new int[26];
        for (int i = 0; i < 26; i++) {
            if (color[i] == 0 && (sub >> i & 1) == 0 && dfs(i, color, g, sub)) {
                return true;
            }
        }
        return false;
    }

    private boolean dfs(int x, int[] color, List<Integer>[] g, int sub) {
        color[x] = 1;
        for (int y : g[x]) {
            if ((sub >> y & 1) != 0) {
                continue;
            }
            if (color[y] == 1 || color[y] == 0 && dfs(y, color, g, sub)) {
                return true;
            }
        }
        color[x] = 2;
        return false;
    }


}









