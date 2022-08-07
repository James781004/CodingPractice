package TeacherZuoCodingInterviewGuide.ch05;

import java.util.HashMap;

public class Q21_MinWindowLength {
//    最短包含字符串的长度
//    描述
//    给定字符串str1和str2，求str1的字串中含有str2所有字符的最小字符串长度。

    public static int minLength(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() < str2.length()) {
            return 0;
        }
        char[] chas1 = str1.toCharArray();
        char[] chas2 = str2.toCharArray();
        int[] map = new int[256];
        for (int i = 0; i != chas2.length; i++) {
            map[chas2[i]]++;
        }
        int left = 0;
        int right = 0;
        int match = chas2.length; // 待比較的str2長度
        int minLen = Integer.MAX_VALUE;

        while (right != chas1.length) {
            map[chas1[right]]--;    // 每次走過都要在該位置-1
            if (map[chas1[right]] >= 0) {   // 碰到目標位置就match - 1
                match--;
            }
            if (match == 0) { // match是0表示目前子串已經包含所有目標

                // 左指針開始移動，移動到大於或等於0的位置就停下
                // 小於0的位置表示不是目標，加回1
                while (map[chas1[left]] < 0) {
                    map[chas1[left++]]++;
                }

                // 這時的位置已經排除掉不相關字元，計算最小長度
                minLen = Math.min(minLen, right - left + 1);

                // 左指針移動1位的同時match加回1，經過的位置也要加回1
                match++;
                map[chas1[left++]]++;
            }
            right++;    // 右指針移動
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }


    // 雙指針滑動視窗求解（left和right雙指針）：
    // 1. 先統計str2的字元頻數，以及總的字元負債debt=str2.length；
    // 2. 遍歷str1，利用right指針擴張右邊界，遇到str2中沒有的字元直接跳過，否則同步減少當前字元的計數和總的字元負債，
    // 但當計數小於0時，不再減小總的字元負債，因為出現字元的多餘；
    //
    // 3. 當debt=0時，表示在str1中找到了包含str2所有字元的子串，右移左指針left收縮左邊界，直到排除所有多餘字元（頻數小於0的字元）後更新長度。
    // 滑動視窗重復上面三個步驟，查找後面有沒有更短的子串。
    public static int minLength2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() < str2.length()) {
            return 0;
        }
        char[] chas1 = str1.toCharArray();
        char[] chas2 = str2.toCharArray();

        HashMap<Character, Integer> termFreq = new HashMap<>();
        for (int i = 0; i < chas2.length; i++) termFreq.put(chas2[i], termFreq.getOrDefault(chas2[i], 0) + 1);
        int left = 0, right = 0;
        int debt = chas2.length;        // 剛開始欠str2長度的字元
        int minLen = chas1.length + 1;

        while (right < chas1.length) {
            if (!termFreq.containsKey(chas1[right])) {
                right++;    // 跳過不相關的字元
                continue;
            }
            termFreq.put(chas1[right], termFreq.getOrDefault(chas1[right], 0) - 1);
            if (termFreq.get(chas1[right]) >= 0) debt--;
            if (debt == 0) {
                if (!termFreq.containsKey(chas1[left])) {
                    left++;    // 跳過不相關的字元
                    continue;
                }
                // 負債小於0的詞是多餘的，先去掉
                while (termFreq.get(chas1[left]) < 0) {
                    termFreq.put(chas1[left], termFreq.getOrDefault(chas1[left], 0) + 1);
                    left++;
                }
                // 負債為0且無多餘詞，更新長度
                minLen = Math.min(minLen, right - left + 1);
                termFreq.put(chas1[left], termFreq.getOrDefault(chas1[left], 0) + 1);
                debt++;
                left++;
            }
            right++;
        }
        minLen = Math.min(minLen, right - left + 1);

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }


    public static void main(String[] args) {
        String str1 = "adabbca";
        String str2 = "acb";
        System.out.println(minLength(str1, str2));

    }
}
