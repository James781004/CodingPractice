package EndlessCheng.GenreMenu.Backtracking.Backtracking.Other;

public class ProcessStr {

    // https://leetcode.cn/problems/process-string-with-special-operations-ii/solutions/3722462/ni-xiang-si-wei-pythonjavacgo-by-endless-26al/
    char processStr(String S, long k) {
        char[] s = S.toCharArray();
        int n = s.length;
        long[] size = new long[n];
        long sz = 0;
        for (int i = 0; i < n; i++) {
            char c = s[i];
            if (c == '*') {
                sz = Math.max(sz - 1, 0);
            } else if (c == '#') {
                sz *= 2; // 題目保證 sz <= 1e15
            } else if (c != '%') { // c 是字母
                sz++;
            }
            size[i] = sz;
        }

        if (k >= size[n - 1]) { // 下標越界
            return '.';
        }

        // 迭代
        for (int i = n - 1; ; i--) {
            char c = s[i];
            sz = size[i];
            if (c == '#') {
                if (k >= sz / 2) { // k 在復制後的右半邊
                    k -= sz / 2;
                }
            } else if (c == '%') {
                k = sz - 1 - k; // 反轉前的下標為 sz-1-k 的字母就是答案
            } else if (c != '*' && k == sz - 1) { // 找到答案
                return c;
            }
        }
    }


}
