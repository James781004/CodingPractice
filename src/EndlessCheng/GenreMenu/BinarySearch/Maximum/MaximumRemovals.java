package EndlessCheng.GenreMenu.BinarySearch.Maximum;

public class MaximumRemovals {

    // https://leetcode.cn/problems/maximum-number-of-removable-characters/solutions/2978301/er-fen-da-an-pythonjavacti-jie-by-yfsilv-81hc/
    public int maximumRemovals(String s, String p, int[] removable) {
        int l = 0;
        int r = removable.length;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (check(s, p, removable, mid)) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return r;
    }

    public boolean check(String s, String p, int[] removable, int x) {
        boolean[] del = new boolean[s.length()];
        for (int i = 0; i < x; i++) {
            del[removable[i]] = true;
        }
        int i = 0;
        for (int j = 0; j < s.length(); j++) {
            if (!del[j] && s.charAt(j) == p.charAt(i)) {
                i++;
                if (i == p.length()) {
                    return true;
                }
            }
        }
        return false;
    }


}
