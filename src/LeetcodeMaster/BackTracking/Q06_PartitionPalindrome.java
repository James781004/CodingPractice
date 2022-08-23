package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Q06_PartitionPalindrome {
//    131.分割回文串
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0131.%E5%88%86%E5%89%B2%E5%9B%9E%E6%96%87%E4%B8%B2.md
//
//    給定一個字符串 s，將 s 分割成一些子串，使每個子串都是回文串。
//
//    返回 s 所有可能的分割方案。
//
//    示例: 輸入: "aab" 輸出: [ ["aa","b"], ["a","a","b"] ]


    List<List<String>> lists = new ArrayList<>();
    Deque<String> deque = new LinkedList<>();

    public List<List<String>> partition(String s) {
        process(s, 0);
        return lists;
    }

    private void process(String s, int index) {
        if (index >= s.length()) { // 如果起始位置大於s的大小，說明找到了一組分割方案
            lists.add(new ArrayList<>(deque));
            return;
        }

        for (int i = index; i < s.length(); i++) {
            if (isPalindrome(s, index, i)) {
                String str = s.substring(index, i + 1);
                deque.addLast(str); // 如果是回文子串，則記錄
            } else {
                continue;
            }

            process(s, i + 1); // 起始位置後移，保證不重複
            deque.removeLast();
        }
    }

    // 判斷是否是回文串
    private boolean isPalindrome(String s, int start, int end) {
        for (int i = start, j = end; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
    }
}
