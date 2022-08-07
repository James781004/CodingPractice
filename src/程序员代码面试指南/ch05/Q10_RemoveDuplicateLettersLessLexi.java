package 程序员代码面试指南.ch05;

public class Q10_RemoveDuplicateLettersLessLexi {
//    題目
//    給定一個全是小寫字母的字符串str, 刪除多余字符，使得每種字符只保留一個，並讓最終結果的字符串的字典序最小
//
//            舉例
//    str='acbc', 刪掉第一個'c', 得到‘abc’，是所有結果字符串中字典序最小的。
//    str='dbcacbca’, 刪掉第一個‘b’, 'c', 第二個‘c’,'a', 得到‘dabc’是所有結果字符串中字典序最小的。

    public static String removeDuplicateLetters(String s) {
        char[] str = s.toCharArray();
        // 小寫字母ascii碼值範圍[97~122]，所以用長度為26的數組做次數統計
        // 如果map[i] > -1，則代表ascii碼值為i的字符的出現次數
        // 如果map[i] == -1，則代表ascii碼值為i的字符不再考慮
        int[] map = new int[26];
        for (int i = 0; i < str.length; i++) {
            map[str[i] - 'a']++;
        }
        char[] res = new char[26];
        int index = 0;
        int L = 0;
        int R = 0;
        while (R != str.length) {
            // 如果當前字符是不再考慮的，直接跳過
            // 如果當前字符的出現次數減1之後，後面還能出現，直接跳過
            if (map[str[R] - 'a'] == -1 || --map[str[R] - 'a'] > 0) {
                R++;
            } else { // 當前字符需要考慮並且之後不會再出現了
                // 在str[L..R]上所有需要考慮的字符中，找到ascii碼最小字符的位置
                int pick = -1;
                for (int i = L; i <= R; i++) {
                    if (map[str[i] - 'a'] != -1 && (pick == -1 || str[i] < str[pick])) {
                        pick = i;
                    }
                }
                // 把ascii碼最小的字符放到挑選結果中
                res[index++] = str[pick];
                // 在上一個的for循環中，str[L..R]範圍上每種字符的出現次數都減少了
                // 需要把str[pick + 1..R]上每種字符的出現次數加回來
                for (int i = pick + 1; i <= R; i++) {
                    if (map[str[i] - 'a'] != -1) { // 只增加以後需要考慮字符的次數
                        map[str[i] - 'a']++;
                    }
                }
                // 選出的ascii碼最小的字符，以後不再考慮了
                map[str[pick] - 'a'] = -1;
                // 繼續在str[pick + 1......]上重覆這個過程
                L = pick + 1;
                R = L;
            }
        }
        return String.valueOf(res, 0, index);
    }
}
