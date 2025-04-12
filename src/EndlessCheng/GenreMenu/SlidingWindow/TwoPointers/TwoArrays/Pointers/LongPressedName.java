package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

public class LongPressedName {

    // https://leetcode.cn/problems/long-pressed-name/solutions/454473/925-chang-an-jian-ru-mo-ni-pi-pei-xiang-jie-by-car/
    public boolean isLongPressedName(String name, String typed) {
        int i = 0, j = 0;
        int m = name.length(), n = typed.length();
        while (i < m && j < n) {
            if (name.charAt(i) == typed.charAt(j)) {  // 相同則同時向後匹配
                i++;
                j++;
            } else {
                if (j == 0) return false; // 如果是第一位就不相同直接返回false
                // 判斷邊界為n-1,若為n會越界,例如name:"kikcxmvzi" typed:"kiikcxxmmvvzzz"
                while (j < n - 1 && typed.charAt(j) == typed.charAt(j - 1)) j++;
                if (name.charAt(i) == typed.charAt(j)) {  // j跨越重復項之後再次和name[i]匹配
                    i++;
                    j++; // 相同則同時向後匹配
                } else return false;
            }
        }
        // 說明name沒有匹配完
        if (i < m) return false;
        // 說明type沒有匹配完
        while (j < n) {
            if (typed.charAt(j) == typed.charAt(j - 1)) j++;
            else return false;
        }
        return true;
    }


}
