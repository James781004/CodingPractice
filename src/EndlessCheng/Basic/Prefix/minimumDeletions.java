package EndlessCheng.Basic.Prefix;

public class minimumDeletions {


    // https://leetcode.cn/problems/minimum-deletions-to-make-string-balanced/solutions/2149746/qian-hou-zhui-fen-jie-yi-zhang-tu-miao-d-dor2/
    public int minimumDeletions(String S) {
        var s = S.toCharArray();
        int del = 0;
        for (var c : s)
            del += 'b' - c; // 統計 'a' 的個數
        int ans = del;
        for (var c : s) {
            // 'a' -> -1    'b' -> 1
            del += (c - 'a') * 2 - 1;
            ans = Math.min(ans, del);
        }
        return ans;
    }


    // 動態規劃（一次遍歷）
    public int minimumDeletions2(String s) {
        int f = 0, cntB = 0;
        for (char c : s.toCharArray())
            if (c == 'b') ++cntB; // f 值不變
            else f = Math.min(f + 1, cntB);
        return f;
    }


}
