package TeacherZuoCodingInterviewGuide.ch09;

public class Q26_CandyProblem {
//    CD77 分糖果問題
//    描述
//    一群孩子做遊戲，現在請你根據遊戲得分來發糖果，要求如下：
//            1. 每個孩子不管得分多少，起碼分到一個糖果。
//            2. 任意兩個相鄰的孩子之間，得分較多的孩子必須拿多一些糖果。(若相同則無此限制)
//    給定一個數組arr代表得分數組，請返回最少需要多少糖果。
//    CD78 分糖果問題進階問題
//    描述
//    一群孩子做遊戲，現在請你根據遊戲得分來發糖果，要求如下：
//            1. 每個孩子不管得分多少，起碼分到一個糖果。
//            2. 任意兩個相鄰的孩子之間，得分較多的孩子必須拿多一些糖果。
//            3. 任意兩個相鄰的孩子之間的得分如果一樣多，糖果數必須相同
//    給定一個數組arr代表得分數組，請返回最少需要多少糖果。


    // CD77 貪心算法：從左往右檢查一遍，從右往左再檢查一遍，以最小限度超越旁邊分數比自己低且糖果還比自己多的小朋友
//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        int n = Integer.parseInt(br.readLine());
//        String[] strArr = br.readLine().split(" ");
//        int[] scores = new int[n];
//        for (int i = 0; i < n; i++) scores[i] = Integer.parseInt(strArr[i]);
//        int[] candy = new int[n];
//        Arrays.fill(candy, 1);
//        // 從左往右
//        for (int i = 1; i < n; i++) {
//            if (scores[i] > scores[i - 1] && candy[i] <= candy[i - 1])
//                candy[i] = candy[i - 1] + 1;
//        }
//        // 從右往左
//        for (int i = n - 2; i >= 0; i--) {
//            if (scores[i] > scores[i + 1] && candy[i] <= candy[i + 1])
//                candy[i] = candy[i + 1] + 1;
//        }
//        int total = 0;
//        for (int i = 0; i < n; i++) total += candy[i];
//        System.out.println(total);
//    }


    // CD78 從左往右按照題中規則調整一遍糖果數，然後從右往左再調整一遍糖果數即可
//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        int n = Integer.parseInt(br.readLine());
//        String[] strArr = br.readLine().split(" ");
//        int[] scores = new int[n];
//        for(int i = 0; i < n; i++) scores[i] = Integer.parseInt(strArr[i]);
//        int[] candy = new int[n];
//        Arrays.fill(candy, 1);
//        // 從左往右
//        for(int i = 1; i < n; i++){
//            if(scores[i] > scores[i - 1] && candy[i] <= candy[i - 1]){
//                candy[i] = candy[i - 1] + 1;   // 最小限度超過鄰居
//            }else if(scores[i] == scores[i - 1]){
//                candy[i] = candy[i - 1]; // 相同個數則糖果數相同
//            }
//        }
//        // 從右往左
//        for(int i = n - 2; i >= 0; i--){
//            if(scores[i] > scores[i + 1] && candy[i] <= candy[i + 1]){
//                candy[i] = candy[i + 1] + 1;
//            }else if(scores[i] == scores[i + 1]){
//                candy[i] = candy[i + 1]; // 相同個數則糖果數相同
//            }
//        }
//        int total = 0;
//        for(int i = 0; i < n; i++) total += candy[i];
//        System.out.println(total);
//    }

    // 找到左坡以及右坡，湊成一對進行計算
    // 推薦使用上面的貪心算法，比較直觀
    public static int candy1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int index = nextMinIndex1(arr, 0); // 不斷上坡找坡頂
        int res = rightCands(arr, 0, index++);
        int lbase = 1;
        int next = 0;
        int rcands = 0;
        int rbase = 0;
        while (index != arr.length) {
            if (arr[index] > arr[index - 1]) { // 上坡
                res += ++lbase;
                index++;
            } else if (arr[index] < arr[index - 1]) { // 下坡
                next = nextMinIndex1(arr, index - 1); // 不斷上坡找坡頂
                rcands = rightCands(arr, index - 1, next++);
                rbase = next - index + 1;
                res += rcands + (rbase > lbase ? -lbase : -rbase);
                lbase = 1;
                index = next;
            } else {
                res += 1;
                lbase = 1;
                index++;
            }
        }
        return res;
    }

    public static int nextMinIndex1(int[] arr, int start) {
        for (int i = start; i != arr.length - 1; i++) {
            if (arr[i] <= arr[i + 1]) {
                return i;
            }
        }
        return arr.length - 1;
    }

    public static int rightCands(int[] arr, int left, int right) {
        int n = right - left + 1;
        return n + n * (n - 1) / 2;
    }

    public static int candy2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int index = nextMinIndex2(arr, 0);
        int[] data = rightCandsAndBase(arr, 0, index++);
        int res = data[0];
        int lbase = 1;
        int same = 1;
        int next = 0;
        while (index != arr.length) {
            if (arr[index] > arr[index - 1]) {
                res += ++lbase;
                same = 1;
                index++;
            } else if (arr[index] < arr[index - 1]) {
                next = nextMinIndex2(arr, index - 1);
                data = rightCandsAndBase(arr, index - 1, next++);
                if (data[1] <= lbase) {
                    res += data[0] - data[1];
                } else {
                    res += -lbase * same + data[0] - data[1] + data[1] * same;
                }
                index = next;
                lbase = 1;
                same = 1;
            } else {
                res += lbase;
                same++;
                index++;
            }
        }
        return res;
    }

    public static int nextMinIndex2(int[] arr, int start) {
        for (int i = start; i != arr.length - 1; i++) {
            if (arr[i] < arr[i + 1]) {
                return i;
            }
        }
        return arr.length - 1;
    }

    public static int[] rightCandsAndBase(int[] arr, int left, int right) {
        int base = 1;
        int cands = 1;
        for (int i = right - 1; i >= left; i--) {
            if (arr[i] == arr[i + 1]) {
                cands += base;
            } else {
                cands += ++base;
            }
        }
        return new int[]{cands, base};
    }

    public static void main(String[] args) {
        int[] test1 = {3, 0, 5, 5, 4, 4, 0};
        System.out.println(candy1(test1));

        int[] test2 = {3, 0, 5, 5, 4, 4, 0};
        System.out.println(candy2(test2));
    }

}
