package TeacherZuoCodingInterviewGuide.ch05;

import java.util.HashMap;
import java.util.Map;

public class Q11_MinDistance {
    //    數組中兩個字符串的最小距離
//
//    描述
//    給定一個字符串數組strs，再給定兩個字符串str1和str2，返回在strs中str1和str2的最小距離，
//    如果str1或str2為null，或不在strs中，返回-1。

    public static int minDistance(String[] strs, String str1, String str2) {
        if (str1 == null || str2 == null) {
            return -1;
        }
        if (str1.equals(str2)) {
            return 0;
        }

        int last1 = -1, last2 = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i != strs.length; i++) {
            if (str1.equals(strs[i])) {
                // 如果last2還在初始化-1階段，表示目標str2還沒找到，先不更新
                // 如果str2前面找到過了，就計算i - last2然後比較長度
                min = Math.min(min, last2 == -1 ? min : i - last2);

                // 更新str1目前位置
                last1 = i;
            }
            if (str2.equals(strs[i])) {
                // 如果last1還在初始化-1階段，表示目標str1還沒找到，先不更新
                // 如果str1前面找到過了，就計算i - last1然後比較長度
                min = Math.min(min, last1 == -1 ? min : i - last1);

                // 更新str2目前位置
                last2 = i;
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    public static class Record {

        // key: 當前檢查的str1
        // value: (對應其他str, 最短距離)
        private HashMap<String, HashMap<String, Integer>> record;

        public Record(String[] strArr) {
            record = new HashMap<String, HashMap<String, Integer>>();
            HashMap<String, Integer> indexMap = new HashMap<String, Integer>();
            for (int i = 0; i != strArr.length; i++) {
                String curStr = strArr[i];
                update(indexMap, curStr, i);
                indexMap.put(curStr, i); // 紀錄當前字符的位置
            }
        }

        private void update(HashMap<String, Integer> indexMap, String str, int i) {
            if (!record.containsKey(str)) { // 當前字符在總表中沒有"前一次"保存的資料，直接新增空map
                record.put(str, new HashMap<String, Integer>());
            }
            HashMap<String, Integer> strMap = record.get(str); // 取得當前字符在總表中"前一次"保存的資料
            for (Map.Entry<String, Integer> lastEntry : indexMap.entrySet()) {

                // 透過indexMap取得目前出現過字符的"前一次"出現位置
                String key = lastEntry.getKey();
                int index = lastEntry.getValue();
                if (!key.equals(str)) {  // 傳參str是當前字符，不計算自己
                    HashMap<String, Integer> lastMap = record.get(key); // 取得目標key字符在總表中"前一次"保存的資料
                    int curMin = i - index; // 目前位置與"前一次"index的距離
                    if (strMap.containsKey(key)) {
                        int preMin = strMap.get(key);
                        if (curMin < preMin) { // 距離比之前短就更新資料
                            strMap.put(key, curMin);
                            lastMap.put(str, curMin);
                        }
                    } else { // 如果總表中前一次沒有保存目標key的資料，直接新增curMin進去
                        strMap.put(key, curMin);
                        lastMap.put(str, curMin);
                    }
                }
            }
        }

        public int minDistance(String str1, String str2) {
            if (str1 == null || str2 == null) {
                return -1;
            }
            if (str1.equals(str2)) {
                return 0;
            }
            if (record.containsKey(str1) && record.get(str1).containsKey(str2)) {
                return record.get(str1).get(str2);
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        String[] strArr = new String[]{"4", "2", "2", "3", "2", "2", "3",
                "1", "1", "3"};
        System.out.println(minDistance(strArr, "4", "3"));
        System.out.println(minDistance(strArr, "2", "3"));
        System.out.println(minDistance(strArr, "2", "1"));

        System.out.println("=======");

        Record record = new Record(strArr);
        System.out.println(record.minDistance("4", "3"));
        System.out.println(record.minDistance("2", "3"));
        System.out.println(record.minDistance("2", "1"));
    }

}
