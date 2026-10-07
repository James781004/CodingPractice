package EndlessCheng.GenreMenu.DP.Linear.LIS.Advance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MinOperations {

    // https://leetcode.cn/problems/minimum-operations-to-make-a-subsequence/solutions/896620/python-zui-chang-gong-gong-zi-xu-lie-by-r3yje/
    public int minOperations(int[] target, int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();

        // 數字對應坐標(離散化)
        for (int i = 0; i < target.length; i++)
            map.put(target[i], i);

        // 300.最長上升子序列
        List<Integer> stack = new ArrayList<>();
        
        for (int num : arr) {
            // 轉換坐標
            Integer idx = map.get(num);

            // 只有在target的數字才可能屬於公共子序列
            if (idx != null) {
                int l = 0, r = stack.size();
                while (l < r) {
                    int mid = l + r >> 1;
                    if (stack.get(mid) >= idx) r = mid;
                    else l = mid + 1;
                }

                // 該坐標在當前棧中的位置
                if (l == stack.size()) stack.add(idx);
                else stack.set(l, idx);
            }
        }

        // 最終stack的長度就構成了最長上升子序列的長度，用減法即可得到本題答案
        return target.length - stack.size();
    }


}
