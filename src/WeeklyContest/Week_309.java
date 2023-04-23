package WeeklyContest;

import java.util.Arrays;
import java.util.Objects;
import java.util.PriorityQueue;

public class Week_309 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2399.Check%20Distances%20Between%20Same%20Letters/README.md
    public boolean checkDistances(String s, int[] distance) {
        int[] d = new int[26];
        for (int i = 1; i <= s.length(); ++i) {
            int c = s.charAt(i - 1) - 'a';
            if (d[c] > 0 && i - d[c] - 1 != distance[c]) {
                return false;
            }
            d[c] = i;
        }
        return true;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2400.Number%20of%20Ways%20to%20Reach%20a%20Position%20After%20Exactly%20k%20Steps/README.md
    private Integer[][] f;
    private final int mod = (int) 1e9 + 7;

    public int numberOfWays(int startPos, int endPos, int k) {
        f = new Integer[k + 1][k + 1];
        return dfs(Math.abs(startPos - endPos), k);
    }

    // 當前位置距離目標位置的距離為 i ，還剩 j 步，有多少種方法到達目標位置
    private int dfs(int i, int j) {
        if (i > j || j < 0) return 0; // 當前位置距離目標位置的距離大於剩余步數，或者剩余步數為負數，此時無法到達目標位置，返回0
        if (j == 0) return i == 0 ? 1 : 0; // 剩余步數為 0，此時只有當前位置距離目標位置的距離為0時才能到達目標位置，否則無法到達目標位置，返回1或者0
        if (f[i][j] != null) return f[i][j];

        // 向左移動一步，此時當前位置距離目標位置的距離為 i + 1，還剩 j - 1 步，方法數為 dfs(i + 1, j - 1)
        // 向右移動一步，此時當前位置距離目標位置的距離為 Math.abs(i - 1)，還剩 j - 1 步，方法數為 dfs(Math.abs(i - 1), j - 1)
        int ans = dfs(i + 1, j - 1) + dfs(Math.abs(i - 1), j - 1);
        ans %= mod;
        return f[i][j] = ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2401.Longest%20Nice%20Subarray/README.md
    public int longestNiceSubArray(int[] nums) {
        int ans = 0, mask = 0;
        for (int i = 0, j = 0; i < nums.length; ++i) {
            while ((mask & nums[i]) != 0) {
                mask ^= nums[j++];
            }
            ans = Math.max(ans, i - j + 1);
            mask |= nums[i];
        }
        return ans;
    }


    // https://www.bilibili.com/video/BV1Dt4y1j7qh/
    // https://leetcode.cn/problems/meeting-rooms-iii/solution/shuang-dui-mo-ni-pythonjavacgo-by-endles-ctwc/
    public int mostBooked(int n, int[][] meetings) {
        int[] cnt = new int[n];
        PriorityQueue<Integer> idle = new PriorityQueue<>();
        for (int i = 0; i < n; ++i) idle.offer(i);
        PriorityQueue<Pair> using = new PriorityQueue<>((a, b) -> !Objects.equals(a.key, b.key) ? Long.compare(a.key, b.key) : Integer.compare(a.value, b.value));
        Arrays.sort(meetings, (a, b) -> Integer.compare(a[0], b[0]));
        for (int[] m : meetings) {
            long st = m[0], end = m[1];
            while (!using.isEmpty() && using.peek().key <= st) {
                idle.offer(using.poll().value); // 維護在 st 時刻空閒的會議室
            }
            int id;
            if (idle.isEmpty()) {
                Pair p = using.poll(); // 沒有可用的會議室，那麼彈出一個最早結束的會議室（若有多個同時結束的，會彈出下標最小的）
                end += p.key - st; // 更新當前會議的結束時間
                id = p.value;
            } else id = idle.poll();
            ++cnt[id];
            using.offer(new Pair(end, id)); // 使用一個會議室
        }
        int ans = 0;
        for (int i = 0; i < n; ++i) if (cnt[i] > cnt[ans]) ans = i;
        return ans;
    }


    class Pair {
        Long key;
        int value;

        public Pair(long k, int v) {
            key = k;
            value = v;
        }
    }

}
