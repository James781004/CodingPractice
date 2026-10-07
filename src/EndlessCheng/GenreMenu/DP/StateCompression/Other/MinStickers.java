package EndlessCheng.GenreMenu.DP.StateCompression.Other;

import java.util.*;

public class MinStickers {

    // https://leetcode.cn/problems/stickers-to-spell-word/solutions/1492923/by-ac_oier-5vv3/
    /**
     * 相當於每一次可以任意選擇1張貼紙，這張貼紙上的部分字符可以為湊出target做出貢獻，問至少需要幾張貼紙，才能湊出target
     */

    int n;

    int[] memo;


    /**
     * 方法一：dfs
     * 需要考慮的是，對於任意一張貼紙，使用當前貼紙後(假設每個字符都做出最大貢獻)，能夠得到什麼樣的字符串
     * 我們最終要得到target，所以我們可以 用 0 或 1表示 target 每一位置上的字符是否已經湊出，因為 target長度在1-15之間，
     * 所以我們可以用1個int變量state完成這件事，
     * 初始時：state = 0，每個字符都沒湊出來
     * 如果能湊出target，最終 state就是 2^(target.length)-1，也就是 target.length()個1，表示每個位置都被湊到了
     * 為了避免某些狀態重復搜索，我們還需要一個memo數組來做備忘錄，因為一共有 2^(target.length) 個狀態（state取值為0-1<<len-1），所以數組大小為 1<< len
     * <p>
     * 那麼，假設當前狀態為state，選擇 某個 貼紙 sticker時，能得到什麼樣的新狀態
     * <p>
     * 遍歷 sticker 的每個字符 s[i]
     * 遍歷 target 的每個字符 t[j]
     * if (s[i] == t[j] && state & (1 << j) == 0) 說明此時t[j]還未被湊出，並且s[i] 能湊出 t[j]，那麼state第j個位置變為1
     * break；s[i]對當前字符做出貢獻後，就該考慮s[i+1]為哪個位置做貢獻了，並且因為state已經更新，所以不會出現s[i+1]仍然為t[j]貢獻的情況
     * 這裡不要先統計s每個字符出現次數，再統計t每個字符出現次數，又逐一比較，更新，這樣是做不到明確target每個位置是否被湊出的，也就是得不到新的state
     * 因為每次可以選擇任意一個貼紙，所以 遍歷s前要准備一個 state的備份nstate，在遍歷過程中使用的也是nstate
     * 並且，如果遍歷完成後，nstate==state，說明當前貼紙對當前狀態已再無貢獻，直接換下一個貼紙
     * 否則·從當前狀態 到 target 所需要的貼紙數 就是 1(使用當前貼紙) + dfs(nstate)
     * <p>
     * 然後從多個選擇的結果中選擇結果最小的（所以，先給一個不可能的結果）
     * <p>
     * 並且，當 state == 1<<n-1時，返回0，
     *
     * @param stickers
     * @param target
     * @return
     */
    public int minStickers(String[] stickers, String target) {
        // 記錄target長度
        n = target.length();
        // 備忘錄，注意初始化大小
        memo = new int[1 << n];
        // 標記備忘錄
        Arrays.fill(memo, -1);
        // 如果湊不出來target，返回的是個不可能的值，這裡用 n + 1代替
        int ans = dfs(stickers, target, 0);
        return ans > n ? -1 : ans;
    }

    /**
     * dfs，當前狀態是 state（二進制第j位為1代表target[j]已經湊出）
     * 返回從當前狀態湊出target需要的最少貼紙數
     *
     * @param stickers
     * @param target
     * @param state
     * @return
     */
    private int dfs(String[] stickers, String target, int state) {
        // target每個字符全部湊出
        if (state == (1 << n) - 1) {
            return 0;
        }
        // 已計算過
        if (memo[state] != -1) {
            return memo[state];
        }
        // 可以選擇任意一張貼紙，選擇結果最小的選擇，先給個不可能的值
        int ans = n + 1;
        // 任意選一張貼紙
        for (String sticker : stickers) {
            // 先做state的備份
            int nstate = state;
            // 考慮貼紙s每個字符能做出的貢獻
            for (char c : sticker.toCharArray()) {
                for (int j = 0; j < n; ++j) {
                    // 如果s[i]恰好能湊上t[j]（前提是t[j]還未被湊出，注意這裡用的是nstate）
                    if (c == target.charAt(j) && ((nstate >> j) & 1) == 0) {
                        // 標記t[j]被湊出
                        nstate |= (1 << j);
                        // 考慮是[i+1]做貢獻
                        break;
                    }
                }
            }
            // 的確做出了貢獻
            if (nstate != state) {
                // 選擇當前貼紙，完成整個過程所需要的最小貼紙數
                // 多個選擇中選最優
                ans = Math.min(ans, 1 + dfs(stickers, target, nstate));
            }
        }
        // 更新備忘錄並返回
        memo[state] = ans;
        return ans;
    }


    /**
     * 思路不變，使用 bfs, 每次可以 選擇任意一張貼紙。選擇不同貼紙就會得到不同鄰接狀態
     * 按照 bfs 【齊頭並進】特點，最先 達到 終極狀態 (1<<n-1)時，返回 step(看作多叉樹數層序遍歷的話，對應最小的葉子節點層數)即可
     *
     * @param stickers
     * @param target
     * @return
     */
    public int minStickers2(String[] stickers, String target) {
        n = target.length();
        return bfs(stickers, target);
    }

    /**
     * bfs，當前狀態是 state（二進制第j位為1代表target[j]已經湊出）
     * <p>
     * 每個節點 有 stickers.length 種鄰接選項，選擇不同貼紙，得到不同鄰接狀態
     *
     * @param stickers
     * @param target
     * @return
     */
    private int bfs(String[] stickers, String target) {
        int step = 0;
        Deque<Integer> queue = new ArrayDeque<>();
        // bfs不需要備忘錄，但要避免節點重復訪問
        Set<Integer> set = new HashSet<>();
        // 初始狀態
        queue.offer(0);
        set.add(0);
        while (!queue.isEmpty()) {
            // 當前層 節點個數
            int sz = queue.size();
            // 逐個取出當前層節點，找到他們的鄰接點，加入下一層隊列
            for (int i = 0; i < sz; ++i) {
                Integer state = queue.poll();
                // 發現目標狀態，返回 step
                if (state == (1 << n) - 1) {
                    return step;
                }
                // 找尋全部鄰接點
                // 任意選一張貼紙
                for (String sticker : stickers) {
                    // 先做state的備份
                    int nstate = state;
                    // 考慮貼紙s每個字符能做出的貢獻
                    for (char c : sticker.toCharArray()) {
                        for (int j = 0; j < n; ++j) {
                            // 如果s[i]恰好能湊上t[j]（前提是t[j]還未被湊出，注意這裡用的是nstate）
                            if (c == target.charAt(j) && ((nstate >> j) & 1) == 0) {
                                // 標記t[j]被湊出
                                nstate |= (1 << j);
                                // 考慮是[i+1]做貢獻
                                break;
                            }
                        }
                    }
                    // 的確做出了貢獻 並且 鄰接狀態沒訪問過
                    if (nstate != state && !set.contains(nstate)) {
                        // 鄰接狀態入隊列
                        queue.offer(nstate);
                        // 標記鄰接狀態已訪問
                        set.add(nstate);
                    }
                }
            }
            // 齊頭並進
            step++;
        }
        // 不在bfs過程中返回，就是無法湊出target
        return -1;
    }

}
