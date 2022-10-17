package FuckingAlgorithm.Arrays;

import java.util.*;

public class Q07_SlidingWindow {
    //    https://leetcode.cn/problems/minimum-window-substring/
//    76. 最小覆蓋子串
//    給你一個字符串 s 、一個字符串 t 。返回 s 中涵蓋 t 所有字符的最小子串。如果 s 中不存在涵蓋 t 所有字符的子串，則返回空字符串 "" 。
    public String minWindow(String s, String t) {
        if (t.length() > s.length()) {
            return "";
        }

        Map<Character, Integer> need = new HashMap<>(); // 統計所需字符及其數量
        for (int i = 0; i < t.length(); i++) {
            need.put(t.charAt(i), need.getOrDefault(t.charAt(i), 0) + 1);
        }

        Map<Character, Integer> window = new HashMap<>(); // 統計窗口內的字符及其數量
        int valid = 0;// 滿足條件的字符數
        int left = 0, right = 0;// left-right pointers
        int min_LEN = Integer.MAX_VALUE, min_LEFT = 0; //記錄最小窗口的長度和開始位置

        while (right < s.length()) {
            // 擴大右邊界
            char c = s.charAt(right);
            right++;
            window.put(c, window.getOrDefault(c, 0) + 1); // 相當於window[c]++
            if (window.get(c).equals(need.get(c))) {
                valid++;
            }

            // 滿足條件時縮小窗口
            while (valid == need.size()) {
                // 記錄當前窗口
                if (right - left < min_LEN) {
                    min_LEN = right - left;
                    min_LEFT = left;
                }

                // 縮小左邊界
                char d = s.charAt(left);
                if (window.get(d).equals(need.get(d))) {
                    valid--;
                }
                window.put(d, window.get(d) - 1);  // 相當於window[d]--
                left++;
            }
        }
        return min_LEN == Integer.MAX_VALUE ? "" : s.substring(min_LEFT, min_LEFT + min_LEN);
    }


//    https://leetcode.cn/problems/permutation-in-string/
//    567. 字符串的排列
//    給你兩個字符串 s1 和 s2 ，寫一個函數來判斷 s2 是否包含 s1 的排列。如果是，返回 true ；否則，返回 false 。
//
//    換句話說，s1 的排列之一是 s2 的 子串 。

    public boolean checkInclusion(String t, String s) {
        if (t.length() > s.length()) {
            return false;
        }

        Map<Character, Integer> need = new HashMap<>(); // 統計所需字符及其數量
        for (int i = 0; i < t.length(); i++) {
            need.put(t.charAt(i), need.getOrDefault(t.charAt(i), 0) + 1);
        }

        Map<Character, Integer> window = new HashMap<>(); // 統計窗口內的字符及其數量
        int valid = 0;// 滿足條件的字符數
        int left = 0, right = 0;// left-right pointers

        while (right < s.length()) {
            // 擴大右邊界
            char c = s.charAt(right);
            right++;

            // 進行窗口內數據的一系列更新
            window.put(c, window.getOrDefault(c, 0) + 1);
            if (window.get(c).equals(need.get(c))) {
                valid++;
            }

            // 判斷左側窗口是否要收縮
            while (right - left >= t.length()) {
                // 在這裡判斷是否找到了合法的子串
                if (valid == need.size()) return true;

                // 縮小左邊界
                char d = s.charAt(left);
                if (window.get(d).equals(need.get(d))) {
                    valid--;
                }
                window.put(d, window.get(d) - 1);
                left++;
            }
        }

        // 未找到符合條件的子串
        return false;
    }

//    https://leetcode.cn/problems/find-all-anagrams-in-a-string/
//    438. 找到字符串中所有字母異位詞
//    給定兩個字符串 s 和 p，找到 s 中所有 p 的 異位詞 的子串，返回這些子串的起始索引。不考慮答案輸出的順序。
//
//    異位詞 指由相同字母重排列形成的字符串（包括相同的字符串）。

    public List<Integer> findAnagrams(String t, String s) {
        if (t.length() > s.length()) return null;

        Map<Character, Integer> need = new HashMap<>(); // 統計所需字符及其數量
        for (int i = 0; i < t.length(); i++) {
            need.put(t.charAt(i), need.getOrDefault(t.charAt(i), 0) + 1);
        }

        List<Integer> res = new ArrayList<>();
        Map<Character, Integer> window = new HashMap<>(); // 統計窗口內的字符及其數量
        int valid = 0;// 滿足條件的字符數
        int left = 0, right = 0; // left-right pointers

        while (right < s.length()) {
            // 擴大右邊界
            char c = s.charAt(right);
            right++;

            // 進行窗口內數據的一系列更新
            window.put(c, window.getOrDefault(c, 0) + 1);
            if (window.get(c).equals(need.get(c))) {
                valid++;
            }

            // 判斷左側窗口是否要收縮
            while (right - left >= t.length()) {
                // 當窗口符合條件時，把起始索引加入 res
                if (valid == need.size()) {
                    res.add(left);
                }

                // 縮小左邊界
                char d = s.charAt(left);
                if (window.get(d).equals(need.get(d))) {
                    valid--;
                }
                window.put(d, window.get(d) - 1);
                left++;
            }
        }
        return res;
    }

    //    https://leetcode.cn/problems/longest-substring-without-repeating-characters/
//    3. 無重復字符的最長子串
//    給定一個字符串 s ，請你找出其中不含有重復字符的 最長子串 的長度。
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> window = new HashMap<>(); // 統計窗口內的字符及其數量
        int res = 0;// 記錄結果
        int left = 0, right = 0; // left-right pointers
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;

