package EndlessCheng.Basic.DifferenceArray;

import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeMap;

public class fullBloomFlowers {

    // https://leetcode.cn/problems/number-of-flowers-in-full-bloom/solutions/1445000/chai-fen-pythonjavacgo-by-endlesscheng-wz35/
    public int[] fullBloomFlowers(int[][] flowers, int[] people) {
        var diff = new TreeMap<Integer, Integer>();
        for (var f : flowers) {
            diff.merge(f[0], 1, Integer::sum);
            diff.merge(f[1] + 1, -1, Integer::sum);
        }

        // 離散數組排序 people
        int n = people.length;
        var id = new Integer[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
        Arrays.sort(id, (i, j) -> people[i] - people[j]);

        // Java 迭代器沒有 peek，可以自己實現一個帶 peek 的迭代器
        var it = new PeekingIterator<>(diff.entrySet().iterator());
        int sum = 0;
        for (int i : id) {
            while (it.hasNext() && it.peek().getKey() <= people[i]) {
                sum += it.next().getValue(); // 累加不超過 people[i] 的差分值
            }
            people[i] = sum; // 從而得到這個時刻花的數量
        }
        return people;
    }

    // 284. 頂端迭代器 https://leetcode.cn/problems/peeking-iterator/
    private static class PeekingIterator<E> implements Iterator<E> {
        private final Iterator<E> iterator;
        private E nextElement;

        public PeekingIterator(Iterator<E> iterator) {
            this.iterator = iterator;
            if (iterator.hasNext()) {
                nextElement = iterator.next();
            }
        }

        public E peek() {
            return nextElement;
        }

        @Override
        public E next() {
            E currentElement = nextElement;
            nextElement = iterator.hasNext() ? iterator.next() : null;
            return currentElement;
        }

        @Override
        public boolean hasNext() {
            return nextElement != null;
        }
    }


}
