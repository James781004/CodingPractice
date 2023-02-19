package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Q05_Backtracking {
    // https://docs.google.com/presentation/d/1ygAUp7D6Jv_8OQACIid079TkW4ZMxT-uhrH2ARZZ1ho/edit#slide=id.g109ee49acd5_0_181
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(res, new ArrayList<>(), nums, 0);
        return res;
    }

    // 自上而下找尋 valid path，使用void方法
    private void backtrack(List<List<Integer>> res, List<Integer> tmp, int[] nums, int start) {
        res.add(new ArrayList<>(tmp));
        for (int i = start; i < nums.length; i++) { // 剪枝操作
            tmp.add(nums[i]);
            backtrack(res, tmp, nums, i + 1);
            tmp.remove(tmp.size() - 1);
        }
    }


    // https://docs.google.com/presentation/d/1ygAUp7D6Jv_8OQACIid079TkW4ZMxT-uhrH2ARZZ1ho/edit#slide=id.g109ee49acd5_0_189
    String[] map = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits.length() != 0) dfs(res, new StringBuilder(), 0, digits);
        return res;
    }

    private void dfs(List<String> res, StringBuilder level, int index, String digits) {
        if (level.length() == digits.length()) { // digits string 已經走完的情況，開始保存path
            res.add(level.toString());
            return;
        }
        // index表示目前按下的數字
        String tmp = map[digits.charAt(index) - '0'];
        for (int i = 0; i < tmp.length(); i++) {
            level.append(tmp.charAt(i));
            dfs(res, level, index + 1, digits);
            level.deleteCharAt(level.length() - 1);
        }
    }


    public List<String> letterCombinationsBFS(String digits) {
        Queue<String> q = new LinkedList<>();
        if (digits.isEmpty()) return new ArrayList<>();
        q.add("");
        for (int i = 0; i < digits.length(); i++) {
            String tmp = map[digits.charAt(i) - '0'];
            int size = q.size();
            for (int k = 0; k <= size; k++) {
                String t = q.poll();
                for (char c : tmp.toCharArray()) q.add(t + c);  // 原本字符組合加上新字符後加回queue
            }
        }
        return new ArrayList<>(q);
    }
}
