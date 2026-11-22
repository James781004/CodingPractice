package EndlessCheng.GenreMenu.DataStructure.PrefixSum.Basic;

import java.util.ArrayList;
import java.util.List;

public class ProductOfNumbers {

    // https://leetcode.cn/problems/product-of-the-last-k-numbers/solutions/4055919/qian-zhui-ji-zhu-yi-0pythonjavacgo-by-en-4012/
    private final List<Integer> p = new ArrayList<>();

    public ProductOfNumbers() {
        p.add(1);
    }

    public void add(int num) {
        if (num != 0) {
            p.add(p.getLast() * num);
        } else {
            p.clear();
            p.add(1);
        }
    }

    public int getProduct(int k) {
        int n = p.size();
        if (n <= k) {
            return 0;
        }
        return p.getLast() / p.get(n - 1 - k);
    }

}
