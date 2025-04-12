package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

public class BackspaceCompare {

    // https://leetcode.cn/problems/backspace-string-compare/solutions/683776/shuang-zhi-zhen-bi-jiao-han-tui-ge-de-zi-8fn8/
    public boolean backspaceCompare(String S, String T) {

        // 設置兩個指針的位置，定義跳過次數機會變量
        int i = S.length() - 1, j = T.length() - 1;
        int skipS = 0, skipT = 0;

        while (i >= 0 || j >= 0) {
            // 現在可以看成i後面的都是與j後面的一樣，此時i往前面尋找下一個要比較的數，看看是否和j等等要找的數字一樣
            while (i >= 0) {
                // 如果i是井號跳過次數+1，就不用拿井號前面那個數字比較，通過這個循環來找下一個比較的數
                if (S.charAt(i) == '#') {
                    skipS++;
                    i--;
                } else if (skipS > 0) {
                    skipS--;
                    i--;
                } else {
                    break; // 如果找到了下一個要比較的數字，那你就break，去看看指針j怎麼找的
                }
            }

            // 此時j往前面尋找下一個要比較的數，看看是否和i剛剛要找的數字一樣
            while (j >= 0) {
                if (T.charAt(j) == '#') {
                    skipT++;
                    j--;
                } else if (skipT > 0) {
                    skipT--;
                    j--;
                } else {
                    break;
                }
            }

            // 如果i和j沒越過邊界，那就看看i，j所指向的值是否相等
            if (i >= 0 && j >= 0) {
                if (S.charAt(i) != T.charAt(j)) {
                    return false;
                }
            } else {
                // 這裡能夠進去代表i或者j有一個或者兩個越界了，
                // 那什麼情況會進入？假如現在你的i和j後面的數字都比較過了，都是相等的‘
                // 但如果i找不到下一位的時候，找不到了，並且j能夠找到下一個要比較的數字的時候，就證明兩個數組不相等，返回false
                if (i >= 0 || j >= 0) {
                    return false;
                }
            }
            i--;
            j--;
        }
        return true;
    }


}
