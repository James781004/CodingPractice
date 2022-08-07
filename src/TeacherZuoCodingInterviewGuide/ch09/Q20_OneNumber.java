package TeacherZuoCodingInterviewGuide.ch09;

public class Q20_OneNumber {
//    CD72 1到n中1的出现次数
//    描述
//    给定一个整数n，返回从1到n的数字中1出现的个数。
//    例如：
//    n=5,1∼n为1, 2, 3, 4, 5。1,2,3,4,5。那么1出现了1次，所以返回1。
//    n=11,1∼n为1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11。1,2,3,4,5,6,7,8,9,10,11。
//    那么1出现的次数为1(出现1次)，10(出现1次)，11(有两个1，所以出现了2次)，所以返回4。


    // 参考：https://blog.csdn.net/qq_25024883/article/details/80337312
    public static int solution1(int num) {
        if (num < 1) {
            return 0;
        }
        int count = 0;
        for (int i = 1; i != num + 1; i++) {
            count += get1Nums(i);
        }
        return count;
    }

    public static int get1Nums(int num) {
        int res = 0;
        while (num != 0) {
            if (num % 10 == 1) {
                res++;
            }
            num /= 10;
        }
        return res;
    }

    public static int solution2(int num) {
        if (num < 1) {
            return 0;
        }
        int len = getLenOfNum(num);
        if (len == 1) {
            return 1;
        }
        int tmp1 = powerBaseOf10(len - 1);
        int first = num / tmp1;
        int firstOneNum = first == 1 ? num % tmp1 + 1 : tmp1;
        int otherOneNum = first * (len - 1) * (tmp1 / 10);
        return firstOneNum + otherOneNum + solution2(num % tmp1);
    }

    public static int getLenOfNum(int num) {
        int len = 0;
        while (num != 0) {
            len++;
            num /= 10;
        }
        return len;
    }

    public static int powerBaseOf10(int base) {
        return (int) Math.pow(10, base);
    }

    public static void main(String[] args) {
        int num = 50000000;
        long start1 = System.currentTimeMillis();
        System.out.println(solution1(num));
        long end1 = System.currentTimeMillis();
        System.out.println("cost time: " + (end1 - start1) + " ms");

        long start2 = System.currentTimeMillis();
        System.out.println(solution2(num));
        long end2 = System.currentTimeMillis();
        System.out.println("cost time: " + (end2 - start2) + " ms");

    }
}
