package personal.lock;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class SynchronizedTest
{
   /* String name = "true";
    CountDownLatch counter = new CountDownLatch(1);
    int count = 0;
    Object lock1 = new Object();
    Object lock2 = new Object();
    @Test
    public void threadTest() throws InterruptedException {
        *//*HashMap
        new Thread(()->{
            try {
                for (int j = 0; j < 4; j++) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("non-syn: " + +j+"-"+ name + System.currentTimeMillis());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        for (int j = 0; j < 4; j++) {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("syn: " + +j+"-"+ name + System.currentTimeMillis());
        }
        TimeUnit.SECONDS.sleep(100);*//*
    }

    @Test
    public void volatiledTest() throws InterruptedException {
        new Thread(()->{
            while (true){
                synchronized (lock1){
                    System.out.println(count +" from non syn");
                    try {
                        lock1.wait();
                        System.out.println("wait cancel!");
                        lock2.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        for (int i = 0; i < 50; i++) {
            new Thread(()->{
                try {
                    counter.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2){
                    for (int j = 0; j < 100000; j++) {
                        count += 1;
                    }
                    System.out.println(count + " from syn");
                    try {
                        lock1.notifyAll();
                        TimeUnit.SECONDS.sleep(2);
                        lock2.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        counter.countDown();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("over");
        System.out.println(count);
    }

    @Test
    public void nofityTEst() throws InterruptedException {
        new Thread(()->{
            synchronized (lock1){
                System.out.println("locked");
                try {
                    lock1.wait(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("unlocked");
            }
        }).start();
        TimeUnit.SECONDS.sleep(10);
        synchronized (lock1){
            System.out.println("lock in main thread");
            TimeUnit.SECONDS.sleep(10);
            lock1.notifyAll();
            System.out.println("notify");
        }
    }
    @Test
    public void selfLockTest() throws InterruptedException {
        Mutex mutex = new Mutex();
        new Thread(()->{
            mutex.lock();
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-1").start();
        TimeUnit.SECONDS.sleep(2);
        new Thread(()->{
            mutex.unlock();
        }, "thread-2").start();
    }

    @Test
    public void parkTest() throws InterruptedException {
//        LockSupport.unpark(Thread.currentThread());
//        TimeUnit.SECONDS.sleep(3);
//        LockSupport.park();
//        System.out.println("parking");
        this.lock1.notifyAll();
    }
}

class Mutex2 implements Lock {
    private static class Sync extends AbstractQueuedSynchronizer {
        protected boolean isHeldExclusively() {
            return getState() == 1 && getExclusiveOwnerThread() == Thread.currentThread();
        }
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0, 1)){
                setExclusiveOwnerThread(Thread.currentThread());
                System.out.println(Thread.currentThread().getName() + " owns the lock");

                return true;
            }
            return false;
        }
        protected boolean tryRelease(int arg){
            if (getState() == 0 || getExclusiveOwnerThread() != Thread.currentThread()) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            System.out.println(Thread.currentThread().getName() + " release the lock");
            return true;
        }
        Condition newCondition() {
            return new ConditionObject();
        }
    }
    private final Sync sync = new Sync();
    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

}

class Mutex implements Lock, java.io.Serializable {

    // Our internal helper class
    private static class Sync extends AbstractQueuedSynchronizer {
        // Reports whether in locked state
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        // Acquires the lock if state is zero
        public boolean tryAcquire(int acquires) {
            assert acquires == 1; // Otherwise unused
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                System.out.println(Thread.currentThread().getName() + " owns the lock");
                return true;
            }
            return false;
        }

        // Releases the lock by setting state to zero
        protected boolean tryRelease(int releases) {
            assert releases == 1; // Otherwise unused
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            System.out.println(Thread.currentThread().getName() + " release the lock");
            return true;
        }

        // Provides a Condition
        Condition newCondition() { return new ConditionObject(); }

        // Deserializes properly
        private void readObject(ObjectInputStream s)
                throws IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }
    }

    // The sync object does all the hard work. We just forward to it.
    private final Sync sync = new Sync();

    public void lock()                { sync.acquire(1); }
    public boolean tryLock()          { return sync.tryAcquire(1); }
    public void unlock()              { sync.release(1); }
    public Condition newCondition()   { return sync.newCondition(); }
    public boolean isLocked()         { return sync.isHeldExclusively(); }
    public boolean hasQueuedThreads() { return sync.hasQueuedThreads(); }
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }
    public boolean tryLock(long timeout, TimeUnit unit)
            throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }*/
}

