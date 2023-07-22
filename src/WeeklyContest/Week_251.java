package WeeklyContest;

import java.util.*;

class Week_251 {
    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1945.Sum%20of%20Digits%20of%20String%20After%20Convert/README.md
    public int getLucky(String s, int k) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(c - 'a' + 1);
        }
        s = sb.toString();
        while (k-- > 0) {
            int t = 0;
            for (char c : s.toCharArray()) {
                t += c - '0';
            }
            s = String.valueOf(t);
        }
        return Integer.parseInt(s);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1946.Largest%20Number%20After%20Mutating%20Substring/README.md
    public String maximumNumber(String num, int[] change) {
        char[] s = num.toCharArray();
        // 從左到右遍歷字符串 num，找到第一個比 change 中對應數字小的數字，
        // 然後將其替換為 change 中對應的數字，直到遇到比 change 中對應數字大的數字，停止替換。
        for (int i = 0; i < s.length; i++) {
            if (change[s[i] - '0'] > s[i] - '0') {
                for (; i < s.length && s[i] - '0' <= change[s[i] - '0']; i++) {
                    s[i] = (char) (change[s[i] - '0'] + '0');
                }
                break;
            }
        }
        return String.valueOf(s);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1947.Maximum%20Compatibility%20Score%20Sum/README.md
    // 預處理 + 回溯
    // 預處理出每個學生與每個導師的兼容性評分，然後使用回溯的方法枚舉所有的配對方案，求出最大的兼容性評分和。
    private int[][] g;
    private boolean[] vis;
    private int m;
    private int ans;

    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        m = students.length;
        g = new int[m][m]; // g[i][j] 表示學生 i 和導師 j 的兼容性評分
        vis = new boolean[m];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < m; ++j) {
                for (int k = 0; k < students[i].length; ++k) {
                    g[i][j] += students[i][k] == mentors[j][k] ? 1 : 0;
                }
            }
        }
        dfs(0, 0);
        return ans;
    }

    // 固定學生 i，枚舉老師 j，計算評分 t
    private void dfs(int i, int t) {
        if (i == m) {
            ans = Math.max(ans, t);
            return;
        }
        for (int j = 0; j < m; ++j) {
            if (!vis[j]) {
                vis[j] = true;
                dfs(i + 1, t + g[i][j]);
                vis[j] = false;
            }
        }
    }


    // https://leetcode.cn/problems/delete-duplicate-folders-in-system/solution/1948-shan-chu-xi-tong-zhong-de-zhong-fu-ks4b5/
    // 1、先使用TreeMap構造前綴樹；
    // 2、再通過DFS（深度優先遍歷）對前綴樹具有相同子樹的節點（hash判斷是否相同）進行標記；
    // 3、最後遞歸統計結果，去除標記過的節點及其子樹節點；
    public List<List<String>> deleteDuplicateFolder(List<List<String>> paths) {
        // 構建前綴樹
        Node root = new Node();
        for (List<String> path : paths) {
            Node node = root;
            for (String s : path) {
                node = node.nextMap.computeIfAbsent(s, t -> new Node());
            }
        }

        // 對前綴樹的所有節點進行標記，判斷子樹是否相等
        tagDupFolderByDfs(root, new HashMap<>());

        ArrayList<List<String>> resList = new ArrayList<>();
        // 結果入棧
        deleteDuplicateFolder(root, new ArrayList<>(), resList);

        return resList;
    }

    // 對前綴樹的所有節點進行標記，判斷子樹是否相等
    private int tagDupFolderByDfs(Node root, HashMap<Integer, ArrayList<Node>> map) {
        int hash = Objects.hash();
        for (Map.Entry<String, Node> entry : root.nextMap.entrySet()) {
            // 求當前節點的hash值，記錄了當前節點為頭的所有子樹節點的信息
            hash = Objects.hash(hash, entry.getKey(), tagDupFolderByDfs(entry.getValue(), map));
        }

        // 標記hash值相等的節點
        if (!root.nextMap.isEmpty()) {
            for (Node node : map.getOrDefault(hash, new ArrayList<>())) {
                if (root.equals(node)) {
                    root.mark = node.mark = true;
                    break;
                }
            }
        }

        // 將hash值相等的節點存入同一個list中
        map.computeIfAbsent(hash, t -> new ArrayList<>()).add(root);
        return hash;
    }

    // 統計結果，刪除所有標記為true的節點及其子樹
    private void deleteDuplicateFolder(Node root, ArrayList<String> stack, ArrayList<List<String>> resList) {
        if (!root.mark) {
            if (!stack.isEmpty()) {
                resList.add(new ArrayList<>(stack));
            }

            for (Map.Entry<String, Node> entry : root.nextMap.entrySet()) {
                stack.add(entry.getKey());
                deleteDuplicateFolder(entry.getValue(), stack, resList);
                stack.remove(stack.size() - 1);
            }
        }
    }

    // 前綴樹節點
    private class Node {
        // TreeMap存儲節點所有分支，這樣最後統計結果時會按字典序進行排列
        private TreeMap<String, Node> nextMap;
        // 標記為true則代表，節點為頭的子樹存在重複，需要刪除
        private boolean mark;

        public Node() {
            this.nextMap = new TreeMap<>();
            this.mark = false;
        }

        // 自定義判斷節點是否相等的規則，即以當前節點為頭的子樹分支路徑必須完全相等
        public boolean equals(Node node) {
            if (!nextMap.keySet().equals(node.nextMap.keySet())) {
                return false;
            }
            for (Map.Entry<String, Node> entry : nextMap.entrySet()) {
                if (!entry.getValue().equals(node.nextMap.get(entry.getKey()))) {
                    return false;
                }
            }
            return true;
        }
    }

}


