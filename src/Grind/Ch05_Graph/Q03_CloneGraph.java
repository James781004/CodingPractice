package Grind.Ch05_Graph;

import java.util.*;

public class Q03_CloneGraph {
    // https://leetcode.cn/problems/clone-graph/solutions/14893/dfs-he-bfs-by-powcai/
    public Node cloneGraph(Node node) {
        Map<Node, Node> lookup = new HashMap<>();
        return dfs(node, lookup);
    }

    private Node dfs(Node node, Map<Node, Node> lookup) {
        if (node == null) return null;
        if (lookup.containsKey(node)) return lookup.get(node); // node節點已經被訪問過了,直接從map中取出對應的複製節點返回。
        Node clone = new Node(node.val, new ArrayList<>()); // 複製節點
        lookup.put(node, clone); // 原本節點到複製節點在map的映射
        for (Node n : node.neighbors) clone.neighbors.add(dfs(n, lookup)); // 複製節點鄰居
        return clone;
    }


    public Node cloneGraphBFS(Node node) {
        if (node == null) return null;
        Map<Node, Node> lookup = new HashMap<>();
        Node clone = new Node(node.val, new ArrayList<>()); // 複製原節點
        lookup.put(node, clone);  // 原本節點到複製節點在map的映射
        Deque<Node> queue = new LinkedList<>();
        queue.offer(node); // 原本節點加入隊列
        while (!queue.isEmpty()) {
            Node tmp = queue.poll();
            for (Node n : tmp.neighbors) {  // 複製原本節點鄰居
                if (!lookup.containsKey(n)) {  // 原本節點鄰居如果之前沒訪問過，這邊也加入隊列
                    lookup.put(n, new Node(n.val, new ArrayList<>())); // map映射關係建立
                    queue.offer(n);
                }
                lookup.get(tmp).neighbors.add(lookup.get(n)); // 複製節點鄰居
            }
        }
        return clone;
    }


    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
