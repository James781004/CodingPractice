package TeacherZuoCodingInterviewGuide.ch01;

import java.util.Scanner;
import java.util.Stack;

public class Q01_DesignGetMinStack {
//    描述
//    实现一个特殊功能的栈，在实现栈的基本功能的基础上，再实现返回栈中最小元素的操作。
//    输入描述：
//    第一行输入一个整数N，表示对栈进行的操作总数。
//
//    下面N行每行输入一个字符串S，表示操作的种类。
//
//    如果S为"push"，则后面还有一个整数X表示向栈里压入整数X。
//
//    如果S为"pop"，则表示弹出栈顶操作。
//
//    如果S为"getMin"，则表示询问当前栈中的最小元素是多少。
//    输出描述：
//    对于每个getMin操作，输出一行表示当前栈中的最小元素是多少。
//    示例1
//    输入：
//            6
//    push 3
//    push 2
//    push 1
//    getMin
//            pop
//    getMin
//            复制
//    输出：
//            1
//            2
//    复制
//    备注：
//            1<=N<=1000000
//
//            -1000000<=X<=1000000
//
//    数据保证没有不合法的操作

    static class MyStack1 {
        private Stack<Integer> stackData; // 存放當前值
        private Stack<Integer> stackMin; // 存放最小值

        public MyStack1() {
            this.stackData = new Stack<Integer>();
            this.stackMin = new Stack<Integer>();
        }

        public int getMin() {
            if (this.stackMin.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return this.stackMin.peek(); // stackMin的頭永遠是最小值
        }

        public int pop() {
            if (this.stackData.isEmpty()) {
                throw new RuntimeException("Your stack is empty");
            }
            this.stackMin.pop();
            return this.stackData.pop(); // 兩個stack同步彈出頭
        }

        // stackData加入新值的同時，stackMin也要新增一次最小值，重複壓入同值也無所謂
        // 這樣stackMin中的最小值(getMin)最底位置就會和stackData相同值處於同位置
        // 當兩個stack同步彈出相同值，最小值(getMin)就會發生改變
        public void push(int num) {
            if (this.stackMin.isEmpty()) {
                this.stackMin.push(num);
            } else if (num <= this.getMin()) {
                this.stackMin.push(num);
            } else {
                int addNum = this.getMin();
                this.stackMin.push(addNum);
            }

            this.stackData.push(num);
        }
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        MyStack1 stack1 = new MyStack1();
        int t = scan.nextInt();
        for (int i = 0; i < t; i++) {
            String op = scan.next();
            if (op.equals("push")) {
                int x = scan.nextInt();
                stack1.push(x);
            } else if (op.equals("getMin")) {
                System.out.println(stack1.getMin());
            } else if (op.equals("pop")) {
                stack1.pop();
            }
        }
    }
}
