package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.List;

public class Q07_FixIP {
//    93.復原IP地址
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0093.%E5%A4%8D%E5%8E%9FIP%E5%9C%B0%E5%9D%80.md
//
//    給定一個只包含數字的字符串，復原它並返回所有可能的 IP 地址格式。
//
//    有效的 IP 地址 正好由四個整數（每個整數位於 0 到 255 之間組成，且不能含有前導 0），整數之間用 '.' 分隔。
//
//    例如："0.1.2.201" 和 "192.168.1.1" 是 有效的 IP 地址，但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 無效的 IP 地址。
//
//    示例 1：
//
//    輸入：s = "25525511135"
//    輸出：["255.255.11.135","255.255.111.35"]
//    示例 2：
//
//    輸入：s = "0000"
//    輸出：["0.0.0.0"]
//    示例 3：
//
//    輸入：s = "1111"
//    輸出：["1.1.1.1"]
//    示例 4：
//
//    輸入：s = "010010"
//    輸出：["0.10.0.10","0.100.1.0"]
//    示例 5：
//
//    輸入：s = "101023"
//    輸出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
//    提示：
//    0 <= s.length <= 3000
//    s 僅由數字組成


    List<String> result = new ArrayList<>();

    public List<String> restoreIpAddresses(String s) {
        if (s.length() > 12) return result; // 算是剪枝了
        process(s, 0, 0);
        return result;
    }

    // startIndex: 搜索的起始位置， pointNum: 添加分隔點的數量
    private void process(String s, int startIndex, int pointNum) {
        if (pointNum == 3) { // 分隔點數量為3時，切割結束
            // 判斷第四段⼦字符串是否合法，如果合法就放進result中
            if (isValid(s, startIndex, s.length() - 1)) {
                result.add(s);
            }
            return;
        }

        for (int i = startIndex; i < s.length(); i++) {
            if (isValid(s, startIndex, i)) {
                s = s.substring(0, i + 1) + "." + s.substring(i + 1); // 在str的後⾯插⼊⼀個分隔點
                pointNum++;
                process(s, i + 2, pointNum); // 插⼊分隔點之後下⼀個⼦串的起始位置為i+2
                pointNum--;
                s = s.substring(0, i + 1) + s.substring(i + 2);// 回溯刪掉分隔點
            } else {
                break;
            }
        }
    }

    // 判斷字符串s在左閉右閉區間[start, end]所組成的數字是否合法
    private boolean isValid(String s, int start, int end) {
        if (start > end) {
            return false;
        }
        if (s.charAt(start) == '0' && start != end) { // 0開頭的數字不合法
            return false;
        }
        int num = 0;
        for (int i = start; i <= end; i++) {
            if (s.charAt(i) > '9' || s.charAt(i) < '0') { // 遇到⾮數字字符不合法
                return false;
            }
            num = num * 10 + (s.charAt(i) - '0');
            if (num > 255) { // 如果⼤於255了不合法
                return false;
            }
        }
        return true;
    }


    // 方法二：比上面的方法時間覆雜度低，更好地剪枝，優化時間覆雜度
    public List<String> restoreIpAddresses2(String s) {
        restoreIpAddressesHandler(s, 0, 0);
        return result;
    }

    StringBuilder stringBuilder = new StringBuilder();

    // number表示stringbuilder中ip段的數量
    public void restoreIpAddressesHandler(String s, int start, int number) {
        // 如果start等於s的長度並且ip段的數量是4，則加入結果集，並返回
        if (start == s.length() && number == 4) {
            result.add(stringBuilder.toString());
            return;
        }

        // 如果start等於s的長度但是ip段的數量不為4，或者ip段的數量為4但是start小於s的長度，則直接返回
        if (start == s.length() || number == 4) {
            return;
        }

        // 剪枝：ip段的長度最大是3，並且ip段處於[0,255]
        for (int i = start; i < s.length() && i - start < 3 && Integer.parseInt(s.substring(start, i + 1)) >= 0
                && Integer.parseInt(s.substring(start, i + 1)) <= 255; i++) {
            // 如果ip段的長度大於1，並且第一位為0的話，continue
            if (i + 1 - start > 1 && s.charAt(start) - '0' == 0) {
                continue;
            }
            stringBuilder.append(s.substring(start, i + 1));
            // 當stringBuilder里的網段數量小於3時，才會加點；如果等於3，說明已經有3段了，最後一段不需要再加點
            if (number < 3) {
                stringBuilder.append(".");
            }
            number++;
            restoreIpAddressesHandler(s, i + 1, number);
            number--;
            // 刪除當前stringBuilder最後一個網段，注意考慮點的數量的問題
            stringBuilder.delete(start + number, i + number + 2);
        }
    }
}
