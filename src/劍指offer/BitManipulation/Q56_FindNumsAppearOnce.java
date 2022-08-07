package 劍指offer.BitManipulation;

import java.util.HashMap;

public class Q56_FindNumsAppearOnce {

    public int[] FindNumsAppearOnce(int[] array) {

        // 先将全部数进行异或运算，得出最终结果
        int tmp = 0;
        for (int num : array) {
            tmp ^= num;
        }

        // 找到那个可以充当分组去进行与运算的数
        // 从最低位开始找起
        int mask = 1;
        while ((tmp & mask) == 0) {
            mask <<= 1;
        }

        // 进行分组，分成两组，转换为两组 求出现一次的数字 去求
        int a = 0;
        int b = 0;
        for (int num : array) {
            if ((num & mask) == 0) {
                a ^= num;
            } else {
                b ^= num;
            }
        }
        // 因为题目要求小的数放前面，所以这一做个判断
        if (a > b) {
            int c = a;
            a = b;
            b = c;
        }
        return new int[]{a, b};
    }


    // 1、创建一个哈希表
    // 2、当数组元素没有在哈希表中成为key的时候，put进哈希表，当已存在的时候，则remove掉。
    // 3、最后哈希表中剩下的key就是只出现一次的数字
    // 4、遍历key然后返回结果
    public int[] FindNumsAppearOnceMap(int[] array) {
        // write code here
        // 用于返回结果
        int[] res = new int[2];
        // 创建一个哈希表
        HashMap<Integer, Object> set = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            // 如果已经被当作key了，那就直接remove掉
            if (set.containsKey(array[i])) {
                set.remove(array[i], null);
            } else {
                // 否则就添加进去
                set.put(array[i], null);
            }
        }
        int i = 0;
        // 最后拿出来放进返回结果的数组中进行返回
        for (Integer num : set.keySet()) {
            res[i++] = num;
        }
        return res;
    }

}
