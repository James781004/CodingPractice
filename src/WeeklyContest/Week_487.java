package WeeklyContest;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class Week_487 {

    // https://leetcode.cn/problems/count-monobit-integers/solutions/3893641/o1-yi-xing-dai-ma-pythonjavacgo-by-endle-ew9k/
    public int countMonobit(int n) {
        // 非負整數 x 二進制的所有位都相同，
        // 意味著 x=0 或者 x 的二進制全為 1，即 x=2^k −1，其中 k 是非負整數
        // 問題變成求 [0,n] 中有多少個 2^k −1，或者說有多少個不同的 k
        return 32 - Integer.numberOfLeadingZeros(n + 1);
    }


    // https://leetcode.cn/problems/final-element-after-subarray-deletions/solutions/3893639/nao-jin-ji-zhuan-wan-yan-ge-zheng-ming-p-vjzr/
    public int finalElement(int[] nums) {
        return Math.max(nums[0], nums[nums.length - 1]);
    }


    // https://leetcode.cn/problems/design-ride-sharing-system/solutions/3893657/dui-lie-mo-ni-jun-tan-o1-zuo-fa-pythonja-yqut/
    class RideSharingSystem {
        private final Deque<Integer> riders = new ArrayDeque<>();
        private final Deque<Integer> drivers = new ArrayDeque<>();
        private final Set<Integer> waitingEiders = new HashSet<>();

        public void addRider(int riderId) {
            riders.addLast(riderId);
            waitingEiders.add(riderId);
        }

        public void addDriver(int driverId) {
            drivers.addLast(driverId);
        }

        public int[] matchDriverWithRider() {
            // 彈出隊列中的已取消乘客
            while (!riders.isEmpty() && !waitingEiders.contains(riders.peekFirst())) {
                riders.pollFirst();
            }
            // 沒有乘客或者司機
            if (riders.isEmpty() || drivers.isEmpty()) {
                return new int[]{-1, -1};
            }
            // 配對（這裡沒有刪除 waitingEiders 中的乘客編號，面試的話建議寫上刪除的邏輯）
            return new int[]{drivers.pollFirst(), riders.pollFirst()};
        }

        public void cancelRider(int riderId) {
            waitingEiders.remove(riderId);
        }
    }


    // https://leetcode.cn/problems/longest-alternating-subarray-after-removing-at-most-one-element/solutions/3893643/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-ml47/
    public int longestAlternating(int[] nums) {
        int n = nums.length;
        int[] pre = calc(nums); // pre[i] 表示以 i 結尾的最長交替子數組的長度

        reverse(nums);
        int[] suf = calc(nums); // suf[i] 表示以 i 開頭的最長交替子數組的長度
        reverse(suf);
        reverse(nums);

        // 不刪除元素時的最長交替子數組的長度
        int ans = 0;
        for (int x : pre) {
            ans = Math.max(ans, x);
        }

        // 枚舉刪除 nums[i]
        for (int i = 1; i < n - 1; i++) {
            if (nums[i - 1] == nums[i + 1]) { // 無法拼接
                continue;
            }

            // 計算 (i-2,i-1), (i-1,i+1), (i+1,i+2) 的大小關系
            int x = i > 1 ? Integer.compare(nums[i - 2], nums[i - 1]) : 0;
            int y = Integer.compare(nums[i - 1], nums[i + 1]);
            int z = i < n - 2 ? Integer.compare(nums[i + 1], nums[i + 2]) : 0;

            if (x == -y && x == z) { // 左右兩邊可以拼接
                ans = Math.max(ans, pre[i - 1] + suf[i + 1]);
            } else {
                if (x == -y) {
                    ans = Math.max(ans, pre[i - 1] + 1); // 只拼接 nums[i+1]
                }
                if (z == -y) {
                    ans = Math.max(ans, suf[i + 1] + 1); // 只拼接 nums[i-1]
                }
            }
        }

        return ans;
    }

    private int[] calc(int[] a) {
        int n = a.length;
        int[] f = new int[n]; // f[i] 表示以 i 結尾的最長交替子數組的長度
        for (int i = 0; i < n; i++) {
            if (i == 0 || a[i - 1] == a[i]) {
                f[i] = 1;
            } else if (i > 1 && a[i - 2] != a[i - 1] && (a[i - 2] < a[i - 1]) == (a[i - 1] > a[i])) {
                f[i] = f[i - 1] + 1;
            } else {
                f[i] = 2;
            }
        }
        return f;
    }

    private void reverse(int[] a) {
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
            i++;
            j--;
        }
    }


}









