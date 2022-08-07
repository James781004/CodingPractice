package 程序员代码面试指南.ch05;

import java.util.LinkedList;

public class Q15_ExpressionCompute {
//    公式字符串求值
//    描述
//    给定一个字符串str，str表示一个公式，公式里可以有整数，加减乘除和左右括号，返回公式的计算结果（注意：题目中所有运算都是整型运算，向下取整,且保证数据合法，不会出现除0等情况）。

    public static int getValue(String str) {
        return value(str.toCharArray(), 0)[0];
    }

    public static int[] value(char[] str, int i) {
        LinkedList<String> que = new LinkedList<>();
        int pre = 0;
        int[] bra = null;
        while (i < str.length && str[i] != ')') {
            if (str[i] >= '0' && str[i] <= '9') {
                pre = pre * 10 + str[i++] - '0';
            } else if (str[i] != '(') {
                // 遇到計算符號就先進行que裡面的計算
                // 這時候pre會是目前準備要進入que的數字
                // 也就是說que尾部目前只可能是空或是先前放入的計算符號
                addNum(que, pre);

                // 把計算結果加回que尾部
                que.addLast(String.valueOf(str[i++]));
                pre = 0;
            } else {
                // 遇到'('進入遞歸，指針跳過i位置的'('符號
                bra = value(str, i + 1);
                pre = bra[0];

                // 遞歸結束後會是在')'位置，指針i跳過符號進入下一位
                i = bra[1] + 1;
            }
        }
        addNum(que, pre); // 前面處理完後，que裡面只有加減運算
        return new int[]{getNum(que), i};
    }

    public static void addNum(LinkedList<String> que, int num) {
        if (!que.isEmpty()) {
            int cur = 0;
            String top = que.pollLast();
            if (top.equals("+") || top.equals("-")) {
                // 先不處理加減運算，直接把符號放回隊尾
                que.addLast(top);
            } else {
                // 栈顶为乘除运算，先计算再压栈
                cur = Integer.valueOf(que.pollLast());
                num = top.equals("*") ? (cur * num) : (cur / num);
            }
        }
        que.addLast(String.valueOf(num));
    }

    public static int getNum(LinkedList<String> que) {
        int res = 0;
        boolean add = true;
        String cur = null;
        int num = 0;
        if (!que.isEmpty()) {
            cur = que.pollFirst();
            if (cur.equals("+")) {
                add = true;
            } else if (cur.equals("-")) {
                add = false;
            } else {
                num = Integer.valueOf(cur);
                res += add ? num : (-num);
            }
        }
        return res;
    }


    public static void main(String[] args) {
        String exp = "48*((70-65)-43)+8*1";
        System.out.println(getValue(exp));

        exp = "4*(6+78)+53-9/2+45*8";
        System.out.println(getValue(exp));

        exp = "10-5*3";
        System.out.println(getValue(exp));

        exp = "-3*4";
        System.out.println(getValue(exp));

        exp = "3+1*4";
        System.out.println(getValue(exp));

    }
}
