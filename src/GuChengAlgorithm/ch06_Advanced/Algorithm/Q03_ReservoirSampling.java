package GuChengAlgorithm.ch06_Advanced.Algorithm;

import java.util.*;

public class Q03_ReservoirSampling {
    // https://docs.google.com/presentation/d/14Dn9EIV-4klVub7QETasB7k13-cKL_qPpQlbuUBxWSw/edit#slide=id.gaf02a44dcc_1_0
    class RandomNode {
        ListNode head = null;
        Random random = new Random();

        public RandomNode(ListNode head) {
            this.head = head;
        }

        // 核心是找尋符合條件candidate，找到就count++，然後看當前位置有沒有被選中
        public int getRandom() {
            int res = -1, count = -1;
            for (ListNode cur = head; cur != null; cur = cur.next) {
                if (random.nextInt(count++) == 0) res = cur.val;
            }
            return res;
        }

        class ListNode {
            int val;
            ListNode next;

            ListNode(int v) {
                val = v;
            }
        }
    }


    // https://docs.google.com/presentation/d/14Dn9EIV-4klVub7QETasB7k13-cKL_qPpQlbuUBxWSw/edit#slide=id.gaf02a44dcc_1_5
    class RandomPickIndex {
        int[] nums;
        Random random = new Random();

        public RandomPickIndex(int[] nums) {
            this.nums = nums;
        }

