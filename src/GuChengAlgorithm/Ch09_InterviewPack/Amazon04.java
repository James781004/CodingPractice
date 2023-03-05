package GuChengAlgorithm.Ch09_InterviewPack;

import java.util.*;

public class Amazon04 {
    // https://docs.google.com/presentation/d/1GjSmNVCwq7zhpQGfXGjVHFBKpQ9NkehYVuej46llm8M/edit#slide=id.g14428641100_0_52
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

        // val跟尾部交換，然後刪除尾部
        public boolean remove(int val) {
            if (!map.containsKey(val)) return false;
            int location = map.get(val);
            if (location < list.size() - 1) {
                int last = list.size() - 1;
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


    // https://docs.google.com/presentation/d/1GjSmNVCwq7zhpQGfXGjVHFBKpQ9NkehYVuej46llm8M/edit#slide=id.g14428641100_0_85
    class MedianFinder {
        int A[] = new int[101], n = 0;
        int countLessZero = 0;

        public void addNum(int num) {
            if (num < 0) countLessZero++;
            else A[num]++;
            n++;
        }

        public double findMedian() {
            int count = countLessZero, i = 0;
            while (count < n / 2) count += A[i++];
            int j = i;
            while (count < n / 2 + 1) count += A[j++];
            return (n % 2 == 1) ? i : (i - 1 + j - 1) / 2.0;
        }

    }

    class MedianFinderPQ {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        public void addNum(int num) {
            maxHeap.add(num);
            minHeap.add(maxHeap.remove());
            if (minHeap.size() > maxHeap.size()) {
                maxHeap.add(minHeap.remove());
            }
        }

        public double findMedian() {
            return maxHeap.size() == minHeap.size() ? (maxHeap.peek() + minHeap.peek()) / 2.0 : maxHeap.peek();
        }
    }


    // https://docs.google.com/presentation/d/1GjSmNVCwq7zhpQGfXGjVHFBKpQ9NkehYVuej46llm8M/edit#slide=id.g14428641100_0_107
    class ReorganizeString {
        public String reorganizeString(String S) {
            Map<Character, Integer> map = new HashMap<>();
            for (char c : S.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);  // <character, frequency>
            PriorityQueue<Point> pq = new PriorityQueue<>((a, b) -> b.num - a.num);  // 根據frequency排列
            for (char c : map.keySet()) pq.add(new Point(c, map.get(c)));
            StringBuilder res = new StringBuilder();
            while (pq.size() >= 2) {
                Point p1 = pq.poll(), p2 = pq.poll();
                res.append(p1.c).append(p2.c);

                // frequency更新之後還是大於0，就再次放進queue
                if (--p1.num > 0) pq.add(p1);
                if (--p2.num > 0) pq.add(p2);
            }
            if (pq.size() > 0) res.append(pq.poll().c);
            return res.length() == S.length() ? res.toString() : "";
        }

        class Point {
            char c;
            int num;

            public Point(char c, int num) {
                this.c = c;
                this.num = num;
            }
        }
    }


    // https://docs.google.com/presentation/d/1GjSmNVCwq7zhpQGfXGjVHFBKpQ9NkehYVuej46llm8M/edit#slide=id.g14428641100_0_118
    class EmployeeImportance {
        public int getImportanceBFS(List<Employee> employees, int id) {
            int total = 0;
            Map<Integer, Employee> map = new HashMap<>();
            for (Employee employee : employees) {
                map.put(employee.id, employee);
            }
            Queue<Employee> q = new LinkedList<>();
            q.offer(map.get(id));
            while (!q.isEmpty()) {
                Employee cur = q.poll();
                total += cur.importance;
                for (int child : cur.subordinates) {
                    q.offer(map.get(child));
                }
            }
            return total;
        }


        public int getImportanceDFS(List<Employee> employees, int id) {
            Map<Integer, Employee> map = new HashMap<>();
            for (Employee employee : employees) {
                map.put(employee.id, employee);
            }
            return helper(map, id);
        }

        private int helper(Map<Integer, Employee> map, int rootId) {
            Employee root = map.get(rootId);
            int total = root.importance;
            for (int sub : root.subordinates) {
                total += helper(map, sub);
            }
            return total;
        }

        class Employee {
            public int id;
            public int importance;
            public List<Integer> subordinates;
        }
    }
}
