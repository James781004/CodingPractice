package EndlessCheng.GenreMenu.Graph.Other;

import java.util.*;

public class Supersequences {

    // https://leetcode.cn/problems/frequencies-of-shortest-supersequences/solutions/3057743/mei-ju-zi-ji-jian-tu-pan-duan-shi-fou-yo-n43u/
    public List<List<Integer>> supersequences(String[] words) {
        // 收集有哪些字母，同時建圖
        int all = 0;
        List<Integer>[] g = new ArrayList[26];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (String s : words) {
            int x = s.charAt(0) - 'a';
            int y = s.charAt(1) - 'a';
            all |= 1 << x | 1 << y;
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

        List<List<Integer>> ans = new ArrayList<>(set.size()); // 預分配空間
        for (int s : set) {
            List<Integer> cnt = new ArrayList<>(26); // 預分配空間
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
            // 只遍歷不在 sub 中的字母
            if (color[i] == 0 && (sub >> i & 1) == 0 && dfs(i, color, g, sub)) {
                return true;
            }
        }
        return false;
    }

    private boolean dfs(int x, int[] color, List<Integer>[] g, int sub) {
        color[x] = 1;
        for (int y : g[x]) {
            // 只遍歷不在 sub 中的字母
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
