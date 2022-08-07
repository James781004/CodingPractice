package TeacherZuoCodingInterviewGuide.ch04;

public class Q07_LIS {
    //    最長遞增子序列
//    題目
//            給定數組arr,返回arr的最長遞增子序列
//    例如：arr = [2,1,5,3,6,4,8,9,7], 返回的最長遞增子序列爲[1,3,4,8,9].
//    如果arr 長度爲N, 請實現時間複雜度爲O(NlogN)的方法。

    public static int[] lis1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int[] dp = getdp1(arr);
        return generateLIS(arr, dp);
    }

    public static int[] getdp1(int[] arr) {
        int[] dp = new int[arr.length]; // dp[i]代表以arr[i]為結尾的最長子序列長度
        for (int i = 0; i < arr.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    // 0 ~ i-1範圍內比arr[i]小的arr[j]都可以在arr[i]前面
                    // dp[j]這邊代表先前已經計算過的結果
                    // 現在碰到arr[i]這個更大數值結尾，就會變成dp[j] + 1
                    // 比較範圍內每一個dp[j] + 1就是要求的目標結果dp[i]
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return dp;
    }

    public static int[] generateLIS(int[] arr, int[] dp) {
        int len = 0;
        int index = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] > len) {
                len = dp[i];
                index = i;
            }
        }
        int[] lis = new int[len];
        lis[--len] = arr[index];
        for (int i = index; i >= 0; i--) {
            if (arr[i] < arr[index] && dp[i] == dp[index] - 1) {
                lis[--len] = arr[i];
                index = i;
            }
        }
        return lis;
    }

    public static int[] lis2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int[] dp = getdp2(arr);
        return generateLIS(arr, dp);
    }

    public static int[] getdp2(int[] arr) {
        int[] dp = new int[arr.length]; // dp[i]代表以arr[i]為結尾的最長子序列長度
        int[] ends = new int[arr.length]; // 存放i+1長度子序列的最小結尾
        ends[0] = arr[0];
        dp[0] = 1;

        // 用來在end數組裡面目前有效區域的右邊界位置
        int right = 0;

        // 用來在end數組裡面二分搜尋的指針
        int l = 0;
        int r = 0;
        int m = 0;
        for (int i = 1; i < arr.length; i++) {
            l = 0;
            r = right;

            // 左閉右開二分搜尋，最後l會是我們要找的對象
            while (l <= r) {
                m = (l + r) / 2;
                if (arr[i] > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }

            // 更新有效區域的右邊界位置
            right = Math.max(right, l);

            // 更新有效區右邊界數值
            // ends[l]同位置每次更新，值只有可能愈來愈小
            // 因為更大的值可以出現在ends[l]後面位置形成更長的遞增子序列
            ends[l] = arr[i];

            // 記錄以arr[i]為結尾的最大長度
            dp[i] = l + 1;
        }
        return dp;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {2, 1, 5, 3, 6, 4, 8, 9, 7};
        printArray(arr);
        printArray(lis1(arr));
        printArray(lis2(arr));

    }
}
