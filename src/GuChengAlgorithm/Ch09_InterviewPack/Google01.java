package GuChengAlgorithm.Ch09_InterviewPack;

import java.util.*;

public class Google01 {
    // https://docs.google.com/presentation/d/1dXz1pB94BAsMtEaAnnY6NADAJbOvNfxRFw6KUpyOVWw/edit#slide=id.g13df7e486ad_0_51
    class MyCalendar {
        TreeMap<Integer, Integer> calendar;

        MyCalendar() {
            calendar = new TreeMap<>();
        }

        public boolean book(int start, int end) {
            Integer prev = calendar.floorKey(start), next = calendar.ceilingKey(start);
            if ((prev == null || calendar.get(prev) <= start) &&
                    (next == null || end <= next)) {  // 看看有沒有overlap
                calendar.put(start, end);
                return true;
            }
            return false;
        }
    }


    // https://docs.google.com/presentation/d/1dXz1pB94BAsMtEaAnnY6NADAJbOvNfxRFw6KUpyOVWw/edit#slide=id.g13df7e486ad_0_1
    class MaxReward {
        public int maxReward(int[] A, int K) {
            return findMax(A, 0, K);
        }

        Map<String, Integer> memo = new HashMap<>();

        private int findMax(int[] A, int i, int k) {
            if (i == A.length) return 0;
            String key = i + " : " + k;
            if (memo.containsKey(key)) return memo.get(key);
            int r = 0;

            // 選擇做工作，得到A[i]，失去k一次
            if (k != 0) {
                r = A[i] + findMax(A, i + 1, k - 1);
            }

            // 選擇不做工作休息一天，k增加一次機會
            r = Math.max(r, findMax(A, i + 1, k + 1));
            memo.put(key, r);
            return r;
        }


        public int maxRewardPQ(int[] arr, int k) {
            if (k >= arr.length) return Arrays.stream(arr).sum();
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            for (int i = 0; i < arr.length; i++) {
                if (i < k) {
                    minHeap.offer(arr[i]);
                } else {
                    if (!minHeap.isEmpty() && arr[i] > minHeap.peek()) {
                        minHeap.poll();
                        minHeap.offer(arr[i]);
                    }

                    if (i < arr.length - 1) {
                        i++; // 之前多休息一天，現在可以多工作一天，把下一天也算上
                        minHeap.offer(arr[i]);
                    }
                }
            }
            int res = 0;
            for (int n : minHeap) res += n;
            return res;
        }
    }


    // https://docs.google.com/presentation/d/1dXz1pB94BAsMtEaAnnY6NADAJbOvNfxRFw6KUpyOVWw/edit#slide=id.g13df7e486ad_0_10
    class EvaluateDivision {
        Map<String, Map<String, Double>> m = new HashMap<>();

        public double[] calEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
            for (int i = 0; i < equations.size(); i++) {
                String from = equations.get(i).get(0), to = equations.get(i).get(1);
                m.computeIfAbsent(from, x -> new HashMap<>()).put(to, values[i]);
                m.computeIfAbsent(to, x -> new HashMap<>()).put(from, 1 / values[i]);
            }

            double[] r = new double[queries.size()];
            for (int i = 0; i < queries.size(); i++) {
                r[i] = dfs(queries.get(i).get(0), queries.get(i).get(1), 1, new HashSet<>());
            }
            return r;
        }

        private double dfs(String s, String t, double r, HashSet<String> seen) {
            if (!m.containsKey(s) || !seen.add(s)) return -1;
            if (s.equals(t)) return r;
            Map<String, Double> neighbor = m.get(s);  // <nextString,轉化利率>
            for (String next : neighbor.keySet()) {
                double result = dfs(next, t, r * neighbor.get(next), seen);
                if (result != -1) return result;
            }
            seen.remove(s);
            return -1;
        }
    }
}