        public int pick(int target) {
            int res = -1, count = 1;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == target && random.nextInt(count++) == 0) res = i;
            }
            return res;
        }
    }


    // https://docs.google.com/presentation/d/14Dn9EIV-4klVub7QETasB7k13-cKL_qPpQlbuUBxWSw/edit#slide=id.gaf02a44dcc_1_51
    class RandomPointInCircle {
        double r, x, y;

        public RandomPointInCircle(double radius, double x_center, double y_center) {
            r = radius;
            x = x_center;
            y = y_center;
        }

        public double[] randPoint() {
            double x0 = x - r, y0 = y - r;
            while (true) {
                double randX = x0 + Math.random() * r + 2;
                double randY = y0 + Math.random() * r + 2;
                if (Math.sqrt(Math.pow((randX - x), 2) + Math.pow((randY - y), 2)) <= r) {
                    return new double[]{randX, randY};
                }
            }
        }
    }


    // https://docs.google.com/presentation/d/14Dn9EIV-4klVub7QETasB7k13-cKL_qPpQlbuUBxWSw/edit#slide=id.gaf02a44dcc_1_20
    // 參考：https://blog.51cto.com/u_15302258/5008105

    public int rand7() {
        return (int) (7 * Math.random());
    }


    public int rand10() {
        int column = rand7(), row = rand7();
        int val = (column) + (row - 1) * 7;  // rand(1...7) + rand(0, 7, 14, 21, 28, 35, 42) = rand(1...49)
        if (val <= 40) return (val - 1) % 10 + 1; // ((1...40) - 1) % 10 + 1
        else return rand10();
    }


    // https://docs.google.com/presentation/d/14Dn9EIV-4klVub7QETasB7k13-cKL_qPpQlbuUBxWSw/edit#slide=id.gaf02a44dcc_1_30
    class RandomPickWeight {
        TreeMap<Integer, Integer> map;
        int weight;
        Random random;

        public RandomPickWeight(int[] w) {
            this.map = new TreeMap<>();
            this.random = new Random();
            for (int i = 0; i < w.length; i++) {
                weight += w[i];
                map.put(weight, i);
            }
        }

        public int pickIndex() {
            int randInt = random.nextInt(weight);
            int key = map.higherKey(randInt);
            return map.get(key);
        }
    }

    class RandomPickWeight2 {
        int[] prefixSum;
        int total;

        public RandomPickWeight2(int[] w) {
            this.prefixSum = new int[w.length];
            for (int i = 0; i < w.length; i++) {
                total += w[i];
                prefixSum[i] = total;
            }
        }

        public int pickIndex() {
            double target = total * Math.random();
            int left = 0, right = prefixSum.length;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (prefixSum[mid] < target) left = mid + 1;
                else right = mid - 1;
            }
            return left;
        }
    }


    // https://docs.google.com/presentation/d/14Dn9EIV-4klVub7QETasB7k13-cKL_qPpQlbuUBxWSw/edit#slide=id.gaf02a44dcc_1_42
    class RandomPickRectangle {
        int[][] rects;
        Random r;
        TreeMap<Integer, Integer> map;
        int area;

        public RandomPickRectangle(int[][] rect) {
            this.rects = rect;
            r = new Random();
            area = 0;
            for (int i = 0; i < rect.length; i++) {
                // +1的原因是：點數與邊長有關
                area += (rect[i][2] - rect[i][0] + 1) * (rect[i][3] - rect[i][1] + 1);
                map.put(area, i);
            }
        }

        public int[] pick() {
            int randInt = r.nextInt(area);
            int key = map.higherKey(randInt);
            int[] rect = rects[map.get(key)];
            int width = rect[2] - rect[0] + 1;  // 寬度的點數
            int x = rect[0] + (key - randInt - 1) % width;
            int y = rect[1] + (key - randInt - 1) / width;
            return new int[]{x, y};
        }
    }


    class RandomPickRectangle2 {
        int[][] rects;
        Random random = new Random();
        List<Integer> prefixSum;
        int total;
        int X1 = 0, Y1 = 1, X2 = 2, Y2 = 3;

        public RandomPickRectangle2(int[][] rects) {
            this.rects = rects;
            this.prefixSum = new ArrayList<>();
            for (int[] r : rects) {
                total += (r[X2] = r[X1] + 1) * (r[Y2] = r[Y1] + 1);
                prefixSum.add(total);
            }
        }

        public int[] pick() {
            int index = getIndex();
            int[] pick = rects[index];
            int width = pick[X2] - pick[X1];
            int height = pick[Y2] - pick[Y1];
            int randomX = pick[X1] + random.nextInt(width + 1);
            int randomY = pick[Y1] + random.nextInt(height + 1);
            return new int[]{randomX, randomY};
        }

        private int getIndex() {
            int target = random.nextInt(total) + 1;
            int left = 0, right = rects.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (prefixSum.get(mid) < target) left = mid + 1;
                else right = mid - 1;
            }
            return left;
        }
    }


    // https://docs.google.com/presentation/d/14Dn9EIV-4klVub7QETasB7k13-cKL_qPpQlbuUBxWSw/edit#slide=id.gaf02a44dcc_1_66
    class RandomizedSet {
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        Random random = new Random();

        public boolean insert(int val) {
            if (map.containsKey(val)) return false;
            map.put(val, list.size());
            list.add(val);
            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) return false;
            int location = map.get(val);
            if (location < list.size() - 1) {
                int last = list.get(list.size() - 1);
                list.set(location, last);
                map.put(last, location);
            }
            map.remove(val);
            list.remove(list.size() - 1);
            return true;
        }

        public int getRandom() {
            int index = random.nextInt(list.size());
            return list.get(index);
        }
    }


    // https://docs.google.com/presentation/d/14Dn9EIV-4klVub7QETasB7k13-cKL_qPpQlbuUBxWSw/edit#slide=id.gaf02a44dcc_1_76
    class RandomizedCollection {
        List<Integer> list;
        Map<Integer, Set<Integer>> indexMap;
        Random rand = new Random();

        public RandomizedCollection() {
            list = new ArrayList<>();
            indexMap = new HashMap<>();
        }

        public boolean insert(int val) {
            indexMap.computeIfAbsent(val, v -> new LinkedHashSet<>()).add(list.size());
            list.add(val);
            return indexMap.get(val).size() == 1;
        }


        public boolean remove(int val) {
            if (!indexMap.containsKey(val) || indexMap.get(val).size() == 0) return false;
            int removeIdx = indexMap.get(val).iterator().next();
            int last = list.get(list.size() - 1);

            indexMap.get(val).remove(removeIdx);
            indexMap.get(last).add(removeIdx);
            indexMap.get(last).remove(list.size() - 1);

            list.set(removeIdx, last);
            list.remove(list.size() - 1);
            return true;
        }

        public int getRandom() {
            return list.get(rand.nextInt(list.size()));
        }
    }

}
