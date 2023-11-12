package WeeklyContest;

import java.util.*;

public class Week_371 {


    // https://leetcode.cn/problems/high-access-employees/solutions/2523223/an-zhao-ming-zi-fen-zu-pai-xu-pythonjava-fkax/
    // 把名字相同的員工對應的訪問時間（轉成分鐘數）分到同一組中。
    // 對於每一組的訪問時間 a，排序後，判斷是否有 a[i]−a[i−2]<60，
    // 如果有，那麼把這一組的員工名字加到答案中。
    public List<String> findHighAccessEmployees(List<List<String>> accessTimes) {
        Map<String, List<Integer>> groups = new HashMap<>();
        for (List<String> entry : accessTimes) {
            String name = entry.get(0), s = entry.get(1);
            int t = Integer.parseInt(s.substring(0, 2)) * 60 + Integer.parseInt(s.substring(2));
            groups.computeIfAbsent(name, k -> new ArrayList<>()).add(t);
        }

        List<String> ans = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : groups.entrySet()) {
            List<Integer> a = entry.getValue();
            Collections.sort(a);
            for (int i = 2; i < a.size(); i++) {
                if (a.get(i) - a.get(i - 2) < 60) {
                    ans.add(entry.getKey());
                    break;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-operations-to-maximize-last-elements-in-arrays/solutions/2523218/zhi-you-liang-chong-qing-kuang-pythonjav-jdeg/
    public int minOperations(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int ans = Math.min(f(nums1[n - 1], nums2[n - 1], nums1, nums2),
                1 + f(nums2[n - 1], nums1[n - 1], nums1, nums2));
        return ans > n ? -1 : ans;
    }

    // 總共就兩種情況：
    // 1. 不交換 nums1[n−1] 和 nums2[n−1]
    // 2. 交換 nums1[n−1] 和 nums2[n−1]
    // 對於每種情況，從 i=0 枚舉到 i=n−2，
    // 一旦發現 nums1[i] > nums1[n−1] 或 nums2[i] > nums2[n−1]，就必須執行交換操作。
    // 如果操作後仍然滿足 nums1[i] > nums1[n−1] 或 nums2[i] > nums2[n−1]，
    // 說明這種情況無法滿足要求。
    // 如果兩種情況都無法滿足要求，返回 −1。
    private int f(int last1, int last2, int[] nums1, int[] nums2) {
        int res = 0;
        for (int i = 0; i + 1 < nums1.length; i++) {
            int x = nums1[i], y = nums2[i];
            if (x > last1 || y > last2) {
                if (y > last1 || x > last2) {
                    return nums1.length + 1; // 交換無效，返回 n + 1 或者max value
                }
                res++; // 執行了一次交換操作
            }
        }
        return res;
    }

    // https://leetcode.cn/problems/maximum-strong-pair-xor-ii/solutions/2523213/0-1-trie-hua-dong-chuang-kou-pythonjavac-gvv2/
    public int maximumStrongPairXor(int[] nums) {
        Arrays.sort(nums);
        Trie t = new Trie();
        int ans = 0, left = 0;
        for (int y : nums) {
            t.insert(y);
            while (nums[left] * 2 < y) {
                t.remove(nums[left++]);
            }
            ans = Math.max(ans, t.maxXor(y));
        }
        return ans;
    }

    class Node {
        Node[] children = new Node[2];
        int cnt; // 子樹大小
    }

    class Trie {
        private static final int HIGH_BIT = 6;
        private Node root = new Node();

        // 添加 val
        public void insert(int val) {
            Node cur = root;
            for (int i = HIGH_BIT; i >= 0; i--) {
                int bit = (val >> i) & 1;
                if (cur.children[bit] == null) {
                    cur.children[bit] = new Node();
                }
                cur = cur.children[bit];
                cur.cnt++; // 維護子樹大小
            }
        }

        //（懶惰）刪除 val
        // 要求 val 必須在 t 中
        public void remove(int val) {
            Node cur = root;
            for (int i = HIGH_BIT; i >= 0; i--) {
                cur = cur.children[(val >> i) & 1];
                cur.cnt--; // 維護子樹大小
            }
        }

        // 返回 val 與 t 中一個元素的最大異或和
        // 要求 t 不能為空
        public int maxXor(int val) {
            Node cur = root;
            int ans = 0;
            for (int i = HIGH_BIT; i >= 0; i--) {
                int bit = (val >> i) & 1;
                // 如果 cur.children[bit^1].cnt == 0，視作空節點
                if (cur.children[bit ^ 1] != null && cur.children[bit ^ 1].cnt > 0) {
                    ans |= 1 << i;
                    bit ^= 1;
                }
                cur = cur.children[bit];
            }
            return ans;
        }
    }


    public int maximumStrongPairXor2(int[] nums) {
        Arrays.sort(nums);
        int highBit = 31 - Integer.numberOfLeadingZeros(nums[nums.length - 1]);

        int ans = 0, mask = 0;
        Map<Integer, Integer> mp = new HashMap<>();
        for (int i = highBit; i >= 0; i--) { // 從最高位開始枚舉
            mp.clear();
            mask |= 1 << i;
            int newAns = ans | (1 << i); // 這個比特位可以是 1 嗎？
            for (int y : nums) {
                int maskY = y & mask; // 低於 i 的比特位置為 0
                if (mp.containsKey(newAns ^ maskY) && mp.get(newAns ^ maskY) * 2 >= y) {
                    ans = newAns; // 這個比特位可以是 1
                    break;
                }
                mp.put(maskY, y);
            }
        }
        return ans;
    }
}
