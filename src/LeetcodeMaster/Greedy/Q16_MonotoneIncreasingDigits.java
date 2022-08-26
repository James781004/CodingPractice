package LeetcodeMaster.Greedy;

public class Q16_MonotoneIncreasingDigits {
//    738.單調遞增的數字
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0738.%E5%8D%95%E8%B0%83%E9%80%92%E5%A2%9E%E7%9A%84%E6%95%B0%E5%AD%97.md
//
//    給定一個非負整數 N，找出小於或等於 N 的最大的整數，同時這個整數需要滿足其各個位數上的數字是單調遞增。
//
//            （當且僅當每個相鄰位數上的數字 x 和 y 滿足 x <= y 時，我們稱這個整數是單調遞增的。）
//
//    示例 1:
//
//    輸入: N = 10
//    輸出: 9
//    示例 2:
//
//    輸入: N = 1234
//    輸出: 1234
//    示例 3:
//
//    輸入: N = 332
//    輸出: 299
//    說明: N 是在 [0, 10^9] 範圍內的一個整數。


    // 貪心思路：
    // 局部最優：遇到strNum[i - 1] > strNum[i]的情況，讓strNum[i - 1]--，然後strNum[i]給為9，可以保證這兩位變成最大單調遞增整數。
    // 全局最優：得到小於等於N的最大單調遞增的整數。
    public int monotoneIncreasingDigits(int N) {
        String[] strings = (N + "").split("");

        // start用來標記賦值9從哪里開始
        // 設置為這個默認值，為了防止第二個for循環在start沒有被賦值的情況下執行
        int start = strings.length;

        for (int i = strings.length - 1; i > 0; i--) {
            if (Integer.parseInt(strings[i]) < Integer.parseInt(strings[i - 1])) {
                strings[i - 1] = (Integer.parseInt(strings[i - 1]) - 1) + "";
                start = i;
            }
        }

        for (int i = start; i < strings.length; i++) {
            strings[i] = "9"; // 從start位置開始賦值9
        }
        return Integer.parseInt(String.join("", strings));
    }

    public int monotoneIncreasingDigits2(int n) {
        String s = String.valueOf(n);
        char[] chars = s.toCharArray();

        // start用來標記賦值9從哪里開始
        // 設置為這個默認值，為了防止第二個for循環在start沒有被賦值的情況下執行
        int start = s.length();

        for (int i = s.length() - 2; i >= 0; i--) {
            if (chars[i] > chars[i + 1]) {
                chars[i]--;
                start = i + 1;
            }
        }
        for (int i = start; i < s.length(); i++) {
            chars[i] = '9';  // 從start位置開始賦值9
        }
        return Integer.parseInt(String.valueOf(chars));
    }

}
