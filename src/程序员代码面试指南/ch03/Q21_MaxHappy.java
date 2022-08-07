package 程序员代码面试指南.ch03;

import java.util.ArrayList;
import java.util.List;

public class Q21_MaxHappy {
//      派對的最大快樂值
//    描述
//    整個公司的人員結構可以看作是一棵標準的多叉樹。
//    樹的頭節點是公司唯一的老板，除老板外，每個員工都有唯一的直接上級，葉節點是沒有任何下屬的基層員工，
//    除基層員工外，每個員工都有一個或多個直接下級，另外每個員工都有一個快樂值。
//    這個公司現在要辦 party，你可以決定哪些員工來，哪些員工不來。但是要遵循如下的原則：
//            1.如果某個員工來了，那麽這個員工的所有直接下級都不能來。
//            2.派對的整體快樂值是所有到場員工快樂值的累加。
//            3.你的目標是讓派對的整體快樂值盡量大。
//    給定一棵多叉樹，請輸出派對的最大快樂值。

    public static class Employee {
        public int happy;
        List<Employee> subordinates;

        public Employee(int happy) {
            this.happy = happy;
            subordinates = new ArrayList<>();
        }
    }

    // 每棵樹處理完之後的返回值類型
    public static class ReturnData {
        public int yesHeadMax; // 樹的頭節點來的情況下，整棵樹的最大快樂值
        public int noHeadMax; // 樹的頭節點不來的情況下，整棵樹的最大快樂值

        public ReturnData(int yesHeadMax, int noHeadMax) {
            this.yesHeadMax = yesHeadMax;
            this.noHeadMax = noHeadMax;
        }
    }

    // 該函數處理以X為頭的樹，並且返回X來和不來兩種情況下的兩個最大快樂值
    // 所以返回值的類型為ReturnData類型
    public static ReturnData process(Employee X) {
        int yesX = X.happy;// X來的情況下，一定要累加上X自己的快樂值
        int noX = 0;// X不來的情況下，不累加上X自己的快樂值
        if (X.subordinates.isEmpty()) { // 如果X沒有直接下屬，說明是基層員工，直接返回即可
            return new ReturnData(yesX, noX);
        } else { // 如果X有直接下屬，就按照題目的分析來
            // 枚舉X的每一個直接下級員工next
            for (Employee next : X.subordinates) {

                // 遞歸調用process，得到以next為頭的子樹，在next來和不來兩個情況下，分別的最大收益
                ReturnData subTreeInfo = process(next);

                // X來了，X的直接下屬不能來
                // 所以yesX加上X直接下屬不來的總和noHeadMax
                yesX += subTreeInfo.noHeadMax;

                // X不來，X的直接下屬來不來都可以，
                // 所以noX加上yesHeadMax和noHeadMax較大的那一方
                noX += Math.max(subTreeInfo.yesHeadMax, subTreeInfo.noHeadMax);
            }
            return new ReturnData(yesX, noX);
        }
    }

    public static int getMaxHappy(Employee boss) {
        ReturnData allTreeInfo = process(boss);
        return Math.max(allTreeInfo.noHeadMax, allTreeInfo.yesHeadMax);
    }
}
