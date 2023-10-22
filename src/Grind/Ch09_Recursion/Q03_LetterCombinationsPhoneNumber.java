package Grind.Ch09_Recursion;

import java.util.ArrayList;
import java.util.List;

public class Q03_LetterCombinationsPhoneNumber {
    // https://leetcode.cn/problems/letter-combinations-of-a-phone-number/solutions/839901/dai-ma-sui-xiang-lu-17-dian-hua-hao-ma-d-ya2x/
    // 設置全局列表存儲最後的結果
    List<String> list = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return list;
        }
        //初始對應所有的數字，為了直接對應2-9，新增了兩個無效的字符串""
        String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        //迭代處理
        backTracking(digits, numString, 0);
        return list;

    }

    // 每次迭代獲取一個字符串，所以會設計大量的字符串拼接，所以這裡選擇更為高效的 StringBuilder
    StringBuilder temp = new StringBuilder();

    // 比如digits如果為"23",num 為0，則str表示2對應的 abc
    public void backTracking(String digits, String[] numString, int num) {
        // 遍歷全部一次記錄一次得到的字符串
        if (num == digits.length()) {
            list.add(temp.toString());
            return;
        }
        // str 表示當前num對應的字符串
        String str = numString[digits.charAt(num) - '0'];
        for (int i = 0; i < str.length(); i++) {
            temp.append(str.charAt(i));
            // c
            backTracking(digits, numString, num + 1);
            // 剔除末尾的繼續嘗試
            temp.deleteCharAt(temp.length() - 1);
        }
    }
}
