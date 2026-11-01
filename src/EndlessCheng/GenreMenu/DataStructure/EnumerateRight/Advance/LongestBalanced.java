package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

import java.util.*;

public class LongestBalanced {

    // https://leetcode.cn/problems/longest-balanced-substring-i/solutions/3803799/bao-li-mei-ju-pythonjavacgo-by-endlessch-wd3t/
    // 用於哈希表鍵值的狀態物件
    private static class State {
        final int[] d;
        final int mask;

        State(int[] d, int mask) {
            this.d = d;
            this.mask = mask;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof State)) return false;
            State state = (State) o;
            return mask == state.mask && Arrays.equals(d, state.d);
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(d);
            result = 31 * result + mask;
            return result;
        }
    }

    public int longestBalanced(String str) {
        int n = str.length();
        if (n == 0) return 0;

        // 1. 統計所有字元出現次數
        Map<Character, Integer> charCount = new HashMap<>();
        for (int i = 0; i < n; i++) {
            char c = str.charAt(i);
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        // 檢查整個字串是否本身就是平衡的
        int maxFreq = 0;
        for (int freq : charCount.values()) {
            maxFreq = Math.max(maxFreq, freq);
        }
        int distinctCount = charCount.size();
        if ((long) maxFreq * distinctCount == n) {
            return n;
        }

        // 2. 離散化：將字元映射為 0 到 distinctCount - 1 的整數
        Map<Character, Integer> mp = new HashMap<>();
        int idGen = 0;
        for (char c : charCount.keySet()) {
            mp.put(c, idGen++);
        }
        int[] s = new int[n];
        for (int i = 0; i < n; i++) {
            s[i] = mp.get(str.charAt(i));
        }

        // 3. 後綴順序預處理 (suf_orders)
        // suf_orders[i] 存放在後綴 s[i...n-1] 中各字元按照「從左往右首次出現」的先後順序排列的列表
        List<Integer>[] sufOrders = new ArrayList[n];
        List<Integer> order = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            int val = s[i];
            order.remove((Integer) val);
            order.add(val);
            sufOrders[i] = new ArrayList<>(order);
        }

        // 4. 正向遍歷比對前綴狀態
        order = new ArrayList<>();
        int[] cnt = new int[distinctCount];
        Map<State, Integer> pos = new HashMap<>();
        int ans = 0;

        for (int i = 0; i < n; i++) {
            int b = s[i];
            List<Integer> sufOrder = sufOrders[i];

            // 處理左端點候選：此時 cnt 代表前綴 s[0...i-1] 的統計資訊
            int minCh = Integer.MAX_VALUE;
            int mask = 0;
            for (int j = sufOrder.size() - 1; j >= 0; j--) {
                minCh = Math.min(minCh, sufOrder.get(j));
                mask |= 1 << sufOrder.get(j);

                // 在子串候選集合中的字母，減去基準基準字母 cnt[minCh] 的頻率
                int[] d = cnt.clone();
                int baseCount = cnt[minCh];
                for (int k = j; k < sufOrder.size(); k++) {
                    d[sufOrder.get(k)] -= baseCount;
                }

                State p = new State(d, mask);
                // 記錄狀態 p 首次出現時對應的前綴邊界索引 (i - 1)
                pos.putIfAbsent(p, i - 1);
            }

            // 更新 order 列表：將當前字元移動到末尾
            order.remove((Integer) b);
            order.add(b);
            cnt[b]++;

            // 處理右端點：檢查以當前位置 i 結尾的子串是否命中已有的平衡前綴狀態
            minCh = Integer.MAX_VALUE;
            mask = 0;
            for (int j = order.size() - 1; j >= 0; j--) {
                minCh = Math.min(minCh, order.get(j));
                mask |= 1 << order.get(j);

                int[] d = cnt.clone();
                int baseCount = cnt[minCh];
                for (int k = j; k < order.size(); k++) {
                    d[order.get(k)] -= baseCount;
                }

                State p = new State(d, mask);
                if (pos.containsKey(p)) {
                    ans = Math.max(ans, i - pos.get(p));
                }
            }
        }

        return ans;
    }

}
