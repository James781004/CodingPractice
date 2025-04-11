package WeeklyContest;

import java.util.*;

public class Week_444 {

    // https://leetcode.cn/problems/minimum-pair-removal-to-sort-array-i/solutions/3642233/bao-li-by-dui-tang-jiang-de-su-da-shui-8w6l/
    int min = 50001;
    int start = 0;

    public int minimumPairRemoval(int[] nums) {
        int count = 0;
        while (!isNonDecreasing(nums)) {
            count += 1;
            nums = getMerge(nums);
        }
        return count;
    }

    boolean isNonDecreasing(int[] nums) {
        boolean res = true;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) res = false;
            int tmp = nums[i] + nums[i - 1];
            if (tmp < min) {
                min = tmp;
                start = i - 1;
            }
        }
        return res;
    }

    int[] getMerge(int[] nums) {
        int[] res = new int[nums.length - 1];
        int j = 0;
        for (int i = 0; i < nums.length && j < nums.length - 1; i++) {
            if (i == start) {
                res[j++] = min;
                min = 50001;
                i++;
            } else res[j++] = nums[i];
        }
        return res;
    }


    // https://leetcode.cn/problems/implement-router/solutions/3641772/mo-ni-ha-xi-biao-dui-lie-er-fen-cha-zhao-y7l7/
    class Router {
        private record Packet(int source, int destination, int timestamp) {
        }

        private record Queue(List<Integer> timestamps, int head) {
        }

        private final int memoryLimit;
        private final Deque<Packet> packetQ = new ArrayDeque<>(); // Packet 隊列
        private final Set<Packet> packetSet = new HashSet<>(); // Packet 集合
        private final Map<Integer, Queue> destToTimestamps = new HashMap<>(); // destination -> [[timestamp], head]

        public Router(int memoryLimit) {
            this.memoryLimit = memoryLimit;
        }

        public boolean addPacket(int source, int destination, int timestamp) {
            Packet packet = new Packet(source, destination, timestamp);
            if (!packetSet.add(packet)) {
                return false;
            }
            if (packetQ.size() == memoryLimit) { // 太多了
                forwardPacket();
            }
            packetQ.offer(packet); // 入隊
            destToTimestamps.computeIfAbsent(destination, k -> new Queue(new ArrayList<>(), 0)).timestamps.add(timestamp);
            return true;
        }

        public int[] forwardPacket() {
            if (packetQ.isEmpty()) {
                return new int[]{};
            }
            Packet packet = packetQ.poll(); // 出隊
            packetSet.remove(packet);
            destToTimestamps.compute(packet.destination, (k, q) -> new Queue(q.timestamps, q.head + 1)); // 隊首下標加一，模擬出隊
            return new int[]{packet.source, packet.destination, packet.timestamp};
        }

        public int getCount(int destination, int startTime, int endTime) {
            Queue q = destToTimestamps.get(destination);
            if (q == null) {
                return 0;
            }
            int left = lowerBound(q.timestamps, startTime, q.head - 1);
            int right = lowerBound(q.timestamps, endTime + 1, q.head - 1);
            return right - left;
        }

        private int lowerBound(List<Integer> nums, int target, int left) {
            int right = nums.size();
            while (left + 1 < right) {
                int mid = (left + right) >>> 1;
                if (nums.get(mid) >= target) {
                    right = mid;
                } else {
                    left = mid;
                }
            }
            return right;
        }
    }


    // https://leetcode.cn/problems/maximum-product-of-subsequences-with-an-alternating-sum-equal-to-k/solutions/3641861/bitset-you-hua-dp-by-time-v5-thdr/
    public int maxProduct(int[] nums, int k, int limit) {
        int s = 0;
        for (int v : nums) {
            s += v;
        }
        if (s < Math.abs(k)) {
            return -1;
        }
        // 由於存在負數，所有交錯和都要 + s
        BitSet[] pdp0 = new BitSet[limit + 2], pdp1 = new BitSet[limit + 2];// 方便狀態轉移
        BitSet[] dp0 = new BitSet[limit + 2], dp1 = new BitSet[limit + 2];
        for (int i = 0; i <= limit + 1; i++) {
            pdp0[i] = new BitSet(s * 2 + 1);
            pdp1[i] = new BitSet(s * 2 + 1);
            dp0[i] = new BitSet(s * 2 + 1);
            dp1[i] = new BitSet(s * 2 + 1);
        }
        int t = 0;
        for (int v : nums) {
            // 設計 limit + 1 可以解決 0 的影響
            for (int i = 0; i <= limit + 1; i++) {
                int x = Math.min(i * v, limit + 1);
                // dp1[x] 的 s - t + v ~ s + t + v 依賴 pdp0[i] 的 s - t ~ s + t
                dp1[x].or(pdp0[i], s - t, s + t, s - t + v, s + t + v);

                // dp0[x] 的 s - t - v ~ s + t - v 依賴 pdp1[i] 的 s - t ~ s + t
                dp0[x].or(pdp1[i], s - t, s + t, s - t - v, s + t - v);
            }
            dp1[Math.min(v, limit + 1)].insert(s + v);// 只選擇 v 這個數
            t += v;
            for (int i = 0; i <= limit + 1; i++) {
                // 類似滾動數組優化
                pdp0[i].or(dp0[i], s - t, s + t, s - t, s + t);
                pdp1[i].or(dp1[i], s - t, s + t, s - t, s + t);
            }
        }
        for (int i = limit, j = s + k; i >= 0; i--) {
            if (dp0[i].contains(j) || dp1[i].contains(j)) {
                return i;
            }
        }
        return -1;
    }

    class BitSet {
        private int[] mag;
        private int END;
        private int n;

        public BitSet(int MAXV) {
            n = MAXV / 32 + 1;
            mag = new int[n];
            END = -1 >>> (31 - MAXV % 32);
        }

        public boolean contains(int v) {
            return (mag[v / 32] & (1 << (v % 32))) != 0;
        }

        public void insert(int v) {
            mag[v / 32] |= 1 << (v % 32);
        }

        public void delete(int v) {
            mag[v / 32] &= ~(1 << (v % 32));
        }

        // 指定區間設置為 0
        public void clear(int l, int r) {
            int i0 = l / 32, j0 = l % 32, i1 = r / 32, j1 = r % 32;
            int v0 = (mag[i0] >>> j0 << j0) ^ mag[i0], v1 = (mag[i1] << (31 - j1) >>> (31 - j1)) ^ mag[i1];
            if (i0 == i1) {
                mag[i0] = v0 | v1;
                return;
            }
            mag[i0] = v0;
            mag[i1] = v1;
            for (int i = i0 + 1; i < i1; i++) {
                mag[i] = 0;
            }
        }

        // 指定區間設置為 1
        public void full(int l, int r) {
            int i0 = l / 32, j0 = l % 32, i1 = r / 32, j1 = r % 32;
            if (i0 == i1) {
                mag[i0] |= (j1 == 31 ? -1 : (1 << (j1 + 1) - 1)) ^ (j0 == 31 ? -1 : (1 << (j0 + 1) - 1));
                return;
            }
            mag[i0] |= -1 >>> j0 << j0;
            mag[i1] |= -1 >>> (31 - j1);
            for (int i = i0 + 1; i < i1; i++) {
                mag[i] = -1;
            }
        }

        // 翻轉指定區間
        public void flip(int l, int r) {
            int i0 = l / 32, j0 = l % 32, i1 = r / 32, j1 = r % 32;
            mag[i0] ^= -1 >>> j0 << j0;
            mag[i1] ^= -1 >>> (31 - j1);
            if (i0 == i1) {
                mag[i0] ^= -1;
                return;
            }
            for (int i = i0 + 1; i < i1; i++) {
                mag[i] ^= -1;
            }
        }

        // 左移 b 位
        public void shiftLeft(int b) {
            int t = b / 32, d = b % 32;
            for (int i = n - 1; i >= n - t; i--) {
                mag[i] = 0;
            }
            for (int i = n - t - 1; i >= 0; i--) {
                mag[i + t] = mag[i];
            }
            for (int i = t - 1; i >= 0; i--) {
                mag[i] = 0;
            }
            if (d > 0) {
                mag[n - 1] <<= d;
                for (int i = n - 2; i >= t; i--) {
                    mag[i + 1] |= mag[i] >>> (32 - d);
                    mag[i] <<= d;
                }
            }
            mag[n - 1] &= END;
        }

        // 右移 b 位
        public void shiftRight(int b) {
            int t = b / 32, d = b % 32;
            for (int i = 0; i < t; i++) {
                mag[i] = 0;
            }
            for (int i = t; i < n; i++) {
                mag[i - t] = mag[i];
            }
            for (int i = n - t; i < n; i++) {
                mag[i] = 0;
            }
            if (d > 0) {
                mag[0] >>>= d;
                for (int i = 1; i < n - t; i++) {
                    mag[i - 1] |= mag[i] << (32 - d);
                    mag[i] >>>= d;
                }
            }
        }

        // 將 st 的 sl ~ sr 的狀態 與 當前對象的 l ~ r 的狀態進行 與 運算
        public void and(BitSet st, int sl, int sr, int l, int r) {
            int i0 = sl / 32, j0 = sl % 32, i1 = sr / 32, j1 = sr % 32, m = i1 - i0 + 2;
            int[] tmp = new int[m];
            for (int i = 0, j = i0; j <= i1; i++, j++) {
                tmp[i] = st.mag[j];
            }
            tmp[0] = tmp[0] >>> j0 << j0;
            tmp[m - 2] = tmp[m - 2] << (31 - j1) >>> (31 - j1);
            int t0 = l % 32;
            if (t0 < j0) {
                int d = j0 - t0;
                tmp[0] >>>= d;
                for (int i = 1; i < m - 1; i++) {
                    tmp[i - 1] |= tmp[i] << (32 - d);
                    tmp[i] >>>= d;
                }
            } else if (t0 > j0) {
                int d = t0 - j0;
                for (int i = m - 2; i >= 0; i--) {
                    tmp[i + 1] |= tmp[i] >>> (32 - d);
                    tmp[i] <<= d;
                }
            }
            i0 = l / 32;
            j0 = l % 32;
            i1 = r / 32;
            j1 = r % 32;
            int v0 = (mag[i0] >>> j0 << j0) ^ mag[i0], v1 = (mag[i1] << (31 - j1) >>> (31 - j1)) ^ mag[i1];
            for (int i = 0, j = i0; j <= i1; i++, j++) {
                mag[j] &= tmp[i];
            }
            mag[i0] |= v0;
            mag[i1] |= v1;
        }

        // 將 st 的 sl ~ sr 的狀態 與 當前對象的 l ~ r 的狀態進行 或 運算
        public void or(BitSet st, int sl, int sr, int l, int r) {
            int i0 = sl / 32, j0 = sl % 32, i1 = sr / 32, j1 = sr % 32, m = i1 - i0 + 2;
            int[] tmp = new int[m];
            for (int i = 0, j = i0; j <= i1; i++, j++) {
                tmp[i] = st.mag[j];
            }
            tmp[0] = tmp[0] >>> j0 << j0;
            tmp[m - 2] = tmp[m - 2] << (31 - j1) >>> (31 - j1);
            int t0 = l % 32;
            if (t0 < j0) {
                int d = j0 - t0;
                tmp[0] >>>= d;
                for (int i = 1; i < m - 1; i++) {
                    tmp[i - 1] |= tmp[i] << (32 - d);
                    tmp[i] >>>= d;
                }
            } else if (t0 > j0) {
                int d = t0 - j0;
                for (int i = m - 2; i >= 0; i--) {
                    tmp[i + 1] |= tmp[i] >>> (32 - d);
                    tmp[i] <<= d;
                }
            }
            i0 = l / 32;
            i1 = r / 32;
            for (int i = 0, j = i0; j <= i1; i++, j++) {
                mag[j] |= tmp[i];
            }
        }

        // 將 st 的 sl ~ sr 的狀態 與 當前對象的 l ~ r 的狀態進行 異或 運算
        public void xor(BitSet st, int sl, int sr, int l, int r) {
            int i0 = sl / 32, j0 = sl % 32, i1 = sr / 32, j1 = sr % 32, m = i1 - i0 + 2;
            int[] tmp = new int[m];
            for (int i = 0, j = i0; j <= i1; i++, j++) {
                tmp[i] = st.mag[j];
            }
            tmp[0] = tmp[0] >>> j0 << j0;
            tmp[m - 2] = tmp[m - 2] << (31 - j1) >>> (31 - j1);
            int t0 = l % 32;
            if (t0 < j0) {
                int d = j0 - t0;
                tmp[0] >>>= d;
                for (int i = 1; i < m - 1; i++) {
                    tmp[i - 1] |= tmp[i] << (32 - d);
                    tmp[i] >>>= d;
                }
            } else if (t0 > j0) {
                int d = t0 - j0;
                for (int i = m - 2; i >= 0; i--) {
                    tmp[i + 1] |= tmp[i] >>> (32 - d);
                    tmp[i] <<= d;
                }
            }
            i0 = l / 32;
            i1 = r / 32;
            for (int i = 0, j = i0; j <= i1; i++, j++) {
                mag[j] ^= tmp[i];
            }
        }
    }


    // https://leetcode.cn/problems/minimum-pair-removal-to-sort-array-ii/solutions/3641787/mo-ni-liang-ge-you-xu-ji-he-by-endlessch-gx8s/
    private record Pair(long s, int i) {
    }

    public int minimumPairRemovalII(int[] nums) {
        int n = nums.length;
        // (相鄰元素和，左邊那個數的下標)
        TreeSet<Pair> pairs = new TreeSet<>((a, b) -> a.s != b.s ? Long.compare(a.s, b.s) : a.i - b.i);
        int dec = 0; // 遞減的相鄰對的個數
        for (int i = 0; i < n - 1; i++) {
            int x = nums[i];
            int y = nums[i + 1];
            if (x > y) {
                dec++;
            }
            pairs.add(new Pair(x + y, i));
        }

        // 剩余下標
        TreeSet<Integer> idx = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            idx.add(i);
        }

        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = nums[i];
        }
        int ans = 0;
        while (dec > 0) {
            ans++;

            // 刪除相鄰元素和最小的一對
            Pair p = pairs.pollFirst();
            long s = p.s;
            int i = p.i;

            // (當前元素，下一個數)
            int nxt = idx.higher(i);
            if (a[i] > a[nxt]) { // 舊數據
                dec--;
            }

            // (前一個數，當前元素)
            Integer pre = idx.lower(i);
            if (pre != null) {
                if (a[pre] > a[i]) { // 舊數據
                    dec--;
                }
                if (a[pre] > s) { // 新數據
                    dec++;
                }
                pairs.remove(new Pair(a[pre] + a[i], pre));
                pairs.add(new Pair(a[pre] + s, pre));
            }

            // (下一個數，下下一個數)
            Integer nxt2 = idx.higher(nxt);
            if (nxt2 != null) {
                if (a[nxt] > a[nxt2]) { // 舊數據
                    dec--;
                }
                if (s > a[nxt2]) { // 新數據（當前元素，下下一個數）
                    dec++;
                }
                pairs.remove(new Pair(a[nxt] + a[nxt2], nxt));
                pairs.add(new Pair(s + a[nxt2], i));
            }

            a[i] = s;
            idx.remove(nxt);
        }
        return ans;
    }


}









