package TeacherZuoCodingInterviewGuide.ch09;

public class Q33_ManacherAlgorithm {
//    CD85 Manacher算法

    //    描述
//            给定一个字符串str, 返回str中最长回文子串的长度
//[举例]
//    str=“123”。其中的最长回文子串“1”或者“2”或者“3”，所以返回1。
//    str=“abc1234321ab”。其中的最长回文子串“1234321”，所以返回7。
//            [要求]
//    如果str的长度为N，解决原问题的时间复杂度都达到O(N).


    // 不用算法的中心擴散法
//    public String longestPalindrome(String s) {
//        int len = s.length();
//        if (len < 2) {
//            return s;
//        }
//        int maxLen = 1;
//        String res = s.substring(0, 1);
//        // 中心位置枚舉到 len - 2 即可
//        for (int i = 0; i < len - 1; i++) {
//            String oddStr = centerSpread(s, i, i);
//            String evenStr = centerSpread(s, i, i + 1);
//            String maxLenStr = oddStr.length() > evenStr.length() ? oddStr : evenStr;
//            if (maxLenStr.length() > maxLen) {
//                maxLen = maxLenStr.length();
//                res = maxLenStr;
//            }
//        }
//        return res;
//    }
//
//    private String centerSpread(String s, int left, int right) {
//        // left = right 的時候，此時回文中心是一個空隙，回文串的長度是奇數
//        // right = left + 1 的時候，此時回文中心是任意一個字符，回文串的長度是偶數
//        int len = s.length();
//        int i = left;
//        int j = right;
//        while (i >= 0 && j < len) {
//            if (s.charAt(i) == s.charAt(j)) {
//                i--;
//                j++;
//            } else {
//                break;
//            }
//        }
//        // 這里要小心，跳出 while 循環時，恰好滿足 s.charAt(i) != s.charAt(j)，因此不能取 i，不能取 j
//        return s.substring(i + 1, j);
//    }

    public static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    public static int maxLcpsLength(String str) {
        if (str == null || str.length() == 0) return 0;

        char[] charArr = manacherString(str); // 預處理原始字符串，往每個字符之間加#
        int[] pArr = new int[charArr.length];
        int index = -1;
        int pR = -1; // 最遠右邊界(下一位置)及其對應的中心位置
        int max = Integer.MIN_VALUE;
        for (int i = 0; i != charArr.length; i++) {
            pArr[i] = pR > i ? Math.min(pArr[2 * index - i], pR - i) : 1; // i在R內部或不在R內部的情況下，不需要檢查的範圍
            while (i + pArr[i] < charArr.length && i - pArr[i] > -1) { // 沒有越界
                if (charArr[i + pArr[i]] == charArr[i - pArr[i]]) {
                    pArr[i]++;   // 左右字母相等, 擴1, 直到不能擴為止
                } else {
                    break;
                }
            }
            if (i + pArr[i] > pR) { // 最遠右邊界變大時要更新其位置和中心
                pR = i + pArr[i];
                index = i;
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }

    public static String shortestEnd(String str) {
        if (str == null || str.length() == 0) return null;
        char[] charArr = manacherString(str);
        int[] pArr = new int[charArr.length];
        int index = -1;
        int pR = -1;
        int maxContainsEnd = -1;
        for (int i = 0; i != charArr.length; i++) {
            pArr[i] = pR > i ? Math.min(pArr[2 * index - i], pR - i) : 1;
            while (i + pArr[i] < charArr.length && i - pArr[i] > -1) {
                if (charArr[i + pArr[i]] == charArr[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            if (i + pArr[i] > pR) {
                pR = i + pArr[i];
                index = i;
            }
            if (pR == charArr.length) {
                maxContainsEnd = pArr[i];
                break;
            }
        }
        char[] res = new char[str.length() - maxContainsEnd + 1];
        for (int i = 0; i < res.length; i++) {
            res[res.length - 1 - i] = charArr[i * 2 + 1];
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        String str1 = "abc1234321ab";
        System.out.println(maxLcpsLength(str1));

        String str2 = "abcd123321";
        System.out.println(shortestEnd(str2));

    }

}
