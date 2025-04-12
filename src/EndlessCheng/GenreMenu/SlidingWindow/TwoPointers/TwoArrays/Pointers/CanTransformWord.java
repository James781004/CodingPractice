package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

public class CanTransformWord {

    // https://leetcode.cn/problems/swap-adjacent-in-lr-string/solutions/1659769/by-endlesscheng-fcgc/
    public boolean canTransform(String start, String target) {
        if (!start.replace("X", "").equals(target.replace("X", ""))) {
            return false;
        }
        for (int i = 0, j = 0; i < start.length(); i++) {
            if (start.charAt(i) == 'X') {
                continue;
            }
            while (target.charAt(j) == 'X') {
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
