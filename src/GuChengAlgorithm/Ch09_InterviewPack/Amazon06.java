package GuChengAlgorithm.Ch09_InterviewPack;

import java.util.*;

public class Amazon06 {
    // https://docs.google.com/presentation/d/1Gv0bWUkF2J36MWPjbKcXxAD-vnG9KrvfOfyGMN-fvg4/edit#slide=id.g1564038e06c_0_64
    public int tupleSameProduct(int[] nums) {
        int N = nums.length, res = 0;
        Map<Integer, Integer> map = new HashMap<>();

        // 因為題目明確說了所有數字不同，
        // 所以兩個pair乘積相同，只要有一個數字不同，必然二者都不同，
        // 類似4 sum
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                int product = nums[1] * nums[j];
                map.put(product, map.getOrDefault(product, 0) + 1);
            }
        }

        for (int key : map.keySet()) {
            int count = map.get(key);
            if (count > 1) {
                int time = (0 + (count - 1)) * count / 2;  // (頭 + 尾) * 總長 / 2
                res += time * 8;  // 共有8種組合
            }
        }
        return res;
    }

    // https://docs.google.com/presentation/d/1Gv0bWUkF2J36MWPjbKcXxAD-vnG9KrvfOfyGMN-fvg4/edit#slide=id.g1564038e06c_0_73
    public boolean canJump(int[] nums) {
        int max = 0;

        // 直接greedy, 如果當前的位置可以reachable, 我們就更新更遠的我們可以到達的位置，
        // 反之我們啥也不幹，最後看我們最遠能不能到達末尾
        for (int i = 0; i < nums.length - 1; i++) {
            if (i <= max) max = Math.max(max, i + nums[i]);
        }

        return max >= nums.length - 1;
    }


    // https://docs.google.com/presentation/d/1Gv0bWUkF2J36MWPjbKcXxAD-vnG9KrvfOfyGMN-fvg4/edit#slide=id.g1564038e06c_0_85
    public int riceBagsHelper(List<Integer> riceBags) {
        Collections.sort(riceBags);
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 1;
        for (int k = riceBags.size() - 1; k >= 0; k--) {
            int elem = riceBags.get(k);
            if (map.containsKey(elem * elem)) {
                int temp = map.get(elem * elem) + 1;
                ans = Math.max(ans, temp);
                map.put(elem, temp);
            } else {
                map.put(elem, 1);
            }
        }
        return ans == 1 ? -1 : ans;
    }


    // https://docs.google.com/presentation/d/1Gv0bWUkF2J36MWPjbKcXxAD-vnG9KrvfOfyGMN-fvg4/edit#slide=id.g1564038e06c_0_102
    public String countAndSay(int n) {
        String s = "1";
        for (int i = 1; i < n; i++) {
            s = countIdx(s);
        }
        return s;
    }

    // 上面是示範案例，主要方法是這個
    public String countIdx(String s) {
        StringBuilder sb = new StringBuilder();
        char c = s.charAt(0);
        int count = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == c) count++;
            else {
                sb.append(count);
                sb.append(c);
                c = s.charAt(i);
                count = i;
            }
        }
        sb.append(count);
        sb.append(c);
        return sb.toString();
    }


    // https://docs.google.com/presentation/d/1Gv0bWUkF2J36MWPjbKcXxAD-vnG9KrvfOfyGMN-fvg4/edit#slide=id.g1564038e06c_0_113
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[0] + a[1] - b[0] - b[1]); // <nums1元素, nums2元素, nums2下標位置>
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums1.length && i < k; i++) {
            q.offer(new int[]{nums1[i], nums2[0], 0});  // nums1每個元素先跟nums2開頭配對，最後一個位置放nums2下標位置
        }
        while (k-- > 0 && !q.isEmpty()) {
            int[] cur = q.poll(); // <nums1元素, nums2元素, nums2下標位置>
            res.add(new ArrayList<>(Arrays.asList(cur[0], cur[1])));
            if (cur[2] == nums2.length - 1) continue;  // nums2下標位置到尾端就跳過
            q.offer(new int[]{cur[0], nums2[cur[2] + 1], cur[2] + 1});   // nums2下標位置 + 1
        }
        return res;
    }


    // 注意如果是n2logk會TLE, 不可以把每一個pair都嘗試，這樣就沒有用到array本身sorted的性質了
    public List<List<Integer>> kSmallestPairs2(int[] nums1, int[] nums2, int k) {
        PriorityQueue<List<Integer>> maxHeap = new PriorityQueue<List<Integer>>((a, b) -> (b.get(0) + b.get(1)) - (a.get(0) + a.get(1)));
        for (int n1 : nums1) {
            for (int n2 : nums2) {
                maxHeap.add(Arrays.asList(n1, n2));
                if (maxHeap.size() > k) maxHeap.remove();
            }
        }
        List<List<Integer>> res = new ArrayList<>();
        while (!maxHeap.isEmpty()) res.add(maxHeap.poll());
        return res;
    }
}
