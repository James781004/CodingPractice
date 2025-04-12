package EndlessCheng.GenreMenu.Graph.TopologicalSorting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SortItems {

    // https://leetcode.cn/problems/sort-items-by-groups-respecting-dependencies/solutions/557292/tuo-bu-pai-xu-liang-ceng-grouphua-fen-yi-offg/
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        List<List<Integer>> groupItems = new ArrayList<>(); // 每個group下的項目成員
        List<Integer> groupIdList = new ArrayList<>(); // group id 列表
        List<List<Integer>> groupEdges = new ArrayList<>(); // group與group之間的連接邊

        // 此處為何是 m+n 呢？因為假設每個項目都無現有的group接管，則可直接延續m,創n個group id下標
        // 各個數據初始化
        for (int i = 0; i < n + m; i++) {
            groupItems.add(new ArrayList<>());
            groupIdList.add(i);
            groupEdges.add(new ArrayList<>());
        }

        // 項目與項目之間的連接邊
        List<List<Integer>> itemEdges = new ArrayList<>();
        int lastGroup = m;
        for (int j = 0; j < n; j++) {
            itemEdges.add(new ArrayList<>());

            if (group[j] == -1) {
                group[j] = lastGroup;
                lastGroup++;
            }

            //把每個group下的項目放在一個list裡
            groupItems.get(group[j]).add(j);
        }

        int[] itemDegree = new int[n];
        int[] groupDegree = new int[m + n];
        //建立圖關系
        for (int k = 0; k < beforeItems.size(); k++) {
            int curGroup = group[k];
            for (int r = 0; r < beforeItems.get(k).size(); r++) {
                //為同一group內的項目則刷新項目入度數，建立被依賴關系，一個下標對著一個list，整個list依賴此下標項目
                int data = beforeItems.get(k).get(r);
                if (group[data] == curGroup) {
                    itemDegree[k]++;
                    itemEdges.get(data).add(k);
                } else {// 不為同一個group，則建立group 入度數和被依賴關系
                    groupDegree[curGroup]++;
                    groupEdges.get(group[data]).add(curGroup);
                }
            }
        }

        //獲取 group之間的拓撲排序
        List<Integer> outsideSort = toSort(groupDegree, groupEdges, groupIdList);
        if (outsideSort.size() == 0) { //有循環，則返回空
            return new int[0];
        }

        List<Integer> ans = new ArrayList<>();
        for (Integer finGroup : outsideSort) {
            if (groupItems.get(finGroup).size() == 0) {
                continue;
            }
            List<Integer> insideSort = toSort(itemDegree, itemEdges, groupItems.get(finGroup));
            if (insideSort.size() == 0) {
                return new int[0];
            }

            ans.addAll(insideSort);
        }

        return ans.stream().mapToInt(Integer::valueOf).toArray();
    }

    public List<Integer> toSort(int[] degree, List<List<Integer>> edges, List<Integer> points) {
        List<Integer> res = new ArrayList<>();
        Queue<Integer> que = new LinkedList<>();
        for (Integer item : points) {
            if (degree[item] == 0) {
                que.offer(item);
            }
        }

        while (!que.isEmpty()) {
            Integer temp = que.poll();
            List<Integer> list = edges.get(temp);
            for (Integer ed : list) {
                degree[ed]--;
                if (degree[ed] == 0) {
                    que.offer(ed);
                }
            }
            res.add(temp);
        }

        //若有循環，則輸出個數肯定會少於頂點個數
        return res.size() == points.size() ? res : new ArrayList<>();
    }


}
