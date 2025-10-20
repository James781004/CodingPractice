package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.*;

public class FindLadders {

    // https://leetcode.cn/problems/word-ladder-ii/solutions/42238/java-duo-jie-fa-bfs-shuang-xiang-bfsdfssi-lu-fen-x/
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // 結果集
        List<List<String>> res = new ArrayList<>();
        Set<String> words = new HashSet<>(wordList);
        // 字典中不包含目標單詞
        if (!words.contains(endWord)) {
            return res;
        }
        // 存放關系：每個單詞可達的下層單詞
        Map<String, List<String>> mapTree = new HashMap<>();
        Set<String> begin = new HashSet<>(), end = new HashSet<>();
        begin.add(beginWord);
        end.add(endWord);
        if (buildTree(words, begin, end, mapTree, true)) {
            dfs(res, mapTree, beginWord, endWord, new LinkedList<>());
        }
        return res;
    }

    // 雙向BFS，構建每個單詞的層級對應關系
    private boolean buildTree(Set<String> words, Set<String> begin, Set<String> end, Map<String, List<String>> mapTree, boolean isFront) {
        if (begin.size() == 0) {
            return false;
        }
        // 始終以少的進行探索
        if (begin.size() > end.size()) {
            return buildTree(words, end, begin, mapTree, !isFront);
        }
        // 在已訪問的單詞集合中去除
        words.removeAll(begin);
        // 標記本層是否已到達目標單詞
        boolean isMeet = false;
        // 記錄本層所訪問的單詞
        Set<String> nextLevel = new HashSet<>();
        for (String word : begin) {
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char temp = chars[i];
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    chars[i] = ch;
                    String str = String.valueOf(chars);
                    if (words.contains(str)) {
                        nextLevel.add(str);
                        // 根據訪問順序，添加層級對應關系：始終保持從上層到下層的存儲存儲關系
                        // true: 從上往下探索：word -> str
                        // false: 從下往上探索：str -> word（查找到的 str 是 word 上層的單詞）
                        String key = isFront ? word : str;
                        String nextWord = isFront ? str : word;
                        // 判斷是否遇見目標單詞
                        if (end.contains(str)) {
                            isMeet = true;
                        }
                        if (!mapTree.containsKey(key)) {
                            mapTree.put(key, new ArrayList<>());
                        }
                        mapTree.get(key).add(nextWord);
                    }
                }
                chars[i] = temp;
            }
        }
        if (isMeet) {
            return true;
        }
        return buildTree(words, nextLevel, end, mapTree, isFront);
    }

    // DFS: 組合路徑
    private void dfs(List<List<String>> res, Map<String, List<String>> mapTree, String beginWord, String endWord, LinkedList<String> list) {
        list.add(beginWord);
        if (beginWord.equals(endWord)) {
            res.add(new ArrayList<>(list));
            list.removeLast();
            return;
        }
        if (mapTree.containsKey(beginWord)) {
            for (String word : mapTree.get(beginWord)) {
                dfs(res, mapTree, word, endWord, list);
            }
        }
        list.removeLast();
    }


}
