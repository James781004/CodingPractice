package WeeklyContest;

import java.util.*;

public class Week_485 {

    // https://leetcode.cn/problems/vowel-consonant-score/solutions/3883324/ku-han-shu-xie-fa-pythonjavacgo-by-endle-5nlf/
    public int vowelConsonantScore(String s) {
        int v = 0;
        int c = 0;
        for (char ch : s.toCharArray()) {
            if (!Character.isLetter(ch)) {
                continue;
            }
            if ("aeiou".indexOf(ch) != -1) {
                v++;
            } else {
                c++;
            }
        }
        return c > 0 ? v / c : 0;
    }


    // https://leetcode.cn/problems/maximum-capacity-within-budget/solutions/3883309/pai-xu-dan-diao-zhan-by-xie-bin-o-g1k0/
    public int maxCapacity(int[] costs, int[] capacity, int budget) {
        int n = costs.length;
        int ans = 0;
        int[][] d = new int[n][2];
        for (int i = 0; i < n; i++) {
            d[i][0] = costs[i];
            d[i][1] = capacity[i];
        }
        Arrays.sort(d, (a, b) -> (a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]));
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (d[i][0] >= budget) continue;
            while (!q.isEmpty() && d[q.peek()][0] + d[i][0] >= budget) {
                q.pop();
            }
            ans = Math.max(ans, q.isEmpty() ? d[i][1] : d[i][1] + d[q.peek()][1]);
            if (q.isEmpty() || d[q.peek()][1] < d[i][1]) {
                q.push(i);
            }
        }
        return ans;
    }

    // https://leetcode.cn/problems/design-auction-system/solutions/3883292/lan-shan-chu-dui-pythonjavacgo-by-endles-hwsw/
    class AuctionSystem {

        private final Map<Integer, Integer> amount = new HashMap<>(); // (userId, itemId) -> bidAmount
        private final Map<Integer, PriorityQueue<int[]>> itemPQ = new HashMap<>(); // itemId -> [(bidAmount, userId)]

        public void addBid(int userId, int itemId, int bidAmount) {
            // 把 (userId, itemId) 壓縮到一個 32 位 int 中
            amount.put(userId << 16 | itemId, bidAmount);
            itemPQ.computeIfAbsent(itemId, k -> new PriorityQueue<>((a, b) -> a[0] != b[0] ? b[0] - a[0] : b[1] - a[1]))
                    .offer(new int[]{bidAmount, userId});
        }

        public void updateBid(int userId, int itemId, int newAmount) {
            addBid(userId, itemId, newAmount);
            // 堆中重復的元素在 getHighestBidder 中刪除（懶更新）
        }

        public void removeBid(int userId, int itemId) {
            amount.remove(userId << 16 | itemId);
            // 堆中元素在 getHighestBidder 中刪除（懶刪除）
        }

        public int getHighestBidder(int itemId) {
            PriorityQueue<int[]> pq = itemPQ.get(itemId);
            if (pq == null) {
                return -1;
            }

            while (!pq.isEmpty()) {
                int[] top = pq.peek();
                int bidAmount = top[0];
                int userId = top[1];
                Integer realAmount = amount.get(userId << 16 | itemId);
                if (realAmount != null && realAmount == bidAmount) {
                    return userId;
                }
                // 貨不對板，堆頂出價與實際不符
                pq.poll();
            }
            return -1;
        }
    }


    // https://leetcode.cn/problems/lexicographically-smallest-string-after-deleting-duplicate-characters/solutions/3883318/dan-diao-zhan-pythonjavacgo-by-endlessch-48tm/
    public String lexSmallestAfterDeletion(String S) {
        char[] s = S.toCharArray();
        int[] left = new int[26];
        for (char ch : s) {
            left[ch - 'a']++;
        }

        StringBuilder st = new StringBuilder();
        for (char ch : s) {
            // 如果 ch 比棧頂小，用 ch 代替棧頂，可以讓字典序更小
            while (!st.isEmpty() && ch < st.charAt(st.length() - 1) && left[st.charAt(st.length() - 1) - 'a'] > 1) {
                left[st.charAt(st.length() - 1) - 'a']--;
                st.setLength(st.length() - 1);
            }
            st.append(ch);
        }

        // 最後，移除末尾的重復元素
        while (left[st.charAt(st.length() - 1) - 'a'] > 1) {
            left[st.charAt(st.length() - 1) - 'a']--;
            st.setLength(st.length() - 1);
        }

        return st.toString();
    }


}









