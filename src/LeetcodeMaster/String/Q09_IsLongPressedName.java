package LeetcodeMaster.String;

public class Q09_IsLongPressedName {
//    925.長按鍵入
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0925.%E9%95%BF%E6%8C%89%E9%94%AE%E5%85%A5.md
//
//    你的朋友正在使用鍵盤輸入他的名字 name。偶爾，在鍵入字符 c 時，按鍵可能會被長按，而字符可能被輸入 1 次或多次。
//
//    你將會檢查鍵盤輸入的字符 typed。如果它對應的可能是你的朋友的名字（其中一些字符可能被長按），那麼就返回 True。
//
//    示例 1：
//
//    輸入：name = "alex", typed = "aaleex"
//    輸出：true
//    解釋：'alex' 中的 'a' 和 'e' 被長按。
//    示例 2：
//
//    輸入：name = "saeed", typed = "ssaaedd"
//    輸出：false
//    解釋：'e' 一定需要被鍵入兩次，但在 typed 的輸出中不是這樣。
//    示例 3：
//
//    輸入：name = "leelee", typed = "lleeelee"
//    輸出：true
//    示例 4：
//
//    輸入：name = "laiden", typed = "laiden"
//    輸出：true
//    解釋：長按名字中的字符並不是必要的。

    public boolean isLongPressedName(String name, String typed) {
        int i = 0, j = 0;
        int m = name.length(), n = typed.length();
        while (i < m && j < n) {
            if (name.charAt(i) == typed.charAt(j)) {  // 相同則同時向後匹配
                i++;
                j++;
            } else {
                if (j == 0) return false; // 如果是第一位就不相同直接返回false
                // 判斷邊界為n-1,若為n會越界,例如name:"kikcxmvzi" typed:"kiikcxxmmvvzzz"
                while (j < n - 1 && typed.charAt(j) == typed.charAt(j - 1)) j++;
                if (name.charAt(i) == typed.charAt(j)) {  // j跨越重覆項之後再次和name[i]匹配
                    i++;
                    j++; // 相同則同時向後匹配
                } else return false;
            }
        }

        // 說明name沒有匹配完
        if (i < m) return false;
        // 說明type沒有匹配完
        while (j < n) {
            if (typed.charAt(j) == typed.charAt(j - 1)) j++;
            else return false;
        }
        return true;
    }
}
