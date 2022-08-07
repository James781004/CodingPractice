package TeacherZuoCodingInterviewGuide.ch09;

public class Q03_FactorialProblem {
//    CD56 有關階乘的兩個問題1
//    描述
//    給定一個非負整數N，返回N!結果的末尾為0的數量
//    CD57 有關階乘的兩個問題
//    描述
//    給定一個非負整數N，如果用二進制數表達N!的結果，返回最低位的1在哪個位置上，認為最右的位置為位置0。

    // 每一對2和5就能夠乘出一個0來，因此我們需要統計有多少對2和5的因數。
    // 事實上，我們只需要統計有多少個5即可，因為2的數量遠遠多於5。
    public static int zeroNum1(int num) {
        if (num < 0) return 0;
        int res = 0;
        int cur = 0;
        for (int i = 5; i < num + 1; i += 5) {
            cur = i;
            while (cur % 5 == 0) {
                res++;
                cur /= 5;
            }
        }
        return res;
    }


    public static int zeroNum2(int num) {
        if (num < 0) {
            return 0;
        }
        int res = 0;
        while (num != 0) {
            res += num / 5;
            num /= 5;
        }
        return res;
    }

    public static int rightOne1(int num) {
        if (num < 1) {
            return -1;
        }
        int res = 0;
        while (num != 0) {
            num >>>= 1;
            res += num;
        }
        return res;
    }

    public static int rightOne2(int num) {
        if (num < 1) {
            return -1;
        }
        int ones = 0;
        int tmp = num;
        while (tmp != 0) {
            ones += (tmp & 1) != 0 ? 1 : 0;
            tmp >>>= 1;
        }
        return num - ones;
    }

    public static void main(String[] args) {
        int num = 1000000000;

        System.out.println(zeroNum2(num));
        System.out.println(zeroNum1(num));

        System.out.println(rightOne2(num));
        System.out.println(rightOne1(num));

    }

}
