package EndlessCheng.GenreMenu.Backtracking.Backtracking.Divide;

public class SplitString {

    // https://leetcode.cn/problems/splitting-a-string-into-descending-consecutive-values/solutions/755455/zhi-jie-xun-huan-liang-ge-chao-yue-100ji-9g2i/
    public boolean splitString(String s) {
        char[] array = s.toCharArray();
        int n = array.length;
        long pre = 0;
        //枚舉pre的大小，然後進入遞歸
        for (int i = 0; i < n - 1; ++i) {
            pre = pre * 10 + array[i] - '0';
            if (dfs(array, i + 1, pre)) {
                return true;
            }
        }
        return false;
    }

    public boolean dfs(char[] array, int index, long pre) {
        //當下標到達盡頭時，返回true，結束
        if (index == array.length) {
            return true;
        }
        long cur = 0;
        //判斷當前數與前方的數的大小關系
        for (int i = index; i < array.length; ++i) {
            cur = cur * 10 + array[i] - '0';
            //如果滿足cur == pre - 1，再次進入遞歸
            if (pre - cur == 1) {
                //如果已經到了最後，就依次返回了
                if (dfs(array, i + 1, cur)) {
                    return true;
                }
            }
            //如果cur >= pre， 此路不通，從新選擇路
            else if (pre <= cur) {
                break;
            }
        }
        return false;
    }

}
