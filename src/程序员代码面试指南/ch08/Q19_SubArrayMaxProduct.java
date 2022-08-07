package 程序员代码面试指南.ch08;

public class Q19_SubArrayMaxProduct {
//    CD32 數組中子數組的最大累乘積
//    描述
//    給定一個double類型的數組arr，其中的元素可正、可負、可0，返回子數組累乘的最大乘積。
//    例如，arr=[-2.5, 4, 0, 3, 0.5, 8, -1]，子數組[3, 0.5, 8]累乘可以獲得最大的乘積12，所以返回12
//[要求]
//    時間覆雜度為O(n)，空間覆雜度為O(1)

    public static double maxProduct(double[] arr) {
        if (arr == null || arr.length == 0) return 0;
        double max = arr[0]; // 表示最大累計結果
        double min = arr[0]; // 表示最小累計結果
        double res = arr[0];
        double maxEnd = 0; // 存放當前滾動結果用
        double minEnd = 0; // 存放當前滾動結果用

        // 答案來自三種可能選項：
        // 1. arr[i]乘上最大值max
        // 2. arr[i]乘上最小值min，如果兩者都是負數的話
        // 3. arr[i]自己
        // 所以滾動更新max和min來求得答案
        for (int i = 1; i < arr.length; i++) {
            maxEnd = max * arr[i];
            minEnd = min * arr[i];
            max = Math.max(Math.max(maxEnd, minEnd), arr[i]);
            min = Math.min(Math.min(maxEnd, minEnd), arr[i]);
            res = Math.max(max, min);
        }
        return res;
    }

    public static void main(String[] args) {
        double[] arr = {-2.5, 4, 0, 3, 0.5, 8, -1};
        System.out.println(maxProduct(arr));

    }
}
