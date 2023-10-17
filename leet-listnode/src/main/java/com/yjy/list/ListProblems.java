package com.yjy.list;

import javax.swing.undo.CannotUndoException;
import java.lang.invoke.VarHandle;
import java.util.*;

public class ListProblems {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //    反转链表：递归，迭代
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
    public ListNode reverseList1(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }

    //    删除倒数第k个：双指针
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode front = dummy, behind = dummy;
        for (int i = 0; i < n; i++) {
            front = front.next;
        }
        while (front.next != null) {
            front = front.next;
            behind = behind.next;
        }
        behind.next = behind.next.next;
        return dummy.next;
    }

    //    检测环的起始位置：双指针  法二：hashset
    public ListNode detectCycle(ListNode head) {
//        两个节点，一个先走环的数目x个，相遇位置一定在起点处（因为从起点走环的数目个点又回到起点）
//        需要有环的数目，双指针，一个走k，一个走2k，相遇，多走的一定扣了n圈，所以k是环数目x的整数倍
        ListNode first = head, second = head;
        while (second != null && second.next != null) {
            first = first.next;
            second = second.next.next;
            if (first == second) {
                ListNode third = head;
                while (third != first) {
                    third = third.next;
                    first = first.next;
                }
                return third;
            }
        }
        return null;
    }

    //    检测两个链表相交位置：1.暴力循环，遍历第一个链表，内层遍历第二个链表看有没有相同的. 1.1 优化：哈希set
//    2.把b的头接到a的尾上，成环，求环的起点     3.从后往前遍历，找最后一个相同的节点（用栈，因为希望最后的节点最先比较）
//    4.如果两个链表一样长的话，从前往后找第一个相同的即可，所以先求长度，让长的先走（a-b）个节点
//    5.a的尾接b的头，b的尾接a的头，构造两个环形链表，因两表长度相同，故会同时到达环的起点
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode pa = headA, pb = headB;
        while (pa != pb) {
            pa = pa.next == null ? headB : pa.next;
            pb = pb.next == null ? headA : pb.next;
        }
        return pa;
    }

    //    leet2：两数相加，因为要先加个位再加十位，所以先倒序，再逐位相加
//    因为从后往前加，所以可以用栈，也可以使用递归
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverseList(l1);
        l2 = reverseList(l2);
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int res = carry;
            if (l1 != null) res += l1.val;
            if (l2 != null) res += l2.val;
            carry = res / 10;
            cur.next = new ListNode(res % 10);
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
            cur = cur.next;
        }
        if (carry != 0) cur.next = new ListNode(carry);
        return reverseList(dummy.next);
    }

    //    比上面慢很多
    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        while (l1 != null) {
            stack1.push(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }
        ListNode cur = null;//使用头插法避免最后需要反转
        int carry = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
            int a = stack1.isEmpty() ? 0 : stack1.pop();
            int b = stack2.isEmpty() ? 0 : stack2.pop();
            int res = carry + a + b;
            carry = res / 10;
            ListNode pre = new ListNode(res % 10);
            pre.next = cur;
            cur = pre;
        }
        return cur;
    }
    //重排链表，找到中间位置，把后半断开，反转，再拼接
    //法2，希望能由下标访问，所以用线性表储存链表 list<listnode> = new arraylist
    //类似的：回文链表：1.双指针，找到中间，后半反转2.存到arraylist
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;//可以使用dummy来防止head=null,但不能保证只有一个节点
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode head2 = slow.next;
        slow.next = null;
        head2 = reverseList(head2);
        while (head != null && head2 != null) {
            ListNode temp1 = head.next;
            ListNode temp2 = head2.next;
            head.next = head2;
            head2.next = temp1;
            head = temp1;
            head2 = temp2;
        }
    }

    //    展平多级列表：遇到一个child就要处理他，让他的子链表扁平化，这是一个深度优先搜索的过程
    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    };
    public Node flatten(Node head) {
        Node res = head;
        flattenGetTail(head);
        return res;
    }
    public Node flattenGetTail(Node head) {
        Node tail = head;
        while (head != null) {
            Node next = head.next;
            if (head.child != null) {
                //先连前面
                Node childTail = flattenGetTail(head.child);
                head.next = head.child;
                head.child.prev = head;
                head.child = null;
                //再连后面
                childTail.next = next;
                if (next != null) next.prev = childTail;
                //移动tail
                tail = childTail;
            } else {
                tail = head;
            }
            head = next;
        }
        return tail;
    }
    //循环有序链表的插入 1.考虑插入规则，找到两个节点，前一个值小于val小于后一个，
