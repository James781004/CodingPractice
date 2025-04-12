package EndlessCheng.GenreMenu.Graph.TopologicalSorting;

import java.util.*;

public class AlienOrder {

    // https://leetcode.cn/problems/Jf1JuT/solutions/1528116/wai-by-jiang-hui-4-662z/
    /**
     * 是否存在不合法
     */
    private boolean valid = true;

    /**
     * 存放每個點的入度
     */
    private int[] inDegrees = new int[26];


    public String alienOrder(String[] words) {
        // 構建圖
        Map<Character, List<Character>> edges = buildGraph(words);
        // 在構建圖中存在不合法情況
        if (!valid) {
            return "";
        }
        return bfs(edges);
    }

    /**
     * 構建圖
     *
     * @param words 單詞列表
     */
    private Map<Character, List<Character>> buildGraph(String[] words) {
        Map<Character, List<Character>> edges = new HashMap<>();
        //存放所有的點
        for (String word : words) {
            for (int j = 0; j < word.length(); j++) {
                edges.putIfAbsent(word.charAt(j), new ArrayList<>());
            }
        }
        // 存放所有的邊
        for (int i = 0; i < words.length - 1; i++) {
            String pre = words[i];
            String cur = words[i + 1];
            int minLength = Math.min(pre.length(), cur.length());
            int j = 0;
            while (j < minLength) {
                if (pre.charAt(j) != cur.charAt(j)) {
                    // 添加邊
                    edges.get(pre.charAt(j)).add(cur.charAt(j));
                    // 入度+1
                    inDegrees[cur.charAt(j) - 'a']++;
                    break;
                }
                j++;
            }
            // 存在不合法的情況: 前一個字符串長度大於當前字符串長度，但前minLength均相等
            if (j == minLength && pre.length() > cur.length()) {
                valid = false;
            }
        }
        return edges;
    }

    /**
     * 廣度優先遍歷
     *
     * @param edges 當前圖
     * @return 字符串
     */
    private String bfs(Map<Character, List<Character>> edges) {
        StringBuilder ans = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        for (Character point : edges.keySet()) {
            // 結果集入度為0
            if (inDegrees[point - 'a'] == 0) {
                queue.offer(point);
            }
        }
        // 隊列中是否存在入度為0的點
        while (!queue.isEmpty()) {
            char curPoint = queue.poll();
            ans.append(curPoint);
            List<Character> nextPoints = edges.get(curPoint);
            for (Character nextPoint : nextPoints) {
                // 入度-1
                inDegrees[nextPoint - 'a']--;
                // 判斷是否入度為0 為0加入隊列
                if (inDegrees[nextPoint - 'a'] == 0) {
                    queue.offer(nextPoint);
                }
            }
        }
        // 是否所有的點都加入了結果集，如果不是則存在環
        return ans.length() == edges.size() ? ans.toString() : "";
    }


}
