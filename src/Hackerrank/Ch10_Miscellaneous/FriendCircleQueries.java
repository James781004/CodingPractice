package Hackerrank.Ch10_Miscellaneous;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendCircleQueries {

    static int[] maxCircle(int[][] queries) {

        int res[] = new int[queries.length];
        Map<Integer, Integer> personToId = new HashMap<Integer, Integer>();
        ArrayList<Integer> relation = new ArrayList<Integer>();
        ArrayList<Integer> count = new ArrayList<Integer>();
        int maxSoFar = Integer.MIN_VALUE;

        for (int i = 0; i < queries.length; i++) {

            int p1 = getParent(personToId, relation, count, queries[i][0]);
            int p2 = getParent(personToId, relation, count, queries[i][1]);

            if (p1 != p2) {
                if (count.get(p1) < count.get(p2)) {

                    relation.set(p1, p2);
                    count.set(p2, count.get(p1) + count.get(p2));
                    maxSoFar = Integer.max(maxSoFar, count.get(p2));

                } else {
                    relation.set(p2, p1);
                    count.set(p1, count.get(p1) + count.get(p2));
                    maxSoFar = Integer.max(maxSoFar, count.get(p1));
                }
            }

            res[i] = maxSoFar;

        }

        return res;

    }

    private static int getParent(Map<Integer, Integer> personToId, ArrayList<Integer> relation,
                                 ArrayList<Integer> count, int f) {
        if (!personToId.containsKey(f)) {
            personToId.put(f, relation.size());
            relation.add(relation.size());
            count.add(1);
        }

        int id = personToId.get(f);

        while (relation.get(id) != id) {
            id = relation.get(id);
        }

        return id;
    }
}
