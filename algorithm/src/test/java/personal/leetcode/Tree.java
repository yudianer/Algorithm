package personal.leetcode;

import org.junit.Test;
import sun.font.TrueTypeFont;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 树的遍历： 至少要测试两层
 *      1
       / \
 *    2   3
     / \  / \
 *  4  5  6  7
 *
 *      1
       / \
 *    2   3
     /     \
 *  4       5
 *
 *
 *
 **/

/**
 * todo
 *  非递归中：
 *      先序、中序、后序的特点是先往左遍历。
 *      Stack<TreeNode> roots 保存的都是根。
 *      但是在先序和中序中， 开始访问右节点时会丢失（抛弃）根信息。
 *          while(true){
 *              if(root != null){
 *                  // 如果是先序遍历： visitNode(root);
 *                  roots.push(root);
 *                  root = root.left;
 *              }else{
 *                  if(roots.empty()){
 *                      break;
 *                  }
 *                  root = roots.peek();
 *                  // 如果是中序遍历：visitNode(root);
 *                  root = roots.pop().right; //开始遍历右孩子时，根就没有用了，要pop出去。
 *              }
 *          }
 *          --
 *      而在后序遍历中只有左右子树都访问后才会访问根节点，之后才会抛弃根节点，所以后序遍历中在访问一个节点的时候，roots里面都是它的路径节点。
 *          while(true){
 *              if(root !=null){ //
 *                  roots.push(root);// roots里面的左子树肯定遍历完毕了
 *                  root = root.left;
 *              }else{ // root的来源有2处。 1：父节点的左节点， 2：父节点的右节点。
 *                      // root == null, 说明root的父节点的左子树肯定遍历完了，下面判断是不是右子树遍历完了，如果遍历完了则遍历根节点，否则遍历右子树
 *                  if(roots.empty())
 *                      break;
 *                  root == roots.peek();
 *                  if(root.right == null || root.right == lastNode){ //如果root的右子树为null 或者已经遍历过了，这时遍历root
 *                      visitNode(root);
 *                      lastNode = roots.pop();
 *                      root = null; //迫使下一次循环中从roots中提取新元素root，并访问root的右子树开始访问，这就形成了递归返回的过程。
 *                  }else{
 *                      root = root.right;
 *                  }
 *              }
 *          }
 *
 */
public class Tree {

    TreeNode t0 = new TreeNode(0);
    TreeNode t1 = new TreeNode(1);
    TreeNode t2 = new TreeNode(2);
    TreeNode t3 = new TreeNode(3);
    TreeNode t4 = new TreeNode(4);
    TreeNode t5 = new TreeNode(5);
    TreeNode t6 = new TreeNode(6);
    TreeNode t7 = new TreeNode(6);
    TreeNode t8 = new TreeNode(6);

