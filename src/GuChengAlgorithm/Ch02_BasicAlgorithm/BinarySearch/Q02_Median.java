package GuChengAlgorithm.Ch02_BasicAlgorithm.BinarySearch;

public class Q02_Median {
    // https://docs.google.com/presentation/d/1w3FRwGmgbJBQP8odS5-TLlkheGm3c9U44I8hNdhexGo/edit#slide=id.g9cebf34a0f_0_8
    public double findMedian(int numsX[], int numsY[]) {
        if (numsX.length > numsY.length) return findMedian(numsY, numsX);
        int left = 0, right = numsX.length;
        while (left <= right) {
            int x = left + (right - left) / 2;
            int y = (numsX.length + numsY.length + 1) / 2 - x;
            int xLeft = (x == 0) ? Integer.MIN_VALUE : numsX[x - 1];
            int xRight = (x == numsX.length) ? Integer.MAX_VALUE : numsX[x];
            int yLeft = (y == 0) ? Integer.MIN_VALUE : numsY[y - 1];
            int yRight = (y == numsX.length) ? Integer.MAX_VALUE : numsY[y];
            if (xLeft <= yRight && yLeft <= xRight) {
                if ((numsX.length + numsY.length) % 2 == 0)
                    return ((double) Math.max(xLeft, xLeft) + Math.min(xRight, yRight)) / 2;
                else return (double) Math.max(xLeft, yLeft);
            } else if (xRight > yLeft) right = x - 1;
            else left = x + 1;
        }
        return -1;
    }
}