//    2.考虑特例，插入的比最大的还大 ， 或比最小的还小
//    3.考虑边界条件：插入规则要求必须有两个节点，但可能只给1个，或者不给
    class Node {
        public int val;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }


    }
    public Node insert(Node head, int insertVal) {
        Node node = new Node(insertVal);
        if (head == null) {
            node.next = node;
            head = node;
            return head;
        }
        if (head.next == head) {
            head.next = node;
            node.next = head;
            return head;
        }
        //给的head不一定是最小值，遍历一圈去找满足条件的位置插入，如果没找到，说明插入在最大元素的后面
        Node cur = head, next = head.next;
        while (next!=head){//有可能插到head和head前一个位置之间，这点要处理
            //使用if判断位置可以省略biggest的内存
            if(cur.val<=insertVal&&insertVal<=next.val) break;
            else if(cur.val>next.val) {
                if(insertVal>=cur.val || insertVal<=next.val) break;
            }
            cur = next;
            next = next.next;
        }
        cur.next = node;
        node.next = next;
        return head;
    }
    //合并两个有序链表
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1==null) return list2;
        if(list2==null) return list1;
        if(list1.val<list2.val){
            list1.next = mergeTwoLists(list1.next,list2);
            return list1;
        }
        else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }
    //复杂链表的复制：哈希表 新表.next = 原表->原表.next->原表.next的复制
    //正常链表的复制，每轮创建新节点即可，但是这样没法调整random的指向，因为不知道她有没有被建立过，建立了也没法快速找到
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
    public Node copyRandomList(Node head) {
        if(head==null) return head;
        Map<Node,Node> map = new HashMap<>();
        Node cur = head;
        while (cur!=null){
            map.put(cur,new Node(cur.val));
            cur = cur.next;
        }
        cur = head;
        while (cur!=null){
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head);
    }
    //思路：只要保证能找到新random的位置，就能进行指针的填充
    //解法：拼接+拆分
    public Node copyRandomList1(Node head){
        if(head==null)return head;
        Node cur = head;
        while (cur!=null){
            Node newCur = new Node(cur.val);
            newCur.next = cur.next;
            cur.next = newCur;
            cur = cur.next.next;
        }
        cur = head;
        while (cur!=null){
            if(cur.random!=null) {
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }
        Node originNode = head;
        Node newNode = head.next;
        Node res = newNode;
        while (originNode!=null){
            originNode.next = newNode.next;
            if(originNode.next != null) newNode.next = originNode.next.next;
            originNode = originNode.next;
            newNode = newNode.next;
        }
        return res;
    }
    //删除链表中重复的节点：哈希表
    public ListNode removeDuplicateNodes(ListNode head) {
        Set<Integer> set  = new HashSet<>();
        ListNode cur = head;
        set.add(cur.val);
        while (cur.next!=null){
            if(set.contains(cur.next.val)) {
                cur.next = cur.next.next;
            }
            else {
                set.add(cur.next.val);
                cur = cur.next;//只有没删节点才需要移动，因为每次看的是cur.next要不要删
            }
        }
        return head;
    }
    //两两交换链表中的节点:迭代递归
    public ListNode swapPairs(ListNode head) {
        if(head==null || head.next==null) return head;
        ListNode node = swapPairs(head.next.next);
        ListNode newHead = head.next;
        head.next.next = head;
        head.next = node;
        return newHead;
    }
    //迭代没写出来
    public ListNode swapPairs(ListNode head){
        //每次需要关注的是三个节点，一个前驱节点(cur)，两个需要交换的节点(front,end)；实际上是四个，end后的也是
        ListNode dummy = new ListNode(0,head);
        ListNode cur = dummy;
        while (cur.next != null && cur.next.next != null){
            ListNode front = cur.next;
            ListNode end = cur.next.next;
            front.next = end.next;
            end.next = front;
            cur.next = end;
            cur = front;
        }
        return dummy.next;
    }
    //旋转链表，没有考虑到k可能大于链表总是，造成多次循环
    //没有想到拼接成环，旋转使用拼接成环可以得到所由可能性，如abc旋转，主要拼接abcabc，然后确定首尾位置或是断开位置
    //本题不成环也可以，不过要考虑循环后还是原装
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null) return head;
        int n =1;
        ListNode cur = head;
        while (cur.next!=null){
            n++;
            cur = cur.next;
        }//为了成环，正常从0开始计数
        cur.next = head;
        int pos = n - k%n;//要找到倒数第k个节点的前一个节点，然后断开
