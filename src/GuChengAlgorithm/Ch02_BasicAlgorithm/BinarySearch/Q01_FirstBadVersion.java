package GuChengAlgorithm.Ch02_BasicAlgorithm.BinarySearch;

public class Q01_FirstBadVersion {
    // https://docs.google.com/presentation/d/1w3FRwGmgbJBQP8odS5-TLlkheGm3c9U44I8hNdhexGo/edit#slide=id.g9cebf34a0f_0_23
    public int firstBadVersion1(int n) {
        int left = 1, right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (!isBadVersion(mid)) left = mid + 1;
            else right = mid - 1;
        }

        return left; // mid這邊是最後一個good version
    }


    // 左閉右開，找尋左邊界
    public int firstBadVersion2(int n) {
        int left = 1, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (!isBadVersion(mid)) left = mid + 1;
            else right = mid;
        }

        return left; // left == right，所以return right 也可以
    }


    public int firstBadVersion3(int n) {
        if (n == 1) return isBadVersion(1) ? 1 : -1;
        int left = 1, right = n;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (!isBadVersion(mid)) left = mid;
            else right = mid;
        }

        if (isBadVersion(left)) return left;
        if (isBadVersion(right)) return right;
        return -1;
    }


    // mock api
    private boolean isBadVersion(int mid) {
        return mid == 87;
    }
}
