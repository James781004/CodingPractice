package WeeklyContest;

import java.util.Arrays;

class Week_282 {
    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2185.Counting%20Words%20With%20a%20Given%20Prefix/README.md
    public int prefixCount(String[] words, String pref) {
        int ans = 0;
        for (String w : words) {
            if (w.startsWith(pref)) {
                ++ans;
            }
        }
        return ans;
    }


    // 前綴樹
    public int prefixCount2(String[] words, String pref) {
        Trie tree = new Trie();
        for (String w : words) {
            tree.insert(w);
        }
        return tree.search(pref);
    }


    class Trie {
        private Trie[] children = new Trie[26];
        private int cnt;

        public void insert(String w) {
            Trie node = this;
            for (int i = 0; i < w.length(); ++i) {
                int j = w.charAt(i) - 'a';
                if (node.children[j] == null) {
                    node.children[j] = new Trie();
                }
                node = node.children[j];
                ++node.cnt;
            }
        }

        public int search(String pref) {
            Trie node = this;
            for (int i = 0; i < pref.length(); ++i) {
                int j = pref.charAt(i) - 'a';
                if (node.children[j] == null) {
                    return 0;
                }
                node = node.children[j];
            }
            return node.cnt;
        }
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2186.Minimum%20Number%20of%20Steps%20to%20Make%20Two%20Strings%20Anagram%20II/README.md
    public int minSteps(String s, String t) {
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) {
            ++cnt[c - 'a'];
        }
        for (char c : t.toCharArray()) {
            --cnt[c - 'a'];
        }

        int ans = 0;
        for (int v : cnt) {
            ans += Math.abs(v);
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-time-to-complete-trips/solution/er-fen-da-an-python-yi-xing-gao-ding-by-xwvs8/
    public long minimumTime(int[] time, int totalTrips) {
         /*
        對花費的時間進行二分查找
        初始最小時間為0,最大時間為只用一輛速度最快的車跑完所有旅途花費的時間
        取中間時間mid,求mid內能完成得旅途數目trips
        1.若trips>=totalTrips,表明時間還可以優化,部分車可能多跑了,向左查找right=mid
        2.若trips<totalTrips,表明時間不足,全部車出動了旅途還沒跑完,向右查找left=mid+1
        坑點:注意返回類型為long
        時間復雜度:O(logN),空間復雜度:O(1)
        */

        // 先將時間排序
        Arrays.sort(time);

        // 計算最長時間:全部旅途都用速度最快的一輛車跑完
        long right = (long) time[0] * totalTrips;
        long left = 0;

        // 進行二分查找
        while (left < right) {
            long mid = (left + right) >>> 1;
            // 計算mid時間能走的旅途次數
            long trips = 0;
            for (int t : time) {
                // 當mid時間不足以完成一趟旅途,接下來的車更不可能,退出
                if (mid < t) {
                    break;
                }
                // 累加每輛車在mid時間內能完成的旅行次數
                trips += mid / t;
            }
            // 判斷trips與totalTrips的大小關系進行轉移
            if (trips >= totalTrips) {
                // 旅途數超標,時間還可以再優化,向左查找
                right = mid;
            } else {
                // 旅途數不足,時間不足,向右查找
                left = mid + 1;
            }
        }
        // 最後left=right,任意返回一個即可
        return left;
    }


    // https://leetcode.cn/problems/minimum-time-to-finish-the-race/solution/jie-he-xing-zhi-qiao-miao-dp-by-endlessc-b963/
    public int minimumFinishTime(int[][] tires, int changeTime, int numLaps) {
        // 預處理出連續使用用一個輪胎，跑 x 圈，所用的最少時間
        // 由於 x 是個整數，因此 x 的上界為 log2(changeTime+1)+1
        // 根據題目的數據範圍，代碼實現時可將上界視為 17。
        int[] minSec = new int[18];

        // 防止在計算跑i圈的最小值時，相加上溢出
        Arrays.fill(minSec, Integer.MAX_VALUE / 2);

        // 枚舉每一個輪胎，跑i圈的最小值
        for (int[] tire : tires) {
            long time = tire[0];

            // 跑完第x圈，所用的時間，且最多跑numLaps圈
            // changeTime+tire[0] 表示跑一圈就換胎
            // 消耗 changeTime 之後就可以換相同種類輪胎，圈速時間也可以從 tire[0] 重新計算，
            // 沒必要繼續用原本愈跑愈慢的輪胎跑，所以超過 changeTime + tire[0] 就換輪胎
            for (int x = 1, sum = 0; time <= changeTime + tire[0]; x++) {
                // 不換胎，跑一圈
                sum += time;
                // 所有輪胎中，跑完第x圈，所用的最少時間
                minSec[x] = Math.min(minSec[x], sum);
                // 不換輪胎，跑完下一圈需要用的時間
                time *= tire[1];
            }
        }

        int[] f = new int[numLaps + 1]; // 表示跑 i 圈的最小耗時
        Arrays.fill(f, Integer.MAX_VALUE);
        f[0] = -changeTime; // 為方便計算，初始值 f[0]=−changeTime

        // 跑i圈，所用的最少時間
        for (int i = 1; i <= numLaps; ++i) {
            // 先跑i-j圈，剩下的j圈，用一個輪胎跑完，所用的最少時間
            // 一個輪胎，最多跑17圈
            for (int j = 1; j <= Math.min(17, i); ++j) {
                f[i] = Math.min(f[i], f[i - j] + minSec[j]);
            }
            // 最後的i-j圈，由一個新的輪胎跑，所以中間有一個換胎過程
            f[i] += changeTime;
        }
        return f[numLaps];
    }

}

