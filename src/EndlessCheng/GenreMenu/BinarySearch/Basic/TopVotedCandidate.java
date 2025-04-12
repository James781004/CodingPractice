package EndlessCheng.GenreMenu.BinarySearch.Basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopVotedCandidate {

    // https://leetcode.cn/problems/online-election/solutions/1150963/gong-shui-san-xie-er-fen-yun-yong-ti-by-5y3hi/
    List<int[]> list = new ArrayList<>();

    public TopVotedCandidate(int[] persons, int[] times) {
        int val = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < times.length; i++) {
            map.put(persons[i], map.getOrDefault(persons[i], 0) + 1);

            // 起始時 val=0，當出現票數大於等於 val 時，
            // 往 list 追加二元組記錄 list[idx]=(times[i],persons[i])，並更新 val
            // 每個 list[idx] 代表了當前候選人 list[idx][1] 的首次領先時刻為 list[idx][0]
            if (map.get(persons[i]) >= val) {
                val = map.get(persons[i]);
                list.add(new int[]{times[i], persons[i]});
            }
        }
    }

    // 通過「二分」找到 list 中滿足 list[i][0]<=t 的分割點 r（最大下標），取 list[r][1] 即是答案
    public int q(int t) {
        int l = 0, r = list.size() - 1;
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (list.get(mid)[0] <= t) l = mid;
            else r = mid - 1;
        }
        return list.get(r)[0] <= t ? list.get(r)[1] : 0;
    }


}
