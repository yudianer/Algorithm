package personal.queue;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class QueueTest {
    @Test
    public void forkJoinTest(){
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println(System.currentTimeMillis());
        forkJoinPool.submit(new CounterTask(1, 1000));
        System.out.println(System.currentTimeMillis());
    }
}
/*class CounterTask2 extends RecursiveTask<Integer>{
    private final int a;
    private final int b;
    public CounterTask2(int a, int b){
        this.a = a;
        this.b = b;
    }
    @Override
    protected Integer compute() {
        if (b - a < 10){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return (a+b) * (b-a+1) / 2;
        }
        else{
            int middle = (a+b)/2;
            CounterTask left = new CounterTask(a, middle);
            CounterTask right = new CounterTask(middle + 1, b);
            invokeAll(left, right);
            return left.join() + right.join();
        }
    }
}*/
class CounterTask extends RecursiveTask<Integer>{
    private final int a;
    private final int b;
    public CounterTask(int a, int b){
        this.a = a;
        this.b = b;
    }
    @Override
    protected Integer compute() {
        if (b - a < 10)return (a+b) * (b-a+1) / 2;
        else{
            int middle = (a+b)/2;
            CounterTask left = new CounterTask(a, middle);
            CounterTask right = new CounterTask(middle + 1, b);
            left.fork();
            right.fork();
/**
 *          invokeAll(left, right);
            invokeAll(Collection<?>)
 */
            return left.join() + right.join();
        }
    }
}