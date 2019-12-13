package personal.leetcode;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.*;

/**
 * @author malujia
 * @create 11-26-2019 下午1:07
 * todo
 *  堆排序： 建堆 + 重建堆; 建堆的时间复杂度O(n),重建堆的时间复杂度为O(nlog(n)) => 堆排序的时间复杂度为 O(nlog(n))
 *  堆的特点： 第一个节点是最大的或者最小的，可以利用的就是这点
 *
 **/

public class Heap {
    //

    //1. 使用 n-k+1 個元素來建大根堆
    //2. 獲取 根元素 作爲第一個最大的元素
    //3. 其餘的 k-1 個元素的獲取通過：
    /**
     *      eles = eles[k];
     *      替換堆根->建堆->獲取根元素 這個
     *      for(int i=1; i<k-1;i++){
     *          替換堆根 //nums[0] = nums[heapSize + i -1];
     *          建堆 //buildMaxRootHeap;
     *          獲取根元素//eles[i++] = nums[0]
     *      }
     */

    //4. buildMaxHeap(eles) + sortHeap(eles)
    @Test
    public void topKFreTest(){
        String[] words = {"glarko","zlfiwwb","nsfspyox"};
        Map<String, Integer> wordFr = new HashMap<>();
        for (String word: words){
            if (wordFr.containsKey(word)){
                wordFr.put(word, wordFr.get(word) + 1);
            }else {
                wordFr.put(word, 1);
            }
        }
        for (String word: topKFrequent(words, 14)){
            System.out.println(word + ":" + wordFr.get(word));
        }
    }


    /**
     * todo
     *   大顶堆： 堆顶的节点是最大的，
     *   小顶堆： 堆顶的节点是最小的。
     *   1. 使用大顶堆：将堆中的最大元素最大元素挤出来存起来。
     *      过滤出k个最大的元素 ==>  对这些最大的元素做堆排序
     *                                                                                    -
     *      maxNodes 保存k个最大的元素。
     *      1). n-k+1 个元素建堆，O(log(n-k+1))
     *      2). 堆顶元素是第一个筛选出来的最大元素加入maxNodes。
     *      3). O((k-1)log(n-k+1))
     *          for 使用剩下的 k-1 个元素{
     *              替代堆顶元素
     *              调整堆 O(log(n-k+1))
     *              获取堆顶元素
     *          }
     *      4). 对 maxNodes 中的元素做堆排序。O(k(log(k)))
     *                                                                                          -
     *      最终的时间复杂度：O(log(n-k+1)) + O((k-1)log(n-k+1)) + O(k(log(k)))
     *                                                                                       -
     *    2. 使用小顶堆： 将最小值挤到堆顶，然后被抛弃掉（替换掉）。 这样剩下的就是最大的元素。
     *              过滤掉 n-k 个最小的值，剩下的堆中除了堆顶以外的最大 k 个元素。
     *              ** 堆顶元素就是用来替换的。
     *       1). k+1 的元素建堆， 堆顶的元素最小。 O(k+1)
     *       2). O((n-k-1)log(k+1))
     *           for 剩下的 n-k-1 个元素{
     *              代替堆顶的元素
     *              调整堆(Olog(k+1))
     *           }
     *       3). 对堆中的元素进行堆排序. O((k+1)log(k+1))
     *                                                                                -
     *       最终时间复杂度: O(k+1) + O((n-k-1)log(k+1)) + O((k+1)log(k+1))
     *
     */
    public List<String> topKFrequent(String[] words, int k) {
        if (k> words.length)
            k=words.length;
        Map<String, Integer> wordFr = new HashMap<>();
        for (String word: words){
            if (wordFr.containsKey(word)){
                wordFr.put(word, wordFr.get(word) + 1);
            }else {
                wordFr.put(word, 1);
            }
        }
        List<String> res = new ArrayList<>();
        //用於堆操作的元素
        words = new String[wordFr.size()];
        int i =0;
        for (String word: wordFr.keySet()){
            words[i++] = word;
        }
        int heapSize = words.length - k +1;
        //建立大根堆
        String[] tempHeapWords = new String[k];
        int tmpi = 0;
        buildMaxHeap(words, heapSize, wordFr);//log(k)
        // 開始獲取k個最大元素
        tempHeapWords[tmpi ++] = words[0];
        // 上面已經獲取一次了， 這裏只需要獲取k-1次就可
        for (int j = 1; j < k; j++) {
            words[0] = words[j + heapSize -1];
            adjustDown(1, heapSize, words, wordFr);//log(k)
            tempHeapWords[tmpi ++] = words[0];
        }
        // 對k個最大元素做堆排序：
        /**
         * for (int j =0; j<k; j++){
         *      建堆
         *      獲取首元素
         *      最後一個元素替換首元素
         *      堆size-1
         *  }
         */
        for (int j =0; j<k; j++){
            buildMaxHeap(tempHeapWords, k-j, wordFr);
            res.add(tempHeapWords[0]);
            tempHeapWords[0] = tempHeapWords[k-j-1];
        }
        return res;
    }

