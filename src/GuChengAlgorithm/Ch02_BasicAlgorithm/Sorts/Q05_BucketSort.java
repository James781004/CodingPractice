package GuChengAlgorithm.Ch02_BasicAlgorithm.Sorts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q05_BucketSort {
    // https://docs.google.com/presentation/d/179ocIZBRl1Tj34UkfEeodsIcfJYIv9-gMxazKYMgDLI/edit#slide=id.gbee19338a6_0_2
    public String frequencySort(String s) {
        // 計算詞頻
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);

        // 入桶，桶編號就是詞頻
        List<Character>[] bucket = new List[s.length() + 1];
        for (char key : map.keySet()) {
            int frequency = map.get(key);
            if (bucket[frequency] == null) bucket[frequency] = new ArrayList<>();
            bucket[frequency].add(key);
        }

        // 詞頻高的先加入
        StringBuilder sb = new StringBuilder();
        for (int pos = bucket.length - 1; pos >= 0; pos--) {
            if (bucket[pos] != null) {
                for (char c : bucket[pos]) {
                    for (int i = 0; i < pos; i++) {
                        sb.append(c);
                    }
                }
            }
        }

        return sb.toString();
    }
}
