package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_420 {

    // https://leetcode.cn/problems/find-the-sequence-of-strings-appeared-on-the-screen/solutions/2957770/mo-ni-pythonjavacgo-by-endlesscheng-7tcy/
    List<String> stringSequence(String target) {
        List<String> ans = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        for (int c : target.toCharArray()) {
            s.append('a');
            for (char j = 'a'; j <= c; j++) {
                s.setCharAt(s.length() - 1, j);
                ans.add(s.toString());
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-substrings-with-k-frequency-characters-i/solutions/2957691/on-hua-dong-chuang-kou-pythonjavacgo-by-1xgqm/
    int numberOfSubstrings(String S, int k) {
        char[] s = S.toCharArray();
        int ans = 0;
        int left = 0;
        int[] cnt = new int[26];
        for (char c : s) {
            cnt[c - 'a']++;

            //  cnt[c - 'a']++ 導致了 cnt[c - 'a'] 達到 k，
            //  所以其余字母的出現次數必然小於 k，無需判斷
            while (cnt[c - 'a'] >= k) {
                cnt[s[left] - 'a']--;
                left++;
            }
            ans += left;
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-division-operations-to-make-array-non-decreasing/solutions/2957768/yu-chu-li-lpf-cong-you-dao-zuo-tan-xin-p-k3gt/
    private static final int MX = 1_000_001;
    private static final int[] lpf = new int[MX];

    // 預處理最小質因子
    static {
        for (int i = 2; i < MX; i++) {
            if (lpf[i] == 0) {
                for (int j = i; j < MX; j += i) {
                    if (lpf[j] == 0) {
                        lpf[j] = i;
                    }
                }
            }
        }
    }

    public int minOperations(int[] nums) {
        int ans = 0;

        // 最後一個數肯定無需減少，所以從 n−2 開始倒著遍歷
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1]) {
                nums[i] = lpf[nums[i]]; // 操作除以最大真因數後，就變為最小質因子
                if (nums[i] > nums[i + 1]) {
                    return -1; // 操作無法把 nums 變成非遞減，返回 −1
                }
                ans++; // 操作次數加一
            }
        }
        return ans;
    }

    // https://leetcode.cn/problems/check-if-dfs-strings-are-palindromes/solutions/2957704/mo-ban-dfs-shi-jian-chuo-manacher-suan-f-ttu6/
    private int time = 0;

    public boolean[] findAnswer(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 1; i < n; i++) {
            int p = parent[i];
            // 由於 i 是遞增的，所以 g[p] 必然是有序的，下面無需排序
            g[p].add(i);
        }

        // dfsStr 是後序遍歷整棵樹得到的字符串
        char[] dfsStr = new char[n];
        // nodes[i] 表示子樹 i 的後序遍歷的開始時間戳和結束時間戳+1（左閉右開區間）
        int[][] nodes = new int[n][2];
        dfs(0, g, s.toCharArray(), dfsStr, nodes);

        // Manacher 模板
        // 將 dfsStr 改造為 t，這樣就不需要討論 n 的奇偶性，因為新串 t 的每個回文子串都是奇回文串（都有回文中心）
        char[] t = new char[n * 2 + 3];
        Arrays.fill(t, '#');
        t[0] = '^';
        for (int i = 0; i < n; i++) {
            t[i * 2 + 2] = dfsStr[i];
        }
        t[n * 2 + 2] = '$';

        // 定義一個奇回文串的回文半徑=(長度+1)/2，即保留回文中心，去掉一側後的剩余字符串的長度
        // halfLen[i] 表示在 t 上的以 t[i] 為回文中心的最長回文子串的回文半徑
        // 即 [i-halfLen[i]+1,i+halfLen[i]-1] 是 t 上的一個回文子串
        int[] halfLen = new int[t.length - 2];
        halfLen[1] = 1;
        // boxR 表示當前右邊界下標最大的回文子串的右邊界下標+1
        // boxM 為該回文子串的中心位置，二者的關系為 r=mid+halfLen[mid]
        int boxM = 0, boxR = 0;
        for (int i = 2; i < halfLen.length; i++) { // 循環的起止位置對應著原串的首尾字符
            int hl = 1;
            if (i < boxR) {
                // 記 i 關於 boxM 的對稱位置 i'=boxM*2-i
                // 若以 i' 為中心的最長回文子串范圍超出了以 boxM 為中心的回文串的范圍（即 i+halfLen[i'] >= boxR）
                // 則 halfLen[i] 應先初始化為已知的回文半徑 boxR-i，然後再繼續暴力匹配
                // 否則 halfLen[i] 與 halfLen[i'] 相等
                hl = Math.min(halfLen[boxM * 2 - i], boxR - i);
            }
            // 暴力擴展
            // 算法的復雜度取決於這部分執行的次數
            // 由於擴展之後 boxR 必然會更新（右移），且擴展的的次數就是 boxR 右移的次數
            // 因此算法的復雜度 = O(len(t)) = O(n)
            while (t[i - hl] == t[i + hl]) {
                hl++;
                boxM = i;
                boxR = i + hl;
            }
            halfLen[i] = hl;
        }

        // t 中回文子串的長度為 hl*2-1
        // 由於其中 # 的數量總是比字母的數量多 1
        // 因此其在 dfsStr 中對應的回文子串的長度為 hl-1
        // 這一結論可用在 isPalindrome 中

        // 判斷左閉右開區間 [l,r) 是否為回文串  0<=l<r<=n
        // 根據下標轉換關系得到 dfsStr 的 [l,r) 子串在 t 中對應的回文中心下標為 l+r+1
        boolean[] ans = new boolean[n];
        for (int i = 0; i < n; i++) {
            int l = nodes[i][0], r = nodes[i][1];
            ans[i] = halfLen[l + r + 1] > r - l;
        }
        return ans;
    }

    private void dfs(int x, List<Integer>[] g, char[] s, char[] dfsStr, int[][] nodes) {
        nodes[x][0] = time;
        for (int y : g[x]) {
            dfs(y, g, s, dfsStr, nodes);
        }
        dfsStr[time++] = s[x]; // 後序遍歷
        nodes[x][1] = time;
    }


}






