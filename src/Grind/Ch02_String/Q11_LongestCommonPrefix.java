package Grind.Ch02_String;

public class Q11_LongestCommonPrefix {
    // https://leetcode.com/problems/longest-common-prefix/
    // https://leetcode.cn/problems/longest-common-prefix/solutions/567919/javazhong-liang-chong-bi-jiao-hao-li-jie-ezx5/
    public String longestCommonPrefix(String[] strs) {
        int m = strs.length;
        // 以第 0 行的列數為基准
        int n = strs[0].length();

        // 外層遍歷字符 str 的每個字元 str.charAt(col)，遍歷不能超過字符本身長度
        for (int col = 0; col < n; col++) {
            // 內層遍歷每個 str
            for (int row = 1; row < m; row++) {
                String thisStr = strs[row], prevStr = strs[row - 1];
                // 判斷每個字符串的 col 索引是否都相同
                // 首先是比較每個 str 的 [0]，然後 [0..1]，然後 [0..2] 以此類推
                if (col >= thisStr.length() || col >= prevStr.length() ||
                        thisStr.charAt(col) != prevStr.charAt(col)) {
                    // 發現不匹配的字符，只有 strs[row][0..col-1] 是公共前綴
                    return strs[row].substring(0, col);
                }
            }
        }
        return strs[0];
    }


    // 使用內建函數
    public String longestCommonPrefix2(String[] strs) {
        if (strs.length == 0) return "";
        String res = strs[0];  // 用於儲存最長公共前綴，初始化為strs[0]
        for (String str : strs) {
            // 當字符串str不以res為前綴時，就對res截斷最後一個字符
            while (!str.startsWith(res)) {
                res = res.substring(0, res.length() - 1);
            }
        }
        return res;
    }

}
