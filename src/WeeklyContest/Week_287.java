package WeeklyContest;

import java.util.*;

class Week_287 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2224.Minimum%20Number%20of%20Operations%20to%20Convert%20Time/README.md
    public int convertTime(String current, String correct) {
        int a = Integer.parseInt(current.substring(0, 2)) * 60
                + Integer.parseInt(current.substring(3));
        int b = Integer.parseInt(correct.substring(0, 2)) * 60
                + Integer.parseInt(correct.substring(3));
        int ans = 0, d = b - a;  // 轉化為分鐘差之後，從大到小開始分配
        for (int i : Arrays.asList(60, 15, 5, 1)) {
            ans += d / i;
            d %= i;
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2225.Find%20Players%20With%20Zero%20or%20One%20Losses/README.md
    public List<List<Integer>> findWinners(int[][] matches) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int[] m : matches) {
            int win = m[0], lose = m[1];
            cnt.putIfAbsent(win, 0);
            cnt.put(lose, cnt.getOrDefault(lose, 0) + 1);
        }

        List<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());
        ans.add(new ArrayList<>());
        for (Map.Entry<Integer, Integer> entry : cnt.entrySet()) {
            int u = entry.getKey();
            int v = entry.getValue();
            if (v < 2) {  // 輸不超過1次的直接放進對應位置
                ans.get(v).add(u);
            }
        }
        Collections.sort(ans.get(0));
        Collections.sort(ans.get(1));
        return ans;
    }


    // https://leetcode.cn/problems/maximum-candies-allocated-to-k-children/solution/by-endlesscheng-y031/
    public int maximumCandies(int[] candies, long k) {
        // 二分法
        int max = candies[0];
        for (int candy : candies) max = Math.max(max, candy);
        // 每個孩子最多能分max個糖果
        int left = 0, right = max;
        while (left < right) {  // 左閉右開，找符合條件的最左位置
            // 右邊主動收縮應該主動偏右
            int mid = left + (right - left + 1) / 2;
            if (getKids(candies, mid) < k) {
                // 減治法:不行的先排除
                // 不夠分這麼多孩子,每個孩子分少點->向左查找
                right = mid - 1;
            } else {
                // 可能剛好夠分或者多了->向右查找
                left = mid;
            }
        }
        return left;
    }

    // 當每個孩子分candiesNum顆糖果時最多能分多少個孩子
    private long getKids(int[] candies, int candiesNum) {
        long count = 0;
        for (int candy : candies) {
            count += candy / candiesNum;
        }
        return count;
    }


    // https://leetcode.cn/problems/encrypt-and-decrypt-strings/solution/by-endlesscheng-sm8h/
    class Encrypter {
        String[] map = new String[26];
        Map<String, Integer> cnt = new HashMap<>();

        public Encrypter(char[] keys, String[] values, String[] dictionary) {
            for (int i = 0; i < keys.length; i++) map[keys[i] - 'a'] = values[i];
            // 加密 dictionary 中的每個字符串，並用哈希表記錄每個加密後的字符串的出現次數。
            // 這樣每次調用 decrypt 時，返回哈希表中 word2 的出現次數即可。
            for (String s : dictionary) {
                String e = encrypt(s);
                cnt.put(e, cnt.getOrDefault(e, 0) + 1);
            }
        }

        public String encrypt(String word1) {
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < word1.length(); i++) {
                String s = map[word1.charAt(i) - 'a'];
                if (s == null) return "";
                res.append(s);
            }
            return res.toString();
        }

        public int decrypt(String word2) {
            return cnt.getOrDefault(word2, 0);
        }
    }

}

