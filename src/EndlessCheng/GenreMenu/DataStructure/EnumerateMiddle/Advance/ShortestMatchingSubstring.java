package EndlessCheng.GenreMenu.DataStructure.EnumerateMiddle.Advance;

import java.util.ArrayList;
import java.util.List;

public class ShortestMatchingSubstring {

    // https://leetcode.cn/problems/shortest-matching-substring/solutions/3076453/xian-xing-zuo-fa-kmpmei-ju-zhong-jian-sa-5qow/
    public int shortestMatchingSubstring(String S, String p) {
        char[] s = S.toCharArray();
        String[] sp = p.split("\\*", -1);
        char[] p1 = sp[0].toCharArray();
        char[] p2 = sp[1].toCharArray();
        char[] p3 = sp[2].toCharArray();

        // 三段各自在 s 中的所有匹配位置
        List<Integer> pos1 = kmpSearch(s, p1);
        List<Integer> pos2 = kmpSearch(s, p2);
        List<Integer> pos3 = kmpSearch(s, p3);

        int ans = Integer.MAX_VALUE;
        int i = 0;
        int k = 0;
        // 枚舉中間（第二段），維護最近的左右（第一段和第三段）
        for (int j : pos2) {
            // 右邊找離 j 最近的子串（但不能重疊）
            while (k < pos3.size() && pos3.get(k) < j + p2.length) {
                k++;
            }
            if (k == pos3.size()) { // 右邊沒有
                break;
            }
            // 左邊找離 j 最近的子串（但不能重疊）
            while (i < pos1.size() && pos1.get(i) <= j - p1.length) {
                i++;
            }
            // 循環結束後，pos1.get(i-1) 是左邊離 j 最近的子串下標（首字母在 s 中的下標）
            if (i > 0) {
                ans = Math.min(ans, pos3.get(k) + p3.length - pos1.get(i - 1));
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // 計算字符串 p 的 pi 數組
    private int[] calcPi(char[] p) {
        int[] pi = new int[p.length];
        int match = 0;
        for (int i = 1; i < p.length; i++) {
            char v = p[i];
            while (match > 0 && p[match] != v) {
                match = pi[match - 1];
            }
            if (p[match] == v) {
                match++;
            }
            pi[i] = match;
        }
        return pi;
    }

    // 在文本串 s 中查找模式串 p，返回所有成功匹配的位置（p[0] 在 s 中的下標）
    private List<Integer> kmpSearch(char[] s, char[] p) {
        if (p.length == 0) {
            // s 的所有位置都能匹配空串，包括 s.length
            List<Integer> pos = new ArrayList<>(s.length + 1);
            for (int i = 0; i <= s.length; i++) {
                pos.add(i);
            }
            return pos;
        }

        int[] pi = calcPi(p);
        List<Integer> pos = new ArrayList<>();
        int match = 0;
        for (int i = 0; i < s.length; i++) {
            char v = s[i];
            while (match > 0 && p[match] != v) {
                match = pi[match - 1];
            }
            if (p[match] == v) {
                match++;
            }
            if (match == p.length) {
                pos.add(i - p.length + 1);
                match = pi[match - 1];
            }
        }
        return pos;
    }

}
