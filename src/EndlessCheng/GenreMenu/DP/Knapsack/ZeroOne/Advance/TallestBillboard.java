package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Advance;

import java.util.HashMap;
import java.util.Map;

public class TallestBillboard {

    // https://leetcode.cn/problems/tallest-billboard/solutions/368725/yi-quan-ji-ben-mei-shuo-ming-bai-de-zhe-pian-kan-l/
    public int tallestBillboard(int[] rods) {
        //key(左右高度差(左側-右側)) ; value (左側高度)
        Map<Integer, Integer> res = new HashMap<>();
        Map<Integer, Integer> copy = new HashMap<>();
        res.put(0, 0);
        for (int h : rods) {
            copy.clear();
            copy.putAll(res);//獲取當前支架之前的所有支架，構成的所有高度差
            for (Integer key : copy.keySet()) {//對之前每種情況進行當前支架的選擇更新
                //將當前支架放在左側
                //（判斷當前高度差是否存在，若存在，更新左側高（取較大值），若不存在，添加新的鍵值對）
                res.put(key + h, Math.max(res.getOrDefault(key + h, 0), copy.get(key) + h));
                //將當前支架放在右側
                //（判斷當前高度差是否存在，若存在，更新左側高（取較大值），若不存在，添加新的鍵值對）
                res.put(key - h, Math.max(res.getOrDefault(key - h, 0), copy.get(key)));
                //放棄當前支架，則res保持不變
            }
        }
        //返回最後高度差為0的左側高度
        return res.get(0);
    }


}
