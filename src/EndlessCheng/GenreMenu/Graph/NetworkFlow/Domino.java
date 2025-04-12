package EndlessCheng.GenreMenu.Graph.NetworkFlow;

import java.util.*;

public class Domino {

    // 匈牙利算法: https://leetcode.cn/problems/broken-board-dominoes/solutions/1/suan-fa-xiao-ai-cong-ling-dao-yi-jiao-hu-8b4k/
    // https://leetcode.cn/problems/broken-board-dominoes/solutions/975480/er-fen-tu-xiong-ya-li-suan-fa-javaban-be-8mym/
    static int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int domino(int m, int n, int[][] broken) {
        Set<Integer> transBroken = new HashSet<>();
        for (int[] bro : broken) {
            transBroken.add(bro[0] * n + bro[1]);
        }
        Map<Integer, List<Integer>> m1 = new HashMap<>();
        Map<Integer, List<Integer>> m2 = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int key = i * n + j;
                if (transBroken.contains(key)) continue;
                Map<Integer, List<Integer>> mm = (i + j) % 2 == 0 ? m1 : m2;
                mm.put(key, new ArrayList<>());
                for (int[] dir : dirs) {
                    int ni = i + dir[0];
                    int nj = j + dir[1];
                    int nkey = ni * n + nj;
                    if (ni >= 0 && ni < m && nj >= 0 && nj < n && !transBroken.contains(nkey)) {
                        mm.get(key).add(nkey);
                    }
                }
            }
        }
        return Math.max(maxMatch(m1), maxMatch(m2));
    }

    int maxMatch(Map<Integer, List<Integer>> inMap) {
        Map<Integer, Integer> used = new HashMap<>();
        Map<Integer, Integer> matchList = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : inMap.entrySet()) {
            int key = entry.getKey();
            List<Integer> v = entry.getValue();
            Map<Integer, Integer> pathMap = new HashMap<>();
            List<Integer> path = new ArrayList<>();
            if (!v.isEmpty()) {
                matchFun(inMap, key, v, path, pathMap, used, matchList);
            }
        }
        return matchList.size();
    }

    boolean matchFun(Map<Integer, List<Integer>> inMap, int key, List<Integer> v, List<Integer> path, Map<Integer, Integer> pathMap, Map<Integer, Integer> used, Map<Integer, Integer> matchList) {
        boolean isMatch = false;
        for (int i = 0; i < v.size(); i++) {
            int nkey = v.get(i);
            if (!used.containsKey(nkey)) {
                matchList.put(key, nkey);
                used.put(nkey, key);
                for (int j = path.size() - 1; j >= 0; j--) {
                    matchList.put(path.get(j), pathMap.get(path.get(j)));
                    used.put(pathMap.get(path.get(j)), path.get(j));
                }
                isMatch = true;
                break;
            }
        }
        if (!isMatch) {
            if (pathMap.containsKey(key)) return false;
            path.add(key);
            pathMap.put(key, v.get(0));
            int mkey = used.get(v.get(0));
            List<Integer> v2 = inMap.get(mkey);
            int index = v2.indexOf(v.get(0));
            if (index > -1) {
                v2.remove(index);
            }
            if (!v2.isEmpty()) {
                matchFun(inMap, mkey, v2, path, pathMap, used, matchList);
            }
        }
        return true;
    }


}
