package WeeklyContest;

import java.util.*;
import java.util.stream.IntStream;

public class Week_290 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2248.Intersection%20of%20Multiple%20Arrays/README.md
    public List<Integer> intersection(int[][] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        List<Integer> ans = new ArrayList<>();
        for (int[] arr : nums) {
            for (int x : arr) {
                if (cnt.merge(x, 1, Integer::sum) == nums.length) {
                    ans.add(x);
                }
            }
        }

        Collections.sort(ans);
        return ans;
    }

    // 題目範圍 1 <= nums.length <= 1000
    // 可以用數組代替Map
    public List<Integer> intersection2(int[][] nums) {
        int[] cnt = new int[1001];
        for (int[] arr : nums) {
            for (int x : arr) {
                ++cnt[x];
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int x = 0; x < 1001; ++x) {
            if (cnt[x] == nums.length) {
                ans.add(x);
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2249.Count%20Lattice%20Points%20Inside%20a%20Circle/README.md
    public int countLatticePoints(int[][] circles) {
        // 將所有圓的最大橫縱坐標求出來，作為枚舉的上界
        int mx = 0, my = 0;
        for (int[] c : circles) {
            mx = Math.max(mx, c[0] + c[2]);
            my = Math.max(my, c[1] + c[2]);
        }

        // 枚舉所有的格點，判斷其是否在圓內，如果在圓內，則答案加一
        int ans = 0;
        for (int i = 0; i <= mx; ++i) {
            for (int j = 0; j <= my; ++j) {
                for (int[] c : circles) {
                    int dx = i - c[0], dy = j - c[1];
                    if (dx * dx + dy * dy <= c[2] * c[2]) {
                        ++ans;
                        break;
                    }
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-number-of-rectangles-containing-each-point/solution/pai-xu-pai-xu-pai-xu-pythonjavacgo-by-en-ou4k/
    // 基本思路就是對每個點 (Xi, Yi)，統計橫坐標不小於 Xi 且縱坐標不小於 Yi 的矩形個數。
    public int[] countRectangles(int[][] rectangles, int[][] points) {
        // 將 rectangles 和 points 都按照縱坐標從大到小排序
        Arrays.sort(rectangles, (a, b) -> b[1] - a[1]);

        int n = points.length;
        Integer[] ids = IntStream.range(0, n).boxed().toArray(Integer[]::new);
        Arrays.sort(ids, (i, j) -> points[j][1] - points[i][1]);

        int[] ans = new int[n];
        List<Integer> xs = new ArrayList<>();
        int i = 0;
        for (int id : ids) {
            int start = i;

            // 遍歷每個點 (Xi, Yi)，將所有縱坐標不小於 Yi 的矩形的橫坐標加入一個有序列表 xs。
            while (i < rectangles.length && rectangles[i][1] >= points[id][1]) {
                xs.add(rectangles[i++][0]);
            }
            if (start < i) Collections.sort(xs); // 只有在 xs 插入了新元素時才排序

            // 然後在  xs 中二分即可算出橫坐標不小於 Xi 的矩形個數，
            // 由於是按縱坐標從大到小遍歷的，因此這些矩形的縱坐標均不小於 Yi ，
            // 因此這些矩形均包含點 (Xi, Yi)
            ans[id] = i - lowerBound(xs, points[id][0]);
        }
        return ans;
    }


    // 按行統計 + 二分查找
    public int[] countRectangles2(int[][] rectangles, int[][] points) {
        List<Integer>[] xs = new List[101];
        Arrays.setAll(xs, e -> new ArrayList<>());
        for (int[] r : rectangles) xs[r[1]].add(r[0]);
        for (List<Integer> x : xs) Collections.sort(x);

        int n = points.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = points[i][1]; j <= 100; j++) {
                ans[i] += xs[j].size() - lowerBound(xs[j], points[i][0]);
            }
        }
        return ans;
    }

    int lowerBound(List<Integer> xs, int x) {
        int left = 0, right = xs.size();
        while (left < right) {
            int mid = (left + right) / 2;
            if (xs.get(mid) >= x) right = mid;
            else left = mid + 1;
        }
        return left;
    }


    // https://leetcode.cn/problems/number-of-flowers-in-full-bloom/solution/chai-fen-pythonjavacgo-by-endlesscheng-wz35/
    public int[] fullBloomFlowers(int[][] flowers, int[] persons) {
        // 差分+排序優化
        int n = persons.length;
        int[] res = new int[n];

        // 構建離散化差分映射
        // 用變化量表示一段區間上的更新，即在時間點 start[i] 變化量增加了 1，在時間點 end[i] +1 變化量減少了 1（類比導數的概念）
        // 遍歷 flowers，統計這些區間端點產生的變化量，記錄在有序集合 diff 中。
        Map<Integer, Integer> diff = new HashMap<>();
        for (int[] flower : flowers) {
            diff.put(flower[0], diff.getOrDefault(flower[0], 0) + 1);
            diff.put(flower[1] + 1, diff.getOrDefault(flower[1] + 1, 0) - 1);
        }

        // 提取有變化的時刻並排序
//        var times = diff.keySet().stream().mapToInt(Integer::intValue).sorted().toArray();
        List<Integer> times = new ArrayList<>(diff.keySet());
        times.sort(Comparator.comparingInt(a -> a));

        // 這裡返回的結果要還要保證persons原來的順序,因此建立索引與數據映射
        int[][] p = new int[n][2];
        for (int i = 0; i < n; i++) {
            p[i][0] = i;
            p[i][1] = persons[i];
        }

        // 快速計算每個人的答案，我們需要將 person 從小到大排序，這樣可以在遍歷 person 的同時從小到大遍歷 diff
        // 按照p[1]升序排列.也就是越早來的人排在越前面,同時保留索引信息
        Arrays.sort(p, Comparator.comparingInt(a -> a[1]));

        // t表示花數目有變化的時刻,sum表示差分在當前處的累計值
        int t = 0, sum = 0;

        // 遍歷 person 的同時從小到大遍歷 diff，累加變化量（類比積分的概念）
        // 第 i 個人到達時，花的數目即為不超過 person[i] 時間點的變化量的累加值。
        for (int[] pair : p) {
            // 尋找在person[i]之前的變化量,從而實現局部求解當前person擁有花的數目
            while (t < times.size() && times.get(t) <= pair[1]) {
                // 累加進結果
                sum += diff.get(times.get(t++));
            }
            // 在對應的person處寫入當前sum
            res[pair[0]] = sum;
        }
        return res;
    }

    // 轉換 + 二分
    // 第 i 個人能看到的花的數目，
    // 等價於 start 不晚於 persons[i] 的花的數目，減去 end 早於 persons[i] 的花的數目，
    // 即開花數減去凋落數。
    // 所以單獨統計開花時間和凋落時間，排序後二分就得到了答案。
    public int[] fullBloomFlowers2(int[][] flowers, int[] persons) {
        int[] starts = Arrays.stream(flowers).mapToInt(f -> f[0]).sorted().toArray();
        int[] ends = Arrays.stream(flowers).mapToInt(f -> f[1]).sorted().toArray();
        return Arrays.stream(persons).map(p -> lowerBound(starts, p + 1) - lowerBound(ends, p)).toArray();
    }

    int lowerBound(int[] arr, int x) {
        int left = 0, right = arr.length;
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (arr[mid] >= x) right = mid;
            else left = mid + 1;
        }
        return left;
    }
}