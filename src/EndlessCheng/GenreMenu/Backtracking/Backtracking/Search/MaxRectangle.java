package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.*;

public class MaxRectangle {

    // https://leetcode.cn/problems/word-rectangle-lcci/solutions/1877315/-by-lucian-6-azl3/
    Trie root;
    //用max保存當前最大矩形面積
    int max = -1;
    //用哈希表保存各個長度字符串的集合
    Map<Integer, List<String>> map = new HashMap<>();
    List<String> ans = new ArrayList<>();

    public String[] maxRectangle(String[] words) {
        this.root = new Trie();
        int n = words.length;
        for (String w : words) {
            add(w);
            map.computeIfAbsent(w.length(), k -> new ArrayList<>()).add(w);
        }
        //按照字符串長度從大到小排序
        Arrays.sort(words, (a, b) -> b.length() - a.length());
        for (int i = 0; i < n; i++) {
            Trie cur = root;
            String w = words[i];
            //用curList保存矩形的每個字符串
            List<String> curList = new ArrayList<>();
            curList.add(w);
            //用list保存當前可能形成矩形的字符串在字典樹中的當前結點
            List<Trie> list = new ArrayList<>();
            int len = w.length();
            //如果當前長度的積不大於最大矩形面積，則退出
            //因為從最大到最小排序，其中4 * 3的字符串矩陣和3 * 4的矩陣是相同的，可以進行去重
            if (len * len <= max) break;
            //check檢測是否能組成矩形，flag檢測是否可能組成更大的矩形
            boolean check = true, flag = true;
            for (int j = 0; j < len; j++) {
                int c = w.charAt(j) - 'a';
                if (cur.child[c] != null) {
                    //判斷當前字符串是否所有結點都為葉結點，即能組成字符串
                    if (!cur.child[c].isEnd) check = false;
                    list.add(cur.child[c]);
                } else {
                    //如果字符串沒有對應子結點，則無法組成矩形
                    check = false;
                    flag = false;
                    break;
                }
            }
            //如果當前字符串可以組成矩形，則進入dfs
            if (flag) {
                dfs(1, len, curList, list);
            }
            //如果當前字符串所有結點都可以為葉結點，則判斷當前矩形是否最大
            if (check && max == -1) {
                max = len;
                ans = curList;
            }
        }
        int size = ans.size();
        String[] strs = new String[size];
        for (int i = 0; i < size; i++) strs[i] = ans.get(i);
        // System.out.println(max);
        return strs;
    }

    void dfs(int cur, int len, List<String> curList, List<Trie> list) {
        if (cur == len) {
            return;
        }
        //各種結點判斷同上
        for (String w : map.get(len)) {
            boolean check = true, flag = true;
            //其中next保存下一個可能形成矩形的字典樹結點集合
            List<Trie> next = new ArrayList<>();
            //nextList保存下一個可能形成矩形的字符串集合
            List<String> nextList = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                int c = w.charAt(i) - 'a';
                Trie ct = list.get(i);
                if (ct.child[c] != null) {
                    if (!ct.child[c].isEnd) check = false;
                    next.add(ct.child[c]);
                } else {
                    check = false;
                    flag = false;
                    break;
                }
            }
            if (flag) {
                // System.out.println(w);
                nextList.addAll(curList);
                nextList.add(w);
                dfs(cur + 1, len, nextList, next);
            }
            if (check && len * (cur + 1) > max) {
                max = len * (cur + 1);
                ans = nextList;
            }
        }
    }


    class Trie {
        boolean isEnd = false;
        Trie[] child = new Trie[26];
    }

    void add(String s) {
        Trie cur = root;
        for (char c : s.toCharArray()) {
            int n = c - 'a';
            if (cur.child[n] == null) cur.child[n] = new Trie();
            cur = cur.child[n];
        }
        cur.isEnd = true;
    }

}
