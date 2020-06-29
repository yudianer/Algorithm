package personal;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * @author malujia
 * @create 11-26-2019 下午12:10
 **/

public class Al {

    @Test
    public void minHeapTest(){
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println(System.currentTimeMillis());
        forkJoinPool.submit(new CounterTask(1, 1000));
        System.out.println(System.currentTimeMillis());
    }

    public static void sortMinHeap(int[] nums){
        for (int i = 0; i < nums.length; i++) {
            buildMinHeap(nums, nums.length - i);
            System.out.println(nums[0]);
            nums[0] = nums[nums.length - i -1];
        }
    }

    public static void sortMaxHeap(int[] nums){
        for (int i=0; i< nums.length;i++){
            buildMaxHeap(nums, nums.length -i);
            System.out.println(nums[0]);
            nums[0] = nums[nums.length - i -1];
        }
    }

    public static void buildMaxHeap(int[] nums, int n){
        for (int i = n/2; i > 0; i--) {
            adjustDownForMaxHeap(i, n, nums);
        }
    }
    public static void buildMinHeap(int[] nums, int n){
        for (int i=n/2; i >0; i--){
            adjustDownForMinHeap(i, n, nums);
        }
    }
    private static void adjustDownForMaxHeap(int i, int n, int[] nums){
        if (n==1)
            return;
        int tmp = nums[i-1];
        for (int idx = 2 * i; idx <= n; idx = 2*i){
            if (idx + 1<=n && nums[idx] > nums[idx -1]){
                idx ++;
            }
            if (tmp < nums[idx-1]){
                nums[i-1] = nums[idx-1];
                i = idx;
            }else{
                break;
            }
        }
        nums[i-1] = tmp;
    }

    private static void adjustDownForMinHeap(int i, int n, int[] nums){
        int tmp = nums[i-1];
        for (int idx = 2 * i; idx<=n; idx = 2*i){
            if (idx+1<=n && nums[idx] < nums[idx-1]){
                idx++;
            }
            if (nums[idx-1] < tmp){
                nums[i-1] = nums[idx-1];
                i = idx;
            }else{
                break;
            }
        }
        nums[i-1] = tmp;
    }
}
class CounterTask extends RecursiveTask<Integer> {
    private final int a;
    private final int b;
    public CounterTask(int a, int b){
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
            left.fork();
            right.fork();
            return left.join() + right.join();
        }
    }
}