package TeacherZuoCodingInterviewGuide.ch08;

public class Q25_SmallestMissNum {
//    CD39 數組中未出現的最小正整數
//    描述
//    給定一個無序數組arr，找到數組中未出現的最小正整數
//    例如arr = [-1, 2, 3, 4]。返回1
//    arr = [1, 2, 3, 4]。返回5
//[要求]
//    時間覆雜度為O(n)，空間覆雜度為O(1)

    public static int missNum(int[] arr) {
        int left = 0;
        int right = arr.length;
        while (left < right) {
            if (arr[left] == left + 1) { // 1.表示正常情況
                left++;
            } else if (arr[left] <= left || arr[left] > right || arr[arr[left] - 1] == arr[left]) {
                // 2.表示出現不合法情況
                // arr[left] <= left 表示在區間[left+1...right]上的數少了一個，因為當前值屬於[0...left]範圍的值
                // arr[left] > right 表示當前值大於整個區間範圍，即不在[left+1...right]的區間範圍
                // arr[arr[left] - 1] == arr[left]表示當前值出現了重復，則[left+1...right]範圍應該出現的數少了一個
                // 上述狀況都會導致原本應該出現在[left+1...right]區間的數少一個
                // 此時用最右邊的數替換掉當前不合法的數，並且縮減右邊界(因為區間上的數少了一個)
                // 也就是變成[left+1...right-1]，然後繼續驗證
                arr[left] = arr[--right];
            } else {
                // 3.表示當前值是一個合法的值，但是沒有在理想的位置上，需要進行交換處理
                swap(arr, left, arr[left] - 1);
            }
        }
        return left + 1;
    }

    public static void swap(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = {-1, 0, 2, 1, 3, 5};
        System.out.println(missNum(arr));

    }
}
