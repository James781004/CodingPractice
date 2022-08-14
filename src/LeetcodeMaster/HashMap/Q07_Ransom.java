package LeetcodeMaster.HashMap;

public class Q07_Ransom {
//    383. 贖金信
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0383.%E8%B5%8E%E9%87%91%E4%BF%A1.md
//
//    給定一個贖金信 (ransom) 字符串和一個雜志(magazine)字符串，判斷第一個字符串 ransom 能不能由第二個字符串 magazines 里面的字符構成。
//    如果可以構成，返回 true ；否則返回 false。
//    (題目說明：為了不暴露贖金信字跡，要從雜志上搜索各個需要的字母，組成單詞來表達意思。雜志字符串中的每個字符只能在贖金信字符串中使用一次。)
//
//    注意：
//
//    你可以假設兩個字符串均只含有小寫字母。
//
//    canConstruct("a", "b") -> false
//    canConstruct("aa", "ab") -> false
//    canConstruct("aa", "aab") -> true


    public boolean canConstruct(String ransomNote, String magazine) {
        int[] record = new int[26];

        // 通過recode數據記錄 magazine里各個字符出現次數
        for (char c : magazine.toCharArray()) {
            record[c - 'a']++;
        }

        for (char c : ransomNote.toCharArray()) {
//            record[c - 'a']--;
            // 如果數組中存在負數，說明ransomNote字符串總存在magazine中沒有的字符
            if (--record[c - 'a'] < 0) {
                return false;
            }
        }


        return true;
    }
}
