package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

import java.util.*;

public class CountPairs {

    // https://leetcode.cn/problems/count-almost-equal-pairs-ii/solutions/2892072/pai-xu-mei-ju-you-wei-hu-zuo-pythonjavac-vbg0/
    public int countPairs(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            Set<Integer> st = new HashSet<>();
            st.add(x); // 不交換
            char[] s = Integer.toString(x).toCharArray();
            int m = s.length;
            for (int i = 0; i < m; i++) {
                for (int j = i + 1; j < m; j++) {
                    swap(s, i, j);
                    st.add(Integer.parseInt(new String(s))); // 交換一次
                    for (int p = i + 1; p < m; p++) {
                        for (int q = p + 1; q < m; q++) {
                            swap(s, p, q);
                            st.add(Integer.parseInt(new String(s))); // 交換兩次
                            swap(s, p, q);
                        }
                    }
                    swap(s, i, j);
                }
            }
            for (int v : st) {
                ans += cnt.getOrDefault(v, 0);
            }
            cnt.merge(x, 1, Integer::sum);
        }
        return ans;
    }

    private void swap(char[] s, int i, int j) {
        char tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
    }

}
