package GuChengAlgorithm.Ch02_BasicAlgorithm.IntervalSweepLine;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class Q01_CountingAirplanes {

    // https://blog.csdn.net/u013325815/article/details/103388203
    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g885522199d_0_47
    // LintCode 391
    public int countOfPlanes(List<Interval> planes) {
        int count = 0;
        List<Node> list = new ArrayList<>();

        // Sweep Line算法
        for (Interval interval : planes) {
            list.add(new Node(interval.start, 1));  // 起飛+1
            list.add(new Node(interval.start, -1)); // 降落-1
        }

        Collections.sort(list, (a, b) -> a.time != b.time ? a.time - b.time : a.flag - b.flag);
        int max = 0;
        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            if (node.flag == 1) { // 空中有飛機，count++，反之count--
                count++;
            } else {
                count--;
            }
            max = Math.max(max, count); // 保存空中最大飛機數量
        }

        return max;
    }

    // 用tree map來做
    public int countOfPlanesTm(List<Interval> planes) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (Interval interval : planes) {
            treeMap.put(interval.start, treeMap.getOrDefault(interval.start, 0) + 1);
            treeMap.put(interval.end, treeMap.getOrDefault(interval.end, 0) - 1);
        }

        int count = 0, max = 0;

        for (Integer key : treeMap.keySet()) {
            count += treeMap.get(key);
            max = Math.max(max, count);
        }

        return max;
    }

    private class Node {
        public int time;
        public int flag;

        public Node(int t, int f) {
            time = t;
            flag = f;
        }
    }

    private class Interval {
        public int start;
        public int end;

        public Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

}
