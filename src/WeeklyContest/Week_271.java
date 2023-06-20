package WeeklyContest;

import java.util.*;

class Week_271 {
    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2103.Rings%20and%20Rods/README.md
    public int countPoints(String rings) {
        Map<Integer, Set<Character>> mp = new HashMap<>();
        for (int i = 1; i < rings.length(); i += 2) {
            int c = rings.charAt(i) - '0';
            mp.computeIfAbsent(c, k -> new HashSet<>()).add(rings.charAt(i - 1));
        }
        int ans = 0;
        for (Set<Character> e : mp.values()) {
            if (e.size() == 3) {
                ans++;
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2104.Sum%20of%20Subarray%20Ranges/README.md
    // 暴力枚舉
    // 循環遍歷 i，作為子數組的起始位置。對於每個 i，遍歷每個 j 作為子數組的終止位置，
    // 此過程中不斷求解子數組的最大值、最小值，然後累加差值到結果 ans 中
    public long subArrayRanges(int[] nums) {
        long ans = 0;
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            int mi = nums[i], mx = nums[i];
            for (int j = i + 1; j < n; ++j) {
                mi = Math.min(mi, nums[j]);
                mx = Math.max(mx, nums[j]);
                ans += (mx - mi);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/sum-of-subarray-ranges/solution/cong-on2-dao-ondan-diao-zhan-ji-suan-mei-o1op/
    // 單調棧
    // 枚舉每個元素 nums[i] 作為最大值出現在了多少個子數組中，以及作為最小值出現在多少個子數組中
    public long subArrayRanges2(int[] nums) {
        long mx = f(nums);

        // 小技巧：所有元素取反後算的就是最小值的貢獻
        // nums[i] 作為最大值的貢獻為正，作為最小值的貢獻為負nums[i]
        for (int i = 0; i < nums.length; i++) {
            nums[i] *= -1;
        }

        long mi = f(nums);
        return mx + mi;
    }

    private long f(int[] nums) {
        Deque<Integer> stk = new ArrayDeque<>();
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(left, -1);
        Arrays.fill(right, n);

        // 找出左側第一個比 nums[i] 大的位置 left[i]
        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && nums[stk.peek()] <= nums[i]) {
                stk.pop();
            }
            if (!stk.isEmpty()) {
                left[i] = stk.peek();
            }
            stk.push(i);
        }
        stk.clear();

        // 找出右側第一個大於等於 nums[i] 的位置 right[i]
        for (int i = n - 1; i >= 0; i--) {
            while (!stk.isEmpty() && nums[stk.peek()] < nums[i]) {
                stk.pop();
            }
            if (!stk.isEmpty()) {
                right[i] = stk.peek();
            }
            stk.push(i);
        }

        // 計算每個 nums[i] 的貢獻
        // 設以 nums[i] 為最大值的子數組為 nums[l..r]
        // nums[i] 可以作為最大值出現在i−left[i])⋅(right[i]−i)個子數組中
        // 最小值的做法同理（貢獻為負數）
        long s = 0;
        for (int i = 0; i < n; i++) {
            s += (long) (i - left[i]) * (right[i] - i) * nums[i];
        }
        return s;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2105.Watering%20Plants%20II/README.md
    // 雙指針直接模擬
    public int minimumRefill(int[] plants, int capacityA, int capacityB) {
        int i = 0, j = plants.length - 1;
        int ans = 0, a = capacityA, b = capacityB;
        while (i <= j) {
            if (i == j) {
                if (Math.max(capacityA, capacityB) < plants[i]) {
                    ans++;
                }
                break;
            }
            if (capacityA < plants[i]) {
                capacityA = a - plants[i]; // 重新灌滿澆水罐後幫植物澆水
                ans++;
            } else {
                capacityA -= plants[i];
            }
            if (capacityB < plants[j]) {
                capacityB = b - plants[j];
                ans++;
            } else {
                capacityB -= plants[j];
            }
            i++;
            j--;
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-fruits-harvested-after-at-most-k-steps/solution/hua-dong-chuang-kou-jian-ji-xie-fa-pytho-1c2d/
    // 由於只能一步步地走，人移動的範圍必然是一段連續的區間。
    // 如果反覆左右移動，會白白浪費移動次數，所以最優方案要麼先向右再向左，要麼先向左再向右（或者向一個方向走到底）。
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int left = lowerBound(fruits, startPos - k); // 向左最遠能到 fruits[left][0]
        int right = left, s = 0, n = fruits.length;
        for (; right < n && fruits[right][0] <= startPos; right++)
            s += fruits[right][1]; // 從 fruits[left][0] 到 startPos 的水果數
        int ans = s;
        for (; right < n && fruits[right][0] <= startPos + k; right++) {
            s += fruits[right][1]; // 枚舉最右位置為 fruits[right][0]

            // 先向右再向左，那麼移動距離為 (fruits[right][0]−startPos)+(fruits[right][0]−fruits[left][0])
            // 先向左再向右，那麼移動距離為 (startPos−fruits[left][0])+(fruits[right][0]−fruits[left][0])
            // 因為for循環是枚舉最右位置right，超過k步的狀況，這邊必須縮減左邊界left
            while (fruits[right][0] * 2 - fruits[left][0] - startPos > k &&
                    fruits[right][0] - fruits[left][0] * 2 + startPos > k)
                s -= fruits[left++][1]; // fruits[left][0] 無法到達

            ans = Math.max(ans, s); // 更新答案最大值
        }
        return ans;
    }

    // 見 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(int[][] fruits, int target) {
        int left = -1, right = fruits.length; // 開區間 (left, right)
        while (left + 1 < right) { // 開區間不為空
            // 循環不變量：
            // fruits[left][0] < target
            // fruits[right][0] >= target
            int mid = (left + right) >>> 1;
            if (fruits[mid][0] < target)
                left = mid; // 範圍縮小到 (mid, right)
            else
                right = mid; // 範圍縮小到 (left, mid)
        }
        return right;
    }
}

