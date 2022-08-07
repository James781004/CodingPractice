package 程序员代码面试指南.ch09;

public class Q36_ArtistProblem {
//    CD89 畫匠問題
//    描述
//    給定一個整型數組arr, 數組中的每個值都為正數，表示完成一幅畫作需要的時間，
//    再給定一個整數num，表示畫匠的數量，每個畫匠只能畫連在一起(即數組內連續的一段)的畫作。
//    所有畫家並行工作，請返回完成所有的畫作的最少時間。
//            [要求]
//    時間覆雜度為O(nlogS)(其中S表示所有畫作所需時間的和)


    public static int solution1(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 1) {
            throw new RuntimeException("err");
        }
        int[] sumArr = new int[arr.length];
        int[] map = new int[arr.length];
        sumArr[0] = arr[0];
        map[0] = arr[0];
        for (int i = 1; i < sumArr.length; i++) {
            sumArr[i] = sumArr[i - 1] + arr[i];
            map[i] = sumArr[i];
        }
        for (int i = 1; i < num; i++) {
            for (int j = map.length - 1; j > i - 1; j--) {
                int min = Integer.MAX_VALUE;
                for (int k = i - 1; k < j; k++) {
                    int cur = Math.max(map[k], sumArr[j] - sumArr[k]);
                    min = Math.min(min, cur);
                }
                map[j] = min;
            }
        }
        return map[arr.length - 1];
    }

    // 四邊形不等式
    public static int solution2(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 1) {
            throw new RuntimeException("err");
        }
        int[] sumArr = new int[arr.length];
        int[] map = new int[arr.length];
        sumArr[0] = arr[0];
        map[0] = arr[0];
        for (int i = 1; i < sumArr.length; i++) {
            sumArr[i] = sumArr[i - 1] + arr[i];
            map[i] = sumArr[i];
        }
        int[] cands = new int[arr.length];
        for (int i = 1; i < num; i++) {
            for (int j = map.length - 1; j > i - 1; j--) {
                int minPar = cands[j];
                int maxPar = j == map.length - 1 ? j : cands[j + 1];
                int min = Integer.MAX_VALUE;
                for (int k = minPar; k < maxPar + 1; k++) {
                    int cur = Math.max(map[k], sumArr[j] - sumArr[k]);
                    if (cur <= min) {
                        min = cur;
                        cands[j] = k;
                    }
                }
                map[j] = min;
            }
        }
        return map[arr.length - 1];
    }


    // 貪心解法
    public static int solution3(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 1) {
            throw new RuntimeException("err");
        }
        if (arr.length < num) { // 畫家數量多過arr長度，那一人處理一幅畫即可
            int max = Integer.MIN_VALUE;
            for (int i = 0; i != arr.length; i++) {
                max = Math.max(max, arr[i]);
            }
            return max;
        } else {
            int minSum = 0;
            int maxSum = 0;
            for (int i = 0; i < arr.length; i++) {
                maxSum += arr[i];  // maxSum代表需要的最大時數
            }

            // 用二分法調整限制時數
            while (minSum != maxSum - 1) {
                int mid = (minSum + maxSum) / 2;
                if (getNeedNum(arr, mid) > num) {
                    minSum = mid; // 需要的畫家數量超過了num，就調高minSum，也就是增加限制時數(加重負荷)
                } else {
                    maxSum = mid;  // 需要的畫家數量過少或剛好，可以嘗試調低maxSum，也就是減少限制時數(減少負荷)
                }
            }

            // 注意mid是單位畫家同步作業的最大負荷時數，
            // 而上面的二分搜索已經設計成在得到符合答案時會把maxSum置換為mid
            // 所以maxSum才是我們需要的整體最大時數
            return maxSum;
        }
    }

    // 求取同時限內同步作業所需的單位畫家數
    public static int getNeedNum(int[] arr, int lim) {
        int res = 1;
        int stepSum = 0;
        for (int i = 0; i != arr.length; i++) {
            if (arr[i] > lim) {
                return Integer.MAX_VALUE;
            }
            stepSum += arr[i];
            if (stepSum > lim) { // 如果累積時數超過限制，就交給下一位畫家處理，res++
                res++;
                stepSum = arr[i];
            }
        }
        return res;
    }

    public static int[] generateRandomArray(int length) {
        int[] result = new int[length];
        for (int i = 0; i != result.length; i++) {
            result[i] = (int) (Math.random() * 10) + 1;
        }
        return result;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = generateRandomArray(300);
        int painterNum = 2;
        System.out.println(solution1(arr, painterNum));
        System.out.println(solution2(arr, painterNum));
        System.out.println(solution3(arr, painterNum));

        arr = generateRandomArray(5000000);
        painterNum = 20000;
        long start = System.currentTimeMillis();
        System.out.println(solution3(arr, painterNum));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");

    }
}
