package FuckingAlgorithm.Graph;

public class Q08_FindCelebrity {
//    https://blog.csdn.net/qq_21201267/article/details/107369072
//    LeetCode 277. 搜尋名人
//    假設你是一個專業的狗仔，參加了一個 n 人派對，其中每個人被從 0 到 n - 1 標號。
//    在這個派對人群當中可能存在一位 “名人”。
//    所謂 “名人” 的定義是：其他所有 n - 1 個人都認識他/她，而他/她並不認識其他任何人。
//
//    現在你想要確認這個 “名人” 是誰，或者確定這裡沒有 “名人”。
//    而你唯一能做的就是問諸如 “A 你好呀，請問你認不認識 B呀？” 的問題，以確定 A 是否認識 B。
//    你需要在（漸近意義上）盡可能少的問題內來確定這位 “名人” 是誰（或者確定這裡沒有 “名人”）。
//
//    在本題中，你可以使用輔助函數 bool knows(a, b) 獲取到 A 是否認識 B。請你來實現一個函數 int findCelebrity(n)。
//
//    派對最多只會有一個 “名人” 參加。
//    若 “名人” 存在，請返回他/她的編號；若 “名人” 不存在，請返回 -1。

    // 可以直接調用，能夠返回 i 是否認識 j
    boolean knows(int i, int j) {
        return false;
    }

    // 「名人」的定義：
    // 1、所有其他人都認識名人。
    // 2、名人不認識任何其他人。
    // 如果有兩個人同時是名人，那麼這兩條定義就自相矛盾了。
    // 換句話說，只要觀察任意兩個候選人的關系，我一定能確定其中的一個人不是名人，把他排除。
    int findCelebrity(int n) {
        // 先假設 cand 是名人
        int cand = 0;
        for (int other = 1; other < n; other++) {
            if (!knows(other, cand) || knows(cand, other)) {
                // cand 不可能是名人，排除
                // 假設 other 是名人
                cand = other;
            } else {
                // other 不可能是名人，排除
                // 什麼都不用做，繼續假設 cand 是名人
            }
        }

        // 現在的 cand 是排除的最後結果，但不能保證一定是名人
        for (int other = 0; other < n; other++) {
            if (cand == other) continue;
            // 需要保證其他人都認識 cand，且 cand 不認識任何其他人
            if (!knows(other, cand) || knows(cand, other)) {
                return -1;
            }
        }

        return cand;
    }

}
