package GuChengAlgorithm.Ch02_BasicAlgorithm.SlidingWindow;

import java.util.*;

public class Q01_SubStrings {
    // https://docs.google.com/presentation/d/1lGdnMBo_1w50NAPqVMzTTlnlgGBXUxzfHpIToGs2cgE/edit#slide=id.g834bfc4e92_0_41
    public int lengthOfLongestSubString(String s, int k) {
        int N = s.length(), left = 0, res = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) left = Math.max(map.get(c), left);
            map.put(c, i + 1);  // map裡面存的是「不含當前字符c的起點」，也就是c所在位置i的下一位
            res = Math.max(res, i - left + 1);
        }
        return res;
    }


    public int lengthOfLongestSubString2(String s, int k) {
        Set<Character> set = new HashSet<>();
        int N = s.length(), left = 0, res = 0;
        for (int i = 0; i < N; i++) {
            char c = s.charAt(i);
            while (!set.add(c)) set.remove(s.charAt(left++));  // 如果set已經含有當前字符c，就移動左指針縮小窗口，並把左方字符移除
            res = Math.max(res, i - left + 1);
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1lGdnMBo_1w50NAPqVMzTTlnlgGBXUxzfHpIToGs2cgE/edit#slide=id.g834bfc4e92_0_14
    // 1. 當前character入map
    // 2. 移除多餘的character
    // 3. 計算長度結果
    public int lengthOfLongestSubStringTwoDistinct(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int N = s.length(), left = 0, res = 0;
        for (int i = 0; i < N; i++) {
            char cur = s.charAt(i);
            map.put(cur, map.getOrDefault(cur, 0) + 1);  // 1. 當前character入map
            while (map.size() > 2) {  // 2. 移除多餘的character，左指針前進
                char c = s.charAt(left);
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) map.remove(c);
                left++;
            }
            res = Math.max(res, i - left + 1); // 3. 計算長度結果
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1lGdnMBo_1w50NAPqVMzTTlnlgGBXUxzfHpIToGs2cgE/edit#slide=id.g834bfc4e92_0_27
    public int lengthOfLongestSubStringKDistinct(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();
        int N = s.length(), left = 0, res = 0;
        for (int i = 0; i < N; i++) {
            char cur = s.charAt(i);
            map.put(cur, map.getOrDefault(cur, 0) + 1);
            while (map.size() > k) {  // 因為for loop一個一個加入map，所以這邊使用if也同樣效果
                char c = s.charAt(left++);
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) map.remove(c);
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1lGdnMBo_1w50NAPqVMzTTlnlgGBXUxzfHpIToGs2cgE/edit#slide=id.gae033655d8_0_19
    public String minWindow(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);  // 紀錄t的字符種類和數量，下面的for loop目標是「用完」map value
        }

        int left = 0, minStart = 0, minLen = Integer.MAX_VALUE, count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {  // 如果c有在map中，表示找到了t的字符
                if (map.get(c) > 0) count++;  // map裡面的c還有剩餘，就把map裡面的c拿出來使用
                map.put(c, map.get(c) - 1); // 就把map裡面的c拿出來使用後，數量減少，也可能變成負數(表示s之中的c數量多於t)
            }
            while (count == t.length()) {  // 如果找到了t全部的字符(valid count)
                if (i - left + 1 < minLen) {  // 如果找到了更短的子串，就更新長度結果以及左指針位置(子串起點)
                    minLen = i - left + 1;
                    minStart = left;
                }
                char leftChar = s.charAt(left);  // 移動左指針縮小窗口，尋找其他可能性(其實這邊就可以left++)
                if (map.containsKey(leftChar)) {  // 如果左指針有指向map裡面的key，表示即將被排除的leftChar是t需求的字符，必須處理count
                    map.put(leftChar, map.get(leftChar) + 1);  // 如果縮小窗口排除leftChar，就必須還回map，leftChar value + 1
                    if (map.get(leftChar) > 0) count--;  // 如果map裡面的leftChar變回正數，表示t需求的字符沒有全部找到，valid count - 1
                }
                left++;  // 移動左指針縮小窗口
            }
        }
        if (minLen == Integer.MAX_VALUE) return "";
        return s.substring(minStart, minStart + minLen);  // 找到最短長度的左指針，substring(左指針, 左指針 + 最短長度)
    }


    // https://docs.google.com/presentation/d/1lGdnMBo_1w50NAPqVMzTTlnlgGBXUxzfHpIToGs2cgE/edit#slide=id.gb533e9b64a_0_52
    public int longest(String s, int k) {
        int res = 0;
        for (int unique = 1; unique <= 26; unique++) {
            Map<Character, Integer> map = new HashMap<>();
            int left = 0, validCount = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                map.put(c, map.getOrDefault(c, 0) + 1);
                if (map.get(c) == k) validCount++;  // c已經出現k次，validCount + 1
                while (map.keySet().size() > unique) {
                    char leftChar = s.charAt(left);
                    if (map.getOrDefault(map.get(leftChar), 0) == k) validCount--; // 排除掉了已經出現k次的leftChar，validCount - 1
                    map.put(leftChar, map.getOrDefault(map.get(leftChar), 0) - 1);
                    if (map.get(leftChar) == 0) map.remove(leftChar);
                    left++;  // 移動左指針縮小窗口
                }

                // 確定window裡面全部character都必须最少出现k次，才可以計算窗口長度
                int count = map.keySet().size();
                if (count == unique && count == validCount) res = Math.max(i - left + 1, res);
            }
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1lGdnMBo_1w50NAPqVMzTTlnlgGBXUxzfHpIToGs2cgE/edit#slide=id.gae033655d8_0_9
    // 最長重復字元串置換，最多可以變k個字元，讓window裡面全部都是同樣的char，問最長
    public int characterReplace(String s, int k) {
        int N = s.length();
        int[] count = new int[26];
        int left = 0, res = 0;
        for (int i = 0; i < N; i++) {
            count[s.charAt(i) - 'A']++;
            while (i - left + 1 - Arrays.stream((count)).max().getAsInt() > k) {  // 當前窗口長度 - 最長重複字符長度 > 限制k
                count[s.charAt(left) - 'A']--;  // 需要改的數量大於k，就必須嘗試縮小窗口，左指針開始移動，排除掉起點
                left++;
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }
}
