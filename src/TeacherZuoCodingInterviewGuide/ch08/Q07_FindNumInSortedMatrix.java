package TeacherZuoCodingInterviewGuide.ch08;

public class Q07_FindNumInSortedMatrix {
//    CD1 在行列都排好序的矩陣中找指定的數
//    描述
//    給定一個N×M的整形矩陣matrix和一個整數K, matrix的每一行和每一列都是排好序的。
//    實現一個函數，判斷K是否在matrix中
//[要求]
//    時間覆雜度為O(N+M)，額外空間覆雜度為O(1)。

    // 從右上開始找，當下比K大就左移，當下比K小就下移
    public static boolean isContains(int[][] matrix, int K) {
        int row = 0;
        int col = matrix[0].length - 1;
        while (row < matrix.length && col > -1) {
            if (matrix[row][col] == K) {
                return true;
            } else if (matrix[row][col] > K) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{0, 1, 2, 3, 4, 5, 6},// 0
                {10, 12, 13, 15, 16, 17, 18},// 1
                {23, 24, 25, 26, 27, 28, 29},// 2
                {44, 45, 46, 47, 48, 49, 50},// 3
                {65, 66, 67, 68, 69, 70, 71},// 4
                {96, 97, 98, 99, 100, 111, 122},// 5
                {166, 176, 186, 187, 190, 195, 200},// 6
                {233, 243, 321, 341, 356, 370, 380} // 7
        };
        int K = 233;
        System.out.println(isContains(matrix, K));
    }
}
