package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

public class IsValidSerialization {

    // https://leetcode.cn/problems/verify-preorder-serialization-of-a-binary-tree/solutions/2717573/javapython3cji-shu-tong-ji-gou-jian-huan-8ori/
    public boolean isValidSerialization(String preorder) {
        int need = 1;   // 統計需要的節點數，初始為1表示需要1個根節點
        int n = preorder.length();
        int idx = 0;
        // 遍歷整個前序字符串
        while (idx < n) {
            if (need == 0) return false;  // 字符串還沒遍歷完就不需要節點了，肯定不正確
            if (preorder.charAt(idx) == ',') {
                // 分隔字符，繼續向後處理
                idx++;
            } else if (preorder.charAt(idx) == '#') {
                // 空節點，可以減少1個需要節點
                need--;
                idx++;
            } else {
                // 數字，找到這個值的完整范圍，即到下一個分隔點
                // 非空節點減少1個需要節點，但又需要兩個子節點，-1+2=+1
                while (idx < n && preorder.charAt(idx) != ',') idx++;
                need++;
            }
        }
        return need == 0;   // 前序字符串遍歷完還需要節點，肯定不正確
    }


}
