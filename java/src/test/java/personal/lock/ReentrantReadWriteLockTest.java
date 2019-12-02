package personal.lock;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @author malujia
 * @create 08-26-2019 上午9:48
 **/

public class ReentrantReadWriteLockTest {
    @Test
    public void mapTest(){
        ConcurrentHashMap hashMap = new ConcurrentHashMap();
        HashMap hashMap1 = new HashMap();
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        concurrentLinkedQueue.remove();
    }
    /*class DelayTest implements Delayed{
        long time;
        public DelayTest(long time){
            this.time = time;
        }
        @Override
        public long getDelay(TimeUnit unit) {
            long now = System.nanoTime();
            return time - now;
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }

    @Test
    public void writeLockTest() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        Condition condition = lock.newCondition();
        condition.await();

//        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
//        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
//        concurrentLinkedQueue.add("hello");
//        concurrentLinkedQueue.peek();
//        DelayQueue<DelayTest> delayTests = new DelayQueue<>();
//        DelayTest delayTest = new DelayTest(System.nanoTime() + 10 * 1000000000L);
//        delayTests.add(delayTest);
//        delayTests.take();

    }

    @Test
    public void shiftTest() throws InterruptedException {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        System.out.println(Thread.currentThread().getName() + new Date().toLocaleString());
        Thread.sleep(2000);

        System.out.println("------------------");
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread("thread " + i){
                @Override
                public void run(){
                    readLock.lock();
                    System.out.println(Thread.currentThread().getName() + " coming !" + new Date().toLocaleString());
                    try {
                        Thread.sleep(3000);
//                        readLock.unlock();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
        Thread.sleep(4000);
        Thread writeThread = new Thread("write thread"){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                writeLock.lock();
                System.out.println(Thread.currentThread().getName() + new Date().toLocaleString());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                writeLock.unlock();
            }
        };
        writeThread.start();
        Thread.sleep(4000);

        for (int i = 3; i < 5; i++) {
            Thread thread = new Thread("thread " + i){
                @Override
                public void run(){
                    readLock.lock();
                    System.out.println(Thread.currentThread().getName() + " coming ! older" + new Date().toLocaleString());
                    try {
                        Thread.sleep(2000);
                        readLock.unlock();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
        System.out.println(Thread.currentThread().getName() + new Date().toLocaleString());
        writeLock.unlock();
        Thread.sleep(1000000);
//        ReentrantLock lock = new ReentrantLock();
    }*/
}
