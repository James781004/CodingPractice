package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Week_247 {
    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1913.Maximum%20Product%20Difference%20Between%20Two%20Pairs/README.md
    public int maxProductDifference(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        return (nums[n - 1] * nums[n - 2]) - (nums[0] * nums[1]);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1914.Cyclically%20Rotating%20a%20Grid/README.md
    // 思路：https://leetcode.cn/problems/cyclically-rotating-a-grid/solutions/846885/java-si-lu-ju-jian-dan-fen-zu-xuan-zhuan-0rqj/
    public int[][] rotateGrid(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int s1 = 0, e1 = 0;
        int s2 = m - 1, e2 = n - 1;
        while (s1 <= s2 && e1 <= e2) {
            rotate(grid, s1++, e1++, s2--, e2--, k);
        }
        return grid;
    }

    private void rotate(int[][] grid, int s1, int e1, int s2, int e2, int k) {
        // 把每一層的元素取出來，放到數組中
        List<Integer> t = new ArrayList<>();
        for (int j = e2; j > e1; --j) {
            t.add(grid[s1][j]);
        }
        for (int i = s1; i < s2; ++i) {
            t.add(grid[i][e1]);
        }
        for (int j = e1; j < e2; ++j) {
            t.add(grid[s2][j]);
        }
        for (int i = s2; i > s1; --i) {
            t.add(grid[i][e2]);
        }
        int n = t.size();
        k %= n;
        if (k == 0) {
            return;
        }

        // 取出對應位置的數
        k = n - k;
        for (int j = e2; j > e1; --j) {
            grid[s1][j] = t.get(k);
            k = (k + 1) % n;
        }
        for (int i = s1; i < s2; ++i) {
            grid[i][e1] = t.get(k);
            k = (k + 1) % n;
        }
        for (int j = e1; j < e2; ++j) {
            grid[s2][j] = t.get(k);
            k = (k + 1) % n;
        }
        for (int i = s2; i > s1; --i) {
            grid[i][e2] = t.get(k);
            k = (k + 1) % n;
        }
    }


    // https://leetcode.cn/problems/number-of-wonderful-substrings/solutions/846871/qian-zhui-he-chang-jian-ji-qiao-by-endle-t57t/
    public long wonderfulSubstrings(String word) {
        int[] counter = new int[1 << 10];
        counter[0] = 1;  // 初始前綴和為 0，需將其計入出現次數
        int state = 0;
        long ans = 0;
        for (char c : word.toCharArray()) {
            // 1<<i表示1左移i位，此時求出來的數值為當前前綴和
            state ^= (1 << (c - 'a'));

            // 加上所有出現偶數次的字符串的次數；首先abb中的a對應二進制001，遍歷到第一個b時對應的二進制為011，
            // 遍歷到第二個b時，異或得到的二進制為001，也就是當前字符出現偶數次
            ans += counter[state];

            // 枚舉10個字符出現奇數次的情況
            for (int i = 0; i < 10; ++i) { // 枚舉其中一個字母出現奇數次
                // 某個字符出現奇數次，那麼10位二進制數的第i位為1，也就是1<<i
                // 設某位為1，其余位為0的前綴和為x，那麼x^pre=1<<i ==> x=(x^pre)^pre=(1<<i)^pre
                // 也就是說某位1，其余位為0的前綴和x= (1<<i)^pre
                ans += counter[state ^ (1 << i)];
            }
            ++counter[state]; // 更新前綴和出現次數
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-ways-to-build-rooms-in-an-ant-colony/solutions/2258856/javadfslin-jie-biao-ji-yi-hua-sou-suo-zu-zaun/
    int MOD = (int) 1e9 + 7;
    //以當前節點為根的有向圖的節點數量,記憶化搜索
    int[] subTreeCount;
    //鄰接表
    List<Integer>[] g;

    public int waysToBuildRooms(int[] prevRoom) {
        int n = prevRoom.length;
        g = new List[n];
        for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
        for (int i = 1; i < n; i++) g[prevRoom[i]].add(i);

        subTreeCount = new int[n];
        Arrays.fill(subTreeCount, -1);
        for (int i = 0; i < n; i++) {
            getSubTreeCount(i);
            // System.out.println("sub count of " + i + ":" + subTreeCount[i]);
        }
        long res = 1L;
        // 先計算(n-1)!
        for (int i = 1; i < n; i++) {
            res *= i;
            res %= MOD;
        }
        // 計算每個可能計數的逆元, nF(i) 是 i的逆元, 即(i)^{MOD-2}%MOD
        long[] nF = new long[n];
        for (int i = 1; i < n; i++) nF[i] = pow(i, MOD - 2);
        for (int i = 1; i < n; i++) {
            res *= nF[subTreeCount[i]];
            res %= MOD;
        }
        return (int) res;
    }

    public int getSubTreeCount(int current) {
        if (subTreeCount[current] != -1) return subTreeCount[current];
        if (g[current].size() == 0) {
            subTreeCount[current] = 1;
            return subTreeCount[current];
        }
        int total = 1;
        for (int next : g[current]) total += getSubTreeCount(next);
        subTreeCount[current] = total;
        return subTreeCount[current];
    }

    // 計算a^b%MOD
    private long pow(long a, int b) {
        long res = 1;
        long a1 = a;
        while (b > 0) {
            if ((b & 1) > 0) res = (res * a1) % MOD;
            a1 = (a1 * a1) % MOD;
            b = b / 2;
        }
        return res;
    }
}


