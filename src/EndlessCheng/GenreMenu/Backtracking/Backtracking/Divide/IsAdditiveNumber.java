package EndlessCheng.GenreMenu.Backtracking.Backtracking.Divide;

public class IsAdditiveNumber {

    // https://leetcode.cn/problems/additive-number/solutions/1202024/tong-ge-lai-shua-ti-la-dfs-jian-zhi-by-t-jxsf/
    public boolean isAdditiveNumber(String num) {
        return dfs(num, 0, 0, 0, 0);
    }

    private boolean dfs(String num, int index, int count, long prevprev, long prev) {
        if (index >= num.length()) {
            return count > 2;
        }

        long current = 0;
        for (int i = index; i < num.length(); i++) {
            char c = num.charAt(i);

            if (num.charAt(index) == '0' && i > index) {
                // 剪枝1：不能做為前導0，但是它自己是可以單獨做為0來使用的
                return false;
            }

            current = current * 10 + c - '0';

            if (count >= 2) {
                long sum = prevprev + prev;
                if (current > sum) {
                    // 剪枝2：如果當前數比之前兩數的和大了，說明不合適
                    return false;
                }
                if (current < sum) {
                    // 剪枝3：如果當前數比之前兩數的和小了，說明還不夠，可以繼續添加新的字符進來
                    continue;
                }
            }

            // 當前滿足條件了，或者還不到兩個數，向下一層探索
            if (dfs(num, i + 1, count + 1, prev, current)) {
                return true;
            }
        }

        return false;
    }


}
