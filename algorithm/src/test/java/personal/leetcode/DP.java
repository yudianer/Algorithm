package personal.leetcode;

/**
 * @author malujia
 * @create 12-26-2019 下午12:04
 **/

public class DP {
    public String longestPalindrome(String s) {
        if(s.length() <=1)
            return s;
        String res = '';
        int pre=-1,post=-1;
        for(int i=0;i<s.length();i++){
            int j;
            for(j=i+1; j<s.length();j++){
                if(s.charAt(j) != s.charAt(i))
                    break;
            }
            j--;
            int foreHead =i-1, behindHead=j+1; // charAt(j) == charAt(i)
            while(forHead>-1 && behindHead<s.length()){
                if(s.charAt(forHead) != s.charAt(behindHead))
                    break;
                foreHead--;
                behindHead++;
            }

            foreHead++;
            behindHead--;

            if(behindHead-foreHead + 1 > res.length()){
                pre = foreHead;
                post = behindHead;
            }
            i = behindHead+1;
        }
        return s.substring(pre, post+1);
    }
}
