package WeeklyContest;

import java.util.HashSet;
import java.util.Set;

public class Week_298 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2309.Greatest%20English%20Letter%20in%20Upper%20and%20Lower%20Case/README.md
    public String greatestLetter(String s) {
        Set<Character> ss = new HashSet<>();
        for (char c : s.toCharArray()) {
            ss.add(c);
        }

        for (char a = 'Z'; a >= 'A'; --a) {
            if (ss.contains(a) && ss.contains((char) (a + 32))) {
                return String.valueOf(a);
            }
        }
        return "";
    }

    // 位運算
    public String greatestLetter2(String s) {
        int mask1 = 0, mask2 = 0;  // 用兩個整數分別記錄字符串 s 中出現的小寫字母和大寫字母
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);

            // 進行與運算，得到的結果 mask 的第 i 位表示第 i 個字母的大小寫是否同時出現
            if (Character.isLowerCase(c)) {
                mask1 |= 1 << (c - 'a');
            } else {
                mask2 |= 1 << (c - 'A');
            }
        }

        // 只要獲取 mask 的二進制表示中最高位的 1 的位置，將其轉換為對應的大寫字母即可
        // 如果所有二進制位都不為 1 ，說明不存在大小寫同時出現的字母，返回空字符串
        int mask = mask1 & mask2;
        return mask > 0 ? String.valueOf((char) (31 - Integer.numberOfLeadingZeros(mask) + 'A'))
                : "";
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2310.Sum%20of%20Numbers%20With%20Units%20Digit%20K/README.md
    public int minimumNumbers(int num, int k) {
        /*
        直接可能的答案n
        1.以k結尾的正數可以表示為b=a*10+k(a>=0)
        2.則n個k的和為:∑b=∑ai*10+n*k=num --> ∑ai*10 = num - n*k >= 0 && (num - n*k) % 10 == 0
        從小到大枚舉可能的n的答案,遇到第一個合適的返回即可,范圍為[1,num] 若沒有符合條件的返回-1
         */

        if (num == 0) return 0; // num為0的情況單獨討論

        // num >= k的情況:至少需要一個
        for (int i = 1; i < num; i++) {
            int t = num - k * i;
            if (t >= 0 && t % 10 == 0) return i;
        }
        return -1;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2311.Longest%20Binary%20Subsequence%20Less%20Than%20or%20Equal%20to%20K/README.md
    // 最長二進制子序列必然包含原字符串中所有的 0，
    // 在此基礎上，從右到左遍歷 s ，
    // 若遇到 1 ，判斷子序列能否添加 1 ，使得子序列對應的二進制數字 v <= K
    public int longestSubsequence(String s, int k) {
        int ans = 0, v = 0;
        for (int i = s.length() - 1; i >= 0; --i) {
            if (s.charAt(i) == '0') {
                ++ans;
            } else if (ans < 30 && (v | 1 << ans) <= k) {
                v |= 1 << ans;
                ++ans;
            }
        }
        return ans;
    }

    // O(n) 分類討論 + 貪心
    // https://www.bilibili.com/video/BV1CW4y1k7B3/
    public int longestSubsequence2(String s, int k) {
        // m 是 k 有幾位，整數一共有32位，減掉2進制前導0的數目就是k代表數值的二進制長度
        int n = s.length(), m = 32 - Integer.numberOfLeadingZeros(k);

        // 字符串總位數無法和k二進制位數相等，比它少，說明不可能超過k,直接返回全長
        if (n < m) return n;

        // 剛好是一樣位數的這一位，可能要也可能不要，如果加最高位超出最高位就不要，否則就要
        // 比如 k=110, 如果拿到字符串尾巴是 111，比k大，那只能要2位，如果是101，比k小就能都要
        int ans = Integer.parseInt(s.substring(n - m), 2) <= k ? m : m - 1;

        // 然後看前面的0的數目，把0的數目加起來就是答案
        return ans + (int) s.substring(0, n - m).chars().filter(c -> c == '0').count();
    }


    // https://leetcode.cn/problems/selling-pieces-of-wood/solution/by-endlesscheng-mrmd/
    public long sellingWood(int m, int n, int[][] prices) {
        /*
        線性DP->木塊劈完之後還是一個方方正正的木塊:
        1.狀態定義:f[i][j]表示切割高 i 長 j 木塊最多能賣多少錢?
        2.狀態轉移:要求f[i][j]就要看看有哪幾種分割方法?
            2.1 直接不劈整塊賣 pr[i][j]
            2.2 橫著劈 f[i][j]=max(f[k][j]+f[i-k][j]) k∈[1,i-1]
            2.3 豎著劈 f[i][j]=max(f[i][k]+f[i][j-k]) k∈[1,j-1]
            反過來想一下其實很好懂:對於一塊高 i 寬 j 的木頭
            怎麼由更小的木頭組成?只有兩種方式:橫著拼接和豎著拼接
            那為什麼不同時橫著拼接與豎著拼接?因為沒必要!因為i與j的遍歷均為正序
            比i與j小的的木頭能賣的最大價錢其實已經有了
            不需要分割成4塊,就算你分割成4塊和分割成2塊計算出來的其實是一樣的
            因此歸根到底轉移途徑就3條:橫著切賣,豎著切賣,不切直接賣!
        3.初始化:可以將所有f[i][j]初始化為直接賣的價錢
        4.遍歷順序:i∈[1,m];j∈[1,n];k∈[1,i-1]以及[1,j-1]
        5.返回形式:返回f[m][n]就代表高m 寬 n的木頭經過切割能賣多少錢
         */

        // 將價錢裝入矩陣
        int[][] pr = new int[m + 1][n + 1];
        for (int[] p : prices) pr[p[0]][p[1]] = p[2];

        long[][] f = new long[m + 1][n + 1];
        // 枚舉每個高寬的木頭
        for (int i = 1; i <= m; i++) {  // i∈[1,m]
            for (int j = 1; j <= n; j++) {  // j∈[1,n]
                f[i][j] = pr[i][j]; // 不切直接賣能賣多少錢(不能賣錢的為0)
                for (int k = 1; k <= i - 1; k++) {  // 橫著切
                    f[i][j] = Math.max(f[i][j], f[k][j] + f[i - k][j]);
                }
                for (int k = 1; k <= j - 1; k++) {  // 豎著切
                    f[i][j] = Math.max(f[i][j], f[i][k] + f[i][j - k]);
                }
            }
        }
        return f[m][n];
    }


    // 兩處優化點：
    // 1. 根據對稱性，內層循環枚舉到一半的位置即可；
    // 2. 注意到我們是從小往大計算 f 的，可以直接將 prices 記錄到 f 中，而不會影響每個 f[i][j] 的計算。
    public long sellingWood2(int m, int n, int[][] prices) {
        long[][] f = new long[m + 1][n + 1];
        for (int[] p : prices) f[p[0]][p[1]] = p[2];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= j / 2; k++) f[i][j] = Math.max(f[i][j], f[i][k] + f[i][j - k]); // 垂直切割
                for (int k = 1; k <= i / 2; k++) f[i][j] = Math.max(f[i][j], f[k][j] + f[i - k][j]); // 水平切割
            }
        return f[m][n];
    }
}
