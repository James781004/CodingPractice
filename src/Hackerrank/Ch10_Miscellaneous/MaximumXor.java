package Hackerrank.Ch10_Miscellaneous;

public class MaximumXor {
    static int[] maxXor(int[] arr, int[] queries) {
        int[] answer = new int[queries.length];
        Node root = new Node();
        for (int i : arr) root.add(i, 30);
        for (int i = 0; i < queries.length; i++)
            answer[i] = queries[i] ^ root.get(queries[i], 30);
        return answer;
    }

    static class Node {
        Node zero;
        Node one;

        void add(int num, int bit) {
            if (bit == -1) return;
            if (((num >> bit) & 1) == 0) {
                if (zero == null) zero = new Node();
                zero.add(num, bit - 1);
            } else {
                if (one == null) one = new Node();
                one.add(num, bit - 1);
            }
        }

        int get(int num, int bit) {
            if (bit == -1) return 0;
            if (((num >> bit) & 1) == 0 && one != null || zero == null)
                return (1 << bit) ^ one.get(num, bit - 1);
            else
                return (0 << bit) ^ zero.get(num, bit - 1);
        }
    }
}
