package personal.leetcode;

import org.junit.Test;

import java.util.Stack;

/**
 * @author malujia
 * @create 11-27-2019 下午5:56
 **/

public class Tree {

    BTree root = new BTree(0);
    BTree t1 = new BTree(1);
    BTree t2 = new BTree(2);
    BTree t3 = new BTree(3);
    BTree t4 = new BTree(4);
    BTree t5 = new BTree(5);
    {
        root.left = t1;
        root.right = t2;
        t1.left = t3;
        t2.right = t4;
    }


    @Test
    public void testPostOrderNonRecursion(){
        postOrderNonRecursion(root);
    }

    public void postOrderNonRecursion(BTree root){
        Stack<BTree> roots = new Stack<>();
        BTree last = null;
        while(true){
            if (root != null){
                roots.push(root);
                root = root.left;
            }else{
                if (roots.empty())
                    break;
                root = roots.peek();
                //返回到上一层（该root的父节点）的两种情况：
                //1. 右子树为null，右子树已经访问过了
                if (root.right == null){
                    visitNode(roots.pop());// 后续遍历，每次遍历的是根节点，根节点访问了之后，就应该返回了
                    last = root;
                    root = null;//root 设置为null，可以使得，返回上一层后从父节点的右侧开始遍历
                }else{
                    if (last != root.right){// 上次遍历的节点不是当前节点的右孩子（上次不是从右节点返回过来的。）
                        root = root.right;
                    }else{
                        root = roots.pop();
                        visitNode(root);
                        last = root;
                        root = null;
                    }
                }
            }
        }

    }

    @Test
    public void testPreOrder(){
        preOrderNonRecursion(root);
    }

    public static void preOrderNonRecursion(BTree root){
        Stack<BTree> roots = new Stack<>();
        while (true){
            if (root != null){
                visitNode(root);
                roots.push(root);
                root = root.left;
            }else{
                if (roots.empty()){
                    break;
                }else {
                    root = roots.pop().right;
                }
            }
        }
    }

    @Test
    public void testInOrderNonRecursion(){
        inOrderNonRecursion(root);
    }
    public static void inOrderNonRecursion(BTree root){
        Stack<BTree> roots = new Stack<>();
        while (true){
            if (root != null){
                roots.push(root);
                root = root.left;
            }else{
                if (roots.empty())
                    break;
                else {
                    root = roots.pop();
                    visitNode(root);
                    root = root.right;
                }
            }
        }
    }

    public static void visitNode(BTree node){
        System.out.println(node.data);
    }


}

class BTree{
    int data;
    BTree left;
    BTree right;
    public BTree(int data){
        this.data = data;
    }
}
