package Grind.Ch04_BinarySearch;

public class Q02_FirstBadVersion {
    // https://leetcode.cn/problems/first-bad-version/solutions/1693158/by-jyd-19pr/
    public int firstBadVersion(int n) {
        int i = 1, j = n;
        while (i <= j) {
            // 向下取整除法計算中點 m
            int m = i + (j - i) / 2;
            // 若 m 是錯誤版本，則最後一個正確版本一定在閉區間 [i, m - 1]
            if (isBadVersion(m)) j = m - 1;
                // 若 m 是正確版本，則首個錯誤版本一定在閉區間 [m + 1, j]
            else i = m + 1;
        }
        // i 指向首個錯誤版本，j 指向最後一個正確版本
        return i;
    }

    // 左閉右開區間
    public int firstBadVersion2(int n) {
        int l = 1, r = n;
        while (l < r) {
            int mid = l + (r - l >> 1);
            if (isBadVersion(mid)) r = mid;
            else l = mid + 1;
        }
        return l;
    }

    private boolean isBadVersion(int version) {
        return true;
    }
}
