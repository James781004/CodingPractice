package LeetcodeMaster.String;

public class Q02_Reverse2 {
//    541. 反轉字符串II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0541.%E5%8F%8D%E8%BD%AC%E5%AD%97%E7%AC%A6%E4%B8%B2II.md
//
//    給定一個字符串 s 和一個整數 k，你需要對從字符串開頭算起的每隔 2k 個字符的前 k 個字符進行反轉。
//
//    如果剩余字符少於 k 個，則將剩余字符全部反轉。
//
//    如果剩余字符小於 2k 但大於或等於 k 個，則反轉前 k 個字符，其余字符保持原樣。
//
//    示例:
//
//    輸入: s = "abcdefg", k = 2
//    輸出: "bacdfeg"

    public String reverseStr1(String s, int k) {
        StringBuffer res = new StringBuffer();
        int length = s.length();
        int start = 0;
        while (start < length) {
            // 找到k處和2k處
            StringBuffer temp = new StringBuffer();
            // 與length進行判斷，如果大於length了，那就將其置為length
            int firstK = (start + k > length) ? length : start + k;
            int secondK = (start + (2 * k) > length) ? length : start + (2 * k);

            //無論start所處位置，至少會反轉一次
            temp.append(s.substring(start, firstK));
            res.append(temp.reverse());

            // 如果firstK到secondK之間有元素，這些元素直接放入res里即可。
            if (firstK < secondK) { //此時剩余長度一定大於k。
                res.append(s.substring(firstK, secondK));
            }
            start += (2 * k);
        }
        return res.toString();
    }


    public String reverseStr2(String s, int k) {
        char[] ch = s.toCharArray();
        for (int i = 0; i < ch.length; i += 2 * k) {
            int start = i;

            // 判斷尾數夠不夠k個來取決end指針的位置
            int end = Math.min(ch.length - 1, start + k - 1);

            // 用異或運算反轉
            while (start < end) {
                ch[start] ^= ch[end];
                ch[end] ^= ch[start];
                ch[start] ^= ch[end];
                start++;
                end--;
            }
        }

        return new String(ch);
    }


    public String reverseStr3(String s, int k) {
        char[] ch = s.toCharArray();
        // 1. 每隔 2k 個字符的前 k 個字符進行反轉
        for (int i = 0; i < ch.length; i += 2 * k) {
            // 2. 剩余字符小於 2k 但大於或等於 k 個，則反轉前 k 個字符
            if (i + k <= ch.length) {
                reverse(ch, i, i + k - 1);
                continue;
            }
            // 3. 剩余字符少於 k 個，則將剩余字符全部反轉
            reverse(ch, i, ch.length - 1);
        }
        return new String(ch);
    }

    // 定義翻轉函數
    public void reverse(char[] ch, int i, int j) {
        for (; i < j; i++, j--) {
            char temp = ch[i];
            ch[i] = ch[j];
            ch[j] = temp;
        }

    }
}
