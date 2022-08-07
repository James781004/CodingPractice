package 程序员代码面试指南.ch01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Q05_SortStack {
//    描述
//    一个栈中元素的类型为整型，现在想将该栈从顶到底按从大到小的顺序排序，只许申请一个栈。
//    除此之外，可以申请新的变量，但不能申请额外的数据结构。如何完成排序？
//    输入描述：
//    第一行输入一个N，表示栈中元素的个数
//            第二行输入N个整数a_i
//    表示栈顶到栈底的各个元素
//    输出描述：
//    输出一行表示排序后的栈中栈顶到栈底的各个元素。
//    示例1
//    输入：
//            5
//            5 8 4 3 6
//    输出：
//            8 6 5 4 3
//    备注：
//            1 <= N <= 10000
//            -1000000 <= a_n <= 1000000


    public static void main(String[] args) throws IOException {
        Stack<Integer> stack = new Stack<>();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int row = Integer.parseInt(in.readLine());
        String[] item = in.readLine().split(" ");
        for (int i = 0; i < row; i++) {
            stack.push(Integer.parseInt(item[i]));
        }

        stack = SortStackByStack.stackSort(stack);

        StringBuilder sb = new StringBuilder();
        while (!stack.empty()) {
            sb.append(stack.pop()).append(" ");
        }
        System.out.println(sb.substring(0, sb.length() - 1));
    }
}


class SortStackByStack {


    /**
     * @param stack 无序的栈
     * @return 有序的栈（从栈顶到栈底，由大到小排序)
     */
    public static Stack<Integer> stackSort(Stack<Integer> stack) {
        Stack<Integer> help = new Stack<>(); // 輔助棧
        while (!stack.isEmpty()) {
            Integer curr = stack.pop(); // 當前原棧的棧頂元素

            // 技巧：原本題目給的結構在算法裡也可以成為輔助結構
            // 比較小元素的要先進入help
            // 如果當前help棧頂大於原棧的棧頂元素
            // 就把當前help棧頂彈出並放進原棧棧頂，直到help棧頂小於等於curr
            // help先前彈出的元素已經進入原棧，在接下來的while循環會再次加入
            while (!help.isEmpty() && help.peek() > curr) {
                stack.push(help.pop());
            }
            help.push(curr);
        }
        return help;
    }


    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(3);
        stack.push(4);
        stack.push(2);
        Stack<Integer> result = SortStackByStack.stackSort(stack);
        while (!result.isEmpty()) {
            System.out.println(result.pop());
        }
    }

}
