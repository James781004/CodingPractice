package EndlessCheng.GenreMenu.Backtracking.NormalTree.Other;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CheckWays {

    // https://leetcode.cn/problems/number-of-ways-to-reconstruct-a-tree/solutions/1270933/tong-ge-lai-shua-ti-la-mian-xiang-ti-shi-hthq/
    public int checkWays(int[][] pairs) {
        // 1. 根節點肯定是跟所有節點都有關聯的
        // 2. 一個節點相關的pair數量肯定小於或等於其父節點相關的pair數量，且是其父的相關pair的子集
        // 3. 如果是相等的話，肯定不止一種構造樹的方式，因為他們可以交換一下位置
        // 4. 我們需要遍歷所有的節點看它是否都滿足上面的條件2和條件3
        // 開始擼代碼~~

        // 使用Map存儲每個節點的對應關系
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int[] pair : pairs) {
            map.computeIfAbsent(pair[0], k -> new HashSet<>()).add(pair[1]);
            map.computeIfAbsent(pair[1], k -> new HashSet<>()).add(pair[0]);
        }

        // 找到任意一個根節點
        Integer root = findRoot(map);
        if (root == null) {
            return 0;
        }

        // 遍歷所有節點（除根節點），看是否滿足條件2和條件3
        int ans = 1;
        for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
            int node = entry.getKey();
            // 根節點是沒有父節點的，所以不用參與計算
            if (node == root) {
                continue;
            }

            // 尋找父節點
            Integer parent = findParent(map, node);
            if (parent == null) {
                return 0;
            }

            // 檢查當前節點的pair是否為父節點的pair的子集
            Set<Integer> parentSet = map.get(parent);
            Set<Integer> currentSet = entry.getValue();
            if (!containsAll(parent, parentSet, currentSet)) {
                return 0;
            }

            // 如果與父節點的pair數量相等，說明可交換，那就不止一棵樹了
            if (currentSet.size() == parentSet.size()) {
                ans = 2;
            }
        }

        return ans;
    }

    private Integer findRoot(Map<Integer, Set<Integer>> map) {
        // 總節點數
        int size = map.size();
        for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
            if (entry.getValue().size() == size - 1) {
                return entry.getKey();
            }
        }
        return null;
    }

    private Integer findParent(Map<Integer, Set<Integer>> map, int node) {
        int currentSize = map.get(node).size();
        Integer parent = null;
        int parentSize = Integer.MAX_VALUE;
        for (int related : map.get(node)) {
            int relatedSize = map.get(related).size();
            // 關聯數 “略大於” 當前節點的節點才是父節點，其他的是祖先節點
            if (node != related && relatedSize >= currentSize && relatedSize < parentSize) {
                parent = related;
                parentSize = relatedSize;
            }
        }
        return parent;
    }

    private boolean containsAll(int parent, Set<Integer> parentSet, Set<Integer> currentSet) {
        for (int current : currentSet) {
            if (current != parent && !parentSet.contains(current)) {
                return false;
            }
        }
        return true;
    }


}
