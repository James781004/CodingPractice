package EndlessCheng.GenreMenu.DP.JumpGame;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;

public class OddEvenJumps {

    // https://leetcode.cn/problems/odd-even-jump/solutions/3586/qi-ou-tiao-by-leetcode/
    public int oddEvenJumps(int[] arr) {
        int n = arr.length;
        Integer[] ids = new Integer[n];
        for (int i = 0; i < n; i++) ids[i] = i;

        // 奇數跳躍時，前進的格子必須比當前要大，所以需要index按照對應A[i]的遞增排序
        Arrays.sort(ids, Comparator.comparingInt(o -> arr[o]));
        Integer[] odd_next = make(ids);

        // 偶數跳躍時，前進的格子必須比當前要小，所以需要index按照對應A[i]的遞減排序
        Arrays.sort(ids, Comparator.comparingInt(o -> -arr[o]));
        Integer[] even_next = make(ids);

        // odd[i]/ even[i] 為DP數組，代表從i進行奇數/偶數跳躍時能否到達末尾。
        int[] odd = new int[n];
        int[] even = new int[n];

        // 根據題意，數組末尾默認為好的起始索引
        odd[n - 1] = even[n - 1] = 1;

        // 注意一定要從數組倒數第二位開始倒著遍歷，因為只有數組末尾的初始化狀態是正確的，倒著遍歷才能正確轉移DP狀態。
        for (int i = n - 2; i >= 0; i--) {
            // 先從odd_next拿到下一個能跳到的節點。接下來要進行偶數跳躍了，查詢even，看看能不能跳到末尾。
            if (odd_next[i] != null) odd[i] = even[odd_next[i]];
            if (even_next[i] != null) even[i] = odd[even_next[i]];
        }
        return Arrays.stream(odd).sum();
    }

    // 思路：首先，我們可以發現下一步應該跳到哪裡只與我們當前的位置與跳躍次數的奇偶性有關系。
    // 對於每一種狀態，接下來可以跳到的狀態一定只有一種（或者接下來不能跳躍了）。
    // 如果我們知道了不同狀態之間的轉移關系，我們就可以通過一次簡單的遍歷解決這個問題了。
    // 問題就簡化為了：從索引 i 進行奇數次跳躍時，下一步應該跳到哪裡去（如果有的話）。偶數次跳躍也是類似的。
    // 算法：假設當前是奇數次跳躍，讓我們來搞清楚在索引 i 的位置接下來應該跳到哪裡去。
    // 我們從小到大考慮數組 A 中的元素。
    // 假設當前我們正在考慮 A[j] = v，
    // 在我們已經處理過但是還未確定下一步跳躍位置的索引中（也就是 <= v 的那些）進行搜索。
    // 如果我們找到了某些已經處理過的值 v0 = A[i] 且 i < j，
    // 那麼我們就可以知道從索引 i 下一步應該跳躍到索引 j 的位置。
    // 我們可以使用單調棧來加速這個過程。我們在棧中保存所有已經處理過的索引 i ，
    // 並且時時刻刻維護這個棧中的元素是遞減的。
    // 當我們增加一個新的索引 j 的時候，我們彈出棧頂比較小的索引 i < j，並且記錄這些索引下一步全都會跳躍到索引 j。
    // 然後，我們就知道所有的 oddnext[i]，也就是位於索引 i 在奇數次跳躍時將會跳到的位置。
    // 使用類似的方法，我們也可以求出 evennext[i]。有了這些信息，我們就可以使用動態規劃的技巧快速建立所有可達狀態。
    private Integer[] make(Integer[] ids) {
        Integer[] ans = new Integer[ids.length];
        Deque<Integer> st = new ArrayDeque<>();
        for (Integer i : ids) { // 彈出棧頂比較小的索引stack_top，並且記錄這些索引下一步全都會跳躍到索引 i
            while (!st.isEmpty() && i > st.peek()) {
                ans[st.pop()] = i;
            }
            st.push(i);
        }
        return ans;
    }
}
