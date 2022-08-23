package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.List;

public class Q03_PhoneNumber {
//    17.電話號碼的字母組合
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0017.%E7%94%B5%E8%AF%9D%E5%8F%B7%E7%A0%81%E7%9A%84%E5%AD%97%E6%AF%8D%E7%BB%84%E5%90%88.md
//
//    給定一個僅包含數字 2-9 的字符串，返回所有它能表示的字母組合。
//
//    示例: 輸入："23" 輸出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
//
//    說明：盡管上面的答案是按字典序排列的，但是你可以任意選擇答案輸出的順序。


    // 初始對應所有的數字，為了直接對應2-9，新增了兩個無效的字符串""
    String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    // 設置全局列表存儲最後的結果
    List<String> list = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return list;
        }

        // 迭代處理
        process(digits, 0);
        return list;
    }


    // 每次迭代獲取一個字符串，所以會設計大量的字符串拼接，所以這里選擇更為高效的 StringBuilder
    StringBuilder temp = new StringBuilder();

    // 比如digits如果為"23"，index 為0，則digitLetters表示2對應的 abc
    public void process(String digits, int index) {
        if (index == digits.length()) {
            list.add(temp.toString());
            return;
        }

        // digitLetters 表示當前index對應的字符串
        String digitLetters = numString[digits.charAt(index) - '0'];
        for (int i = 0; i < digitLetters.length(); i++) {
            temp.append(digitLetters.charAt(i)); // 處理當前決策，字符拼接在temp最尾部
            process(digits, index + 1); // 進入遞歸，index+1表示要處理下一個數字
            temp.deleteCharAt(temp.length() - 1); // 回溯前一步決策，移除temp最尾字符
        }
    }
}
