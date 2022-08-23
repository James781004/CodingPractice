package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q01_Combination {
//    第77題. 組合
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0077.%E7%BB%84%E5%90%88.md
//
//    給定兩個整數 n 和 k，返回 1 ... n 中所有可能的 k 個數的組合。
//
//    示例:
//    輸入: n = 4, k = 2
//    輸出:
//            [
//            [2,4],
//            [3,4],
//            [2,3],
//            [1,2],
//            [1,3],
//            [1,4],
//            ]


    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();

    public List<List<Integer>> combine(int n, int k) {
        process(n, k, 1);
        return result;
    }

    /**
     * 每次從集合中選取元素，可選擇的範圍隨著選擇的進行而收縮，調整可選擇的範圍，就是要靠startIndex
     *
     * @param startIndex 用來記錄本層遞歸中，集合從哪里開始遍歷（集合就是[1,...,n] ）。
     */
    private void process(int n, int k, int startIndex) {

        // 終止條件
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }

        // for循環代表目前層數的橫向遍歷，可以進行剪枝操作：
        // 1. 已經選擇的元素個數：path.size();
        // 2. 還需要的元素個數為: k - path.size();
        // 3. 在集合n中至多要從該起始位置 : n(集合總數) - (k - path.size()) + 1，開始遍歷
        // 注意要+1是因為包括起始位置，我們要是一個左閉的集合。
        // 可以剪枝的地方就在遞歸中每一層的for循環所選擇的起始位置。
        // 如果for循環選擇的起始位置之後的元素個數 已經不足 我們需要的元素個數了，那麽就沒有必要搜索了。
        for (int i = startIndex; i <= n - (k - path.size()) + 1; i++) {
            path.add(i);
            process(n, k, i + 1);  // 遞歸：控制樹的縱向遍歷，注意下一層搜索要從i+1開始
            path.removeLast();
        }
    }

}
