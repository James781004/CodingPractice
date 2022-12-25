package GuChengAlgorithm.Ch01_BasicDataStructure.DSU;

public class Q01_FriendCircle {
    //    https://www.cnblogs.com/grandyang/p/6686983.html
//    There are N students in a class. Some of them are friends, while some are not. 
//    Their friendship is transitive in nature. 
//    For example, if A is a direct friend of B, and B is a direct friend of C, then A is an indirect friend of C. 
//    And we defined a friend circle is a group of students who are direct or indirect friends.
//
//    Given a N*N matrix M representing the friend relationship between students in the class. If M[i][j] = 1, 
//    then the ith and jth students are direct friends with each other, otherwise not.
//    And you have to output the total number of friend circles among all the students.

    public int findCircle(int[][] M) {
        DSU dsu = new DSU(M.length);
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                if (M[i][j] == 1) dsu.union(i, j); // 把friend連結成同一個union
            }
        }

        // 直接數有幾個union
        int res = 0;
        for (int i = 0; i < M.length; i++) {
            if (dsu.find(i) == i) res++;
        }
        return res;
    }

    class DSU {
        int[] parent;

        public DSU(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
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
}
