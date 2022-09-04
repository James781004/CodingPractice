package LeetcodeMaster.Greedy;

public class Q19_Dota2 {
//    649. Dota2 參議院
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0649.Dota2%E5%8F%82%E8%AE%AE%E9%99%A2.md
//
//    Dota2 的世界里有兩個陣營：Radiant(天輝)和 Dire(夜魘)
//
//    Dota2 參議院由來自兩派的參議員組成。現在參議院希望對一個 Dota2 遊戲里的改變作出決定。他們以一個基於輪為過程的投票進行。在每一輪中，每一位參議員都可以行使兩項權利中的一項：
//
//    禁止一名參議員的權利：參議員可以讓另一位參議員在這一輪和隨後的幾輪中喪失所有的權利。
//
//    宣布勝利：如果參議員發現有權利投票的參議員都是同一個陣營的，他可以宣布勝利並決定在遊戲中的有關變化。
//
//    給定一個字符串代表每個參議員的陣營。字母 “R” 和 “D” 分別代表了 Radiant（天輝）和 Dire（夜魘）。然後，如果有 n 個參議員，給定字符串的大小將是 n。
//
//    以輪為基礎的過程從給定順序的第一個參議員開始到最後一個參議員結束。這一過程將持續到投票結束。所有失去權利的參議員將在過程中被跳過。
//
//    假設每一位參議員都足夠聰明，會為自己的政黨做出最好的策略，你需要預測哪一方最終會宣布勝利並在 Dota2 遊戲中決定改變。輸出應該是 Radiant 或 Dire。


    // 消滅的策略是，盡量消滅自己後面的對手，因為前面的對手已經使用過權利了，而後序的對手依然可以使用權利消滅自己的同伴！
    // 那麽局部最優：有一次權利機會，就消滅自己後面的對手。全局最優：為自己的陣營贏取最大利益。
    public String predictPartyVictory(String senateStr) {
        // R = true表示本輪循環結束後，字符串里依然有R。D同理
        Boolean R = true, D = true;
        // 當flag大於0時，R在D前出現，R可以消滅D。當flag小於0時，D在R前出現，D可以消滅R
        int flag = 0;
        byte[] senate = senateStr.getBytes();
        while (R && D) { // 一旦R或者D為false，就結束循環，說明本輪結束後只剩下R或者D了
            R = false;
            D = false;
            for (int i = 0; i < senate.length; i++) {
                if (senate[i] == 'R') {
                    if (flag < 0) senate[i] = 0; // 消滅R，R此時為false
                    else R = true; // 如果沒被消滅，本輪循環結束有R
                    flag++;
                }
                if (senate[i] == 'D') {
                    if (flag > 0) senate[i] = 0;
                    else D = true;
                    flag--;
                }
            }
        }
        // 循環結束之後，R和D只能有一個為true
        return R == true ? "Radiant" : "Dire";
    }
}
