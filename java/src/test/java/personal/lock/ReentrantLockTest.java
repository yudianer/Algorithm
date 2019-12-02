package personal.lock;

import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author malujia
 * @create 08-23-2019 上午11:09
 **/

public class ReentrantLockTest {
    private static CountDownLatch start = new CountDownLatch(1);
    @Test
    public void unfairTest() throws InterruptedException {
        fairUnfairTest(false);
        Thread.sleep(100000);
    }

    public void fairUnfairTest(boolean fair) throws InterruptedException {
        ReentrantLock2 lock = new ReentrantLock2(fair);
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(lock));
            thread.setName(i+"");
            thread.setDaemon(false);
            thread.start();
        }
        start.countDown();
    }

    private static class Job implements Runnable{
        private ReentrantLock2 lock;
        public Job(ReentrantLock2 lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 2; i++) {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " owns this thread: " + lock.getQueuedThreads());
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock{
        public ReentrantLock2(boolean fair){
            super(fair);
        }

        public Collection<Thread> getQueuedThreads(){
            return super.getQueuedThreads();
        }
    }
}

