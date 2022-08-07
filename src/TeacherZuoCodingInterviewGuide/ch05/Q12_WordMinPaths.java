package TeacherZuoCodingInterviewGuide.ch05;

import java.util.*;

public class Q12_WordMinPaths {
//    數組中兩個字符串的最小距離
//    描述
//    給定一個字符串數組strs，再給定兩個字符串str1和str2，返回在strs中str1和str2的最小距離，
//    如果str1或str2為null，或不在strs中，返回-1。

    public static List<List<String>> findMinPaths(String start, String to,
                                                  List<String> list) {
        list.add(start);
        HashMap<String, ArrayList<String>> nexts = getNexts(list);
        HashMap<String, Integer> distances = getDistances(start, nexts);
        LinkedList<String> pathList = new LinkedList<>();
        List<List<String>> res = new ArrayList<>();
        getShortestPaths(start, to, nexts, distances, pathList, res);
        return res;
    }

    // 建立關連表
    public static HashMap<String, ArrayList<String>> getNexts(List<String> words) {
        Set<String> dict = new HashSet<>(words); // 把words中所有元素放進set作參照資料使用
        HashMap<String, ArrayList<String>> nexts = new HashMap<>();
        for (int i = 0; i < words.size(); i++) {
            nexts.put(words.get(i), new ArrayList<>());
        }
        for (int i = 0; i < words.size(); i++) {
            nexts.put(words.get(i), getNext(words.get(i), dict));
        }
        return nexts;
    }

    private static ArrayList<String> getNext(String word, Set<String> dict) {
        ArrayList<String> res = new ArrayList<String>();
        char[] chs = word.toCharArray();
        for (char cur = 'a'; cur <= 'z'; cur++) { // 每個位置字符都替換一遍a~z試試看，如果dict裡面有就放進res
            for (int i = 0; i < chs.length; i++) {
                if (chs[i] != cur) {
                    char tmp = chs[i];
                    chs[i] = cur;
                    if (dict.contains(String.valueOf(chs))) {
                        res.add(String.valueOf(chs));
                    }
                    chs[i] = tmp; // 復原字串，下一個loop還要繼續替換字符查詢dict比較
                }
            }
        }
        return res;
    }

    // 建立距離表
    public static HashMap<String, Integer> getDistances(String start,
                                                        HashMap<String, ArrayList<String>> nexts) {
        HashMap<String, Integer> distances = new HashMap<>();
        distances.put(start, 0); // start距離自己距離為0
        Queue<String> queue = new LinkedList<String>();
        queue.add(start); // 先進先出，目標是做出類似圖的結構
        HashSet<String> set = new HashSet<String>();
        set.add(start); // 使用過的元素放進set，不可重複使用
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            for (String str : nexts.get(cur)) { // 從cur關連表裡找下一個元素，以建立距離關係
                if (!set.contains(str)) { // 沒有重複使用過的元素
                    distances.put(str, distances.get(cur) + 1); // 距離+1
                    queue.add(str); // 加入隊列
                    set.add(str); // 使用過的元素放進set，不可重複使用
                }
            }
        }
        return distances;
    }

    private static void getShortestPaths(String cur, String to,
                                         HashMap<String, ArrayList<String>> nexts,
                                         HashMap<String, Integer> distances, LinkedList<String> solution,
                                         List<List<String>> res) {
        solution.add(cur); // 默認加入尾部
        if (to.equals(cur)) { // 已經到達終點的狀況，res直接添加結果
            res.add(new LinkedList<String>(solution));
        } else {
            for (String next : nexts.get(cur)) { // 遍歷關連表
                if (distances.get(next) == distances.get(cur) + 1) { // 如果關連表中找到當前cur的下一步路徑，就進入遞歸
                    getShortestPaths(next, to, nexts, distances, solution, res);
                }
            }
        }
        solution.pollLast(); // 回溯，移除先前加入尾部的元素
    }

    public static void main(String[] args) {
        String start = "abc";
        String end = "cab";
        String[] test = {"abc", "cab", "acc", "cbc", "ccc", "cac", "cbb",
                "aab", "abb"};
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < test.length; i++) {
            list.add(test[i]);
        }
        List<List<String>> res = findMinPaths(start, end, list);
        for (List<String> obj : res) {
            for (String str : obj) {
                System.out.print(str + " -> ");
            }
            System.out.println();
        }

    }
}
