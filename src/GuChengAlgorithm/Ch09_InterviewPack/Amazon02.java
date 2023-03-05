package GuChengAlgorithm.Ch09_InterviewPack;

import java.util.*;

public class Amazon02 {
    // https://docs.google.com/presentation/d/1xc5lyMDoNGZvYn7oiWH2cM0HSj_jqqzsR3P0LTO10AE/edit#slide=id.g13c4ece6fbc_0_52
    public String minWindow(String s, String t) {
        Map<Character, Integer> map = new HashMap<>(); // map存放t的所有字元總數
        for (char c : t.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);
        int left = 0, minStart = 0, minLen = Integer.MAX_VALUE, count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) { // map中含有當前字元c
                if (map.get(c) > 0) count++; // valid count: map中含有當前字元c並且數量有剩餘
                map.put(c, map.get(c) - 1);  // 從map中取一個字元c出來(無論實際上字元c數量是否大於0都要取出來)
            }

            while (count == t.length()) { // t字串條件滿足
                if (i - left + 1 < minLen) {  // 發現更短的合法字串長度，可以更新minLen
                    minLen = i - left + 1;
                    minStart = left;
                }

                // 移動左窗口之前，如果左窗口字元本來就在map中，需要還回去
                char leftChar = s.charAt(left);
                if (map.containsKey(leftChar)) {
                    map.put(leftChar, map.get(leftChar) + 1);
                    if (map.get(leftChar) > 0) count--; // valid count: map中含有當前字元c並且數量"開始"有剩餘，這邊count就減1
                }
                left++;
            }
        }

        if (minLen == Integer.MAX_VALUE) return "";
        return s.substring(minStart, minStart + minLen);
    }


    // https://docs.google.com/presentation/d/1xc5lyMDoNGZvYn7oiWH2cM0HSj_jqqzsR3P0LTO10AE/edit#slide=id.g13c4ece6fbc_0_57
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        dfs(root, null, parent);  // 建立parent map
        Queue<TreeNode> q = new LinkedList<>();
        q.add(target);
        Set<TreeNode> set = new HashSet<>();
        set.add(target);
        int distance = 0;
        List<Integer> res = new ArrayList<>();
        while (!q.isEmpty()) {
            int size = q.size();
            if (distance == K) {
                for (TreeNode node : q) res.add(node.val);  // 展開k層後，queue裡面就是題目要的答案
                break;
            }
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                TreeNode p = parent.get(cur);
                if (p != null && set.add(p)) q.offer(p);  // go parent
                if (cur.left != null && set.add(cur.left)) q.offer(cur.left);  // go left child
                if (cur.right != null && set.add(cur.right)) q.offer(cur.right);  // go right child
            }
            distance++;
        }
        return res;
    }

    private void dfs(TreeNode node, TreeNode p, Map<TreeNode, TreeNode> parent) {
        parent.put(node, p);
        if (node.left != null) dfs(node.left, node, parent);
        if (node.right != null) dfs(node.right, node, parent);
    }

    class TreeNode {
        TreeNode left, right;
        int val;

        public TreeNode(int v) {
            val = v;
        }
    }


    // https://docs.google.com/presentation/d/1xc5lyMDoNGZvYn7oiWH2cM0HSj_jqqzsR3P0LTO10AE/edit#slide=id.g13c4ece6fbc_0_102
    public String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 9, 5, 4, 1};
        String[] strs = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                sb.append(strs[i]);
            }
        }
        return sb.toString();
    }

    public String intToRoman2(int num) {
        String[] M = {"", "M", "MM", "MMM"};
        String[] C = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] X = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] I = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return M[num / 1000] + C[(num % 100) / 100] + X[(num % 100) / 10] + I[num % 10];
    }


    // https://docs.google.com/presentation/d/1xc5lyMDoNGZvYn7oiWH2cM0HSj_jqqzsR3P0LTO10AE/edit#slide=id.g13c4ece6fbc_0_112
    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int res = map.get(s.charAt(s.length() - 1));
        for (int i = s.length() - 2; i >= 0; i--) {
            if (map.get(s.charAt(i)) >= map.get(s.charAt(i + 1))) {
                res += map.get(s.charAt(i));
            } else { // ex: IV, IX
                res -= map.get(s.charAt(i));  // IV: 5 - 1 = 4
            }
        }
        return res;
    }

    static Map<String, Integer> values = new HashMap<>();

    static {
        values.put("I", 1);
        values.put("V", 5);
        values.put("X", 10);
        values.put("L", 50);
        values.put("C", 100);
        values.put("D", 500);
        values.put("M", 1000);
        values.put("IV", 4);
        values.put("IX", 9);
        values.put("XL", 40);
        values.put("XC", 90);
        values.put("CD", 400);
        values.put("CM", 900);
    }

    public int romanToInt2(String s) {
        int sum = 0;
        int i = 0;
        while (i < s.length()) {
            if (i < s.length() - 1) {
                String doubleSymbol = s.substring(i, i + 2);
                if (values.containsKey(doubleSymbol)) {  // check if it is the length - 2 symbol case
                    sum += values.get(doubleSymbol);
                    i += 2;
                    continue;
                }
            }
            // Otherwise, it must be the length - 1 symbol case
            String singleSymbol = s.substring(i, i + 1);
            sum += values.get(singleSymbol);
            i += 1;
        }
        return sum;
    }
}
