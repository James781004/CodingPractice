package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

public class FindMinStep {

    // https://leetcode.cn/problems/zuma-game/solutions/1092242/tong-ge-lai-shua-ti-la-ji-yi-hua-sou-suo-je0m/
    // 手中最多只有5個球，所以，操作次數不會多於5
    int INF = 6;

    public int findMinStep(String board, String hand) {
        // 記憶化緩存
        Map<String, Integer> memo = new HashMap<>();
        // 遞歸開始
        int ans = dfs(board, hand.toCharArray(), memo);
        // 判斷結果
        return ans >= INF ? -1 : ans;
    }

    private int dfs(String board, char[] hand, Map<String, Integer> memo) {
        // 如果board全部消除完了，直接返回
        if (board.length() == 0) {
            return 0;
        }

        // 如果緩存中已經處理過了，直接返回
        if (memo.containsKey(board)) {
            return memo.get(board);
        }

        // 存儲本次遞歸的結果
        int ans = INF;

        // 將手中的球填序的board的任意位置嘗試去消除
        for (int i = 0; i < hand.length; i++) {
            char c = hand[i];
            if (c != '0') {
                for (int j = 0; j < board.length(); j++) {
                    // 構造新的board，插入到舊board的任意位置
                    StringBuilder newBoard = new StringBuilder()
                            .append(board.substring(0, j))
                            .append(c)
                            .append(board.substring(j));
                    // 嘗試消除
                    removeSame(newBoard, j);
                    // 表示這個球已經用過了
                    hand[i] = '0';
                    // 進入下一次遞歸
                    ans = Math.min(ans, dfs(newBoard.toString(), hand, memo) + 1);
                    // 回溯，恢復狀態
                    hand[i] = c;
                }
            }
        }

        // 記錄到緩存中
        memo.put(board, ans);
        // 返回結果
        return ans;
    }

    private void removeSame(StringBuilder board, int index) {
        // 移除三個以上連續的
        if (index < 0) {
            return;
        }
        // 從index的位置向兩邊擴散
        int left = index, right = index;
        char c = board.charAt(index);
        // 注意這裡的操作
        while (--left >= 0 && board.charAt(left) == c) ;
        while (++right < board.length() && board.charAt(right) == c) ;

        // 擴散完了兩邊的right和left位置的值都是不等於 c 的，需要減一表示 c 出現的次數
        int count = right - left - 1;
        // 大於等於3才消除
        if (count >= 3) {
            board.delete(left + 1, right);
            // 連鎖反應，比如 YYGGGY，移除了中間的G，三個Y挨一塊了，也要移除
            removeSame(board, left);
        }
    }


}
