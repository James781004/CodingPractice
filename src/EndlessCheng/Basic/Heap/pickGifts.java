package EndlessCheng.Basic.Heap;

public class pickGifts {


    // https://leetcode.cn/problems/take-gifts-from-the-richest-pile/solutions/2501655/yuan-di-dui-hua-o1-kong-jian-fu-ti-dan-p-fzdh/
    public long pickGifts(int[] gifts, int k) {
        heapify(gifts); // 原地堆化（最大堆）
        while (k-- > 0 && gifts[0] > 1) {
            gifts[0] = (int) Math.sqrt(gifts[0]); // 直接修改堆頂
            sink(gifts, 0); // 堆化（只需要把 gifts[0] 下沉）
        }

        long ans = 0;
        for (int x : gifts) {
            ans += x;
        }
        return ans;
    }

    // 原地堆化（最大堆）
    // 堆化可以保證 h[0] 是堆頂元素，且 h[i] >= max(h[2*i+1], h[2*i+2])
    private void heapify(int[] h) {
        // 倒著遍歷，從而保證 i 的左右子樹一定是堆，那麼 sink(h, i) 就可以把左右子樹合並成一個堆
        // 下標 >= h.length / 2 的元素是二叉樹的葉子，無需下沉
        for (int i = h.length / 2 - 1; i >= 0; i--) {
            sink(h, i);
        }
    }

    // 把 h[i] 不斷下沉，每次找左右兒子中最大的交換，直到 i 的左右兒子都 <= h[i] 時停止
    private void sink(int[] h, int i) {
        int n = h.length;
        while (2 * i + 1 < n) {
            int j = 2 * i + 1; // i 的左兒子
            if (j + 1 < n && h[j + 1] > h[j]) { // i 的右兒子比 i 的左兒子大
                j++;
            }
            if (h[j] <= h[i]) { // 說明 i 的左右兒子都 <= h[i]，停止下沉
                break;
            }
            swap(h, i, j); // 下沉
            i = j;
        }
    }

    // 交換 h[i] 和 h[j]
    private void swap(int[] h, int i, int j) {
        int tmp = h[i];
        h[i] = h[j];
        h[j] = tmp;
    }


}
