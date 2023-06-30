package WeeklyContest;

import java.util.*;

class Week_266 {
    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2062.Count%20Vowel%20Substrings%20of%20a%20String/README.md
    public int countVowelSubstrings(String word) {
        int n = word.length();
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            Set<Character> t = new HashSet<>();
            for (int j = i; j < n; ++j) {
                char c = word.charAt(j);
                if (!isVowel(c)) {
                    break;
                }
                t.add(c);  // 當前右端點對應的元音字母加入哈希表
                if (t.size() == 5) { // 元素個數為5，說明當前子字符串是一個元音子字符串
                    ans++;
                }
            }
        }
        return ans;
    }

    private boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2063.Vowels%20of%20All%20Substrings/README.md
    public long countVowels(String word) {
        long ans = 0;
        int n = word.length();
        for (int i = 0; i < n; i++) {
            char c = word.charAt(i); // 枚舉字符串的每個字符 word[i]
            // 如果 word[i] 是元音字母，
            // 設 word 的長度為 n。子字符串 word[l..r] 若要包含 word[i]，則必須滿足
            // 0≤l≤i
            // i≤r≤n−1
            // 這樣的 l 有 i+1 個，r 有 n−i 個，
            // 因此有 (i+1)*(n−i) 個子字符串，所以 word[i] 在所有子字符串中一共出現了 (i+1)*(n−i) 次。
            if (isVowel(c)) ans += (i + 1L) * (n - i);
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2064.Minimized%20Maximum%20of%20Products%20Distributed%20to%20Any%20Store/README.md
    public int minimizedMaximum(int n, int[] quantities) {
        int left = 1, right = (int) 1e5;
        while (left < right) {
            int mid = (left + right) >> 1;
            int cnt = 0;
            for (int v : quantities) cnt += (v + mid - 1) / mid; // 模擬 ceil() 方法向上取整

            // 判斷是否存在一個分配方案，使得分配給任意商店商品數目的最大值為 mid
            if (cnt <= n) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    // https://leetcode.cn/problems/maximum-path-quality-of-a-graph/solution/wei-rao-li-lun-dfsde-shi-hou-ba-jing-guo-xrja/
    Map<Integer, List<int[]>> graphM = new HashMap<>();
    int ans = 0;
    // List<Integer> path=new ArrayList<>();//用作debug
    int[] values;
    int[][] edges;
    int maxTime;

    public int maximalPathQuality(int[] values, int[][] edges, int maxTime) {
        this.values = values;
        this.edges = edges;
        this.maxTime = maxTime;
        int lenN = values.length;
        //初始化,存圖
        for (int[] edge : edges) {
            int stN = edge[0], endN = edge[1], val = edge[2];
            graphM.putIfAbsent(stN, new ArrayList<>());
            graphM.putIfAbsent(endN, new ArrayList<>());
            graphM.get(stN).add(new int[]{endN, val});
            graphM.get(endN).add(new int[]{stN, val});
        }
        dfs(0, 0, 0);
        return ans;
    }

    public void dfs(int startN, int curTime, int curValue) {
        // path.add(startN);
        int temp = values[startN];
        curValue += temp; // 計算這層目前得到的總價值

        // 題目規定：每個節點的價值至多算入價值總和中一次
        // 所以這邊訪問過節點置0，防止重複累加
        values[startN] = 0;

        // 回到了原點，更新答案
        if (startN == 0) {
            ans = Math.max(ans, curValue);
        }

        // 沒有鄰接點直接return
        if (null == graphM.get(startN)) {
            return;
        } else {
            // 遍歷所有鄰接點，計算總花費時間
            for (int[] nextN : graphM.get(startN)) {
                if (curTime + nextN[1] > maxTime) {
                    continue;
                }
                dfs(nextN[0], curTime + nextN[1], curValue);
            }
        }
        
        // 還原現場
        // path.remove(path.size()-1);
        values[startN] = temp;
        return;
    }
}

