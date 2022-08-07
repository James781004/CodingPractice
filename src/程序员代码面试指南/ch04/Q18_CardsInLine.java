package 程序员代码面试指南.ch04;

public class Q18_CardsInLine {
//    排成一條線的紙牌博弈問題
//    描述
//    給定一個整型數組arr，代表數值不同的紙牌排成一條線，玩家A和玩家B依次拿走每張紙牌，
//    規定玩家A先拿，玩家B後拿，但是每個玩家每次只能拿走最左和最右的紙牌，
//    玩家A和玩家B絕頂聰明。請返回最後的獲勝者的分數。

    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        return Math.max(f(arr, 0, arr.length - 1), s(arr, 0, arr.length - 1));
    }

    public static int f(int[] arr, int head, int back) {
        if (head == back) return arr[head]; // 只剩一張牌，只有當下先手玩家可以拿走

        // 題目規定只能拿頭尾
        // 所以先手玩家拿走頭之後，在剩下牌堆head+1 ~ back的區間玩家就變成後手
        // 同理先手玩家拿走尾之後，在剩下牌堆head ~ back-1的區間玩家就變成後手
        // 兩種情況找最大的
        return Math.max(arr[head] + s(arr, head + 1, back), arr[back] + s(arr, head, back - 1));
    }

    public static int s(int[] arr, int i, int j) {
        if (i == j) return 0; // 只剩一張牌，當下後手玩家不能拿走

        // 題目設定先手玩家必定選擇拿走較大的選項
        // 因此對後手玩家來說只會剩下比較小的選項
        return Math.min(f(arr, i + 1, j), f(arr, i, j - 1));
    }

    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) return 0;

        // 建立先手表格以及後手表格
        int[][] f = new int[arr.length][arr.length];
        int[][] s = new int[arr.length][arr.length];

        for (int right = 0; right < arr.length; right++) { // right代表目前數組右邊界位置
            for (int left = right; left >= 0; left--) { // left代表目前數組左邊界位置
                if (left == right) {
                    f[left][right] = arr[left]; // 只剩一張牌，只有當下先手玩家可以拿走
                    s[left][right] = 0; // 只剩一張牌，當下後手玩家不能拿走
                } else {
                    // 所以先手玩家拿走頭之後，在剩下牌堆head+1 ~ back的區間玩家就變成後手
                    // 同理先手玩家拿走尾之後，在剩下牌堆head ~ back-1的區間玩家就變成後手
                    // 兩種情況找最大的
                    f[left][right] = Math.max(arr[left] + s[left + 1][right], arr[right] + s[left][right - 1]);

                    // 題目設定先手玩家必定選擇拿走較大的那邊
                    // 因此對後手玩家來說只會剩下比較小的選項
                    s[left][right] = Math.min(f[left + 1][right], f[left][right - 1]);
                }
            }
        }
        return Math.max(f[0][arr.length - 1], s[0][arr.length - 1]);
    }

}