    public static void buildMaxHeap(String[] words, int n, Map<String, Integer> wordF){
        // 從堆的1/2出開始adjust，這在大小根堆中是一樣的。
        for (int i=n/2; i>0;i--){
            adjustDown(i, n, words, wordF);
        }
    }

    //大根堆和小根堆的區別 在於 adjustDown時的根元素與子元素的交換條件：
    /**
     *      大根堆： 根元素小於子元素的中最大的那一個。
     *      小根對： 根元素大於子元素中的最小的那一個。
     *
     *  //warning 獲取元素時的下標，容易忘記-1
     */
    public static void adjustDown(int i, int n, String[] words, Map<String, Integer> wordF){
        String tmp = words[i-1];//容易忘記-1
        for (int idx = 2*i; idx <=n ; idx = 2*i) {
            // 找到兩個子元素中適合的那一個。 大小根堆的區別就在這裏，交換的條件不一樣
            if (idx + 1<=n && (wordF.get(words[idx-1]) < wordF.get(words[idx]) ||
                    wordF.get(words[idx]).equals(wordF.get(words[idx-1])) && words[idx].compareTo(words[idx-1]) <0 )){
                idx++;
            }
            //info 根元素（i） 是否與 子元素(idx)交換位置。大小根堆的區別就在這裏的交換條件不一樣
            if (wordF.get(tmp) < wordF.get(words[idx-1]) ||
                    wordF.get(tmp).equals(wordF.get(words[idx-1])) && tmp.compareTo(words[idx-1]) > 0){
                words[i-1] = words[idx-1];
                i = idx;
            }else {
                break;
            }
        }
        words[i-1] = tmp;
    }

    //todo 大根堆这样排出来是 升序：1,2,3,4,5
    @Test
    public void test(){
        int[] nums = {5,4,2,3,1};
        aescOrderedHeapSort(nums);
    }
    public static void aescOrderedHeapSort(int[] nums){
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
    /**
     * 大根堆调整
     * @param i
     * @param n
     */
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

    //todo 小根堆 这样排出来的降序。5,4,3,2,1 因为从数组尾部开始放的。
    public static void descHeapSort(int[] nums){
        for (int n = nums.length; n>1;n--){
            buildMinRootHeap(nums, n);
            int tmpRootNum = nums[n-1];
            nums[n-1] = nums[0];
            nums[0] = tmpRootNum;
        }
    }
    private static void buildMinRootHeap(int[] nums, int n){
        for (int i = n/2; i>0; i--){
            minRootAdjustDown(nums, i, n);
        }
    }
    private static void minRootAdjustDown(int[] nums, int i, int n){
        int numI = nums[i-1];
        for (int nextIdx = 2*i; nextIdx < n+1; nextIdx = 2*i){
            if (nextIdx < n && nums[nextIdx-1] > nums[nextIdx])
                nextIdx++;
            if (nums[nextIdx] >= numI)
                break;
            nums[i-1]= nums[nextIdx-1];
            i = nextIdx;
        }
        nums[i-1] = numI;
    }
}
