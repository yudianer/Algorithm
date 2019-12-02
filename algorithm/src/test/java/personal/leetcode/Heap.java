package personal.leetcode;

import org.junit.Test;

import java.util.*;

/**
 * @author malujia
 * @create 11-26-2019 下午1:07
 **/

public class Heap {
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
            // 根元素（i） 是否與 子元素(idx)交換位置。大小根堆的區別就在這裏的交換條件不一樣
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
}
