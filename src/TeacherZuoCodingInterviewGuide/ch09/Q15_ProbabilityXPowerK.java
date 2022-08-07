package TeacherZuoCodingInterviewGuide.ch09;

public class Q15_ProbabilityXPowerK {
//    調整[0,x)區間上的數出現的概率
//    題目描述：
//            假設函數Math.random()等概率隨機返回一個在[0,1)範圍上的數，那麽我們知道，在[0,x)區間上的數出現的概率為x （0<x≤1）。
//    給定一個大於0的整數k，
//    並且可以使用 Math.
//
//    random()函數，請實現一個函數依然返回在[0,1)範圍上的數，但是在[0,x)
//
//    區間上的數出現的概率為x的k次方(0<x≤1)。

    public static double randXPower2() {   //計算在0——x範圍內，出現x平方次的概率
        return Math.max(Math.random(), Math.random());
    }

    public static double randXPowerK(int k) {
        if (k == 1) {
            return 0;
        }
        double res = 0;
        for (int i = k; i > 0; i--) {
            res = Math.max(res, Math.random()); // 計算k次，保證每次取到的值都在要求範圍內，則此時是x的k次方
        }
        return res;
    }

    public static void main(String[] args) {
        double range = 0.7;
        int count = 0;
        int times = 500000;
        for (int i = 0; i < times; i++) {
            if (randXPowerK(2) < range) { // 滿足所求出的值在（0， range）範圍內即可
                count++;
            }
        }
        double p = (double) count / (double) times;
        System.out.println("range [0," + range + "), probability: " + p);
    }
}
