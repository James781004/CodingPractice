package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Week_327 {
    // https://www.bilibili.com/video/BV1KG4y1j73o/?t=8m51s
    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2529.Maximum%20Count%20of%20Positive%20Integer%20and%20Negative%20Integer/README.md
    public int maximumCount(int[] nums) {
        int a = 0, b = 0;
        for (int v : nums) {
            if (v > 0) {
                ++a;
            }
            if (v < 0) {
                ++b;
            }
        }
        return Math.max(a, b);
    }


    public int maximumCountBinary(int[] nums) {
        int a = nums.length - search(nums, 1);
        int b = search(nums, 0);
        return Math.max(a, b);
    }

    // 找尋下標位置
    private int search(int[] nums, int x) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = left + (right - left) >> 1;
            if (nums[mid] >= x) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return left;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2530.Maximal%20Score%20After%20Applying%20K%20Operations/README.md
    public long maxKelements(int[] nums, int k) {

        // 使用優先隊列（大根堆）來維護當前元素值最大的元素。
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for (int v : nums) {
            pq.offer(v);
        }

        // 每次從優先隊列中取出元素值最大的元素，將其分數加入答案，然後將其替換為 ceil(nums[i] / 3)
        long res = 0;
        while (k-- > 0) {
            int v = pq.poll();
            res += v;
            pq.offer((v + 2) / 3);
        }
        return res;
    }


    // https://leetcode.cn/problems/make-number-of-distinct-characters-equal/solution/mei-ju-jian-ji-xie-fa-by-endlesscheng-tjpp/
    public boolean isItPossible(String word1, String word2) {

        // 統計每個字母出現次數
        Map<Character, Integer> c1 = new HashMap<>(), c2 = new HashMap<>();
        for (char c : word1.toCharArray()) c1.merge(c, 1, Integer::sum);
        for (char c : word2.toCharArray()) c2.merge(c, 1, Integer::sum);

        // 枚舉交換的兩個字母
        for (Map.Entry<Character, Integer> e : c1.entrySet()) {
            for (Map.Entry<Character, Integer> f : c2.entrySet()) {
                char x = e.getKey(), y = f.getKey();

                if (x == y) {
                    // 相同字母，如果出現次數相等，互相交換不影響結果，直接return true
                    if (c1.size() == c2.size()) return true;
                } else if (c1.size() - (e.getValue() == 1 ? 1 : 0) + (c1.containsKey(y) ? 0 : 1) ==
                        c2.size() - (f.getValue() == 1 ? 1 : 0) + (c2.containsKey(x) ? 0 : 1)) // 基於長度計算變化量
                    return true;
            }
        }
        return false;
    }


    public boolean isItPossible2(String word1, String word2) {
        int[] cnt1 = new int[26];
        int[] cnt2 = new int[26];
        for (int i = 0; i < word1.length(); ++i) {
            ++cnt1[word1.charAt(i) - 'a'];
        }
        for (int i = 0; i < word2.length(); ++i) {
            ++cnt2[word2.charAt(i) - 'a'];
        }
        for (int i = 0; i < 26; ++i) {
            for (int j = 0; j < 26; ++j) {
                if (cnt1[i] > 0 && cnt2[j] > 0) {

                    // 進行交換
                    --cnt1[i];
                    --cnt2[j];
                    ++cnt1[j];
                    ++cnt2[i];

                    // 差異值d歸零就可以return true
                    int d = 0;
                    for (int k = 0; k < 26; ++k) {
                        if (cnt1[k] > 0) {
                            ++d;
                        }
                        if (cnt2[k] > 0) {
                            --d;
                        }
                    }
                    if (d == 0) {
                        return true;
                    }

                    // 回溯
                    ++cnt1[i];
                    ++cnt2[j];
                    --cnt1[j];
                    --cnt2[i];
                }
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/time-to-cross-a-bridge/solution/by-endlesscheng-nzqo/
    public int findCrossingTime(int n, int k, int[][] time) {
        Arrays.sort(time, (a, b) -> a[0] + a[2] - b[0] - b[2]); // 穩定排序
        PriorityQueue<int[]> workL = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]);
        PriorityQueue<int[]> workR = new PriorityQueue<int[]>(workL.comparator());
        PriorityQueue<int[]> waitL = new PriorityQueue<int[]>((a, b) -> b[0] - a[0]); // 下標越大效率越低
        PriorityQueue<int[]> waitR = new PriorityQueue<int[]>(waitL.comparator());
        for (int i = k - 1; i >= 0; --i) waitL.add(new int[]{i, 0});
        int cur = 0;
        while (n > 0) {
            while (!workL.isEmpty() && workL.peek()[1] <= cur) waitL.add(workL.poll()); // 左邊完成放箱
            while (!workR.isEmpty() && workR.peek()[1] <= cur) waitR.add(workR.poll()); // 右邊完成搬箱
            if (!waitR.isEmpty()) { // 右邊過橋，注意加到 waitR 中的都是 <= cur 的（下同）
                int[] p = waitR.poll();
                cur += time[p[0]][2];
                p[1] = cur + time[p[0]][3];
                workL.add(p); // 放箱
            } else if (!waitL.isEmpty()) { // 左邊過橋
                int[] p = waitL.poll();
                cur += time[p[0]][0];
                p[1] = cur + time[p[0]][1];
                workR.add(p); // 搬箱
                --n;
            } else if (workL.isEmpty()) cur = workR.peek()[1]; // cur 過小，找個最小的放箱/搬箱完成時間來更新 cur
            else if (workR.isEmpty()) cur = workL.peek()[1];
            else cur = Math.min(workL.peek()[1], workR.peek()[1]);
        }
        while (!workR.isEmpty()) {
            int[] p = workR.poll(); // 右邊完成搬箱
            // 如果沒有排隊，直接過橋；否則由於無論誰先過橋，最終完成時間都一樣，所以也可以直接計算
            cur = Math.max(p[1], cur) + time[p[0]][2];
        }
        return cur; // 最後一個過橋的時間
    }

}