    {
        t3.left = t5;
        t3.right = t1;
        t5.left = t6;
        t5.right = t2;
        t1.left = t0;
        t1.right = t8;
        t2.left = t7;
        t2.right = t4;
    }
    @Test
    public void testLow(){
        System.out.println(lowestCommonAncestor4(t3, t8, t1).val);
    }
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
        if (p == q)
            return p;
        if (p == null || q == null)
            return null;
        return lowestCommonAncestorRecursive(root, p, q);
    }

 /**
 *
 */

    /**
     *  递归的方式：
     *
     *  如果左孩子找到了，就推左孩子
     *  如果右孩子找到了，就推右孩子
     *  如果左右孩子都找到了，就推root
     *
     *  这种方法有一个漏洞：如果p和q其中有一个不属于这个树，那么返回的结果是那个在这个树里面的节点。
     */
    public TreeNode lowestCommonAncestorRecursive(TreeNode root, TreeNode p, TreeNode q){

        if (root == null)
            return null;

        if (root == p || root == q)
            return root;

        TreeNode leftFound = lowestCommonAncestorRecursive(root.left, p, q);
        TreeNode rightFound = lowestCommonAncestorRecursive(root.right, p, q);

        if (leftFound == null)
            return rightFound;
        if (rightFound == null)
            return leftFound;
        return root;
    }

    /**
     * 对于非递归方法：
     *
     *    找到两个节点的路径节点， 比较最近的相同元素即为最近共同父节点。时间复杂度最大：O（n） 空间复杂度最大为O(n)
     *      寻找两个元素的最近共同父节点。
     *      寻找父节点所以在遍历的过程中必须在访问该节点的时候它的父节点已经保存下来了，即roots中就是父节点，
     *      但是在先序和后序遍历的时候，一旦开始遍历右子树就会丢失父节点，所以只能使用后序遍历。
     *
     *      后序遍历寻找p，和q，找到后比较两节点的路径，定位最近的一个相同元素。
     */
    /**
     * todo
     *      先使用后续遍历找到一个节点node，那么此时roots里面的根，左侧节点肯定都是遍历过的。
     *      弹出roots 里面的根，以此先序遍历它右根的寻找另一个节点notFoundNode。
     *      注意：
     *          roots 里面不包含第一次找到的那个节点，所以再遍历roots里面的根的时候先对node的左右遍历，如果找不到才遍历roots
     *          roots 里面的根都不等于 notFoundNode
     *          遍历根时判断是否已经遍历过它的右根，用lastNode标记上次访问的root，然后通过roots.peek().right =? lastNode
     *          本次遍历完的根root要赋值给lastNode: lastNode = root;
     */

    public TreeNode lowestCommonAncestor4(TreeNode root, TreeNode p, TreeNode q) {
        if(p == q)
            return p;
        if (q == root || p == root)
            return root;
        TreeNode lastNode = null;
        TreeNode notFound ;
        Stack<TreeNode> roots = new Stack<>();
        // Tree，判断是否有左侧的节点
        while (true){
            if (root != null){
                if (p == root || q == root){
                    notFound = root == p ? q:p;
                    break;
                }
                roots.push(root);
                root = root.left;
            }else{
                root = roots.peek();
                if(root.right == null || root.right == lastNode){
                    lastNode = roots.pop();
                    root = null;
                }else{
                    root = root.right;
                }
            }
        }
        if (preOrderSearch(root.left, notFound) || preOrderSearch(root.right, notFound))
            return root;
        lastNode = root;
        while (roots.size() > 1){
            root = roots.pop();
            if (lastNode == root.left){
                if (preOrderSearch(root.right, notFound))
                    return root;
            }
            lastNode = root;
        }
        return roots.get(0);
    }

    public boolean preOrderSearch(TreeNode root, TreeNode node){
        if (root == null)
            return false;

        if (root == node)
            return true;

        Stack<TreeNode> roots = new Stack<>();
        while (true){
            if (root != null){
                if (root == node)
                    return true;
                roots.push(root);
                root = root.left;
            }else{
                if(roots.empty())
                    return false;
                root = roots.pop().right;
            }
        }
    }


    //todo 先找到一个节点，然后通过对roots中的节点root进行后序遍历，找到另一个节点, 那么root就是共同节点
    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        if(p == q)
            return p;
        if (q == root || p == root)
            return root;
        TreeNode lastNode = null;
        TreeNode notFound = null;
        Stack<TreeNode> roots = new Stack<>();
        // Tree，判断是否有左侧的节点
        while (true){
            if (root != null){
                roots.push(root);
                if (p == root || q == root){
                    notFound = root == p ? q:p;
                    break;
                }
                root = root.left;
            }else{
                root = roots.peek();
                if(root.right == null || root.right == lastNode){
                    lastNode = roots.pop();
                    root = null;
                }else{
                    root = root.right;
                }
            }
        }
        // 通过对roots进行后序遍历，在哪个根中找到notFound节点，就找到了共同节点
        TreeNode parent ;
        while (roots.size()>1){
            Stack<TreeNode> leftRoots = new Stack<>();
            parent = root = roots.pop();
            while (true){
                if (root != null){
                    leftRoots.push(root);
                    if (notFound == root)
                        return parent;
                    root = root.left;
                }else{
                    if (leftRoots.empty())
                        break;
                    root = leftRoots.peek();
                    if(root.right == null || root.right == lastNode){
                        lastNode = leftRoots.pop();
                        root = null;
                    }else{
                        root = root.right;
                    }
                }
            }
        }
        return roots.get(0);
    }
    //todo 找到两个节点的根，然后通过对比找到最近的共同节点
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if(p == q)
            return p;
        if (q == root || p == root)
            return root;
        TreeNode lastNode = null;
        boolean pFound = false;
        boolean qFound = false;
        Stack<TreeNode> roots = new Stack<>();
        List<TreeNode> ps = new ArrayList<>();
        List<TreeNode> qs = new ArrayList<>();
        // Tree，判断是否有左侧的节点
        while (true){
            if (root != null){
                roots.push(root);
                if (!pFound){
                    ps.add(root);
                    if (p == root){
                        pFound = true;
                    }
                }
                if (!qFound){
                    qs.add(root);
                    if (q == root){
                        qFound = true;
                    }
                }
                if (qFound && pFound)
                    break;
                root = root.left;
            }else{
                root = roots.peek();
                if(root.right == null || root.right == lastNode){
                    if(!pFound){
                        ps.remove(ps.size()-1);
                    }
                    if(!qFound){
                        qs.remove(qs.size()-1);
                    }
                    lastNode = roots.pop();
                    root = null;
                }else{
                    if (roots.size() == 1 && (pFound || qFound)){
                        return roots.get(0);
                    }
                    root = root.right;
                }
            }
        }

        int i = 0;
        while(i< ps.size() && i< qs.size() && ps.get(i) == qs.get(i)){
            ++i;
        }
        return ps.get(i-1);
    }

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if(p == q)
            return p;
        TreeNode lastNode = null;
        boolean pFound = false;
        boolean qFound = false;
        Stack<TreeNode> roots = new Stack<>();
        List<TreeNode> ps = new ArrayList<>();
        List<TreeNode> qs = new ArrayList<>();
        while (true){
            if (root != null){
                roots.push(root);
                ps.add(root);
                qs.add(root);
                root = root.left;
            }else{
                root = roots.peek();
                if(root.right == null || root.right == lastNode){
                    if(!pFound){
                        if(p == root){
                            pFound = true;
                            ps.add(root);
                        }else{
                            ps.remove(ps.size()-1);
                        }
                    }
                    if(!qFound){
                        if(q == root){
                            qFound = true;
                            qs.add(root);
                        }else{
                            qs.remove(qs.size()-1);
                        }
                    }

                    if(qFound && pFound)
                        break;

                    lastNode = roots.pop();
                    root = null;
                }else{
                    root = root.right;
                }
            }
        }

        int i = 0;
        while(true){
            if(ps.get(i) != qs.get(i)){
                return ps.get(i-1);
            }
            ++i;
        }
    }


    /**
     * 100. Same Tree
     * https://leetcode.com/problems/same-tree/
     *
     * 对两颗树进行遍历操作，如果:
     *      两者之间只有一个null;
     *      或者都不是null，但是两个值不一样，则返回false;
     *   roots.empty 则遍历完成，返回true
     *
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        Stack<TreeNode> roots = new Stack<>();
        Stack<TreeNode> rootsQ = new Stack<>();
        while(true){
            if(p != null){
                if(q == null || q.val != p.val){
                    return false;
                }
                roots.push(p);
                rootsQ.push(q);
                p = p.left;
                q = q.left;

            }else{
                if(q != null){
                    return false;
                }
                if(roots.empty()){
                    return true;
                }
                p = roots.pop().right;
                q = rootsQ.pop().right;
            }
        }
    }

    /**
     * 145. Binary Tree Postorder Traversal
     * https://leetcode.com/problems/binary-tree-postorder-traversal/submissions/
     *
     * 每一个元素在访问的时候都是作为根元素来访问的，访问完该元素后要返回到父节点。
     * 访问当前节点之后，返回父节点的时候设置lastNode=root，表明是从左孩子还是从右孩子返回的。
     * 访问该节点的时候判断lastNode =? root.right：
     *          如果==，说明是从右子树遍历过了（是从右返回的），那么访问root，设置root=null，表明以root为根节点的子树遍历完了，使得从roots中弹出父元素。
     *          否则， 说明右子树没有访问过，访问右子树。
     */
    /**
     *   另外一种方法，使用一个List来保存访问过的节点，如果root.right在该List中说明已经访问过了
     */
    public List<Integer> postorderTraversalNonRecursion(TreeNode root) {
        Stack<TreeNode> roots = new Stack<>();
        List<Integer> res = new ArrayList<>();
        TreeNode lastNode = null;
        while (true){
            if (root != null){// 在不为空的时候一直向左遍历，
                roots.push(root);
                root = root.left;
            }else{// root 如果为空，将最新的根拿出来。这种情况是在遍历完lastNode(子树root.left/root.right)完毕后， 设置（root.left/root.right =null），表明子树全部遍历完成。
                if(roots.empty()){
                    break;
                }
                root = roots.peek();
                if(root.right == null){// 当前节点的右子树为null，直接返回
                    res.add(roots.pop().val);
                    lastNode = root; // 当前元素设置为上一次访问的元素，它的父元素，它已经被访问过了。
                    root = null;
                }else{
                    if(root.right == lastNode){ // 当前节点的右子树是上一次访问的节点，所以以该节点为根元素的树访问完毕。
                        res.add(roots.pop().val);
                        lastNode = root;
                        root = null;
                    }else{//从左子树返回到root的，左子树遍历完了
                        root = root.right;
                    }
                }
            }
        }
        return res;
    }



    /**
     *
     */
    public static void preOrderNonRecursion(TreeNode root){
        Stack<TreeNode> roots = new Stack<>();
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

    public static void inOrderTravelNonRecursion(TreeNode root){
        Stack<TreeNode> roots = new Stack<>();
        while (true){
            if (root != null){
                roots.push(root);
                root = root.left;
            }else{//root == null, 将pop出roots最新根，并从右孩子访问
                if (roots.empty())
                    break;
                else {
                    root = roots.pop(); // 在访问右子树时它的父节点肯定被pop出去了
                    visitNode(root);
                    root = root.right; // 如果right == null, 下次循环将迫使从最新的根节点的右节点开始访问。
                }
            }
        }
    }

    public static void visitNode(TreeNode node){
        System.out.println(node.val);
    }


}

class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
 }