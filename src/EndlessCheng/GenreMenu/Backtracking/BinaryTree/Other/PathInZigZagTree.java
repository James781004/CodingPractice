package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathInZigZagTree {

    // https://leetcode.cn/problems/path-in-zigzag-labelled-binary-tree/solutions/901472/er-cha-shu-xun-lu-by-leetcode-solution-ryx0/
    public List<Integer> pathInZigZagTree(int n) {
        // 計算 n 所在層級
        int level = 1;
        while (getEnd(level) < n) level++;

        int[] ans = new int[level];
        int idx = level - 1, cur = n;
        while (idx >= 0) {
            ans[idx--] = cur;
            int tot = (int) Math.pow(2, level - 1);
            int start = getStart(level), end = getEnd(level);
            if (level % 2 == 0) {
                // 當前層為偶數層，則當前層節點「從右往左」數值遞增，相應計算上一層下標也應該「從右往左」
                int j = tot / 2;
                for (int i = start; i <= end; i += 2, j--) {
                    if (i == cur || (i + 1) == cur) break;
                }
                int prevStart = getStart(level - 1);
                while (j-- > 1) prevStart++;
                cur = prevStart;
            } else {
                // 當前層為奇數層，則當前層節點「從左往右」數值遞增，相應計算上一層下標也應該「從左往右」
                int j = 1;
                for (int i = start; i <= end; i += 2, j++) {
                    if (i == cur || (i + 1) == cur) break;
                }
                int prevEnd = getEnd(level - 1);
                while (j-- > 1) prevEnd--;
                cur = prevEnd;
            }
            level--;
        }
        List<Integer> list = new ArrayList<>();
        for (int i : ans) list.add(i);
        return list;
    }

    // 第 level 層的起始節點值
    int getStart(int level) {
        return (int) Math.pow(2, level - 1);
    }

    // 第 level 層的結束節點值
    int getEnd(int level) {
        int a = getStart(level);
        return a + a - 1;
    }


    // https://leetcode.cn/problems/path-in-zigzag-labelled-binary-tree/solutions/96636/jian-dan-yi-dong-de-gong-shi-shi-jian-guo-100-by-a/
    // 數學
    public List<Integer> pathInZigZagTreeMath(int label) {
        List<Integer> integers = new ArrayList<>();//0.初始化存放結果的變量
        var a = (int) (Math.log(label) / Math.log(2));//2.計算label所在的層
        while (label > 1) {//5.循環直到遇到特殊情況1
            integers.add(label);//3.將label的結果添加到數組中
            label = (int) (3 * Math.pow(2, --a) - label / 2 - 1);//4.計算下一個label的值
        }
        integers.add(1);//6.添加特殊情況 1
        Collections.reverse(integers); //7.翻轉數組
        return integers;//1.返回結果
    }


}
