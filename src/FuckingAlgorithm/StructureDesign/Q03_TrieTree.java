package FuckingAlgorithm.StructureDesign;

import java.util.LinkedList;
import java.util.List;

public class Q03_TrieTree {
//    https://cloud.tencent.com/developer/article/1938801

    /* Trie 樹節點實現 */
    class TrieNode<V> {
        V val = null;
        TrieNode[] children = new TrieNode[256];
    }


    class TrieMap<V> {
        // ASCII 碼個數
        private static final int R = 256;
        // 當前存在 Map 中的鍵值對個數
        private int size = 0;
        // Trie 樹的根節點
        private TrieNode<V> root = null;

        private class TrieNode<V> {
            V val = null;
            TrieNode<V>[] children = new TrieNode[R];
        }

        /***** 增/改 *****/

        // 在 map 中添加或修改鍵值對
        public void put(String key, V val) {
            if (!containsKey(key)) {
                // 新增鍵值對
                size++;
            }
            // 需要一個額外的輔助函數，並接收其返回值
            root = put(root, key, val, 0);
        }

        // 定義：向以 node 為根的 Trie 樹中插入 key[i..]，返回插入完成後的根節點
        private TrieNode<V> put(TrieNode<V> node, String key, V val, int i) {
            if (node == null) {
                // 如果樹枝不存在，新建
                node = new TrieNode<>();
            }
            if (i == key.length()) {
                // key 的路徑已插入完成，將值 val 存入節點
                node.val = val;
                return node;
            }
            char c = key.charAt(i);
            // 遞歸插入子節點，並接收返回值
            node.children[c] = put(node.children[c], key, val, i + 1);
            return node;
        }

        /***** 刪 *****/

        // 在 Map 中刪除 key
        public void remove(String key) {
            if (!containsKey(key)) {
                return;
            }
            // 遞歸修改數據結構要接收函數的返回值
            root = remove(root, key, 0);
            size--;
        }

        // 定義：在以 node 為根的 Trie 樹中刪除 key[i..]，返回刪除後的根節點
        private TrieNode<V> remove(TrieNode<V> node, String key, int i) {
            if (node == null) {
                return null;
            }
            if (i == key.length()) {
                // 找到了 key 對應的 TrieNode，刪除 val
                node.val = null;
            } else {
                char c = key.charAt(i);
                // 遞歸去子樹進行刪除
                node.children[c] = remove(node.children[c], key, i + 1);
            }
            // 後序位置，遞歸路徑上的節點可能需要被清理
            if (node.val != null) {
                // 如果該 TireNode 存儲著 val，不需要被清理
                return node;
            }
            // 檢查該 TrieNode 是否還有後綴
            for (int c = 0; c < R; c++) {
                if (node.children[c] != null) {
                    // 只要存在一個子節點（後綴樹枝），就不需要被清理
                    return node;
                }
            }
            // 既沒有存儲 val，也沒有後綴樹枝，則該節點需要被清理
            return null;
        }

        /***** 查 *****/

        // 搜索 key 對應的值，不存在則返回 null
        public V get(String key) {
            // 從 root 開始搜索 key
            TrieNode<V> x = getNode(root, key);
            if (x == null || x.val == null) {
                // x 為空或 x 的 val 字段為空都說明 key 沒有對應的值
                return null;
            }
            return x.val;
        }

        // 判斷 key 是否存在在 Map 中
        public boolean containsKey(String key) {
            return get(key) != null;
        }

        // 判斷是和否存在前綴為 prefix 的鍵
        public boolean hasKeyWithPrefix(String prefix) {
            // 只要能找到一個節點，就是存在前綴
            return getNode(root, prefix) != null;
        }

        // 在所有鍵中尋找 query 的最短前綴
        public String shortestPrefixOf(String query) {
            TrieNode<V> p = root;
            // 從節點 node 開始搜索 key
            for (int i = 0; i < query.length(); i++) {
                if (p == null) {
                    // 無法向下搜索
                    return "";
                }
                if (p.val != null) {
                    // 找到一個鍵是 query 的前綴
                    return query.substring(0, i);
                }
                // 向下搜索
                char c = query.charAt(i);
                p = p.children[c];
            }

            if (p != null && p.val != null) {
                // 如果 query 本身就是一個鍵
                return query;
            }
            return "";
        }

