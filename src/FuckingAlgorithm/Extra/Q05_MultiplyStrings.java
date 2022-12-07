package FuckingAlgorithm.Extra;

public class Q05_MultiplyStrings {
//    https://leetcode.cn/problems/multiply-strings/
//    43. 字符串相乘
//    給定兩個以字符串形式表示的非負整數 num1 和 num2，返回 num1 和 num2 的乘積，它們的乘積也表示為字符串形式。
//
//    注意：不能使用任何內置的 BigInteger 庫或直接將輸入轉換為整數。

    public String multiply(String num1, String num2) {
        int m = num1.length(), n = num2.length();
        int[] res = new int[m + n];  // 結果最多為 m + n 位數

        // 從個位數開始逐位相乘
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int sum = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j, p2 = i + j + 1; // 乘積在 res 對應的索引位置
                sum += res[p2];  // 疊加到 res 上
                res[p2] = sum % 10;
                res[p1] += sum / 10;
            }
        }

        // 結果前綴可能存的 0（未使用的位）
        int i = 0;
        while (i < res.length && res[i] == 0) {
            i++;
        }

        // 將計算結果轉化成字符串
        StringBuilder sb = new StringBuilder();
        for (; i < res.length; i++) {
            sb.append(res[i] + "");
        }

        return sb.length() == 0 ? "0" : sb.toString();
    }
}
