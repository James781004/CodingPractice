package 程序员代码面试指南.ch01;

import java.util.Scanner;
import java.util.Stack;

public class Q02_TwoStackQueue {
//    描述
//    用两个栈实现队列，支持队列的基本操作。
//    输入描述：
//    第一行输入一个整数N，表示对队列进行的操作总数。
//
//    下面N行每行输入一个字符串S，表示操作的种类。
//
//    如果S为"add"，则后面还有一个整数X表示向队列尾部加入整数X。
//
//    如果S为"poll"，则表示弹出队列头部操作。
//
//    如果S为"peek"，则表示询问当前队列中头部元素是多少。
//    输出描述：
//    对于每一个为"peek"的操作，输出一行表示当前队列中头部元素是多少。
//    示例1
//    输入：
//            6
//    add 1
//    add 2
//    add 3
//    peek
//            poll
//    peek
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

    static class MyQueue {
        private final Stack<Integer> dataStack; // 資料輸入用的Stack
        private final Stack<Integer> outputStack; // 資料輸出用的Stack

        public MyQueue() {
            this.dataStack = new Stack<Integer>();
            this.outputStack = new Stack<Integer>();
        }

        public void add(int n) {
            this.dataStack.push(n);
        }

        public int poll() {
            if (this.outputStack.isEmpty()) { // 進行查詢或彈出操作時，如果outputStack是空的，就把dataStack裡面資料全倒進來
                while (!this.dataStack.isEmpty()) {
                    this.outputStack.push(this.dataStack.pop());
                }
            }

            return this.outputStack.pop();
        }

        public int peek() {
            if (this.outputStack.isEmpty()) { // 進行查詢或彈出操作時，如果outputStack是空的，就把dataStack裡面資料全倒進來
                while (!this.dataStack.isEmpty()) {
                    this.outputStack.push(this.dataStack.pop());
                }
            }

            return this.outputStack.peek();
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        MyQueue queue = new MyQueue();

        int t = scan.nextInt();
        for (int i = 0; i < t; i++) {
            String op = scan.next();
            if (op.equals("add")) {
                int x = scan.nextInt();
                queue.add(x);
            } else if (op.equals("peek")) {
                System.out.println(queue.peek());
            } else if (op.equals("poll")) {
                queue.poll();
            }
        }
    }
}
