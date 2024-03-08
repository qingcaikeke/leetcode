import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 239.滑动窗口最大值 1.双端队列(单调队列) 2.优先队列,与双端单调队列的区别在于，插入一个新元素，是在队列内部进行了重排序，而没有删除那些应该删除的元素 3.手搓堆排序实现优先队列
 */
public class Other {
    public int[] maxSlidingWindow1(int[] nums, int k) {
        int[] res = new int[nums.length-k+1];
        Deque<Integer> deque = new LinkedList<>();
        for(int i=0;i<nums.length;i++){
            //nums[i]已经进入窗口了，比nums[i]小的元素一定不可能是窗口的最大值
            while (!deque.isEmpty() && deque.peekLast()<nums[i]){
                deque.pollLast();
            }
            deque.addLast(nums[i]);
            //[i-k]已经超出窗口范围了,头需要出去了
            if(i>=k && nums[i-k]==deque.peekFirst()){
                deque.pollFirst();
            }
            if(i>=k-1) res[i-k+1] = deque.peekFirst();
        }
        return res;
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        //优先级队列，插入一个元素是内部进行了重排序，原来的元素没有被删除，也只能保证队头元素最优先，参考堆
        //PriorityQueue底层就是创建了一个小顶堆
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0]-o1[0];//把大的放前面
            }
        });
        for(int i=0;i<k;i++){
            pq.add(new int[]{nums[i],i});
        }
        int[] res = new int[nums.length-k+1];
        res[0] = pq.peek()[0];
        for(int i=k;i<nums.length;i++){
            pq.add(new int[]{nums[i],i});
            while (i-k>= pq.peek()[1]){
                pq.poll();
            }
            res[i-k+1] = pq.peek()[0];
        }
        return res;
    }

}
