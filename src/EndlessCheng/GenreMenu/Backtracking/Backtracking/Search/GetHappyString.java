package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

public class GetHappyString {

    // https://leetcode.cn/problems/the-k-th-lexicographical-string-of-all-happy-strings-of-length-n/solutions/3734215/chang-du-wei-n-de-kai-xin-zi-fu-chuan-zh-8onp/
    private int k;

    public String getHappyString(int n, int k) {
        // 計算總數
        int total = 3 * (1 << (n - 1));
        if (k > total) return "";
        this.k = k;
        StringBuilder cur = new StringBuilder();
        backtrace(0, cur, n);
        return cur.toString();
    }

    private boolean backtrace(int index, StringBuilder cur, int n) {
        if (index == n) {
            // 碰到可行解計數器減一
            if (--k == 0) return true;
            return false;
        }

        // 按字典序從小到大生成字符串，滿足條件立刻返回
        for (int i = 0; i < 3; i++) {
            if (index == 0) {  // 第一個位置
                cur.append((char) ('a' + i));
                if (backtrace(index + 1, cur, n)) return true;
                cur.setLength(0);
            } else { // 其他位置
                int prev = cur.charAt(index - 1) - 'a';
                if (i == prev) continue;
                cur.append((char) ('a' + i));
                if (backtrace(index + 1, cur, n)) return true;
                cur.deleteCharAt(cur.length() - 1);
            }
        }
        return false;
    }


}
