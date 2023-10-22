package Grind.Ch08_HashTable;

public class Q01_RansomNote {
    // https://leetcode.cn/problems/ransom-note/solutions/1685590/by-clever-austinzya-gd7u/
    public boolean canConstruct(String ransomNote, String magazine) {
        // boolean result = false;
        // char[] ransomNoteChar = ransomNote.toCharArray();
        // int len = ransomNoteChar.length;
        // int i;
        // for(i = 0; i < len; ++i){
        //     if(magazine.contains((ransomNoteChar[i]).toString())){
        //         continue;
        //     }
        // }

        // if(i == len) {
        //     result = true;
        // }
        // return result;

        if (ransomNote.length() > magazine.length()) {
            return false;
        }

        int[] cnt = new int[26];

        for (char c : magazine.toCharArray()) {
            cnt[c - 'a']++;
        }
        for (char c : ransomNote.toCharArray()) {
            cnt[c - 'a']--;
            if (cnt[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}
