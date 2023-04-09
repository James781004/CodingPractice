package WeeklyContest;

import java.util.*;

public class Week_331 {
    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2558.Take%20Gifts%20From%20the%20Richest%20Pile/README.md
    public long pickGifts(int[] gifts, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for (int v : gifts) pq.offer(v);
        while (k-- > 0) pq.offer((int) Math.sqrt(pq.poll()));
        long res = 0;
        for (int v : gifts) res += v;
        return res;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2559.Count%20Vowel%20Strings%20in%20Ranges/README.md
    class VowelStrings {
        public int[] vowelStrings(String[] words, int[][] queries) {
            List<Integer> t = new ArrayList<>();
            Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
            for (int i = 0; i < words.length; ++i) {
                char front = words[i].charAt(0), back = words[i].charAt(words[i].length() - 1);
                if (vowels.contains(front) && vowels.contains(back)) {
                    t.add(i);
                }
            }

            int[] ans = new int[queries.length];
            for (int i = 0; i < ans.length; ++i) {
                // 二分查找統計在預處理數組中的下標范圍內的字符串的數目
                ans[i] = search(t, queries[i][1] + 1) - search(t, queries[i][0]);
            }
            return ans;
        }

        private int search(List<Integer> nums, int x) {
            int left = 0, right = nums.size();
            while (left < right) {
                int mid = (left + right) >> 1;
                if (nums.get(mid) >= x) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }


        // 前綴和
        public int[] vowelStrings2(String[] words, int[][] queries) {
            // 構造元音哈希集合
            String s = "aeiou";
            HashSet<Character> cs = new HashSet<>();
            for (char c : s.toCharArray()) {
                cs.add(c);
            }

            // 構造前綴和數組
            // match的字串看成1，不滿足的看成0
            // 計算連續子數組的前綴和
            int len = words.length;
            int[] prefixAndArr = new int[len];
            for (int i = 0; i < len; i++) {
                String word = words[i];
                if (isMatch(word, cs)) {
                    // 首尾皆是元音
                    if (i == 0) {
                        prefixAndArr[i] = 1; // 數組開頭特別處理
                    } else {
                        prefixAndArr[i] += prefixAndArr[i - 1] + 1; // 前面的前綴和+1
                    }
                } else {
                    if (i > 0) {
                        // 取上一次值
                        prefixAndArr[i] = prefixAndArr[i - 1];
                    }
                }
            }

            len = queries.length;
            int[] rs = new int[len];
            for (int i = 0; i < len; i++) {
                if (queries[i][0] == 0) {
                    // queries[i][0] == 0，表示從0開始查
                    // 直接取queries[i][1]前綴和
                    rs[i] = prefixAndArr[queries[i][1]];
                } else {
                    // 求前綴和的差
                    // 注意起始索引多減一，相當於求兩段的變化：(end - start) + (start - (start - 1))
                    rs[i] = prefixAndArr[queries[i][1]] - prefixAndArr[queries[i][0] - 1];
                }
            }
            return rs;
        }

        // 判斷首尾是否皆是元音
        private boolean isMatch(String word, HashSet<Character> cs) {
            if (cs.contains(word.charAt(0)) && cs.contains(word.charAt(word.length() - 1))) {
                return true;
            } else {
                return false;
            }
        }
    }


    // https://leetcode.cn/problems/house-robber-iv/solution/er-fen-da-an-dp-by-endlesscheng-m558/
    public int minCapability(int[] nums, int k) {
        int left = 0, right = (int) 1e9;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;  // 當前金額上限

            // 概念類似 LC 198
            int f0 = 0, f1 = 0;
            for (int x : nums) {
                if (x > mid) f0 = f1;  // 超過當前金額上限，只能取前次結果
                else {
                    int tmp = f1;
                    f1 = Math.max(f1, f0 + 1);  // 與LC 198不同的是這邊計算房子數量
                    f0 = tmp;
                }
            }

            // 二分查找
            if (f1 >= k) right = mid;
            else left = mid;
        }
        return right;
    }


    // https://leetcode.cn/problems/rearranging-fruits/solution/tan-xin-gou-zao-by-endlesscheng-c2ui/
    public long minCost(int[] basket1, int[] basket2) {
        // 先統計basket1, basket2中元素出現次數
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int i = 0; i < basket1.length; i++) {
            cnt.merge(basket1[i], 1, Integer::sum);
            cnt.merge(basket2[i], -1, Integer::sum);
        }

        int mn = Integer.MAX_VALUE;  // 找兩數組中最小的值來當工具人，之後可以不斷跟對方最大值互換
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
            int x = e.getKey(), c = e.getValue();
            if (c % 2 != 0) return -1;  // 不同元素出現次數不是偶數，無論怎麼交換都不可能相等
            if (c > 0) {
                // 出現偶數次數，只須交換一半次數即可(先不考慮工具人為1)
                // 例如：(1, 1, 3, 3, 7), (2, 2, 5, 5, 7)
                // 其中7是兩數組共有已經排除，接下來交換一組(1, 2) (3, 5)即可
                for (c = Math.abs(c) / 2; c > 0; c--) a.add(x);
            } else {
                for (c = Math.abs(c) / 2; c > 0; c--) b.add(x);
            }
            mn = Math.min(mn, x);
        }

        // a小大排序，b大小排序，一個一個互換
        long res = 0;
        a.sort((x, y) -> x - y);
        b.sort(Comparator.reverseOrder());
        for (int i = 0; i < a.size(); i++) {
            // 把 basket 1 和 basket 2 中的最小值 mn 當作「工具人」，
            // 對於 a[i] 和 b[i] 的交換，可以分別和 mn 交換一次，就相當於 a[i] 和 b[i] 交換了。
            // 如果 a[i] 和 b[i] 直接交換代價更小，那就直接交換即可
            // 否則分別和 mn 交換一次，代價為 mn * 2
            res += Math.min(Math.min(a.get(i), b.get(i)), mn * 2);
        }
        return res;
    }


    public long minCost2(int[] basket1, int[] basket2) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int i = 0; i < basket1.length; i++) {
            cnt.merge(basket1[i], 1, Integer::sum);
            cnt.merge(basket2[i], -1, Integer::sum);
        }

        int mn = Integer.MAX_VALUE;  // 找兩數組中最小的值來當工具人，不斷跟對方最大值互換

        // 空間優化
        // 相當於上面a數組取前綴 + b數組取後綴，找到全部的最小數
        // 例如：(1, 1, 3, 3, 7), (2, 2, 5, 5, 7)
        // 其中7是兩數組共有已經排除，接下來取(1, 1, 2, 2)即可
        // list裡面元素相當於不考慮工具人，直接交換兩數最小代價
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
            int x = e.getKey(), c = e.getValue();
            if (c % 2 != 0) return -1;  // 不同元素出現次數不是偶數，無論怎麼交換都不可能相等
            mn = Math.min(mn, x);
            for (c = Math.abs(c) / 2; c > 0; c--) list.add(x);
        }


        long res = 0;
        list.sort((x, y) -> x - y);
        for (int i = 0; i < list.size() / 2; i++) {
            // 前面已經預處理過a數組取前綴 + b數組取後綴，也就是直接交換最小代價
            // 這邊就比較直接交換最小代價和 mn * 2，取較小值即可
            res += Math.min(list.get(i), mn * 2);
        }
        return res;
    }
}
