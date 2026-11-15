package EndlessCheng.GenreMenu.DataStructure.EnumerateDiagonal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FindDiagonalOrderII {

    // https://leetcode.cn/problems/diagonal-traverse-ii/solutions/220288/treemapan-dui-jiao-xian-ju-he-zhi-by-zuo-zhou-ren/
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        int len = 0;
        Map<Integer, List<Integer>> map = new TreeMap<>();
        for (int i = 0; i < nums.size(); i++) {
            len += nums.get(i).size(); // 獲取最後要返回的數組的長度，即元素個數
            for (int j = 0; j < nums.get(i).size(); j++) {
                if (map.containsKey(i + j)) {
                    map.get(i + j).add(nums.get(i).get(j));
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums.get(i).get(j));
                    map.put(i + j, list);
                }
            }
        }
        int[] ans = new int[len];
        int index = 0;
        for (int key : map.keySet()) { // 遍歷map
            List<Integer> list = map.get(key);
            for (int j = list.size() - 1; j >= 0; j--) { // 根據題目的輸出要求確定生成數組中元素的順序
                ans[index] = list.get(j);
                index++;
            }
        }
        return ans;
    }

}
