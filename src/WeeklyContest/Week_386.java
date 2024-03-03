package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Week_386 {
    // https://leetcode.cn/problems/split-the-array/submissions/504541702/
    // 題目要求分割成兩數組，且兩數組中元素互不相同，
    // 表示相同元素最多只能出現兩次，然後分配在兩數組中，
    // 只要出現超過兩次就不可能符合題目要求，因為必定出現重複
    public boolean isPossibleToSplit(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int v : nums) {
            map.put(v, map.getOrDefault(v, 0) + 1);
            if (map.get(v) > 2) return false;
        }

        return true;
    }


    // https://leetcode.cn/problems/find-the-largest-area-of-square-inside-two-rectangles/solutions/2653088/que-ding-jiao-ji-de-tiao-jian-by-celtics-bbtm/
    // 首先需要確定交集的條件。把矩形拍扁，先看x軸相交時是什麼情況。
    // 相交時，左端取的是兩個矩形左邊界的最大值，右端取得是右邊界的最小值。
    // y軸同理。求得相交矩形的邊長後，取二者最小值即可計算正方形面積。
    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {
        long res = 0L;
        int n = bottomLeft.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                long l = (long) Math.max(bottomLeft[i][0], bottomLeft[j][0]);
                long r = (long) Math.min(topRight[i][0], topRight[j][0]);
                long d = (long) Math.max(bottomLeft[i][1], bottomLeft[j][1]);
                long u = (long) Math.min(topRight[i][1], topRight[j][1]);
                if (l <= r && d <= u) {
                    long we = r - l, hi = u - d;
                    long tmp = we < hi ? we * we : hi * hi;
                    res = res < tmp ? tmp : res;
                }
            }
        }

        return res;
    }


    // https://leetcode.cn/problems/earliest-second-to-mark-indices-i/solutions/2653101/er-fen-da-an-pythonjavacgo-by-endlessche-or61/
    public int earliestSecondToMarkIndices(int[] nums, int[] changeIndices) {
        int m = changeIndices.length;
        int left = 0, right = m + 1;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (check(nums, changeIndices, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right > m ? -1 : right;
    }

    private boolean check(int[] nums, int[] changeIndices, int mx) {
        int[] lastT = new int[nums.length];
        Arrays.fill(lastT, -1);
        for (int t = 0; t < mx; t++) {
            lastT[changeIndices[t] - 1] = t;
        }
        for (int t : lastT) {
            if (t < 0) { // 沒有標記時間
                return false;
            }
        }

        int cnt = 0;
        for (int i = 0; i < mx; i++) {
            int idx = changeIndices[i] - 1;
            if (i == lastT[idx]) { // 一定要標記
                if (nums[idx] > cnt) { // 無法減一
                    return false;
                }
                cnt -= nums[idx]; // 減一的時間
            } else {
                cnt++; // 留著後面減一
            }
        }
        return true;
    }


    // https://leetcode.cn/problems/earliest-second-to-mark-indices-ii/solutions/2653053/er-fen-da-an-fan-hui-tan-xin-pythonjavac-997n/
    public int earliestSecondToMarkIndices2(int[] nums, int[] changeIndices) {
        int n = nums.length;
        int m = changeIndices.length;
        int[] firstT = new int[n];
        Arrays.fill(firstT, -1);
        for (int t = m - 1; t >= 0; --t) {
            firstT[changeIndices[t] - 1] = t;
        }

        int left = -1, right = m;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (check2(nums, changeIndices, firstT, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right == m ? -1 : right + 1;
    }


    private boolean check2(int[] nums, int[] changeIndices, int[] firstT, int mx) {
        int n = nums.length;
        int cnt = 0;
        boolean[] done = new boolean[n];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int t = mx; t >= 0; t--) {
            int i = changeIndices[t] - 1;
            int v = nums[i];
            if (v <= 1 || firstT[i] != t) {
                cnt++; // 留著給前面，用來減一/標記
                continue;
            }
            if (cnt == 0) {
                if (pq.isEmpty() || v <= pq.peek()[0]) {
                    cnt += 1; // 留著給前面，用來減一/標記
                    continue;
                }
                done[pq.poll()[1]] = false;
                cnt += 2; // 反悔：一次置 0，一次標記
            }
            done[i] = true;
            cnt--; // nums[i] 置 0，然後消耗一次標記的時間
            pq.offer(new int[]{v, i});
        }

        for (int i = 0; i < n; i++) {
            if (!done[i]) {
                cnt -= nums[i] + 1; // 減一的時間，以及標記的時間
                if (cnt < 0) {
                    return false;
                }
            }
        }
        return true;
    }
}


