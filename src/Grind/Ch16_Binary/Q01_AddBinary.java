package Grind.Ch16_Binary;

public class Q01_AddBinary {
    // https://leetcode.cn/problems/add-binary/solutions/1081207/fu-xue-ming-zhu-qiu-jia-fa-ti-mu-kan-zhe-h4kx/
    // 使用「豎式」計算十進制的加法的方式：
    // 兩個「加數」的右端對齊；
    // 從最右側開始，依次計算對應的兩位數字的和。如果和大於等於 10，則把和的個位數字計入結果，並向前面進位。
    // 依次向左計算對應位置兩位數字的和，如果有進位需要加上進位。如果和大於等於 10，仍然把和的個位數字計入結果，並向前面進位。
    // 當兩個「加數」的每個位置都計算完成，如果最後仍有進位，需要把進位數字保留到計算結果中。
    // 二進制加法的計算也可以采用類似的方法，與十進制不同的是，二進制的進位規則是「逢二進一」，即當求和結果 >=2>= 2>=2 時，需要向前進位。
    public String addBinary(String a, String b) {
        StringBuilder res = new StringBuilder(); // 返回結果
        int i = a.length() - 1; // 標記遍歷到 a 的位置
        int j = b.length() - 1; // 標記遍歷到 b 的位置
        int carry = 0; // 進位

        // 字符串 a 和 b 只要有一個沒遍歷完，那麼就繼續遍歷；
        // 如果字符串 a 和 b 都遍歷完了，但是最後留下的進位 carry != 0，那麼需要把進位也保留到結果中。
        while (i >= 0 || j >= 0 || carry != 0) { // a 沒遍歷完，或 b 沒遍歷完，或進位不為 0
            int digitA = i >= 0 ? a.charAt(i) - '0' : 0; // 當前 a 的取值
            int digitB = j >= 0 ? b.charAt(j) - '0' : 0; // 當前 b 的取值
            int sum = digitA + digitB + carry; // 當前位置相加的結果
            carry = sum >= 2 ? 1 : 0; // 是否有進位
            sum = sum >= 2 ? sum - 2 : sum; // 去除進位後留下的數字
            res.append(sum); // 把去除進位後留下的數字拼接到結果中
            i--;  // 遍歷到 a 的位置向左移動
            j--;  // 遍歷到 b 的位置向左移動
        }
        return res.reverse().toString(); // 把結果反轉並返回
    }
}
