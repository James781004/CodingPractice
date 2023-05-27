package WeeklyContest;

import java.util.Arrays;
import java.util.PriorityQueue;

class Week_288 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2231.Largest%20Number%20After%20Digit%20Swaps%20by%20Parity/README.md
    public int largestInteger(int num) {
        int[] cnt = new int[10];
        int x = num;
        while (x != 0) {
            cnt[x % 10]++;
            x /= 10;
        }
        x = num;
        int ans = 0;
        int t = 1;
        while (x != 0) {
            int v = x % 10;
            x /= 10;
            for (int y = 0; y < 10; y++) {
                if (((v ^ y) & 1) == 0 && cnt[y] > 0) {
                    cnt[y]--;
                    ans += y * t;
                    t *= 10;
                    break;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/largest-number-after-digit-swaps-by-parity/solution/-by-magic_rubik-747h/
    public int largestInteger2(int num) {
        PriorityQueue<Integer> one = new PriorityQueue<>((a, b) -> (b - a));
        PriorityQueue<Integer> two = new PriorityQueue<>((a, b) -> (b - a));
        char[] n = String.valueOf(num).toCharArray();
        for (char c : n) {
            int t = c - '0';
            if (t % 2 == 1) {
                one.offer(t);
            } else {
                two.offer(t);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (char c : n) {
            int t = c - '0';
            if (t % 2 == 1) {
                sb.append(one.poll());
            } else {
                sb.append(two.poll());
            }
        }
        return Integer.parseInt(sb.toString());
    }


    // https://leetcode.cn/problems/largest-number-after-digit-swaps-by-parity/solution/an-qi-ou-xing-jiao-huan-hou-de-zui-da-sh-29oa/
    public int largestInteger3(int num) {
        StringBuilder numStr = new StringBuilder();
        numStr.append(num); // 轉化為字符串
        int l = numStr.length();
        for (int i = 0; i < l - 1; i++) {
            for (int j = i + 1; j < l; j++) {
                // 只有下標數值奇偶相同才進行判斷
                int n = numStr.charAt(i) - '0' + numStr.charAt(j) - '0';
                if (n % 2 == 0 && numStr.charAt(i) < numStr.charAt(j)) {
                    char c = numStr.charAt(i);
                    numStr.setCharAt(i, numStr.charAt(j));
                    numStr.setCharAt(j, c);
                }
            }
        }
        return Integer.valueOf(numStr.toString());
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2232.Minimize%20Result%20by%20Adding%20Parentheses%20to%20Expression/README.md
    public String minimizeResult(String expression) {
        int idx = expression.indexOf('+');
        String l = expression.substring(0, idx);  // +左邊
        String r = expression.substring(idx + 1);  // +右邊
        int m = l.length(), n = r.length();
        int mi = Integer.MAX_VALUE;
        String ans = "";

        // +左右邊分別遍歷，枚舉左右括號的插入位置
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int c = Integer.parseInt(l.substring(i)) + Integer.parseInt(r.substring(0, j + 1));  // 括號內計算
                int a = i == 0 ? 1 : Integer.parseInt(l.substring(0, i));  // 括號左邊計算
                int b = j == n - 1 ? 1 : Integer.parseInt(r.substring(j + 1));  // 括號右邊計算
                int t = a * b * c;
                if (t < mi) {
                    mi = t;
                    ans = String.format("%s(%s+%s)%s", l.substring(0, i), l.substring(i),
                            r.substring(0, j + 1), r.substring(j + 1));
                }
            }
        }
        return ans;
    }


    // 效率高解法
    // https://leetcode.cn/problems/minimize-result-by-adding-parentheses-to-expression/solution/by-mwh-z-5nij/
    public String minimizeResult2(String expression) {
        // 直接枚舉
        int addIndex = expression.indexOf('+');
        int len = expression.length();
        int min = Integer.MAX_VALUE;
        int[] pair = new int[2];
        // 以addIndex為分界線兩邊進行分割
        for (int i = 0; i < addIndex; i++) {  // 最多走到+號前一位數字
            for (int j = addIndex + 2; j <= len; j++) {  // 從+號後第二位數字開始走
                // 括號左邊計算
                String s1 = expression.substring(0, i);
                int num1 = "".equals(s1) ? 1 : Integer.parseInt(s1);

                // 括號內計算
                String s2 = expression.substring(i, addIndex);
                int num2 = Integer.parseInt(s2);
                String s3 = expression.substring(addIndex + 1, j);
                int num3 = Integer.parseInt(s3);

                // 括號右邊計算
                String s4 = expression.substring(j, len);
                int num4 = "".equals(s4) ? 1 : Integer.parseInt(s4);
                int num = num1 * (num2 + num3) * num4;
                if (num < min) {
                    pair = new int[]{i, j};
                    min = num;
                }
            }
        }
        StringBuilder sb = new StringBuilder(expression);
        sb.insert(pair[0], '(');
        sb.insert(pair[1] + 1, ')');
        return sb.toString();
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2233.Maximum%20Product%20After%20K%20Increments/README.md
    // 貪心 + 優先隊列（小根堆）
    // 每次操作，貪心地選擇最小的元素進行加 1，共進行 k 次操作。
    // 最後累乘所有元素得到結果。注意取模操作。
    public int maximumProduct(int[] nums, int k) {
        int MOD = (int) 1e9 + 7;
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int v : nums) {
            q.offer(v);
        }
        while (k-- > 0) {
            q.offer(q.poll() + 1);
        }
        long ans = 1;
        while (!q.isEmpty()) {
            ans = (ans * q.poll()) % MOD;
        }
        return (int) ans;
    }


    // https://leetcode.cn/problems/maximum-total-beauty-of-the-gardens/solution/by-endlesscheng-10i7/
    // 核心思路：
    // 1. 如果要填充完善花園，一定是優先填種花數最多的花園，因此容易想到排序數組，然後逆序遍歷進行填充
    // 2. 如果不填充為完善花園，那就要使得所有不完善花園種花數的最小值最大
    // 3. But，填還是不填我們並不知道，需要根據full和partial的值確定，不過這個分類討論就太細了沒法做。
    //    因此！填和不填兩種情況的美麗值我們都算出來，取最大值就行了！
    // 4. 根據上述三點，我們可以逆序遍歷數組，對於每個花園，
    //    分別計算不填充為完善花園（當前花園和之前花園都不填充）的美麗值，以及當前花園填充為完善花園，然後繼續循環，在下一個花園上走相同邏輯！
    // 注意，newFlowers表示最大可種新花數目，即種的新花數目也可以小於newFlowers，甚至可以不種新花
    public long maximumBeauty(int[] flowers, long newFlowers, int target, int full, int partial) {
        // 升序
        Arrays.sort(flowers);
        int n = flowers.length;
        // 種花最少的花園已經達到了target，可直接返回結果
        if (flowers[0] >= target) return (long) n * full;

        // 前綴和，sum[i]表示前i個花園累積已經種花的數量，這個僅僅用於後續的一步計算，不是重點
        long[] sum = new long[n];
        sum[0] = flowers[0];
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + flowers[i];
        }

        // 保存最終結果
        long ans = 0;

        // 逆序遍歷，優先把種花數多的花園，填滿至target
        for (int i = n - 1; i >= 0; i--) {
            // 當前花園種花數已經>=target，不處理
            if (flowers[i] >= target) continue;

            // 當前花園種花數 < target時，此時有兩種選擇：
            // 1.當前花園以及前面花園都不填充為完善花園，使得當前花園以及前面花園種花數的最小值最大！
            // （例如partial遠大於full的情況，此時把新種花指標用在增大不完善花園的最小值上對結果增益更大，但要牢牢記住這些花園都不能填充為完善花園）
            // 2.當前花園填充成完善花園，再繼續循環處理前面花園的情況（例如full比較大的情況，此時把新種花指標用在湊完善花園上對結果增益更大）

            // 針對1，要使得當前花園以及前面花園都不能填充為完善花園，則這些花園的種花數范圍必定在[flowers[0], target - 1]內，
            // flowers[0]是種花數最小的花園，題目也說了已經種了的花不能移走，因此左邊界是flowers[0]
            // 那麼新花種完以後，這些花園的種花數最小值必定在這個范圍內，如此轉化為：在一個遞增區間內，找最後一個滿足xxx條件的數minVal，
            // 典型二分查找問題。（因為要盡可能使這個最小值最大嘛，所以要找最後一個滿足條件的！！）
            // 而這個xxx條件就是指，能否用newFlowers把當前花園以及前面花園種花數小於minVal的全都填滿到minVal
            long l = flowers[0], r = target - 1;
            while (l < r) {
                long mid = (r - l + 1) / 2 + l;
                if (check(flowers, sum, newFlowers, mid, i)) {
                    l = mid;  // 當前 mid (minVal) 可以填滿，就嘗試更大的 mid，直到找出最大左邊界 l
                } else {
                    r = mid - 1;  // 當前 mid (minVal) 無法填滿，就嘗試更小的 mid
                }
            }

            // 這裡計算了選擇1情況下的美麗值(下標 i 右半部乘上 full，加上 l * partial)，並更新全局最大美麗值
            ans = Math.max(ans, (n - i - 1) * (long) full + l * partial);

            // 繼續走選擇2讓當前位置完善，繼續循環
            newFlowers -= target - flowers[i];

            // 注意，如果newFlowers不足以把當前花園填充為美麗花園，那說明只能走選擇1了，而且後續的花園都不能填充了
            // 直接結束，返回ans！
            if (newFlowers < 0) return ans;
        }

        // 循環能走出來，說明全部花園都可以填充為完善花園
        // 處理邊界情況，也就是全部花園都填成完善花園的情況，上面循環中最後這次少計算了
        if (newFlowers >= 0) return Math.max(ans, (long) full * n);
        return ans;
    }


    // 能否用newFlowers把flowers[0, idx]中小於num的花園都填滿到minVal
    public boolean check(int[] flowers, long[] sum, long newFlowers, long minVal, int idx) {
        int l = 0, r = idx;
        // 既然要填滿所有小於minVal的元素，那就要找最後一個小於minVal的元素，繼續二分！
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (flowers[mid] < minVal) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        // 此時flowers[0,l]都是小於minVal的，計算一下填滿它們需要的花數量
        long diff = minVal * (l + 1) - sum[l];
        // 如果newFlowers夠填，返回true；不夠，返回false
        return newFlowers >= diff;
    }

}

