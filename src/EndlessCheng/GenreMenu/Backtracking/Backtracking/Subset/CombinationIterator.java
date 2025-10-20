package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

import java.util.LinkedList;
import java.util.Queue;

public class CombinationIterator {

    // https://leetcode.cn/problems/iterator-for-combination/solutions/3617977/zi-ji-xing-hui-su-ling-shen-ti-dan-ban-z-h2kp/
    private final Queue<String> res;
    private char[] s;
    private char[] path;
    private int len;

    public CombinationIterator(String characters, int combinationLength) {
        res = new LinkedList<>();
        this.s = characters.toCharArray();
        this.len = combinationLength;
        path = new char[len];
        dfs(0, 0);
    }

    private void dfs(int i, int pathlen) {
        if (pathlen == len) {
            res.offer(new String(path));
            return;
        }
        if (i == s.length) return;
        path[pathlen] = s[i]; // 直接覆蓋
        dfs(i + 1, pathlen + 1);
        dfs(i + 1, pathlen);

    }

    public String next() {
        return res.poll(); // 取出隊列首元素
    }

    public boolean hasNext() {
        return !res.isEmpty(); // 檢查是否有下一個組合
    }
}

