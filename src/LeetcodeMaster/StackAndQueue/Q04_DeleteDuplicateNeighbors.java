package LeetcodeMaster.StackAndQueue;

import java.util.ArrayDeque;

public class Q04_DeleteDuplicateNeighbors {
//    1047. 刪除字符串中的所有相鄰重覆項
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/1047.%E5%88%A0%E9%99%A4%E5%AD%97%E7%AC%A6%E4%B8%B2%E4%B8%AD%E7%9A%84%E6%89%80%E6%9C%89%E7%9B%B8%E9%82%BB%E9%87%8D%E5%A4%8D%E9%A1%B9.md
//
//    給出由小寫字母組成的字符串 S，重覆項刪除操作會選擇兩個相鄰且相同的字母，並刪除它們。
//
//    在 S 上反覆執行重覆項刪除操作，直到無法繼續刪除。
//
//    在完成所有重覆項刪除操作後返回最終的字符串。答案保證唯一。
//
//    示例：
//
//    輸入："abbaca"
//    輸出："ca"
//    解釋：例如，在 "abbaca" 中，我們可以刪除 "bb" 由於兩字母相鄰且相同，這是此時唯一可以執行刪除操作的重覆項。之後我們得到字符串 "aaca"，
//    其中又只有 "aa" 可以執行重覆項刪除操作，所以最後的字符串為 "ca"。
//    提示：
//
//            1 <= S.length <= 20000
//    S 僅由小寫英文字母組成。

    public String removeDuplicatesByStack(String S) {
        //ArrayDeque會比LinkedList在除了刪除元素這一點外會快一點
        //參考：https://stackoverflow.com/questions/6163166/why-is-arraydeque-better-than-linkedlist
        ArrayDeque<Character> deque = new ArrayDeque<>();
        char c;
        for (int i = 0; i < S.length(); i++) {
            c = S.charAt(i);
            if (deque.isEmpty() || deque.peek() != c) {
                deque.push(c);
            } else {
                deque.pop();
            }
        }

        StringBuilder sb = new StringBuilder();
        //剩余的元素即為不重覆的元素
        while (!deque.isEmpty()) {
            sb.append(deque.pop());
        }
        return sb.toString();
    }


    // 拿字符串直接作為棧，省去了棧還要轉為字符串的操作。
    public String removeDuplicates(String s) {
        StringBuilder sb = new StringBuilder(); // 將 sb 當做棧
        int top = -1; // top 為 sb 的尾部下標位置，記錄下來就不用每次調用sb.length()-1
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);

            // 當 top > 0,即棧中有字符時，當前字符如果和棧中字符相等，彈出棧頂字符，同時尾部下標位置top--
            if (top > 0 && sb.charAt(top) == c) {
                sb.deleteCharAt(top);
                top--;
            } else { // 否則，將該字符入棧，同時top++
                sb.append(c);
                top++;
            }
        }

        return sb.toString();
    }
}
