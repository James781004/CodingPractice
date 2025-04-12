package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

public class CanChangePieces {

    // https://leetcode.cn/problems/move-pieces-to-obtain-a-string/solutions/1658923/nao-jin-ji-zhuan-wan-pythonjavacgo-by-en-9sqt/
    public boolean canChange(String start, String target) {

        // 無論怎麼移動，由於 L 和 R 無法互相穿過對方，那麼去掉 _ 後的剩余字符應該是相同的，否則返回 false
        if (!start.replace("_", "").equals(target.replace("_", ""))) {
            return false;
        }

        // 雙指針從左向右遍歷 start 和 target，分類討論
        for (int i = 0, j = 0; i < start.length(); i++) {
            if (start.charAt(i) == '_') {
                continue;
            }
            while (target.charAt(j) == '_') {
                j++;
            }

            // 若i=j，則說明一定匹配（因為在for循環上面的判斷中保證了相對順序）
            // 若當前 start[i] 為 L，則必須滿足 i >= j，否則 L 無法左移到 target[j]
            if (start.charAt(i) == 'L' && i < j) {
                return false;
            }

            // 若當前 start[i] 為 R，則必須滿足 i <= j，否則 R 無法右移到 target[j]
            if (start.charAt(i) == 'R' && i > j) {
                return false;
            }

            j++;
        }
        return true;
    }


}
