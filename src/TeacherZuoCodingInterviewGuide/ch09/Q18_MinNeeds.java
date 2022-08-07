package TeacherZuoCodingInterviewGuide.ch09;

public class Q18_MinNeeds {
//    CD70 累加出整個範圍所有的數最少還需要幾個數
//    描述
//    給定一個正數數組arr 和一個正數range，可以選擇arr 中的任意個數字加起來的和為sum。
//    返回最小需要往arr 中添加幾個數，使得sum 可以取到1∼range範圍上的每一個數。
//    給出的數組不保證有序！
//    参考：https://blog.csdn.net/MIC10086/article/details/111185406


    // 假設 arr={3,17,21,78}, range=67：
    //
    // 設置變量 touch ，表示當前已經搞定了 1~touch 範圍上的數。初始時令 touch=0 ，表示哪個範圍也不搞定；
    // 遍歷到 arr[0]=3 ，當前目標為先搞定 1~2 範圍上所有數字。目前 touch=0 ，所以缺 1 ，有了 1 之後缺 2 ，
    // 有了2 之後可以搞定 1~3 範圍上所有的數字，當前擁有數字 3 ，所以可以搞定 1~6 範圍上的所有數字，即 touch=6 ；
    // 遍歷到 arr[1]=17 ，當前目標為搞定 1~16 範圍上所有的數。目前 touch=6 ，所以缺 7 ，有了 7 之後缺 14 ，
    // 有了 14 之後可以搞定 1~27 範圍上所有的數字，當前擁有數字 17 ，所以可以搞定 1~44 範圍，即 touch=44 ；
    // 遍歷到 arr[2]=21 ，當前目標為搞定 1~20 範圍上的所有數字，
    // 步驟 3 已經完成目標，當前擁有數字 21 ，所以可以搞定 1~65 範圍，即 touch=65；
    // 遍歷到 arr[3]=78，當前目標為先搞定 1~77 範圍上的所有數。
    // 目前 touch=65 ，所以缺少 66 ，有了 66 之後可以搞定 1~131 範圍，此時已經滿足了 1~67 的總目標。
    // 在遍歷 arr 的過程中，任何時候只要 touch>=range ，直接返回結果。
    // 若遍歷完 arr 後，touch依然沒有達到 range ，剩下的就是搞定 1~range 了。
    public static int minNeeds(int[] arr, int range) {
        int needs = 0;
        long touch = 0;
        for (int i = 0; i < arr.length; i++) {
            // 若不考慮數組 arr ，我們看看累加得到 1~range 範圍上所有的數最少需要幾個數。
            //
            // 首先缺少 1 ，得到 1 之後可以得到 1~1 範圍上所有的數
            // 然後缺少 2 ，得到 2 之後可以得到 1~3 範圍上所有的數
            // 然後缺少 4 ，得到 4 之後可以得到 1~7 範圍上所有的數
            // …
            //
            // 也就是說，如果搞定了 1~touch 範圍上所有的數，下一個缺少的數字就是 touch + 1，有了 touch + 1 之後，
            // 就可以搞定 1~touch+touch+1 範圍上的所有數字，隨著 touch 的擴大，最終會達到或超過 range ，這樣就知道最少需要幾個數字了。
            while (arr[i] > touch + 1) {
                touch += touch + 1; // 不斷擴充touch直到比arr[i]大為止
                needs++; // 不斷擴充touch的過程每次增加一個touch + 1
                if (touch >= range) return needs;
            }
            touch += arr[i]; // 擴充touch加上arr[i]，現在範圍就是1,2,3...touch + arr[i]
            if (touch >= range) return needs;
        }
        while (range >= touch + 1) {
            touch += touch + 1;
            needs++;
        }
        return needs;
    }

    public static void main(String[] args) {
        int[] test = {1, 2, 31, 33};
        int n = 2147483647;
        System.out.println(minNeeds(test, n));
    }
}
