package Grind.Ch05_Graph;

import java.util.*;

public class Q19_AlienDictionary {
    // https://medium.com/@ChYuan/leetcode-269-alien-dictionary-%E5%BF%83%E5%BE%97-hard-7b04656b7569
    public String alienOrder(String[] words) {
        // 1.構建圖
        Map<Character, Set<Character>> map = new HashMap<>();
        for (int i = 0; i < words.length - 1; i++) {
            for (int j = 0; j < words[i].length() && j < words[i + 1].length(); j++) {
                // 如果字符相同，比較下一個
                if (words[i].charAt(j) == words[i + 1].charAt(j)) continue;
                // 保存第一個不同的字符順序
                Set<Character> set = map.getOrDefault(words[i].charAt(j), new HashSet<>());
                set.add(words[i + 1].charAt(j));
                map.put(words[i].charAt(j), set);
                break;
            }
        }

        // 2.拓撲排序
        // 創建保存入度的數組
        int[] degrees = new int[26];
        Arrays.fill(degrees, -1);
        // 注意，不是26字母都在words中出現，所以出度分為兩種情況：沒有出現的字母出度為-1，出現了的字母的出度為非負數
        for (String str : words) {
            // 將出現過的字符的出度設定為0
            for (char c : str.toCharArray())
                degrees[c - 'a'] = 0;
        }
        for (char key : map.keySet()) {
            for (char val : map.get(key)) {
                degrees[val - 'a']++;
            }
        }
        // 創建StringBuilder保存拓撲排序
        StringBuilder sb = new StringBuilder();
        // 創建一個Queue保存入度為0的節點
        Queue<Character> q = new LinkedList<>();

        int count = 0; // 計算圖中節點數
        for (int i = 0; i < 26; i++) {
            if (degrees[i] != -1) count++;
            if (degrees[i] == 0) {
                q.add((char) ('a' + i));
            }
        }

        while (!q.isEmpty()) {
            Character cur = q.poll();
            sb.append(cur);
            // 將鄰接點出度-1
            if (map.containsKey(cur)) {
                Set<Character> set = map.get(cur);
                for (Character c : set) {
                    degrees[c - 'a']--;
                    if (degrees[c - 'a'] == 0) q.add(c);
                }
            }
        }

        // 判斷是否有環
        if (sb.length() != count) return "";
        else return sb.toString();
    }
}
