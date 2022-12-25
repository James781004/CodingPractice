package GuChengAlgorithm.Ch01_BasicDataStructure.DSU;

import java.util.Arrays;

public class Q00_UF_Model {
//    https://docs.google.com/presentation/d/1GCtsrnfljBVwm_ng0izVP0M8CGs0ZcOMVoPRIPrahfk/edit#slide=id.g967dfc86ae_0_0

    class DSU {
        int[] parent;

        public DSU(int N) {
            parent = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        public void union(int x, int y) {
            parent[find(x)] = find(y);
        }
    }


    class DSUSize {
        int[] parent;
        int[] size;

        public DSUSize(int N) {
            parent = new int[N];
            size = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
            }
            Arrays.fill(size, 1);
        }

        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x), rootY = find(y);
            if (rootX == rootY) return;
            if (size[rootX] <= size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else if (size[rootX] > size[rootY]) {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }
        }
    }


    class DSURank {
        int[] parent;
        int[] rank;

        public DSURank(int N) {
            parent = new int[N];
            rank = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
            }
            Arrays.fill(rank, 1);
        }

        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x), rootY = find(y);
            if (rootX == rootY) return;
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootX] = rootY;
                rank[rootY]++; // rank相等時才需要操作
            }
        }
    }
}
