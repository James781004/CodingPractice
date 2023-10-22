package Grind.Ch04_BinarySearch;

import java.util.*;

public class Q04_TimeBasedKeyValueStore {
    // https://leetcode.cn/problems/time-based-key-value-store/solutions/868550/981-ji-yu-shi-jian-de-jian-zhi-cun-chu-g-ehvm/
    class TimeMap {
        // 構建一個內部類，用於存儲 時間戳-值 的鍵值對
        // 當然也可以使用HashMap，但是在get的時候需要順序查值，還是用數組更好
        class Pair {
            String k, v;
            int t;

            Pair(String _k, String _v, int _t) {
                k = _k;
                v = _v;
                t = _t;
            }
        }

        // 定義一個數據結構：hash套數組，因為一個hash主鍵可能有多個值，那麽則使用List將那些值存起來
        Map<String, List<Pair>> map = new HashMap<>();

        // set操作
        public void set(String k, String v, int t) {
            // 尋找是否已經存在Hash主鍵，沒有就創建一個
            List<Pair> list = map.getOrDefault(k, new ArrayList<>());
            // 值添加進list
            list.add(new Pair(k, v, t));
            // list添加進map
            map.put(k, list);
        }

        // get操作
        public String get(String k, int t) {
            // 首先判斷是否存在HashMap主鍵
            List<Pair> list = map.getOrDefault(k, new ArrayList<>());
            if (list.isEmpty()) {
                return "";
            }

            // 使用二分法找到，最接近 t 的 [time-value]
            int n = list.size();
            int l = 0, r = n - 1;
            while (l < r) {
                int mid = l + r + 1 >> 1;
                if (list.get(mid).t <= t) {
                    l = mid;
                } else {
                    r = mid - 1;
                }
            }
            // 判斷是否存在 [time-value]
            return list.get(r).t <= t ? list.get(r).v : "";
        }
    }

    class TimeMapAdv {

        Map<String, TreeMap<Integer, String>> map = new HashMap<>();

        public TimeMapAdv() {

        }

        public void set(String k, String v, int t) {
            // 尋找是否已經存在Hash主鍵，沒有就創建一個
            TreeMap<Integer, String> ts = map.getOrDefault(k, new TreeMap<Integer, String>());
            // 以time為主鍵添加進TreeMap
            ts.put(t, v);
            // 將TreeMap添加進HashMap
            map.put(k, ts);

        }

        public String get(String k, int t) {
            // 首先判斷是否存在HashMap主鍵
            TreeMap<Integer, String> ts = map.getOrDefault(k, new TreeMap<>());
            // 然後獲取timestamp_prev
            Map.Entry<Integer, String> entry = ts.floorEntry(t);
            // 判斷是否為空
            if (entry == null) {
                return "";
            }
            // 獲取值
            return entry.getValue();
        }

        // 刪除
        public String del(String k, int t) {
            // 首先判斷是否存在HashMap主鍵
            TreeMap<Integer, String> ts = map.get(k);
            if (ts == null) return null;
            // 然後獲取timestamp_prev
            Map.Entry<Integer, String> entry = ts.floorEntry(t);
            if (entry == null) return null;

            // 獲取value，然後刪除value，並返回value
            String v = entry.getValue();
            ts.remove(t);
            return v;
        }
    }
}
