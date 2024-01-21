package Hackerrank.Ch10_Miscellaneous;

public class FlippingBits {
    public static long flippingBits(long n) {
        long maxUnsignedInt = (1L << 32) - 1;
        return n ^ maxUnsignedInt;
    }
}