            // 進行窗口內數據的一系列更新
            window.put(c, window.getOrDefault(c, 0) + 1);

            // 判斷左側窗口是否要收縮
            while (window.get(c) > 1) {
                // 縮小左邊界
                char d = s.charAt(left);
                window.put(d, window.get(d) - 1);
                left++;
            }

            // 在這裡更新答案
            res = Math.max(res, right - left);
        }

        return res;
    }


    // Rabin-Karp 指紋字符串查找算法
    public int rabinKarp(String txt, String pat) {
        // 位數
        int L = pat.length();
        // 進制（只考慮 ASCII 編碼）
        int R = 256;
        // 取一個比較大的素數作為求模的除數
        long Q = 1658598167;
        // R^(L - 1) 的結果
        long RL = 1;
        for (int i = 1; i <= L - 1; i++) {
            // 計算過程中不斷求模，避免溢出
            RL = (RL * R) % Q;
        }
        // 計算模式串的哈希值，時間 O(L)
        long patHash = 0;
        for (int i = 0; i < pat.length(); i++) {
            patHash = (R * patHash + pat.charAt(i)) % Q;
        }

        // 滑動窗口中子字符串的哈希值
        long windowHash = 0;

        // 滑動窗口代碼框架，時間 O(N)
        int left = 0, right = 0;
        while (right < txt.length()) {
            // 擴大窗口，移入字符
            windowHash = ((R * windowHash) % Q + txt.charAt(right)) % Q;
            right++;

            // 當子串的長度達到要求
            if (right - left == L) {
                // 根據哈希值判斷是否匹配模式串
                if (windowHash == patHash) {
                    // 當前窗口中的子串哈希值等於模式串的哈希值
                    // 還需進一步確認窗口子串是否真的和模式串相同，避免哈希沖突
                    if (pat.equals(txt.substring(left, right))) {
                        return left;
                    }
                }
                // 縮小窗口，移出字符
                windowHash = (windowHash - (txt.charAt(left) * RL) % Q + Q) % Q;
                // X % Q == (X + Q) % Q 是一個模運算法則
                // 因為 windowHash - (txt[left] * RL) % Q 可能是負數
                // 所以額外再加一個 Q，保證 windowHash 不會是負數

                left++;
            }
        }
        // 沒有找到模式串
        return -1;
    }

//    https://leetcode.cn/problems/repeated-dna-sequences/
//    187. 重復的DNA序列
//    DNA序列 由一系列核苷酸組成，縮寫為 'A', 'C', 'G' 和 'T'.。
//
//    例如，"ACGAATTCCG" 是一個 DNA序列 。
//    在研究 DNA 時，識別 DNA 中的重復序列非常有用。
//
//    給定一個表示 DNA序列 的字符串 s ，返回所有在 DNA 分子中出現不止一次的 長度為 10 的序列(子字符串)。你可以按 任意順序 返回答案。

    // Sliding Window
    public static List<String> findRepeatedDnaSequences(String s) {
        int L = 10;
        Set<String> res = new HashSet();
        HashSet<String> seen = new HashSet<>();

        List<Character> window = new ArrayList<>();
        int left = 0;
        int right = 0;

        while (right < s.length()) {
            // 擴大右邊界
            window.add(s.charAt(right));
            right++;

            // 當子串的長度達到要求
            if (right - left == L) {
                String windowStr = "";

                for (int i = 0; i < window.size(); i++) {
                    windowStr += window.get(i);
                }

                if (seen.contains(windowStr)) {
//                   System.out.println("找到一個重復子串: "+ windowStr);
                    res.add(windowStr);
                } else {
                    seen.add(windowStr);
                }

                // 縮小窗口，移出字符
                window.remove(0);
                left++;
            }
        }

        return new ArrayList<>(res);
    }


    // Rabin-Karp 指紋字符串查找算法
    List<String> findRepeatedDnaSequences2(String s) {
        // 先把字符串轉化成四進制的數字數組
        int[] nums = new int[s.length()];
        for (int i = 0; i < nums.length; i++) {
            switch (s.charAt(i)) {
                case 'A':
                    nums[i] = 0;
                    break;
                case 'G':
                    nums[i] = 1;
                    break;
                case 'C':
                    nums[i] = 2;
                    break;
                case 'T':
                    nums[i] = 3;
                    break;
            }
        }

        // 記錄重復出現的哈希值
        HashSet<Integer> seen = new HashSet<>();
        // 記錄重復出現的字符串結果
        HashSet<String> res = new HashSet<>();

        // 數字位數
        int L = 10;
        // 進制
        int R = 4;
        // 存儲 R^(L - 1) 的結果
        int RL = (int) Math.pow(R, L - 1);
        // 維護滑動窗口中字符串的哈希值
        int windowHash = 0;

        // 滑動窗口代碼框架，時間 O(N)
        int left = 0, right = 0;
        while (right < nums.length) {
            // 擴大窗口，移入字符，並維護窗口哈希值（在最低位添加數字）
            windowHash = R * windowHash + nums[right];
            right++;

            // 當子串的長度達到要求
            if (right - left == L) {
                // 根據哈希值判斷是否曾經出現過相同的子串
                if (seen.contains(windowHash)) {
                    // 當前窗口中的子串是重復出現的
                    res.add(s.substring(left, right));
                } else {
                    // 當前窗口中的子串之前沒有出現過，記下來
                    seen.add(windowHash);
                }
                // 縮小窗口，移出字符，並維護窗口哈希值（刪除最高位數字）
                windowHash = windowHash - nums[left] * RL;
                left++;
            }
        }
        // 轉化成題目要求的 List 類型
        return new LinkedList<>(res);
    }
}
