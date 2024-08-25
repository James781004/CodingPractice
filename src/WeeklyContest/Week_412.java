package WeeklyContest;

import java.util.*;

public class Week_412 {
    // https://leetcode.cn/problems/final-array-state-after-k-multiplication-operations-ii/solutions/2892178/zui-xiao-dui-mo-ni-shu-xue-gong-shi-pyth-z4zw/
    private static final int MOD = 1_000_000_007;

    public int[] getFinalState(int[] nums, int k, int multiplier) {
        if (multiplier == 1) { // 數組不變
            return nums;
        }

        int n = nums.length;
        int mx = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));
        for (int i = 0; i < n; i++) {
            mx = Math.max(mx, nums[i]);
            pq.offer(new long[]{nums[i], i});
        }

        // 模擬，直到堆頂是最大值
        for (; k > 0 && pq.peek()[0] < mx; k--) {
            long[] p = pq.poll();
            p[0] *= multiplier;
            pq.offer(p);
        }

        // 剩余的操作可以直接用公式計算
        for (int i = 0; i < n; i++) {
            long[] p = pq.poll();
            nums[(int) p[1]] = (int) (p[0] % MOD * pow(multiplier, k / n + (i < k % n ? 1 : 0)) % MOD);
        }
        return nums;
    }

    private long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }


    // https://leetcode.cn/problems/count-almost-equal-pairs-ii/solutions/2892072/pai-xu-mei-ju-you-wei-hu-zuo-pythonjavac-vbg0/
    public int countPairs(int[] nums) {
        Integer[] a = Arrays.stream(nums).boxed().toArray(Integer[]::new);
        Arrays.sort(a, (x, y) -> Integer.toString(x).length() - Integer.toString(y).length());
        int ans = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : a) {
            Set<Integer> st = new HashSet<>();
            st.add(x);
            char[] s = Integer.toString(x).toCharArray();
            int m = s.length;
            for (int i = 0; i < m; i++) {
                for (int j = i + 1; j < m; j++) {
                    swap(s, i, j); // 第一次交換
                    st.add(Integer.parseInt(new String(s)));
                    for (int p = i + 1; p < m; p++) {
                        for (int q = p + 1; q < m; q++) {
                            swap(s, p, q); // 第二次交換
                            st.add(Integer.parseInt(new String(s)));
                            swap(s, p, q);
                        }
                    }
                    swap(s, i, j);
                }
            }
            for (int v : st) {
                ans += cnt.getOrDefault(v, 0);
            }
            cnt.merge(x, 1, Integer::sum);
        }
        return ans;
    }

    private void swap(char[] s, int i, int j) {
        char tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
    }


}


