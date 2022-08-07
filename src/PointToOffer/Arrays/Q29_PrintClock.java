package PointToOffer.Arrays;

import java.util.ArrayList;
import java.util.List;

public class Q29_PrintClock {
    public List<Integer> printMatrix(int[][] matrix) {
        List<Integer> ret = new ArrayList<>();
        int rowStart = 0, rowEnd = matrix.length - 1, colStart = 0, colEnd = matrix[0].length - 1;
        while (rowStart <= rowEnd && colStart <= colEnd) {
            // up
            for (int i = colStart; i <= colEnd; i++) {
                ret.add(matrix[rowStart][i]);
            }

            // right
            for (int i = rowStart + 1; i <= rowEnd; i++) {
                ret.add(matrix[i][colEnd]);
            }

            if (rowStart != rowEnd) {
                // down
                for (int i = colEnd - 1; i >= colStart; i--) {
                    ret.add(matrix[rowEnd][i]);
                }
            }

            if (colStart != colEnd) {
                // left
                for (int i = rowEnd - 1; i >= rowStart; i--) {
                    ret.add(matrix[i][colStart]);
                }
            }
            rowStart++;
            rowEnd--;
            colStart++;
            colEnd--;
        }

        return ret;
    }

}
