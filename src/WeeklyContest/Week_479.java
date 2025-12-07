package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Week_479 {

    // https://leetcode.cn/problems/sort-integers-by-binary-reflection/solutions/3850877/ku-han-shu-xie-fa-pythonjavacgo-by-endle-v30w/
    public int[] sortByReflection(int[] nums) {
        Integer[] arr = Arrays.stream(nums).boxed().toArray(Integer[]::new);

        Arrays.sort(arr, (a, b) -> {
            int revA = Integer.reverse(a) >>> Integer.numberOfLeadingZeros(a);
            int revB = Integer.reverse(b) >>> Integer.numberOfLeadingZeros(b);
            return revA != revB ? revA - revB : a - b;
        });

        for (int i = 0; i < nums.length; i++) {
            nums[i] = arr[i];
        }

        return nums;
    }


    // https://leetcode.cn/problems/largest-prime-from-consecutive-prime-sum/solutions/3850902/liang-chong-xie-fa-yu-chu-li-er-fen-cha-ge31e/
    private static final int MX = 1_000_000;
    private static final boolean[] isPrime = new boolean[MX + 1];
    private static final List<Integer> primes = new ArrayList<>();
    private static final List<Integer> specialPrimes = new ArrayList<>();
    private static boolean initialized = false;

    // 這樣寫比 static block 更快
    private void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false; // 0 和 1 不是質數
        for (int i = 2; i <= MX; i++) {
            if (isPrime[i]) {
                primes.add(i);
                for (long j = (long) i * i; j <= MX; j += i) {
                    isPrime[(int) j] = false; // j 是質數 i 的倍數
                }
            }
        }

        specialPrimes.add(0); // 哨兵
        int sum = 0;
        for (int p : primes) {
            sum += p;
            if (sum > MX) {
                break;
            }
            if (isPrime[sum]) {
                specialPrimes.add(sum);
            }
        }
    }

    public int largestPrime(int n) {
        init();

        // 二分找 <= n 的最大特殊質數
        int i = Collections.binarySearch(specialPrimes, n + 1);
        if (i < 0) i = ~i;
        return specialPrimes.get(i - 1);
    }


    // https://leetcode.cn/problems/total-score-of-dungeon-runs/solutions/3850868/gong-xian-fa-qian-zhui-he-er-fen-cha-zha-uwu2/
    public long totalScore(int hp, int[] damage, int[] requirement) {
        int n = damage.length;
        int[] sum = new int[n + 1];
        long ans = 0;
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + damage[i];
            int low = sum[i + 1] + requirement[i] - hp;
            // 本題 sum 是嚴格遞增的，沒有重復元素，可以用 Arrays.binarySearch
            int j = Arrays.binarySearch(sum, 0, i + 1, low); // 在 [0, i] 中二分
            if (j < 0) j = ~j;
            ans += i - j + 1;
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-subgraph-score-in-a-tree/solutions/3850874/huan-gen-dppythonjavacgo-by-endlesscheng-y5tw/
    public int[] maxSubgraphScore(int n, int[][] edges, int[] good) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        // subScore[x] 表示（以 0 為根時）子樹 x 的最大得分（一定包含節點 x）
        int[] subScore = new int[n];
        dfs(0, -1, g, good, subScore);

        int[] ans = new int[n];
        reroot(0, -1, 0, g, subScore, ans);
        return ans;
    }

    // 計算並返回 subScore[x]
    private int dfs(int x, int fa, List<Integer>[] g, int[] good, int[] subScore) {
        for (int y : g[x]) {
            if (y != fa) {
                // 如果子樹 y 的得分是負數，不選子樹 y，否則選子樹 y
                subScore[x] += Math.max(dfs(y, x, g, good, subScore), 0);
            }
        }
        subScore[x] += good[x] == 1 ? 1 : -1; // subScore[x] 一定包含 x
        return subScore[x];
    }

    // 計算子圖 x 的最大得分 scoreX，其中 faScore 表示來自父節點 fa 的最大得分（一定包含節點 fa）
    private void reroot(int x, int fa, int faScore, List<Integer>[] g, int[] subTreeScore, int[] ans) {
        int scoreX = subTreeScore[x] + Math.max(faScore, 0);
        ans[x] = scoreX;
        for (int y : g[x]) {
            if (y != fa) {
                // scoreX-max(subTreeScore[y],0) 是不含子樹 y 的最大得分
                reroot(y, x, scoreX - Math.max(subTreeScore[y], 0), g, subTreeScore, ans);
            }
        }
    }


}









