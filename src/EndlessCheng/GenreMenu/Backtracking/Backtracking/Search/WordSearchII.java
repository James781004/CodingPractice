package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordSearchII {

    // https://leetcode.cn/problems/word-search-ii/solutions/3724602/shuang-bai-jie-fa-qian-zhui-shu-dfshui-s-inbk/
    private TrieNode root;   // 根節點

    public List<String> findWords(char[][] board, String[] words) {
        int m = board.length;
        int n = board[0].length;

        // 構建words的字典樹
        root = new TrieNode();
        for (String word : words) {
            if (word.length() > m * n) continue;  // 字符串長度超過二維矩陣尺寸，肯定無法構成
            insert(word);
        }

        // 以二維網格的每個位置(i,j)為起點，尋找以其為首字符的所有字符串
        List<String> res = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs_Search(board, i, j, root, 0, res);
            }
        }
        return res;
    }

    /**
     * 將單詞word插入字段數root
     */
    private void insert(String word) {
        TrieNode node = root;      // 從根節點開始構造這個word對應的路徑節點
        int n = word.length();
        for (int i = 0; i < n; i++) {
            char ch = word.charAt(i);
            if (!node.children.containsKey(ch)) {
                // 字符ch對應的節點不存在，新建一個
                node.children.put(ch, new TrieNode());
            }
            // 更新node
            node = node.children.get(ch);
        }
        node.str = word;   // 尾節點記錄單詞，用於後序查找的時候快速得到
    }

    /**
     * 深度優先搜索的同時，判斷當前路徑構成的字符串是否為查找單詞
     *
     * @param board:           二維網格
     * @param r:               行號
     * @param c:               列號
     * @param node：當前字符對應的路徑節點
     * @param len:             當前路徑構成的字符串長度
     * @param res：結果集
     */
    private void dfs_Search(char[][] board, int r, int c, TrieNode node, int len, List<String> res) {
        if (len > 10) return;     // 字符串長度超過10，返回
        char ch = board[r][c];  // 獲取當前行列對應的字符
        if (!node.children.containsKey(ch)) return;  // 當前字符對應的節點不存在，即構造的字符串不在words中
        TrieNode last = node;  // 記錄當前node
        node = node.children.get(ch);  // 更新當前node為當前字符對應得到的節點
        if (!node.str.isEmpty()) {
            res.add(node.str);    // 當前節點記錄了一個單詞，則得到了一個words中的單詞
            node.str = "";     // 匹配了單詞，不重復匹配
        }
        ;
        if (node.children.isEmpty()) {
            // 當前節點沒有後序字符了，那麼這個節點一定是某個單詞最後一個字符對應的節點。
            // 並且不是其他任何單詞的前綴，因此匹配完了之後，可以將這個字符從其父節點的childran列表中刪除。
            last.children.remove(ch);
            return;
        }
        len++;  // 更新長度
        board[r][c] = '*';  // 用特殊符號標記當前位置已使用
        // 四個方向轉遞遞歸
        if (r - 1 >= 0 && board[r - 1][c] != '*') dfs_Search(board, r - 1, c, node, len, res);
        if (r + 1 < board.length && board[r + 1][c] != '*') dfs_Search(board, r + 1, c, node, len, res);
        if (c - 1 >= 0 && board[r][c - 1] != '*') dfs_Search(board, r, c - 1, node, len, res);
        if (c + 1 < board[0].length && board[r][c + 1] != '*') dfs_Search(board, r, c + 1, node, len, res);
        board[r][c] = ch;   // 回溯，這個位置處理完了恢復成原來的字符
    }
}

/**
 * 字典樹節點
 */
class TrieNode {
    Map<Character, TrieNode> children;     // 子節點列表
    String str;              // 如果是尾節點，存儲對應的單詞

    public TrieNode() {
        children = new HashMap<>();
        str = "";
    }
}



