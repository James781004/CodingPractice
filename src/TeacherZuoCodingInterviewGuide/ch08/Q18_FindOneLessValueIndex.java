package TeacherZuoCodingInterviewGuide.ch08;

public class Q18_FindOneLessValueIndex {
//    CD28 在數組中找到一個局部最小的位置
//    描述
//    定義局部最小的概念。arr長度為1時，arr[0]是局部最小。
//    arr的長度為N(N>1)時，如果arr[0]<arr[1]，那麽arr[0]是局部最小；
//    如果arr[N-1]<arr[N-2]，那麽arr[N-1]是局部最小；
//    如果0<i<N-1，既有arr[i]<arr[i-1]，又有arr[i]<arr[i+1]，那麽arr[i]是局部最小。
//    給定無序數組arr，已知arr中任意兩個相鄰的數不相等。寫一個函數，只需返回arr中任意一個局部最小出現的位置即可
//[要求]
//    時間覆雜度為O(logn)，空間覆雜度為O(1)

    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length == 0) return -1;
        if (arr.length == 1 || arr[0] < arr[1]) return 0;
        if (arr[arr.length - 1] < arr[arr.length - 2]) return arr.length - 1;
        int left = 1;
        int right = arr.length - 2;
        int mid = 0;
        while (left != right) {
            mid = left + (right - left) / 2;
            if (arr[mid] > arr[mid - 1]) { // arr[left...mid-1]區間存在局部最小
                right = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) { // arr[mid+1...right]區間存在局部最小
                left = mid + 1;
            } else {
                return mid; // 上述情況不成立時，mid自己就是局部最小
            }
        }

        return left;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {6, 5, 3, 4, 6, 7, 8};
        printArray(arr);
        int index = getLessIndex(arr);
        System.out.println("index: " + index + ", value: " + arr[index]);

    }
}
