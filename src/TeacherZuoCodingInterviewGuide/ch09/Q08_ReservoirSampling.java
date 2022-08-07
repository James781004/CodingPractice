package TeacherZuoCodingInterviewGuide.ch09;

public class Q08_ReservoirSampling {
//    蓄水池采樣算法 Reservoir Sampling
//    Reservoir Sampling是一系列的隨機算法。算法過程：
//    假設數據序列的規模為 n，需要采樣的數量的為 k。
//    首先構建一個可容納 k 個元素的數組，將序列的前 k 個元素放入數組中。
//    然後從第 k+1 個元素開始，以 k/n 的概率來決定該元素是否被替換到數組中（數組中的元素被替換的概率是相同的）。
//    當遍歷完所有元素之後，數組中剩下的元素即為所需采取的樣本。

    public static int rand(int max) {
        return (int) (Math.random() * max) + 1;
    }

    public static int[] getKNumsRand(int k, int max) {
        if (max < 1 || k < 1) {
            return null;
        }
        int[] res = new int[Math.min(k, max)];
        for (int i = 0; i != res.length; i++) {
            res[i] = i + 1; // 處理1~k號球時直接放進res
        }
        for (int i = k + 1; i < max + 1; i++) {
            if (rand(i) <= k) { // k+1開始調用rand方法，隨機把res裡面的球替換掉
                res[rand(k) - 1] = i;
            }
        }
        return res;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] res = getKNumsRand(10, 10000);
        printArray(res);
    }
}
