package 程序员代码面试指南.ch08;

import java.util.*;

public class Q29_BuildingOutline {
//    CD52 大樓輪廓問題
//    描述
//    給定一個N×3的矩陣matrix，對於每一個長度為3的小數組arr，都表示一個大樓的三個數據。
//    arr[0]表示大樓的左邊界，arr[1]表示大樓的右邊界，arr[2]表示大樓的高度(一定大於0)。
//    每座大樓的地基都在X軸上，大樓之間可能會有重疊，請返回整體的輪廓線數組
//[要求]
//    時間覆雜度為O(nlogn)


    // 描述高度變化的對象
    public static class Node {
        public int x; // x軸上的值
        public boolean isAdd;// true為加入，false為刪除
        public int h; // 高度

        public Node(int x, boolean isAdd, int h) {
            this.x = x;
            this.isAdd = isAdd;
            this.h = h;
        }
    }

    // 排序的比較策略
    // 1，第一個維度的x值從小到大。
    // 2，如果第一個維度的值相等，看第二個維度的值，“加入”排在前，“刪除”排在後
    // 3，如果兩個對象第一維度和第二個維度的值都相等，則認為兩個對象相等，誰在前都行。
    public static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.x != o2.x) {
                return o1.x - o2.x;
            }
            if (o1.isAdd != o2.isAdd) {
                return o1.isAdd ? -1 : 1;
            }
            return 0;
        }
    }

    // 全部流程的主方法
    public static List<List<Integer>> buildingOutline(int[][] matrix) {
        Node[] nodes = new Node[matrix.length * 2];
        // 每一個大樓輪廓數組，產生兩個描述高度變化的對象
        for (int i = 0; i < matrix.length; i++) {
            nodes[i * 2] = new Node(matrix[i][0], true, matrix[i][2]);
            nodes[i * 2 + 1] = new Node(matrix[i][1], false, matrix[i][2]);
        }

        // 把描述高度變化的對象數組，按照規定的排序策略排序
        Arrays.sort(nodes, new NodeComparator());

        // TreeMap就是java中的紅黑樹結構，直接當作有序表來使用
        TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>(); // 存放各個height以及出現次數
        TreeMap<Integer, Integer> mapXvalueHeight = new TreeMap<>(); // 存放當前位置x最大height
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].isAdd) { // 如果當前是加入操作
                if (!mapHeightTimes.containsKey(nodes[i].h)) { // 沒有出現的高度直接新加記錄
                    mapHeightTimes.put(nodes[i].h, 1);
                } else { // 之前出現的高度，次數加1即可
                    mapHeightTimes.put(nodes[i].h,
                            mapHeightTimes.get(nodes[i].h) + 1);
                }
            } else { // 如果當前是刪除操作
                if (mapHeightTimes.get(nodes[i].h) == 1) { // 如果當前的高度出現次數為1，直接刪除記錄
                    mapHeightTimes.remove(nodes[i].h);
                } else { // 如果當前的高度出現次數大於1，次數減1即可
                    mapHeightTimes.put(nodes[i].h,
                            mapHeightTimes.get(nodes[i].h) - 1);
                }
            }

            // 根據mapHeightTimes中的最大高度，設置mapXvalueHeight表
            if (mapHeightTimes.isEmpty()) { // 如果mapHeightTimes為空，說明最大高度為0
                mapXvalueHeight.put(nodes[i].x, 0);
            } else { // 如果mapHeightTimes不為空，通過mapHeightTimes.lastKey()取得最大高度
                mapXvalueHeight.put(nodes[i].x, mapHeightTimes.lastKey());
            }
        }
        // res為結果數組，每一個List<Integer>代表一個輪廓線，有開始位置，結束位置，高度，一共三個信息
        List<List<Integer>> res = new ArrayList<>();
        // 一個新輪廓線的開始位置
        int start = 0;
        // 之前的最大高度
        int preHeight = 0;
        // 根據mapXvalueHeight生成res數組
        for (Map.Entry<Integer, Integer> entry : mapXvalueHeight.entrySet()) {
            // 當前位置
            int curX = entry.getKey();
            // 當前最大高度
            int curMaxHeight = entry.getValue();
            if (preHeight != curMaxHeight) { // 之前最大高度和當前最大高度不一樣時
                if (preHeight != 0) {  // preHeight為0表示還在初始階段，必須先跳過
                    res.add(new ArrayList<>(Arrays.asList(start, curX, preHeight)));
                }
                start = curX;
                preHeight = curMaxHeight;
            }
        }
        return res;
    }

}
