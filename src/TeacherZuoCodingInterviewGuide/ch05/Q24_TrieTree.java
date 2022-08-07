package TeacherZuoCodingInterviewGuide.ch05;

public class Q24_TrieTree {

    public static class TrieNode {
        public int path; // 表示有多少個單詞共用這個節點
        public int end;  // 表示有多少個單詞以這個節點為結尾
        public TrieNode[] map; // 也可以用HashMap, key代表路徑，value代表路徑指向的節點

        public TrieNode() {
            path = 0;
            end = 0;
            map = new TrieNode[26];
        }
    }

    public static class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chs = word.toCharArray();
            TrieNode node = root;
            node.path++;
            int index = 0;

            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.map[index] == null) { // 路徑為null的情況新增路徑以及新節點
                    node.map[index] = new TrieNode();
                }
                node = node.map[index]; // node指針向下走
                node.path++; // 經過節點path++
            }
            node.end++; // 到達終點end++
        }

        public void delete(String word) {
            if (search(word)) {
                char[] chs = word.toCharArray();
                TrieNode node = root;
                node.path--;
                int index = 0;

                for (int i = 0; i < chs.length; i++) {
                    index = chs[i] - 'a';

                    // 每次經過節點就把path減1
                    // 如果發現下一個節點path已經等於1，表示已經是最後一條路徑，直接刪掉就可以return
                    if (node.map[index].path-- == 1) {
                        node.map[index] = null;
                        return;
                    }
                    node = node.map[index];
                }
                node.end--; // 到達終點end--
            }
        }

        public boolean search(String word) {
            if (word == null) {
                return false;
            }
            char[] chs = word.toCharArray();
            TrieNode node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.map[index] == null) { // 路徑為null的情況表示路徑不存在
                    return false;
                }
                node = node.map[index]; // node指針向下走
            }
            return node.end != 0; // end不是0才能代表找到終點
        }

        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            TrieNode node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.map[index] == null) {
                    return 0;
                }
                node = node.map[index];
            }
            return node.path; // 表示有多少個單詞共用這個節點，也就是前綴數量
        }
    }


    public static void main(String[] args) {
        Trie trie = new Trie();
        System.out.println(trie.search("zuo"));
        trie.insert("zuo");
        System.out.println(trie.search("zuo"));
        trie.delete("zuo");
        System.out.println(trie.search("zuo"));
        trie.insert("zuo");
        trie.insert("zuo");
        trie.delete("zuo");
        System.out.println(trie.search("zuo"));
        trie.delete("zuo");
        System.out.println(trie.search("zuo"));
        trie.insert("zuoa");
        trie.insert("zuoac");
        trie.insert("zuoab");
        trie.insert("zuoad");
        trie.delete("zuoa");
        System.out.println(trie.search("zuoa"));
        System.out.println(trie.prefixNumber("zuo"));

    }
}
