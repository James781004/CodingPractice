package WeeklyContest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Week_240 {
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1854.Maximum%20Population%20Year/README.md
    public int maximumPopulation(int[][] logs) {
        int[] d = new int[101];  // 模擬年份範圍 [1950...2050]
        final int offset = 1950;
        for (int[] log : logs) { // 數飛機算法：起飛時間+1，降落時間-1，最後算時間區前天上多少飛機
            int a = log[0] - offset;
            int b = log[1] - offset;
            ++d[a];
            --d[b];
        }

        // 遍歷數組 d ，求出前綴和的最大值，即為人口最多的年份，
        // 再加上 offset (1950) 即為答案
        int s = 0, mx = 0;
        int j = 0;
        for (int i = 0; i < d.length; ++i) {
            s += d[i];
            if (mx < s) {
                mx = s;
                j = i;
            }
        }
        return j + offset;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1855.Maximum%20Distance%20Between%20a%20Pair%20of%20Values/README.md
    // 方法一：二分查找
    // 題目給兩個 非遞增 的整數數組，符合單調性，可以使用二分查找
    // 遍歷數組 nums1，對於每個數字 nums1[i]，二分查找 nums2 在 [i, n) 范圍內的數字，
    // 找到最後一個大於等於 nums1[i] 的位置，計算此位置與 i 的距離，並更新最大距離值 ans。
    public int maxDistance(int[] nums1, int[] nums2) {
        int ans = 0;
        int m = nums1.length, n = nums2.length;
        for (int i = 0; i < m; ++i) {
            int left = i, right = n - 1;
            while (left < right) {
                int mid = (left + right + 1) >> 1;
                if (nums2[mid] >= nums1[i]) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            }
            ans = Math.max(ans, left - i); // 計算合法位置與 i 的距離
        }
        return ans;
    }


    // 方法二：雙指針
    public int maxDistance2(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int ans = 0;
        for (int i = 0, j = 0; i < m; ++i) {
            while (j < n && nums1[i] <= nums2[j]) {
                ++j; // 模擬題目條件把 j 走到底
            }
            ans = Math.max(ans, j - i - 1); // 比較最大距離
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-subarray-min-product/solutions/766373/java-qian-zhui-he-dan-diao-zhan-zhu-xing-95ui/
    // 使用前綴和，對數組做一遍預處理
    // 然後使用單調棧（單調遞增） 從左到右，求一遍每個元素右邊第一個小於的元素的 從右到左，
    // 求一遍每個元素左邊第一個小於該元素的 隨後將元素下標在數組中，
    // 和前綴和配合使用，獲得最終解
    public int maxSumMinProduct(int[] nums) {
        int n = nums.length;

        // 數組前綴和
        long[] pre = new long[n + 1];  // 存儲下標“之前”的元素和
        pre[0] = nums[0];
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + nums[i - 1];
        }

        // 單遞增調棧
        Stack<Integer> stack = new Stack<>();
        // 求元素右邊第一個比其小的
        int[] rightLower = new int[n];
        Arrays.fill(rightLower, n);  // 默認為n，即沒發現
        for (int i = 0; i < n; i++) {
            // 單調遞增棧
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                int t = stack.pop();
                rightLower[t] = i;
            }
            stack.push(i);
        }
        // 求元素左邊第一個比其小的
        int[] leftLower = new int[n];
        Arrays.fill(leftLower, -1);  // 默認為-1，即沒發現
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                int t = stack.pop();
                leftLower[t] = i;
            }
            stack.push(i);
        }

        // 在前綴和及單調棧基礎上，求最終解
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int r = rightLower[i];
            int l = leftLower[i] + 1;
            long t = pre[r] - pre[l];
            ans = Math.max(ans, t * nums[i]);
        }
        long mod = (long) 1e9 + 7;
        return (int) (ans % mod);
    }


    // https://leetcode.cn/problems/largest-color-value-in-a-directed-graph/solutions/766070/you-xiang-tu-zhong-zui-da-yan-se-zhi-by-dmtaa/
    public int largestPathValue(String colors, int[][] edges) {
        int length = colors.length(); // length 個節點
        NewNode[] adjList = new NewNode[length]; // 鄰接表
        int[] degree = new int[length]; // 統計節點入度，為後續拓撲排序做准備

        /*** 創建鄰接表adjList，並填充入度表degree ***/
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            degree[to]++; // 一條 from -> to 的邊, 則 to 節點的入度 + 1
            adjList[from] = new NewNode(to, adjList[from]); // 鄰接矩陣
            /** 「頭插法」上面一行等價於下面幾行操作,
             *    NewNode node = new NewNode(to);
             *    to.next = adjList[from];
             *    adjList[from] = to;
             */
        }

        /*** 記錄拓撲排序遇到的節點個數 ***/
        int found = 0;
        Queue<Integer> q = new LinkedList<Integer>();
        int[][] f = new int[length][26]; //f[i][j]: 截止到第 i 個節點，第 j 種顏色的數量
        for (int i = 0; i < length; i++) { // 將入度為 0 的節點加入隊列 q 中
            if (degree[i] == 0) {
                q.offer(i);
            }
        }

        // 開始拓撲排序
        while (!q.isEmpty()) {
            int cur = q.poll();

            found++;
            f[cur][colors.charAt(cur) - 'a']++; // 將節點對應顏色 + 1

            // 遍歷當前節點所有的下一個節點
            for (NewNode next = adjList[cur]; next != null; next = next.next) {
                degree[next.val]--; // 下一個節點入度 - 1

                // 如果節點的入度為 0, 則將其加入隊列 q 中
                if (degree[next.val] == 0) q.offer(next.val);

                // 更新 next 所有顏色數量，即f[next][c] = Math.max(f[cur][c], f[next][c])
                for (int c = 0; c < 26; c++) { // 顏色為小寫字母，共26種
                    f[next.val][c] = Math.max(f[next.val][c], f[cur][c]);
                }
            }
        }

        if (found != length) return -1; // 有環，直接返回 -1

        int res = 0;
        for (int i = 0; i < length; i++) {
            res = Math.max(res, Arrays.stream(f[i]).max().getAsInt());
        }
        return res;
    }


    class NewNode {
        int val;
        NewNode next;

        public NewNode(int val, NewNode next) {
            this.val = val;
            this.next = next;
        }
    }
}



