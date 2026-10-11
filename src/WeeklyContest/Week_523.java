package WeeklyContest;

import java.util.*;

public class Week_523 {

    // https://leetcode.cn/problems/three-fibonacci-sum/solutions/4041618/yu-chu-li-hou-o1-shi-jian-pythonjavacgo-kcewq/
    static class ThreeFibonacciSum {
        private static final Set<Integer> fibSet = new HashSet<>();

        static {
            int a = 0;
            int b = 1;
            while (b < 1e9) {
                int x = a + b;
                a = b;
                b = x;
                fibSet.add(b);
            }
        }

        public boolean threeFibonacciSum(int n) {
            return n % 2 == 0 && fibSet.contains(n / 2);
        }
    }

    // https://leetcode.cn/problems/prime-subset-selection-i/solutions/4041636/yu-chu-li-hou-olog-shi-jian-pythonjavacg-12xf/
    static class MaxPrimes {
        private static final int MX = 1_000_001;
        private static final boolean[] isPrime = new boolean[MX];
        private static final List<Integer> primes = new ArrayList<>();
        private static final List<Long> sum = new ArrayList<>();
        private static boolean initialized = false;

        // 這樣寫比 static block 快
        public MaxPrimes() {
            if (initialized) {
                return;
            }
            initialized = true;

            Arrays.fill(isPrime, true);
            long s = 0;
            for (int i = 2; i < MX; i++) {
                if (isPrime[i]) {
                    primes.add(i);
                    s += i;
                    sum.add(s);
                    for (long j = (long) i * i; j < MX; j += i) {
                        isPrime[(int) j] = false; // j 是質數 i 的倍數
                    }
                }
            }
        }

        public List<Integer> maxPrimes(int n, int s) {
            int i = Collections.binarySearch(primes, n + 1);
            if (i < 0) i = ~i; // 見 Collections.binarySearch 源碼

            int j = Collections.binarySearch(sum.subList(0, i), (long) s + 1);
            if (j < 0) j = ~j;

            return primes.subList(0, j);
        }
    }


    // https://leetcode.cn/problems/design-bus-seat-booking-system/solutions/4041616/nao-jin-ji-zhuan-wan-pythonjavacgo-by-en-xx1l/
    static class BusBooking {
        private final int[] states;
        private final int[][] times;
        private int totalTime;
        private int bookedNum;

        public BusBooking(int n) {
            states = new int[n + 1];
            times = new int[n + 1][4];
        }

        public void toggle(String seat) {
            int row = Integer.parseInt(seat.substring(1));
            int col = seat.charAt(0) - 'A';
            int mask = states[row];

            if ((mask >> col & 1) == 0) {
                // 預訂
                int time = col == 0 && (mask >> 1 & 1) > 0 || col == 3 && (mask >> 2 & 1) > 0 ? 3 : 1;
                totalTime += time;
                times[row][col] = time; // 記錄預訂該座位所需的秒數，將來釋放時用
                bookedNum++;
            } else {
                // 釋放
                totalTime -= times[row][col];
                times[row][col] = 0;
                bookedNum--;
            }

            states[row] ^= 1 << col;
        }

        public int getTotalTime() {
            return totalTime;
        }

        public int getMinTime() {
            return bookedNum;
        }
    }


    // https://leetcode.cn/problems/prime-subset-selection-ii/solutions/4041612/0-1-bei-bao-shu-chu-ju-ti-fang-an-python-x6g1/
    static class BusBookingII {
        private static final int MX = 1001;
        private static final boolean[] isPrime = new boolean[MX];
        private static final List<Integer> primes = new ArrayList<>();
        private static boolean initialized = false;

        // 這樣寫比 static block 快
        public BusBookingII() {
            if (initialized) {
                return;
            }
            initialized = true;

            Arrays.fill(isPrime, true);
            for (int i = 2; i < MX; i++) {
                if (isPrime[i]) {
                    primes.add(i);
                    for (long j = (long) i * i; j < MX; j += i) {
                        isPrime[(int) j] = false; // j 是質數 i 的倍數
                    }
                }
            }
        }

        public List<Integer> maxPrimeSubset(int n, int s) {
            int m = Collections.binarySearch(primes, n + 1);
            if (m < 0) m = ~m; // 見 Collections.binarySearch 源碼

            int[][] f = new int[m + 1][s + 1];
            for (int[] row : f) {
                Arrays.fill(row, Integer.MAX_VALUE / 2);
            }
            f[m][0] = 0;

            // 倒著 DP，這樣可以正序輸出方案，且保證越靠前的質數越小
            for (int i = m - 1; i >= 0; i--) {
                int x = primes.get(i);
                for (int j = 0; j <= s; j++) {
                    if (j < x) {
                        f[i][j] = f[i + 1][j];
                    } else {
                        f[i][j] = Math.min(f[i + 1][j], f[i + 1][j - x] + 1);
                    }
                }
            }

            int j = s;
            while (j > 0 && f[0][j] == Integer.MAX_VALUE / 2) {
                j--;
            }

            List<Integer> ans = new ArrayList<>();
            for (int i = 0; j > 0; i++) {
                int x = primes.get(i);
                // 靠前的質數越小越好，f[i+1][j-x]+1 = f[i+1][j] 的時候要選
                if (f[i + 1][j - x] + 1 <= f[i + 1][j]) {
                    ans.add(x);
                    j -= x;
                }
            }

            return ans;
        }
    }


}