        // 在所有鍵中尋找 query 的最長前綴
        public String longestPrefixOf(String query) {
            TrieNode<V> p = root;
            // 記錄前綴的最大長度
            int max_len = 0;

            // 從節點 node 開始搜索 key
            for (int i = 0; i < query.length(); i++) {
                if (p == null) {
                    // 無法向下搜索
                    break;
                }
                if (p.val != null) {
                    // 找到一個鍵是 query 的前綴，更新前綴的最大長度
                    max_len = i;
                }
                // 向下搜索
                char c = query.charAt(i);
                p = p.children[c];
            }

            if (p != null && p.val != null) {
                // 如果 query 本身就是一個鍵
                return query;
            }
            return query.substring(0, max_len);
        }

        // 搜索前綴為 prefix 的所有鍵
        public List<String> keysWithPrefix(String prefix) {
            List<String> res = new LinkedList<>();
            // 找到匹配 prefix 在 Trie 樹中的那個節點
            TrieNode<V> x = getNode(root, prefix);
            if (x == null) {
                return res;
            }
            // DFS 遍歷以 x 為根的這棵 Trie 樹
            traverse(x, new StringBuilder(prefix), res);
            return res;
        }

        // 遍歷以 node 節點為根的 Trie 樹，找到所有鍵
        private void traverse(TrieNode<V> node, StringBuilder path, List<String> res) {
            if (node == null) {
                // 到達 Trie 樹底部葉子結點
                return;
            }

            if (node.val != null) {
                // 找到一個 key，添加到結果列表中
                res.add(path.toString());
            }

            // 回溯算法遍歷框架
            for (char c = 0; c < R; c++) {
                // 做選擇
                path.append(c);
                traverse(node.children[c], path, res);
                // 撤銷選擇
                path.deleteCharAt(path.length() - 1);
            }
        }

        // 通配符 . 匹配任意字符
        public List<String> keysWithPattern(String pattern) {
            List<String> res = new LinkedList<>();
            traverse(root, new StringBuilder(), pattern, 0, res);
            return res;
        }

        // 遍歷函數，嘗試在「以 node 為根的 Trie 樹中」匹配 pattern[i..]
        private void traverse(TrieNode<V> node, StringBuilder path, String pattern, int i, List<String> res) {
            if (node == null) {
                // 樹枝不存在，即匹配失敗
                return;
            }
            if (i == pattern.length()) {
                // pattern 匹配完成
                if (node.val != null) {
                    // 如果這個節點存儲著 val，則找到一個匹配的鍵
                    res.add(path.toString());
                }
                return;
            }
            char c = pattern.charAt(i);
            if (c == '.') {
                // pattern[i] 是通配符，可以變化成任意字符
                // 多叉樹（回溯算法）遍歷框架
                for (char j = 0; j < R; j++) {
                    path.append(j);
                    traverse(node.children[j], path, pattern, i + 1, res);
                    path.deleteCharAt(path.length() - 1);
                }
            } else {
                // pattern[i] 是普通字符 c
                path.append(c);
                traverse(node.children[c], path, pattern, i + 1, res);
                path.deleteCharAt(path.length() - 1);
            }
        }

        // 判斷是和否存在前綴為 prefix 的鍵
        public boolean hasKeyWithPattern(String pattern) {
            // 從 root 節點開始匹配 pattern[0..]
            return hasKeyWithPattern(root, pattern, 0);
        }

        // 函數定義：從 node 節點開始匹配 pattern[i..]，返回是否成功匹配
        private boolean hasKeyWithPattern(TrieNode<V> node, String pattern, int i) {
            if (node == null) {
                // 樹枝不存在，即匹配失敗
                return false;
            }
            if (i == pattern.length()) {
                // 模式串走到頭了，看看匹配到的是否是一個鍵
                return node.val != null;
            }
            char c = pattern.charAt(i);
            // 沒有遇到通配符
            if (c != '.') {
                // 從 node.children[c] 節點開始匹配 pattern[i+1..]
                return hasKeyWithPattern(node.children[c], pattern, i + 1);
            }
            // 遇到通配符
            for (int j = 0; j < R; j++) {
                // pattern[i] 可以變化成任意字符，嘗試所有可能，只要遇到一個匹配成功就返回
                if (hasKeyWithPattern(node.children[j], pattern, i + 1)) {
                    return true;
                }
            }
            // 都沒有匹配
            return false;
        }

        // 從節點 node 開始搜索 key，如果存在返回對應節點，否則返回 null
        private TrieNode<V> getNode(TrieNode<V> node, String key) {
            TrieNode<V> p = node;
            // 從節點 node 開始搜索 key
            for (int i = 0; i < key.length(); i++) {
                if (p == null) {
                    // 無法向下搜索
                    return null;
                }
                // 向下搜索
                char c = key.charAt(i);
                p = p.children[c];
            }
            return p;
        }

