package TeacherZuoCodingInterviewGuide.ch01;

import java.util.Stack;

public class Q09_MaximalRectangle {
    //    描述
//    给定一个整型矩阵 map，其中的值只有 0 和 1 两种，求其中全是 1 的所有矩形区域中，最大的矩形区域里 1 的数量。
//    输入描述：
//    第一行输入两个整数 n 和 m，代表 n*m 的矩阵
//    接下来输入一个 n*m 的矩阵
//    输出描述：
//    输出其中全是 1 的所有矩形区域中，最大的矩形区域里 1 的数量。
//    示例1
//    输入：
//            1 4
//            1 1 1 0 d
//    输出： 3
//    说明：
//    最大的矩形区域有3个1，所以返回3
//    备注： 1 <= n.m <= 2000


    public static int maxRecSize(int[][] map) {
        if (map == null || map.length == 0 || map[0].length == 0) return 0;
        int maxArea = 0;
        int[] height = new int[map[0].length];

        // 統計從第i行開始每列往上(定義成往下也可以)有多少個"連續的1"
        // 統計結果放入height裡面，類似於一個壓縮Matrix成為Array的流程
        // 所以如果遇到map[i][j]為0，height[j]位置就必須歸零
        // 如果map[i][j]為1，height[j]位置就是原本height[j]值加上1
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                height[j] = map[i][j] == 0 ? 0 : height[j] + 1;
            }
            maxArea = Math.max(maxRecFromBottom(height), maxArea);
        }

        return maxArea;
    }

    /**
     * 相當於尋找height中每個元素左邊和右邊的第一個比它小的元素(單調棧)，時間覆雜度O(M)
     */
    private static int maxRecFromBottom(int[] height) {
        // 排除兩種特例：null 空數組[]
        if (height == null || height.length < 1) {
            return -1;
        }
        // 單調棧初始化，依序存放height中對應的位置
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        int cur;
        int leftLessIndex;
        int maxArea = 0;
        int curArea;
        while (i < height.length) {
            if (stack.isEmpty() || height[i] > height[stack.peek()]) {
                // 滿足從棧頂到棧底單調遞減時，壓入
                stack.push(i);
                i++;
            } else {
                // 不滿足棧頂到棧底單調遞減時，彈出
                cur = stack.pop();
                leftLessIndex = stack.isEmpty() ? -1 : stack.peek();

                // 計算當前列及其左邊最大子矩陣的大小
                // 設定height[cur]為高，長就是當前下標i與leftLessIndex的距離(即i - leftLessIndex - 1)
                // 注意此流程結束後i不一定就會入棧!!!!
                // 如果下個loop依然不滿足棧頂到棧底單調遞減時，i就停留在原位置不動，只變動cur位置
                // 然後繼續計算矩陣大小，長度依然是i與leftLessIndex的距離，height[cur](cur位置已變動)為高
                curArea = (i - leftLessIndex - 1) * height[cur];
                maxArea = Math.max(curArea, maxArea); // 記錄下矩陣大小(即最大面積)
            }
        }
        // 清算棧中剩余的元素，這些元素右邊已經沒有比它小的數字
        while (!stack.isEmpty()) {
            cur = stack.pop();
            leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
            // 計算當前列及其左邊最大子矩陣的大小
            curArea = (i - leftLessIndex - 1) * height[cur];
            maxArea = Math.max(curArea, maxArea);
        }
        return maxArea;
    }

    public static void main(String[] args) {
        int[][] map = {{1, 0, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 0},};
        System.out.println(maxRecSize(map));
    }
}
