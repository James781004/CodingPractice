package WeeklyContest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Week_281 {
    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2180.Count%20Integers%20With%20Even%20Digit%20Sum/README.md
    public int countEven(int num) {
        int ans = 0;
        for (int i = 0; i <= num; i++) {
            int s = 0;
            for (int j = i; j > 0; j /= 10) {
                s += j % 10;
            }
            if (s % 2 == 0) {
                ans++;
            }
        }
        return ans;
    }


    public int countEven2(int num) {
        // 在 [0...x] 的所有數中，每 10 個數中，就有 5 個數的各位數字之和為偶數。
        // 例如，在 [0...9] 中，每 10 個數中，就有 5 個數的各位數字之和為偶數，分別是 [0,2,4,6,8]。
        // 因此，可以先算出 num 中有多少個 10 的倍數，然後乘以 5 再減去 1 （排除 0 這個偶數）
        // 得到初始答案 ans == num / 10 * 5 -1
        int ans = num / 10 * 5 - 1;

        // 接下來，還需要考慮剩下的 num 個數字中，有多少個數的各位數字之和為偶數。
        // 這些數字是否是偶數，跟數字的前面數字之和有關，因此，可以算出 num 的前面數字之和 x，
        // 那麼剩余的數字中，還有 num / 2 個數的各位數字之和為偶數。累加到答案 ans 中即可。
        int s = 0;
        for (int x = num / 10; x > 0; x /= 10) {
            s += x % 10;
        }
        ans += (num % 10 + 2 - (s & 1)) >> 1;
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2181.Merge%20Nodes%20in%20Between%20Zeros/README.md
    public ListNode mergeNodes(ListNode head) {
        ListNode dummy = new ListNode(-1);
        int s = 0;
        ListNode tail = dummy;
        for (ListNode cur = head.next; cur != null; cur = cur.next) {
            if (cur.val != 0) {
                s += cur.val;
            } else {
                tail.next = new ListNode(s);
                tail = tail.next;
                s = 0;
            }
        }
        return dummy.next;
    }

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2182.Construct%20String%20With%20Repeat%20Limit/README.md
    public String repeatLimitedString(String s, int repeatLimit) {
        // 統計字元數量
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        // 倒序遍歷cnt，以組合最大字典序
        StringBuilder ans = new StringBuilder();
        for (int i = 25; i >= 0; --i) {
            int j = i - 1;  // 先記錄當前可能的次大字典序
            while (true) {
                // 從最大字典序字元開始組合，直到達到 repeatLimit 或者字元用完為止
                for (int k = Math.min(repeatLimit, cnt[i]); k > 0; --k) {
                    cnt[i]--;
                    ans.append((char) ('a' + i));
                }

                // 最大字典序字元用完跳出 while，尋找下一個候選的最大字典序字元
                if (cnt[i] == 0) {
                    break;
                }

                // 找出存在的次大字典序字元
                while (j >= 0 && cnt[j] == 0) {
                    --j;
                }

                // 次大字典序字元不存在，跳出 while
                if (j < 0) {
                    break;
                }

                // 接上一個次大字典序字元，下個 while 循環繼續接上最大字典序字元
                cnt[j]--;
                ans.append((char) ('a' + j));
            }
        }
        return ans.toString();
    }


    // https://leetcode.cn/problems/count-array-pairs-divisible-by-k/solution/tong-ji-yin-zi-chu-xian-ci-shu-by-endles-t5k8/
    public long countPairs(int[] nums, int k) {
        int mx = 100001;
        List<List<Integer>> divisors = new ArrayList<>();
        for (int i = 0; i < mx; i++) divisors.add(new ArrayList<>());
        for (int i = 1; i < mx; i++) // 預處理每個數的所有因子，時間復雜度 O(MlogM)，M=1e5
            for (int j = i; j < mx; j += i)
                divisors.get(j).add(i);

        long ans = 0L;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int v : nums) {
            ans += cnt.getOrDefault(k / gcd(v, k), 0);
            for (int d : divisors.get(v)) cnt.put(d, cnt.getOrDefault(d, 0) + 1);
        }
        return ans;
    }


    public long countPairs2(int[] nums, int k) {
        // 預處理 k 的所有因子
        List<Integer> divisors = new ArrayList<>();
        for (int d = 1; d * d <= k; d++) {
            if (k % d == 0) {
                divisors.add(d);
                if (d * d < k) divisors.add(k / d);
            }
        }

        long ans = 0L;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int v : nums) {
            ans += cnt.getOrDefault(k / gcd(v, k), 0);
            for (int d : divisors) {
                if (v % d == 0) cnt.put(d, cnt.getOrDefault(d, 0) + 1);
            }
        }

        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}

