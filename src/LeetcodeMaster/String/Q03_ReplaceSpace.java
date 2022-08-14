package LeetcodeMaster.String;

public class Q03_ReplaceSpace {
//    題目：劍指Offer 05.替換空格
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/%E5%89%91%E6%8C%87Offer05.%E6%9B%BF%E6%8D%A2%E7%A9%BA%E6%A0%BC.md
//
//    請實現一個函數，把字符串 s 中的每個空格替換成"%20"。
//
//    示例 1： 輸入：s = "We are happy."
//    輸出："We%20are%20happy."

    //使用一個新的對象，覆制 str，覆制的過程對其判斷，是空格則替換，否則直接覆制，類似於數組覆制
    public static String replaceSpace(StringBuffer str) {
        if (str == null) return null;

        // 選用 StringBuilder 單線程使用，比較快，選不選都行
        StringBuilder sb = new StringBuilder();

        // 使用 sb 複製 str ，碰到空格則替換，否則直接複製
        for (int i = 0; i < str.length(); i++) {
            // str.charAt(i) 為 char 類型，為了比較需要將其轉為和 " " 相同的字符串類型
            // if (" ".equals(String.valueOf(str.charAt(i)))){
            if (str.charAt(i) == ' ') {
                sb.append("%20");
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    // 雙指針法
    public String replaceSpace(String s) {
        if (s == null || s.length() == 0) return null;

        // 選用 StringBuilder 單線程使用，比較快，選不選都行
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                sb.append("  ");
            }
        }

        // 若是沒有空格直接返回
        if (sb.length() == 0) {
            return s;
        }

        // 有空格情況 定義兩個指針
        int left = s.length() - 1; // 左指針：指向原始字符串最後一個位置
        s += sb.toString();
        int right = s.length() - 1; // 右指針：指向擴展字符串的最後一個位置
        char[] chars = s.toCharArray();

        while (left >= 0) {
            if (chars[left] == ' ') {
                chars[right--] = '0';
                chars[right--] = '2';
                chars[right] = '%';
            } else {
                chars[right] = chars[left];
            }
            left--;
            right--;
        }

        return new String(chars);
    }
}
