package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

import java.util.ArrayList;
import java.util.List;

public class LetterCasePermutation {

    // https://leetcode.cn/problems/letter-case-permutation/solutions/3076463/javaban-ling-shen-mo-ban-shi-jian-ji-bai-pvgj/
    private List<String> ans = new ArrayList<>();//用於存儲答案
    private String s;
    private char[] path;//用於存儲當前解

    public List<String> letterCasePermutation(String s) {
        int n = s.length();
        path = new char[n];//因為當前解大小和s一樣，所以提前分配好空間
        this.s = s;
        dfs(0);//開始回溯
        return ans;
    }

    //回溯模板
    private void dfs(int i) {
        if (i == s.length()) {
            ans.add(new String(path));//將當前解添加到最終答案里
            return;
        }
        if (Character.isLetter(s.charAt(i))) {
            //當前是大寫字母
            path[i] = Character.toLowerCase(s.charAt(i));
            dfs(i + 1);
            //當前是小寫字母
            path[i] = Character.toUpperCase(s.charAt(i));
            dfs(i + 1);
        } else {
            //當前是數字
            path[i] = s.charAt(i);
            dfs(i + 1);
        }
    }


}
