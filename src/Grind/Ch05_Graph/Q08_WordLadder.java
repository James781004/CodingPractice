package Grind.Ch05_Graph;

import java.util.*;

public class Q08_WordLadder {
    // https://leetcode.cn/problems/word-ladder/solutions/473829/127-dan-ci-jie-long-wei-shi-yao-yao-yong-yan-sou-x/
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> wordSet = new HashSet<>(wordList); // 轉換為hashset 加快速度
        if (wordSet.size() == 0 || !wordSet.contains(endWord)) {  // 特殊情況判斷
            return 0;
        }
        Queue<String> queue = new LinkedList<>(); // bfs 隊列
        queue.offer(beginWord);
        Map<String, Integer> map = new HashMap<>(); // 記錄單詞對應路徑長度
        map.put(beginWord, 1);

        while (!queue.isEmpty()) {
            String word = queue.poll(); // 取出隊頭單詞
            int path = map.get(word); // 獲取到該單詞的路徑長度
            for (int i = 0; i < word.length(); i++) { // 遍歷單詞的每個字符
                char[] chars = word.toCharArray(); // 將單詞轉換為char array，方便替換
                for (char k = 'a'; k <= 'z'; k++) { // 從'a' 到 'z' 遍歷替換
                    chars[i] = k; //替換第i個字符
                    String newWord = String.valueOf(chars); //得到新的字符串
                    if (newWord.equals(endWord)) {  // 如果新的字符串值與endWord一致，返回當前長度+1
                        return path + 1;
                    }
                    if (wordSet.contains(newWord) && !map.containsKey(newWord)) { // 如果新單詞在set中，但是沒有訪問過
                        map.put(newWord, path + 1); // 記錄單詞對應的路徑長度
                        queue.offer(newWord); // 加入隊尾
                    }
                }
            }
        }
        return 0; // 未找到
    }


    // https://leetcode.cn/problems/word-ladder/solutions/276923/yan-du-you-xian-bian-li-shuang-xiang-yan-du-you-2/
    // 雙向廣度優先遍歷
    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        // 第 1 步：先將 wordList 放到哈希表裡，便於判斷某個單詞是否在 wordList 裡
        Set<String> wordSet = new HashSet<>(wordList);
        if (wordSet.size() == 0 || !wordSet.contains(endWord)) {
            return 0;
        }

        // 第 2 步：已經訪問過的 word 添加到 visited 哈希表裡
        Set<String> visited = new HashSet<>();
        // 分別用左邊和右邊擴散的哈希表代替單向 BFS 裡的隊列，它們在雙向 BFS 的過程中交替使用
        Set<String> beginVisited = new HashSet<>();
        beginVisited.add(beginWord);
        Set<String> endVisited = new HashSet<>();
        endVisited.add(endWord);

        // 第 3 步：執行雙向 BFS，左右交替擴散的步數之和為所求
        int step = 1;
        while (!beginVisited.isEmpty() && !endVisited.isEmpty()) {
            // 優先選擇小的哈希表進行擴散，考慮到的情況更少
            if (beginVisited.size() > endVisited.size()) {
                Set<String> temp = beginVisited;
                beginVisited = endVisited;
                endVisited = temp;
            }

            // 邏輯到這裡，保證 beginVisited 是相對較小的集合，nextLevelVisited 在擴散完成以後，會成為新的 beginVisited
            Set<String> nextLevelVisited = new HashSet<>();
            for (String word : beginVisited) {
                if (changeWordEveryOneLetter(word, endVisited, visited, wordSet, nextLevelVisited)) {
                    return step + 1;
                }
            }

            // 原來的 beginVisited 廢棄，從 nextLevelVisited 開始新的雙向 BFS
            beginVisited = nextLevelVisited;
            step++;
        }
        return 0;
    }

    /**
     * 嘗試對 word 修改每一個字符，看看是不是能落在 endVisited 中，擴展得到的新的 word 添加到 nextLevelVisited 裡
     */
    private boolean changeWordEveryOneLetter(String word, Set<String> endVisited,
                                             Set<String> visited,
                                             Set<String> wordSet,
                                             Set<String> nextLevelVisited) {
        char[] charArray = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char originChar = charArray[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (originChar == c) {
                    continue;
                }
                charArray[i] = c;
                String nextWord = String.valueOf(charArray);
                if (wordSet.contains(nextWord)) {
                    if (endVisited.contains(nextWord)) {
                        return true;
                    }
                    if (!visited.contains(nextWord)) {
                        nextLevelVisited.add(nextWord);
                        visited.add(nextWord);
                    }
                }
            }
            // 恢復，下次再用
            charArray[i] = originChar;
        }
        return false;
    }
}
