package personal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long start = System.currentTimeMillis();
        forkJoinPool.submit(new CounterTask2(1, 100000)).get();
        System.out.println(System.currentTimeMillis() - start);
    }

}
class CounterTask2 extends RecursiveTask<Integer> {
    private final int a;
    private final int b;
    public CounterTask2(int a, int b){
        this.a = a;
        this.b = b;
    }
    @Override
    protected Integer compute() {
        if (b - a < 100){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return (a+b) * (b-a+1) / 2;
        }
        else{
            int middle = (a+b)/2;
            CounterTask2 left = new CounterTask2(a, middle);
            CounterTask2 right = new CounterTask2(middle + 1, b);
            left.fork();
            right.fork();
            invokeAll(left, right);
            return left.join() + right.join();
        }
    }
}