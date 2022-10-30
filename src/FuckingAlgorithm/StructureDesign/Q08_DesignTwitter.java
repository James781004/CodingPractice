package FuckingAlgorithm.StructureDesign;

import java.util.*;

public class Q08_DesignTwitter {
//    https://leetcode.cn/problems/design-twitter/
//    355. 設計推特
//    設計一個簡化版的推特(Twitter)，可以讓用戶實現發送推文，關注/取消關注其他用戶，能夠看見關注人（包括自己）的最近 10 條推文。
//
//    實現 Twitter 類：
//
//    Twitter() 初始化簡易版推特對象
//    void postTweet(int userId, int tweetId) 根據給定的 tweetId 和 userId 創建一條新推文。每次調用此函數都會使用一個不同的 tweetId 。
//    List<Integer> getNewsFeed(int userId) 檢索當前用戶新聞推送中最近  10 條推文的 ID 。
//    新聞推送中的每一項都必須是由用戶關注的人或者是用戶自己發布的推文。推文必須 按照時間順序由最近到最遠排序 。
//    void follow(int followerId, int followeeId) ID 為 followerId 的用戶開始關注 ID 為 followeeId 的用戶。
//    void unfollow(int followerId, int followeeId) ID 為 followerId 的用戶不再關注 ID 為 followeeId 的用戶。

    class Twitter {
        // 全局時間戳
        int globalTime = 0;
        // 記錄用戶 ID 到用戶示例的映射
        HashMap<Integer, User> idToUser = new HashMap<>();

        // Tweet 類
        class Tweet {
            private int id;
            // 時間戳用於對信息流按照時間排序
            private int timestamp;
            // 指向下一條 tweet，類似單鏈表結構
            private Tweet next;

            public Tweet(int id) {
                this.id = id;
                // 新建一條 tweet 時記錄並更新時間戳
                this.timestamp = globalTime++;
            }

            public int getId() {
                return id;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public Tweet getNext() {
                return next;
            }

            public void setNext(Tweet next) {
                this.next = next;
            }
        }

        // 用戶類
        class User {
            // 記錄該用戶的 id 以及發布的 tweet
            private int id;
            private Tweet tweetHead;
            // 記錄該用戶的關注者
            private HashSet<User> followedUserSet;

            public User(int id) {
                this.id = id;
                this.tweetHead = null;
                this.followedUserSet = new HashSet<>();
            }

            public int getId() {
                return id;
            }

            public Tweet getTweetHead() {
                return tweetHead;
            }

            public HashSet<User> getFollowedUserSet() {
                return followedUserSet;
            }

            public boolean equals(User other) {
                return this.id == other.id;
            }

            // 關注其他人
            public void follow(User other) {
                followedUserSet.add(other);
            }

            // 取關其他人
            public void unfollow(User other) {
                followedUserSet.remove(other);
            }

            // 發布一條 tweet
            public void post(Tweet tweet) {
                // 把新發布的 tweet 作為鏈表頭節點
                tweet.setNext(tweetHead);
                tweetHead = tweet;
            }
        }

        public void postTweet(int userId, int tweetId) {
            // 如果這個用戶還不存在，新建用戶
            if (!idToUser.containsKey(userId)) {
                idToUser.put(userId, new User(userId));
            }
            User user = idToUser.get(userId);
            user.post(new Tweet(tweetId));
        }

        public List<Integer> getNewsFeed(int userId) {
            List<Integer> res = new LinkedList<>();
            if (!idToUser.containsKey(userId)) {
                return res;
            }

            // 獲取該用戶關注的用戶列表
            User user = idToUser.get(userId);
            Set<User> followedUserSet = user.getFollowedUserSet();

            // 每個用戶的 tweet 是一條按時間排序的鏈表
            // 現在執行合並多條有序鏈表的邏輯，找出時間線中的最近 10 條動態
            PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> {
                // 按照每條 tweet 的發布時間降序排序（最近發布的排在事件流前面）
                return b.timestamp - a.timestamp;
            });

            // 該用戶自己的 tweet 也在時間線內
            if (user.getTweetHead() != null) {
                pq.offer(user.getTweetHead());
            }

            for (User other : followedUserSet) {
                if (other.getTweetHead() != null) {
                    pq.offer(other.tweetHead);
                }
            }

            // 合並多條有序鏈表
            int count = 0;
            while (!pq.isEmpty() && count < 10) {
                Tweet tweet = pq.poll();
                res.add(tweet.getId());
                if (tweet.getNext() != null) {
                    pq.offer(tweet.getNext());
                }
                count++;
            }

            return res;
        }

        public void follow(int followerId, int followeeId) {
            // 如果用戶還不存在，則新建用戶
            if (!idToUser.containsKey(followerId)) {
                idToUser.put(followerId, new User(followerId));
            }
            if (!idToUser.containsKey(followeeId)) {
                idToUser.put(followeeId, new User(followeeId));
            }

            User follower = idToUser.get(followerId);
            User followee = idToUser.get(followeeId);
            // 關注者關注被關注者
            follower.follow(followee);
        }

        public void unfollow(int followerId, int followeeId) {
            if (!idToUser.containsKey(followerId) || !idToUser.containsKey(followeeId)) {
                return;
            }
            User follower = idToUser.get(followerId);
            User followee = idToUser.get(followeeId);
            // 關注者取關被關注者
            follower.unfollow(followee);
        }
    }
}
