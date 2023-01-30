package GuChengAlgorithm.Ch07_ClassicInterviewQuestion;

import java.util.*;

public class Q03_TopK {
    // https://docs.google.com/presentation/d/1yugv0iH1LXmTnujF1MS8Za2YiArdGMI1Py0_X5s-81A/edit#slide=id.gd604440cc9_0_6
    class topKFrequent {
        // 直接用maxHeap放入所有數字，拿出heap頭部最大的k個數字
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int n : nums) map.put(n, map.getOrDefault(n, 0) + 1);
            PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> map.get(b) - map.get(a));
            pq.addAll(map.keySet());

            int[] res = new int[k];
            for (int i = 0; i < k; i++) {
                res[i] = pq.poll();
            }
            return res;
        }


        public int[] topKFrequentMinHeap(int[] nums, int k) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int n : nums) map.put(n, map.getOrDefault(n, 0) + 1);
            PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b));
            for (int key : map.keySet()) {
                pq.offer(key);
                if (pq.size() > k) pq.poll();  // 直接開一個size=k的minHeap
            }

            int[] res = new int[k];
            for (int i = 0; i < k; i++) {
                res[i] = pq.poll();
            }
            return res;
        }

        int[] unique;
        Map<Integer, Integer> valueToFreq;

        public int[] topKFrequent2(int[] nums, int k) {
            valueToFreq = new HashMap<>();
            for (int n : nums) valueToFreq.put(n, valueToFreq.getOrDefault(n, 0) + 1);
            int N = valueToFreq.size();
            unique = new int[N];
            int i = 0;
            for (int n : valueToFreq.keySet()) unique[i++] = n;
            quickSelect(0, N - 1, N - k);  // n-k小，其他部份就是前k大
            return Arrays.copyOfRange(unique, N - k, N);
        }

        private void quickSelect(int left, int right, int kSmallest) {
            if (left == right) return;
            int pos = partition(left, right);
            if (kSmallest == pos) return;
            else if (kSmallest < pos) quickSelect(left, pos - 1, kSmallest);
            else quickSelect(pos + 1, right, kSmallest);
        }

        private int partition(int left, int right) {
            int pivot = valueToFreq.get(unique[right]);
            int wall = left;
            for (int i = left; i <= right; i++) {
                if (valueToFreq.get(unique[i]) < pivot) {
                    swap(wall, i);
                    wall++;
                }
            }
            swap(wall, right);
            return wall;
        }

        private void swap(int a, int b) {
            int tmp = unique[a];
            unique[a] = unique[b];
            unique[b] = tmp;
        }

        // bucket sort使用frequency作為index
        public int[] topKFrequent3(int[] nums, int k) {
            List<Integer>[] bucket = new List[nums.length + 1];
            Map<Integer, Integer> map = new HashMap<>();
            for (int n : nums) map.put(n, map.getOrDefault(n, 0) + 1);
            for (int key : map.keySet()) {
                int frequency = map.get(key);
                if (bucket[frequency] == null) bucket[frequency] = new ArrayList<>();
                bucket[frequency].add(key);
            }

            List<Integer> res = new ArrayList<>();
            for (int pos = bucket.length - 1; pos >= 0 && res.size() < k; pos--) {
                if (bucket[pos] != null) res.addAll(bucket[pos]);
            }
            return res.stream().mapToInt(x -> x).toArray();
        }
    }


    // https://docs.google.com/presentation/d/1yugv0iH1LXmTnujF1MS8Za2YiArdGMI1Py0_X5s-81A/edit#slide=id.gd604440cc9_0_70
    class KthLargestInStream {
        PriorityQueue<Integer> pq;
        int k;

        public KthLargestInStream(int k, int[] nums) {
            this.k = k;
            pq = new PriorityQueue<>();
            for (int n : nums) add(n);
        }

        public int add(int val) {
            if (pq.size() < k || val > pq.peek()) pq.offer(val);
            if (pq.size() > k) pq.poll();
            return pq.peek();
        }
    }


    // https://docs.google.com/presentation/d/1yugv0iH1LXmTnujF1MS8Za2YiArdGMI1Py0_X5s-81A/edit#slide=id.gd604440cc9_0_93
    class StreamChecker {
        TrieNode root = new TrieNode();
        StringBuilder sb = new StringBuilder();

        public StreamChecker(String[] words) {
            createTrie(words);
        }

        private void createTrie(String[] words) {
            for (String s : words) {
                TrieNode cur = root;
                int len = s.length();
                for (int i = len - 1; i >= 0; i--) {
                    char c = s.charAt(i);
                    if (cur.children[c - 'a'] == null) {
                        cur.children[c - 'a'] = new TrieNode();
                    }
                    cur = cur.children[c - 'a'];
                }
                cur.isWord = true;
            }
        }

        public boolean query(char letter) {
            sb.append(letter);
            TrieNode cur = root;
            for (int i = sb.length() - 1; i >= 0 && cur != null; i--) {
                char c = sb.charAt(i);
                cur = cur.children[c - 'a'];
                if (cur != null && cur.isWord) return true;
            }
            return false;
        }


        class TrieNode {
            boolean isWord;
            TrieNode[] children = new TrieNode[26];
        }
    }

}
