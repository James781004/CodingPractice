package LeetcodeMaster.TwoPointer;

public class Q03_ReplaceSpaces {
    //    題目：劍指Offer 05.替換空格
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/%E5%89%91%E6%8C%87Offer05.%E6%9B%BF%E6%8D%A2%E7%A9%BA%E6%A0%BC.md
//
//    請實現一個函數，把字符串 s 中的每個空格替換成"%20"。
//
//    示例 1： 輸入：s = "We are happy."
//    輸出："We%20are%20happy."

    public static char[] replace(char[] chas) {
        if (chas == null || chas.length == 0) {
            return chas;
        }
        int num = 0;
        int len = 0;
        for (len = 0; len < chas.length && chas[len] != 0; len++) {
            if (chas[len] == ' ') {
                num++; // 紀錄空格數
            }
        }

        // 空格變成%20之後，字串總長度變成len + 2 * num，下標位置再減去1
        int j = len + num * 2 - 1;
        char[] res = new char[j + 1];

        // 逆向遍歷字串把結果放進chas
        for (int i = len - 1; i > -1; i--) {
            if (chas[i] != ' ') {
                res[j--] = chas[i];
            } else {
                res[j--] = '0';
                res[j--] = '2';
                res[j--] = '%';
            }
        }

        return res;
    }
}
