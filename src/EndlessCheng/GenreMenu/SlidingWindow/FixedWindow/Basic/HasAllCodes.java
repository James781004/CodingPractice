package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Basic;

import java.util.HashSet;

public class HasAllCodes {

    // https://leetcode.cn/problems/check-if-a-string-contains-all-binary-codes-of-size-k/solutions/268378/hua-dong-chuang-kou-cha-zhao-biao-shi-jian-fu-za-d/
    public boolean hasAllCodes(String s, int k) {
        HashSet<String> set = new HashSet();
        for (int i = 0; i <= s.length() - k; i++) {
            set.add(s.substring(i, i + k));
        }
        return set.size() == (int) Math.pow(2, k);

    }


}
