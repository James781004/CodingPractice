package WeeklyContest;

import java.util.Arrays;

public class Week_304 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2357.Make%20Array%20Zero%20by%20Subtracting%20Equal%20Amounts/README.md
    // 每一次操作，都可以把數組 nums 中相同且非零的元素減少到 0
    // 因此，我們只需要統計數組 nums 中有多少個不同的非零元素，即為最少操作數。
    public int minimumOperations(int[] nums) {
        boolean[] s = new boolean[101];
        s[0] = true;
        int ans = 0;
        for (int x : nums) {
            if (!s[x]) {
                ++ans;
                s[x] = true;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-number-of-groups-entering-a-competition/solution/pai-xu-tan-xin-mo-ni-by-endlesscheng-zq8m/
    public int maximumGroups(int[] grades) {
        long len = grades.length;
        long ans = 1;
        long l = 1, r = len;
        while (l <= r) {
            long m = ((r - l) >> 1) + l; // 假設分成m組
            long need = (1 + m) * m / 2;// 分m組至少need個人，等差數列求和。
            if (need > len) { // 人不夠
                r = m - 1;// 大了就縮
            } else { // 人有富余
                ans = m;// 先收了再說，可能是這個，也有可能存在一個大於當前m，但是仍然滿足need<=len的數，需要向右找，所以
                l = m + 1; // 可以再擠一擠，試一試， 富余的人還有可能多分出組，需要繼續嘗試
            }
        }
        return (int) ans;
    }

    // 公式解
    // 其實就是排序後，第1個人一組，2~3兩個人一組，然後4~6三個人一組......k個人一組，
    // 直到這些組的 累加人數總和 為不超過 n 的最大值
    // 第一組 1 個人，第二組 2 個人，第三組 3 個人...第 k 組 k 個人。
    // 所有組的人數總和：1+2+3+4+..+k = (k*(k + 1))/2 不超過總人數 n
    // 因此答案就是滿足 (k*(k + 1))/2  ≤ n  的最大的 k，湊個完全平方式取正解即可。
    public int maximumGroups2(int[] grades) {
        //學生總人數
        int n = grades.length;

        //排序都不需要了，只需要結果
        double ans = Math.sqrt(0.25 + 2 * n) - 0.5;

        //取下整返回
        return (int) Math.floor(ans);
    }


    // https://leetcode.cn/problems/find-closest-node-to-given-two-nodes/solution/ji-suan-dao-mei-ge-dian-de-ju-chi-python-gr2u/
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int[] d1 = calcDis(edges, node1), d2 = calcDis(edges, node2);
        int ans = -1, n = edges.length;
        for (int i = 0, minDis = n; i < n; ++i) {
            int d = Math.max(d1[i], d2[i]);
            if (d < minDis) {
                minDis = d;
                ans = i;
            }
        }
        return ans;
    }

    int[] calcDis(int[] edges, int x) {
        int n = edges.length;
        int[] dis = new int[n];
        Arrays.fill(dis, n);
        for (int d = 0; x >= 0 && dis[x] == n; x = edges[x])
            dis[x] = d++;
        return dis;
    }


    // https://leetcode.cn/problems/longest-cycle-in-a-graph/solution/nei-xiang-ji-huan-shu-zhao-huan-li-yong-pmqmr/
    // 初始時間戳 clock=1，首次訪問一個點 x 時，記錄訪問這個點的時間 time[x]=clock，然後將 clock 加一。
    // 如果首次訪問一個點，則記錄當前時間 startTime=clock，並嘗試從這個點出發，看能否找到環。
    // 如果找到了一個之前訪問過的點 x，且之前訪問 x 的時間不早於 startTime，
    // 則說明我們找到了一個新的環，此時的環長就是前後兩次訪問 x 的時間差，即 clock−time[x]。
    public int longestCycle(int[] edges) {
        int n = edges.length, ans = -1;
        int[] time = new int[n];
        for (int i = 0, clock = 1; i < n; ++i) {
            if (time[i] > 0) continue;
            for (int x = i, startTime = clock; x >= 0; x = edges[x]) {
                if (time[x] > 0) { // 重復訪問
                    if (time[x] >= startTime) // 找到了一個新的環
                        ans = Math.max(ans, clock - time[x]);
                    break;
                }
                time[x] = clock++;
            }
        }
        return ans;
    }
}
