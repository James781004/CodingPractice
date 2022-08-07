package 劍指offer.Tree;

public class Q33_VerifySquenceOfBST {
    public boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) return false;
        return verify(sequence, 0, sequence.length - 1);
    }

    private boolean verify(int[] sequence, int first, int last) {
        if (last - first <= 1) return true; // 若当前子树只有一个节点
        int rootVal = sequence[last]; // 当前子树的根节点
        int cutIndex = first;
        while (cutIndex < last && sequence[cutIndex] <= rootVal)
            cutIndex++; // 划分出左子树
        for (int i = cutIndex; i < last; i++)
            if (sequence[i] < rootVal) return false; // 检查右子树是不是存在小于根节点的数

        // 分治法检查左子树和右子树
        return verify(sequence, first, cutIndex - 1) && verify(sequence, cutIndex, last - 1);
    }

}
