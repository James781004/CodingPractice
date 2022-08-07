package TeacherZuoCodingInterviewGuide.ch09;

import java.util.Arrays;

public class Q30_FindKthMinNumber {
//    CD82 在兩個排序數組中找到第k小的數
//    描述
//    給定兩個有序數組arr1和arr2，再給定一個整數K，返回所有數中第K小的數。
//            [要求]
//    如果arr1的長度為N，arr2的長度為M，時間覆雜度請達到O(log(minN,M))，額外空間覆雜度O(1)。


    // 可以利用雙指針歸併的方式求答案，走k步就可以找到
//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String[] params = br.readLine().split(" ");
//        int n = Integer.parseInt(params[0]), m = Integer.parseInt(params[1]), k = Integer.parseInt(params[2]);
//        params = br.readLine().split(" ");
//        int[] nums1 = new int[n];
//        for(int i = 0; i < n; i++) nums1[i] = Integer.parseInt(params[i]);
//        params = br.readLine().split(" ");
//        int[] nums2 = new int[m];
//        for(int i = 0; i < m; i++) nums2[i] = Integer.parseInt(params[i]);
//        // 歸併
//        int p1 = 0, p2 = 0, p = 0;
//        while(p1 < n && p2 < m){
//            if(nums1[p1] < nums2[p2]){
//                p1 ++;
//                p ++;
//                if(p == k){
//                    System.out.println(nums1[p1 - 1]);
//                    break;
//                }
//            }else{
//                p2 ++;
//                p ++;
//                if(p == k){
//                    System.out.println(nums2[p2 - 1]);
//                    break;
//                }
//            }
//        }
//    }


    // 利用上中位數來求取答案
    // 整體思維是，把兩數組無效區的數排除掉之後，讓兩數組"等長"
    // 這樣可以讓kth移動到整體中位，剩下的數取上中位數即為答案
    public static int findKthNum(int[] arr1, int[] arr2, int kth) {
        if (arr1 == null || arr2 == null) {
            throw new RuntimeException("Your arr is invalid!");
        }
        if (kth < 1 || kth > arr1.length + arr2.length) {
            throw new RuntimeException("K is invalid!");
        }
        int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
        int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
        int l = longs.length;
        int s = shorts.length;

        // 1. 如果k小於shorts數組長度，那麼兩數組各取前k個求上中位數即可
        if (kth <= s) {
            return getUpMedian(shorts, 0, kth - 1, longs, 0, kth - 1);
        }

        // 2. 如果k大於longs數組長度
        if (kth > l) {
            if (shorts[kth - l - 1] >= longs[l - 1]) {
                return shorts[kth - l - 1];
            }
            if (longs[kth - s - 1] >= shorts[s - 1]) {
                return longs[kth - s - 1];
            }

            // 如果求37個數中的第33個數，假設長數組27個數，短數組10個數
            // 在短數組{1...10}中，第6(33-27)個數可能成為第33個數(6+27)，而5頂多成為第32個數(因為壓過了27+4個數)，5之前就更不可能，必須排除掉
            // 在長數組{1...27}中，第23(33-10)個數可能成為第33個數(23+10)，但是{1...22}就不可能，必須排除掉
            // 如果短數組{6}或者長數組{23}滿足"大於另一數組所有數"的條件，即可直接返回
            // 否則短數組{6}或者長數組{23}也不符合條件，也必須排除掉
            // 最後應該在短數組{7...10}和長數組{24...27}這個範圍求上中位數
            // 總之，如果k大於longs數組長度，排除無效元素的時候，短數組排除前k-l個數，長數組排除前k-s個數，兩數組即可等長
            return getUpMedian(shorts, kth - l, s - 1, longs, kth - s, l - 1);
        }

        // 3. lenS < k <= lenL的情況
        // 假設求第17個數，10<17<=27
        // 在短數組{1...10}中，每個都可能是答案
        // 在長數組{1...27}中，第6個數最好情況就是整體第16個數(因為壓過了10+5個數)，不可能成為答案，排除{1...6}
        // 在長數組{1...27}中，第18個數最好情況就是整體第18個數，不可能成為答案，排除{18...27}
        // 如果長數組{7}(17-10)滿足"大於短數組所有數"條件，就有可能成為第17個數(因為壓過了10+6個數)，否則也要排除
        // 最後應該在短數組{1...10}和長數組{8...17}這個範圍求上中位數
        // 總之，lenS < k <= lenL的情況，排除無效元素的時候，長數組排除前k-s個數，兩數組即可等長
        if (longs[kth - s - 1] >= shorts[s - 1]) {
            return longs[kth - s - 1];
        }

        return getUpMedian(shorts, 0, s - 1, longs, kth - s, kth - 1);
    }

    public static int getUpMedian(int[] a1, int s1, int e1, int[] a2, int s2, int e2) {
        int mid1 = 0;
        int mid2 = 0;
        int offset = 0;
        while (s1 < e1) {
            mid1 = (s1 + e1) / 2;
            mid2 = (s2 + e2) / 2;
            offset = ((e1 - s1 + 1) & 1) ^ 1;
            if (a1[mid1] > a2[mid2]) {
                e1 = mid1;
                s2 = mid2 + offset;
            } else if (a1[mid1] < a2[mid2]) {
                s1 = mid1 + offset;
                e2 = mid2;
            } else {
                return a1[mid1];
            }
        }
        return Math.min(a1[s1], a2[s2]);
    }

    // For test, this method is inefficient but absolutely right
    public static int[] getSortedAllArray(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null) {
            throw new RuntimeException("Your arr is invalid!");
        }
        int[] arrAll = new int[arr1.length + arr2.length];
        int index = 0;
        for (int i = 0; i != arr1.length; i++) {
            arrAll[index++] = arr1[i];
        }
        for (int i = 0; i != arr2.length; i++) {
            arrAll[index++] = arr2[i];
        }
        Arrays.sort(arrAll);
        return arrAll;
    }

    public static int[] generateSortedArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != len; i++) {
            res[i] = (int) (Math.random() * (maxValue + 1));
        }
        Arrays.sort(res);
        return res;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len1 = 10;
        int len2 = 23;
        int maxValue1 = 20;
        int maxValue2 = 100;
        int[] arr1 = generateSortedArray(len1, maxValue1);
        int[] arr2 = generateSortedArray(len2, maxValue2);
        printArray(arr1);
        printArray(arr2);
        int[] sortedAll = getSortedAllArray(arr1, arr2);
        printArray(sortedAll);
        int kth = 17;
        System.out.println(findKthNum(arr1, arr2, kth));
        System.out.println(sortedAll[kth - 1]);

    }
}