//        倒数第k个节点，下标0开始，为n-k，前一个节点n-k-1;但是是从最后一个节点开始循环，而非head，所以需要多走一步
        for(int i=0;i<pos;i++){
            cur = cur.next;
        }
        ListNode newHead = cur.next;
        cur.next = null;
        return newHead;
    }
    //删除排序链表中的重复元素
    //多解法：1.一个一个看 2. 双指针 3.递归 但后两种没必要
    public ListNode deleteDuplicates1(ListNode head) {
        if(head==null)return head;
        ListNode res = head;
        while (head.next!=null){
            if(head.val == head.next.val){
                head.next = head.next.next;
            }
            else head = head.next;
        }
        return res;
    }
    public ListNode deleteDuplicates(ListNode head){
        if(head==null || head.next==null ) return head;
        ListNode node = deleteDuplicates(head.next);
        if(head.val == node.val) return node;
        else {
            head.next = node;
            return head;
        }
    }
    //删除重复元素2：只要有重的就删除，一个不留
    //写的不好
    public ListNode deleteDuplicates2(ListNode head) {
        ListNode dummy = new ListNode(0,head);
        ListNode cur = dummy;
        while (cur.next!=null&&cur.next.next!=null){
            //如果出现相等
            if(cur.next.val==cur.next.next.val){
                int value = cur.next.val;
                while (cur.next!=null&&cur.next.val==value){
                    cur.next = cur.next.next;
                }
            }
            //如果没有
            else {
                cur = cur.next;
            }
        }
        return dummy.next;
    }
    //分隔链表，把小于x的放前面，大于x的放后面，相对位置不变
    //没想到搞两个链表，想到了头插但是写的不好
    public ListNode partition(ListNode head, int x) {
        ListNode dummy = new ListNode();
        ListNode small = dummy , large = null;
        ListNode cur = head;
        while (cur!=null){
            if(cur.val<x){
                //特殊情况
                if(large==null){
                    small = cur;
                    cur = cur.next;
                }
                //一般情况
                else {
                    large.next = cur.next;
                    cur.next = small.next;
                    small.next = cur;
                    small = small.next;
                    cur = large.next;
                }
            }
            else {
                large = cur;
                cur = large.next;
            }
        }
        return dummy.next;
    }
    public ListNode partition1(ListNode head, int x){
        ListNode small = new ListNode(0);
        ListNode smallHead = small;
        ListNode big = new ListNode(0);
        ListNode bigHead = big;
        while (head!=null){
            if(head.val<x){
                small.next = head;
                small = small.next;
            }else {
                big.next = head;
                big = big.next;
            }
            head = head.next;
        }
        big.next = null;//一定别忘，否则很可能出现环
        small.next = bigHead.next;
        return smallHead.next;
    }
    //反转链表，反转部分,找到反转位置，断链，反转，拼接
    public ListNode reverseBetween(ListNode head, int left, int right) {
        //本题要把四个变量都创建出来，begin，behind，leftNode,rightNode，否则很可能出问题
        ListNode dummy = new ListNode(0,head);//还是得有dummy，要不不好返回
        ListNode cur = dummy;//left给1的话应该保证begin为dummy
        for(int i=0;i<left-1;i++){
            cur = cur.next;
        }
        ListNode begin = cur;//begin为left的前一个点
        ListNode leftNode = cur.next;
        for(int i=0;i<=right-left;i++){
            cur = cur.next;
        }
        ListNode rightNode  = cur;
        ListNode behind = cur.next;//behind为right的后一个点

        begin.next = null;
        rightNode.next = null;

        reverseList(leftNode);
        begin.next = rightNode;
        leftNode.next = behind;
        return dummy.next;
    }
    //使用迭代然后进行头插更好
    public ListNode reverseBetween1(ListNode head, int left, int right){
        //需要left前一个节点，left可能等于1，所以需要dummy；
        ListNode dummy = new ListNode(0,head);
        ListNode cur = dummy;
        for (int i=0;i<left-1;i++){
            cur = cur.next;
        }//此时来到left的前一个节点
        ListNode pre = cur;
        cur = cur.next;
        for(int i = left;i<right;i++){
            ListNode next = cur.next;
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return dummy.next;
    }
    //反转链表的前n个节点：迭代，递归
    ListNode succ = null;
    public ListNode reverseN(ListNode head,int n){
        if(n==1) {
            head.next = succ;
            return head;
        }
        ListNode newHead = reverseN(head.next,n-1);
        head.next.next = head;
        head.next = succ;
        return newHead;
    }
    //根据题目要求1.o(1)时间定位：哈希表，
    // 定位元素后移到最前面，表示最近调用，而移动一个节点有需要涉及前驱节点，所以需要双向链表
    //get要做的事：map中找到节点，addtohead;
    //put: 第一次，创建节点，addtohead,放入map中，size++
    //      之后,拿出节点，改变val，移到最前面分两部，一部断链，一部addtohead；
    //      可能会超出容量，这时候要移除链尾，同时删除map
    class LRUCache {
        class DLinkedNode{
            int key;
            int val;
            DLinkedNode prev;
            DLinkedNode next;
            DLinkedNode(){};
            DLinkedNode (int key,int val){
                this.key = key;
                this.val = val;
            }
        }
        Map<Integer,DLinkedNode> map = new HashMap<>();
        int capacity;
        int size = 0;
        DLinkedNode head;//是类的一个字段，在构造函数里创建的化外部用不了
        DLinkedNode tail;
        public LRUCache(int capacity){
            this.capacity = capacity;
            head = new DLinkedNode();
            tail = new DLinkedNode();
            head.next = tail;
            tail.prev = head;
        }
        public int get(int key){
            DLinkedNode node = map.get(key);
            if(node==null) return -1;
            deleteNode(node);
            addToHead(node);
            return node.val;
        }
        public void put(int key,int val){
            //第一次放入和不是第一次
            if(!map.containsKey(key)){
                DLinkedNode node = new DLinkedNode(key,val);
                map.put(key,node);
                addToHead(node);
                size++;
                if(size>capacity){
                    DLinkedNode needToDelete = tail.prev;
                    deleteNode(needToDelete);
                    map.remove(needToDelete.key);//map别忘了
                    size--;
                }
            }
            else {//第二次put和get是一样的，都需要先断链，再移到前面
                DLinkedNode node = map.get(key);
                node.val = val;
                deleteNode(node);
                addToHead(node);
            }
        }
        public void addToHead(DLinkedNode node){
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }
        public DLinkedNode deleteNode(DLinkedNode node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return node;
        }
    }
    //对链表进行插入排序：写的不好
    public ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode(0,head);
        ListNode lastSorted = head;
        ListNode cur = lastSorted.next;
        while (cur!=null){
            if(cur.val >= lastSorted.val){
                lastSorted = lastSorted.next;
            }
            else {
                lastSorted.next = cur.next;
                ListNode pos = dummy;
                while (pos.next.val<cur.val){
                    pos = pos.next;
                }
                cur.next = pos.next;
                pos.next = cur;
            }
            cur = lastSorted.next;
        }
        return dummy.next;
    }



























}