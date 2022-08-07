package 程序员代码面试指南.ch01;

import java.util.LinkedList;
import java.util.Queue;

public class Q04_DogCatQueue {
    //    描述
//    实现一种猫狗队列的结构，要求如下：
//    1. 用户可以调用 add 方法将 cat 或者 dog 放入队列中
//    2. 用户可以调用 pollAll 方法将队列中的 cat 和 dog 按照进队列的先后顺序依次弹出
//    3. 用户可以调用 pollDog 方法将队列中的 dog 按照进队列的先后顺序依次弹出
//    4. 用户可以调用 pollCat 方法将队列中的 cat 按照进队列的先后顺序依次弹出
//    5. 用户可以调用 isEmpty 方法检查队列中是否还有 dog 或 cat
//    6. 用户可以调用 isDogEmpty 方法检查队列中是否还有 dog
//    7. 用户可以调用 isCatEmpty 方法检查队列中是否还有 cat
//    输入描述：
//    第一行输入一个整数 n 表示 用户的操作总次数。
//
//    以下 n行 每行表示用户的一次操作
//
//    每行的第一个参数为一个字符串 s，若 s = “add”， 则后面接着有 “cat x”（表示猫）或者“dog x”（表示狗），其中的 x 表示猫狗的编号。
//    输出描述：
//    对于每个操作：
//
//    若为 “add”，则不需要输出。
//
//    以下仅列举几个代表操作，其它类似的操作输出同理。
//
//    若为 “pollAll”，则将队列中的 cat 和 dog 按照进队列的先后顺序依次弹出。(FIFO)，格式见样例。
//
//    若为 "isEmpty"，则检查队列中是否还有 dog 或 cat， 为空则输出 “yes”， 否则输出 “no”。
//    示例1
//    输入：
//            11
//    add cat 1
//    add dog 2
//    pollAll
//            isEmpty
//    add cat 5
//    isDogEmpty
//            pollCat
//    add dog 10
//    add cat 199
//    pollDog
//            pollAll
//    复制
//    输出：
//    cat 1
//    dog 2
//    yes
//            yes
//    cat 5
//    dog 10
//    cat 199
//    复制
//    备注：10000001≤n≤1000000
//    保证每个猫和狗的编号x都不相同且  10000001≤x≤1000000
//    保证没有不合法的操作

    // Pet class記錄進入隊列的Pet種類
    public static class Pet {
        private String type;

        public Pet(String type) {
            this.type = type;
        }

        public String getPetType() {
            return this.type;
        }
    }

    // Dog和Cat繼承Pet，就同屬Pet類別
    public static class Dog extends Pet {
        public Dog() {
            super("dog");
        }
    }

    public static class Cat extends Pet {
        public Cat() {
            super("cat");
        }
    }

    // 包裝Pet實際進入隊列的class，用pet記錄元素種類，再用count記錄加入隊列的順序
    public static class PetEnterQueue {
        private Pet pet;
        private long count;

        public PetEnterQueue(Pet pet, long count) {
            this.pet = pet;
            this.count = count;
        }

        public Pet getPet() {
            return this.pet;
        }

        public long getCount() {
            return this.count;
        }

        public String getEnterPetType() {
            return this.pet.getPetType();
        }
    }

    public static class DogCatQueue {
        private Queue<PetEnterQueue> dogQ;
        private Queue<PetEnterQueue> catQ;
        private long count;

        public DogCatQueue() {
            this.dogQ = new LinkedList<PetEnterQueue>();
            this.catQ = new LinkedList<PetEnterQueue>();
            this.count = 0;
        }

        public void add(Pet pet) {
            if (pet.getPetType().equals("dog")) {
                this.dogQ.add(new PetEnterQueue(pet, this.count++));
            } else if (pet.getPetType().equals("cat")) {
                this.catQ.add(new PetEnterQueue(pet, this.count++));
            } else {
                throw new RuntimeException("err, not dog or cat");
            }
        }

        public Pet pollAll() {
            if (!this.dogQ.isEmpty() && !this.catQ.isEmpty()) {
                if (this.dogQ.peek().getCount() < this.catQ.peek().getCount()) {
                    return this.dogQ.poll().getPet();
                } else {
                    return this.catQ.poll().getPet();
                }
            } else if (!this.dogQ.isEmpty()) {
                return this.dogQ.poll().getPet();
            } else if (!this.catQ.isEmpty()) {
                return this.catQ.poll().getPet();
            } else {
                throw new RuntimeException("err, queue is empty!");
            }
        }

        public Dog pollDog() {
            if (!this.isDogQueueEmpty()) {
                return (Dog) this.dogQ.poll().getPet();
            } else {
                throw new RuntimeException("Dog queue is empty!");
            }
        }

        public Cat pollCat() {
            if (!this.isCatQueueEmpty()) {
                return (Cat) this.catQ.poll().getPet();
            } else
                throw new RuntimeException("Cat queue is empty!");
        }

        public boolean isEmpty() {
            return this.dogQ.isEmpty() && this.catQ.isEmpty();
        }

        public boolean isDogQueueEmpty() {
            return this.dogQ.isEmpty();
        }

        public boolean isCatQueueEmpty() {
            return this.catQ.isEmpty();
        }

    }

    public static void main(String[] args) {
        DogCatQueue test = new DogCatQueue();

        Pet dog1 = new Dog();
        Pet cat1 = new Cat();
        Pet dog2 = new Dog();
        Pet cat2 = new Cat();
        Pet dog3 = new Dog();
        Pet cat3 = new Cat();

        test.add(dog1);
        test.add(cat1);
        test.add(dog2);
        test.add(cat2);
        test.add(dog3);
        test.add(cat3);

        test.add(dog1);
        test.add(cat1);
        test.add(dog2);
        test.add(cat2);
        test.add(dog3);
        test.add(cat3);

        test.add(dog1);
        test.add(cat1);
        test.add(dog2);
        test.add(cat2);
        test.add(dog3);
        test.add(cat3);
        while (!test.isDogQueueEmpty()) {
            System.out.println(test.pollDog().getPetType());
        }
        while (!test.isEmpty()) {
            System.out.println(test.pollAll().getPetType());
        }
    }
}
