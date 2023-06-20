package WeeklyContest;

import java.util.*;

class Week_278 {
    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2154.Keep%20Multiplying%20Found%20Values%20by%20Two/README.md
    public int findFinalValue(int[] nums, int original) {
        Set<Integer> s = new HashSet<>();
        for (int num : nums) {
            s.add(num);
        }
        while (s.contains(original)) {
            original <<= 1;
        }
        return original;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2155.All%20Divisions%20With%20the%20Highest%20Score%20of%20a%20Binary%20Array/README.md
    public List<Integer> maxScoreIndices(int[] nums) {
        int left = 0, right = Arrays.stream(nums).sum();  // 預處理1的個數或0的個數, 記錄當前的最大左右值之和
        int mx = right; // 此時得到的右邊全值的值為最大左右值之和的初始值
        List<Integer> ans = new ArrayList<>();
        ans.add(0);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) { // 記錄當前下標i 大小的左數組的左值 與 n-i 大小的右值
                left++;
            } else {
                right--;
            }

            // 如果出現了新的最大值則先將之前作為最大值記錄的下標清除、再添加
            int t = left + right;
            if (mx == t) {
                ans.add(i + 1);
            } else if (mx < t) {
                mx = t;
                ans.clear();
                ans.add(i + 1);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-substring-with-given-hash-value/solution/cha-zhao-gei-ding-ha-xi-zhi-de-zi-chuan-fi8jd/
    public String subStrHash(String s, int power, int modulo, int k, int hashValue) {
        long p = 1; // 記錄power的k-1次冪
        long hash = 0; // 記錄滾動哈希
        int ans = s.length(); // 記錄符合要求的字符串的起始坐標
        for (int i = 0; i < k; i++) {
            if (i < k - 1) {
                p = p * power % modulo;
            }
            hash = (hash * power + s.charAt(s.length() - 1 - i) - 'a' + 1) % modulo;
        }
        if (hashValue == hash) {
            ans -= k;
        }
        for (int i = s.length() - k - 1; i >= 0; i--) {
            hash = ((hash + (modulo - p) * (s.charAt(i + k) - 'a' + 1)) % modulo * power + s.charAt(i) - 'a' + 1) % modulo;
            if (hash == hashValue) {
                ans = i;
            }
        }
        return s.substring(ans, ans + k);
    }


    // https://leetcode.cn/problems/find-substring-with-given-hash-value/solution/java-qiong-ju-by-electrobikeman-nanning-rltj/
    // java 窮舉法，容易理解但可能超時
    public String subStrHash2(String s, int power, int modulo, int k, int hashValue) {
        long[] pows = new long[k];
        pows[0] = 1;
        for (int i = 1; i < k; i++) {
            pows[i] = pows[i - 1] * power % modulo;
        }

        for (int i = 0; i <= s.length() - k; i++) {
            String subStr = s.substring(i, i + k);
            if (val(subStr, pows, modulo) == hashValue) {
                return subStr;
            }
        }
        return "";
    }

    private int val(String subStr, long[] pows, int modulo) {
        long res = 0;
        for (int i = 0; i < subStr.length(); i++) {
            res += (subStr.charAt(i) - 'a' + 1) * pows[i];
            res %= modulo;
        }
        return (int) (res % modulo);
    }


    // https://leetcode.cn/problems/groups-of-strings/solution/bing-cha-ji-wei-yun-suan-by-endlesscheng-uejd/
    // 由於字符串不含重複字母且只由小寫字母組成，可以用一個二進制數來表示字符串，
    // 二進制數的第 i 位為 1 則表示第 i 個小寫字母出現在 s 中。
    //
    // 枚舉 words 中的字符串 s，並枚舉 s 通過添加、刪除和替換操作得到的字符串 t，
    // 如果 t 也在 words 中，則說明 s 和 t 可以分到同一組。我們可以用並查集來關聯可以分到同一組的字符串。
    //
    // 遍歷結束後，並查集中的集合個數就是 words 分組後的總組數，最大的集合即為字符串數目最多的組所包含的字符串數目。
    // 這可以在並查集合並的同時維護出來。

    // 並查集模板（哈希表寫法）
    HashMap<Integer, Integer> fa = new HashMap<>(), size = new HashMap<>();
    int groups, maxSize;

    int find(int x) {
        if (fa.get(x) != x) fa.put(x, find(fa.get(x)));
        return fa.get(x);
    }

    void merge(int x, int y) {
        if (!fa.containsKey(y)) return;  // 底下枚舉y時，不存在fa的y這邊過濾掉
        x = find(x);
        y = find(y);
        if (x == y) return;
        fa.put(x, y);
        size.put(y, size.get(y) + size.get(x));
        maxSize = Math.max(maxSize, size.get(y)); // 維護答案
        --groups;
    }

    public int[] groupStrings(String[] words) {
        groups = words.length;
        for (String word : words) {
            int x = 0;
            for (int i = 0; i < word.length(); i++)
                x |= 1 << (word.charAt(i) - 'a'); // 計算 word 的二進制表示
            fa.put(x, x); // 添加至並查集
            size.put(x, size.getOrDefault(x, 0) + 1);
            maxSize = Math.max(maxSize, size.get(x)); // 維護答案
            if (size.get(x) > 1) --groups;
        }

        fa.forEach((x, fx) -> {
            for (int i = 0; i < 26; i++) {
                merge(x, x ^ (1 << i)); // 添加或刪除字符 i (就是在26位數組上某位置xor操作)
                if (((x >> i) & 1) == 1) { // AND 運算子 (&): 如果這兩個位元都是 1，則對應的結果位元會設為 1。 否則，對應的結果位元會設為 0。
                    for (int j = 0; j < 26; j++) {  // 這裡&運算表示任意一對0 1對換
                        if (((x >> j) & 1) == 0) {
                            merge(x, x ^ (1 << i) | (1 << j)); // 替換字符 i 為 j
                        }
                    }
                }
            }
        });
        return new int[]{groups, maxSize};
    }
}

