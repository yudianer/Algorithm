package personal.leetcode;

import org.junit.Test;

/**
 * @author malujia
 * @create 12-26-2019 下午12:04
 **/
//"cbbd"
public class DP {
    @Test
    public void test(){
        String s = "cbbd";
        System.out.println(longestPalindrome(s));
    }
    /**
     *todo
     * --------------------------------------------------------------------------------------------------------------------------------
     * 5. Longest Palindromic Substring，
     * 遍历所有的元素，以每个元素为中轴来测试是否是回文串，相当于遍历法，但是时间复杂度肯定是小于n*n的
     */
    public String longestPalindrome(String s) {
        if(s.length() <=1)
            return s;
        int pre=0,post=0;
        for(int i=0;i<s.length();){
            int j;
            for(j=i+1; j<s.length();j++){
                if(s.charAt(j) != s.charAt(i))
                    break;
            }
            j--;
            int foreHead =i-1, behindHead=j+1; // charAt(j) == charAt(i)
            while(foreHead>-1 && behindHead<s.length()){
                if(s.charAt(foreHead) != s.charAt(behindHead))
                    break;
                foreHead--;
                behindHead++;
            }

            foreHead++;
            behindHead--;

            if(behindHead-foreHead > post-pre){
                pre = foreHead;
                post = behindHead;
            }
            i = j+1;
        }
        return s.substring(pre, post+1);
    }

    /**
     * todo
     * 使用动态规划，动态规划的思想是先解决小问题，然后利用小问题的解去解决大问题
     * pal[i][j]表示[i,j]是否是回文串。pal[][] = new boolean[s.length()][s.length()]
     * 先把s中所有长度为2的子串测试一遍，填充pal[][]
     * 遍历所有长度为k的子串，对于 m,n 来说[m+1,n-1]已经知道了是不是回文串，所以填充pal[m][n]
     * 这里需要把 填充 n*n 个元素，所以复杂度为 O(n*n)
     */
    public String longestPalindromeWithDP(String s) {
        if (s.length() <=1)
            return s;
        boolean[][] pal = new boolean[s.length()][s.length()];
        int pre =0, next=0;
        for (int i = 0; i < s.length()-1; i++) {
            pal[i][i] = true;
            if (s.charAt(i) == s.charAt(i+1)){
                pal[i][i+1] = true;
                pre = i;
                next = i+1;
            }
        }
        pal[s.length()-1][s.length()-1] = true;
        for (int len = 3; len<=s.length(); len++){
            int maxI = s.length() - len;
            for (int i =0; i<= maxI; i++){
                int j = i + len -1;
                if (pal[i+1][j-1] && s.charAt(i) == s.charAt(j)){
                    pal[i][j] = true;
                    pre = i;
                    next = j;
                }
            }
        }
        return s.substring(pre, next + 1);
    }


    /**
     *todo
     * --------------------------------------------------------------------------------------------------------
     * 53. Maximum Subarray
     * @param nums [-2,1,-3,4,-1,2,1,-5,4],
     * 计算了所有子串的和然后比较
     * 开辟了new int[nums.length][nums.length]， 太费内存
     */
    public int maxSubArray(int[] nums) {
        int res = Integer.MIN_VALUE;
        int[][] sum = new int[nums.length][nums.length];
        for (int i = 0; i < nums.length; i++) {
            sum[i][i] = nums[i];
            if (nums[i] > res)
                res = nums[i];
        }
        for (int len = 2; len<=nums.length; len++){
            int maxI = nums.length - len;
            for (int i = 0; i <= maxI ; i++) {
                int j = i + len -1;
                sum[i][j] = sum[i][j-1] + nums[j];
                if (sum[i][j] > res)
                    res = sum[i][j];
            }
        }
        return res;
    }

    /**
     *TODO
     * 依然是计算了所有的子串的和，但是复用了数组，所以节省了空间，但是没有节省时间。
     * @param nums
     * @return
     */
    public int maxSubArray2(int[] nums) {
        int res = nums[0];
        int[] sum = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            sum[i] = nums[i];
            if (nums[i] > res)
                res = nums[i];
        }
        for (int len = 2; len<=nums.length; len++){
            int maxI = nums.length - len;
            for (int i = 0; i <= maxI ; i++) {
                sum[i] = sum[i] + nums[i + len -1];
                if (sum[i] > res)
                    res = sum[i];
            }
        }
        return res;
    }

    /**
     *todo
     * 计算以 i 结尾的串的最大子数组的和。
     * 只需要判断 sum[i-1] 是否大于 0 即可。
     */
    public int maxSubArray3(int[] nums) {
        int res = Integer.MIN_VALUE;
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i-1] <= 0 ? nums[i] : sum[i-1] + nums[i];
            if (sum[i] > res)
                res = sum[i];
        }
        return res;
    }
}
