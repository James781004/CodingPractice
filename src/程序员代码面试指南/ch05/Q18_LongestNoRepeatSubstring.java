package 程序员代码面试指南.ch05;

import java.util.HashSet;

public class Q18_LongestNoRepeatSubstring {
//    找到字符串的最长无重复字符子串
//    描述
//    给定一个数组arr，返回arr的最长无的重复子串的长度(无重复指的是所有字母都不相同)。

    public static int maxUnique(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }
        char[] chas = str.toCharArray();
        int[] map = new int[256];
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }

        // 如果當前遍歷到chas[i], 表示在必須以chas[i-1]字元結尾時，
        // 最長無重復子串開始位置的前一個位置
        int len = 0;
        int pre = -1;
        int cur = 0;

        // 假設chas[i]之前出現過的位置是a, 若a在pre左側，則當前最長長度為i-pre;
        // 若a在pre右側，則當前最長長度為i-a
        for (int i = 0; i < chas.length; i++) {
            pre = Math.max(pre, map[chas[i]]);
            cur = i - pre;
            len = Math.max(len, cur);
            map[chas[i]] = i;
        }
        return len;
    }

    // 雙指針滑動視窗求解，剛開始兩個指針都在0位置，如果元素一直不重復就擴張右邊界，
    // 重復了就更新長度並收縮左邊界，在這個遍歷的過程中使用哈希表來進行判重。
    public static int maxUnique2(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }

        char[] chas = str.toCharArray();
        int n = chas.length;
        int left = 0, right = 0;
        HashSet<Character> used = new HashSet<>();
        int maxLen = 0;

        while (right < n) {
            if (!used.contains(chas[right])) {
                used.add(chas[right]);       // 一直不重復就一直擴張右邊界
                right++;
            } else {
                // 重復了就更新長度，收縮左邊界，直到把第一次出現的重復元素排除掉
                maxLen = Math.max(maxLen, right - left);
                while (left < right && chas[left] != chas[right]) {
                    used.remove(chas[left]);
                    left++;
                }
                left++;
                right++;
            }
        }


        return maxLen;
    }


    // for test
    public static String getRandomString(int len) {
        char[] str = new char[len];
        int base = 'a';
        int range = 'z' - 'a' + 1;
        for (int i = 0; i != len; i++) {
            str[i] = (char) ((int) (Math.random() * range) + base);
        }
        return String.valueOf(str);
    }

    // for test
    public static String maxUniqueString(String str) {
        if (str == null || str.equals("")) {
            return str;
        }
        char[] chas = str.toCharArray();
        int[] map = new int[256];
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }
        int len = -1;
        int pre = -1;
        int cur = 0;
        int end = -1;
        for (int i = 0; i != chas.length; i++) {
            pre = Math.max(pre, map[chas[i]]);
            cur = i - pre;
            if (cur > len) {
                len = cur;
                end = i;
            }
            map[chas[i]] = i;
        }
        return str.substring(end - len + 1, end + 1);
    }

    public static void main(String[] args) {
        String str = getRandomString(20);
        System.out.println(str);
        System.out.println(maxUnique(str));
        System.out.println(maxUniqueString(str));
    }

}
