package 程序员代码面试指南.ch09;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Q14_UnionFind {
//    CD66 並查集的實現
//    描述
//    給定一個沒有重覆值的整形數組arr，初始時認為arr中每一個數各自都是一個單獨的集合。請設計一種叫UnionFind的結構，並提供以下兩個操作。
//    boolean isSameSet(int a, int b): 查詢a和b這兩個數是否屬於一個集合
//    void union(int a, int b): 把a所在的集合與b所在的集合合並在一起，原本兩個集合各自的元素以後都算作同一個集合
//[要求]
//    如果調用isSameSet和union的總次數逼近或超過O(N)，請做到單次調用isSameSet或union方法的平均時間覆雜度為O(1)

    public static class Element<V> {
        public V value;

        public Element(V value) {
            this.value = value;
        }

    }

    public static class UnionFindSet<V> {
        public HashMap<V, Element<V>> elementMap;
        public HashMap<Element<V>, Element<V>> fatherMap;
        public HashMap<Element<V>, Integer> rankMap;

        public UnionFindSet(List<V> list) {
            elementMap = new HashMap<>();
            fatherMap = new HashMap<>();
            rankMap = new HashMap<>();
            for (V value : list) {
                Element<V> element = new Element<V>(value);
                elementMap.put(value, element);
                fatherMap.put(element, element); // 初始化時父節點先指向自己
                rankMap.put(element, 1); // 初始化時自己就是一個集合，長度為1
            }
        }

        private Element<V> findHead(Element<V> element) {
            Stack<Element<V>> path = new Stack<>();

            // 當前element父節點如果不指向自己，表示目前element不是整個集合的代表節點
            // 那就放入path並透過fatherMap找尋父節點
            // element最後會移動到整個集合的代表節點
            while (element != fatherMap.get(element)) {
                path.push(element);
                element = fatherMap.get(element);
            }

            // path彈出節點並在fatherMap留下紀錄
            // 所有彈出節點的父節點現在都是整個集合的代表節點(也就是element經過while loop後抵達的位置)
            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), element);
            }
            return element;
        }

        // a, b如果有相同的代表節點，就是在相同集合
        public boolean isSameSet(V a, V b) {
            if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
                return findHead(elementMap.get(a)) == findHead(elementMap
                        .get(b));
            }
            return false;
        }

        // 在兩個代表節點不同的情況下，比較兩個集合的長度，
        // 短的集合的代表節點指向長的集合，再把兩個集合的總長度加到代表節點上。
        public void union(V a, V b) {
            if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
                Element<V> aF = findHead(elementMap.get(a));
                Element<V> bF = findHead(elementMap.get(b));
                if (aF != bF) {
                    Element<V> big = rankMap.get(aF) >= rankMap.get(bF) ? aF : bF;
                    Element<V> small = big == aF ? bF : aF;
                    fatherMap.put(small, big); // 小集合的代表節點指向大集合
                    rankMap.put(big, rankMap.get(aF) + rankMap.get(bF)); // 集合長度相加
                    rankMap.remove(small); // 合併完成後把短的集合資料移除
                }
            }
        }
    }
}
