package personal.leetcode;

import org.junit.Test;

/**
 * @author malujia
 *
 *info
 * 困难是找到子问题，并将子问题的结果存储下来，然后使用子问题的结果去解决总问题
 * 所以动态规划一般都可以使用递归的方式来解决：
 * f(n) = f(n-1).....
 * 然后使用 空间换时间的方式，使用数组等方式存储子问题的结果，然后从小往大推
 **/

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
     *todo
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

    /**
     *todo
     * ----------------------------------------------------------------------------------
     * 70. Climbing Stairs
     * climbStairs(n) = climbStairs(n-2) + clibStairts(n-1)
     */
    public int climbStairs(int n) {
        if(n==1)
            return 1;
        if(n==2)
            return 2;
        int pre1 =1, pre2=2;
        int res=0;
        while(n>2){
            res=pre1 + pre2;
            pre1=pre2;
            pre2=res;
            n--;
        }
        return res;
    }

    /**
     *todo
     * --------------------------------------------------------------------------------
     * 62. Unique Paths
     * uniquePaths(m,n) = uniquePaths(m-1,n) + uniquePaths(m,n-1)
     * 下面代码还可以根据对称性进行优化
     */

    public int uniquePaths(int m, int n) {
        int[]sum = new int[n];
        for(int i=0; i<n; i++){
            sum[i]=1;
        }
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                sum[j] = sum[j-1] + sum[j];
            }
        }
        return sum[n-1];
    }



    /**
     *todo
     * --------------------------------------------------------------------------------------------------------
     * 63. Unique Paths II
     * int[][] obstacleGrid = {{0,1,0,0,0},
     *                         {1,0,0,0,0},
     *                         {0,0,0,0,0},
     *                         {0,0,0,0,0}
     *                         };
     * 这个二维数组中 1 是障碍，0可以通过。
     * 整体思路还是 uniquePaths(m,n) = uniquePaths(m-1,n) + uniquePaths(m,n-1)
     * 这里需要注意 obstacleGrid(m,n) = 1 时，  uniquePaths(m,n) = 0
     * 另外，判断第一行和第一列时，uniquePaths(m,0) 或者 uniquePaths(0,n) 不通的情况：
     *  1. obstacleGrid(m,0) == 1， uniquePaths(0,n) == 1
     *  2. sum[0]存储的上一行的列 sum[0] == 1，
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[]sum = new int[n];
        if (obstacleGrid[0][0] == 1){
            return 0;
        }
        sum[0] = 1;
        int idx = 1;
        //根据第一行设置，如果前一个元素为1，则后面的元素位置都是不可达的
        while (idx < n && obstacleGrid[0][idx] == 0){
                sum[idx ++] = 1;
        }
        while (idx<n){
            sum[idx ++] = 0;
        }
        for(int i=1;i<m;i++){
            //根据每行的第一列，不可达的条件是本身为1,或者前一行的第一列不可达，即sum[0]
            if (obstacleGrid[i][0] == 1 || sum[0] == 0){
                sum[0] = 0;
            }else {
                sum[0] = 1;
            }
            for(int j=1;j<n;j++){
                if (obstacleGrid[i][j] == 1){
                    sum[j] = 0;
                    continue;
                }
                sum[j] += sum[j-1];
            }
        }
        return sum[n-1];
    }

    @Test
    public void test3(){
        int[][] obstacleGrid = {
                {1},
                {1},
                {4}
        };
        System.out.println(minPathSum(obstacleGrid));
    }
    /**
     *todo
     * ----------------------------------------------------------------------------------------------------
     *  64. Minimum Path Sum
     *  从左上角到右下角，在所有的路径中找到最小路径的路径值
     *  minPathSum(m,n) = Math.min(minPathSum(m-1,n),minPathSum(m,n-1)) + grid[m,n]
     * [
     *   [1,3,1],
     *   [1,5,1],
     *   [4,2,1]
     * ]
     * Output: 7
     *       易错处在设置第一行和第一列的值
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] sum = new int[n];
        sum[0] = grid[0][0];
        for (int i = 1; i < sum.length; i++) {
            sum[i] = sum[i-1] + grid[0][i];
        }
        for (int i = 1; i< m; i++){
            sum[0] += grid[i][0];
            for (int j = 1; j < n; j++) {
                if (sum[j]>sum[j-1])
                    sum[j] = sum[j-1];
                sum[j] += grid[i][j];
            }
        }
        return sum[n-1];
    }

}
