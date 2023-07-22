package WeeklyContest;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

class Week_255 {
    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1979.Find%20Greatest%20Common%20Divisor%20of%20Array/README.md
    public int findGCD(int[] nums) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }
        return gcd(max, min);
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    // https://leetcode.cn/problems/find-unique-binary-string/solution/bao-li-mei-ju-ha-xi-biao-by-soldier6-lgdz/
    public String findDifferentBinaryString(String[] nums) {
        int len = nums[0].length();
        Set<String> set = new HashSet<>();
        for (String num : nums) {
            set.add(num); // 將數組中字符串存在哈希表中，去重
        }

        // 通過字串的長度n可以得到可能的整數範圍 0 -- 2^n-1,
        // 利用整數生成對應的二進制字符串，再判斷哈希表中是否存在。
        for (int i = 0; i < Math.pow(2, len); i++) {
            StringBuilder sb = new StringBuilder();
            int temp = i;
            for (int j = 0; j < len; j++) {
                if ((temp & 1) == 1) {
                    sb.append(1);
                } else {
                    sb.append(0);
                }
                temp >>= 1;
            }
            if (!set.contains(sb.toString())) {
                return sb.toString();
            }
        }
        return null;
    }


    // https://leetcode.cn/problems/minimize-the-difference-between-target-and-chosen-elements/solution/5852-zui-xiao-hua-mu-biao-zhi-yu-suo-xua-xcxs/
    int m, n, target;
    int ans = Integer.MAX_VALUE;
    int[][] mat;
    boolean[][] dp;

    public int minimizeTheDifference(int[][] _mat, int _target) {
        target = _target;
        mat = _mat;
        n = mat.length;
        m = mat[0].length; // n，m記錄大小
        dp = new boolean[71][5000]; // 題目有設定範圍，這邊記憶化記錄第i行總和為j之前是否來過
        dfs(0, 0);
        return ans;
    }

    private void dfs(int i, int now) {
        if (i == n) { // 搜索完畢，更新ans
            ans = Math.min(ans, Math.abs(now - target));
            return;
        }

        // 如果以前來過或者當前差值已經大於ans，剪枝
        if (now - target > ans || dp[i][now]) return;
        dp[i][now] = true;
        for (int j = 0; j < m; j++) { // 往下繼續搜索
            dfs(i + 1, now + mat[i][j]);
        }
    }


    // https://github.com/doocs/leetcode/tree/main/solution/1900-1999/1981.Minimize%20the%20Difference%20Between%20Target%20and%20Chosen%20Elements
    // 動態規劃（分組背包）
    public int minimizeTheDifferenceDP(int[][] mat, int target) {

        // 原本用二維 f[i][j] 表示前 i 行是否能選出元素和為 j
        // 這邊使用滾動數組優化，用set保存每一層所有的累計和
        Set<Integer> f = new HashSet<>();
        f.add(0);
        for (int[] row : mat) {
            Set<Integer> g = new HashSet<>();
            for (int a : f) {
                for (int b : row) {
                    g.add(a + b);
                }
            }
            f = g; // 更新本層累計結果，滾動到下層
        }

        // 找出最小的絕對差
        int ans = Integer.MAX_VALUE;
        for (int v : f) {
            ans = Math.min(ans, Math.abs(v - target));
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/tree/main/solution/1900-1999/1982.Find%20Array%20Given%20Subset%20Sums
    // https://leetcode.cn/problems/find-array-given-subset-sums/solution/ti-jie-cong-zi-ji-de-he-huan-yuan-shu-zu-q9qw/

    /**
     * 如果原數組中的所有元素都是非負的
     * 1.<關鍵1>：在sums中除去0之後最小的元素就原數組中最小的數
     * 除去0的意思是，除去一個元素都不選的case
     * 然後就剩下兩類sum：
     * a.單個數構成的sum, 對應state中有僅有1個二進制的1
     * b.至少兩個數構成的sum，對應的state中有2個以上的二進制的1
     * 顯然是a類 < b類，a類中最小的那個（也是全局最小）就是最小元素
     * *
     * 2.在sums中除去最小數之後, sums中還剩兩類sum：
     * a.除最小數之外，單個數構成的sum
     * b.至少兩個數構成的sum（可能還有最小數在）
     * 顯然還剩a類 < b類，a類中最小的那個（也是全局最小）就是未確定的最小元素
     * *
     * 3.在sums繼續除去剛才確定的次小元素
     * a.除最小的兩個數之外，單個數構成的sum
     * b.至少兩個數構成的sum，但注意這樣的剔除方式，還會有最小的兩個數組合成的sum存在
     * 此時無法確定 a類 < b類，因為有可能兩個最小元素只和 < 第3小的元素
     * <關鍵2>：所以sums中的剔除元素方式應該改為剔除 <已經確定了的元素> 的所有子集的和
     * 這樣剔除之後
     * a.還是除最小的兩個數之外，單個數構成的sum
     * b.變為 單個元素 + 至少一個其它元素構成的sum
     * 並且單個元素雖然可以取最小的兩個，但是其它元素至少要取到第三小的元素，因為最小的兩個元素之和已經被刪除了
     * 這種情況下就能確定 a類 < b類，a類中最小的那個（也是全局最小）就是未確定的最小元素
     * *
     * 4.同3，將sums中剔除 <已經確定了的元素> 即最小三個數的所有子集的和
     * a.除最小的3個數之外，單個數構成的sum
     * b.單個元素 + 至少一個其它元素構成的sum
     * 單個元素雖然可以取最小的3個，但是和他們3搭配的，至少是第四小的元素
     * 注意最小三數之和也被剔除了
     * a類 < b類，確定了第四小的元素
     * *
     * <關鍵3>：以此類推，每次剔除以知元素的子序和，剩余sums中最小值，就是未確定元素中最小的那個
     * *
     * *
     * 但本題中，元素還有負數
     * <關鍵4> sums中的最小值 t，是所有負數之和
     * 如果sums中的所有元素加上 -t，相當於每一個子數組和中選了負數的，變為不選
     * 沒有選負數的，變為選其相反數
     * 從子序列狀態的角度，就是負數對應位置，原本是1表示選擇負數的，現在變為0，表示不選
     * 原本是0表示不選負數的，現在變為1，表示選其相反數
     * <關鍵5> 於是整個狀態集合就表示 <原數組絕對值的子序列>
     * 因此用剛才的方式求出 原數組的絕對值數組
     * *
     * 絕對值數組選擇一些元素變為負數，同樣用狀態壓縮來實現
     * 如果某一個狀態對應的情況下，負數之和剛好為最小值 t，就找到了答案
     */

    public int[] recoverArray(int n, int[] sums) {
        int m = 1 << 30;
        for (int x : sums) {
            m = Math.min(m, x);
        }
        m = -m;
        TreeMap<Integer, Integer> tm = new TreeMap<>();
        for (int x : sums) {
            tm.merge(x + m, 1, Integer::sum);
        }
        int[] ans = new int[n];
        if (tm.merge(0, -1, Integer::sum) == 0) {
            tm.remove(0);
        }
        ans[0] = tm.firstKey();
        for (int i = 1; i < n; ++i) {
            for (int j = 0; j < 1 << i; ++j) {
                if ((j >> (i - 1) & 1) == 1) {
                    int s = 0;
                    for (int k = 0; k < i; ++k) {
                        if (((j >> k) & 1) == 1) {
                            s += ans[k];
                        }
                    }
                    if (tm.merge(s, -1, Integer::sum) == 0) {
                        tm.remove(s);
                    }
                }
            }
            ans[i] = tm.firstKey();
        }
        for (int i = 0; i < 1 << n; ++i) {
            int s = 0;
            for (int j = 0; j < n; ++j) {
                if (((i >> j) & 1) == 1) {
                    s += ans[j];
                }
            }
            if (s == m) {
                for (int j = 0; j < n; ++j) {
                    if (((i >> j) & 1) == 1) {
                        ans[j] *= -1;
                    }
                }
                break;
            }
        }
        return ans;
    }
}

