package GuChengAlgorithm.Ch02_BasicAlgorithm.BFS;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Q00_BFS_Model {
    // https://docs.google.com/presentation/d/1R8rHF7l3C5DEOI0GTwSwSzkmyRmscu1KwjVIVpL4tgQ/edit#slide=id.g99a2da5d10_1_14

    public int BFS(Node start, Node target) {
        Queue<Node> q = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        q.offer(start);
        visited.add(start);
        int step = 0;

        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                Node cur = q.poll();
                if (cur.equals(target)) return step;
                for (Node x : cur.adj) {
                    if (!visited.contains(x)) {
                        q.offer(x);
                        visited.add(x);
                    }
                }
            }
            step++;
        }

        return step;
    }

    class Node {
        int val;
        Node[] adj;

        public Node(int v) {
            val = v;
        }
    }
}
