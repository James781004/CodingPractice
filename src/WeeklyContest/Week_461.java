package WeeklyContest;

public class Week_461 {

    // https://leetcode.cn/problems/trionic-array-i/solutions/3741036/yi-ci-bian-li-pythonjavacgo-by-endlessch-ollu/
    public boolean isTrionic(int[] nums) {
        int n = nums.length;
        // 第一段
        int i = 1;
        while (i < n && nums[i - 1] < nums[i]) {
            i++;
        }
        if (i == 1) { // 第一段至少要有兩個數
            return false;
        }

        // 第二段
        int i0 = i;
        while (i < n && nums[i - 1] > nums[i]) {
            i++;
        }
        if (i == i0 || i == n) { // 第二段至少要有兩個數，第三段至少要有兩個數
            return false;
        }

        // 第三段
        while (i < n && nums[i - 1] < nums[i]) {
            i++;
        }
        return i == n;
    }


    // https://leetcode.cn/problems/maximum-balanced-shipments/solutions/3741018/hua-fen-xing-tan-xin-nao-jin-ji-zhuan-wa-6zbk/
    public int maxBalancedShipments(int[] weight) {
        int ans = 0;
        for (int i = 1; i < weight.length; i++) {
            if (weight[i - 1] > weight[i]) {
                ans++;
                i++; // 每個裝運至少要有兩個包裹
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-time-to-activate-string/solutions/3741028/er-fen-da-an-pythonjavacgo-by-endlessche-6s8n/
    public int minTime(String s, int[] order, int k) {
        int n = s.length();
        if ((long) n * (n + 1) / 2 < k) {
            return -1;
        }

        int[] star = new int[n]; // 避免在二分內部反復創建/初始化列表
        int left = -1;
        int right = n;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (check(mid, k, order, star)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(int m, int k, int[] order, int[] star) {
        m++;
        for (int j = 0; j < m; j++) {
            star[order[j]] = m;
        }
        int cnt = 0;
        int last = -1; // 上一個 '*' 的位置
        for (int i = 0; i < star.length; i++) {
            if (star[i] == m) { // s[i] 是 '*'
                last = i;
            }
            cnt += last + 1;
            if (cnt >= k) { // 提前退出循環
                return true;
            }
        }
        return false;
    }

    // https://leetcode.cn/problems/trionic-array-ii/solutions/3741020/fen-zu-xun-huan-on-shi-jian-o1-kong-jian-ewr5/
    public long maxSumTrionic(int[] nums) {
        int n = nums.length;
        long ans = Long.MIN_VALUE;
        for (int i = 0; i < n; ) {
            // 第一段
            int start = i;
            for (i++; i < n && nums[i - 1] < nums[i]; i++) ;
            if (i == start + 1) { // 第一段至少要有兩個數
                continue;
            }

            // 第二段
            int peak = i - 1;
            long res = nums[peak - 1] + nums[peak]; // 第一段的最後兩個數必選
            for (; i < n && nums[i - 1] > nums[i]; i++) {
                res += nums[i]; // 第二段的所有元素必選
            }
            if (i == peak + 1 || i == n) { // 第二段至少要有兩個數，第三段至少要有兩個數
                continue;
            }

            // 第三段
            int bottom = i - 1;
            res += nums[i]; // 第三段的前兩個數必選（第一個數在上面的循環中加了）
            // 從第三段的第三個數往右，計算最大元素和
            long maxS = 0;
            long s = 0;
            for (i++; i < n && nums[i - 1] < nums[i]; i++) {
                s += nums[i];
                maxS = Math.max(maxS, s);
            }
            res += maxS;

            // 從第一段的倒數第三個數往左，計算最大元素和
            maxS = 0;
            s = 0;
            for (int j = peak - 2; j >= start; j--) {
                s += nums[j];
                maxS = Math.max(maxS, s);
            }
            res += maxS;
            ans = Math.max(ans, res);

            i = bottom; // 第三段的起點也是下一個極大三段式子數組的第一段的起點
        }
        return ans;
    }


}









