package EndlessCheng.GenreMenu.Backtracking.NormalTree.TimeStamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAnswer {

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
        // dfsStr 和 t 的下標轉換關系：
        // (dfsStr_i+1)*2 = ti
        // ti/2-1 = dfsStr_i
        // ti 為偶數，對應奇回文串（從 2 開始）
        // ti 為奇數，對應偶回文串（從 3 開始）
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
        int boxM = 0;
        int boxR = 0;
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
        // 這一結論可用在下面的判斷回文串中

        boolean[] ans = new boolean[n];
        for (int i = 0; i < n; i++) {
            // 判斷左閉右開區間 [l,r) 是否為回文串  0<=l<r<=n
            // 根據下標轉換關系得到 dfsStr 的 [l,r) 子串在 t 中對應的回文中心下標為 l+r+1
            // 需要滿足 halfLen[l + r + 1] - 1 >= r - l，即 halfLen[l + r + 1] > r - l
            int l = nodes[i][0];
            int r = nodes[i][1];
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
