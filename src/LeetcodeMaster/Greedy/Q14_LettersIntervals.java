package LeetcodeMaster.Greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Q14_LettersIntervals {
//    763.劃分字母區間
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0763.%E5%88%92%E5%88%86%E5%AD%97%E6%AF%8D%E5%8C%BA%E9%97%B4.md
//
//    字符串 S 由小寫字母組成。我們要把這個字符串劃分為盡可能多的片段，同一字母最多出現在一個片段中。返回一個表示每個字符串片段的長度的列表。
//
//    示例：
//
//    輸入：S = "ababcbacadefegdehijhklij"
//    輸出：[9,7,8]
//    解釋： 劃分結果為 "ababcbaca", "defegde", "hijhklij"。
//    每個字母最多出現在一個片段中。 像 "ababcbacadefegde", "hijhklij" 的劃分是錯誤的，因為劃分的片段數較少。
//    提示：
//
//    S的長度在[1, 500]之間。
//    S只包含小寫字母 'a' 到 'z' 。


    public List<Integer> partitionLabels(String S) {
        List<Integer> list = new LinkedList<>();
        int[] edge = new int[26];
        char[] chars = S.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            edge[chars[i] - 'a'] = i;  // 統計每一個字符最後出現的位置
        }
        int idx = 0;
        int last = -1;
        for (int i = 0; i < chars.length; i++) {
            idx = Math.max(idx, edge[chars[i] - 'a']);  // 找到字符出現的最遠邊界

            // 目前i已經到達某個字符所出現的最遠邊界，就要準備開始分割，
            // 因為題目設定同一字母最多出現在一個片段中
            // 既然之後的片段都不可能再次出現當前字符，那些現在這個邊界就只能作為一個片段分割出來
            if (i == idx) {
                list.add(i - last); // 如果找到之前遍歷過的所有字母的最遠邊界，說明這個邊界就是分割點了
                last = i;
            }
        }
        return list;
    }

    // 使用射氣球問題的思路
    public List<Integer> partitionLabels2(String s) {
        int[][] partitions = findPartitions(s);
        List<Integer> res = new ArrayList<>();
        Arrays.sort(partitions, (o1, o2) -> Integer.compare(o1[0], o2[0]));
        int right = partitions[0][1];
        int left = 0;
        for (int i = 0; i < partitions.length; i++) {
            if (partitions[i][0] > right) {
                res.add(right - left + 1); // 當前左邊界大於之前右邊界即可作為一次分割
                left = partitions[i][0]; // 分割完後更新左邊界
            }
            right = Math.max(right, partitions[i][1]);  // 更新右邊界

        }
        //最右端
        res.add(right - left + 1);
        return res;
    }

    public int[][] findPartitions(String s) {
        List<Integer> temp = new ArrayList<>();
        int[][] hash = new int[26][2]; // 26個字母2列 表示該字母對應的區間

        for (int i = 0; i < s.length(); i++) {
            // 更新字符c對應的位置i
            char c = s.charAt(i);
            if (hash[c - 'a'][0] == 0) hash[c - 'a'][0] = i;

            hash[c - 'a'][1] = i;

            // 第一個元素區別對待一下
            hash[s.charAt(0) - 'a'][0] = 0;
        }


        List<List<Integer>> h = new LinkedList<>();
        // 組裝區間
        for (int i = 0; i < 26; i++) {
            //if (hash[i][0] != hash[i][1]) {
            temp.clear();
            temp.add(hash[i][0]);
            temp.add(hash[i][1]);
            //System.out.println(temp);
            h.add(new ArrayList<>(temp));
            // }
        }
        // System.out.println(h);
        // System.out.println(h.size());
        int[][] res = new int[h.size()][2];
        for (int i = 0; i < h.size(); i++) {
            List<Integer> list = h.get(i);
            res[i][0] = list.get(0);
            res[i][1] = list.get(1);
        }

        return res;

    }
}
