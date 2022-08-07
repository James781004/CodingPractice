package 劍指offer.BinarySearch;

public class Q53_GetNumberOfK {

    // 先定位到左边界
    // 接着定位右边界
    // 判断左边界是否符合规范，若超出数组范围，则证明目标值出现的次数为0
    // 否则，右边界减去左边界即能统计出目标出现的次数
    public int GetNumberOfK(int[] array, int k) {
        // 找到左边界
        int first = binarySearch(array, k);
        // 找到右边界（注意：k+1）
        int last = binarySearch(array, k + 1);
        // 若超出数组范围，则证明目标值出现的次数为0，否则，右边界减去左边界即能统计出目标出现的次数
        return (first == array.length || array[first] != k) ? 0 : last - first;
    }

    private int binarySearch(int[] array, int k) {
        int l = 0, r = array.length;
        while (l < r) {
            // 二分求中点
            int m = l + (r - l) / 2;
            if (array[m] >= k)
                r = m;
            else
                l = m + 1;
        }
        // 返回左边界
        return l;
    }
}
