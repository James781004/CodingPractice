package TeacherZuoCodingInterviewGuide.ch01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Q03_ReverseStack {
    //    描述
//    一个栈依次压入1,2,3,4,5，那么从栈顶到栈底分别为5,4,3,2,1。将这个栈转置后，从栈顶到栈底为1,2,3,4,5，也就是实现栈中元素的逆序，但是只能用递归函数来实现，不能用其他数据结构。
//    输入描述：
//    输入数据第一行一个整数N为栈中元素的个数。
//
//    接下来一行N个整数X_iX
//            i
//​
//    表示一个栈依次压入的每个元素。
//    输出描述：
//    输出一行表示栈中元素逆序后的栈顶到栈底的每个元素
//            示例1
//    输入：
//            5
//            1 2 3 4 5
//    复制
//    输出：
//            1 2 3 4 5


    // 獲取並移除棧底元素
    public static int getAndRemoveLast(Stack<Integer> stack) {
        int res = stack.pop(); // 彈出並保存當前棧頂元素
        if (stack.isEmpty()) {
            return res; // 這個返回位置會是棧底元素
        } else {
            int last = getAndRemoveLast(stack); // 進入遞歸的點，出來後要繼續往下走流程
            stack.push(res); // 回溯流程把原先彈出的元素還原
            return last; // 這個return會從進入遞歸的點出來，然後往下走流程
        }

    }

    // 主函數部分
    // 總之先獲取並移除棧底元素，接著剩餘部分進入遞歸流程
    // 之後把保存好的原棧底元素壓入棧(回溯操作)，就可以完成反轉
    public static void reverse(Stack<Integer> st) {
        if (st.isEmpty()) {
            return; // 棧空了就會從reverse(進入遞歸的點)的部份繼續往下走流程
        }
        int i = getAndRemoveLast(st); // 獲取並移除當前棧底元素
        reverse(st); // 進入遞歸的點
        st.push(i); // 回溯流程把原棧底元素壓入棧頂
    }

    public static void main(String args[]) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Stack<Integer> stack = new Stack<>();
        int n = Integer.parseInt(in.readLine());
        String ins[] = in.readLine().split(" ");

        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(ins[i]);
            stack.push(num);
        }
        reverse(stack);

        for (int i = 0; i < n; i++) {
            System.out.print(stack.pop() + " ");
        }
        in.close();
    }
}
