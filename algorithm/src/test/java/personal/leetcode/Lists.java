package personal.leetcode;

import org.junit.Test;

/**
 * @author malujia
 * @create 12-16-2019 下午6:15
 **/

public class Lists {

    @Test
    public void test(){

    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode p1 = head, p2 = head;
        for(int i=0;i<n && p2!=null;i++){
            p2 = p2.next;
        }
        //要删除倒数第n个节点(第一个节点)
        if(p2==null){
            head = head.next;
            p1.next = null;
            return head;
        }

        while(p2.next != null){
            p2=p2.next;
            p1=p1.next;
        }
        p2 = p1.next;
        p1.next = p1.next.next;
        p2.next = null;
        return head;
    }
}