        public int size() {
            return size;
        }
    }


    class TrieSet {
        // 底層用一個 TrieMap，鍵就是 TrieSet，值僅僅起到佔位的作用
        // 值的類型可以隨便設置，我參考 Java 標准庫設置成 Object
        private final TrieMap<Object> map = new TrieMap<>();

        /***** 增 *****/

        // 在集合中添加元素 key
        public void add(String key) {
            map.put(key, new Object());
        }

        /***** 刪 *****/

        // 從集合中刪除元素 key
        public void remove(String key) {
            map.remove(key);
        }

        /***** 查 *****/

        // 判斷元素 key 是否存在集合中
        public boolean contains(String key) {
            return map.containsKey(key);
        }

        // 在集合中尋找 query 的最短前綴
        public String shortestPrefixOf(String query) {
            return map.shortestPrefixOf(query);
        }

        // 在集合中尋找 query 的最長前綴
        public String longestPrefixOf(String query) {
            return map.longestPrefixOf(query);
        }

        // 在集合中搜索前綴為 prefix 的所有元素
        public List<String> keysWithPrefix(String prefix) {
            return map.keysWithPrefix(prefix);
        }

        // 判斷集合中是否存在前綴為 prefix 的元素
        public boolean hasKeyWithPrefix(String prefix) {
            return map.hasKeyWithPrefix(prefix);
        }

        // 通配符 . 匹配任意字符，返回集合中匹配 pattern 的所有元素
        public List<String> keysWithPattern(String pattern) {
            return map.keysWithPattern(pattern);
        }

        // 通配符 . 匹配任意字符，判斷集合中是否存在匹配 pattern 的元素
        public boolean hasKeyWithPattern(String pattern) {
            return map.hasKeyWithPattern(pattern);
        }

        // 返回集合中元素的個數
        public int size() {
            return map.size();
        }
    }


//    https://leetcode.cn/problems/implement-trie-prefix-tree/
//    208. 實現 Trie (前綴樹)
//    Trie（發音類似 "try"）或者說 前綴樹 是一種樹形數據結構，用於高效地存儲和檢索字符串數據集中的鍵。
//    這一數據結構有相當多的應用情景，例如自動補完和拼寫檢查。
//
//    請你實現 Trie 類：
//
//    Trie() 初始化前綴樹對象。
//    void insert(String word) 向前綴樹中插入字符串 word 。
//    boolean search(String word) 如果字符串 word 在前綴樹中，返回 true（即，在檢索之前已經插入）；否則，返回 false 。
//    boolean startsWith(String prefix) 如果之前已經插入的字符串 word 的前綴之一為 prefix ，返回 true ；否則，返回 false 。

    class Trie {

        class TrieNode {
            private boolean isEnd;
            TrieNode[] next;

            public TrieNode() {
                isEnd = false;
                next = new TrieNode[26];
            }
        }

        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.next[c - 'a'] == null) {
                    node.next[c - 'a'] = new TrieNode();
                }
                node = node.next[c - 'a'];
            }
            node.isEnd = true;
        }

        public boolean search(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                node = node.next[c - 'a'];
                if (node == null) {
                    return false;
                }
            }
            return node.isEnd;
        }

        public boolean startsWith(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                node = node.next[c - 'a'];
                if (node == null) {
                    return false;
                }
            }
            return true;
        }
    }

//    https://leetcode.cn/problems/replace-words/
//    648. 單詞替換
//    在英語中，我們有一個叫做 詞根(root) 的概念，可以詞根後面添加其他一些詞組成另一個較長的單詞——我們稱這個詞為 繼承詞(successor)。
//    例如，詞根an，跟隨著單詞 other(其他)，可以形成新的單詞 another(另一個)。
//
//    現在，給定一個由許多詞根組成的詞典 dictionary 和一個用空格分隔單詞形成的句子 sentence。你需要將句子中的所有繼承詞用詞根替換掉。
//    如果繼承詞有許多可以形成它的詞根，則用最短的詞根替換它。
//
//    你需要輸出替換之後的句子。

    String replaceWords(List<String> dict, String sentence) {
        // 先將詞根都存入 TrieSet
        TrieSet set = new TrieSet();
        for (String key : dict) {
            set.add(key);
        }
        StringBuilder sb = new StringBuilder();
        String[] words = sentence.split(" ");
        // 處理句子中的單詞
        for (int i = 0; i < words.length; i++) {
            // 在 Trie 樹中搜索最短詞根（最短前綴）
            String prefix = set.shortestPrefixOf(words[i]);
            if (!prefix.isEmpty()) {
                // 如果搜索到了，改寫為詞根
                sb.append(prefix);
            } else {
                // 否則，原樣放回
                sb.append(words[i]);
            }

            if (i != words.length - 1) {
                // 添加單詞之間的空格
                sb.append(' ');
            }
        }

        return sb.toString();
    }


