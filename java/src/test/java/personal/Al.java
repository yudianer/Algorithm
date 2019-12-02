package personal;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author malujia
 * @create 11-26-2019 下午12:10
 **/

public class Al {

    @Test
    public void minHeapTest(){
        int[] nums = {2,1,4,5,3};
        sortMinHeap(nums);
        System.out.println("-----------------");
        System.out.println(Arrays.toString(nums));
        System.out.println("-----------------");
        sortMaxHeap(nums);
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
