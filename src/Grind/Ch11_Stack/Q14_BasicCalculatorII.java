package Grind.Ch11_Stack;

import java.util.HashMap;
import java.util.Stack;

public class Q14_BasicCalculatorII {

    int[] numStack;
    char[] charStack;
    int p1 = -1, p2 = -1;

    public int calculate(String s) {
        /*
        雙棧
        1.用HashMap存儲運算的優先級，加減為1，乘除為2
        2.創建兩個棧，其中一個是數字棧，另一個為字符棧
        3.遍歷字符串s
            3.1 當遇到數字時，統計完整數字入數字棧
            3.2 當遇到運算符時，把之前的大於等於當前符號運算級的符號全部運算，後該符號入棧
         */
        HashMap<Character, Integer> map = new HashMap<Character, Integer>() {{
            put('+', 1);
            put('-', 1);
            put('*', 2);
            put('/', 2);
        }};
        s = s.replaceAll(" ", "");
        if (s.charAt(0) == '-') s = "0" + s;
        int n = s.length();
        numStack = new int[10];
        charStack = new char[10];
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int j = i + 1, num = c - '0';
                while (j < n && Character.isDigit(s.charAt(j))) {
                    num = num * 10 + (s.charAt(j) - '0');
                    j++;
                }
                i = j - 1;
                numStack[++p1] = num;
            } else {
                int priority = map.get(c);
                // 把之前優先級相等的全部算完
                while (p2 >= 0 && map.get(charStack[p2]) >= priority) calc();
                // 當前符號入棧
                charStack[++p2] = c;
            }
        }
        // 計算最外圍的式子
        while (p2 >= 0) calc();
        return numStack[0];
    }

    // 進行一次計算
    private void calc() {
        int b = numStack[p1--], a = numStack[p1--];
        p1++;
        if (charStack[p2] == '+') {
            numStack[p1] = a + b;
        } else if (charStack[p2] == '-') {
            numStack[p1] = a - b;
        } else if (charStack[p2] == '*') {
            numStack[p1] = a * b;
        } else {
            numStack[p1] = a / b;
        }
        p2--;
    }


    // 普通解法
    // 由於乘除優先於加減計算，因此不妨考慮先進行所有乘除運算，
    // 並將這些乘除運算後的整數值放回原表達式的相應位置，則隨後整個表達式的值，就等於一系列整數加減後的值
    public int calculate2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        // 用一個棧，保存這些（進行乘除運算後的）整數的值
        // 對於加減號後的數字，將其直接壓入棧中；
        // 對於乘除號後的數字，可以直接與棧頂元素計算，並替換棧頂元素為計算後的結果
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        char sign = '+'; // 記錄每個數字之前的運算符
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                num = s.charAt(i) - '0';
                while (i + 1 < s.length() && (Character.isDigit(s.charAt(i + 1)))) {
                    num = num * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
            }

            // i == s.length() - 1: dealing with the last digit
            if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ' || i == s.length() - 1) {
                if (sign == '+') { // 加號：將數字壓入棧
                    stack.push(num);
                } else if (sign == '-') { // 減號：將數字的相反數壓入棧
                    stack.push(-num);
                } else if (sign == '*') { // 乘除號：計算數字與棧頂元素，並將棧頂元素替換為計算結果
                    stack.push(stack.pop() * num);
                } else if (sign == '/') {
                    stack.push(stack.pop() / num);
                }

                // 若讀到一個運算符，或者遍歷到字符串末尾，即認為是遍歷到了數字末尾。
                // 處理完該數字後，更新 sign 為當前遍歷的字符
                sign = s.charAt(i); // reset current sigh if we meet the sign
                num = 0; // reset num to 0 for next loop
            }
        }

        // 棧中元素累加，即為該字符串表達式的值
        for (int i : stack) { // add all the num in the stack together
            res += i;
        }
        return res;
    }
}
