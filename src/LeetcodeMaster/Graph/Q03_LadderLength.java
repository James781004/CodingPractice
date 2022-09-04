package LeetcodeMaster.Graph;

import java.util.*;

public class Q03_LadderLength {
//    127. 單詞接龍
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0127.%E5%8D%95%E8%AF%8D%E6%8E%A5%E9%BE%99.md
//
//    字典 wordList 中從單詞 beginWord 和 endWord 的 轉換序列 是一個按下述規格形成的序列：
//
//    序列中第一個單詞是 beginWord 。
//    序列中最後一個單詞是 endWord 。
//    每次轉換只能改變一個字母。
//    轉換過程中的中間單詞必須是字典 wordList 中的單詞。
//    給你兩個單詞 beginWord 和 endWord 和一個字典 wordList ，找到從 beginWord 到 endWord 的 最短轉換序列 中的 單詞數目 。
//    如果不存在這樣的轉換序列，返回 0。
//    示例 1：
//
//    輸入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
//    輸出：5
//    解釋：一個最短轉換序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的長度 5。
//    示例 2：
//
//    輸入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
//    輸出：0
//    解釋：endWord "cog" 不在字典中，所以無法進行轉換。

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
                    chars[i] = k; // 替換第i個字符
                    String newWord = String.valueOf(chars); // 得到新的字符串
                    if (newWord.equals(endWord)) {  // 如果新的字符串值與endWord一致，返回當前長度+1
                        return path + 1;
                    }
                    if (wordSet.contains(newWord) && !map.containsKey(newWord)) { // 如果新單詞在set中，但是沒有訪問過
                        map.put(newWord, path + 1); // 記錄單詞對應的路徑長度
                        queue.offer(newWord);// 加入隊尾
                    }
                }
            }
        }
        return 0; // 未找到
    }
}
