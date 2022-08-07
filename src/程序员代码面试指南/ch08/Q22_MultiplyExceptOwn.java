package 程序员代码面试指南.ch08;

public class Q22_MultiplyExceptOwn {
//    CD35 不包含本位置值的累乘數組
//    描述
//    給定一個數組arr，返回不包含本位置值的累乘數組
//    例如，arr=[2,3,1,4]，返回[12, 8, 24, 6]，即除自己外，其他位置上的累乘
//[要求]
//    時間覆雜度為O(n)，額外空間覆雜度為O(1)

    public static int[] product1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }
        int count = 0;
        int all = 1;
        for (int i = 0; i != arr.length; i++) {
            if (arr[i] != 0) {
                all *= arr[i];
            } else {
                count++;
            }
        }
        int[] res = new int[arr.length];
        if (count == 0) {
            for (int i = 0; i != arr.length; i++) {
                res[i] = all / arr[i];
            }
        }
        if (count == 1) {
            for (int i = 0; i != arr.length; i++) {
                if (arr[i] == 0) {
                    res[i] = all;
                }
            }
        }
        return res;
    }

    public static int[] product2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }
        int[] res = new int[arr.length];

        // 左邊第一位先放arr首位數字，然後依序向右算出"包含自己"的左方累計(也就是原本的left[i+1])
        // 所以這個流程是在把res數組初始化成left數組，但是每個元素相當於往前挪了一位
        // 原始left數組：left[i] 表示 arr[0...i-1]的乘積
        // 現在res[i]的含意為"arr[0...i]的乘積"
        res[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            res[i] = res[i - 1] * arr[i];
        }

        // 右邊第一位先乘上1，然後依序向左算出"除自己之外"右方累計
        // 使用tmp來存放原本right數組中的right[i+1]
        // 原始right數組：right[i] 表示 arr[i...N-1]的乘積
        // 現在tmp的含意為"arr[i+1...N-1]的乘積"
        int tmp = 1;
        for (int i = arr.length - 1; i > 0; i--) {
            res[i] = res[i - 1] * tmp;  // res[i]普遍位置結果是 arr[0...i-1]的乘積 * arr[i+1...N-1]的乘積
            tmp *= arr[i];
        }


        // res[0] == r[1], res[N-1] == l[N-2]
        res[0] = tmp;
        return res;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        int[] res1 = product1(arr);
        printArray(res1);
        int[] res2 = product2(arr);
        printArray(res2);

    }
}
