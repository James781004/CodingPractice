package TeacherZuoCodingInterviewGuide.ch09;

public class Q05_PointInTriangle {
//    CD59 判斷一個點是否在三角形內部
//    描述
//    在二維坐標系中，所有的值都是double類型，那麽一個三角形可以由3個點來代表，
//    給定3個點代表的三角形，再給定一個點(x, y)，判斷(x, y)是否在三角形中

    public static boolean isInside1(double x1, double y1, double x2, double y2,
                                    double x3, double y3, double x, double y) {
        double area1 = getArea(x1, y1, x2, y2, x, y);
        double area2 = getArea(x1, y1, x3, y3, x, y);
        double area3 = getArea(x2, y2, x3, y3, x, y);
        double allArea = getArea(x1, y1, x2, y2, x3, y3);
        return area1 + area2 + area3 <= allArea;
    }

    public static double getArea(double x1, double y1, double x2, double y2,
                                 double x3, double y3) {
        double side1Len = getSideLength(x1, y1, x2, y2);
        double side2Len = getSideLength(x1, y1, x3, y3);
        double side3Len = getSideLength(x2, y2, x3, y3);
        double p = (side1Len + side2Len + side3Len) / 2;
        return Math.sqrt((p - side1Len) * (p - side2Len) * (p - side3Len) * p);
    }

    public static double getSideLength(double x1, double y1, double x2,
                                       double y2) {
        double a = Math.abs(x1 - x2);
        double b = Math.abs(y1 - y2);
        return Math.sqrt(a * a + b * b);
    }

    // 假設，三角形其中一條邊的兩個端點為(x1,y1)和(x2,y2)，待判斷位置的點為(x,y)，
    // 通過向量(x2-x1,y2-y1)和(x-x1,y-y1)叉積的符號可以判斷點(x,y)與向量(x2-x1,y2-y1)的位置關系（大於0為順時針方向，小於0逆時針方向，等於0為共線）
    // 如果點(x,y)對於三角形任意一條邊的向量都位於同一側，則點在三角形內。
    public static boolean isInside2(double x1, double y1, double x2, double y2,
                                    double x3, double y3, double x, double y) {
        // 如果三角形的点不是逆时针输入，改变一下顺序
        if (crossProduct(x3 - x1, y3 - y1, x2 - x1, y2 - y1) >= 0) {
            double tmpx = x2;
            double tmpy = y2;
            x2 = x3;
            y2 = y3;
            x3 = tmpx;
            y3 = tmpy;
        }
        if (crossProduct(x2 - x1, y2 - y1, x - x1, y - y1) < 0) {
            return false;
        }
        if (crossProduct(x3 - x2, y3 - y2, x - x2, y - y2) < 0) {
            return false;
        }
        if (crossProduct(x1 - x3, y1 - y3, x - x3, y - y3) < 0) {
            return false;
        }
        return true;
    }

    public static double crossProduct(double x1, double y1, double x2, double y2) {
        return x1 * y2 - x2 * y1;
    }

    public static void main(String[] args) {
        double x1 = -5;
        double y1 = 0;
        double x2 = 0;
        double y2 = 8;
        double x3 = 5;
        double y3 = 0;
        double x = 0;
        double y = 5;
        System.out.println(isInside1(x1, y1, x2, y2, x3, y3, x, y));
        System.out.println(isInside2(x1, y1, x2, y2, x3, y3, x, y));

    }
}
