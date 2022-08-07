package 劍指offer.Math;

public class Q43_NumberOf1Between1AndN_Solution {

    // 将所有数字转换成字符串，再遍历每个字符串的每一位。当n位数较大时，时间复杂度会比较高
    public int NumberOf1Between1AndN_Solution(int n) {
        int count = 0;
        for (int i = 0; i <= n; i++) {
            String str = String.valueOf(i);
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == '1')
                    count++;
            }
        }
        return count;
    }

    public int NumberOf1Between1AndN_Solution2(int n) {
        //count表示1出现的总次数
        int count = 0;
        //从1到n循环n次，对每一个数求它包含多少个1
        for (int i = 1; i <= n; i++) {
            int temp = i;
            //求这个数包含多少个1
            while (temp != 0) {
                if (temp % 10 == 1) {
                    count++;
                }
                temp = temp / 10;
            }
        }
        return count;
    }

    // 统计某个位置上 1出现的次数。如34，1在十位上出现的次数是10次
    //（10到19），1在个位上出现的次数是4次（1，11，21，31），因此34中1出现了14次。
    //
    // 对于整数n，将这个整数分为三部分：当前位数字cur，更高位数字high，更低位数字low，
    // 如：对于n=21034，当位数是十位时，cur=3，high=210，low=4。
    // 我们从个位到最高位 依次计算每个位置出现1的次数：

    // 1）当前位的数字等于0时，例如n=21034，在百位上的数字cur=0，
    // 百位上是1的情况有：00100-00199，01100-01199，……，20100-20199。一共有21*100种情况，即high*100;

    // 2）当前位的数字等于1时，例如n=21034，在千位上的数字cur=1，
    // 千位上是1的情况有：01000-01999，11000-11999，21000-21034。一共有2*1000+（34+1）种情况，即high*1000+(low+1)。

    // 3）当前位的数字大于1时，例如n=21034，在十位上的数字cur=3，
    // 十位上是1的情况有：00010-00019，……，21010-21019。一共有(210+1)*10种情况，即(high+1)*10。
    public int NumberOf1Between1AndN_Solution_Math(int n) {
        int count = 0;
        for (int i = 1; i <= n; i *= 10) {  //i代表位数
            int high = n / (i * 10); //更高位数字
            int low = (n % i);  //更低位数字
            int cur = (n / i) % 10;  //当前位数字
            if (cur == 0) {
                count += high * i;
            } else if (cur == 1) {
                count += high * i + (low + 1);
            } else {
                count += (high + 1) * i;
            }
        }
        return count;
    }
}
