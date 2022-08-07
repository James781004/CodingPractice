package TeacherZuoCodingInterviewGuide.ch09;

public class Q10_MaxABSBetweenLeftAndRight {
//    CD63 最大的leftMax與rightMax之差的絕對值
//    描述
//    給定一個長度為N(N>1)的整形數組arr, 可以劃分成左右兩個部分，左部分為arr[0…K]，右部分為arr[K+1…N-1],
//    K可以取值的範圍是[0,N-2]。求這麽多劃分方案中，左部分中的最大值減去右部分最大值的絕對值中，最大是多少
//[要求]
//    時間覆雜度為O(n), 空間覆雜度為O(n)


    public static int maxABS1(int[] arr) {
        int res = Integer.MIN_VALUE;
        int maxLeft = 0;
        int maxRight = 0;
        for (int i = 0; i != arr.length - 1; i++) {
            maxLeft = Integer.MIN_VALUE;
            for (int j = 0; j != i + 1; j++) {
                maxLeft = Math.max(arr[j], maxLeft);
            }
            maxRight = Integer.MIN_VALUE;
            for (int j = i + 1; j != arr.length; j++) {
                maxRight = Math.max(arr[j], maxRight);
            }
            res = Math.max(Math.abs(maxLeft - maxRight), res);
        }
        return res;
    }

    // 用預處理的技巧。構建一個前綴和數組leftMax，數組中的i位置表示從0到當前位置的最大值；
    // 再構建一個後綴數組rightMax，數組中的位置i表示從下一個位置到數組末尾的最大值，這樣空間復雜度為O(N)。
    // 然後我們只需要求得這兩個數組對應位置差的絕對值最大值就可以了，一共遍歷三遍數組，時間復雜度為O(N)。
    public static int maxABS2(int[] arr) {
        int[] lArr = new int[arr.length];
        int[] rArr = new int[arr.length];
        lArr[0] = arr[0];
        rArr[arr.length - 1] = arr[arr.length - 1];
        for (int i = 1; i < arr.length; i++) {
            lArr[i] = Math.max(lArr[i - 1], arr[i]);
        }
        for (int i = arr.length - 2; i > -1; i--) {
            rArr[i] = Math.max(rArr[i + 1], arr[i]);
        }
        int max = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            max = Math.max(max, Math.abs(lArr[i] - rArr[i + 1]));
        }
        return max;
    }

    // 全局最大值被分到了左邊的數組，由於被劃分的兩部分數組都不能是空，因此右邊的數組一定包括數組的最後一個值arr[n-1]。
    // 如果我們擴大右邊的子數組，就會增加arr[n-1]之外的數，
    // 如果新增的數比它大，那麼就會增加右邊數組的最大值，從而減小右邊數組最大值和左邊數組最大值的絕對差；
    // 如果新增的數比它小，由於不會改變右邊的最大值，所以並不會改變這個絕對差。
    // 因此，絕對差最大的情況與右邊的數組只有arr[n-1]一個數的情況是等效的。
    // 同理可得，當全局最大值被分配到右邊數組的時候，絕對差取得最大值的情況與左邊的數組只有arr[0]一個數的情況是等效的。
    public static int maxABS3(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(arr[i], max);
        }
        return max - Math.min(arr[0], arr[arr.length - 1]);
    }

    public static int[] generateRandomArray(int length) {
        int[] arr = new int[length];
        for (int i = 0; i != arr.length; i++) {
            arr[i] = (int) (Math.random() * 1000) - 499;
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = generateRandomArray(200);
        System.out.println(maxABS1(arr));
        System.out.println(maxABS2(arr));
        System.out.println(maxABS3(arr));
    }
}
