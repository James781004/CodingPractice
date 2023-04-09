package WeeklyContest;

import java.util.*;

public class Week_336 {
    // https://www.youtube.com/watch?v=sRewxYsQLuA

    // https://zxi.mytechroad.com/blog/string/leetcode-2586-count-the-number-of-vowel-strings-in-range/
    class VowelStrings {
        public int vowelStrings(String[] words, int left, int right) {
            int count = 0;
            for (int i = left; i < right; i++) {
                char start = words[i].charAt(0);
                char end = words[i].charAt(words[i].length() - 1);
                if (isVowel(start) && isVowel(end)) count++;
            }
            return count;
        }

        private boolean isVowel(char c) {
            return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
        }

    }


    // https://leetcode.com/problems/rearrange-array-to-maximize-prefix-score/description/
    class MaxCount {
        public int maxCount(int[] nums) {
            List<Integer> list = new ArrayList<>();
            Arrays.sort(nums);
            for (int n : nums) list.add(0, n);  // sort from big to small
            long prefix = 0;
            int count = 0;
            for (int num : list) {
                prefix += num;
                if (prefix >= 0) count++;
                else break;
            }
            return count;
        }

        public int maxCount2(int[] nums) {
            Arrays.sort(nums);  // sort from small to big
            long prefix = 0;
            int ans = 0;

            // loop from the back
            for (int i = nums.length - 1; i >= 0; i--) {
                prefix += nums[i];
                if (prefix <= 0) break;
                ans++;
            }
            return ans;
        }
    }


    // https://leetcode.com/problems/count-the-number-of-beautiful-subarrays/
    class BeautifulSubArrays {
        public long beautifulSubArrays(int[] nums) {
            Map<Long, Integer> map = new HashMap<>();
            long res = 0;
            map.put(0L, 1); // prefix initialize
            long cur = 0;
            for (int i = 0; i < nums.length; i++) {
                cur = helper(cur, nums[i]);
                if (map.containsKey(cur)) {
                    res += map.get(cur);
                }
                map.put(cur, map.getOrDefault(cur, 0) + 1);
            }
            return res;
        }

        private long helper(long cur, int num) {
            long res = 0L;
            for (int i = 0; i < 64; i++) {
                int mask = (1 << i);
//            if ((cur & mask) > 0 && (num & mask) > 0) continue;
//            if ((cur & mask) > 0 || (num & mask) > 0) {
//                res |= mask;
//            }
                res |= (cur & mask) ^ (num & mask);
            }
            return res;
        }

        // https://leetcode.com/problems/count-the-number-of-beautiful-subarrays/solutions/3286372/java-c-python-prefix-xor/
        // Prefix XOR solution
        public long beautifulSubArrays2(int[] A) {
            HashMap<Integer, Integer> dp = new HashMap<>();
            dp.put(0, 1);
            int pre = 0;  // pre records the prefix xor, where pre[i] = pre[i-1] ^ A[i].
            long res = 0;
            for (int a : A) {
                pre ^= a;
                int v = dp.getOrDefault(pre, 0);
                res += v;
                dp.put(pre, v + 1);
            }
            return res;
        }
    }


    // https://leetcode.com/problems/minimum-time-to-complete-all-tasks/description/
    class FindMinimumTime {
        public int findMinimumTime(int[][] tasks) {
            // Determines whether work has been done before
            boolean visited[] = new boolean[2001];

            // Sort by interval end time
            Arrays.sort(tasks, (x, y) -> x[1] - y[1]);

            int res = 0;
            for (int t[] : tasks) {
                int start = t[0], end = t[1], d = t[2];
                // If work has been done before, do it simultaneously
                for (int i = start; i <= end; i++) {
                    if (visited[i]) {
                        d--;
                    }
                    if (d == 0) {
                        break;
                    }
                }
                // For work that has not been done before
                while (d > 0) {
                    // duration is less than or equal to interval size
                    // so no worry about going out of bound
                    if (!visited[end]) {
                        visited[end] = true;
                        d--;
                        res++;
                    }
                    end--;
                }
            }
            return res;
        }
    }


}
