package EndlessCheng.GenreMenu.DP.Other;

import java.util.Deque;
import java.util.LinkedList;

public class MinOperationsToFlip {

    // https://leetcode.cn/problems/minimum-cost-to-change-the-final-value-of-expression/solutions/825496/zhan-dong-tai-gui-hua-javaxiang-xi-zhu-s-t3is/
    public int minOperationsToFlip(String expression) {
        char[] exp = expression.toCharArray();//轉為char[]簡化代碼
        Deque<Integer> num = new LinkedList<>();//棧，存儲當前表達式的結果值
        Deque<Integer> op = new LinkedList<>();//棧，存儲反轉當前表達式的最小操作次數，與表達式結果棧中的元素一一對應（二者也可合並為一個存儲了對應數組的棧）
        Deque<Character> sign = new LinkedList<>();//符號棧
        for (char e : exp) {
            if (e == '(' || e == '&' || e == '|') {//除')'以外的符號直接入棧
                sign.offerFirst(e);
                continue;
            } else if (e == ')') {//遇到右括號，彈出上一左括號，觸發後續計算
                sign.pollFirst();
            } else {//遇到數字，入棧
                num.offerFirst((int) (e - '0'));//數值
                op.offerFirst(1);//任意單個數值反轉操作次數為1
            }
            //計算當前可處理的表達式部分
            if (num.size() > 1 && sign.peekFirst() != '(') {//棧中存在超過2個數字，且未被括號分隔，可處理
                int[] result = minOp(num.pollFirst(), num.pollFirst(), op.pollFirst(), op.pollFirst(), sign.pollFirst());//計算當前表達式的值和最小反轉操作次數
                num.offerFirst(result[0]);
                op.offerFirst(result[1]);
            }
        }
        return op.peekFirst();//反轉操作次數棧此時只剩1個元素，就是整個表達式反轉的最小操作次數
    }

    //針對計算當前表達式中的2個元素，計算值和最小反轉操作次數
    public int[] minOp(int num1, int num2, int op1, int op2, char sign) {
        if (sign == '&') {
            if (num1 == 1 && num2 == 1)//1&1,將其中一個1反轉為0
                return new int[]{1, Math.min(op1, op2)};
            else if (num1 == 0 && num2 == 0)//0&0,將其中一個0反轉為1,並將&反轉為|
                return new int[]{0, Math.min(op1, op2) + 1};
            else//1&0,將&反轉為|
                return new int[]{0, 1};
        } else {
            if (num1 == 0 && num2 == 0)//0|0，將其中一個0反轉為1
                return new int[]{0, Math.min(op1, op2)};
            else if (num1 == 1 && num2 == 1)//1|1,將其中一個1反轉為0，並將|反轉為&
                return new int[]{1, Math.min(op1, op2) + 1};
            else//1|0,將|反轉為&
                return new int[]{1, 1};
        }
    }

}
