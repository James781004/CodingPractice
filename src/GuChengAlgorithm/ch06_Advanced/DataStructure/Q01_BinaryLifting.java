package GuChengAlgorithm.ch06_Advanced.DataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q01_BinaryLifting {
    // https://docs.google.com/presentation/d/1F0gjgdt4f5IQAOpMY_YDm0amnxdbGAk6fUUO9rUwCww/edit#slide=id.g88f3d15c95_0_792

    class TreeAncestor {
        Map<Integer, List<Integer>> children = new HashMap<>();
        Integer[][] memo;

        public TreeAncestor(int n, int[] parent) {
            this.memo = new Integer[n][20];
            for (int i = 0; i < n; i++) {
                int curNode = i;
                int parentNode = parent[i];
                children.computeIfAbsent(parentNode, v -> new ArrayList<>()).add(curNode);
                if (i > 0) memo[curNode][0] = parent[i];
            }
            dfs(0);
        }

        private void dfs(int cur) {
            for (int i = 1; memo[cur][i - 1] != null; i++) {
                int jumpToNextNode = memo[cur][i - 1];
                memo[cur][i] = memo[jumpToNextNode][i - 1];
            }
            for (int child : children.getOrDefault(cur, new ArrayList<>())) {
                dfs(child);
            }
        }

        public int getKthAncestor(int node, int k) {
            for (int i = 0; k > 0; i++) {
                if (k % 2 == 1) {
                    if (memo[node][i] == null) return -1;
                    node = memo[node][i];
                }
                k /= 2;
            }
            return node;
        }
    }


}
