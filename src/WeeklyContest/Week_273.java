package WeeklyContest;

import java.util.*;

class Week_273 {
    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2119.A%20Number%20After%20a%20Double%20Reversal/README.md
    public boolean isSameAfterReversals(int num) {
        return num == 0 || num % 10 != 0;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2120.Execution%20of%20All%20Suffix%20Instructions%20Staying%20in%20a%20Grid/README.md
    public int[] executeInstructions(int n, int[] startPos, String s) {
        int m = s.length();
        int[] ans = new int[m];
        Map<Character, int[]> mp = new HashMap<>(4);
        mp.put('L', new int[]{0, -1});
        mp.put('R', new int[]{0, 1});
        mp.put('U', new int[]{-1, 0});
        mp.put('D', new int[]{1, 0});
        for (int i = 0; i < m; i++) {
            int x = startPos[0], y = startPos[1];
            int t = 0;

            // 控制起始移動字串位置(後綴)
            for (int j = i; j < m; j++) {
                char c = s.charAt(j);
                int a = mp.get(c)[0], b = mp.get(c)[1];

                // 每次移動一格直到碰壁，每次移動後t++
                if (0 <= x + a && x + a < n && 0 <= y + b && y + b < n) {
                    x += a;
                    y += b;
                    t++;
                } else {
                    break;
                }
            }
            ans[i] = t;
        }
        return ans;
    }


    // https://leetcode.cn/problems/intervals-between-identical-elements/solution/on-zuo-fa-ha-xi-biao-mei-ju-xiang-tong-y-f5yb/
    public long[] getDistances(int[] arr) {
        /*
        HashMap+前綴和
        1.用HashMap將arr[i]值對應索引存儲起來
        2.由於每個數字可能對應多個索引，因此利用前綴和進行優化
        3.假設現在arr[i]=3的索引i有5個[2,5,6,8,10]
            那麼其中6對應的間隔和為:(相當於一部分階梯欠缺，一部分突出)
            3.1 sum[i+1,n-1]-(n-i-1)*nums[i] -> 右邊部分貢獻
            3.2 i*nums[i]-sum[0,i-1] ->左邊部分貢獻
        兩部分貢獻的和就是索引為6處的間隔和(前提是要將索引排序好)
        時間復雜度:O(N) 空間復雜度:O(N)
         */
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int n = arr.length;
        long[] res = new long[n];
        for (int i = 0; i < n; i++) {
            if (!map.containsKey(arr[i])) map.put(arr[i], new ArrayList<>());
            map.get(arr[i]).add(i); // 記錄相同元素的位置
        }
        for (Integer key : map.keySet()) {
            List<Integer> list = map.get(key);

            // 只有一個索引間隔和必定為0，跳過
            int size = list.size();
            if (size == 1) continue;

            // 注意int可能會溢出
            long[] sum = new long[size + 1];
            for (int i = 0; i < size; i++) {
                sum[i + 1] = sum[i] + list.get(i);
            }
            for (int i = 0; i < size; i++) {
                int curIdx = list.get(i);
                long l = (long) i * curIdx - sum[i];
                long r = (sum[size] - sum[i + 1]) - (long) (size - i - 1) * curIdx;
                res[curIdx] = l + r;    // 寫入結果
            }
        }
        return res;
    }


    // https://leetcode.cn/problems/recover-the-original-array/solution/mei-ju-higher0-shuang-zhi-zhen-by-endles-ic64/
    public int[] recoverArray(int[] nums) {
        Arrays.sort(nums); // 將 nums 排序後，lower[0] 必然是 nums[0]
        int m = nums.length;
        int[] ans = new int[m / 2];
        for (int i = 1; i < m; i++) {
            // nums[0] 必定是第一個值，用nums中的其他值枚舉
            int diff = nums[i] - nums[0];

            // diff不為奇數和0
            if (diff % 2 == 1 || diff == 0) continue;

            // 在 nums 中枚舉 higher[0] 的值
            // 從而得到 k == (higher[0] - lower[0]) / 2
            int k = diff / 2;
            ans[0] = nums[0] + nums[i] >> 1;

            // 數組下標
            int idx = 1;

            // l從1開始
            int l = 1;

            // r從i+1開始 如果nums[i]為目標值的higher，那個下一個值+k 一定比nums[i]大
            int r = i + 1;
            boolean[] visited = new boolean[m];
            visited[i] = true;

            while (r < m) {
                // i+1後是沒有被遍歷過的，r遍歷過的不能遍歷，l遍歷過的在l和r前面
                while (l < m && visited[l]) l++;
                // 需要大於2*k才可能
                // r不可能等於l
                while (r < m && nums[r] - nums[l] < 2 * k) r++;
                // 邊界 或者 大於2*k 則無效
                if (r == m || nums[r] - nums[l] > 2 * k) break;
                visited[r] = true;
                ans[idx] = nums[r] + nums[l] >> 1;
                idx++;
                l++;
                r++;
            }
            // 第一次滿足，直接返回，lower[0]對應的higher[0]肯定是在比較前面的
            if (idx == m / 2) return ans;
        }
        return ans;
    }
}