//    https://leetcode.cn/problems/design-add-and-search-words-data-structure/
//    211. 添加與搜索單詞 - 數據結構設計
//    請你設計一個數據結構，支持 添加新單詞 和 查找字符串是否與任何先前添加的字符串匹配 。
//
//    實現詞典類 WordDictionary ：
//
//    WordDictionary() 初始化詞典對象
//    void addWord(word) 將 word 添加到數據結構中，之後可以對它進行匹配
//    bool search(word) 如果數據結構中存在字符串與 word 匹配，則返回 true ；否則，返回  false 。
//    word 中可能包含一些 '.' ，每個 . 都可以表示任何一個字母。

    class WordDictionary {
        TrieSet set = new TrieSet();

        // 在 TrieSet 中添加元素
        public void addWord(String word) {
            set.add(word);
        }

        // 通配符匹配元素
        public boolean search(String word) {
            return set.hasKeyWithPattern(word);
        }
    }


//    https://blog.csdn.net/weixin_42295205/article/details/125032391
//    LeetCode 1804 實現 Trie （前綴樹） II
//    前綴樹（trie ，發音為 "try"）是一個樹狀的數據結構，用於高效地存儲和檢索一系列字符串的前綴。
//    前綴樹有許多應用，如自動補全和拼寫檢查。
//
//    實現前綴樹 Trie 類：
//
//    Trie() 初始化前綴樹對象。
//    void insert(String word) 將字符串 word 插入前綴樹中。
//    int countWordsEqualTo(String word) 返回前綴樹中字符串 word 的實例個數。
//    int countWordsStartingWith(String prefix) 返回前綴樹中以 prefix 為前綴的字符串個數。
//    void erase(String word) 從前綴樹中移除字符串 word 。

    class TrieII {
        // 封裝我們實現的 TrieMap
        TrieMap<Integer> map = new TrieMap<>();

        // 插入 word 並記錄插入次數
        public void insert(String word) {
            if (!map.containsKey(word)) {
                map.put(word, 1);
            } else {
                map.put(word, map.get(word) + 1);
            }
        }

        // 查詢 word 插入的次數
        public int countWordsEqualTo(String word) {
            if (!map.containsKey(word)) {
                return 0;
            }
            return map.get(word);
        }

        // 累加前綴為 prefix 的鍵的插入次數總和
        public int countWordsStartingWith(String prefix) {
            int res = 0;
            for (String key : map.keysWithPrefix(prefix)) {
                res += map.get(key);
            }
            return res;
        }

        // word 的插入次數減一
        public void erase(String word) {
            int freq = map.get(word);
            if (freq - 1 == 0) {
                map.remove(word);
            } else {
                map.put(word, freq - 1);
            }
        }
    }


//    https://leetcode.cn/problems/map-sum-pairs/
//    677. 鍵值映射
//    設計一個 map ，滿足以下幾點:
//
//    字符串表示鍵，整數表示值
//            返回具有前綴等於給定字符串的鍵的值的總和
//    實現一個 MapSum 類：
//
//    MapSum() 初始化 MapSum 對象
//    void insert(String key, int val) 插入 key-val 鍵值對，字符串表示鍵 key ，整數表示值 val 。如果鍵 key 已經存在，那麼原來的鍵值對 key-value 將被替代成新的鍵值對。
//    int sum(string prefix) 返回所有以該前綴 prefix 開頭的鍵 key 的值的總和。

    class MapSum {
        // 封裝我們實現的 TrieMap
        TrieMap<Integer> map = new TrieMap<>();

        // 插入鍵值對
        public void insert(String key, int val) {
            map.put(key, val);
        }

        // 累加所有前綴為 prefix 的鍵的值
        public int sum(String prefix) {
            List<String> keys = map.keysWithPrefix(prefix);
            int res = 0;
            for (String key : keys) {
                res += map.get(key);
            }
            return res;
        }
    }
}
