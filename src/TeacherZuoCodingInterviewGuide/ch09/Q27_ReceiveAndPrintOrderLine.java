package TeacherZuoCodingInterviewGuide.ch09;

import java.util.HashMap;

public class Q27_ReceiveAndPrintOrderLine {
//    CD79 一種消息接收並打印的結構設計
//    描述
//    已知一個消息流會不斷地吐出整數1∼N，但不一定按照順序吐出。
//    如果上次打印的數為i，那麽當i+1出現時，請打印i+1及其之後接收過的並且連續的所有數，
//    直到1∼N全部接收並打印完，請設計這種接收並打印的結構
//[要求]
//    消息流最終會吐出全部的1∼N，當然最終也會打印完所有的1∼N，要求接收和打印1∼N的整個過程，時間覆雜度為O(N)。

    public static class Node {
        public int num;
        public Node next; // 單鏈表結構

        public Node(int num) {
            this.num = num;
        }
    }

    public static class MessageBox {
        private HashMap<Integer, Node> headMap;
        private HashMap<Integer, Node> tailMap;
        private int lastPrint;

        public MessageBox() {
            headMap = new HashMap<>();
            tailMap = new HashMap<>();
            lastPrint = 0;
        }

        public void receive(int num) {
            if (num < 1) return;
            Node cur = new Node(num);
            headMap.put(num, cur);
            tailMap.put(num, cur);

            // 預設是升序排列，所以要確認num之前是否已存在num - 1結尾的區間
            // 結尾關係，如果num之前已經有以num - 1結尾的區間，就與cur合併
            if (tailMap.containsKey(num - 1)) {
                tailMap.get(num - 1).next = cur;
                tailMap.remove(num - 1); // 區間新結尾是cur，移除舊的num - 1結尾
                headMap.remove(num); // 沿用num - 1存在區間的頭
            }

            // 預設是升序排列，所以要確認num之後是否已存在num + 1開頭的區間
            // 開頭關係，如果num之後已經有以num + 1開頭的區間，就與cur合併
            if (headMap.containsKey(num + 1)) {
                cur.next = headMap.get(num + 1);
                tailMap.remove(num); // 沿用num + 1存在區間的結尾
                headMap.remove(num + 1); // 區間新開頭是cur，移除舊的num + 1開頭
            }

            // lastPrint表示之前已經打印過的最末尾，所以要從lastPrint之後的lastPrint + 1查看
            if (headMap.containsKey(lastPrint + 1)) {
                print();
            }
        }

        private void print() {
            Node node = headMap.get(++lastPrint); // 從lastPrint下一位開始印
            headMap.remove(lastPrint);
            while (node != null) {
                System.out.println(node.num + " ");
                node = node.next;
                lastPrint++;
            }
            tailMap.remove(--lastPrint); // while結束後lastPrint位置會在連續區間的下一位，所以要減回1操作
            System.out.println();
        }

    }


    public static void main(String[] args) {
        // MessageBox only receive 1~N
        MessageBox box = new MessageBox();

        box.receive(2); // - 2
        box.receive(1); // 1 2 -> print, trigger is 1

        box.receive(4); // - 4
        box.receive(5); // - 4 5
        box.receive(7); // - 4 5 - 7
        box.receive(8); // - 4 5 - 7 8
        box.receive(6); // - 4 5 6 7 8
        box.receive(3); // 3 4 5 6 7 8 -> print, trigger is 3

        box.receive(9); // 9 -> print, trigger is 9

        box.receive(10); // 10 -> print, trigger is 10

        box.receive(12); // - 12
        box.receive(13); // - 12 13
        box.receive(11); // 11 12 13 -> print, trigger is 11

    }
}
