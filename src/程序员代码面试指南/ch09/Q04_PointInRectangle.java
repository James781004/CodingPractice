package 程序员代码面试指南.ch09;

public class Q04_PointInRectangle {
//    CD58 判斷一個點是否在矩形內部
//    描述
//    在二維坐標系中，所有的值是double類型，那麽一個矩形可以由四個點來代表，
//    (x1, y1)為最左的點，(x2, y2)為最上的點，
//    (x3, y3)為最下的點，(x4, y4)為最右的點。
//    給定4個點代表的矩形，再給定一個點(x, y)，判斷(x, y)是否在矩形中

    public static boolean isInside(double x1, double y1, double x4, double y4,
                                   double x, double y) {
        if (x <= x1) {
            return false;
        }
        if (x >= x4) {
            return false;
        }
        if (y >= y1) {
            return false;
        }
        if (y <= y4) {
            return false;
        }
        return true;
    }


    // 座標變換
    public static boolean isInside(double x1, double y1, double x2, double y2,
                                   double x3, double y3, double x4, double y4, double x, double y) {
        if (y1 == y2) {
            return isInside(x1, y1, x4, y4, x, y);
        }
        double l = Math.abs(y4 - y3);
        double k = Math.abs(x4 - x3);
        double s = Math.sqrt(k * k + l * l);
        double sin = l / s;
        double cos = k / s;
        double x1R = cos * x1 + sin * y1;
        double y1R = -x1 * sin + y1 * cos;
        double x4R = cos * x4 + sin * y4;
        double y4R = -x4 * sin + y4 * cos;
        double xR = cos * x + sin * y;
        double yR = -x * sin + y * cos;
        return isInside(x1R, y1R, x4R, y4R, xR, yR);
    }

    public static void main(String[] args) {
        // (x1,y1),(x2,y2),(x3,y3),(x4,y4) stand for a Rectangle.
        double x1 = 0;
        double y1 = 3;// (x1,y1) should be the most left
        double x2 = 3;
        double y2 = 7;// (x2,y2) should be the most top.
        double x3 = 4;
        double y3 = 0;// (x3,y3) should be the most below.
        double x4 = 7;
        double y4 = 4;// (x4,y4) should be the most right.

        double x = 4;
        double y = 3;
        System.out.print(isInside(x1, y1, x2, y2, x3, y3, x4, y4, x, y));

    }
}
