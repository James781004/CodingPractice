package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class LongestWPI {

    // https://leetcode.cn/problems/longest-well-performing-interval/solutions/2110211/liang-chong-zuo-fa-liang-zhang-tu-miao-d-hysl/
    public int longestWPI(int[] hours) {
        int n = hours.length, ans = 0;
        var s = new int[n + 1]; // 前綴和
        var st = new ArrayDeque<Integer>();
        st.push(0); // s[0]
        for (int j = 1; j <= n; j++) {
            // 勞累的一天視作 nums[i]=1，不勞累的一天視作 nums[i]=−1
            // 則問題變為：計算 nums 的最長子數組，其元素和大於 0
            s[j] = s[j - 1] + (hours[j - 1] > 8 ? 1 : -1);
            if (s[j] < s[st.peek()]) st.push(j); // 感興趣的 j
        }
        for (int i = n; i > 0; i--)
            while (!st.isEmpty() && s[i] > s[st.peek()])
                ans = Math.max(ans, i - st.pop()); // [棧頂,i) 可能是最長子數組
        return ans;
    }


    public int longestWPI2(int[] hours) {
        int n = hours.length, ans = 0, s = 0;
        var pos = new int[n + 2]; // 記錄前綴和首次出現的位置
        for (int i = 1; i <= n; ++i) {
            s -= hours[i - 1] > 8 ? 1 : -1; // 取反，改為減法
            if (s < 0) ans = i;
            else {
                if (pos[s + 1] > 0) ans = Math.max(ans, i - pos[s + 1]);
                if (pos[s] == 0) pos[s] = i;
            }
        }
        return ans;
    }


    public int longestWPI3(int[] hours) {
        int ans = 0, s = 0;
        Map<Integer, Integer> pos = new HashMap<>();
        for (int i = 0; i < hours.length; ++i) {
            s += hours[i] > 8 ? 1 : -1; // hours[i]>8，就讓 s 加 1，否則減 1
            if (s > 0) {
                // 如果 s 大於 0，說明從下標 0 到當前下標的這一段，滿足「表現良好的時間段」，
                // 更新結果 ans=i+1
                ans = i + 1;
            } else if (pos.containsKey(s - 1)) {
                // 否則，如果 s−1 在哈希表 pos 中，記 j=pos[s−1]，
                // 說明從下標 j+1 到當前下標 i 的這一段，滿足「表現良好的時間段」，
                // 更新結果 ans=max(ans,i−j)
                ans = Math.max(ans, i - pos.get(s - 1));
            }
            pos.putIfAbsent(s, i);
        }
        return ans;
    }


}
