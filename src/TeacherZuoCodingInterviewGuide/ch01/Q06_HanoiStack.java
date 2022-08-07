package TeacherZuoCodingInterviewGuide.ch01;

import java.util.Stack;

public class Q06_HanoiStack {
    /**
     * 用棧來求解漢諾塔問題：HanoiStack
     * <p>
     * 【問題描述】：將漢諾塔遊戲（小壓大）規則修改，不能從左（右）側的塔直接移到右（左）側，而是必須經過中間塔。
     * <p>
     * 求當塔有N層時，打印最優移動過程和最優移動步數。如N=2，記上層塔為1，下層為2.則打印：1：left->mid;1
     * <p>
     * 由於必須經過中間，實際動作只有4個：左L->中M，中->左，中->右R，右->中。
     * <p>
     * 原則：①小壓大；②相鄰不可逆（上一步是L->M,下一步絕不能是M->L）
     * <p>
     * 非遞歸方法核心結論：1.第一步一定是L-M；2.為了走出最少步數，四個動作只可能有一個不違反上述兩項原則。
     * <p>
     * 核心結論2證明：假設前一步是L->M（其他3種情況略）
     * <p>
     * a.根據原則①，L->M不可能發生；b.根據原則②，M->L不可能；c.根據原則①，M->R或R->M僅一個達標。
     * <p>
     * So，每走一步只需考察四個步驟中哪個步驟達標，依次執行即可。
     *
     * @author xiaofan
     */


    // 使用遞歸方法
    public static int hanoiProblem1(int num, String left, String mid,
                                    String right) {
        if (num < 1) {
            return 0;
        }
        return process(num, left, mid, right, left, right);
    }

    public static int process(int num, String left, String mid, String right,
                              String from, String to) {
        if (num == 1) { // 邊界條件：最後一個的狀況
            if (from.equals(mid) || to.equals(mid)) {
                System.out.println("Move 1 from " + from + " to " + to);
                return 1;
            } else { // from == left, to == right
                System.out.println("Move 1 from " + from + " to " + mid);
                System.out.println("Move 1 from " + mid + " to " + to);
                return 2;
            }
        }
        if (from.equals(mid) || to.equals(mid)) { // 接下來要移到中間，或者移出中間的狀況
            // 流程為：from --> another --> to
            // part1先把剩下num - 1個 移動：from --> another (進入遞歸)
            // part2剩下最底下1個 移動：from --> to (只有一種可能)
            // part3再把剛才移動過的num - 1個 移動：another --> to (進入遞歸)
            // 把結果數相加即可
            String another = (from.equals(left) || to.equals(left)) ? right : left;
            int part1 = process(num - 1, left, mid, right, from, another);
            int part2 = 1;
            System.out.println("Move " + num + " from " + from + " to " + to);
            int part3 = process(num - 1, left, mid, right, another, to);
            return part1 + part2 + part3;
        } else {
            // 目標是左到右或者右到左的流程
            // part1先把剩下num - 1個 移動：from --> to (進入遞歸)
            // part2剩下最底下1個 移動：from --> mid (只有一種可能)
            // part3再把剛才移動過的num - 1個 移動：to --> from (進入遞歸)
            // part4剛才移動的最底下1個 移動：mid --> to (只有一種可能)
            // part5最後再把剛才移動過的num - 1個 移動：from --> to (進入遞歸)
            int part1 = process(num - 1, left, mid, right, from, to);
            int part2 = 1;
            System.out.println("Move " + num + " from " + from + " to " + mid);
            int part3 = process(num - 1, left, mid, right, to, from);
            int part4 = 1;
            System.out.println("Move " + num + " from " + mid + " to " + to);
            int part5 = process(num - 1, left, mid, right, from, to);
            return part1 + part2 + part3 + part4 + part5;
        }
    }

    // 由於必須經過中間，實際動作只有4個：左L->中M，中->左，中->右R，右->中。
    public static enum Action {
        No, LToM, MToL, MToR, RToM
    }

    public static int hanoiProblem2(int num, String left, String mid, String right) {
        Stack<Integer> leftStack = new Stack<Integer>();
        Stack<Integer> midStack = new Stack<Integer>();
        Stack<Integer> rightStack = new Stack<Integer>();
        leftStack.push(Integer.MAX_VALUE);  // 為了避免棧空無法比較，初始化時棧底先壓入MAX_VALUE
        midStack.push(Integer.MAX_VALUE);
        rightStack.push(Integer.MAX_VALUE);
        for (int i = num; i > 0; i--) {
            leftStack.push(i); // num逆序壓入leftStack完成初始化動作
        }
        Action[] record = {Action.No}; // 第一步之前沒動作，先記錄Action.No
        int step = 0;

        // 設定好規則和流程，交給while loop自動完成
        // 原則：①小壓大；②相鄰不可逆（上一步是L->M,下一步絕不能是M->L）
        // 非遞歸方法核心結論：1.第一步一定是L-M；2.為了走出最少步數，四個動作只可能有一個不違反上述兩項原則。
        // 核心結論2證明：假設前一步是L->M（其他3種情況略）
        // a.根據原則①，L->M不可能發生；b.根據原則②，M->L不可能；c.根據原則①，M->R或R->M僅一個達標。
        // 所以每走一步只需考察四個步驟中哪個步驟達標，依次執行即可。
        while (rightStack.size() != num + 1) { // n+1,因為rS.push(Integer.MAX_VALUE);等於n+1說明全部移動完成
            step += fStackTotStack(record, Action.MToL, Action.LToM, leftStack, midStack, left, mid); // 第一步一定是LToM
            step += fStackTotStack(record, Action.LToM, Action.MToL, midStack, leftStack, mid, left); // 只可能有這4種操作
            step += fStackTotStack(record, Action.RToM, Action.MToR, midStack, rightStack, mid, right);
            step += fStackTotStack(record, Action.MToR, Action.RToM, rightStack, midStack, right, mid);
        }
        return step;
    }

    /**
     * 實施移動操作.
     *
     * @param preNoAct  不能這樣移動
     * @param nowAct    即將執行的操作
     * @param fromStack 起始棧
     * @param toStack   目標棧
     * @return step(成功與否)
     */
    public static int fStackTotStack(Action[] record, Action preNoAct,
                                     Action nowAct, Stack<Integer> fromStack, Stack<Integer> toStack,
                                     String from, String to) {
        if (record[0] != preNoAct && fromStack.peek() < toStack.peek()) {
            toStack.push(fromStack.pop()); // 執行移動操作
            System.out.println("Move " + toStack.peek() + " from " + from + " to " + to);
            record[0] = nowAct; // 更新“上一步動作”
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        int num = 4;

        // solution 1
        int steps1 = hanoiProblem1(num, "left", "mid", "right");
        System.out.println("It will move " + steps1 + " steps.");
        System.out.println("===================================");

        // solution 2
        int steps2 = hanoiProblem2(num, "left", "mid", "right");
        System.out.println("It will move " + steps2 + " steps.");
        System.out.println("===================================");

    }

}