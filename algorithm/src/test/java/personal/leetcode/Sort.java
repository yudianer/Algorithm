package personal.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author malujia
 * @create 12-13-2019 下午3:30
 * 排序算法
 **/

public class Sort {

    @Test
    public void arrayTest(){
        int[] nums = {5,4,2,2,1,6};
        int[] nums2 = {6,2,3,4,5,6};
        System.out.println(Arrays.toString(intersect(nums, nums2)));
    }

    /**
     *todo 350. Intersection of Two Arrays II
     * 求交集，包括重复的元素
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        Heap.aescOrderedHeapSort(nums1);
        Heap.aescOrderedHeapSort(nums2);
        int i = 0, j=0;
        ArrayList<Integer> res = new ArrayList<>();
        while (i<nums1.length && j< nums2.length){
            int tmpI = nums1[i];
            int tmpJ = nums2[j];
            if (tmpI == tmpJ){
                res.add(tmpI);
                i++;
                j++;
            }else if (tmpI < tmpJ){
                i++;
            }else{
                j++;
            }
        }
        int[] nums = new int[res.size()];
        i = 0;
        for (Integer numI: res){
            nums[i++] = numI;
        }
        return nums;
    }
    /**
     *todo 利用 Map 存储各个元素出现的次数。
     */
    public int[] intersect1(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> numsCount1 = new HashMap<>();
        HashMap<Integer, Integer> numsCount2 = new HashMap<>();
        for (int i: nums1){
            int count = 1;
            if (numsCount1.containsKey(i))
                count += numsCount1.get(i);
            numsCount1.put(i, count);
        }
        for (int i: nums2){
            int count = 1;
            if (numsCount2.containsKey(i))
                count += numsCount2.get(i);
            numsCount2.put(i, count);
        }
        List<Integer> ele = new ArrayList<>();
        for (Integer i: numsCount1.keySet()){
            int count1 = numsCount1.get(i);
            int count2 = 0;
            if (numsCount2.containsKey(i)){
                count2 = numsCount2.get(i);
            }
            int count = Math.min(count1, count2);
            for (int i1=0; i1< count; i1++){
                ele.add(i);
            }
        }
        int[] res = new int[ele.size()];
        int idx = 0;
        for (int i: ele){
            res[idx++] = i;
        }
        return res;
    }
    /**
     *todo 922. Sort Array By Parity II
     * 奇数下标的值为奇数
     * 偶数下标的值为偶数
     * 2 <= A.length <= 20000
     * A.length % 2 == 0
     * 0 <= A[i] <= 1000
     */
    public int[] sortArrayByParityII(int[] A) {
        int oddIdx = 1;
        int evenIdx = 0;
        int oddEnd = A.length -1 ;
        int evenEnd = A.length - 2;
        // 对奇数调整
        while (oddIdx<=oddEnd){
            if (!isOdd(A[oddIdx])){
                while (evenIdx<=evenEnd){
                    if (isOdd(A[evenIdx])){
                        int tmp = A[oddIdx];
                        A[oddIdx] = A[evenIdx];
                        A[evenIdx] = tmp;
                        break;
                    }
                    evenIdx += 2;
                }
            }
            oddIdx += 2;
        }
        return A;
    }
    // a是否是奇数
    private boolean isOdd(int a){
        return a % 2 != 0;
    }

    public List<Integer> sortArray(int[] nums) {
        heapSortAsc(nums);
        List<Integer> res = new ArrayList<>();
        for (int num: nums)
            res.add(num);
        return res;
    }


    public static void heapSortAsc(int[] nums){
        if (nums.length <= 1)
            return;
        for (int n = nums.length; n>1; n--){
            buildMaxRootHeap(nums, n);
            int tmpNumsRoot = nums[n - 1];
            nums[n - 1] = nums[0];
            nums[0] = tmpNumsRoot;
        }
    }
    private static void buildMaxRootHeap(int[] nums, int n){
        for (int i = n/2; i > 0 ; i--) {
            maxRootAdjustDown(nums, i, n);
        }
    }
/*    *
     * 大根堆调整
     * @param i
     * @param n*/

    private static void maxRootAdjustDown(int[] nums, int i, int n){
        int numI = nums[i-1];
        for (int tmpIdx = 2*i; tmpIdx < n+1; tmpIdx = 2*i){
            if (tmpIdx < n && nums[tmpIdx-1] < nums[tmpIdx])
                tmpIdx ++;
            if (nums[tmpIdx-1] > numI){
                nums[i-1] = nums[tmpIdx-1];
                i = tmpIdx;
            }
            else break;
        }
        nums[i-1] = numI;
    }

}
