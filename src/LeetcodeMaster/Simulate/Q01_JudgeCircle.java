package LeetcodeMaster.Simulate;

public class Q01_JudgeCircle {
//    657. 機器人能否返回原點
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0657.%E6%9C%BA%E5%99%A8%E4%BA%BA%E8%83%BD%E5%90%A6%E8%BF%94%E5%9B%9E%E5%8E%9F%E7%82%B9.md
//
//    在二維平面上，有一個機器人從原點 (0, 0) 開始。給出它的移動順序，判斷這個機器人在完成移動後是否在 (0, 0) 處結束。
//
//    移動順序由字符串表示。字符 move[i] 表示其第 i 次移動。機器人的有效動作有 R（右），L（左），U（上）和 D（下）。
//    如果機器人在完成所有動作後返回原點，則返回 true。否則，返回 false。
//
//    注意：機器人“面朝”的方向無關緊要。 “R” 將始終使機器人向右移動一次，“L” 將始終向左移動等。此外，假設每次移動機器人的移動幅度相同。
//
//
//
//    示例 1:
//
//    輸入: "UD"
//    輸出: true
//    解釋：機器人向上移動一次，然後向下移動一次。所有動作都具有相同的幅度，因此它最終回到它開始的原點。因此，我們返回 true。
//    示例 2:
//
//    輸入: "LL"
//    輸出: false
//    解釋：機器人向左移動兩次。它最終位於原點的左側，距原點有兩次 “移動” 的距離。我們返回 false，因為它在移動結束時沒有返回原點。

    public boolean judgeCircle(String moves) {
        int x = 0, y = 0;
        for (char c : moves.toCharArray()) {
            if (c == 'U') y++;
            if (c == 'D') y--;
            if (c == 'L') x++;
            if (c == 'R') x--;
        }
        return x == 0 && y == 0;
    }
}
