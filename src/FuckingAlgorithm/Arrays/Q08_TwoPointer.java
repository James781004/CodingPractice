package FuckingAlgorithm.Arrays;

public class Q08_TwoPointer {
    //    https://leetcode.cn/problems/reverse-string/
//            344. 反轉字符串
//    編寫一個函數，其作用是將輸入的字符串反轉過來。輸入字符串以字符數組 s 的形式給出。
//
//    不要給另外的數組分配額外的空間，你必須原地修改輸入數組、使用 O(1) 的額外空間解決這一問題。

    void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }


    //    https://leetcode.cn/problems/longest-palindromic-substring/
//            5. 最長回文子串
//    給你一個字符串 s，找到 s 中最長的回文子串。
// 在 s 中尋找以 s[l] 和 s[r] 為中心的最長回文串
    String palindrome(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            // 以 s[i] 為中心的最長回文子串
            String s1 = palindrome(s, i, i);
            // 以 s[i] 和 s[i+1] 為中心的最長回文子串
            String s2 = palindrome(s, i, i + 1);
            // res = longest(res, s1, s2)
            res = res.length() > s1.length() ? res : s1;
            res = res.length() > s2.length() ? res : s2;
        }
        return res;
    }

    // 在 s 中尋找以 s[l] 和 s[r] 為中心的最長回文串
    String palindrome(String s, int l, int r) {
        // 防止索引越界
        while (l >= 0 && r < s.length()
                && s.charAt(l) == s.charAt(r)) {
            // 雙指針，向兩邊展開
            l--;
            r++;
        }
        // 返回以 s[l] 和 s[r] 為中心的最長回文串
        return s.substring(l + 1, r);
    }
}
