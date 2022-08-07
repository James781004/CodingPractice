package PointToOffer.TwoPointers;

import java.util.ArrayList;
import java.util.List;

public class Q57_2_FindContinuousSequence {
    public List<List<Integer>> FindContinuousSequence(int sum) {
        List<List<Integer>> res = new ArrayList<>();
        if (sum < 3) {
            return res;
        }
        // 两个起点，相当于动态窗口的两边，根据其窗口内的值的和来确定窗口的位置和大小
        int low = 1, high = 2;
        while (high > low) {
            //由于是连续的，差为1的一个序列，那么求和公式是(a0+an)*n/2
            int cur = (high + low) * (high - low + 1) / 2;
            //相等，那么就将窗口范围的所有数添加进结果集
            if (cur == sum) {
                List<Integer> list = new ArrayList<>();
                for (int i = low; i <= high; i++) {
                    list.add(i);
                }
                res.add(list);
                low++;
                //如果当前窗口内的值之和小于sum，那么右边窗口右移一下
            } else if (cur < sum) {
                high++;
            } else {
                //如果当前窗口内的值之和大于sum，那么左边窗口右移一下
                low++;
            }
        }
        return res;
    }
}
