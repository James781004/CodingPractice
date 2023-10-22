package Grind.Ch05_Graph;

import java.util.*;

public class Q07_AccountsMerge {
    // 利用一個字符串的映射存儲並查集
    Map<String, String> q;

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        q = new HashMap<>();
        // 這個映射存儲每個郵箱對應賬戶的名字
        Map<String, String> names = new HashMap<>();

        // 遍歷所有賬戶構建並查集
        for (List<String> a : accounts) {
            for (int i = 1; i < a.size(); i++) {
                if (!q.containsKey(a.get(i))) {
                    // 如果並查集中沒有這個郵箱，則添加郵箱，其根元素就是本身
                    q.put(a.get(i), a.get(i));
                    // 添加該郵箱對應的賬戶名
                    names.put(a.get(i), a.get(0));
                }

                if (i > 1) {
                    // 並查集的合並操作，合並一個賬戶中的所有郵箱
                    q.put(find(a.get(i)), find(a.get(i - 1)));
                }
            }
        }

        // 暫時存儲答案中的郵箱列表，每個鍵值對的鍵就是每個並查集集合的根元素
        Map<String, List<String>> temp = new HashMap<>();
        for (String email : q.keySet()) {
            // 獲取當前郵箱對應並查集的根元素
            String root = find(email);
            // 將當前郵箱放入根元素對應的列表中
            if (!temp.containsKey(root)) temp.put(root, new ArrayList<>());
            temp.get(root).add(email);
        }

        // 將答案從映射中放到列表
        List<List<String>> res = new ArrayList();
        for (String root : temp.keySet()) {
            // 獲取當前根元素對應的列表
            List<String> layer = temp.get(root);
            // 題目要求的排序
            Collections.sort(layer);
            // 添加姓名
            layer.add(0, names.get(root));
            // 將當前列表加入答案
            res.add(layer);
        }

        return res;
    }

    // 並查集查找模板函數，這裡用字符串替換了之前的整型
    // find就是一個尋根歸祖的方法，尋找當前元素最終將屬於哪個根元素，這個根元素來表示一個聯通集合
    // 如果x映射的值等於x，那就可以說明x是個根元素，例如一個集合中只有一個元素，那麼它當然可以代表自己
    // 這樣做可以讓一個集合中的所有元素都能直接或者間接指向根元素，一個集合中只有一個元素的映射是本身
    private String find(String x) {
        // 判斷x是不是就是一個根元素
        if (!q.get(x).equals(x)) {
            // x不是根，那就通過遞歸find函數來找到x映射的元素的根元素（尋根）
            // 找到根元素之後，通過put方法將x直接映射到根元素，這樣就免去中間的多次遞歸（歸祖）
            q.put(x, find(q.get(x)));
        }

        // 最終，x映射的元素就是x所在的集合的根元素，返回x的根元素
        return q.get(x);
    }
}
