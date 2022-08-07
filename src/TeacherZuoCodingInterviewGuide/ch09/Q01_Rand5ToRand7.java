package TeacherZuoCodingInterviewGuide.ch09;

public class Q01_Rand5ToRand7 {
//    題目
//    給出一個隨機產生1到5的函數，不適用其它隨機函數的前提下，設計算法返回隨機產生1到7的的數字。
//擴展
//        題目1
//    有個函數以p概率返回0，1-p的概率返回1，用這個函數隨機產生1到6的數字。
//    題目2
//    給定任意一個隨機產生1到m的隨機函數，用它設計一個隨機產生1到n的算法。


    public static int rand1To5() {
        return (int) (Math.random() * 5) + 1;
    }

    // 假設原始函數為rand1to5()，它能夠隨機的產生1，2，3，4，5這5個數字。
    // 那麼rand1to5() - 1能夠隨機產生0，1，2，3，4這5個數字。
    // 那麼(rand1to5() - 1) * 5能夠隨機產生0， 5， 10， 15， 20這5個數字。
    // 那麼(rand1to5() - 1) * 5 + rand1to5() - 1能夠隨機產生0~24。
    // 假設用t來保存這個數值。（注意，因為分別兩次使用了rand1to5()這個函數，實際上這兩次產生的值是沒有關聯的）
    // 如果t大於20的話，那麼重新計算一次(rand1to5() - 1) * 5 + rand1to5() - 1，一直到t小於等於20，
    // 由於大於20的概率是隨機的，所以可以認為隨機產生了0到20之間的21個數字。
    // 這樣t % 7 + 1就可以隨機產生1到7之間的數字了。
    public static int rand1To7() {
        int num = 0;
        do {
            num = (rand1To5() - 1) * 5 + rand1To5() - 1;
        } while (num > 20);
        return num % 7 + 1;
    }

    // 假設這個函數是rand0to1()，調用它兩次，
    // 第一次是0，第二次是1（即01）的概率是p * （1 - p），
    // 第一次是1，第二次是0（即10）的概率是（1 - p）* p,他們的概率是一樣的，即可以隨機產生01和10，
    // 此時只要取第一次（或者取第二次）產生的數就可以隨機產生0和1了，假設為rand0and1();
    // 同上面的做法，rand0and1() * 2 可以隨機產生0和2；
    // 那麼rand0and1() * 2 + rand0and1()可以隨機產生0，1，2，3，記為rand0to3().
    // 那麼rand0to3() * 2可以隨機產生0，2，4，6；
    //  那麼rand0to3() * 2 + rand0and1()，可以隨機產生0，1，2，3，4，5，6，7，記為t
    // 如果t>5就重新計算rand0to3() * 2 + rand0and1()，一直到t <= 5;
    // 那麼t % 6 + 1就能隨機產生1到6的數字了。
    public static int rand01p() {
        // you can change p to what you like, but it must be (0,1)
        double p = 0.83;
        return Math.random() < p ? 0 : 1;
    }

    public static int rand01() {
        int num;
        do {
            num = rand01p();
        } while (num == rand01p());
        return num;
    }

    public static int rand0To3() {
        return rand01() * 2 + rand01();
    }

    public static int rand1To6() {
        int num = 0;
        do {
            num = rand0To3() * 4 + rand0To3();
        } while (num > 11);
        return num % 6 + 1;
    }

    public static int rand1ToM(int m) {
        return (int) (Math.random() * m) + 1;
    }

    public static int rand1ToN(int n, int m) {
        int[] nMSys = getMSysNum(n - 1, m);
        int[] randNum = getRanMSysNumLessN(nMSys, m);
        return getNumFromMSysNum(randNum, m) + 1;
    }

    // 把value转成m进制的数
    public static int[] getMSysNum(int value, int m) {
        int[] res = new int[32];
        int index = res.length - 1;
        while (value != 0) {
            res[index--] = value % m;
            value = value / m;
        }
        return res;
    }

    // 等概率随机产生一个0~nMsys范围上的数，只不过是m进制表达的。
    public static int[] getRanMSysNumLessN(int[] nMSys, int m) {
        int[] res = new int[nMSys.length];
        int start = 0;
        while (nMSys[start] == 0) {
            start++;
        }
        int index = start;
        boolean lastEqual = true;
        while (index != nMSys.length) {
            res[index] = rand1ToM(m) - 1;
            if (lastEqual) {
                if (res[index] > nMSys[index]) {
                    index = start;
                    lastEqual = true;
                    continue;
                } else {
                    lastEqual = res[index] == nMSys[index];
                }
            }
            index++;
        }
        return res;
    }

    // 把m进制的数转成10进制
    public static int getNumFromMSysNum(int[] mSysNum, int m) {
        int res = 0;
        for (int i = 0; i != mSysNum.length; i++) {
            res = res * m + mSysNum[i];
        }
        return res;
    }

    public static void printCountArray(int[] countArr) {
        for (int i = 0; i != countArr.length; i++) {
            System.out.println(i + " appears " + countArr[i] + " times");
        }
    }

    public static void main(String[] args) {
        int testTimes = 1000000;
        int[] countArr1 = new int[8];
        for (int i = 0; i != testTimes; i++) {
            countArr1[rand1To7()]++;
        }
        printCountArray(countArr1);

        System.out.println("=====================");

        int[] countArr2 = new int[7];
        for (int i = 0; i != testTimes; i++) {
            countArr2[rand1To6()]++;
        }
        printCountArray(countArr2);

        System.out.println("=====================");

        int n = 17;
        int m = 3;
        int[] countArr3 = new int[n + 1];
        for (int i = 0; i != 2000000; i++) {
            countArr3[rand1ToN(n, m)]++;
        }
        printCountArray(countArr3);

        System.out.println("=====================");

    }
}
