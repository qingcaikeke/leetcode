import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        ListNode head = new ListNode(2);
//        ListNode node2 = new ListNode(3);
//        ListNode node3 = new ListNode(4);
//        ListNode node4 = new ListNode(1);
//        // 构建链表          2  3 4     1
//        head.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//          int[] nums = new int[]{5,4,9,1000,52,37,12,0};
        int[] nums = new int[]{86,39,77,23,32,45,58,63,3,4,37,22};
        int n = nums.length;
          heapSort(nums);
        System.out.println(Arrays.toString(nums));
    }
    public static class ListNode {
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
    public static void heapSort(int[] arr){
        int n = arr.length;
        //从最后一个非叶子节点开始往前找
        //构造一个最大堆
        for(int i= (n/2)-1;i>=0;i--){
            heapify(arr,n,i);
        }
//        排序，每次取出当前最大值放到尾部
        for(int i=n-1;i>0;i--){
            //现在根一定是最大值，交换根和最后一个元素，然后把最后一个元素拿出二叉树
            //当前的最后一个元素的索引就是i
            swap(arr,0,i);
            //交换后重新调整，构造二叉堆
            heapify(arr,i,0);
        }

    }
    public static void heapify(int[] arr,int n,int i){
        int largest = i;
        //找到i的左右子节点
        int l = 2*i+1;
        int r = 2*i+2;
        if(l<n && arr[largest] < arr[l]){
            largest = l;
        }
        if(r<n && arr[largest] < arr[r]){
            largest = r;
        }
        if(largest != i){
            swap(arr,largest,i);
            //交换后，被换下去那个节点处在一个新的位置，为了保证最大堆，还需要调整他的子树
            heapify(arr, n, largest);
        }
    }
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
