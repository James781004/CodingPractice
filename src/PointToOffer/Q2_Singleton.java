package PointToOffer;

public class Q2_Singleton {
    private static Q2_Singleton singleton;

    private Q2_Singleton() {
    }

    private static class SingletonHolder {
        private static Q2_Singleton INSTANCE = new Q2_Singleton();
    }

    public static Q2_Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
