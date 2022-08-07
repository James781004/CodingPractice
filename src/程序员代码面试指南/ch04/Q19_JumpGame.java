package 程序员代码面试指南.ch04;

public class Q19_JumpGame {
//    鏈接：https://www.nowcoder.com/questionTerminal/b7d9d79453bf43bf9594e91d24260605
//    跳躍遊戲
//    給定數組arr，arr[i]==k代表可以從位置向右跳1~k個距離。
//    比如，arr[2]==3,代表可以從位置2跳到位置3、位置4或位置5。
//    如果從位置0出發，返回最少跳幾次能跳到arr最後的位置上。

    public static int jump(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int jump = 0; // 目前跳躍步數
        int cur = 0; // 當前所在位置
        int next = 0; // 下一步可以到達的最遠位置
        for (int i = 0; i < arr.length; i++) {

            // 如果當前所在位置已經小於下標位置
            // 代表必須往後移動一步
            // jump增加一步，cur移動到下一步可以到達的最遠位置
            if (cur < i) {
                jump++;
                cur = next;
            }

            // 更新下一步可以到達的最遠位置
            next = Math.max(next, i + arr[i]);
        }
        return jump;
    }


    // 貪心算法，從右往左盡可能多地回推，直至到達位置0
    private static int recurrent(int[] arr, int index) {
        if (index == 0) return 0;
        int start = index;
        // 將起點回退到能一步跳到index的最左點，以保證步數最少
        for (int i = 0; i < index; i++) {
            if (index - i <= arr[i]) {
                // 如果在i處可以一下跳到index處，就把起點往左推
                start = Math.min(start, i);
            }
        }
        return 1 + recurrent(arr, start);
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 3, 1, 1, 4};
        System.out.println(jump(arr));

    }
}
