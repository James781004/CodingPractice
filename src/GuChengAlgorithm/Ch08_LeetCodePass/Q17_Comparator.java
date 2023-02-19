package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.Comparator;

public class Q17_Comparator {
    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_6_16
    class MyComparator implements Comparator<int[]> {

        @Override
        public int compare(int[] a, int[] b) {
            return a[0] - b[0];
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_6_129
    class Node implements Comparable<Node> {
        int time;
        int value;

        public Node(int t, int v) {
            time = t;
            value = v;
        }

        @Override
        public int compareTo(Node other) {
            return this.time == other.time ? this.value - other.value : this.time - other.time;
        }
    }
}
